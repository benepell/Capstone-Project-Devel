package info.pelleritoudacity.android.rcapstone.ui.helper;

import android.content.Context;
import android.graphics.Color;
import android.view.View;

import java.util.List;
import java.util.Objects;

import info.pelleritoudacity.android.rcapstone.utility.Costant;
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

                if(enableColorBackground){
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

}
