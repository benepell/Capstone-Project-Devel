package info.pelleritoudacity.android.rcapstone.data.db.viewmodel;


import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import info.pelleritoudacity.android.rcapstone.data.db.AppDatabase;
import info.pelleritoudacity.android.rcapstone.data.db.entry.T1Entry;
import info.pelleritoudacity.android.rcapstone.utility.Costant;

public class T1TargetViewModel extends ViewModel {

    private LiveData<List<T1Entry>> records;

    public T1TargetViewModel(AppDatabase db, T1Entry entry, String querySearch,int target) {

        switch (target) {
            case Costant.DETAIL_TARGET:
                records = db.t1Dao().loadDetailTarget(Costant.STR_PARENT_LINK + entry.getNameId(), Costant.NONE_DETAIL_MORE_REPLIES, entry.getOver18());
                break;

            case Costant.MORE_DETAIL_TARGET:
                records = db.t1Dao().loadMoreDetailTarget(entry.getId(), entry.getNameId());
                break;

            case Costant.SEARCH_DETAIL_TARGET:
                records = db.t1Dao().loadSearchDetailTarget("%" + querySearch + "%", Costant.STR_PARENT_LINK + entry.getNameId(), Costant.NONE_DETAIL_MORE_REPLIES, entry.getOver18());
                break;

            case Costant.MORE_SEARCH_DETAIL_TARGET:
                records = db.t1Dao().loadMoreSearchDetailTarget("%" + querySearch + "%", +entry.getId(), (entry.getNameId()));

        }

    }

    public LiveData<List<T1Entry>> getTask() {
        return records;
    }
}

