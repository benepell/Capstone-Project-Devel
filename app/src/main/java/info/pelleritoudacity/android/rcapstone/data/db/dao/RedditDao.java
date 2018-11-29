package info.pelleritoudacity.android.rcapstone.data.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import info.pelleritoudacity.android.rcapstone.data.db.entry.RedditEntry;

@Dao
public interface RedditDao {
    @Query("SELECT * FROM _reddit")
    LiveData<List<RedditEntry>> loadAllRecords();

    @Insert
    void insertRecord(RedditEntry entry);

    @Delete
    void deleteRecord(RedditEntry entry);

}
