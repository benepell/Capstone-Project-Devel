package info.pelleritoudacity.android.rcapstone.data.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

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
