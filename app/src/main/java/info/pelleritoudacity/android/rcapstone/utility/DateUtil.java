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
import android.text.format.DateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import info.pelleritoudacity.android.rcapstone.R;

import static info.pelleritoudacity.android.rcapstone.utility.Costant.DOT;

public class DateUtil {

    public static int getSecondsTimeStamp(String timestamp) {

        Date date;
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(
                    "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
            date = dateFormat.parse(timestamp);

            long diffTime = System.currentTimeMillis() - date.getTime();

            return (int) TimeUnit.MILLISECONDS.toSeconds(diffTime);
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }

    }

    public static String getNowTimeStamp() {

        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());

        return dateFormat.format(System.currentTimeMillis());

    }

    public static String getDiffTimeMinute(Context context, long time) {

        long timeDiff = (System.currentTimeMillis() / 1000) - time;

        if (timeDiff < Costant.TIME_APPROX_NOW) {
            return context.getResources().getString(R.string.text_time_now);
        }

        return DateUtils.getRelativeTimeSpanString(time, System.currentTimeMillis() / 1000,
                DateUtils.MINUTE_IN_MILLIS,
                DateUtils.FORMAT_ABBREV_TIME).toString();

    }

    public static String getTimeFormat(long timemillis) {

        SimpleDateFormat simpleDateFormat;
        Locale locale = Locale.getDefault();
        if (timemillis > 3600000) {
            simpleDateFormat = new SimpleDateFormat(Costant.PATTERN_HH_MM_SS, locale);
        } else {
            simpleDateFormat = new SimpleDateFormat(Costant.PATTERN_MM_SS, locale);
        }

        return simpleDateFormat.format(timemillis);

    }
    public static String timeFormat(String timeDoubleString) {
        String dateString;
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        long currentTime = calendar.getTimeInMillis() / 1000L;
        double pastTime = Double.valueOf(timeDoubleString);
        long secAgo = currentTime - ((long) pastTime);
        if (secAgo >= 31536000) {
            int date = (int) secAgo / 31536000;
            dateString = DOT + String.valueOf(date);
            dateString += (date == 1) ? "yr" : "yrs";
        } else if (secAgo >= 2592000) {
            int date = (int) secAgo / 2592000;
            dateString = DOT + String.valueOf(date) + "m";
        } else if (secAgo >= 604800) {
            int date = (int) secAgo / 604800;
            dateString = DOT + String.valueOf(date) + "wk";
        } else if (secAgo >= 86400) {
            int date = (int) secAgo / 86400;
            dateString = DOT + String.valueOf(date);
            dateString += (date == 1) ? "day" : "days";
        } else if (secAgo >= 3600) {
            int date = (int) secAgo / 3600;
            dateString = DOT + String.valueOf(date);
            dateString += (date == 1) ? "hr" : "hrs";
        } else if (secAgo >= 60) {
            int date = (int) secAgo / 60;
            dateString = DOT + String.valueOf(date) + "min";
        } else {
            dateString = DOT + String.valueOf(secAgo) + "sec";
        }
        return dateString;
    }

}
