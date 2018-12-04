package info.pelleritoudacity.android.rcapstone.data.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import info.pelleritoudacity.android.rcapstone.data.db.entry.T3Entry;

@Dao
public interface T3Dao {
    @Query("SELECT * FROM _t3")
    LiveData<List<T3Entry>> loadAllRecords();

    @Query("SELECT * FROM _t3 WHERE _target = :target AND _over18 <= :over18 ")
    LiveData<List<T3Entry>> loadMainTarget(String target, int over18);

    @Query("SELECT * FROM _t3 WHERE _saved = :saved ")
    LiveData<List<T3Entry>> loadFavoriteTarget(int saved);

    @Query("SELECT * FROM _t3 WHERE (_title LIKE :title AND _subbreddit LIKE :subreddit AND _over18 <= :over18 )")
    LiveData<List<T3Entry>> loadSearchMainTarget(String  title,String subreddit,int over18);

    @Query("SELECT * FROM _t3 WHERE (_subbreddit LIKE :subreddit AND _over18 <= :over18 )")
    LiveData<List<T3Entry>> loadWidgetDefaultTarget(String subreddit,int over18);

    @Query("SELECT * FROM _t3 WHERE (_name_id LIKE :nameId AND _over18 <= :over18 )")
    LiveData<List<T3Entry>> loadTitleDetail(String nameId,int over18);

    @SuppressWarnings("unused")
    @Query("SELECT * FROM _t3 WHERE _target = :target AND _over18 <= :over18 ")
    List<T3Entry> loadMainWidget(String target, int over18);

    @Insert
    void insertRecord(T3Entry entry);

    @Query("UPDATE _t3 set _saved = :saved WHERE _name = :name")
    void updateRecordBySaved(int saved, String name);

    @Query("UPDATE _t3 set _score = :score, _dir_score = :dirScore WHERE _name = :name")
    void updateRecordByVote(int score, int dirScore, String name);


    @Delete
    void deleteRecord(T3Entry entry);

    @Query("DELETE FROM _t3 WHERE _subbreddit = :subreddit")
    void deleteRecordByCategory(String subreddit);
}
