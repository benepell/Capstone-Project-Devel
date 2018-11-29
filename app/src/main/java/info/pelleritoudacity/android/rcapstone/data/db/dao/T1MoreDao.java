package info.pelleritoudacity.android.rcapstone.data.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Query;

import java.util.List;

import info.pelleritoudacity.android.rcapstone.data.db.entry.T1MoreEntry;

@Dao
public interface T1MoreDao {
    @Query("SELECT * FROM _t1more")
    LiveData<List<T1MoreEntry>> loadAllRecords();

    @Delete
    void deleteRecord(T1MoreEntry entry);

}
