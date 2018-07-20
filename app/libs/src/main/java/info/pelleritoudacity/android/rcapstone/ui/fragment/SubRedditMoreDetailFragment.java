package info.pelleritoudacity.android.rcapstone.ui.fragment;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
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
import info.pelleritoudacity.android.rcapstone.ui.adapter.SubRedditMoreDetailAdapter;
import info.pelleritoudacity.android.rcapstone.utility.Costant;
import info.pelleritoudacity.android.rcapstone.utility.Preference;
import info.pelleritoudacity.android.rcapstone.utility.Utility;
import timber.log.Timber;

import static info.pelleritoudacity.android.rcapstone.utility.Costant.SUBREDDIT_DETAIL_LOADER_ID;
import static info.pelleritoudacity.android.rcapstone.utility.Costant.SUBREDDIT_MORE_DETAIL_LOADER_ID;

public class SubRedditMoreDetailFragment extends Fragment
        implements LoaderManager.LoaderCallbacks<Cursor>, SubRedditMoreDetailAdapter.OnAdapterListener {


    @SuppressWarnings({"WeakerAccess", "CanBeFinal", "unused"})
    @BindView(R.id.rv_fragment_reddit_detail)
    RecyclerView mRecyclerView;

    private Unbinder unbinder;
    private String mStrId;

    private SubRedditMoreDetailAdapter mAdapter;
    private OnFragmentInteractionListener mListener;

    public SubRedditMoreDetailFragment() {
    }

    public static SubRedditMoreDetailFragment newInstance(String strId) {
        SubRedditMoreDetailFragment fragment = new SubRedditMoreDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Costant.EXTRA_FRAGMENT_SUBREDDIT_MORE_DETAIL_STRID, strId);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mStrId = getArguments().getString(Costant.EXTRA_FRAGMENT_SUBREDDIT_MORE_DETAIL_STRID);
        }

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_subreddit_more_detail, container, false);

        unbinder = ButterKnife.bind(this, view);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(Objects.requireNonNull(getActivity()), LinearLayoutManager.VERTICAL);

        dividerItemDecoration.setDrawable(Objects.requireNonNull(getActivity()).getResources().getDrawable(R.drawable.divider_decoration));

        mRecyclerView.addItemDecoration(dividerItemDecoration);

        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mRecyclerView.setLayoutManager(layoutManager);

        mRecyclerView.setHasFixedSize(true);

        mAdapter = new SubRedditMoreDetailAdapter(this);

        mRecyclerView.setAdapter(mAdapter);

        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (getActivity() != null) {
            getActivity().getSupportLoaderManager().initLoader(SUBREDDIT_MORE_DETAIL_LOADER_ID, null, this).forceLoad();

        }
        if (savedInstanceState != null) {
            mStrId = savedInstanceState.getString(Costant.EXTRA_FRAGMENT_SUBREDDIT_MORE_DETAIL_STRID);

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
        outState.putString(Costant.EXTRA_FRAGMENT_SUBREDDIT_MORE_DETAIL_STRID, mStrId);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        return new SubRedditMoreDetailFragment.SubRedditMoreDetailFragmentAsyncTask(Objects.requireNonNull(getActivity()),
                mStrId);
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
        mListener.clickSelector(position, itemCount);
    }

    private static class SubRedditMoreDetailFragmentAsyncTask extends AsyncTaskLoader<Cursor> {

        Cursor cursorData = null;
        private final String mStringId;

        SubRedditMoreDetailFragmentAsyncTask(@NonNull Context context, String strId) {
            super(context);
            mStringId = strId;
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
                Uri uri = Contract.T1MoresDataEntry.CONTENT_URI;

                String selection = Contract.T1MoresDataEntry.COLUMN_NAME_MORE_ID + " =?";

                String[] selectionArgs = new String[]{mStringId};

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

    }

    public interface OnFragmentInteractionListener {
        void clickSelector(int position, int itemCount);
    }

}
