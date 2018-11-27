package info.pelleritoudacity.android.rcapstone.data.db.viewmodel;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import info.pelleritoudacity.android.rcapstone.data.db.AppDatabase;
import info.pelleritoudacity.android.rcapstone.data.db.entry.T1Entry;

public class T1TargetViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final AppDatabase mDb;
    private final T1Entry mEntry;
    private final String mQuerySearch;
    private final int mTarget;

    public T1TargetViewModelFactory(AppDatabase mDb, T1Entry mEntry, String querySearch, int target) {
        this.mDb = mDb;
        this.mEntry = mEntry;
        mQuerySearch = querySearch;
        mTarget = target;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new T1TargetViewModel(mDb, mEntry, mQuerySearch, mTarget);

    }
}
