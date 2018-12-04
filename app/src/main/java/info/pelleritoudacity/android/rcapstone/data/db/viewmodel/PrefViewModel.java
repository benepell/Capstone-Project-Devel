package info.pelleritoudacity.android.rcapstone.data.db.viewmodel;


import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import info.pelleritoudacity.android.rcapstone.data.db.AppDatabase;
import info.pelleritoudacity.android.rcapstone.data.db.entry.PrefSubRedditEntry;

public class PrefViewModel extends ViewModel {

    private final LiveData<List<PrefSubRedditEntry>> records;
    @SuppressWarnings("FieldCanBeLocal")

    public PrefViewModel(AppDatabase db, PrefSubRedditEntry entry) {
        records = db.prefSubRedditDao().loadRecordByRemoved(entry.getRemoved());
    }

    public LiveData<List<PrefSubRedditEntry>> getTask(){return records;}
}
