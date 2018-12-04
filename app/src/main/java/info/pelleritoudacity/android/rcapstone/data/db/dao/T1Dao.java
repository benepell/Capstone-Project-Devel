package info.pelleritoudacity.android.rcapstone.data.db.dao;


import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import info.pelleritoudacity.android.rcapstone.data.db.entry.T1Entry;

@Dao
public interface T1Dao {
    @Query("SELECT * FROM _t1")
    LiveData<List<T1Entry>> loadAllRecords();

    @Query("SELECT * FROM _t1 WHERE _link_id = :linkId AND _more_replies =:moreReplies AND _over18 <= :over18 ")
    LiveData<List<T1Entry>> loadDetailTarget(String linkId, String moreReplies, int over18);

    @Query("SELECT * FROM _t1 WHERE id = :id OR _name_id IN( :strArrId )")
    LiveData<List<T1Entry>> loadMoreDetailTarget(int id, String strArrId);

    @Query("SELECT * FROM _t1 WHERE (_body LIKE :body AND _link_id = :linkId AND  _more_replies = :moreReplies AND _over18 <= :over18 )")
    LiveData<List<T1Entry>> loadSearchDetailTarget(String body, String linkId, String moreReplies, int over18);

    @Query("SELECT * FROM _t1 WHERE (_body LIKE :body AND id = :id OR _name_id IN( :strArrId) )")
    LiveData<List<T1Entry>> loadMoreSearchDetailTarget(String body, int id, String strArrId);

    @Insert
    void insertRecord(T1Entry entry);

    @Query("UPDATE _t1 SET _num_comments = :numComments , _more_comments = :moreComments  WHERE  _name_id = :nameId")
    void updateRecordMoreComments(int numComments, String moreComments, String nameId);

    @Query("UPDATE _t1 set _score = :score, _dir_score = :dirScore WHERE _name = :name")
    void updateRecordByVote(int score, int dirScore, String name);

    @Delete
    void deleteRecord(T1Entry entry);

    @Query("DELETE FROM _t1 WHERE _name_id = :nameId ")
    void deleteRecordByNameId(String nameId);

    @Query("DELETE FROM _t1 WHERE _name_id = :nameId OR _link_id = :linkId")
    void deleteRecordByCategory(String nameId, String linkId);

    @Query("DELETE FROM _t1 WHERE _more_replies >= :moreReplies")
    void deleteRecordMoreReplies(String moreReplies);


}
