package info.pelleritoudacity.android.rcapstone.data.db.dao;


import java.util.Date;
import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import info.pelleritoudacity.android.rcapstone.data.db.entry.PrefSubRedditEntry;

@SuppressWarnings("ALL")
@Dao
public interface PrefSubRedditDao {

    @Query("SELECT * FROM _pref_sub_reddit")
    LiveData<List<PrefSubRedditEntry>> loadAllRecords();

    @Query("SELECT * FROM _pref_sub_reddit WHERE _removed = :removed ")
    LiveData<List<PrefSubRedditEntry>> loadRecordByRemoved(int removed);

    @Query("SELECT * FROM _pref_sub_reddit WHERE id = :id")
    LiveData<PrefSubRedditEntry> loadRecordById(int id);

    @Query(" SELECT *  FROM _pref_sub_reddit WHERE (_removed = :removed AND _visible = :visible)")
    LiveData<List<PrefSubRedditEntry>> loadRecordByStar(int removed, int visible);

    @Query(" SELECT * FROM _pref_sub_reddit WHERE (_removed = :removed AND _visible = :visible)")
    LiveData<List<PrefSubRedditEntry>> loadRecordByCategory(int removed, int visible);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertRecord(PrefSubRedditEntry entry);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateRecord(PrefSubRedditEntry entry);

    @Query("UPDATE _pref_sub_reddit set _visible = :visible WHERE _name = :category")
    int updateRecordByVisibleStar(int visible, String category);

    @Query("UPDATE _pref_sub_reddit set _removed = :removed WHERE _name = :category")
    void updateRecordByRemovedStar(int removed, String category);

    @Query("UPDATE _pref_sub_reddit set _position = :position, _time_last_modified = :timeLastModified WHERE _position = :pos")
    void updateRecordByManagePosition(int position, Date timeLastModified, int pos);

    @Query("UPDATE _pref_sub_reddit set _position = :position WHERE id = :id")
    int updateRecordByDuplicatePosition(int position, int id);

    @Query("UPDATE _pref_sub_reddit set _removed = :removed WHERE _name Like :name")
    int updateRecordByRemoved(int removed, String name);

    @Delete
    void deleteRecord(PrefSubRedditEntry entry);

    @Query("DELETE FROM _pref_sub_reddit WHERE _name = :name")
    void deleteRecordByCategory(String name);
}
