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
import info.pelleritoudacity.android.rcapstone.data.db.util.DataUtils;
import info.pelleritoudacity.android.rcapstone.data.model.ui.DetailModel;
import info.pelleritoudacity.android.rcapstone.ui.adapter.DetailAdapter;
import info.pelleritoudacity.android.rcapstone.utility.Costant;
import info.pelleritoudacity.android.rcapstone.utility.Preference;
import info.pelleritoudacity.android.rcapstone.utility.Utility;
import timber.log.Timber;

import static info.pelleritoudacity.android.rcapstone.utility.Costant.SUBREDDIT_DETAIL_LOADER_ID;

public class DetailFragment extends Fragment
        implements LoaderManager.LoaderCallbacks<Cursor>, DetailAdapter.OnAdapterListener {

    @SuppressWarnings({"WeakerAccess", "CanBeFinal", "unused"})
    @BindView(R.id.rv_fragment_detail)
    RecyclerView mRecyclerView;

    private Unbinder unbinder;
    private DetailAdapter mAdapter;
    private OnFragmentInteractionListener mListener;
    private DetailModel model;

    public DetailFragment() {
    }

    public static DetailFragment newInstance(DetailModel m) {
        DetailFragment fragment = new DetailFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(Costant.EXTRA_FRAGMENT_PARCEL_SUBREDDIT_DETAIL, m);
        fragment.setArguments(bundle);
        return fragment;
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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            model = getArguments().getParcelable(Costant.EXTRA_FRAGMENT_PARCEL_SUBREDDIT_DETAIL);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_detail, container, false);

        unbinder = ButterKnife.bind(this, view);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(Objects.requireNonNull(getActivity()), LinearLayoutManager.VERTICAL);

        dividerItemDecoration.setDrawable(Objects.requireNonNull(getActivity()).getResources().getDrawable(R.drawable.divider_decoration));

        mRecyclerView.addItemDecoration(dividerItemDecoration);

        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mRecyclerView.setLayoutManager(layoutManager);

        mRecyclerView.setHasFixedSize(true);

        mAdapter = new DetailAdapter(this, getActivity(), model);

        mRecyclerView.setAdapter(mAdapter);

        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (savedInstanceState != null) {
            model = savedInstanceState.getParcelable(Costant.EXTRA_FRAGMENT_PARCEL_SUBREDDIT_DETAIL);

        }

        getLoaderManager().initLoader(SUBREDDIT_DETAIL_LOADER_ID, null, this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    @Override
    public void onResume() {
        super.onResume();
        if (!isStateSaved()) {
            getLoaderManager().restartLoader(SUBREDDIT_DETAIL_LOADER_ID, null, this);
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putParcelable(Costant.EXTRA_FRAGMENT_PARCEL_SUBREDDIT_DETAIL, model);
        super.onSaveInstanceState(outState);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        return new SubRedditDetailFragmentAsyncTask(Objects.requireNonNull(getActivity()), model,
                Preference.isLoginOver18(getContext()));
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        if ((data != null) && (mAdapter != null)) {
            mAdapter.swapCursor(data);

            mListener.detailFragmentResult(data.getCount());
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        if (mAdapter != null) {
            mAdapter.swapCursor(null);
        }
    }

    @Override
    public void clickSelector(int position, int itemCount) {
        mListener.clickSelector(position, itemCount);
    }

    @Override
    public void onClickMore(DetailModel detailModel) {
        mListener.onClickMore(detailModel);
    }

    @Override
    public void snackMsg(int resource) {
       mListener.snackMsg(resource);
    }

    @Override
    public void selectorChange(int position) {
        if (position != RecyclerView.NO_POSITION) {
            getLoaderManager().initLoader(SUBREDDIT_DETAIL_LOADER_ID, null, this);
        }
    }

    private static class SubRedditDetailFragmentAsyncTask extends AsyncTaskLoader<Cursor> {

        Cursor cursorData = null;
        private final boolean isOver18;
        private final DetailModel m;


        SubRedditDetailFragmentAsyncTask(Context context, DetailModel model, boolean isOver18) {
            super(context);
            this.isOver18 = isOver18;
            m = model;
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

                String strOver18 = String.valueOf(Utility.boolToInt(isOver18));

                String selection = Contract.T1dataEntry.COLUMN_NAME_LINK_ID + " =?" + " AND " +
                        Contract.T1dataEntry.COLUMN_NAME_MORE_REPLIES + " =?" + " AND " +
                        Contract.T1dataEntry.COLUMN_NAME_OVER18 + " <=?";

                Uri uri = Contract.T1dataEntry.CONTENT_URI;
                String[] selectionArgs = null;
                DataUtils dataUtils = new DataUtils(getContext());

                switch (m.getTarget()) {

                    case Costant.DETAIL_TARGET:
                        selectionArgs = new String[]{Costant.STR_PARENT_LINK + m.getStrId(), Costant.NONE_DETAIL_MORE_REPLIES, strOver18};

                        break;

                    case Costant.MORE_DETAIL_TARGET:

                        selection = Contract.T1dataEntry._ID + " =?" + " OR " +
                                Contract.T1dataEntry.COLUMN_NAME_ID + " IN(" + dataUtils.stringInQuestionMark(m.getStrArrId()) + ")";

                        selectionArgs = ((m.getId() + ",") + (m.getStrArrId()))
                                .split(Costant.STRING_SEPARATOR);


                        break;
                    case Costant.SEARCH_DETAIL_TARGET:
                        selection = Contract.T1dataEntry.COLUMN_NAME_BODY + " LIKE ?" + " AND " +
                                Contract.T1dataEntry.COLUMN_NAME_LINK_ID + " =?" + " AND " +
                                Contract.T1dataEntry.COLUMN_NAME_MORE_REPLIES + " =?" + " AND " +
                                Contract.T1dataEntry.COLUMN_NAME_OVER18 + " <=?";

                        selectionArgs = new String[]{"%" + m.getStrQuerySearch() + "%", Costant.STR_PARENT_LINK + m.getStrId(), Costant.NONE_DETAIL_MORE_REPLIES, strOver18};

                        break;

                    case Costant.MORE_SEARCH_DETAIL_TARGET:

                        selection = Contract.T1dataEntry.COLUMN_NAME_BODY + " LIKE ?" + " AND (" +
                                Contract.T1dataEntry._ID + " =?" + " OR " +
                                Contract.T1dataEntry.COLUMN_NAME_ID + " IN(" + dataUtils.stringInQuestionMark(m.getStrArrId()) + "))";

                        selectionArgs = ("%" + m.getStrQuerySearch() + "%" + "," + m.getId() + "," + (m.getStrArrId()))
                                .split(Costant.STRING_SEPARATOR);

                        break;

                }

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
            cursorData = data;
            super.deliverResult(data);
        }

    }

    public interface OnFragmentInteractionListener {
        void clickSelector(int position, int itemCount);

        void onClickMore(DetailModel detailModel);

        void snackMsg(int resource);
        void detailFragmentResult(int count);
    }

}
