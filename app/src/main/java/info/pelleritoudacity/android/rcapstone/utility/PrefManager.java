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
import android.content.SharedPreferences;
import android.support.v7.preference.PreferenceManager;

import info.pelleritoudacity.android.rcapstone.R;


public class PrefManager {

    @SuppressWarnings("unused")
    static boolean isGeneralSettings(Context context, String key) {
        PreferenceManager.setDefaultValues(context, R.xml.pref_general_settings, true);

        SharedPreferences sharedPref =
                PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPref.getBoolean
                (key, false);
    }

    static int getIntGeneralSettings(Context context, @SuppressWarnings("SameParameterValue") int key) {
        PreferenceManager.setDefaultValues(context, R.xml.pref_general_settings, true);

        SharedPreferences sharedPref =
                PreferenceManager.getDefaultSharedPreferences(context);
        return Integer.valueOf(
                sharedPref.getString(context.getString(key), "0"));
    }


    static void clearGeneralSettings(Context context) {
        PreferenceManager.setDefaultValues(context, R.xml.pref_general_settings, true);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        sharedPreferences.edit().clear().apply();
    }

    static void putIntPref(Context context, @SuppressWarnings("SameParameterValue") int key, int value) {
        SharedPreferences prefId = context
                .getSharedPreferences(context.getString(key), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefId.edit();
        editor.putInt(context.getString(key), value);
        editor.apply();

    }

    static void putStringPref(Context context, int key, String value) {
        SharedPreferences prefId = context
                .getSharedPreferences(context.getString(key), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefId.edit();
        editor.putString(context.getString(key), value);
        editor.apply();

    }

    static void putBoolPref(Context context, int key, @SuppressWarnings("SameParameterValue") boolean value) {
        SharedPreferences prefId = context
                .getSharedPreferences(context.getString(key), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefId.edit();
        editor.putBoolean(context.getString(key), value);
        editor.apply();

    }

    static int getIntPref(Context context, @SuppressWarnings("SameParameterValue") int key) {
        SharedPreferences sharedPreferences;
        sharedPreferences = context.getSharedPreferences(context.getString(key), Context.MODE_PRIVATE);
        return sharedPreferences.getInt(context.getString(key), 0);
    }

    static long getLongPref(Context context, @SuppressWarnings("SameParameterValue") int key) {
        SharedPreferences sharedPreferences;
        sharedPreferences = context.getSharedPreferences(context.getString(key), Context.MODE_PRIVATE);
        return sharedPreferences.getLong(context.getString(key), 0);
    }

    static boolean getBoolPref(Context context, @SuppressWarnings("SameParameterValue") int key) {
        SharedPreferences sharedPreferences;
        sharedPreferences = context.getSharedPreferences(context.getString(key), Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(context.getString(key), false);
    }

    static String getStringPref(Context context, int key) {
        SharedPreferences sharedPreferences;
        sharedPreferences = context.getSharedPreferences(context.getString(key), Context.MODE_PRIVATE);
        return sharedPreferences.getString(context.getString(key), "");
    }



    static void clearPref(Context context, @SuppressWarnings("SameParameterValue") int[] prefArray) {
        if ((context != null) && (prefArray != null)) {
            for (int pref : prefArray) {
                context.getSharedPreferences(context.getString(pref), 0).edit().clear().apply();
            }

        }
    }

    public static void clearPreferenceLogin(Context context) {
        int[] prefStrArrays = {
                R.string.pref_login_start,
                R.string.pref_session_expired,
                R.string.pref_time_token,
                R.string.pref_session_access_token,
                R.string.pref_login_name,
                R.string.pref_login_over18
        };

        for (int pref : prefStrArrays) {
            context.getSharedPreferences(context.getString(pref), 0).edit().clear().apply();
        }

    }


}

