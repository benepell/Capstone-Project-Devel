package info.pelleritoudacity.android.rcapstone.ui.helper;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import java.util.List;
import java.util.Objects;

import info.pelleritoudacity.android.rcapstone.data.model.ui.DetailModel;
import info.pelleritoudacity.android.rcapstone.utility.Costant;
import info.pelleritoudacity.android.rcapstone.utility.Preference;
import info.pelleritoudacity.android.rcapstone.utility.TextUtil;

public class DetailHelper {
    private final Context mContext;

    public DetailHelper(Context context) {
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

            if (m.getTarget() == Costant.FAVORITE_DETAIL_TARGET) {
                return Costant.FAVORITE_DETAIL_TARGET;
            }

            if (m.getTarget() == Costant.SEARCH_DETAIL_TARGET) {
                return Costant.SEARCH_DETAIL_TARGET;
            }

            if ((m.getTarget() == Costant.MORE_SEARCH_DETAIL_TARGET)) {
                return Costant.MORE_SEARCH_DETAIL_TARGET;
            }

            if (TextUtils.isEmpty(m.getStrArrId())) {
                if ((updateData || !online)) {
                    return Costant.DETAIL_TARGET_NO_UPDATE;
                }

                if (!TextUtils.isEmpty(m.getStrId())) {
                    m.setTarget(Costant.DETAIL_TARGET);
                    return Costant.DETAIL_TARGET;
                }

            } else {
                // more detail update always
                m.setTarget(Costant.MORE_DETAIL_TARGET);
                return Costant.MORE_DETAIL_TARGET;
            }
        }

        return 0;

    }

    public DetailModel initModelTarget(Intent intent) {
        if (intent != null) {

            DetailModel model;

            if (intent.getParcelableExtra(Costant.EXTRA_PARCEL_DETAIL_MODEL) != null) {
                model = intent.getParcelableExtra(Costant.EXTRA_PARCEL_DETAIL_MODEL);

                if (model != null) {
                    model.setTarget(Costant.SEARCH_DETAIL_TARGET);
                }

            } else if (intent.getParcelableExtra(Costant.EXTRA_PARCEL_MORE_DETAIL_MODEL) != null) {
                model = intent.getParcelableExtra(Costant.EXTRA_PARCEL_MORE_DETAIL_MODEL);
                if (model != null) model.setTarget(Costant.MORE_SEARCH_DETAIL_TARGET);

            } else {
                model = new DetailModel();
                model.setTarget(Costant.DETAIL_TARGET);

            }


            if (intent.getIntExtra(Costant.EXTRA_SUBREDDIT_DETAIL_POSITION, RecyclerView.NO_POSITION) != RecyclerView.NO_POSITION) {
                Objects.requireNonNull(model).setPosition(intent.getIntExtra(Costant.EXTRA_SUBREDDIT_DETAIL_POSITION, RecyclerView.NO_POSITION));

            }

            if (intent.getStringExtra(Costant.EXTRA_SUBREDDIT_DETAIL_STR_ID) != null) {
                Objects.requireNonNull(model).setStrId(intent.getStringExtra(Costant.EXTRA_SUBREDDIT_DETAIL_STR_ID));

            }

            if (intent.getStringExtra(Costant.EXTRA_SUBREDDIT_DETAIL_CATEGORY) != null) {
                Objects.requireNonNull(model).setCategory(intent.getStringExtra(Costant.EXTRA_SUBREDDIT_DETAIL_CATEGORY));

            }

            if (intent.getBooleanExtra(Costant.EXTRA_ACTIVITY_DETAIL_REFRESH, false)) {
                Objects.requireNonNull(model).setStrId(Preference.getLastComment(mContext));

            }
            return model;

        }
        return null;
    }

}
