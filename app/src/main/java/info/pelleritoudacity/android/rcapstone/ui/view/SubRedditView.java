package info.pelleritoudacity.android.rcapstone.ui.view;

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
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.material_design_iconic_typeface_library.MaterialDesignIconic;

import java.nio.charset.StandardCharsets;
import java.util.List;

import info.pelleritoudacity.android.rcapstone.R;
import info.pelleritoudacity.android.rcapstone.data.DataUtils;
import info.pelleritoudacity.android.rcapstone.media.MediaPlayer;
import info.pelleritoudacity.android.rcapstone.model.reddit.T1;
import info.pelleritoudacity.android.rcapstone.rest.CommentsExecute;
import info.pelleritoudacity.android.rcapstone.rest.VoteExecute;
import info.pelleritoudacity.android.rcapstone.ui.activity.MediaYoutubeActivity;
import info.pelleritoudacity.android.rcapstone.utility.Costants;
import info.pelleritoudacity.android.rcapstone.utility.ImageUtils;
import info.pelleritoudacity.android.rcapstone.utility.PermissionUtils;

import static info.pelleritoudacity.android.rcapstone.utility.ImageUtils.isSmallImage;

public class SubRedditView {

    private final Context mContext;

    public SubRedditView(Context context) {
        mContext = context;
    }

