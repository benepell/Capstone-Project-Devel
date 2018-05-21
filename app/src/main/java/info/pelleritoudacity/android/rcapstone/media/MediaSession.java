package info.pelleritoudacity.android.rcapstone.media;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.media.session.MediaButtonReceiver;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;

import com.google.android.exoplayer2.SimpleExoPlayer;

import info.pelleritoudacity.android.rcapstone.BuildConfig;
import info.pelleritoudacity.android.rcapstone.R;
import info.pelleritoudacity.android.rcapstone.ui.activity.SubRedditActivity;
import info.pelleritoudacity.android.rcapstone.utility.Costants;

import static android.content.Context.NOTIFICATION_SERVICE;
import static info.pelleritoudacity.android.rcapstone.ui.activity.SubRedditActivity.sMediaSessionCompat;

public class MediaSession {

    private Context mContext;
    private String mDescription;

    private SimpleExoPlayer mPlayer;


    private PlaybackStateCompat.Builder mStateBuilder;

    public MediaSession(Context context, SimpleExoPlayer player) {
        mContext = context;
        mPlayer = player;
    }

    public void initializeMediaSession() {


        sMediaSessionCompat = new MediaSessionCompat(mContext, Costants.EXO_PLAYER_MANAGER_TAG);
        sMediaSessionCompat.setFlags(
                MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS |
                        MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS | Intent.FLAG_ACTIVITY_CLEAR_TOP);

        sMediaSessionCompat.setMediaButtonReceiver(null);
        mStateBuilder = new PlaybackStateCompat.Builder()
                .setActions(
                        PlaybackStateCompat.ACTION_PLAY |
                                PlaybackStateCompat.ACTION_PAUSE |
                                PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS |
                                PlaybackStateCompat.ACTION_PLAY_PAUSE);
        sMediaSessionCompat.setPlaybackState(mStateBuilder.build());
        sMediaSessionCompat.setCallback(new MySessionCallback());
        sMediaSessionCompat.setActive(true);
    }


    public void showNotification(PlaybackStateCompat state) {

        int icon;
        String play_pause;

        if (state.getState() == PlaybackStateCompat.STATE_PLAYING) {
            icon = R.drawable.exo_controls_pause;
            play_pause = mContext.getString(R.string.pause);
        } else {
            icon = R.drawable.exo_controls_play;
            play_pause = mContext.getString(R.string.play);
        }

        NotificationCompat.Action playPauseAction = new NotificationCompat.Action(
                icon, play_pause,
                MediaButtonReceiver.buildMediaButtonPendingIntent(mContext,
                        PlaybackStateCompat.ACTION_PLAY_PAUSE));

        NotificationCompat.Action restartAction = new android.support.v4.app.NotificationCompat
                .Action(R.drawable.exo_controls_previous, mContext.getString(R.string.restart),
                MediaButtonReceiver.buildMediaButtonPendingIntent
                        (mContext, PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS));

        PendingIntent contentPendingIntent = PendingIntent.getActivity
                (mContext, 0, new Intent(mContext, SubRedditActivity.class), 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(mContext, Costants.NOTIFICATION_CHANNEL_ID);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {

            NotificationChannel notificationChannel = new NotificationChannel(Costants.NOTIFICATION_CHANNEL_ID,
                    Costants.NOTIFICATION_CHANNEL_NAME, NotificationManager.IMPORTANCE_MIN);
            notificationChannel.setDescription(mContext.getString(R.string.notification_text));

            notificationChannel.setShowBadge(true);
            notificationChannel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);

            NotificationManager notificationManager = (NotificationManager) mContext.getSystemService(NOTIFICATION_SERVICE);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(notificationChannel);
            }

        }

        builder.setContentTitle(mDescription)
                .setContentText(mContext.getString(R.string.notification_text))
                .setAutoCancel(true)
                .setContentIntent(contentPendingIntent)
                .setSmallIcon(R.drawable.ic_notifications_none_black_24dp)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .addAction(restartAction)
                .addAction(playPauseAction);

        NotificationManager notificationManager = (NotificationManager) mContext.getSystemService(NOTIFICATION_SERVICE);
        if (notificationManager != null) {
            notificationManager.notify(Costants.NOTIFICATION_CHANNEL_ID, Costants.NOTIFICATION_ID, builder.build());
        }

    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    public PlaybackStateCompat.Builder getStateBuilder() {
        return mStateBuilder;
    }

    public void setStateBuilder(PlaybackStateCompat.Builder mStateBuilder) {
        this.mStateBuilder = mStateBuilder;
    }


    private class MySessionCallback extends MediaSessionCompat.Callback implements MediaSessionCallback {

        @Override
        public void onSimplePlayer(SimpleExoPlayer simpleExoPlayer) {
            mPlayer = simpleExoPlayer;
        }

        @Override
        public void onPlay() {
            if (mPlayer != null) mPlayer.setPlayWhenReady(true);
        }

        @Override
        public void onPause() {
            if (mPlayer != null) mPlayer.setPlayWhenReady(false);
        }

        @Override
        public void onSkipToPrevious() {
            if (mPlayer != null) mPlayer.seekToDefaultPosition();
        }

    }

    public interface MediaSessionCallback {

        void onSimplePlayer(SimpleExoPlayer simpleExoPlayer);
    }
}
