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

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import info.pelleritoudacity.android.rcapstone.R;
import info.pelleritoudacity.android.rcapstone.data.db.AppDatabase;
import info.pelleritoudacity.android.rcapstone.data.db.AppExecutors;
import info.pelleritoudacity.android.rcapstone.data.db.entry.PrefSubRedditEntry;
import info.pelleritoudacity.android.rcapstone.data.db.util.DataUtils;
import info.pelleritoudacity.android.rcapstone.data.db.viewmodel.PrefStarViewModelFactory;
import info.pelleritoudacity.android.rcapstone.data.db.viewmodel.PrefStarsViewModel;
import info.pelleritoudacity.android.rcapstone.data.db.viewmodel.PrefViewModel;
import info.pelleritoudacity.android.rcapstone.data.db.viewmodel.PrefViewModelFactory;
import info.pelleritoudacity.android.rcapstone.ui.activity.ManageActivity;
import info.pelleritoudacity.android.rcapstone.ui.adapter.ManageAdapter;
import info.pelleritoudacity.android.rcapstone.ui.helper.OnStartDragListener;
import info.pelleritoudacity.android.rcapstone.ui.helper.SimpleItemTouchHelperCallback;

import info.pelleritoudacity.android.rcapstone.utility.Costant;
import info.pelleritoudacity.android.rcapstone.utility.MapUtil;
import info.pelleritoudacity.android.rcapstone.utility.Preference;


public class ManageFragment extends Fragment
        implements ManageAdapter.OnSubScriptionClick, OnStartDragListener {

    @SuppressWarnings({"WeakerAccess", "CanBeFinal", "unused"})
    @BindView(R.id.rv_fragment_manage)
    RecyclerView mRecyclerView;

    private ManageAdapter mAdapter;
    private AppDatabase mDb;
    private ItemTouchHelper mItemTouchHelper;
    private Unbinder unbinder;
    private boolean isRestore;
    private int mToPosition;


    public ManageFragment() {
        mDb = AppDatabase.getInstance(getActivity());
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
        mDb = AppDatabase.getInstance(getActivity());
        setupViewModel();

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

        mAdapter = new ManageAdapter(getActivity(), mDb, this, this);

        mRecyclerView.setAdapter(mAdapter);

        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(mAdapter);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(mRecyclerView);


        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (isRestore) {
            alerDialog(getActivity());
        }

    }


    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        mItemTouchHelper.startDrag(viewHolder);
    }

    @Override
    public void onClickStar(int visible, String name) {
        int updateVisibleValue = (visible != 0) ? 0 : 1;
        updateVisibleStar(updateVisibleValue, name);

        if (visible != 0) {
            MapUtil.removeElementPrefSubreddit(getActivity(), name);
        } else {
            MapUtil.addElementPrefSubreddit(getActivity(), name);
        }

    }

    @Override
    public void onItemRemove(int position, String description) {
        DataUtils dataUtils = new DataUtils(getActivity(), mDb);
        dataUtils.updateManageRemoved(description);
        startActivity(new Intent(getActivity(), ManageActivity.class)
                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_NO_ANIMATION));


    }

    @Override
    public void onItemMove(int toPosition) {
        mToPosition = toPosition;
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
                if (new DataUtils(context, mDb).updateManageRestore()) {
                    startActivity(new Intent(context, ManageActivity.class)
                            .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                }
            });

            dialog.setNegativeButton(R.string.text_restore_confirm_no_reset, (dlg, which) -> dlg.cancel());

            AlertDialog al = dialog.create();
            al.show();

        }

    }

    @SuppressWarnings("WeakerAccess")
    public void updateVisibleStar(int visible, String category) {
        AppExecutors.getInstance().diskIO().execute(() -> mDb.prefSubRedditDao().updateRecordByVisibleStar(visible, category));

        if ((!TextUtils.isEmpty(category)) &&
                (visible == Costant.DEFAULT_SUBREDDIT_VISIBLE)) {

            String stringPref = restoreStarsOrderFromDb();
            if (!TextUtils.isEmpty(stringPref)) {
                Preference.setSubredditKey(getActivity(), stringPref);

            }

        }

    }

    private String restoreStarsOrderFromDb() {
        final String[] stringPref = {""};

        PrefStarViewModelFactory factoryStars = new PrefStarViewModelFactory(mDb, 0, 1);
        final PrefStarsViewModel viewModel = ViewModelProviders.of(this, factoryStars).get(PrefStarsViewModel.class);

        viewModel.getTask().observe(this, prefSubRedditEntries -> {
            if (prefSubRedditEntries != null) {
                String name;
                int i = 0;

                while (i < prefSubRedditEntries.size()) {
                    i += 1;
                    name = prefSubRedditEntries.get(i).getName();
                    if (!TextUtils.isEmpty(name)) {
                        if (i < prefSubRedditEntries.size() - 1) {
                            //noinspection StringConcatenationInLoop
                            stringPref[0] += name + Costant.STRING_SEPARATOR;
                        } else {
                            //noinspection StringConcatenationInLoop
                            stringPref[0] += name;
                        }
                    }
                }
            }

        });
        return stringPref[0];
    }

    private void setupViewModel() {

        PrefSubRedditEntry entry = new PrefSubRedditEntry();
        entry.setPosition(mToPosition);
        PrefViewModelFactory factory = new PrefViewModelFactory(mDb, entry);
        final PrefViewModel viewModel = ViewModelProviders.of(this, factory).get(PrefViewModel.class);
        viewModel.getTask().observe(this, prefSubRedditEntries -> {
            if ((prefSubRedditEntries != null)  && (mAdapter != null)) {
                mAdapter.setPrefSubRedditEntry(prefSubRedditEntries);
            }
        });
    }

}
