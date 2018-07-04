package info.pelleritoudacity.android.rcapstone.ui.activity;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toolbar;

import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.material_design_iconic_typeface_library.MaterialDesignIconic;

import butterknife.BindView;
import butterknife.ButterKnife;
import info.pelleritoudacity.android.rcapstone.R;
import info.pelleritoudacity.android.rcapstone.utility.ActivityUI;
import info.pelleritoudacity.android.rcapstone.utility.Costant;
import info.pelleritoudacity.android.rcapstone.utility.TextUtil;
import timber.log.Timber;

public class MediaYoutubeActivity extends YouTubeFailureRecoveryActivity {

    @SuppressWarnings({"WeakerAccess", "CanBeFinal", "unused"})
    @BindView(R.id.youtube_toolbar)
    public android.support.v7.widget.Toolbar mToolbar;

    private String mYoutubeStr;
    private String mYoutubeTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityUI.youtubeFullScreen(this);
        setContentView(R.layout.activity_media_youtube);

        Timber.plant(new Timber.DebugTree());
        ButterKnife.bind(this);

        YouTubePlayerFragment youTubePlayerFragment =
                (YouTubePlayerFragment) getFragmentManager().findFragmentById(R.id.youtube_fragment);
        youTubePlayerFragment.initialize(Costant.YOUTUBE_DEVELOPER_KEY, this);

        Intent intent = getIntent();

        if (intent != null) {
            mYoutubeStr = intent.getStringExtra(Costant.EXTRA_YOUTUBE_PARAM);
            mYoutubeTitle = intent.getStringExtra(Costant.EXTRA_YOUTUBE_TITLE);
        }

        mToolbar.setTitle(mYoutubeTitle);

        mToolbar.setNavigationIcon(new IconicsDrawable(this, MaterialDesignIconic.Icon.gmi_arrow_back)
                .sizeDp(getResources().getInteger(R.integer.icon_toolbar_back))
                .color(Color.WHITE)
                .respectFontBounds(true));
        mToolbar.setNavigationOnClickListener(v -> onBackPressed());

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


