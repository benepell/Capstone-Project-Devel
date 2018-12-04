package info.pelleritoudacity.android.rcapstone.data.db.viewmodel;


import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
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

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new PrefCategoryViewModel(mDb, mRemoved, mVisible);
    }

}

