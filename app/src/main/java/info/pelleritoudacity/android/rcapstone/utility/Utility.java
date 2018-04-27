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

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;
import java.util.Collections;

import info.pelleritoudacity.android.rcapstone.R;


public class Utility {

    private Utility() {
    }

    public static boolean isOnline(Context context) {
        ConnectivityManager connMgr = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connMgr != null) {
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
            return (networkInfo != null) && (networkInfo.getState() == NetworkInfo.State.CONNECTED);
        }
        return false;
    }

    public static boolean isTablet(Context context) {
        return context != null && (context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

    public static String appVersionName(Context context) throws PackageManager.NameNotFoundException {
        return context != null ? context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName : "";
    }

    public static boolean isPermissionExtStorage(Context context) {
        SharedPreferences pref = context.getSharedPreferences(context.getString(R.string.pref_write_external_storage), 0);
        return pref.getBoolean(context.getString(R.string.pref_write_external_storage), false);
    }

    public static void RequestPermissionExtStorage(Activity thisActivity) {
        if (ContextCompat.checkSelfPermission(thisActivity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            if (!ActivityCompat.shouldShowRequestPermissionRationale(thisActivity,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                ActivityCompat.requestPermissions(thisActivity,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        Costants.PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
            }

        }
    }

    public static String arrayToString(ArrayList<String> arrayList) {
        if (arrayList != null) {
            StringBuilder builder = new StringBuilder();
            for (String s : arrayList) {
                builder.append(s).append(Costants.STRING_SEPARATOR);
            }
            String str = builder.toString();

            if (str.length() > 0) {
                str = str.substring(0, str.length() - 1);
            }
            return str;
        }
        return "";
    }

    public static ArrayList<String> stringToArray(String string) {
        ArrayList<String> arrayList = new ArrayList<>();
        Collections.addAll(arrayList, string.split(Costants.STRING_SEPARATOR));
        return arrayList;
    }

    public static int getColor(Context ctx, int colorResource) {
        if (Build.VERSION.SDK_INT >= 23) {
            return ctx.getColor(colorResource);
        } else {
            return ctx.getResources().getColor(colorResource, ctx.getTheme());
        }
    }

    public static int getRedditSessionExpired(Context context) {
        if (context != null) {
            Long now = System.currentTimeMillis();
            Long lastTimeLogin = PrefManager.getLongPref(context, R.string.pref_time_logged);
            int expiredRedditAuth = PrefManager.getIntPref(context, R.string.pref_session_expired);
            int sessionTimeout = Costants.SESSION_TIMEOUT_DEFAULT;
            if ((now > 0) && (lastTimeLogin > 0) && (lastTimeLogin < now) && (expiredRedditAuth > 0)) {
                sessionTimeout = (int) (expiredRedditAuth - ((now - lastTimeLogin) / 1000));
            }
            return (sessionTimeout < Costants.SESSION_TIMEOUT_DEFAULT) ? Costants.SESSION_TIMEOUT_DEFAULT : sessionTimeout;
        }
        return 0;
    }
}
