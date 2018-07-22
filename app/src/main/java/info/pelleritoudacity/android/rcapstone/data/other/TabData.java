package info.pelleritoudacity.android.rcapstone.data.other;

import android.content.Context;
import android.text.TextUtils;

import java.util.ArrayList;

import info.pelleritoudacity.android.rcapstone.data.db.Contract;
import info.pelleritoudacity.android.rcapstone.utility.Costant;
import info.pelleritoudacity.android.rcapstone.utility.Preference;

import static info.pelleritoudacity.android.rcapstone.utility.TextUtil.stringToArray;

public class TabData {

    private final Context mContext;

    public TabData(Context context) {
        mContext = context;
    }

    public ArrayList<String> getTabArrayList() {

        String prefString = Preference.getSubredditKey(mContext);

        if (TextUtils.isEmpty(prefString)) {
            prefString = Costant.DEFAULT_SUBREDDIT_CATEGORY;
        }

        ArrayList<String> tabArrayList = stringToArray(prefString);


        return tabArrayList;
    }

}
