package info.pelleritoudacity.android.rcapstone.data.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.Date;
import java.util.List;

import info.pelleritoudacity.android.rcapstone.data.db.entry.PrefSubRedditEntry;

@Dao
public interface PrefSubRedditDao {
    @Query("SELECT * FROM _pref_sub_reddit")
    LiveData<List<PrefSubRedditEntry>> loadAllRecords();

    @Query("SELECT * FROM _pref_sub_reddit WHERE id = :id")
    LiveData<PrefSubRedditEntry> loadRecordById(int id);

//    SELECT DISTINCT _name,_image,_visible,_position,_time_last_modified  FROM _pref_sub_reddit WHERE (_removed = :removed AND _visible = :visible)")
    @Query(" SELECT *  FROM _pref_sub_reddit WHERE (_removed = :removed AND _visible = :visible)")
    LiveData<List<PrefSubRedditEntry>> loadRecordByStar(int removed, int visible);

    @Query(" SELECT * FROM _pref_sub_reddit WHERE (_removed = :removed AND _visible = :visible)")
    LiveData<List<PrefSubRedditEntry>> loadRecordByCategory(int removed, int visible);

    @Insert
    void insertRecord(PrefSubRedditEntry entry);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateRecord(PrefSubRedditEntry entry);

    @Query("UPDATE _pref_sub_reddit set _name = :name WHERE id = :id")
    int updateRecordById(String name, int id);

    @Query("UPDATE _pref_sub_reddit set _removed = :removed, _position = :position, _visible = :visible WHERE _backup_position = :backupPosition")
    int updateRecordByBackupPosition(int removed, int position, int visible, int backupPosition);

    @Query("UPDATE _pref_sub_reddit set _visible = :visible WHERE _name = :category")
    int updateRecordByVisibleStar(int visible, String category);

    @Query("UPDATE _pref_sub_reddit set _removed = :removed WHERE _name = :category")
    void updateRecordByRemovedStar(int removed, String category);

    @Query("UPDATE _pref_sub_reddit set _position = :position, _time_last_modified = :timeLastModified WHERE _position = :pos")
    void updateRecordByManagePosition(int position, Date timeLastModified, int pos);

    @Query("UPDATE _pref_sub_reddit set _position = :position WHERE id = :id")
    int updateRecordByDuplicatePosition(int position, int id);

    @Delete
    void deleteRecord(PrefSubRedditEntry entry);

    @Query("DELETE FROM _pref_sub_reddit WHERE _name = :name")
    void deleteRecordByCategory(String name);
}
