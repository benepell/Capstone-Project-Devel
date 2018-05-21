
/*
 *  ____        _    _                  _
 * | __ )  __ _| | _(_)_ __   __ _     / \   _ __  _ __
 * |  _ \ / _` | |/ / | '_ \ / _` |   / _ \ | '_ \| '_ \
 * | |_) | (_| |   <| | | | | (_| |  / ___ \| |_) | |_) |
 * |____/ \__,_|_|\_\_|_| |_|\__, | /_/   \_\ .__/| .__/
 *                           |___/          |_|   |_|
 *
 * Copyright (C) 2017 Benedetto Pellerito
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
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.view.View;
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
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import info.pelleritoudacity.android.rcapstone.R;
import info.pelleritoudacity.android.rcapstone.utility.Costants;

import static info.pelleritoudacity.android.rcapstone.ui.activity.SubRedditActivity.sMediaSessionCompat;

public class ExoPlayerManager implements Player.EventListener {

    private final Context mContext;

    private boolean isAutoPlay;
    String mShortDescription;

    private int mResumeWindow;
    private long mResumePosition;


    private final PlayerView mPlayerView;
    private SimpleExoPlayer mPlayer;
    private ProgressBar mProgressBar;

    TextView mTvErrorPlayer;

    private ExoPlayerListener iExoPlayer;
    private MediaSession mMediaSession;
    private ImaAdsLoader mImaAdsLoader;

    public ExoPlayerManager(Context context, ExoPlayerListener listener, PlayerView playerView, ProgressBar progressBar, String shortDescription, TextView tvErrorPlayer) {
        mContext = context;
        iExoPlayer = listener;
        mPlayerView = playerView;
        mProgressBar = progressBar;
        mShortDescription = shortDescription;
        mTvErrorPlayer = tvErrorPlayer;

    }

    public ExoPlayerManager(Context context, ImaAdsLoader imaAdsLoader, ExoPlayerListener listener, PlayerView playerView, ProgressBar progressBar, String shortDescription, TextView tvErrorPlayer) {
        mContext = context;
        mImaAdsLoader = imaAdsLoader;
        iExoPlayer = listener;
        mPlayerView = playerView;
        mProgressBar = progressBar;
        mShortDescription = shortDescription;
        mTvErrorPlayer = tvErrorPlayer;

    }


    public void initializePlayer(Uri mediaUri) {
        if (mPlayer == null) {

            BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
            TrackSelection.Factory videoTrackSelectionFactory = new AdaptiveTrackSelection.Factory(bandwidthMeter);
            TrackSelector trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);
            LoadControl loadControl = new DefaultLoadControl();

            // todo render video
            boolean isRenderingVideo = false;

            int extensionRendererMode;
            if (isRenderingVideo) {
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

            final boolean isResume = mResumePosition > 0;
            if (isResume) {
                mPlayer.seekTo(mResumeWindow, mResumePosition);
            }


            if (mImaAdsLoader != null) {

                AdsMediaSource adsMediaSource = new AdsMediaSource(mediaSource, dataSourceFactory, mImaAdsLoader, mPlayerView.getOverlayFrameLayout());
                mPlayer.prepare(adsMediaSource, !isResume, false);

            } else {

                mPlayer.prepare(mediaSource, !isResume, false);

            }

            mPlayer.setPlayWhenReady(isAutoPlay);


            if (Costants.IS_MEDIA_SESSION) {
                mMediaSession = new MediaSession(mContext, mPlayer);
                mMediaSession.setDescription(mShortDescription);
                mMediaSession.initializeMediaSession();

            }

        }
        iExoPlayer.onPlayer(mPlayer);

    }

    public void releasePlayer() {
        if ((mPlayer != null) && (mPlayerView != null)) {
            mPlayerView.setPlayer(null);
            mPlayer.release();
            mPlayer = null;
        }
    }

    private void showPlayer() {
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

        switch (state) {
            case Player.STATE_READY:

                if (playWhenReady) {

                    if (mMediaSession != null) {

                        if (mMediaSession.getStateBuilder() != null) {
                            mMediaSession.setStateBuilder(mMediaSession.getStateBuilder().setState(PlaybackStateCompat.STATE_PLAYING,
                                    mPlayer.getCurrentPosition(), 1f));
                        }
                        setAutoPlay(true);

                    } else {

                        if (mMediaSession.getStateBuilder() != null) {
                            mMediaSession.setStateBuilder(mMediaSession.getStateBuilder().setState(PlaybackStateCompat.STATE_PAUSED,
                                    mPlayer.getCurrentPosition(), 1f));
                        }
                        setAutoPlay(false);
                    }
                }

                break;

            case Player.STATE_ENDED:

                if (playWhenReady) {
                    showPlayer();
                } else {
                    mPlayer.seekToDefaultPosition();
                }
                break;

            default:
        }

        if ((sMediaSessionCompat != null) && (mMediaSession != null) &&
                (mMediaSession.getStateBuilder() != null)) {

            sMediaSessionCompat.setPlaybackState(mMediaSession.getStateBuilder().build());
            mMediaSession.showNotification(mMediaSession.getStateBuilder().build());
        }

    }


    public void setAutoPlay(boolean autoPlay) {
        isAutoPlay = autoPlay;
    }

    public boolean isAutoPlay() {
        return isAutoPlay;
    }

    public void setResume(int resumeWindow, long resumePosition) {
        mResumeWindow = resumeWindow;
        mResumePosition = resumePosition;
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

    public void clearResumePosition() {
        mResumeWindow = C.INDEX_UNSET;
        mResumePosition = C.TIME_UNSET;
    }


    public interface ExoPlayerListener {
        void onPlayer(SimpleExoPlayer player);
    }


}
