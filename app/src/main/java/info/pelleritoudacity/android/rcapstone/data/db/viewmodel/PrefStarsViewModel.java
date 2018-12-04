package info.pelleritoudacity.android.rcapstone.data.db.viewmodel;


import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import info.pelleritoudacity.android.rcapstone.data.db.AppDatabase;
import info.pelleritoudacity.android.rcapstone.data.db.entry.PrefSubRedditEntry;

public class PrefStarsViewModel extends ViewModel {

    private final LiveData<List<PrefSubRedditEntry>> records;


    public PrefStarsViewModel(AppDatabase db, int removed, int visible) {
        records = db.prefSubRedditDao().loadRecordByStar(removed, visible);
    }

    public LiveData<List<PrefSubRedditEntry>> getTask() {
        return records;
    }


}

