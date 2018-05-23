package info.pelleritoudacity.android.rcapstone.utility;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.view.View;

public class ActivityUI {

    public static void leanBackUI(Activity activity) {
        if (activity != null) {
            activity.getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE);

        }
    }

    public static boolean isLandscapeOrientation(Context context) {
        return context != null ? context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE : false;
    }

    public static boolean isPortraitOrientation(Context context) {
        return context != null ? context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT : false;
    }

    public static boolean isTablet(Context context) {
        return context != null && (context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }


}
