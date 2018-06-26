package info.pelleritoudacity.android.rcapstone.ui.helper;

import android.content.Context;
import android.graphics.Color;
import android.view.View;

import java.util.List;

import info.pelleritoudacity.android.rcapstone.utility.Costants;
import info.pelleritoudacity.android.rcapstone.utility.TextUtil;

public class SubRedditDetailHelper {
    Context mContext;

    public SubRedditDetailHelper(Context context) {
        mContext = context;
    }

    public void initDepthIndicator(View viewIndicator,View viewCard, int depth, boolean enablePadding) {
        int depthBackGroundColor = getDepthBackground(depth);
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

    public int getDepthBackground(int depthLevel){

        List<String> colors= TextUtil.stringToArray(Costants.DEFAULT_COLOR_INDICATOR);
        int colorSize = colors.size();
        for (int i = 0; i <colorSize ; i++) {
            if(depthLevel ==i){
                return Color.parseColor(colors.get((i%colorSize)));
            }
        }

        return 0;
    }

}
