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

import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.PlayerView;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import info.pelleritoudacity.android.rcapstone.R;
import info.pelleritoudacity.android.rcapstone.data.Contract;
import info.pelleritoudacity.android.rcapstone.ui.adapter.SubRedditAdapter;
import info.pelleritoudacity.android.rcapstone.utility.Costants;
import timber.log.Timber;

import static info.pelleritoudacity.android.rcapstone.utility.Costants.SUBREDDIT_LOADER_ID;

public class SubRedditFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>, SubRedditAdapter.MediaExoplayer {

    @SuppressWarnings({"WeakerAccess", "CanBeFinal", "unused"})
    @BindView(R.id.rv_fragment_subreddit)
    RecyclerView mRecyclerView;

    private static Parcelable sListState;
    private static String sSubReddit;

    private LinearLayoutManager mLayoutManager;
    private SubRedditAdapter mAdapter;

    private SimpleExoPlayer mPlayer;
    private PlayerView mPlayerView;

    public SubRedditFragment() {
    }

    public static SubRedditFragment newInstance(String subReddit) {
        SubRedditFragment fragment = new SubRedditFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Costants.EXTRA_FRAGMENT_SUBREDDIT, subReddit);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            sSubReddit = getArguments().getString(Costants.EXTRA_FRAGMENT_SUBREDDIT);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_subreddit, container, false);

        ButterKnife.bind(this, view);

        if ((getActivity() != null) && (getActivity().findViewById(R.id.fragment_subreddit_container) != null)) {
            mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
            mRecyclerView.setLayoutManager(mLayoutManager);
        }

        mRecyclerView.setHasFixedSize(true);

        mAdapter = new SubRedditAdapter(this, getContext());

        mRecyclerView.setAdapter(mAdapter);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (savedInstanceState == null) {
            if (getActivity() != null) {
                getActivity().getSupportLoaderManager().initLoader(SUBREDDIT_LOADER_ID, null, this);
            }
        } else {
            sListState = savedInstanceState.getParcelable(Costants.EXTRA_FRAGMENT_STATE);
        }

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        sListState = mLayoutManager.onSaveInstanceState();
        outState.putParcelable(Costants.EXTRA_FRAGMENT_STATE, sListState);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (sListState != null) {
            mLayoutManager.onRestoreInstanceState(sListState);
        }
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        return new SubRedditFragmentAsyncTask(Objects.requireNonNull(getActivity()));
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
    public void onMediaResources(SimpleExoPlayer player, PlayerView playerView) {
        mPlayer = player;
        mPlayerView = playerView;
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAdapter != null) {
            mAdapter.mediaReleaseResourse(mPlayer, mPlayerView);
        }
    }

    private static class SubRedditFragmentAsyncTask extends AsyncTaskLoader<Cursor> {

        Cursor sSubRedditData = null;

        SubRedditFragmentAsyncTask(@NonNull Context context) {
            super(context);
        }

        @Override
        protected void onStartLoading() {
            if (sSubRedditData != null) {
                deliverResult(sSubRedditData);
            } else {
                forceLoad();
            }
        }

        @Nullable
        @Override
        public Cursor loadInBackground() {
            try {
                Uri uri = Contract.T3dataEntry.CONTENT_URI;
                String selection = Contract.T3dataEntry.COLUMN_NAME_SUBREDDIT + " =?";
                String[] selectionArgs = {sSubReddit};
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
                sSubRedditData = data;
                super.deliverResult(data);
            } else {
                forceLoad();
            }
        }
    }

}
