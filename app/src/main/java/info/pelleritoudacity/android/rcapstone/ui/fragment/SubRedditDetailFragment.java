package info.pelleritoudacity.android.rcapstone.ui.fragment;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
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
import info.pelleritoudacity.android.rcapstone.utility.Preference;
import info.pelleritoudacity.android.rcapstone.utility.Utility;
import timber.log.Timber;

import static info.pelleritoudacity.android.rcapstone.utility.Costant.SUBREDDIT_DETAIL_LOADER_ID;
import static info.pelleritoudacity.android.rcapstone.utility.Costant.SUBREDDIT_MORE_DETAIL_LOADER_ID;

public class SubRedditDetailFragment extends Fragment
        implements LoaderManager.LoaderCallbacks<Cursor>, SubRedditDetailAdapter.OnAdapterListener {

    @SuppressWarnings({"WeakerAccess", "CanBeFinal", "unused"})
    @BindView(R.id.rv_fragment_reddit_detail)
    RecyclerView mRecyclerView;

    private Unbinder unbinder;
    private String mStrId;
    private String mStrLinkId;
    private SubRedditDetailAdapter mAdapter;
    private OnFragmentInteractionListener mListener;

    public SubRedditDetailFragment() {
    }

    public static SubRedditDetailFragment newInstance(String strId, String strLinkId) {
        SubRedditDetailFragment fragment = new SubRedditDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Costant.EXTRA_FRAGMENT_SUBREDDIT_DETAIL_STRID, strId);
        bundle.putString(Costant.EXTRA_FRAGMENT_SUBREDDIT_DETAIL_LINKID, strLinkId);

        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mStrId = getArguments().getString(Costant.EXTRA_FRAGMENT_SUBREDDIT_DETAIL_STRID);
            mStrLinkId = getArguments().getString(Costant.EXTRA_FRAGMENT_SUBREDDIT_DETAIL_LINKID);
        }

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_subreddit_detail, container, false);

        unbinder = ButterKnife.bind(this, view);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(Objects.requireNonNull(getActivity()), LinearLayoutManager.VERTICAL);

        dividerItemDecoration.setDrawable(Objects.requireNonNull(getActivity()).getResources().getDrawable(R.drawable.divider_decoration));

        mRecyclerView.addItemDecoration(dividerItemDecoration);

        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mRecyclerView.setLayoutManager(layoutManager);

        mRecyclerView.setHasFixedSize(true);

        mAdapter = new SubRedditDetailAdapter(this);

        mRecyclerView.setAdapter(mAdapter);

        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (getActivity() != null) {
            if (TextUtils.isEmpty(mStrLinkId)) {
                getActivity().getSupportLoaderManager().initLoader(SUBREDDIT_DETAIL_LOADER_ID, null, this).forceLoad();

            } else {
                getActivity().getSupportLoaderManager().initLoader(SUBREDDIT_MORE_DETAIL_LOADER_ID, null, this).forceLoad();

            }

        }

        if (savedInstanceState != null) {
            mStrId = savedInstanceState.getString(Costant.EXTRA_FRAGMENT_DETAIL_STRING_ID);

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
        outState.putString(Costant.EXTRA_FRAGMENT_DETAIL_STRING_ID, mStrId);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        return new SubRedditDetailFragmentAsyncTask(Objects.requireNonNull(getActivity()), mStrId, mStrLinkId, Preference.isLoginOver18(getContext()));
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

    @Override
    public void moreComments(String category, String strId, String linkId, String strArrId) {
        mListener.moreComments(category, strId, linkId, strArrId);

        SubRedditDetailFragment fragment = SubRedditDetailFragment.newInstance(strId, linkId);
        mListener.childMoreFragment( getChildFragmentManager().beginTransaction()
                .replace(R.id.fragment_subreddit_more_container, fragment));
        Preference.setMoreFragmentTransaction(getActivity().getApplicationContext(),true);

    }


    private static class SubRedditDetailFragmentAsyncTask extends AsyncTaskLoader<Cursor> {

        Cursor cursorData = null;
        private final boolean isOver18;
        private final String mStringId;
        private final String mStrLinkId;

        SubRedditDetailFragmentAsyncTask(@NonNull Context context, String strId, String strLinkId, boolean isOver18) {
            super(context);
            this.isOver18 = isOver18;
            mStringId = strId;
            mStrLinkId = strLinkId;
        }

        @Override
        protected void onStartLoading() {
            if (cursorData != null) {
                deliverResult(cursorData);
            } else {
                forceLoad();
            }
        }

        @Override
        protected void onStopLoading() {
            super.onStopLoading();
            cancelLoad();
        }

        @Nullable
        @Override
        public Cursor loadInBackground() {
            try {


                Uri uri;

                String strOver18 = String.valueOf(Utility.boolToInt(isOver18));

                String selection = Contract.T1dataEntry.COLUMN_NAME_LINK_ID + " =?" + " AND " +
                        Contract.T1dataEntry.COLUMN_NAME_OVER18 + " <=?";

                String[] selectionArgs;

                if (TextUtils.isEmpty(mStrLinkId)) {
                    uri = Contract.T1dataEntry.CONTENT_URI;
                    selectionArgs = new String[]{Costant.STR_PARENT_LINK + mStringId, strOver18};

                } else {
                    uri = Contract.T1MdataEntry.CONTENT_URI;
                    selectionArgs = new String[]{mStrLinkId, strOver18};

                }

                String sortOrder = null;
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
        void moreComments(String category, String strId, String linkId, String strArrId);
        void childMoreFragment(FragmentTransaction child);
    }

}
