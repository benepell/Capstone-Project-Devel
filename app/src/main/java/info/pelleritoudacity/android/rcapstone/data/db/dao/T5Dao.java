package info.pelleritoudacity.android.rcapstone.data.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import info.pelleritoudacity.android.rcapstone.data.db.entry.T5Entry;

@Dao
public interface T5Dao {
    @Query("SELECT * FROM _t5")
    LiveData<List<T5Entry>> loadAllRecords();

    @Query("SELECT * FROM _t5 WHERE id = :id")
    LiveData<T5Entry> loadRecordById(int id);

    @Insert
    void insertRecord(T5Entry entry);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateRecord(T5Entry entry);

    @Query("UPDATE _t5 set _name = :name WHERE id = :id")
    int updateRecordById(String name, int id);

    @Delete
    void deleteRecord(T5Entry entry);

}
