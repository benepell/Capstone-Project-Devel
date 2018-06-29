package info.pelleritoudacity.android.rcapstone.ui.activity;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;

import info.pelleritoudacity.android.rcapstone.R;
import info.pelleritoudacity.android.rcapstone.utility.ActivityUI;
import info.pelleritoudacity.android.rcapstone.utility.Costant;
import info.pelleritoudacity.android.rcapstone.utility.TextUtil;
import timber.log.Timber;

public class MediaYoutubeActivity extends YouTubeFailureRecoveryActivity {

    private String mYoutubeStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityUI.youtubeFullScreen(this);
        setContentView(R.layout.activity_media_youtube);

        Timber.plant(new Timber.DebugTree());

        YouTubePlayerFragment youTubePlayerFragment =
                (YouTubePlayerFragment) getFragmentManager().findFragmentById(R.id.youtube_fragment);
        youTubePlayerFragment.initialize(Costant.YOUTUBE_DEVELOPER_KEY, this);

        Intent intent = getIntent();

        if (intent != null) {
            mYoutubeStr = intent.getStringExtra(Costant.EXTRA_YOUTUBE_PARAM);
        }
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player,
                                        boolean wasRestored) {
        if (!wasRestored) {
            if (!TextUtils.isEmpty(mYoutubeStr)) {
                String v = TextUtil.youtubeValue(mYoutubeStr);

                if (Costant.YOUTUBE_CLIENT_AUTOSTART) {
                    player.loadVideo(v);

                } else {
                    player.cueVideo(v);

                }
            }
        }
    }

    @Override
    protected YouTubePlayer.Provider getYouTubePlayerProvider() {
        return (YouTubePlayerFragment) getFragmentManager().findFragmentById(R.id.youtube_fragment);
    }

}