    @SuppressLint("SetJavaScriptEnabled")
    public void loadWebviewYoutube(WebView view, String videoFrameYoutube) {

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

    public void vimeoVideoFirstFrame(MediaPlayer mediaPlayer, FrameLayout layout, ImageView imageView, ProgressBar progressBar,
                                     String imageVimeoUrl, int imageVimeoWidth, int imageVimeoHeight, String videoVimeoUrl) {
        Glide.with(mContext)
                .asBitmap()
                .load(imageVimeoUrl)
                .into(new SimpleTarget<Bitmap>(imageVimeoWidth, imageVimeoHeight) {

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
                            mediaPlayer.initPlayer(Uri.parse(videoVimeoUrl));
                            layout.setVisibility(View.VISIBLE);
                        });

                    }

                });

    }

    public void youtubeVideoFirstFrame(FrameLayout layout, ImageView imageView, ProgressBar progressBar,
                                       String thumbnailUrl, int thumbnailWidth, int thumbnailHeight,
                                       String videoUrl, int videoWidth, int videoHeight) {
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
                                    MediaYoutubeActivity.class).putExtra(Costants.EXTRA_YOUTUBE_PARAM, videoUrl)

                            );
                            layout.setVisibility(View.VISIBLE);
                            progressBar.setVisibility(View.GONE);
                        });

                    }

                });

    }

    public void imageReddit(ImageView imageView, ImageView imageViewSmall, String imagePreviewUrl,
                            int imagePreviewWidth, int imagePreviewHeight, String contentDescription) {
        Glide.with(mContext)
                .asBitmap()
                .load(imagePreviewUrl)
                .into(new SimpleTarget<Bitmap>(imagePreviewWidth, imagePreviewHeight) {

                    @Override
                    public void onLoadFailed(@Nullable Drawable errorDrawable) {
                        super.onLoadFailed(errorDrawable);
                        imageViewSmall.setImageResource(R.drawable.logo);


                    }

                    @Override
                    public void onLoadStarted(@Nullable Drawable placeholder) {
                        super.onLoadStarted(placeholder);
                        imageViewSmall.setImageResource(R.drawable.logo);

                    }

                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, Transition<? super Bitmap> transition) {
                        if (isSmallImage(mContext, imagePreviewWidth, imagePreviewHeight)) {
                            ImageUtils.createRoundImage(mContext, imageViewSmall, resource);
                            imageViewSmall.setContentDescription(contentDescription);

                        } else {
                            imageView.setImageBitmap(resource);
                            imageView.setContentDescription(contentDescription);

                        }

                    }


                });

    }

    public void cardBottomLink(ImageButton[] arrayButton, String linkComments, String nameReddit, String subRedditName, String nameRedditId) {

        if ((arrayButton != null) && (arrayButton.length == 5)) {

            ImageButton buttonVoteUp = arrayButton[0];
            ImageButton buttonVoteDown = arrayButton[1];
            ImageButton buttonStars = arrayButton[2];
            ImageButton buttonComments = arrayButton[3];
            ImageButton buttonOpenBrowser = arrayButton[4];

            buttonVoteUp.setBackgroundColor(Color.WHITE);
            buttonVoteUp.setImageDrawable(new IconicsDrawable(mContext, MaterialDesignIconic.Icon.gmi_thumb_up)
                    .color(Color.GRAY)
                    .sizeDp(mContext.getResources().getInteger(R.integer.icon_card_bottom))
                    .respectFontBounds(true));

            buttonVoteUp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (PermissionUtils.isLogged(mContext)) {
                        String vote = "1";

                        if (view.isActivated()) {
                            vote = "0";
                        }

                        String finalVote = vote;
                        new VoteExecute(PermissionUtils.getToken(mContext), vote, nameReddit)
                                .postData(new VoteExecute.RestAccessToken() {
                                    @Override
                                    public void onRestVote(int responseCode) {
                                        if (responseCode == 200) {
                                            buttonVoteDown.setColorFilter(Color.GRAY);
                                            buttonVoteUp.setActivated(true);
                                            if (finalVote.equals("1")) {
                                                DataUtils dataUtils = new DataUtils(mContext);
                                                buttonVoteUp.setColorFilter(Color.BLUE);


                                            } else {
                                                buttonVoteUp.setActivated(false);
                                                buttonVoteUp.setColorFilter(Color.GRAY);

                                            }
                                        }
                                    }

                                    @Override
                                    public void onErrorVote(Throwable t) {

                                    }
                                });


                    } else {
                        Toast.makeText(mContext, "Please Login ", Toast.LENGTH_LONG).show();
                    }
                }
            });

            buttonVoteDown.setBackgroundColor(Color.WHITE);
            buttonVoteDown.setImageDrawable(new IconicsDrawable(mContext, MaterialDesignIconic.Icon.gmi_thumb_down)
                    .color(Color.GRAY)
                    .sizeDp(mContext.getResources().getInteger(R.integer.icon_card_bottom))
                    .respectFontBounds(true));

            buttonVoteDown.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (PermissionUtils.isLogged(mContext)) {
                        String vote = "-1";

                        if (view.isActivated()) {
                            vote = "0";
                        }

                        String finalVote = vote;
                        new VoteExecute(PermissionUtils.getToken(mContext), vote, nameReddit)
                                .postData(new VoteExecute.RestAccessToken() {
                                    @Override
                                    public void onRestVote(int responseCode) {
                                        if (responseCode == 200) {
                                            buttonVoteUp.setColorFilter(Color.GRAY);
                                            buttonVoteDown.setActivated(true);
                                            if (finalVote.equals("-1")) {
                                                DataUtils dataUtils = new DataUtils(mContext);
                                                buttonVoteDown.setColorFilter(Color.BLUE);
                                            } else {
                                                buttonVoteDown.setActivated(false);
                                                buttonVoteDown.setColorFilter(Color.GRAY);

                                            }
                                        }
                                    }

                                    @Override
                                    public void onErrorVote(Throwable t) {

                                    }
                                });

                    } else {
                        Toast.makeText(mContext, "Please Login ", Toast.LENGTH_LONG).show();
                    }
                }
            });

            buttonStars.setImageDrawable(new IconicsDrawable(mContext, MaterialDesignIconic.Icon.gmi_star_border)
                    .color(Color.GRAY)
                    .sizeDp(mContext.getResources().getInteger(R.integer.icon_card_bottom))
                    .respectFontBounds(true));

            buttonStars.setBackgroundColor(Color.WHITE);

            buttonComments.setImageDrawable(new IconicsDrawable(mContext, MaterialDesignIconic.Icon.gmi_comment_outline)
                    .color(Color.GRAY)
                    .sizeDp(mContext.getResources().getInteger(R.integer.icon_card_bottom))
                    .respectFontBounds(true));

            buttonComments.setBackgroundColor(Color.WHITE);

            buttonComments.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new CommentsExecute(mContext, PermissionUtils.getToken(mContext),
                            subRedditName, nameRedditId).getData(new CommentsExecute.RestSubReddit() {

                        @Override
                        public void onRestSubReddit(List<T1> listenerData) {

                        }

                        @Override
                        public void onErrorSubReddit(Throwable t) {

                        }
                    });
                }
            });

            buttonOpenBrowser.setImageDrawable(new IconicsDrawable(mContext, MaterialDesignIconic.Icon.gmi_open_in_browser)
                    .color(Color.GRAY)
                    .sizeDp(mContext.getResources().getInteger(R.integer.icon_card_bottom))
                    .respectFontBounds(true));

            buttonOpenBrowser.setBackgroundColor(Color.WHITE);

            if (!TextUtils.isEmpty(linkComments)) {
                buttonOpenBrowser.setOnClickListener(view -> mContext.startActivity(new Intent(
                        Intent.ACTION_VIEW, Uri.parse(linkComments))
                        .setFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT)));
            }
        }
    }

}
