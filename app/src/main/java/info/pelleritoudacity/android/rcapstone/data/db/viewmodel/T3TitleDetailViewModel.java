package info.pelleritoudacity.android.rcapstone.data.db.viewmodel;


import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import info.pelleritoudacity.android.rcapstone.data.db.AppDatabase;
import info.pelleritoudacity.android.rcapstone.data.db.entry.T3Entry;

public class T3TitleDetailViewModel extends ViewModel {

    private final LiveData<List<T3Entry>> records;

    public T3TitleDetailViewModel(AppDatabase db, T3Entry entry) {

        records = db.t3Dao().loadTitleDetail(entry.getNameId(), entry.getOver18());

    }

    public LiveData<List<T3Entry>> getTask() {
        return records;
    }
}

