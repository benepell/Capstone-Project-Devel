package info.pelleritoudacity.android.rcapstone.data.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import info.pelleritoudacity.android.rcapstone.data.db.entry.T1Entry;

@Dao
public interface T1Dao {
    @Query("SELECT * FROM _t1")
    LiveData<List<T1Entry>> loadAllRecords();

    @Query("SELECT * FROM _t1 WHERE id = :id")
    LiveData<T1Entry> loadRecordById(int id);

    @Insert
    void insertRecord(T1Entry entry);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateRecord(T1Entry entry);

    @Query("UPDATE _t1 set _name = :name WHERE id = :id")
    int updateRecordById(String name, int id);

    @Delete
    void deleteRecord(T1Entry entry);

}
