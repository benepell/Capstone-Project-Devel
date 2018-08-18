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
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.exoplayer2.ext.ima.ImaAdsLoader;
import com.google.android.exoplayer2.util.Util;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import info.pelleritoudacity.android.rcapstone.R;
import info.pelleritoudacity.android.rcapstone.data.db.Contract;
import info.pelleritoudacity.android.rcapstone.data.model.ui.MainModel;
import info.pelleritoudacity.android.rcapstone.media.MediaPlayer;
import info.pelleritoudacity.android.rcapstone.ui.activity.MainActivity;
import info.pelleritoudacity.android.rcapstone.ui.adapter.MainAdapter;
import info.pelleritoudacity.android.rcapstone.ui.view.GridSpacingItemDecoration;
import info.pelleritoudacity.android.rcapstone.utility.Costant;
import info.pelleritoudacity.android.rcapstone.utility.Utility;
import timber.log.Timber;

import static info.pelleritoudacity.android.rcapstone.utility.Costant.SUBREDDIT_LOADER_ID;

public class MainFragment extends Fragment
        implements LoaderManager.LoaderCallbacks<Cursor>, MainAdapter.OnMainClick {

    @SuppressWarnings({"WeakerAccess", "CanBeFinal", "unused"})
    @BindView(R.id.rv_fragment_main)
    RecyclerView mRecyclerView;

    private Context mContext;
    private Unbinder unbinder;

    private MainModel mModel;

    private MediaPlayer mMediaPlayer;
    private MainAdapter mAdapter;
    private OnMainClick mMainListener;

    public MainFragment() {
    }

    public static MainFragment newInstance(MainModel m) {
        MainFragment fragment = new MainFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(Costant.EXTRA_FRAGMENT_PARCEL_MAIN, m);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mMainListener = (OnMainClick) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().getLocalClassName() + "must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = getActivity();

        if (getArguments() != null) {
            mModel = getArguments().getParcelable(Costant.EXTRA_FRAGMENT_PARCEL_MAIN);

        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_main, container, false);

        unbinder = ButterKnife.bind(this, view);

        int spanCount = Utility.calculateNoOfColumns(Objects.requireNonNull(getActivity()));

        if (Utility.isTablet(mContext)  && spanCount > 1 &&
                (Objects.requireNonNull(getActivity()).getClass().getSimpleName().equals(MainActivity.class.getSimpleName()))) {

            mRecyclerView.addItemDecoration(new GridSpacingItemDecoration(spanCount,
                    getResources().getDimensionPixelSize(R.dimen.recycler_view_item_width)));

            GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),
                    spanCount);

            mRecyclerView.setLayoutManager(gridLayoutManager);

        } else {
            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
            mRecyclerView.setLayoutManager(layoutManager);

        }

        mRecyclerView.setHasFixedSize(true);

        ImaAdsLoader mImaAdsLoader = new ImaAdsLoader(mContext, Uri.parse(getString(R.string.ad_tag_url)));
        mModel.setIma(true);

        mAdapter = new MainAdapter(this, mContext, mImaAdsLoader);

        mRecyclerView.setAdapter(mAdapter);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getLoaderManager().initLoader(SUBREDDIT_LOADER_ID, null, this).forceLoad();

        if (savedInstanceState != null) {
            mModel = savedInstanceState.getParcelable(Costant.EXTRA_FRAGMENT_PARCEL_MAIN);
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        if (isStateSaved()) {
            getLoaderManager().restartLoader(SUBREDDIT_LOADER_ID, null, this).forceLoad();
        }
        if (mMediaPlayer != null) {
            mMediaPlayer.setResume(mMediaPlayer.getResumeWindow(), mMediaPlayer.getResumePosition(), mMediaPlayer.getVideoUri());
        }

    }

    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT <= 23) {
            if (mMediaPlayer != null) {
                mMediaPlayer.releasePlayer();
                mMediaPlayer.updateResumePosition();
                mMediaPlayer.pausePlayer();

            }

        }

    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23) {
            if (mMediaPlayer != null) {
                mMediaPlayer.releasePlayer();
            }

        }

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        if (mMediaPlayer != null) {
            mModel.setWindowPlayer(mMediaPlayer.getResumeWindow());
            mModel.setPositionPlayer(mMediaPlayer.getResumePosition());
            mModel.setAutoplay(false);
        }

        outState.putParcelable(Costant.EXTRA_FRAGMENT_PARCEL_MAIN, mModel);
        super.onSaveInstanceState(outState);

    }

    @Override
    public void selectorChange(int position) {
        if (position != RecyclerView.NO_POSITION) {
            getLoaderManager().restartLoader(SUBREDDIT_LOADER_ID, null, this).forceLoad();
        }
    }

    @Override
    public void mainClick(int position, String category, String strId) {
        mMainListener.mainClick(position, category, strId);
    }

    @Override
    public void mediaPlayer(MediaPlayer mediaPlayer) {
        mMediaPlayer = mediaPlayer;
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        return new MainFragmentAsyncTask(Objects.requireNonNull(getActivity()), mModel);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        if ((data != null) && (mAdapter != null)) {
            mAdapter.swapCursor(data);

            if ((mRecyclerView != null) && (mModel.getPosition() > 0)) {

                mRecyclerView.scrollToPosition(mModel.getPosition());
            }
            mMainListener.mainFragmentResult(data.getCount());
        }

    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        mAdapter.swapCursor(null);

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mMainListener = null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private static class MainFragmentAsyncTask extends AsyncTaskLoader<Cursor> {

        Cursor cursorData = null;
        private final MainModel model;

        MainFragmentAsyncTask(@NonNull Context context, MainModel model) {
            super(context);
            this.model = model;
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
                String selection;
                String[] selectionArgs;

                String strOver18 = String.valueOf(Utility.boolToInt(model.isOver18()));

                switch (model.getTarget()) {
                    case Costant.ALL_MAIN_TARGET:
                        selection = Contract.T3dataEntry.COLUMN_NAME_TARGET + " =?" + " AND " +
                                Contract.T3dataEntry.COLUMN_NAME_OVER_18 + " <=?";
                        selectionArgs = new String[]{model.getTarget(), strOver18};
                        break;

                    case Costant.POPULAR_MAIN_TARGET:
                        selection = Contract.T3dataEntry.COLUMN_NAME_TARGET + " =?" + " AND " +
                                Contract.T3dataEntry.COLUMN_NAME_OVER_18 + " <=?";
                        selectionArgs = new String[]{model.getTarget(), strOver18};
                        break;

                    case Costant.FAVORITE_MAIN_TARGET:

                        selection = Contract.T3dataEntry.COLUMN_NAME_SAVED + " =?";
                        selectionArgs = new String[]{Costant.SUBREDDIT_FAVORITE_SAVED};
                        break;

                    case Costant.SEARCH_MAIN_TARGET:
                        selection = Contract.T3dataEntry.COLUMN_NAME_TITLE + " like ?" + " AND " +
                                Contract.T3dataEntry.COLUMN_NAME_SUBREDDIT + " LIKE ?" + " AND " +
                                Contract.T3dataEntry.COLUMN_NAME_OVER_18 + " <=?";
                        selectionArgs = new String[]{"%" + model.getQuerySearch() + "%", model.getCategory(), strOver18,};
                        break;

                    case Costant.WIDGET_MAIN_TARGET:
                    case Costant.NAVIGATION_MAIN_TARGET:
                    default:
                        selection = Contract.T3dataEntry.COLUMN_NAME_SUBREDDIT + " LIKE ?" + " AND " +
                                Contract.T3dataEntry.COLUMN_NAME_OVER_18 + " <=?";
                        selectionArgs = new String[]{model.getCategory(), strOver18};

                }

                return getContext().getContentResolver().query(uri,
                        null,
                        selection,
                        selectionArgs,
                        null);

            } catch (Exception e) {
                Timber.e("Failed to asynchronously load data. main fragment %s ", e.getMessage());
                return null;
            }
        }

        @Override
        public void deliverResult(Cursor data) {
            cursorData = data;
            super.deliverResult(data);
        }
    }

    public interface OnMainClick {
        void mainClick(int position, String category, String strId);

        void mainFragmentResult(int count);
    }
}
