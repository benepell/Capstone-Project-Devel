/*
 *
 * ______                _____ _
 * | ___ \              /  ___| |
 * | |_/ /___ __ _ _ __ \ `--.| |_ ___  _ __   ___
 * |    // __/ _` | '_ \ `--. \ __/ _ \| '_ \ / _ \
 * | |\ \ (_| (_| | |_) /\__/ / || (_) | | | |  __/
 * \_| \_\___\__,_| .__/\____/ \__\___/|_| |_|\___|
 *                | |
 *                |_|
 *
 * Copyright (C) 2018 Benedetto Pellerito
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package info.pelleritoudacity.android.rcapstone.media;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ext.ima.ImaAdsLoader;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ads.AdsMediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.material_design_iconic_typeface_library.MaterialDesignIconic;


import java.util.concurrent.TimeUnit;

import info.pelleritoudacity.android.rcapstone.R;
import info.pelleritoudacity.android.rcapstone.data.model.MediaModel;
import info.pelleritoudacity.android.rcapstone.ui.activity.FullScreenActivity;
import info.pelleritoudacity.android.rcapstone.utility.Costant;
import info.pelleritoudacity.android.rcapstone.utility.DateUtil;
import info.pelleritoudacity.android.rcapstone.utility.ImageUtil;
import info.pelleritoudacity.android.rcapstone.utility.Preference;
import info.pelleritoudacity.android.rcapstone.utility.TextUtil;

public class MediaPlayer {


    private final Context mContext;

    private int mResumeWindow;
    private long mResumePosition;

    private Uri mVideoUri = null;

    private final PlayerView mPlayerView;
    private SimpleExoPlayer mPlayer;
    private final ImaAdsLoader mImaAdsLoader;

    private final ProgressBar mProgressBar;
    private final TextView mTvErrorPlayer;
    private TextView mTvExoCountDown;
    private ImageView mImageMutedPlay;
    private final ImageView mImagePlay;

    private Handler mHandler;
    private final Runnable mRunnableRemainingPlay = this::countDown;


    public MediaPlayer(Context context, MediaModel mediaModel) {

        mContext = context;
        mPlayerView = mediaModel.getPlayerView();
        mProgressBar = mediaModel.getProgressBar();
        mTvErrorPlayer = mediaModel.getTvErrorPlayer();
        mImaAdsLoader = mediaModel.getImaAdsLoader();
        mImagePlay = mediaModel.getImagePlay();

    }

    public void initPlayerFullScreen(Uri mediaUri) {

        mPlayer = createPlayer();

        mPlayerView.setPlayer(mPlayer);

        DefaultDataSourceFactory dataSourceFactory = new MediaSRC(mContext)
                .createDataSourceFactory(true);

        MediaSource mediaSource = new ExtractorMediaSource.Factory(dataSourceFactory)
                .createMediaSource(mediaUri);

        boolean isResume = mResumePosition > 0;

        if ((Costant.IS_IMA_AD_EXTENSION) && (mImaAdsLoader != null)) {
            mediaSource = new AdsMediaSource(mediaSource, dataSourceFactory, mImaAdsLoader,
                    mPlayerView.getOverlayFrameLayout());

        }

        if (isResume) {
            if (mediaUri.equals(mVideoUri)) {
                mPlayer.seekTo(mResumeWindow, mResumePosition);
            }
        } else {
            mPlayer.seekToDefaultPosition();
        }

        if (Costant.IS_CONTROLLER_ALWAYS) {
            mPlayerView.setControllerShowTimeoutMs(Costant.PLAYER_SHOW_CONTROLLER_ALWAYS);
        }

        mPlayerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FILL);

        mPlayer.prepare(mediaSource, !isResume, false);

        mPlayer.setPlayWhenReady(Costant.IS_AUTOPLAY_VIDEO);

        getExitFullScreen();
        getShareFullScreen(mediaUri.toString());

        mPlayer.addListener(new MediaEvent(this,
                null, null,
                mTvErrorPlayer));

        mVideoUri = mediaUri;

    }


    public void initPlayer(Uri mediaUri) {

        if (mPlayerView != null) {

            mTvExoCountDown = mPlayerView.findViewById(R.id.exo_countdown);
            mTvExoCountDown.setVisibility(View.VISIBLE);

            mHandler = new Handler();


            mPlayer = createPlayer();

            mPlayerView.setPlayer(mPlayer);

            DefaultDataSourceFactory dataSourceFactory = new MediaSRC(mContext)
                    .createDataSourceFactory(true);

            MediaSource mediaSource = new ExtractorMediaSource.Factory(dataSourceFactory)
                    .createMediaSource(mediaUri);

            boolean isResume = mResumePosition > 0;
            if ((Costant.IS_IMA_AD_EXTENSION) && (mImaAdsLoader != null)) {
                mediaSource = new AdsMediaSource(mediaSource, dataSourceFactory, mImaAdsLoader,
                        mPlayerView.getOverlayFrameLayout());

            }

            if (isResume) {
                if (mediaUri.equals(mVideoUri)) {
                    mPlayer.seekTo(mResumeWindow, mResumePosition);
                }
            } else {
                mPlayer.seekToDefaultPosition();
            }

            mPlayer.prepare(mediaSource, !isResume, false);

            mPlayer.setPlayWhenReady(Costant.IS_AUTOPLAY_VIDEO);

            getFeatures();
            getFullScreen(mediaUri.toString());

            mPlayer.addListener(new MediaEvent(this,
                    mHandler, mRunnableRemainingPlay,
                    mTvErrorPlayer));

            mVideoUri = mediaUri;

        }

    }

    private void getFeatures() {

        if (Costant.IS_CONTROLLER_ALWAYS) {
            mPlayerView.setControllerShowTimeoutMs(Costant.PLAYER_SHOW_CONTROLLER_ALWAYS);
        }

        if (Costant.IS_REPEAT_VIDEO) {
            mPlayer.setRepeatMode(Player.REPEAT_MODE_ONE);
        }

        if (Costant.IS_VIDEO_FULL) {
            mPlayerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FILL);
        }

        getVolume();

    }

    private void getVolume() {

        if ((mPlayer != null) && (mPlayerView != null)) {

            mImageMutedPlay = mPlayerView.findViewById(R.id.exo_muted);

            if (Preference.isVolumeMuted(mContext)) {
                mPlayer.setVolume(0f);
                mImageMutedPlay.setImageDrawable(new IconicsDrawable(mContext, MaterialDesignIconic.Icon.gmi_volume_off)
                        .sizeDp(mContext.getResources().getInteger(R.integer.icon_size_muted))
                        .color(ImageUtil.getColor(mContext, R.color.white))
                        .respectFontBounds(true));

            } else {
                mPlayer.setVolume(1f);
                mImageMutedPlay.setImageDrawable(new IconicsDrawable(mContext, MaterialDesignIconic.Icon.gmi_volume_up)
                        .sizeDp(mContext.getResources().getInteger(R.integer.icon_size_volume))
                        .color(ImageUtil.getColor(mContext, R.color.white))
                        .respectFontBounds(true));

            }

            mImageMutedPlay.setOnClickListener(v -> {
                if (isMute()) {
                    setMute(false);
                    mImageMutedPlay.setImageDrawable(new IconicsDrawable(mContext, MaterialDesignIconic.Icon.gmi_volume_up)
                            .sizeDp(mContext.getResources().getInteger(R.integer.icon_size_volume))
                            .color(ImageUtil.getColor(mContext, R.color.white))
                            .respectFontBounds(true));

                } else {
                    setMute(true);
                    mImageMutedPlay.setImageDrawable(new IconicsDrawable(mContext, MaterialDesignIconic.Icon.gmi_volume_off)
                            .sizeDp(mContext.getResources().getInteger(R.integer.icon_size_muted))
                            .color(ImageUtil.getColor(mContext, R.color.white))
                            .respectFontBounds(true));
                }
            });
        }
    }

    private void getFullScreen(String videoUrl) {
        if ((mPlayer != null) && (mPlayerView != null)) {

            ImageView mImageFullScreen = mPlayerView.findViewById(R.id.exo_full_screen);

            mImageFullScreen.setImageDrawable(new IconicsDrawable(mContext, MaterialDesignIconic.Icon.gmi_fullscreen)
                    .sizeDp(mContext.getResources().getInteger(R.integer.icon_size_fullscreen))
                    .color(ImageUtil.getColor(mContext, R.color.white))
                    .respectFontBounds(true));

            mImageFullScreen.setOnClickListener(v -> startClickFullScreen(videoUrl));
        }
    }

    private void getExitFullScreen() {
        if ((mPlayer != null) && (mPlayerView != null)) {

            ImageView mImageExitFullScreen = mPlayerView.findViewById(R.id.exo_exit_full_screen);

            mImageExitFullScreen.setImageDrawable(new IconicsDrawable(mContext, MaterialDesignIconic.Icon.gmi_fullscreen_exit)
                    .sizeDp(mContext.getResources().getInteger(R.integer.icon_size_exit_fullscreen))
                    .color(ImageUtil.getColor(mContext, R.color.white))
                    .respectFontBounds(true));

            mImageExitFullScreen.setOnClickListener(v -> mContext.startActivity(new Intent(mContext, FullScreenActivity.class)
                    .putExtra(Costant.EXTRA_MAIN_EXIT_FULLSCREEN, true)));
        }
    }

    private void getShareFullScreen(String url) {
        if ((mPlayer != null) && (mPlayerView != null)) {

            ImageView mImageShareFullScreen = mPlayerView.findViewById(R.id.exo_share_full_screen);

            mImageShareFullScreen.setImageDrawable(new IconicsDrawable(mContext, MaterialDesignIconic.Icon.gmi_share)
                    .sizeDp(mContext.getResources().getInteger(R.integer.icon_size_share))
                    .color(ImageUtil.getColor(mContext, R.color.white))
                    .respectFontBounds(true));

            mImageShareFullScreen.setOnClickListener(v -> mContext.startActivity(new Intent(Intent.ACTION_SEND)
                    .setType("text/plain")
                    .putExtra(Intent.EXTRA_TEXT, TextUtil.textFromHtml(url))));
        }
    }

    private void startClickFullScreen(String videoUrl) {
        mContext.startActivity(new Intent(mContext, FullScreenActivity.class)
                .putExtra(Costant.EXTRA_MAIN_FULLSCREEN,
                        TextUtil.textFromHtml(videoUrl)));

    }

    public void releasePlayer() {

        if ((mPlayer != null) && (mPlayerView != null)) {
            mPlayerView.setPlayer(null);
            mPlayerView.setVisibility(View.GONE);
            mPlayer.release();
            mPlayer = null;
            hiddenPlayer();
            releaseHandler();
        }

    }


    private void hiddenPlayer() {
        if (mImagePlay != null) {
            mImagePlay.setVisibility(View.VISIBLE);
        }
        mPlayerView.setVisibility(View.GONE);
    }

    public void showPlayer() {
        visibilityProgressBar(false);
        mPlayerView.setVisibility(View.VISIBLE);
        if (mImagePlay != null) {
            mImagePlay.setVisibility(View.GONE);

        }
    }

    void visibilityProgressBar(boolean visibility) {
        int viewVisible = visibility ? View.VISIBLE : View.GONE;
        mProgressBar.setVisibility(viewVisible);
    }


    private long timeRemainingPlay() {
        return (mPlayer != null) ? mPlayer.getDuration() - mPlayer.getContentPosition() : 0;
    }


    private void setMute(boolean toMute) {
        if (toMute) {
            mPlayer.setVolume(0f);
            Preference.setVolumeMuted(mContext, true);

        } else {
            mPlayer.setVolume(1f);
            Preference.setVolumeMuted(mContext, false);

        }

    }

    public void pausePlayer() {
        if (mPlayer != null) {
            mPlayer.setPlayWhenReady(false);
            mPlayer.getPlaybackState();

        }
    }

    private boolean isMute() {
        return mPlayer != null && mPlayer.getVolume() == 0;
    }

    public void setResume(int resumeWindow, long resumePosition, Uri videoUri) {
        mResumeWindow = resumeWindow;
        mResumePosition = resumePosition;
        mVideoUri = videoUri;
    }

    public Uri getVideoUri() {
        return mVideoUri;
    }

    public int getResumeWindow() {
        return mResumeWindow;
    }

    public long getResumePosition() {
        return mResumePosition;
    }


    public void updateResumePosition() {
        if (mPlayer != null) {
            mResumeWindow = mPlayer.getCurrentWindowIndex();
            mResumePosition = Math.max(0, mPlayer.getContentPosition());
        }
    }

    private void releaseHandler() {
        if (mHandler != null) {
            mHandler.removeCallbacks(mRunnableRemainingPlay);
            mHandler = null;
        }
    }

    private void countDown() {
        if ((mTvExoCountDown != null) && (mHandler != null) &&
                (timeRemainingPlay() > 0)) {

            long delayMs = TimeUnit.SECONDS.toMillis(1);
            mHandler.postDelayed(mRunnableRemainingPlay, delayMs);
            mTvExoCountDown.setText(DateUtil.getTimeFormat(timeRemainingPlay()));

        }
    }

    private SimpleExoPlayer createPlayer() {

        int extensionRendererMode;

        if (Costant.IS_RENDERING_VIDEO) {
            extensionRendererMode = DefaultRenderersFactory.EXTENSION_RENDERER_MODE_ON;

        } else {
            extensionRendererMode = DefaultRenderersFactory.EXTENSION_RENDERER_MODE_OFF;

        }

        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        TrackSelection.Factory videoTrackSelectionFactory = new AdaptiveTrackSelection.Factory(bandwidthMeter);
        TrackSelector trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);
        LoadControl loadControl = new DefaultLoadControl();

        return ExoPlayerFactory.newSimpleInstance(
                new DefaultRenderersFactory(mContext, extensionRendererMode)
                , trackSelector, loadControl);

    }

}