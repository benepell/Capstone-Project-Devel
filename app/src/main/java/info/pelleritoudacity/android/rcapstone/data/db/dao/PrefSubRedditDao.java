package info.pelleritoudacity.android.rcapstone.data.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import info.pelleritoudacity.android.rcapstone.data.db.entry.PrefSubRedditEntry;

@Dao
public interface PrefSubRedditDao {
    @Query("SELECT * FROM _pref_sub_reddit")
    LiveData<List<PrefSubRedditEntry>> loadAllRecords();

    @Query("SELECT * FROM _pref_sub_reddit WHERE id = :id")
    LiveData<PrefSubRedditEntry> loadRecordById(int id);

    @Insert
    void insertRecord(PrefSubRedditEntry entry);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateRecord(PrefSubRedditEntry entry);

    @Query("UPDATE _pref_sub_reddit set _name = :name WHERE id = :id")
    int updateRecordById(String name, int id);

    @Delete
    void deleteRecord(PrefSubRedditEntry entry);

}
