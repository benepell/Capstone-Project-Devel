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

import java.util.ArrayList;
import java.util.LinkedHashSet;

import info.pelleritoudacity.android.rcapstone.R;
import timber.log.Timber;

public class MapUtils {

    public static void addElementPrefSubreddit(Context context, String e) {
        String string = Preference.getSubredditKey(context);
        ArrayList<String> arrayList;
        arrayList = TextUtil.stringToArray(string);
        arrayList.add(e);
        ArrayList<String> arrayListNoDup;
        arrayListNoDup = new ArrayList<>((new LinkedHashSet<>(arrayList)));
        string = TextUtil.arrayToString(arrayListNoDup);
        context.getSharedPreferences(context.getString(R.string.pref_subreddit_key), 0).edit().clear().apply();
        Preference.setSubredditKey(context,string);
    }

    public static void removeElementPrefSubreddit(Context context, String e) {
        String string = Preference.getSubredditKey(context);
        ArrayList<String> arrayList;
        arrayList = TextUtil.stringToArray(string);
        arrayList.remove(e);
        for (String str : arrayList) {
            Timber.d("element: %s", str);
        }
        ArrayList<String> arrayListNoDup;
        arrayListNoDup = new ArrayList<>((new LinkedHashSet<>(arrayList)));
        string = TextUtil.arrayToString(arrayListNoDup);
        context.getSharedPreferences(context.getString(R.string.pref_subreddit_key), 0).edit().clear().apply();
        Preference.setSubredditKey(context,string);

    }


    public static ArrayList<String> removeArrayListDuplicate(ArrayList<String> arrayList) {
        ArrayList<String> arrayListNoDup;
        arrayListNoDup = new ArrayList<>((new LinkedHashSet<>(arrayList)));
        return arrayListNoDup;
    }

}
