package info.pelleritoudacity.android.rcapstone.data.db.viewmodel;


import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import info.pelleritoudacity.android.rcapstone.data.db.AppDatabase;
import info.pelleritoudacity.android.rcapstone.data.db.entry.PrefSubRedditEntry;

public class PrefCategoryViewModel extends ViewModel {

    private final LiveData<List<PrefSubRedditEntry>> records;


    public PrefCategoryViewModel(AppDatabase db, int removed, int visible) {
        records = db.prefSubRedditDao().loadRecordByCategory(removed, visible);
    }

    public LiveData<List<PrefSubRedditEntry>> getTask() {
        return records;
    }


}

