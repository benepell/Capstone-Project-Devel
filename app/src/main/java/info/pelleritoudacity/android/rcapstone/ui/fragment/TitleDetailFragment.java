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
import info.pelleritoudacity.android.rcapstone.ui.adapter.TitleDetailAdapter;
import info.pelleritoudacity.android.rcapstone.utility.Costant;
import info.pelleritoudacity.android.rcapstone.utility.Preference;
import info.pelleritoudacity.android.rcapstone.utility.Utility;
import timber.log.Timber;

import static info.pelleritoudacity.android.rcapstone.utility.Costant.SUBREDDIT_SELECTED_LOADER_ID;

public class TitleDetailFragment extends Fragment
        implements LoaderManager.LoaderCallbacks<Cursor>, TitleDetailAdapter.OnVoteChange {

    @SuppressWarnings({"WeakerAccess", "CanBeFinal", "unused"})
    @BindView(R.id.rv_fragment_detail_title)
    RecyclerView mRecyclerView;

    private Context mContext;
    private OnFragmentInteractionListener mListener;
    private Unbinder unbinder;

    private String mStrId;

    private TitleDetailAdapter mAdapter;

    public TitleDetailFragment() {
    }

    public static TitleDetailFragment newInstance(String strId) {
        TitleDetailFragment fragment = new TitleDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Costant.EXTRA_FRAGMENT_SUBREDDIT_SELECTED, strId);
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

        mContext = getActivity();

        if (getArguments() != null) {
            mStrId = getArguments().getString(Costant.EXTRA_FRAGMENT_SUBREDDIT_SELECTED);
        }

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_detail_title, container, false);

        unbinder = ButterKnife.bind(this, view);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mRecyclerView.setHasFixedSize(true);

        mAdapter = new TitleDetailAdapter(this, mContext);

        mRecyclerView.setAdapter(mAdapter);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getLoaderManager().initLoader(SUBREDDIT_SELECTED_LOADER_ID, null, this).forceLoad();


        if (savedInstanceState != null) {
            mStrId = savedInstanceState.getString(Costant.EXTRA_FRAGMENT_STRING_ID);
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        getLoaderManager().restartLoader(SUBREDDIT_SELECTED_LOADER_ID, null, this).forceLoad();

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(Costant.EXTRA_FRAGMENT_STRING_ID, mStrId);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        return new SubRedditDetailFragmentAsyncTask(Objects.requireNonNull(getActivity()), mStrId, Preference.isLoginOver18(mContext));
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
    public void selectorChange(int position) {
        if (position != RecyclerView.NO_POSITION) {
            getLoaderManager().restartLoader(SUBREDDIT_SELECTED_LOADER_ID, null, this).forceLoad();
        }
    }

    @Override
    public void snackMsg(int resource) {
        mListener.snackMsg(resource);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    private static class SubRedditDetailFragmentAsyncTask extends AsyncTaskLoader<Cursor> {

        Cursor cursorData = null;
        private final boolean isOver18;
        private final String mStringId;

        SubRedditDetailFragmentAsyncTask(@NonNull Context context, String stringId, boolean isOver18) {
            super(context);
            this.isOver18 = isOver18;
            mStringId = stringId;
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
                String strOver18 = String.valueOf(Utility.boolToInt(isOver18));

                Uri uri = Contract.T3dataEntry.CONTENT_URI;

                String selection = Contract.T3dataEntry.COLUMN_NAME_ID + " =?" + " AND " +
                        Contract.T3dataEntry.COLUMN_NAME_OVER_18 + " <=?";

                String[] selectionArgs = new String[]{mStringId, strOver18};

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
        void snackMsg(int resource);
    }

}
