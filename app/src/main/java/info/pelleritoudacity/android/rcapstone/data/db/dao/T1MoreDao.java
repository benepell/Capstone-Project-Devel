package info.pelleritoudacity.android.rcapstone.data.db.dao;


import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Query;
import info.pelleritoudacity.android.rcapstone.data.db.entry.T1MoreEntry;

@Dao
public interface T1MoreDao {
    @Query("SELECT * FROM _t1more")
    LiveData<List<T1MoreEntry>> loadAllRecords();

    @Delete
    void deleteRecord(T1MoreEntry entry);

}
