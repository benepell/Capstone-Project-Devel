package info.pelleritoudacity.android.rcapstone.ui.helper;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import info.pelleritoudacity.android.rcapstone.R;
import info.pelleritoudacity.android.rcapstone.utility.ImageUtil;
import info.pelleritoudacity.android.rcapstone.utility.TextUtil;

public class TitleDetailHelper {

    private final Context mContext;

    public TitleDetailHelper(Context mContext) {
        this.mContext = mContext;
    }

    public void imageReddit(ImageView imageView, String image, String imageDescription, int imgWidth, int imgHeight) {
        Glide.with(mContext)
                .asBitmap()
                .load(TextUtil.textFromHtml(image))
                .into(new SimpleTarget<Bitmap>(imgWidth, imgHeight) {

                    @Override
                    public void onLoadFailed(@Nullable Drawable errorDrawable) {
                        super.onLoadFailed(errorDrawable);
                        imageView.setImageResource(R.drawable.no_image);

                    }

                    @Override
                    public void onLoadStarted(@Nullable Drawable placeholder) {
                        super.onLoadStarted(placeholder);
                        imageView.setImageResource(R.drawable.logo);

                    }

                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, Transition<? super Bitmap> transition) {
                        ImageUtil.createRoundImage(mContext, imageView, resource);
                        imageView.setContentDescription(imageDescription);
                        imageView.setVisibility(View.VISIBLE);

                    }
                });

    }
}
