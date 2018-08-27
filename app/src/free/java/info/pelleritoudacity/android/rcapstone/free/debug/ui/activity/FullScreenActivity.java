package info.pelleritoudacity.android.rcapstone.free.debug.ui.activity;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.exoplayer2.ext.ima.ImaAdsLoader;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.util.Util;

import butterknife.BindView;
import butterknife.ButterKnife;
import info.pelleritoudacity.android.rcapstone.R;
import info.pelleritoudacity.android.rcapstone.free.debug.data.model.MediaModel;
import info.pelleritoudacity.android.rcapstone.free.debug.media.MediaPlayer;
import info.pelleritoudacity.android.rcapstone.utility.ActivityUI;
import info.pelleritoudacity.android.rcapstone.utility.Costant;
import timber.log.Timber;

public class FullScreenActivity extends AppCompatActivity {

    @SuppressWarnings({"WeakerAccess", "CanBeFinal", "unused"})
    @BindView(R.id.id_player_full_layout)
    LinearLayout mPlayerLayout;

    @SuppressWarnings({"WeakerAccess", "CanBeFinal", "unused"})
    @BindView(R.id.pv_subreddit_full)
    PlayerView mPlayerView;

    @SuppressWarnings({"WeakerAccess", "CanBeFinal", "unused"})
    @BindView(R.id.exo_progressBar_full)
    ProgressBar mExoProgressBar;

    @SuppressWarnings({"WeakerAccess", "CanBeFinal", "unused"})
    @BindView(R.id.tv_error_player_full)
    TextView mTVErrorPlayer;

    private String mVideoUri;
    private MediaPlayer mMediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityUI.windowFullScreen(this);
        setContentView(R.layout.activity_full_screen);

        Context mContext = FullScreenActivity.this;
        Timber.plant(new Timber.DebugTree());
        ButterKnife.bind(this);


        Intent intent = getIntent();

        if (intent != null) {
            if (intent.getBooleanExtra(Costant.EXTRA_MAIN_EXIT_FULLSCREEN, false)) {
                finish();
                startActivity(new Intent(mContext, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
            }
            mVideoUri = intent.getStringExtra(Costant.EXTRA_MAIN_FULLSCREEN);


        }


        ImaAdsLoader imaAdsLoader = new ImaAdsLoader(mContext, Uri.parse(getString(R.string.ad_tag_url)));

        MediaModel mediaModel = new MediaModel();

        mediaModel.setImaAdsLoader(imaAdsLoader);
        mediaModel.setProgressBar(mExoProgressBar);
        mediaModel.setPlayerView(mPlayerView);
        mediaModel.setTvErrorPlayer(mTVErrorPlayer);

        mMediaPlayer = new MediaPlayer(mContext, mediaModel);

    }

    @Override
    public void onStart() {
        super.onStart();
        if ((Util.SDK_INT > 23) && (mMediaPlayer != null)) {
            mMediaPlayer.initPlayerFullScreen(Uri.parse(mVideoUri));
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (ActivityUI.isLandscapeOrientation(this)) {
            ActivityUI.leanBackUI(this);
        }

        if ((Util.SDK_INT <= 23 && mMediaPlayer != null)) {
            mMediaPlayer.initPlayerFullScreen(Uri.parse(mVideoUri));
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if ((Util.SDK_INT <= 23) && (mMediaPlayer != null)) {
            mMediaPlayer.releasePlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if ((Util.SDK_INT > 23) && (mMediaPlayer != null)) {
            mMediaPlayer.releasePlayer();
        }
    }

}
