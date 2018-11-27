package info.pelleritoudacity.android.rcapstone.data.db.viewmodel;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import info.pelleritoudacity.android.rcapstone.data.db.AppDatabase;

public class PrefStarViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final AppDatabase mDb;
    private final int mRemoved;
    private final int mVisible;


    public PrefStarViewModelFactory(AppDatabase db,int removed, int visible) {
        mDb = db;
        mRemoved = removed;
        mVisible = visible;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new PrefStarsViewModel(mDb,mRemoved,mVisible);
    }

}
