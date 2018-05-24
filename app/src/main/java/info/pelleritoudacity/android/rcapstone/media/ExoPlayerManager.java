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
import android.net.Uri;
import android.os.Handler;
import android.support.v4.media.session.PlaybackStateCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.ext.ima.ImaAdsLoader;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.source.ads.AdsMediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.material_design_iconic_typeface_library.MaterialDesignIconic;

import java.util.concurrent.TimeUnit;

import info.pelleritoudacity.android.rcapstone.R;
import info.pelleritoudacity.android.rcapstone.utility.Costants;
import info.pelleritoudacity.android.rcapstone.utility.DateUtils;
import info.pelleritoudacity.android.rcapstone.utility.ImageUtils;
import info.pelleritoudacity.android.rcapstone.utility.PrefManager;

import static info.pelleritoudacity.android.rcapstone.ui.activity.SubRedditActivity.sMediaSessionCompat;

public class ExoPlayerManager implements Player.EventListener {


    private final Context mContext;

    final String mShortDescription;

    private boolean mIsAutoPlay;
    private int mResumeWindow;
    private long mResumePosition;
    private Uri mVideoUri = null;

    private final PlayerView mPlayerView;
    private SimpleExoPlayer mPlayer;
    private final ProgressBar mProgressBar;

    final TextView mTvErrorPlayer;

    private MediaSession mMediaSession;
    private final ImaAdsLoader mImaAdsLoader;
    private TextView mTvExoCountDown;
    private ImageView mImageMutedPlay;
    private Handler mHandler;
    private int mAdapterPosition;

    public ExoPlayerManager(Context context, ImaAdsLoader imaAdsLoader,  PlayerView playerView,
                            ProgressBar progressBar, String shortDescription, TextView tvErrorPlayer) {

        mContext = context;
        mImaAdsLoader = imaAdsLoader;
        mPlayerView = playerView;
        mProgressBar = progressBar;
        mShortDescription = shortDescription;
        mTvErrorPlayer = tvErrorPlayer;
    }

    public void initializePlayer(Uri mediaUri, int adapterPosition) {

        if (mHandler == null) {
            mHandler = new Handler();
        }

        if ((mPlayer == null) && (mPlayerView != null)) {

            mTvExoCountDown = mPlayerView.findViewById(R.id.exo_countdown);
            mTvExoCountDown.setVisibility(View.VISIBLE);

            BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
            TrackSelection.Factory videoTrackSelectionFactory = new AdaptiveTrackSelection.Factory(bandwidthMeter);
            TrackSelector trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);
            LoadControl loadControl = new DefaultLoadControl();

            int extensionRendererMode;
            if (Costants.IS_RENDERING_VIDEO) {
                extensionRendererMode = DefaultRenderersFactory.EXTENSION_RENDERER_MODE_ON;

            } else {
                extensionRendererMode = DefaultRenderersFactory.EXTENSION_RENDERER_MODE_OFF;

            }

            mPlayer = ExoPlayerFactory.newSimpleInstance(
                    new DefaultRenderersFactory(mContext, extensionRendererMode), trackSelector, loadControl);

            mPlayerView.setPlayer(mPlayer);

            mPlayer.addListener(this);

            DefaultDataSourceFactory dataSourceFactory = new DefaultDataSourceFactory(mContext,
                    Util.getUserAgent(mContext, Costants.USER_AGENT_MEDIA));

            MediaSource mediaSource = new ExtractorMediaSource.Factory(dataSourceFactory)
                    .createMediaSource(mediaUri);

            boolean isResume = mResumePosition > 0;
            if ((Costants.IS_IMA_AD_EXTENSION) && (mImaAdsLoader != null)&& (adapterPosition > 0)) {

                AdsMediaSource adsMediaSource = new AdsMediaSource(mediaSource, dataSourceFactory, mImaAdsLoader,
                        mPlayerView.getOverlayFrameLayout());
                mediaSource = adsMediaSource;

            }

            if (Costants.IS_MEDIA_SESSION) {
                mMediaSession = new MediaSession(mContext, mPlayer);
                mMediaSession.setDescription(mShortDescription);
                mMediaSession.initializeMediaSession();
            }

            if (Costants.IS_CONTROLLER_ALWAYS) {
                mPlayerView.setControllerShowTimeoutMs(Costants.PLAYER_SHOW_CONTROLLER_ALWAYS);
            }

            if (Costants.IS_REPEAT_VIDEO) {
                mPlayer.setRepeatMode(Player.REPEAT_MODE_ONE);
            }

            initializeVolume();

            mPlayerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FILL);

