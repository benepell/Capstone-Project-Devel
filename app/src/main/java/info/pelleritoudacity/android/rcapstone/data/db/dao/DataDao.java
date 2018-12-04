package info.pelleritoudacity.android.rcapstone.data.db.dao;


import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import info.pelleritoudacity.android.rcapstone.data.db.entry.DataEntry;

@SuppressWarnings("unused")
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
