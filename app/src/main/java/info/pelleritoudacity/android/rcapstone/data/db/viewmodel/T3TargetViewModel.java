package info.pelleritoudacity.android.rcapstone.data.db.viewmodel;


import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import info.pelleritoudacity.android.rcapstone.data.db.AppDatabase;
import info.pelleritoudacity.android.rcapstone.data.db.entry.T3Entry;
import info.pelleritoudacity.android.rcapstone.utility.Costant;

public class T3TargetViewModel extends ViewModel {

    private final LiveData<List<T3Entry>> records;

    public T3TargetViewModel(AppDatabase db, T3Entry entry, String target) {

        switch (target) {
            case Costant.ALL_MAIN_TARGET:
            case Costant.POPULAR_MAIN_TARGET:
            case Costant.WIDGET_MAIN_TARGET:

                records = db.t3Dao().loadMainTarget(entry.getTarget(), entry.getOver18());
                break;

            case Costant.FAVORITE_MAIN_TARGET:
                records = db.t3Dao().loadFavoriteTarget(entry.getSaved());
                break;

            case Costant.SEARCH_MAIN_TARGET:
                records = db.t3Dao().loadSearchMainTarget(entry.getTitle(), entry.getSubreddit(), entry.getOver18());
                break;

            case Costant.NAVIGATION_MAIN_TARGET:
            default:
                records = db.t3Dao().loadWidgetDefaultTarget(entry.getSubreddit(), entry.getOver18());

        }

    }

    public LiveData<List<T3Entry>> getTask() {
        return records;
    }
}
