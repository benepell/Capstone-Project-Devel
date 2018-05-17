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
import android.support.v4.content.res.ResourcesCompat;
import android.text.Html;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Display;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

import info.pelleritoudacity.android.rcapstone.R;
import timber.log.Timber;


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
            return ResourcesCompat.getColor(ctx.getResources(), colorResource, ctx.getTheme());
        }
    }

    public static int getRedditSessionExpired(Context context) {
        if (context != null) {
            Long now = System.currentTimeMillis();
            Long lastTimeLogin = PrefManager.getLongPref(context, R.string.pref_time_token);
            Long secondTime = (now - lastTimeLogin) / 1000;
            int expiredRedditAuth = PrefManager.getIntPref(context, R.string.pref_session_expired);
            int sessionTimeout = Costants.SESSION_TIMEOUT_DEFAULT;
            if ((now > 0) && (lastTimeLogin > 0) && (lastTimeLogin < now) && (expiredRedditAuth > 0)) {
                sessionTimeout = expiredRedditAuth - secondTime.intValue();
            }
            return (sessionTimeout < Costants.SESSION_TIMEOUT_DEFAULT) ? Costants.SESSION_TIMEOUT_DEFAULT : sessionTimeout;
        }
        return 0;
    }

    public static int getHourCurrentCreatedUtc(long createdUtc) {
        Long now = System.currentTimeMillis();
        Long diffTime = now / 1000 - createdUtc;
        return Math.round(diffTime / 3600);
    }

    public static int getSecondsTimeStamp(String timestamp) {

        Date date;
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(
                    "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
            date = dateFormat.parse(timestamp);
            Long diffTime = System.currentTimeMillis() - date.getTime();

            return (int) TimeUnit.MILLISECONDS.toSeconds(diffTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }


    public static String normalizeSubRedditLink(String link) {

        return (!TextUtils.isEmpty(link)) ?
                link.replace("/r/", "")
                        .replaceAll("[^a-zA-Z0-9]", "")
                        .trim() : "";


    }

    private static final NavigableMap<Long, String> suffixes = new TreeMap<>();

    static {
        suffixes.put(1_000L, "k");
        suffixes.put(1_000_000L, "M");
        suffixes.put(1_000_000_000L, "G");
        suffixes.put(1_000_000_000_000L, "T");
        suffixes.put(1_000_000_000_000_000L, "P");
        suffixes.put(1_000_000_000_000_000_000L, "E");
    }

    public static String numberFormat(long value) {
        if (value == Long.MIN_VALUE) return numberFormat(Long.MIN_VALUE + 1);
        if (value < 0) return "-" + numberFormat(-value);
        if (value < 1000) return Long.toString(value);

        Map.Entry<Long, String> e = suffixes.floorEntry(value);
        Long divideBy = e.getKey();
        String suffix = e.getValue();

        long truncated = value / (divideBy / 10);
        boolean hasDecimal = truncated < 100 && (truncated / 10d) != (truncated / 10);
        return hasDecimal ? (truncated / 10d) + suffix : (truncated / 10) + suffix;
    }

    public static int boolToInt(boolean b) {
        return b ? 1 : 0;
    }

    public static int toInt(Object object) {
        if (object instanceof Integer) {
            return (Integer) object;
        }
        return 0;
    }

    public static void addElementPrefSubreddit(Context context, String e) {
        String string = PrefManager.getStringPref(context, R.string.pref_subreddit_key);
        ArrayList<String> arrayList;
        arrayList = Utility.stringToArray(string);
        arrayList.add(e);
        ArrayList<String> arrayListNoDup;
        arrayListNoDup = new ArrayList<>((new LinkedHashSet<>(arrayList)));
        string = Utility.arrayToString(arrayListNoDup);
        context.getSharedPreferences(context.getString(R.string.pref_subreddit_key), 0).edit().clear().apply();
        PrefManager.putStringPref(context, R.string.pref_subreddit_key, string);
    }

    public static void removeElementPrefSubreddit(Context context, String e) {
        String string = PrefManager.getStringPref(context, R.string.pref_subreddit_key);
        ArrayList<String> arrayList;
        arrayList = Utility.stringToArray(string);
        arrayList.remove(e);
        for (String str : arrayList) {
            Timber.d("element: %s", str);
        }
        ArrayList<String> arrayListNoDup;
        arrayListNoDup = new ArrayList<>((new LinkedHashSet<>(arrayList)));
        string = Utility.arrayToString(arrayListNoDup);
        context.getSharedPreferences(context.getString(R.string.pref_subreddit_key), 0).edit().clear().apply();
        PrefManager.putStringPref(context, R.string.pref_subreddit_key, string);

    }


    public static ArrayList<String> removeArrayListDuplicate(ArrayList<String> arrayList) {
        ArrayList<String> arrayListNoDup;
        arrayListNoDup = new ArrayList<>((new LinkedHashSet<>(arrayList)));
        return arrayListNoDup;
    }

    public static String textFromHtml(String text) {

        if (TextUtils.isEmpty(text)) return "";

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return Html.fromHtml(text, Html.FROM_HTML_MODE_LEGACY).toString();
        } else {
            return Html.fromHtml(text).toString();
        }
    }


    public static boolean isSmallImage(Context context, int widthPixel, int heightPixel) {
        if ((context != null) || (widthPixel != 0) || (heightPixel != 0)) {
            int dpW = widthPixel / (int) context.getResources().getDisplayMetrics().density;
            int dpH = heightPixel / (int) context.getResources().getDisplayMetrics().density;

            DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();

            float density  = context.getResources().getDisplayMetrics().density;
            float dpHeight = displayMetrics.heightPixels / displayMetrics.density;
            float dpWidth = displayMetrics.widthPixels / displayMetrics.density;

            if(context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
                if ((dpWidth*0.3)>dpW) return true;

            }else {
                if ((dpHeight*0.5)>dpH) return true;

            }



        }


        return false;
    }
}
