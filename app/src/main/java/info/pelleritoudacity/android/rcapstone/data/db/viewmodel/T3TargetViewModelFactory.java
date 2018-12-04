package info.pelleritoudacity.android.rcapstone.data.db.viewmodel;


import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import info.pelleritoudacity.android.rcapstone.data.db.AppDatabase;
import info.pelleritoudacity.android.rcapstone.data.db.entry.T3Entry;

public class T3TargetViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final AppDatabase mDb;
    private final T3Entry mEntry;
    private final String mTarget;

    public T3TargetViewModelFactory(AppDatabase mDb, T3Entry mEntry, String mTarget) {
        this.mDb = mDb;
        this.mEntry = mEntry;
        this.mTarget = mTarget;

    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new T3TargetViewModel(mDb, mEntry, mTarget);

    }

}
