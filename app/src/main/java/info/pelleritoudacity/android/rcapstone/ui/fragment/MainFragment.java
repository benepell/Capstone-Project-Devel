package info.pelleritoudacity.android.rcapstone.ui.fragment;


import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import info.pelleritoudacity.android.rcapstone.BuildConfig;
import info.pelleritoudacity.android.rcapstone.R;
import info.pelleritoudacity.android.rcapstone.data.db.AppDatabase;
import info.pelleritoudacity.android.rcapstone.data.db.entry.T3Entry;
import info.pelleritoudacity.android.rcapstone.data.db.viewmodel.T3TargetViewModel;
import info.pelleritoudacity.android.rcapstone.data.db.viewmodel.T3TargetViewModelFactory;
import info.pelleritoudacity.android.rcapstone.data.model.ui.MainModel;
import info.pelleritoudacity.android.rcapstone.media.MediaPlayer;
import info.pelleritoudacity.android.rcapstone.ui.activity.MainActivity;
import info.pelleritoudacity.android.rcapstone.ui.adapter.MainAdapter;
import info.pelleritoudacity.android.rcapstone.ui.view.GridSpacingItemDecoration;
import info.pelleritoudacity.android.rcapstone.utility.Costant;
import info.pelleritoudacity.android.rcapstone.utility.Utility;


public class MainFragment extends Fragment
        implements MainAdapter.OnMainClick {
    @SuppressWarnings({"WeakerAccess", "CanBeFinal", "unused"})
    @BindView(R.id.rv_fragment_main)
    RecyclerView mRecyclerView;

    private Context mContext;
    private Unbinder unbinder;

    private MainModel mModel;

    private MediaPlayer mMediaPlayer;
    private MainAdapter mAdapter;
    private OnMainClick mMainListener;
    private AppDatabase mDb;

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

        mDb = AppDatabase.getInstance(getActivity());
        setupViewModel();

    }

    @SuppressWarnings("ConstantConditions")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_main, container, false);

        unbinder = ButterKnife.bind(this, view);

        int spanCount = Utility.calculateNoOfColumns(Objects.requireNonNull(getActivity()));

        if (Utility.isTablet(mContext) &&
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


        if (BuildConfig.FLAVOR.equals("free")) {
            ImaAdsLoader mImaAdsLoader = new ImaAdsLoader(mContext, Uri.parse(getString(R.string.ad_tag_url)));
            mModel.setIma(true);

            mAdapter = new MainAdapter(this, mDb, mContext, mImaAdsLoader);

        } else {
            mAdapter = new MainAdapter(this, mDb, mContext, null);

        }

        mRecyclerView.setAdapter(mAdapter);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (savedInstanceState != null) {
            mModel = savedInstanceState.getParcelable(Costant.EXTRA_FRAGMENT_PARCEL_MAIN);
        }

    }

    private void setupViewModel() {
        T3Entry t3Entry = new T3Entry();

        switch (mModel.getTarget()) {
            case Costant.ALL_MAIN_TARGET:
            case Costant.POPULAR_MAIN_TARGET:

                t3Entry.setTarget(mModel.getTarget());
                t3Entry.setOver18(Utility.boolToInt(mModel.isOver18()));
                break;

            case Costant.FAVORITE_MAIN_TARGET:
                t3Entry.setSaved(Costant.SUBREDDIT_FAVORITE_SAVED);
                break;

            case Costant.SEARCH_MAIN_TARGET:
                t3Entry.setTitle("%" + mModel.getQuerySearch() + "%");
                t3Entry.setSubreddit(mModel.getCategory());
                t3Entry.setOver18(Utility.boolToInt(mModel.isOver18()));

                break;

            case Costant.WIDGET_MAIN_TARGET:
            case Costant.NAVIGATION_MAIN_TARGET:
            default:
                t3Entry.setSubreddit(mModel.getCategory());
                t3Entry.setOver18(Utility.boolToInt(mModel.isOver18()));

        }

        T3TargetViewModelFactory factory = new T3TargetViewModelFactory(mDb, t3Entry, mModel.getTarget());
        final T3TargetViewModel viewModel = ViewModelProviders.of(this, factory).get(T3TargetViewModel.class);

        viewModel.getTask().observe(this, t3Entries -> {
                    if ((t3Entries != null) && (mAdapter != null)) {

                        mAdapter.setEntry(t3Entries);

                        if ((mRecyclerView != null) && (mModel.getPosition() > 0)) {

                            mRecyclerView.scrollToPosition(mModel.getPosition());
                        }
                        mMainListener.mainFragmentResult(t3Entries.size());
                    }
                }
        );


    }

    @Override
    public void onResume() {
        super.onResume();
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
    }

    @Override
    public void mainClick(int position, String category, String strId) {
        mMainListener.mainClick(position, category, strId);
    }

    @Override
    public void mediaPlayer(MediaPlayer mediaPlayer) {
        mMediaPlayer = mediaPlayer;
    }

    @Override
    public void snackMsg(int resource) {
        mMainListener.snackMsg(resource);
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

    public interface OnMainClick {
        void mainClick(int position, String category, String strId);

        void snackMsg(int resource);

        void mainFragmentResult(int count);
    }
}
