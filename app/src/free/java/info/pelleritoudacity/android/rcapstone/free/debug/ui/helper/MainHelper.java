package info.pelleritoudacity.android.rcapstone.free.debug.ui.helper;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.material_design_iconic_typeface_library.MaterialDesignIconic;

import java.nio.charset.StandardCharsets;

import info.pelleritoudacity.android.rcapstone.R;
import info.pelleritoudacity.android.rcapstone.free.debug.media.MediaPlayer;
import info.pelleritoudacity.android.rcapstone.free.debug.ui.activity.YoutubeActivity;
import info.pelleritoudacity.android.rcapstone.utility.Costant;

public class MainHelper {

    private final Context mContext;
    private final RequestOptions options;

    public MainHelper(Context context) {
        mContext = context;

        options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.download_in_progress)
                .error(R.drawable.ic_no_image)
                .diskCacheStrategy(DiskCacheStrategy.ALL);
    }

    @SuppressLint("SetJavaScriptEnabled")
    public void loadWebviewVimeo(WebView view, String videoFrameYoutube) {

        String videoFrameBuilder = "<!DOCTYPE html><html><head>" +
                "<meta charset=\"utf-8\"><meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">" +
                "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">" +
                "<style>body{padding:10px;font-size:200%}" +
                "img{width:100%}.videoWrapper{position:relative;padding-bottom:60%;padding-top:10px;height:0}.videoWrapper " +
                "iframe{position:absolute;top:0;left:0;width:100%;height:100%}@media screen and " +
                "(-webkit-min-device-pixel-ratio: 0){body{word-break:break-word}}" +
                "</style></head><body>" +
                "<div class=\"videoWrapper\"> " +
                "<iframe " + videoFrameYoutube.substring(videoFrameYoutube.indexOf("src=")) +
                "</div></body></html>";

        view.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }

        });

        WebSettings webSettings = view.getSettings();
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setJavaScriptEnabled(true);
        view.loadData(videoFrameBuilder,
                "text/html",
                StandardCharsets.UTF_8.name());

        view.setVisibility(View.VISIBLE);
    }

    public void loadVideoFirstFrame(MediaPlayer mediaPlayer, FrameLayout layout, ImageView imageView, ImageView imagePlay, ProgressBar progressBar,
                                    String videoPreviewUrl) throws OutOfMemoryError {

        imagePlay.setVisibility(View.VISIBLE);
        imageView.setVisibility(View.VISIBLE);

        Glide.with(mContext)
                .load(videoPreviewUrl)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        progressBar.setVisibility(View.GONE);
                        imagePlay.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                })
                .apply(options)
                .into(imageView);

        layout.setVisibility(View.VISIBLE);
        imagePlay.setImageDrawable(new IconicsDrawable(mContext, MaterialDesignIconic.Icon.gmi_play_circle)
                .color(Color.WHITE)
                .sizeDp(72)
                .respectFontBounds(true));

        imagePlay.setOnClickListener(v -> {
            progressBar.setVisibility(View.VISIBLE);
            mediaPlayer.initPlayer(Uri.parse(videoPreviewUrl));
            layout.setVisibility(View.VISIBLE);
        });

    }

    public void youtubeVideoFirstFrame(FrameLayout layout, ImageView imageView, ImageView imagePlay, ProgressBar
            progressBar,
                                       String thumbnailUrl,
                                       String videoUrl, String title) {

        imagePlay.setVisibility(View.VISIBLE);
        imageView.setVisibility(View.VISIBLE);

        Glide.with(mContext)
                .load(thumbnailUrl)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        imagePlay.setVisibility(View.GONE);
                        progressBar.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                })
                .apply(options)
                .into(imageView);

        layout.setVisibility(View.VISIBLE);

        imagePlay.setImageDrawable(new IconicsDrawable(mContext, MaterialDesignIconic.Icon.gmi_play_circle)
                .color(Color.WHITE)
                .sizeDp(72)
                .respectFontBounds(true));

        imagePlay.setOnClickListener(v -> {
            progressBar.setVisibility(View.VISIBLE);
            mContext.startActivity(new Intent(mContext,
                    YoutubeActivity.class)
                    .putExtra(Costant.EXTRA_YOUTUBE_PARAM, videoUrl)
                    .putExtra(Costant.EXTRA_YOUTUBE_TITLE, title)
            );
            layout.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
        });

    }


    public void imageReddit(ImageView imageView, String imagePreviewUrl, String contentDescription) {

        imageView.setVisibility(View.VISIBLE);

        Glide.with(mContext)
                .load(imagePreviewUrl)
                .apply(options)
                .into(imageView);

        if (!TextUtils.isEmpty(contentDescription)) {
            imageView.setContentDescription(contentDescription);

        }

    }


}
