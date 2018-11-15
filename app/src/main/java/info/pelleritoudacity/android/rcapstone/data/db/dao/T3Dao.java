package info.pelleritoudacity.android.rcapstone.data.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import info.pelleritoudacity.android.rcapstone.data.db.entry.T3Entry;

@Dao
public interface T3Dao {
    @Query("SELECT * FROM _t3")
    LiveData<List<T3Entry>> loadAllRecords();

    @Query("SELECT * FROM _t3 WHERE id = :id")
    LiveData<T3Entry> loadRecordById(int id);

    @Insert
    void insertRecord(T3Entry entry);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateRecord(T3Entry entry);

    @Query("UPDATE _t3 set _author = :author WHERE id = :id")
    int updateRecordById(String author, int id);

    @Delete
    void deleteRecord(T3Entry entry);

}
