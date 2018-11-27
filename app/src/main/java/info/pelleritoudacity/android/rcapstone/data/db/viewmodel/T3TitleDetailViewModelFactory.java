package info.pelleritoudacity.android.rcapstone.data.db.viewmodel;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import info.pelleritoudacity.android.rcapstone.data.db.AppDatabase;
import info.pelleritoudacity.android.rcapstone.data.db.entry.T3Entry;

public class T3TitleDetailViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private final AppDatabase mDb;
    private final T3Entry mEntry;

    public T3TitleDetailViewModelFactory(AppDatabase mDb, T3Entry mEntry) {
        this.mDb = mDb;
        this.mEntry = mEntry;

    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new T3TitleDetailViewModel(mDb, mEntry);

    }
}
