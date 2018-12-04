package info.pelleritoudacity.android.rcapstone.data.db.dao;


import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
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
