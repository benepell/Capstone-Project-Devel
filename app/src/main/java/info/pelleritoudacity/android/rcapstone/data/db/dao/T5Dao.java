package info.pelleritoudacity.android.rcapstone.data.db.dao;


import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import info.pelleritoudacity.android.rcapstone.data.db.entry.T5Entry;

@Dao
public interface T5Dao {
    @Query("SELECT * FROM _t5")
    LiveData<List<T5Entry>> loadAllRecords();

    @Insert
    void insertRecord(T5Entry entry);

    @Delete
    void deleteRecord(T5Entry entry);

}
