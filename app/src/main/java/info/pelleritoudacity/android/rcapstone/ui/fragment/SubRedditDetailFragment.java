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
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
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
import info.pelleritoudacity.android.rcapstone.data.db.Contract;
import info.pelleritoudacity.android.rcapstone.ui.adapter.SubRedditDetailAdapter;
import info.pelleritoudacity.android.rcapstone.utility.Costant;
import timber.log.Timber;

import static info.pelleritoudacity.android.rcapstone.utility.Costant.SUBREDDIT_DETAIL_LOADER_ID;

public class SubRedditDetailFragment extends Fragment
        implements LoaderManager.LoaderCallbacks<Cursor>, SubRedditDetailAdapter.OnAdapterListener {

    @SuppressWarnings({"WeakerAccess", "CanBeFinal", "unused"})
    @BindView(R.id.rv_fragment_reddit_detail)
    RecyclerView mRecyclerView;

    private Context mContext;
    private Unbinder unbinder;

    private static String sStrId = null;
    private Parcelable mState;

    private LinearLayoutManager mLayoutManager;
    private SubRedditDetailAdapter mAdapter;
    private OnFragmentInteractionListener mListener;

    public SubRedditDetailFragment() {
    }

    public static SubRedditDetailFragment newInstance(String strId) {
        SubRedditDetailFragment fragment = new SubRedditDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Costant.EXTRA_FRAGMENT_SUBREDDIT_DETAIL, strId);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = getActivity();

        if (getArguments() != null) {
            sStrId = getArguments().getString(Costant.EXTRA_FRAGMENT_SUBREDDIT_DETAIL);
        }

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_subreddit_detail, container, false);

        unbinder = ButterKnife.bind(this, view);

        mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);

        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));

        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mRecyclerView.setLayoutManager(mLayoutManager);

        mRecyclerView.setHasFixedSize(true);


        mAdapter = new SubRedditDetailAdapter(this);

        mRecyclerView.setAdapter(mAdapter);

        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (getActivity() != null) {
            getActivity().getSupportLoaderManager().initLoader(SUBREDDIT_DETAIL_LOADER_ID, null, this).forceLoad();

        }
        if (savedInstanceState != null) {
            mState = savedInstanceState.getParcelable(Costant.EXTRA_FRAGMENT_DETAIL_STATE);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (OnFragmentInteractionListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().getLocalClassName() + "must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        mState = mLayoutManager.onSaveInstanceState();
        outState.putParcelable(Costant.EXTRA_FRAGMENT_DETAIL_STATE, mState);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mState != null) {
            mLayoutManager.onRestoreInstanceState(mState);
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

    @Override
    public void adapterPosition(int position, String category) {

    }

    @Override
    public void clickSelector(int position, int itemCount) {
        mListener.clickSelector(position,itemCount);
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
                Uri uri = Contract.T1dataEntry.CONTENT_URI;

                String selection = Contract.T1dataEntry.COLUMN_NAME_LINK_ID + " =?";
                String[] selectionArgs = new String[]{ Costant.STR_PARENT_COMMENT + sStrId};
                String sortOrder = Contract.T1dataEntry.COLUMN_NAME_CHILDREN_ID + " ASC," +
                        Contract.T1dataEntry.COLUMN_NAME_DEPTH + " ASC";

                return getContext().getContentResolver().query(uri,
                        null,
                        selection,
                        selectionArgs,
                        sortOrder);

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

    public interface OnFragmentInteractionListener {
        void clickSelector(int position, int itemCount);
    }

}
