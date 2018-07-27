package info.pelleritoudacity.android.rcapstone.ui.helper;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;

import java.util.List;
import java.util.Objects;

import info.pelleritoudacity.android.rcapstone.data.model.ui.DetailModel;
import info.pelleritoudacity.android.rcapstone.utility.Costant;
import info.pelleritoudacity.android.rcapstone.utility.NetworkUtil;
import info.pelleritoudacity.android.rcapstone.utility.Preference;
import info.pelleritoudacity.android.rcapstone.utility.TextUtil;

public class SubRedditDetailHelper {
    private final Context mContext;

    public SubRedditDetailHelper(Context context) {
        mContext = context;
    }

    public String initDepthIndicator(View viewIndicator, View viewCard, int depth, boolean enablePadding, boolean enableColorBackground) {
        if (getDepthBackground(depth) != null) {

            String depthBackGroundColor = Objects.requireNonNull(getDepthBackground(depth))[0];
            if (depthBackGroundColor != null) {

                viewIndicator.setBackgroundColor(Color.parseColor(depthBackGroundColor));
                viewIndicator.setVisibility(View.VISIBLE);
                if (enablePadding) {
                    int padding = Costant.LEVEL_DEPTH_PADDING;
                    int limit = Preference.getGeneralSettingsDepthPage(mContext);
                    if (limit < 5) {
                        padding *= 2;
                    } else if (limit > 10) {
                        padding *= 0.5;
                    }

                    viewCard.setPadding(padding * depth, 0, 0, 0);

                }

                if (enableColorBackground) {
                    return Objects.requireNonNull(getDepthBackground(depth))[1];
                }
            }

        }
        return null;
    }

    private String[] getDepthBackground(int depthLevel) {

        List<String> colors = TextUtil.stringToArray(Costant.DEFAULT_COLOR_INDICATOR);
        List<String> backgrounds = TextUtil.stringToArray(Costant.DEFAULT_COLOR_BACKGROUND);

        String[] arrayColors = new String[2];
        int colorSize = colors.size();
        for (int i = 0; i < colorSize; i++) {
            if (depthLevel == i) {
                arrayColors[0] = colors.get((i % colorSize));
                arrayColors[1] = backgrounds.get((i % colorSize));

                return arrayColors;
            }
        }

        return null;
    }

    public int getJob(DetailModel m, boolean updateData, boolean online) {

        if (m != null) {

            if (m.getTarget() == Costant.TARGET_DETAIL_SEARCH) {
                return Costant.TARGET_DETAIL_SEARCH;
            }

            if ((m.getTarget() == Costant.TARGET_MORE_DETAIL_SEARCH)) {
                return Costant.TARGET_MORE_DETAIL_SEARCH;
            }

            if (TextUtils.isEmpty(m.getStrArrId())) {
                if ((updateData || !online)) {
                    return Costant.TARGET_DETAIL_NO_UPDATE;
                }

                if (!TextUtils.isEmpty(m.getStrId())) {
                    m.setTarget(Costant.TARGET_DETAIL);
                    return Costant.TARGET_DETAIL;
                }

            } else {
                // more detail update always
                m.setTarget(Costant.TARGET_MORE_DETAIL);
                return Costant.TARGET_MORE_DETAIL;
            }
        }

        return 0;

    }

    public DetailModel initModelTarget(Intent intent, DetailModel model) {
        if (intent != null) {

            if (intent.getParcelableExtra(Costant.EXTRA_PARCEL_DETAIL_MODEL) != null) {
                model = intent.getParcelableExtra(Costant.EXTRA_PARCEL_DETAIL_MODEL);

                if (model != null) model.setTarget(Costant.TARGET_DETAIL_SEARCH);

            } else if (intent.getParcelableExtra(Costant.EXTRA_PARCEL_MORE_DETAIL_MODEL) != null) {
                model = intent.getParcelableExtra(Costant.EXTRA_PARCEL_MORE_DETAIL_MODEL);
                if (model != null) model.setTarget(Costant.TARGET_MORE_DETAIL_SEARCH);

            } else {
                model = new DetailModel();
                model.setTarget(Costant.TARGET_DETAIL);


            }

            if (intent.getStringExtra(Costant.EXTRA_SUBREDDIT_DETAIL_STR_ID) != null) {
                model.setStrId(intent.getStringExtra(Costant.EXTRA_SUBREDDIT_DETAIL_STR_ID));

            }

            if (intent.getBooleanExtra(Costant.EXTRA_ACTIVITY_SUBREDDIT_DETAIL_REFRESH, false)) {
                model.setStrId(Preference.getLastComment(mContext));
            }

            return model;

        }
        return null;
    }

}
