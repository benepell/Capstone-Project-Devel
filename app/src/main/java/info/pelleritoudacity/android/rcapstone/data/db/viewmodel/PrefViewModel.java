package info.pelleritoudacity.android.rcapstone.data.db.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import java.util.List;

import info.pelleritoudacity.android.rcapstone.data.db.AppDatabase;
import info.pelleritoudacity.android.rcapstone.data.db.entry.PrefSubRedditEntry;

public class PrefViewModel extends ViewModel {

    private final LiveData<List<PrefSubRedditEntry>> records;
    @SuppressWarnings("FieldCanBeLocal")
    private final PrefSubRedditEntry mEntry;

    public PrefViewModel(AppDatabase db, PrefSubRedditEntry entry) {
        records = db.prefSubRedditDao().loadAllRecords();
        mEntry = entry;
    }

    public LiveData<List<PrefSubRedditEntry>> getTask(){return records;}
}
