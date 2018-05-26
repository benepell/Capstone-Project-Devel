package info.pelleritoudacity.android.rcapstone.ui.activity;

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
import info.pelleritoudacity.android.rcapstone.media.ExoPlayerExecute;
import info.pelleritoudacity.android.rcapstone.utility.ActivityUI;
import info.pelleritoudacity.android.rcapstone.utility.Costants;
import info.pelleritoudacity.android.rcapstone.utility.TextUtil;
import timber.log.Timber;

public class SubRedditFullScreenActivity extends AppCompatActivity {

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


    private Context mContext;
    private String mVideoUri;
    private ExoPlayerExecute mExoPlayerExecute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       ActivityUI.windowFullScreen(this);
        setContentView(R.layout.activity_sub_reddit_full_screen);

        mContext = SubRedditFullScreenActivity.this;
        Timber.plant(new Timber.DebugTree());
        ButterKnife.bind(this);


        Intent intent = getIntent();

        if (intent != null) {
            if(intent.getBooleanExtra(Costants.EXTRA_SUBREDDIT_EXIT_FULLSCREEN,false)){
                finish();
                startActivity(new Intent(mContext,SubRedditActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
            }
            /*if(intent.getBooleanExtra(Costants.EXTRA_SUBREDDIT_SHARE_FULLSCREEN,false)){
                startActivity( new Intent (Intent.ACTION_SEND)
                        .putExtra(Intent.EXTRA_TEXT, TextUtil.textFromHtml(intent.getStringExtra(Costants.EXTRA_SUBREDDIT_SHARE_URL_FULLSCREEN))));
            }*/
            mVideoUri = intent.getStringExtra(Costants.EXTRA_SUBREDDIT_FULLSCREEN);


        }


        ImaAdsLoader imaAdsLoader = new ImaAdsLoader(mContext, Uri.parse(getString(R.string.ad_tag_url)));

        mExoPlayerExecute = new ExoPlayerExecute(mContext, imaAdsLoader,
                mPlayerView, mExoProgressBar, null, mTVErrorPlayer);

    }

    @Override
    public void onStart() {
        super.onStart();
        if ((Util.SDK_INT > 23) && (mExoPlayerExecute != null)) {
            mExoPlayerExecute.inizializePlayerFullScreen(Uri.parse(mVideoUri));
            Timber.d("FULLVIDEO %s",mVideoUri);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
       if(ActivityUI.isLandscapeOrientation(this)){
           ActivityUI.leanBackUI(this);
       }

        if ((Util.SDK_INT <= 23 && mExoPlayerExecute != null)) {
            mExoPlayerExecute.inizializePlayerFullScreen(Uri.parse(mVideoUri));
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if ((Util.SDK_INT <= 23) && (mExoPlayerExecute != null)) {
            mExoPlayerExecute.releasePlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if( (Util.SDK_INT > 23)&& (mExoPlayerExecute != null)) {
            mExoPlayerExecute.releasePlayer();
        }
    }


}
