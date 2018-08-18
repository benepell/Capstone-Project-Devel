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
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.util.DisplayMetrics;

import info.pelleritoudacity.android.rcapstone.R;


public class Utility {

    private Utility() {
    }

    public static String appVersionName(Context context) throws PackageManager.NameNotFoundException {

        return context != null ? context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName : "";

    }

    public static int boolToInt(Boolean b) {
        return (b != null && b) ? 1 : 0;
    }

    public static int toInt(Object object) {

        if (object instanceof Integer) {

            return (Integer) object;

        }

        return 0;
    }

    public static boolean isTablet(Context context) {

        return context != null && (
                context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;

    }

    public static int calculateNoOfColumns(Context context) {

        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        float itemWidth = context.getResources().getDimension(R.dimen.recycler_view_item_width) / displayMetrics.density;
        int r = (int) (dpWidth / itemWidth);
        return r < 1 ? 1 : r;
    }


}
