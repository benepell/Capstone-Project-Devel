package info.pelleritoudacity.android.rcapstone.ui.helper;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.Request;
import com.bumptech.glide.request.target.SizeReadyCallback;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;

import info.pelleritoudacity.android.rcapstone.R;
import info.pelleritoudacity.android.rcapstone.utility.ImageUtil;
import info.pelleritoudacity.android.rcapstone.utility.TextUtil;

public class TitleDetailHelper {

    private final Context mContext;

    public TitleDetailHelper(Context mContext) {
        this.mContext = mContext;
    }

    public void imageReddit(ImageView imageView, String image, String imageDescription) {
        Glide.with(mContext)
                .asBitmap()
                .load(TextUtil.textFromHtml(image))
                .into(new Target<Bitmap>() {
                    @Override
                    public void onLoadStarted(@Nullable Drawable placeholder) {

                    }

                    @Override
                    public void onLoadFailed(@Nullable Drawable errorDrawable) {
                        imageView.setImageResource(R.drawable.ic_no_image);

                    }

                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        ImageUtil.createRoundImage(mContext, imageView, resource);
                        imageView.setContentDescription(imageDescription);
                        imageView.setVisibility(View.VISIBLE);

                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {

                    }

                    @Override
                    public void getSize(@NonNull SizeReadyCallback cb) {

                    }

                    @Override
                    public void removeCallback(@NonNull SizeReadyCallback cb) {

                    }

                    @Override
                    public void setRequest(@Nullable Request request) {

                    }

                    @Nullable
                    @Override
                    public Request getRequest() {
                        return null;
                    }

                    @Override
                    public void onStart() {

                    }

                    @Override
                    public void onStop() {

                    }

                    @Override
                    public void onDestroy() {

                    }
                });
    }
}
