package info.pelleritoudacity.android.rcapstone.ui.fragment;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import info.pelleritoudacity.android.rcapstone.R;
import info.pelleritoudacity.android.rcapstone.data.Contract;
import info.pelleritoudacity.android.rcapstone.ui.adapter.SubRedditSelectedAdapter;
import info.pelleritoudacity.android.rcapstone.utility.Costant;
import timber.log.Timber;

import static info.pelleritoudacity.android.rcapstone.utility.Costant.SUBREDDIT_SELECTED_LOADER_ID;

public class SubRedditSelectedFragment extends Fragment
        implements LoaderManager.LoaderCallbacks<Cursor>{

    @SuppressWarnings({"WeakerAccess", "CanBeFinal", "unused"})
    @BindView(R.id.rv_fragment_reddit_selected)
    RecyclerView mRecyclerView;

    private Context mContext;
    private Unbinder unbinder;

    private static String sStrId = null;
    private static Parcelable sParcelState;

    private LinearLayoutManager mLayoutManager;
    private SubRedditSelectedAdapter mAdapter;

    public SubRedditSelectedFragment() {
    }

    public static SubRedditSelectedFragment newInstance(String strId) {
        SubRedditSelectedFragment fragment = new SubRedditSelectedFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Costant.EXTRA_FRAGMENT_SUBREDDIT_SELECTED, strId);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = getActivity();

        if (getArguments() != null) {
            sStrId = getArguments().getString(Costant.EXTRA_FRAGMENT_SUBREDDIT_SELECTED);
        }

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_subreddit_selected, container, false);

        unbinder = ButterKnife.bind(this, view);

        mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mRecyclerView.setHasFixedSize(true);

        mAdapter = new SubRedditSelectedAdapter(this);

        mRecyclerView.setAdapter(mAdapter);

        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (getActivity() != null) {
            getActivity().getSupportLoaderManager().initLoader(SUBREDDIT_SELECTED_LOADER_ID, null, this).forceLoad();

        }
        if (savedInstanceState != null) {
            sParcelState = savedInstanceState.getParcelable(Costant.EXTRA_FRAGMENT_SELECTED_STATE);
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        sParcelState = mLayoutManager.onSaveInstanceState();
        outState.putParcelable(Costant.EXTRA_FRAGMENT_SELECTED_STATE, sParcelState);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (sParcelState != null) {
            mLayoutManager.onRestoreInstanceState(sParcelState);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        return new SubRedditDetailFragmentAsyncTask(Objects.requireNonNull(getActivity()));
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        if ((data != null) && (mAdapter != null)) {
            mAdapter.swapCursor(data);
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        mAdapter.swapCursor(null);
    }


    private static class SubRedditDetailFragmentAsyncTask extends AsyncTaskLoader<Cursor> {

        Cursor cursorData = null;

        SubRedditDetailFragmentAsyncTask(@NonNull Context context) {
            super(context);
        }

        @Override
        protected void onStartLoading() {
            if (cursorData != null) {
                deliverResult(cursorData);
            } else {
                forceLoad();
            }
        }

        @Nullable
        @Override
        public Cursor loadInBackground() {
            try {
                Uri uri = Contract.T3dataEntry.CONTENT_URI;
                String selection = Contract.T3dataEntry.COLUMN_NAME_ID + " =?";
                String[] selectionArgs = new String[]{sStrId};

                return getContext().getContentResolver().query(uri,
                        null,
                        selection,
                        selectionArgs,
                        null);

            } catch (Exception e) {
                Timber.e("Failed to asynchronously load data. ");
                e.printStackTrace();
                return null;
            }
        }

        @Override
        public void deliverResult(Cursor data) {
            if ((data != null) && (data.getCount() > 0)) {
                cursorData = data;
                super.deliverResult(data);
            } else {
                forceLoad();
            }
        }

    }
}
