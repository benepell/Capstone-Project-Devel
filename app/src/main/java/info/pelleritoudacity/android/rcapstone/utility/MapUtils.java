package info.pelleritoudacity.android.rcapstone.utility;

import android.content.Context;

import java.util.ArrayList;
import java.util.LinkedHashSet;

import info.pelleritoudacity.android.rcapstone.R;
import timber.log.Timber;

public class MapUtils {

    public static void addElementPrefSubreddit(Context context, String e) {
        String string = PrefManager.getStringPref(context, R.string.pref_subreddit_key);
        ArrayList<String> arrayList;
        arrayList = TextUtil.stringToArray(string);
        arrayList.add(e);
        ArrayList<String> arrayListNoDup;
        arrayListNoDup = new ArrayList<>((new LinkedHashSet<>(arrayList)));
        string = TextUtil.arrayToString(arrayListNoDup);
        context.getSharedPreferences(context.getString(R.string.pref_subreddit_key), 0).edit().clear().apply();
        PrefManager.putStringPref(context, R.string.pref_subreddit_key, string);
    }

    public static void removeElementPrefSubreddit(Context context, String e) {
        String string = PrefManager.getStringPref(context, R.string.pref_subreddit_key);
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
        PrefManager.putStringPref(context, R.string.pref_subreddit_key, string);

    }


    public static ArrayList<String> removeArrayListDuplicate(ArrayList<String> arrayList) {
        ArrayList<String> arrayListNoDup;
        arrayListNoDup = new ArrayList<>((new LinkedHashSet<>(arrayList)));
        return arrayListNoDup;
    }

}
