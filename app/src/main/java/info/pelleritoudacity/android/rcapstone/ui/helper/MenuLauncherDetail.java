package info.pelleritoudacity.android.rcapstone.ui.helper;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.google.android.exoplayer2.util.Util;

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

        if(menuWidgetLauncher(mIntent)){
            saveLastPreference();
            return;
        }

        if (menuShortcutLauncher(mIntent)) {
            saveLastPreference();
            return;
        }

        if (menuTargetLink(mIntent)) {
            saveLastPreference();
            return;
        }

        if(menuHistoryLink()){
            //noinspection UnnecessaryReturnStatement
            return;
        }else {
            menuDefaultValue();
            saveLastPreference();

        }
    }

    private boolean menuWidgetLauncher(Intent intent) {
        if ((intent.getIntExtra(Costant.EXTRA_WIDGET_ID,0)>0) &&
                (!TextUtils.isEmpty(intent.getStringExtra(Costant.EXTRA_WIDGET_CATEGORY)))) {

            setCategory(intent.getStringExtra(Costant.EXTRA_WIDGET_CATEGORY));
            setTarget(Costant.WIDGET_MAIN_TARGET);
            return true;
        }
        return false;

    }


    private void menuDefaultValue(){
        setCategory(TextUtil.stringToArray(Costant.DEFAULT_SUBREDDIT_CATEGORY).get(0));
        setTarget(Costant.DEFAULT_START_VALUE_MAIN_TARGET);
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
                    setCategory(Costant.CATEGORY_ALL);
                    setTarget(Costant.ALL_MAIN_TARGET);
                    return true;

                case Costant.ACTION_SHORTCUT_POPULAR:
                    setCategory(Costant.CATEGORY_POPULAR);
                    setTarget(Costant.POPULAR_MAIN_TARGET);
                    return true;

                case Costant.ACTION_SHORTCUT_FAVORITE:
                    setCategory(Costant.CATEGORY_FAVORITE);
                    setTarget(Costant.FAVORITE_MAIN_TARGET);
                    return true;
            }

        }
        return false;
    }


    private boolean menuTargetLink(Intent intent) {

        if ((!TextUtils.isEmpty(intent.getStringExtra(Costant.EXTRA_MAIN_TARGET)) &&
                (!TextUtils.isEmpty(intent.getStringExtra(Costant.EXTRA_SUBREDDIT_CATEGORY))))) {

            setCategory(intent.getStringExtra(Costant.EXTRA_SUBREDDIT_CATEGORY));
            setTarget(intent.getStringExtra(Costant.EXTRA_MAIN_TARGET));

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
