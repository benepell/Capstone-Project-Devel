package info.pelleritoudacity.android.rcapstone.ui.helper;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.google.android.exoplayer2.util.Util;

import info.pelleritoudacity.android.rcapstone.data.model.ui.MainModel;
import info.pelleritoudacity.android.rcapstone.utility.Costant;
import info.pelleritoudacity.android.rcapstone.utility.Preference;
import info.pelleritoudacity.android.rcapstone.utility.TextUtil;

public class MenuLauncherDetail {

    private final Context mContext;
    private final Intent mIntent;

    private String category;
    private String target;

    public MenuLauncherDetail(Context context, Intent intent) {
        mContext = context;
        mIntent = intent;
    }

    public void showMenu() {

        if (menuShortcutLauncher(mIntent)) {
            saveLastPreference();
            return;
        }

        if (menuTargetLink(mIntent)) {
            saveLastPreference();
            return;
        }

        if(menuHistoryLink()){
            return;
        }else {
            menuDefaultValue();
            saveLastPreference();
            return;

        }
    }


    private boolean menuDefaultValue(){
        setCategory(TextUtil.stringToArray(Costant.DEFAULT_SUBREDDIT_CATEGORY).get(0));
        setTarget(Costant.SUBREDDIT_TARGET_DEFAULT_START_VALUE);
        return true;
    }

    private boolean menuHistoryLink() {
        if (!TextUtils.isEmpty(Preference.getLastCategory(mContext)) &&
                (!TextUtils.isEmpty(Preference.getLastTarget(mContext)))) {
            setCategory(Preference.getLastCategory(mContext));
            setTarget(Preference.getLastTarget(mContext));
            return true;
        }
        return false;
    }


    private boolean menuShortcutLauncher(Intent intent) {
        if ((Util.SDK_INT > 24) && (intent != null) && (intent.getAction() != null)) {

            switch (intent.getAction()) {

                case Costant.ACTION_SHORTCUT_ALL:
                    setCategory(Costant.SUBREDDIT_CATEGORY_ALL);
                    setTarget(Costant.SUBREDDIT_TARGET_ALL);
                    return true;

                case Costant.ACTION_SHORTCUT_POPULAR:
                    setCategory(Costant.SUBREDDIT_CATEGORY_ALL);
                    setTarget(Costant.SUBREDDIT_TARGET_ALL);
                    return true;

                case Costant.ACTION_SHORTCUT_FAVORITE:
                    setCategory(Costant.SUBREDDIT_CATEGORY_FAVORITE);
                    setTarget(Costant.SUBREDDIT_TARGET_FAVORITE);
                    return true;
            }

        }
        return false;
    }


    private boolean menuTargetLink(Intent intent) {

        if ((!TextUtils.isEmpty(intent.getStringExtra(Costant.EXTRA_SUBREDDIT_TARGET)) &&
                (!TextUtils.isEmpty(intent.getStringExtra(Costant.EXTRA_SUBREDDIT_CATEGORY))))) {

            setCategory(intent.getStringExtra(Costant.EXTRA_SUBREDDIT_CATEGORY));
            setTarget(intent.getStringExtra(Costant.EXTRA_SUBREDDIT_TARGET));

            return true;
        }
        return false;
    }


    public void saveLastPreference() {
        Preference.setLastCategory(mContext, getCategory());
        Preference.setLastTarget(mContext, getTarget());
    }

    private String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    private String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

}
