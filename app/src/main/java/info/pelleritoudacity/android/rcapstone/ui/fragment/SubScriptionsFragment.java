package info.pelleritoudacity.android.rcapstone.ui.fragment;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import info.pelleritoudacity.android.rcapstone.R;
import info.pelleritoudacity.android.rcapstone.data.Contract;
import info.pelleritoudacity.android.rcapstone.ui.helper.OnStartDragListener;
import info.pelleritoudacity.android.rcapstone.ui.helper.SimpleItemTouchHelperCallback;
import info.pelleritoudacity.android.rcapstone.ui.adapter.SubScriptionsAdapter;

import timber.log.Timber;

import static info.pelleritoudacity.android.rcapstone.utility.Costants.REDDIT_LOADER_ID;

public class SubScriptionsFragment extends Fragment
        implements OnStartDragListener, LoaderManager.LoaderCallbacks<Cursor> {

    @SuppressWarnings({"WeakerAccess", "CanBeFinal", "unused"})
    @BindView(R.id.rv_fragment_reddit)
    RecyclerView mRecyclerView;

    private SubScriptionsAdapter mAdapter;
    private ItemTouchHelper mItemTouchHelper;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getActivity() != null) {
            getActivity().getSupportLoaderManager().initLoader(REDDIT_LOADER_ID, null, this);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        restartLoader();

    }

    public void restartLoader(){
        if (getActivity() != null) {
            getActivity().getSupportLoaderManager().restartLoader(REDDIT_LOADER_ID, null, this);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_reddit, container, false);

        ButterKnife.bind(this, view);

        if ((getActivity() != null) && (getActivity().findViewById(R.id.fragment_list_container) != null)) {
            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
            mRecyclerView.setLayoutManager(layoutManager);
        }

        mRecyclerView.setHasFixedSize(true);

        mAdapter = new SubScriptionsAdapter(getContext(), this);

        mRecyclerView.setAdapter(mAdapter);

        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(mAdapter);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(mRecyclerView);


        return view;
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
        mAdapter.swapCursor(null);
    }


    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        mItemTouchHelper.startDrag(viewHolder);
    }


    private static class RedditFragmentAsyncTask extends AsyncTaskLoader<Cursor> {

        Cursor mRedditData = null;

        RedditFragmentAsyncTask(@NonNull Context context) {
            super(context);
        }

        @Override
        protected void onStartLoading() {
            if (mRedditData != null) {
                deliverResult(mRedditData);
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
                        Contract.PrefSubRedditEntry.COLUMN_NAME_TIME_LAST_MODIFIED
                };



                return getContext().getContentResolver().query(Contract.PrefSubRedditEntry.CONTENT_URI,
                        projection,
                        Contract.PrefSubRedditEntry.COLUMN_NAME_REMOVED + " =?",
                        new String[]{"0"},
                        Contract.PrefSubRedditEntry.COLUMN_NAME_POSITION + " ASC");

            } catch (Exception e) {
                Timber.e("Failed to asynchronously load data. ");
                e.printStackTrace();
                return null;
            }
        }

        @Override
        public void deliverResult(Cursor data) {
            if ((data != null) && (data.getCount() > 0)) {
                mRedditData = data;
                super.deliverResult(data);
            } else {
                forceLoad();
            }
        }
    }


}
