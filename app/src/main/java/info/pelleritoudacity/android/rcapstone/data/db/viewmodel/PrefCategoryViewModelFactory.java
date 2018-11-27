package info.pelleritoudacity.android.rcapstone.data.db.viewmodel;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import info.pelleritoudacity.android.rcapstone.data.db.AppDatabase;

public class PrefCategoryViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final AppDatabase mDb;
    private final int mRemoved;
    private final int mVisible;


    public PrefCategoryViewModelFactory(AppDatabase db, int removed, int visible) {
        mDb = db;
        mRemoved = removed;
        mVisible = visible;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new PrefCategoryViewModel(mDb, mRemoved, mVisible);
    }

}

