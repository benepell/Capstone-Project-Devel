/*
 *
 * ______                _____ _
 * | ___ \              /  ___| |
 * | |_/ /___ __ _ _ __ \ `--.| |_ ___  _ __   ___
 * |    // __/ _` | '_ \ `--. \ __/ _ \| '_ \ / _ \
 * | |\ \ (_| (_| | |_) /\__/ / || (_) | | | |  __/
 * \_| \_\___\__,_| .__/\____/ \__\___/|_| |_|\___|
 *                | |
 *                |_|
 *
 * Copyright (C) 2018 Benedetto Pellerito
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package info.pelleritoudacity.android.rcapstone.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import info.pelleritoudacity.android.rcapstone.R;
import info.pelleritoudacity.android.rcapstone.data.db.Contract;
import info.pelleritoudacity.android.rcapstone.data.db.util.DataUtils;
import info.pelleritoudacity.android.rcapstone.ui.activity.ManageActivity;
import info.pelleritoudacity.android.rcapstone.ui.adapter.ManageAdapter;
import info.pelleritoudacity.android.rcapstone.ui.helper.OnStartDragListener;
import info.pelleritoudacity.android.rcapstone.ui.helper.SimpleItemTouchHelperCallback;

import info.pelleritoudacity.android.rcapstone.utility.Costant;
import info.pelleritoudacity.android.rcapstone.utility.MapUtil;
import info.pelleritoudacity.android.rcapstone.utility.Preference;
import timber.log.Timber;

import static info.pelleritoudacity.android.rcapstone.utility.Costant.REDDIT_LOADER_ID;

public class ManageFragment extends Fragment
        implements ManageAdapter.OnSubScriptionClick, OnStartDragListener, LoaderManager.LoaderCallbacks<Cursor> {

    @SuppressWarnings({"WeakerAccess", "CanBeFinal", "unused"})
    @BindView(R.id.rv_fragment_manage)
    RecyclerView mRecyclerView;

    private ManageAdapter mAdapter;
    private ItemTouchHelper mItemTouchHelper;
    private Unbinder unbinder;
    private boolean isRestore;

    public ManageFragment() {
    }

    public static ManageFragment newInstance(boolean restore) {
        ManageFragment fragment = new ManageFragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean(Costant.EXTRA_FRAGMENT_MANAGE_RESTORE, restore);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            isRestore = getArguments().getBoolean(Costant.EXTRA_FRAGMENT_MANAGE_RESTORE);

        }

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_manage, container, false);

        unbinder = ButterKnife.bind(this, view);

        if ((getActivity() != null) && (getActivity().findViewById(R.id.fragment_list_container) != null)) {
            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
            mRecyclerView.setLayoutManager(layoutManager);
        }

        mRecyclerView.setHasFixedSize(true);

        mAdapter = new ManageAdapter(getContext(), this, this);

        mRecyclerView.setAdapter(mAdapter);

        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(mAdapter);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(mRecyclerView);


        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getLoaderManager().initLoader(REDDIT_LOADER_ID, null, this);

        if (isRestore) {
            alerDialog(getActivity());
        }

    }

    @Override
    public void onResume() {
        super.onResume();

        getLoaderManager().restartLoader(REDDIT_LOADER_ID, null, this);

    }

    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        mItemTouchHelper.startDrag(viewHolder);
    }

    @Override
    public void onClickStar(int visible, String name) {
        DataUtils utils = new DataUtils(getActivity());
        int updateVisibleValue = (visible != 0) ? 0 : 1;
        utils.updateVisibleStar(updateVisibleValue, name);
        if (utils.updateVisibleStar(updateVisibleValue, name)) {

            if (visible != 0) {
                MapUtil.removeElementPrefSubreddit(getActivity(), name);
            } else {
                MapUtil.addElementPrefSubreddit(getActivity(), name);
            }

            getLoaderManager().restartLoader(REDDIT_LOADER_ID, null, this);

        }

    }

    @Override
    public void onItemRemove(int position, String description) {
        DataUtils dataUtils = new DataUtils(getActivity());
        if (dataUtils.updateManageRemoved(description)) {
            startActivity(new Intent(getActivity(), ManageActivity.class)
                    .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_NO_ANIMATION));
        }

    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        return new RedditFragmentAsyncTask(Objects.requireNonNull(getActivity()));
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        if ((data != null) && (mAdapter != null)) {
            mAdapter.swapCursor(data);
        }

    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        if (mAdapter != null) {
            mAdapter.swapCursor(null);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void alerDialog(Context context) {

        if (context != null) {
            int theme = R.style.confirmDialogLight;
            if (Preference.isNightMode(context)) {
                theme = R.style.confirmDialogDark;
            }
            AlertDialog.Builder dialog = new AlertDialog.Builder(context, theme);
            dialog.setTitle(R.string.title_restore_confirm);
            dialog.setMessage(R.string.text_restore_data);
            dialog.setCancelable(true);

            dialog.setPositiveButton(R.string.text_positive_restore_confirm, (dialog1, which) -> {
                if (new DataUtils(context).updateManageRestore()) {
                    startActivity(new Intent(context, ManageActivity.class)
                            .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                }
            });

            dialog.setNegativeButton(R.string.text_restore_confirm_no_reset, (dlg, which) -> dlg.cancel());

            AlertDialog al = dialog.create();
            al.show();

        }

    }

    private static class RedditFragmentAsyncTask extends AsyncTaskLoader<Cursor> {

        Cursor mCursor = null;

        RedditFragmentAsyncTask(@NonNull Context context) {
            super(context);
        }

        @Override
        protected void onStartLoading() {
            if (mCursor != null) {
                deliverResult(mCursor);
            } else {
                forceLoad();
            }

        }

        @Nullable
        @Override
        public Cursor loadInBackground() {
            try {
                String[] projection = {
                        " Distinct ".concat(Contract.PrefSubRedditEntry.COLUMN_NAME_NAME),
                        Contract.PrefSubRedditEntry.COLUMN_NAME_IMAGE,
                        Contract.PrefSubRedditEntry.COLUMN_NAME_VISIBLE,
                        Contract.PrefSubRedditEntry.COLUMN_NAME_POSITION,
                        Contract.PrefSubRedditEntry.COLUMN_NAME_TIME_LAST_MODIFIED
                };

                return getContext().getContentResolver().query(Contract.PrefSubRedditEntry.CONTENT_URI,
                        projection,
                        Contract.PrefSubRedditEntry.COLUMN_NAME_REMOVED + " =?",
                        new String[]{"0"},
                        Contract.PrefSubRedditEntry.COLUMN_NAME_POSITION + " ASC");


            } catch (Exception e) {
                Timber.e(" Manage Failed to asynchronously load data.   ");
                e.printStackTrace();

                return null;
            }
        }

        @Override
        public void deliverResult(Cursor data) {
            mCursor = data;
            super.deliverResult(data);
        }
    }

}
