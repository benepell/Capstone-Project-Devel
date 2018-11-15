package info.pelleritoudacity.android.rcapstone.data.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import info.pelleritoudacity.android.rcapstone.data.db.entry.RedditEntry;

@Dao
public interface RedditDao {
    @Query("SELECT * FROM _reddit")
    LiveData<List<RedditEntry>> loadAllRecords();

    @Query("SELECT * FROM _reddit WHERE id = :id")
    LiveData<RedditEntry> loadRecordById(int id);

    @Insert
    void insertRecord(RedditEntry entry);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateRecord(RedditEntry entry);

    @Query("UPDATE _reddit set _data = :data WHERE id = :id")
    int updateRecordById(int data, int id);

    @Delete
    void deleteRecord(RedditEntry entry);

}
