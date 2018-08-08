package info.pelleritoudacity.android.rcapstone.widget;

import android.content.Context;

import java.util.List;

import info.pelleritoudacity.android.rcapstone.data.db.Operation.T3Operation;
import info.pelleritoudacity.android.rcapstone.data.model.reddit.T3;
import info.pelleritoudacity.android.rcapstone.data.rest.MainExecute;
import info.pelleritoudacity.android.rcapstone.data.rest.WidgetExecute;
import info.pelleritoudacity.android.rcapstone.service.WidgetService;
import info.pelleritoudacity.android.rcapstone.utility.Costant;
import info.pelleritoudacity.android.rcapstone.utility.NetworkUtil;
import info.pelleritoudacity.android.rcapstone.utility.Preference;
import timber.log.Timber;

public class WidgetUtil {

    private final Context mContext;

    public WidgetUtil(Context context) {
        mContext = context;
    }

    public void init() {
        if (Preference.getWidgetId(mContext) == 0) {
            if (NetworkUtil.isOnline(mContext)) {
                Preference.setWidgetId(mContext, 1);
                updateData(Preference.getGeneralSettingsWidgetCategory(mContext));
            }
        }
        WidgetService.start(mContext);
    }

    public void updateData(String category) {
        String paramCategory = category;
        if ((category.equals(Costant.CATEGORY_POPULAR)) || (category.equals(Costant.CATEGORY_ALL))) {
            paramCategory = "/r/" + category;
        }

        if (NetworkUtil.isOnline(mContext)) {
            new WidgetExecute(new WidgetExecute.OnRestCallBack() {
                @Override
                public void success(T3 response, int code) {
                    T3Operation widgetData = new T3Operation(mContext, response);
                    widgetData.saveData(category, getMainTarget(category));
                }

                @Override
                public void success(List<T3> response, int code) {

                }

                @Override
                public void unexpectedError(Throwable tList) {
                    Timber.e("update error %s", tList.getMessage());
                }
            }, mContext, paramCategory).getData()
            ;

        }

    }

    public String getMainTarget(String category) {
        switch (category) {
            case "all":
                return Costant.ALL_MAIN_TARGET;
            case "popular":
                return Costant.POPULAR_MAIN_TARGET;
            case "hot":
                return Costant.HOT_MAIN_TARGET;
            default:
                return "";
        }

    }
}
