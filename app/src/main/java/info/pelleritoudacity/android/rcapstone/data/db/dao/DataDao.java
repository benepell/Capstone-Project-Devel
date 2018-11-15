package info.pelleritoudacity.android.rcapstone.data.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import info.pelleritoudacity.android.rcapstone.data.db.entry.DataEntry;

@Dao
public interface DataDao {
    @Query("SELECT * FROM _data")
    LiveData<List<DataEntry>> loadAllRecords();

    @Query("SELECT * FROM _data WHERE id = :id")
    LiveData<DataEntry> loadRecordById(int id);

    @Insert
    void insertRecord(DataEntry entry);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateRecord(DataEntry entry);

    @Query("UPDATE _data set _childrens = :children WHERE id = :id")
    int updateRecordById(int children, int id);

    @Delete
    void deleteRecord(DataEntry entry);

}
