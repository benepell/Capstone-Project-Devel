package info.pelleritoudacity.android.rcapstone.data.db.viewmodel;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import info.pelleritoudacity.android.rcapstone.data.db.AppDatabase;
import info.pelleritoudacity.android.rcapstone.data.db.entry.PrefSubRedditEntry;

public class PrefViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final AppDatabase mDb;
    private final PrefSubRedditEntry mEntry;

    public PrefViewModelFactory(AppDatabase mDb, PrefSubRedditEntry entry) {
        this.mDb = mDb;
        mEntry = entry;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new PrefViewModel(mDb,mEntry);
    }
}
