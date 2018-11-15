package info.pelleritoudacity.android.rcapstone.data.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import info.pelleritoudacity.android.rcapstone.data.db.entry.T1MoreEntry;

@Dao
public interface T1MoreDao {
    @Query("SELECT * FROM _t1more")
    LiveData<List<T1MoreEntry>> loadAllRecords();

    @Query("SELECT * FROM _t1more WHERE id = :id")
    LiveData<T1MoreEntry> loadRecordById(int id);

    @Insert
    void insertRecord(T1MoreEntry entry);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateRecord(T1MoreEntry entry);

    @Query("UPDATE _t1more set _more_children = :moreChildren WHERE id = :id")
    int updateRecordById(String moreChildren, int id);

    @Delete
    void deleteRecord(T1MoreEntry entry);

}
