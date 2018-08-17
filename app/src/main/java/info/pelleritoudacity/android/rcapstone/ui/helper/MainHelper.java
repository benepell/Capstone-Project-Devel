package info.pelleritoudacity.android.rcapstone.ui.helper;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.material_design_iconic_typeface_library.MaterialDesignIconic;

import java.nio.charset.StandardCharsets;

import info.pelleritoudacity.android.rcapstone.R;
import info.pelleritoudacity.android.rcapstone.media.MediaPlayer;
import info.pelleritoudacity.android.rcapstone.ui.activity.YoutubeActivity;
import info.pelleritoudacity.android.rcapstone.utility.Costant;


public class MainHelper {

    private final Context mContext;

    public MainHelper(Context context) {
        mContext = context;
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


    public void loadVideoFirstFrame(MediaPlayer mediaPlayer, FrameLayout layout, ImageView imageView, ProgressBar progressBar,
                                    String videoPreviewUrl, int videoPreviewWidth, int videoPreviewHeight) {
        Glide.with(mContext)
                .asBitmap()
                .load(videoPreviewUrl)
                .into(new SimpleTarget<Bitmap>(videoPreviewWidth, videoPreviewHeight) {

                    @Override
                    public void onLoadFailed(@Nullable Drawable errorDrawable) {
                        super.onLoadFailed(errorDrawable);
                        layout.setBackgroundResource(R.drawable.logo);
                    }

                    @Override
                    public void onLoadStarted(@Nullable Drawable placeholder) {
                        super.onLoadStarted(placeholder);
                        layout.setBackgroundResource(R.drawable.logo);
                    }

                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, Transition<? super Bitmap> transition) {
                        layout.setBackground(new BitmapDrawable(mContext.getResources(), resource));
                        layout.setVisibility(View.VISIBLE);

                        imageView.setImageDrawable(new IconicsDrawable(mContext, MaterialDesignIconic.Icon.gmi_play_circle)
                                .color(Color.WHITE)
                                .sizeDp(72)
                                .respectFontBounds(true));

                        imageView.setOnClickListener(v -> {
                            progressBar.setVisibility(View.VISIBLE);
                            mediaPlayer.initPlayer(Uri.parse(videoPreviewUrl));
                            layout.setVisibility(View.VISIBLE);
                        });

                    }

                });

    }

    public void youtubeVideoFirstFrame(FrameLayout layout, ImageView imageView, ProgressBar progressBar,
                                       String thumbnailUrl, int thumbnailWidth, int thumbnailHeight,
                                       String videoUrl,  String title) {
        Glide.with(mContext)
                .asBitmap()
                .load(thumbnailUrl)
                .into(new SimpleTarget<Bitmap>(thumbnailWidth, thumbnailHeight) {

                    @Override
                    public void onLoadFailed(@Nullable Drawable errorDrawable) {
                        super.onLoadFailed(errorDrawable);
                        layout.setBackgroundResource(R.drawable.logo);
                    }

                    @Override
                    public void onLoadStarted(@Nullable Drawable placeholder) {
                        super.onLoadStarted(placeholder);
                        layout.setBackgroundResource(R.drawable.logo);
                    }

                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, Transition<? super Bitmap> transition) {
                        layout.setBackground(new BitmapDrawable(mContext.getResources(), resource));
                        layout.setVisibility(View.VISIBLE);

                        imageView.setImageDrawable(new IconicsDrawable(mContext, MaterialDesignIconic.Icon.gmi_play_circle)
                                .color(Color.WHITE)
                                .sizeDp(72)
                                .respectFontBounds(true));

                        imageView.setOnClickListener(v -> {
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

                });

    }

    public void imageReddit(ImageView imageView,  String imagePreviewUrl,
                            int imagePreviewWidth, int imagePreviewHeight, String contentDescription) {
        Glide.with(mContext)
                .asBitmap()
                .load(imagePreviewUrl)
                .into(new SimpleTarget<Bitmap>(imagePreviewWidth, imagePreviewHeight) {

                    @Override
                    public void onLoadFailed(@Nullable Drawable errorDrawable) {
                        super.onLoadFailed(errorDrawable);
                        imageView.setImageResource(R.drawable.logo);


                    }

                    @Override
                    public void onLoadStarted(@Nullable Drawable placeholder) {
                        super.onLoadStarted(placeholder);
                        imageView.setImageResource(R.drawable.logo);

                    }

                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, Transition<? super Bitmap> transition) {
                            imageView.setImageBitmap(resource);
                            imageView.setContentDescription(contentDescription);


                    }
                });
    }




}
