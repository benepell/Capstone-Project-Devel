package info.pelleritoudacity.android.rcapstone.media;

import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;

import info.pelleritoudacity.android.rcapstone.R;

class MediaEvent implements Player.EventListener {

    private final TextView mTvErrorPlayer;
    private final Handler mHandler;
    private final MediaPlayer mMediaPlayer;
    private final Runnable mRunnableRemainingPlay;

    public MediaEvent(MediaPlayer mediaPlayer,  Handler handler,
                      Runnable runnable,  TextView tvErrorPlayer) {
        mMediaPlayer = mediaPlayer;
        mTvErrorPlayer = tvErrorPlayer;
        mHandler = handler;
        mRunnableRemainingPlay =  runnable;
    }


    @Override
    public void onTimelineChanged(Timeline timeline, Object manifest, int reason) {

    }

    @Override
    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray
            trackSelections) {

    }

    @Override
    public void onLoadingChanged(boolean isLoading) {

    }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {

        switch (playbackState) {

            case Player.STATE_BUFFERING:
                mMediaPlayer.visibilityProgressBar(true);

                break;

            case Player.STATE_READY:
                mMediaPlayer.showPlayer();
                if (mHandler != null) {
                    mHandler.post(mRunnableRemainingPlay);
                }
                break;
            default:
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

}
