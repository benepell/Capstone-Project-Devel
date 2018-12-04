package info.pelleritoudacity.android.rcapstone.data.db.viewmodel;


import android.app.Application;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import info.pelleritoudacity.android.rcapstone.data.db.AppDatabase;
import info.pelleritoudacity.android.rcapstone.data.db.entry.DataEntry;
import info.pelleritoudacity.android.rcapstone.data.db.entry.PrefSubRedditEntry;
import info.pelleritoudacity.android.rcapstone.data.db.entry.RedditEntry;
import info.pelleritoudacity.android.rcapstone.data.db.entry.T1Entry;
import info.pelleritoudacity.android.rcapstone.data.db.entry.T1MoreEntry;
import info.pelleritoudacity.android.rcapstone.data.db.entry.T3Entry;
import info.pelleritoudacity.android.rcapstone.data.db.entry.T5Entry;

@SuppressWarnings("unused")
public class MainViewModel extends AndroidViewModel {

    private final LiveData<List<DataEntry>> dataRecords;
    private final LiveData<List<RedditEntry>> redditRecords;
    private final LiveData<List<PrefSubRedditEntry>> prefSubRedditRecords;
    private final LiveData<List<T1Entry>> t1Records;
    private final LiveData<List<T1MoreEntry>> t1MoreRecords;
    private final LiveData<List<T3Entry>> t3Records;
    private final LiveData<List<T5Entry>> t5Records;

    public MainViewModel(@NonNull Application application) {
        super(application);
        AppDatabase db = AppDatabase.getInstance(this.getApplication());
        this.dataRecords = db.dataDao().loadAllRecords();
        this.redditRecords = db.redditDao().loadAllRecords();
        this.prefSubRedditRecords = db.prefSubRedditDao().loadAllRecords();
        this.t1Records = db.t1Dao().loadAllRecords();
        this.t1MoreRecords = db.t1MoreDao().loadAllRecords();
        this.t3Records = db.t3Dao().loadAllRecords();
        this.t5Records = db.t5Dao().loadAllRecords();
    }

    public LiveData<List<DataEntry>> getDataRecords() {
        return dataRecords;
    }

    public LiveData<List<RedditEntry>> getRedditRecords() {
        return redditRecords;
    }

    public LiveData<List<T1Entry>> getT1Records() {
        return t1Records;
    }

    public LiveData<List<T1MoreEntry>> getT1MoreRecords() {
        return t1MoreRecords;
    }

    public LiveData<List<T3Entry>> getT3Records() {
        return t3Records;
    }

    public LiveData<List<T5Entry>> getT5Records() {
        return t5Records;
    }

    public LiveData<List<PrefSubRedditEntry>> getPrefSubRedditRecords() {
        return prefSubRedditRecords;
    }

}