            if (isResume) {
                if (mediaUri.equals(mVideoUri)) {
                    mPlayer.seekTo(mResumeWindow, mResumePosition);
                }
            }else {
                mPlayer.seekToDefaultPosition();
            }

            mPlayer.prepare(mediaSource, !isResume, false);

            mPlayer.setPlayWhenReady(Costants.IS_AUTOPLAY_VIDEO);

            mVideoUri = mediaUri;

        }

    }

    private void initializeVolume() {

        if ((mPlayer != null) && (mPlayerView != null)) {

            mImageMutedPlay = mPlayerView.findViewById(R.id.exo_muted);

            if (PrefManager.getBoolPref(mContext, R.string.pref_volume_muted)) {
                mPlayer.setVolume(0f);
                mImageMutedPlay.setImageDrawable(new IconicsDrawable(mContext, MaterialDesignIconic.Icon.gmi_volume_off)
                        .sizeDp(mContext.getResources().getInteger(R.integer.icon_size_muted))
                        .color(ImageUtils.getColor(mContext, R.color.white))
                        .respectFontBounds(true));

            } else {
                mPlayer.setVolume(1f);
                mImageMutedPlay.setImageDrawable(new IconicsDrawable(mContext, MaterialDesignIconic.Icon.gmi_volume_up)
                        .sizeDp(mContext.getResources().getInteger(R.integer.icon_size_volume))
                        .color(ImageUtils.getColor(mContext, R.color.white))
                        .respectFontBounds(true));

            }

            mImageMutedPlay.setOnClickListener(v -> {
                if (isMute()) {
                    setMute(false);
                    mImageMutedPlay.setImageDrawable(new IconicsDrawable(mContext, MaterialDesignIconic.Icon.gmi_volume_up)
                            .sizeDp(mContext.getResources().getInteger(R.integer.icon_size_volume))
                            .color(ImageUtils.getColor(mContext, R.color.white))
                            .respectFontBounds(true));

                } else {
                    setMute(true);
                    mImageMutedPlay.setImageDrawable(new IconicsDrawable(mContext, MaterialDesignIconic.Icon.gmi_volume_off)
                            .sizeDp(mContext.getResources().getInteger(R.integer.icon_size_muted))
                            .color(ImageUtils.getColor(mContext, R.color.white))
                            .respectFontBounds(true));
                }
            });
        }
    }

    public void releasePlayer() {
        releaseHandler();

        if ((mPlayer != null) && (mPlayerView != null)) {
            mPlayerView.setPlayer(null);
            mPlayer.release();
            mPlayer = null;
            hiddenPlayer();
        }

    }

    public void releaseHandler() {
        if (mHandler != null) {
            mHandler.removeCallbacks(runnableRemainingPlay);
            mHandler = null;
        }

    }

    public void hiddenPlayer() {
        mPlayerView.setVisibility(View.GONE);
    }

    public void showPlayer() {
        visibilityProgressBar(false);
        mPlayerView.setVisibility(View.VISIBLE);
    }

    private void visibilityProgressBar(boolean visibility) {
        int viewVisible = visibility ? View.VISIBLE : View.GONE;
        mProgressBar.setVisibility(viewVisible);
    }

    @Override
    public void onTimelineChanged(Timeline timeline, Object manifest, int reason) {

    }

    @Override
    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

    }

    @Override
    public void onLoadingChanged(boolean isLoading) {

    }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {

        switch (playbackState) {

            case Player.STATE_BUFFERING:
                visibilityProgressBar(true);

                break;

            case Player.STATE_READY:
                showPlayer();
                mHandler.post(runnableRemainingPlay);
                break;

            default:
        }

        if (Costants.IS_MEDIA_SESSION) {
            mediaSessionState(playWhenReady, playbackState);
        }

    }

    @Override
    public void onRepeatModeChanged(int repeatMode) {

    }

    @Override
    public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) {

    }

    @Override
    public void onPlayerError(ExoPlaybackException error) {
        mTvErrorPlayer.setText(R.string.error_video);
        mTvErrorPlayer.setVisibility(View.VISIBLE);
    }


    @Override
    public void onPositionDiscontinuity(int reason) {

    }

    @Override
    public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {

    }

    @Override
    public void onSeekProcessed() {

    }

    private void mediaSessionState(boolean playWhenReady, int state) {
        if (mMediaSession != null) {
            switch (state) {
                case Player.STATE_READY:
                    if (playWhenReady) {
                        if (mMediaSession.getStateBuilder() != null) {
                            mMediaSession.setStateBuilder(mMediaSession.getStateBuilder().setState(PlaybackStateCompat.STATE_PLAYING,
                                    mPlayer.getCurrentPosition(), 1f));
                        }
                    }

                    break;

                case Player.STATE_ENDED:
                    if (!playWhenReady) {
                        mPlayer.seekToDefaultPosition();
                    }
                    break;

                default:
            }

            if ((sMediaSessionCompat != null) && (mMediaSession.getStateBuilder() != null)) {
                sMediaSessionCompat.setPlaybackState(mMediaSession.getStateBuilder().build());
                mMediaSession.showNotification(mMediaSession.getStateBuilder().build());

            }
        }
    }

    private final Runnable runnableRemainingPlay = this::tvCountDown;

    private long timeRemainingPlay() {
        return (mPlayer != null) ? mPlayer.getDuration() - mPlayer.getContentPosition() : 0;
    }

    private void tvCountDown() {
        if (mPlayer != null) {
            if (timeRemainingPlay() > 0) {
                long delayMs = TimeUnit.SECONDS.toMillis(1);
                mHandler.postDelayed(runnableRemainingPlay, delayMs);
                mTvExoCountDown.setText(DateUtils.getTimeFormat(timeRemainingPlay()));
            }
        }
    }

    public void setMute(boolean toMute) {
        if (toMute) {
            mPlayer.setVolume(0f);
            PrefManager.putBoolPref(mContext, R.string.pref_volume_muted, true);

        } else {
            mPlayer.setVolume(1f);
            PrefManager.putBoolPref(mContext, R.string.pref_volume_muted, false);

        }

    }

    public void pausePlayer() {
        if (mPlayer != null) {
            mPlayer.setPlayWhenReady(false);
            mPlayer.getPlaybackState();

        }
    }

    public void restartPlayer() {
        if (mPlayer != null) {
            mPlayer.setPlayWhenReady(true);
            mPlayer.getPlaybackState();
        }
    }


    public boolean isMute() {
        return mPlayer != null && mPlayer.getVolume() == 0;
    }

   public void setAutoPlay(boolean autoPlay) {
        mIsAutoPlay = autoPlay;
    }

    public boolean isAutoPlay() {
        return mIsAutoPlay;
    }

    public void setResume(int resumeWindow, long resumePosition, Uri videoUri) {
        mResumeWindow = resumeWindow;
        mResumePosition = resumePosition;
        mVideoUri = videoUri;
    }

    public int getResumeWindow() {
        return mResumeWindow;
    }

    public long getResumePosition() {
        return mResumePosition;
    }

    public Uri getVideoUri() {
        return mVideoUri;
    }

    public void updateResumePosition() {
        if (mPlayer != null) {
            mResumeWindow = mPlayer.getCurrentWindowIndex();
            mResumePosition = Math.max(0, mPlayer.getContentPosition());
        }
    }

    public void clearResumePosition() {
        mResumeWindow = C.INDEX_UNSET;
        mResumePosition = C.TIME_UNSET;
    }

}