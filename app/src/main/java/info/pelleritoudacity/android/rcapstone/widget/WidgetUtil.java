package info.pelleritoudacity.android.rcapstone.widget;

import android.content.Context;

import java.util.List;

import info.pelleritoudacity.android.rcapstone.data.db.Operation.T3Operation;
import info.pelleritoudacity.android.rcapstone.data.model.reddit.T3;
import info.pelleritoudacity.android.rcapstone.data.rest.MainExecute;
import info.pelleritoudacity.android.rcapstone.service.WidgetService;
import info.pelleritoudacity.android.rcapstone.utility.Costant;
import info.pelleritoudacity.android.rcapstone.utility.NetworkUtil;
import info.pelleritoudacity.android.rcapstone.utility.Preference;

public class WidgetUtil {

    Context mContext;

    public WidgetUtil(Context context) {
        mContext = context;
    }


    public void init() {
        if (Preference.getWidgetId(mContext) == 0) {

            if (NetworkUtil.isOnline(mContext)) {
                Preference.setWidgetId(mContext, 1);
                updateData();
            }

        }
        WidgetService.start(mContext);
    }

    public void updateData() {
        if (NetworkUtil.isOnline(mContext)) {
            new MainExecute(new MainExecute.OnRestCallBack() {
                @Override
                public void success(T3 response, int code) {
                    T3Operation widgetData = new T3Operation(mContext, response);
                    widgetData.saveData(Costant.CATEGORY_POPULAR, Costant.POPULAR_MAIN_TARGET);
                }

                @Override
                public void success(List<T3> response, int code) {

                }

                @Override
                public void unexpectedError(Throwable tList) {

                }
            }, mContext, Costant.CATEGORY_POPULAR).getData();

        }
    }

}
