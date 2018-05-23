package info.pelleritoudacity.android.rcapstone.utility;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Build;
import android.support.v4.content.res.ResourcesCompat;
import android.util.DisplayMetrics;

import java.util.Objects;

public class ImageUtils {

    public static boolean isSmallImage(Context context, int widthPixel, int heightPixel) {
        if ((context != null) || (widthPixel != 0) || (heightPixel != 0)) {
            int dpW = widthPixel / (int) Objects.requireNonNull(context).getResources().getDisplayMetrics().density;
            int dpH = heightPixel / (int) context.getResources().getDisplayMetrics().density;

            DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();

            float dpHeight = displayMetrics.heightPixels / displayMetrics.density;
            float dpWidth = displayMetrics.widthPixels / displayMetrics.density;

            if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                return (dpWidth * 0.3) > dpW;

            } else {
                return (dpHeight * 0.3) > dpH;

            }
        }
        return false;
    }

    public static int getColor(Context ctx, int colorResource) {
        if (Build.VERSION.SDK_INT >= 23) {
            return ctx.getColor(colorResource);
        } else {
            return ResourcesCompat.getColor(ctx.getResources(), colorResource, ctx.getTheme());
        }
    }

}
