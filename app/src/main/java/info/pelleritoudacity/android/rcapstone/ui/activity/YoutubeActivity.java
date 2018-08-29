package info.pelleritoudacity.android.rcapstone.ui.activity;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.WindowManager;

import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.material_design_iconic_typeface_library.MaterialDesignIconic;

import butterknife.BindView;
import butterknife.ButterKnife;
import info.pelleritoudacity.android.rcapstone.BuildConfig;
import info.pelleritoudacity.android.rcapstone.R;
import info.pelleritoudacity.android.rcapstone.utility.Costant;
import info.pelleritoudacity.android.rcapstone.utility.ImageUtil;
import info.pelleritoudacity.android.rcapstone.utility.Preference;
import info.pelleritoudacity.android.rcapstone.utility.TextUtil;
import timber.log.Timber;

public class   YoutubeActivity extends YouTubeFailureRecoveryActivity {

    @SuppressWarnings({"WeakerAccess", "CanBeFinal", "unused"})
    @BindView(R.id.youtube_toolbar)
    public android.support.v7.widget.Toolbar mToolbar;

    private String mYoutubeStr;
    private String mYoutubeTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        if (Preference.isNightMode(this)) {
            setTheme(R.style.AppThemeDark);
        }
        setContentView(R.layout.activity_media_youtube);
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }

        ButterKnife.bind(this);

        Intent intent = getIntent();

        if (intent != null) {
            mYoutubeStr = intent.getStringExtra(Costant.EXTRA_YOUTUBE_PARAM);
            mYoutubeTitle = intent.getStringExtra(Costant.EXTRA_YOUTUBE_TITLE);
        }

        YouTubePlayerFragment youTubePlayerFragment =
                (YouTubePlayerFragment) getFragmentManager().findFragmentById(R.id.youtube_fragment);
        if(!TextUtils.isEmpty(Costant.YOUTUBE_DEVELOPER_KEY)) {
            youTubePlayerFragment.initialize(Costant.YOUTUBE_DEVELOPER_KEY, this);

        }else {
            mYoutubeTitle = getString(R.string.no_api_key_youtube);
            Timber.d("YoutubeDev Developer key cannot be null or empty");
        }

        mToolbar.setTitle(mYoutubeTitle);

        mToolbar.setNavigationIcon(new IconicsDrawable(this, MaterialDesignIconic.Icon.gmi_arrow_back)
                .sizeDp(getResources().getInteger(R.integer.icon_toolbar_back))
                .color(Color.WHITE)
                .respectFontBounds(true));
        mToolbar.setNavigationOnClickListener(v -> onBackPressed());

        if(Preference.isNightMode(this)){
            mToolbar.setBackgroundColor(ImageUtil.getColor(getApplicationContext(),R.color.colorBackgroundDark) );
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


