/*
 *
 * ______                _____ _
 * | ___ \              /  ___| |
 * | |_/ /___ __ _ _ __ \ `--.| |_ ___  _ __   ___
 * |    // __/ _` | '_ \ `--. \ __/ _ \| '_ \ / _ \
 * | |\ \ (_| (_| | |_) /\__/ / || (_) | | | |  __/
 * \_| \_\___\__,_| .__/\____/ \__\___/|_| |_|\___|
 *                | |
 *                |_|
 *
 * Copyright (C) 2018 Benedetto Pellerito
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package info.pelleritoudacity.android.rcapstone.utility;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;

import java.util.Objects;

import info.pelleritoudacity.android.rcapstone.ui.activity.DetailActivity;
import info.pelleritoudacity.android.rcapstone.ui.activity.MainActivity;
import info.pelleritoudacity.android.rcapstone.ui.activity.ManageActivity;
import info.pelleritoudacity.android.rcapstone.ui.activity.YoutubeActivity;

public class ActivityUI {

    public static void leanBackUI(AppCompatActivity appCompatActivity) {
        if (appCompatActivity != null) {
            if (isPortraitOrientation(appCompatActivity)) {
                appCompatActivity.getWindow().getDecorView().setSystemUiVisibility(
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

            } else {
                appCompatActivity.getWindow().getDecorView().setSystemUiVisibility(
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                                | View.SYSTEM_UI_FLAG_FULLSCREEN
                                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                                | View.SYSTEM_UI_FLAG_IMMERSIVE);

            }

        }

    }

    public static void hideActionBar(AppCompatActivity appCompatActivity) {
        if (appCompatActivity != null) {
            Objects.requireNonNull(appCompatActivity.getSupportActionBar()).hide();
        }
    }

    public static void windowFullScreen(AppCompatActivity appCompatActivity) {
        if (appCompatActivity != null) {
            appCompatActivity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        }
    }

    public static void youtubeFullScreen(YoutubeActivity appCompatActivity) {
        if (appCompatActivity != null) {
            appCompatActivity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        }
    }

    public static boolean isLandscapeOrientation(Context context) {
        return context != null && context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
    }

    private static boolean isPortraitOrientation(Context context) {
        return context != null && context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;
    }

    public static boolean isTablet(Context context) {
        return context != null && (context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

    public static void startRefresh(Context context, Class clazz) {

        if ((clazz == null) || context == null) return;

        Intent intentMain = new Intent(context, clazz);

        if (clazz.equals(MainActivity.class)) {
            intentMain.putExtra(Costant.EXTRA_ACTIVITY_REDDIT_REFRESH, true);

        } else if (clazz.equals(DetailActivity.class)) {
            intentMain.putExtra(Costant.EXTRA_ACTIVITY_DETAIL_REFRESH, true);

        }else if(clazz.equals(ManageActivity.class)){
            intentMain.putExtra(Costant.EXTRA_ACTIVITY_MANAGE_REFRESH, true);

        }

        intentMain.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NO_ANIMATION | Intent.FLAG_ACTIVITY_NO_HISTORY);
        context.startActivity(intentMain);

    }


}
