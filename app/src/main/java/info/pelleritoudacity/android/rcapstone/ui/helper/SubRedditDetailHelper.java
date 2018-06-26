package info.pelleritoudacity.android.rcapstone.ui.helper;

import android.content.Context;
import android.view.View;

import info.pelleritoudacity.android.rcapstone.utility.Costants;
import info.pelleritoudacity.android.rcapstone.utility.Utility;

public class SubRedditDetailHelper {
    Context mContext;

    public SubRedditDetailHelper(Context context) {
        mContext = context;
    }

    public void initDepthIndicator(View viewIndicator,View viewCard, int depth, boolean enablePadding) {
        int depthBackGroundColor = Utility.getDepthBackground(depth);
        if (depthBackGroundColor != 0) {
            viewIndicator.setBackgroundColor(depthBackGroundColor);

            if (enablePadding) {
                int padding = Costants.LEVEL_DEPTH_PADDING;
                int limit = Integer.parseInt(Costants.LIMIT_DEPTH_RESULTS);
                if (limit < 5) {
                    padding *= 2;
                } else if (limit > 10) {
                    padding *= 0.5;
                }
                viewCard.setPadding(padding * depth, 0, 0, 0);

            }

        }

    }

}
