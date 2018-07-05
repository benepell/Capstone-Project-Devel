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
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.ext.ima.ImaAdsLoader;
import com.google.android.exoplayer2.util.Util;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import info.pelleritoudacity.android.rcapstone.R;
import info.pelleritoudacity.android.rcapstone.data.db.Contract;
import info.pelleritoudacity.android.rcapstone.media.MediaPlayer;
import info.pelleritoudacity.android.rcapstone.ui.adapter.SubRedditAdapter;
import info.pelleritoudacity.android.rcapstone.utility.Costant;
import info.pelleritoudacity.android.rcapstone.utility.Preference;
import info.pelleritoudacity.android.rcapstone.utility.Utility;
import timber.log.Timber;

import static info.pelleritoudacity.android.rcapstone.utility.Costant.SUBREDDIT_LOADER_ID;

public class SubRedditFragment extends Fragment
        implements LoaderManager.LoaderCallbacks<Cursor>, SubRedditAdapter.OnPlayerListener, SubRedditAdapter.OnAdapterListener {

    @SuppressWarnings({"WeakerAccess", "CanBeFinal", "unused"})
    @BindView(R.id.rv_fragment_subreddit)
    RecyclerView mRecyclerView;

    private Context mContext;
    private Unbinder unbinder;

//    private Parcelable mListState;
    private int sWindowPlayer;
    private boolean sIsAutoRun;
    private long sPositionPlayer;
    private boolean isIMA = false;

    private String mSubReddit;
    private String mTarget;
    private MediaPlayer mMediaPlayer;

    private LinearLayoutManager mLayoutManager;
    private SubRedditAdapter mAdapter;


    public SubRedditFragment() {
    }

    public static SubRedditFragment newInstance(String subReddit, String target) {
        SubRedditFragment fragment = new SubRedditFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Costant.EXTRA_FRAGMENT_SUBREDDIT, subReddit);
        bundle.putString(Costant.EXTRA_FRAGMENT_TARGET, target);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = getActivity();

        if (getArguments() != null) {
            mSubReddit = getArguments().getString(Costant.EXTRA_FRAGMENT_SUBREDDIT);
            mTarget = getArguments().getString(Costant.EXTRA_FRAGMENT_TARGET);

        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_subreddit, container, false);

        unbinder = ButterKnife.bind(this, view);

        mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mRecyclerView.setHasFixedSize(true);

        ImaAdsLoader mImaAdsLoader = new ImaAdsLoader(mContext, Uri.parse(getString(R.string.ad_tag_url)));
        isIMA = true;

        mAdapter = new SubRedditAdapter(this, mContext, mImaAdsLoader);

        mRecyclerView.setAdapter(mAdapter);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (getActivity() != null) {
            getActivity().getSupportLoaderManager().initLoader(SUBREDDIT_LOADER_ID, null, this).forceLoad();

        }
        if (savedInstanceState != null) {
//            mListState = savedInstanceState.getParcelable(Costant.EXTRA_FRAGMENT_STATE);

            sWindowPlayer = savedInstanceState.getInt(Costant.BUNDLE_EXOPLAYER_WINDOW, C.INDEX_UNSET);
            sPositionPlayer = savedInstanceState.getLong(Costant.BUNDLE_EXOPLAYER_POSITION, C.TIME_UNSET);
            sIsAutoRun = savedInstanceState.getBoolean(Costant.BUNDLE_EXOPLAYER_AUTOPLAY, false);

            Timber.d("STATEX before %s",mSubReddit);

            mSubReddit = savedInstanceState.getString(Costant.EXTRA_SUBREDDIT_CATEGORY);
            mTarget = savedInstanceState.getString(Costant.EXTRA_SUBREDDIT_TARGET);
            Timber.d("STATEX after %s",mSubReddit);

            isIMA = savedInstanceState.getBoolean(Costant.EXTRA_MEDIA_IMA);
        }


    }


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
//        mListState = mLayoutManager.onSaveInstanceState();
//        outState.putParcelable(Costant.EXTRA_FRAGMENT_STATE, mListState);
        outState.putString(Costant.EXTRA_SUBREDDIT_CATEGORY,mSubReddit);
        outState.putString(Costant.EXTRA_SUBREDDIT_TARGET,mTarget);
        outState.putBoolean(Costant.EXTRA_MEDIA_IMA,isIMA);
        if (mMediaPlayer != null) {
            outState.putInt(Costant.BUNDLE_EXOPLAYER_WINDOW, mMediaPlayer.getResumeWindow());
            outState.putLong(Costant.BUNDLE_EXOPLAYER_POSITION, mMediaPlayer.getResumePosition());
            outState.putBoolean(Costant.BUNDLE_EXOPLAYER_AUTOPLAY, mMediaPlayer.isAutoPlay());
        }

    }

    @Override
    public void onPause() {
        super.onPause();
        if (mMediaPlayer != null) {
            if (Util.SDK_INT <= 23) {
                mMediaPlayer.releasePlayer();
            }
            mMediaPlayer.updateResumePosition();
            mMediaPlayer.pausePlayer();
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
    public void onResume() {
        super.onResume();
    }


    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        return new SubRedditFragmentAsyncTask(Objects.requireNonNull(getActivity()),
                Preference.isLoginOver18(getContext()),mSubReddit,mTarget);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        if ((data != null) && (mAdapter != null)) {
            mAdapter.swapCursor(data);
        }
    }

   /* @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            getFragmentManager().beginTransaction().detach(this).attach(this).commit();
        }
    }
*/
    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        mAdapter.swapCursor(null);
    }

    @Override
    public void exoPlayer(MediaPlayer mediaPlayer) {
        mMediaPlayer = mediaPlayer;
        if ((mMediaPlayer != null) && !isIMA) {
            mMediaPlayer.setResume(sWindowPlayer, sPositionPlayer, mMediaPlayer.getVideoUri());

        }
    }

    @Override
    public void adapterPosition(int position, String category) {
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private static class SubRedditFragmentAsyncTask extends AsyncTaskLoader<Cursor> {

        Cursor cursorData = null;
        private final boolean isOver18;
        private final String mCategoryReddit;
        private final String mTargetReddit;


        SubRedditFragmentAsyncTask(@NonNull Context context, boolean isOver18,String categoryReddit, String targetReddit) {
            super(context);
            this.isOver18 = isOver18;
            mCategoryReddit = categoryReddit;
            mTargetReddit = targetReddit;
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
                String selection = null;
                String[] selectionArgs = new String[0];

                String strOver18 = String.valueOf(Utility.boolToInt(isOver18));

                if (!TextUtils.isEmpty(mTargetReddit)) {
                    if (mTargetReddit.equals(Costant.SUBREDDIT_TARGET_ALL)) {
                        selection = Contract.T3dataEntry.COLUMN_NAME_TARGET + " =?" + " AND " +
                                Contract.T3dataEntry.COLUMN_NAME_OVER_18 + " <=?";
                        selectionArgs = new String[]{mTargetReddit, strOver18};

                    } else if (mTargetReddit.equals(Costant.SUBREDDIT_TARGET_POPULAR)) {
                        selection = Contract.T3dataEntry.COLUMN_NAME_TARGET + " =?" + " AND " +
                                Contract.T3dataEntry.COLUMN_NAME_OVER_18 + " <=?";
                        selectionArgs = new String[]{mTargetReddit, strOver18};

                    }

                } else {
                    selection = Contract.T3dataEntry.COLUMN_NAME_SUBREDDIT + " LIKE ?" + " AND " +
                            Contract.T3dataEntry.COLUMN_NAME_OVER_18 + " <=?";
                    selectionArgs = new String[]{mCategoryReddit, strOver18};


                    Timber.d("STROVER18 %s", strOver18);
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
            if ((data != null) && (data.getCount() > 0)) {
                cursorData = data;
                super.deliverResult(data);
            } else {
                forceLoad();
            }
        }
    }

}
