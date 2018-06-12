package info.pelleritoudacity.android.rcapstone.ui.activity;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;

import info.pelleritoudacity.android.rcapstone.R;
import info.pelleritoudacity.android.rcapstone.utility.ActivityUI;
import info.pelleritoudacity.android.rcapstone.utility.Costants;
import info.pelleritoudacity.android.rcapstone.utility.TextUtil;
import timber.log.Timber;

public class MediaYoutubeActivity extends YouTubeFailureRecoveryActivity {

    private String mYoutubeStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityUI.youtubeFullScreen(this);
        setContentView(R.layout.activity_media_youtube);

        YouTubePlayerFragment youTubePlayerFragment =
                (YouTubePlayerFragment) getFragmentManager().findFragmentById(R.id.youtube_fragment);
        youTubePlayerFragment.initialize(Costants.YOUTUBE_DEVELOPER_KEY, this);

        Intent intent = getIntent();

        if (intent != null) {
            mYoutubeStr = intent.getStringExtra(Costants.EXTRA_YOUTUBE_PARAM);
            String[] searchQueryYoutube = {"youtube.com/watch?v=", "youtu.be/", "youtube.com/v/"};
            int nSearchLeft;
            int nSearchRight;
            for (String s : searchQueryYoutube) {
                nSearchLeft = mYoutubeStr.indexOf(s);

                nSearchRight = mYoutubeStr.indexOf("&");

                if (nSearchLeft > 0) {
                    if (nSearchRight > 0) {
                        mYoutubeStr = mYoutubeStr.substring(nSearchLeft + s.length(), nSearchRight);

                    } else {
                        mYoutubeStr = mYoutubeStr.substring(nSearchLeft + s.length());

                    }
                }
            }
        }
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player,
                                        boolean wasRestored) {
        if (!wasRestored) {
            if (!TextUtils.isEmpty(mYoutubeStr))
                if (Costants.YOUTUBE_CLIENT_AUTOSTART) {
                    player.loadVideo(mYoutubeStr);

                } else {
                    player.cueVideo(mYoutubeStr);

                }
        }
    }

    @Override
    protected YouTubePlayer.Provider getYouTubePlayerProvider() {
        return (YouTubePlayerFragment) getFragmentManager().findFragmentById(R.id.youtube_fragment);
    }

}


