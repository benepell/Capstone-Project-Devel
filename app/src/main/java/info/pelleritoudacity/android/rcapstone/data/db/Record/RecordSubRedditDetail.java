package info.pelleritoudacity.android.rcapstone.data.db.Record;

import android.database.Cursor;

import java.util.ArrayList;

import info.pelleritoudacity.android.rcapstone.data.db.Contract;
import info.pelleritoudacity.android.rcapstone.data.model.record.RecordAdapterDetail;

public class RecordSubRedditDetail {

    private final Cursor mCursor;
    private RecordAdapterDetail mRecord;
    private ArrayList<RecordAdapterDetail> recordList;

    public RecordSubRedditDetail(Cursor cursor) {
        mCursor = cursor;
    }

    public ArrayList<RecordAdapterDetail> getRecordList() {
        if (mCursor != null) {
            initRecord();

            if(mRecord!=null){
               recordList = new ArrayList<>(1);
               recordList.add(mRecord);

           }
        }

        return recordList;
    }

    private void initRecord() {

        mRecord = new RecordAdapterDetail();

        mRecord.setSubReddit(mCursor.getString(
                mCursor.getColumnIndex(Contract.T1dataEntry.COLUMN_NAME_SUBREDDIT)));

        mRecord.setStrId(mCursor.getString(
                mCursor.getColumnIndex(Contract.T1dataEntry.COLUMN_NAME_ID)));

        mRecord.setChildrenId(mCursor.getString(
                mCursor.getColumnIndex(Contract.T1dataEntry.COLUMN_NAME_CHILDREN_ID)));

        mRecord.setSubRedditId(mCursor.getString(
                mCursor.getColumnIndex(Contract.T1dataEntry.COLUMN_NAME_SUBREDDITID)));

        mRecord.setSubRedditName(mCursor.getString(
                mCursor.getColumnIndex(Contract.T1dataEntry.COLUMN_NAME_NAME)));

        mRecord.setUps(mCursor.getString(mCursor.getColumnIndex(
                Contract.T1dataEntry.COLUMN_NAME_UPS)));

        mRecord.setTitle(mCursor.getString(mCursor.getColumnIndex(
                Contract.T1dataEntry.COLUMN_NAME_TITLE)));

        mRecord.setVoteDowns(mCursor.getInt(mCursor.getColumnIndex(
                Contract.T1dataEntry.COLUMN_NAME_DOWNS)));

        mRecord.setApprovedAtUtc(mCursor.getString(
                mCursor.getColumnIndex(Contract.T1dataEntry.COLUMN_NAME_APPROVEDATUTC)));

        mRecord.setAuthor(mCursor.getString(
                mCursor.getColumnIndex(Contract.T1dataEntry.COLUMN_NAME_AUTHOR)));

        mRecord.setStickied(mCursor.getString(
                mCursor.getColumnIndex(Contract.T1dataEntry.COLUMN_NAME_STICKIED)));

        mRecord.setSaved(mCursor.getInt(
                mCursor.getColumnIndex(Contract.T1dataEntry.COLUMN_NAME_SAVED)) != 0);

        mRecord.setArchived(mCursor.getInt(
                mCursor.getColumnIndex(Contract.T1dataEntry.COLUMN_NAME_ARCHIVED)) != 0);

        mRecord.setNoFollow(mCursor.getInt(
                mCursor.getColumnIndex(Contract.T1dataEntry.COLUMN_NAME_NOFOLLOW)) != 0);

        mRecord.setSendReplies(mCursor.getInt(
                mCursor.getColumnIndex(Contract.T1dataEntry.COLUMN_NAME_SENDREPLIES)) != 0);

        mRecord.setCanGild(mCursor.getInt(
                mCursor.getColumnIndex(Contract.T1dataEntry.COLUMN_NAME_CANGILD)) != 0);

        mRecord.setModNote(mCursor.getInt(
                mCursor.getColumnIndex(Contract.T1dataEntry.COLUMN_NAME_MODNOTE)) != 0);

        mRecord.setHideScore(mCursor.getInt(
                mCursor.getColumnIndex(Contract.T1dataEntry.COLUMN_NAME_HIDESCORE)) != 0);

        mRecord.setGilded(mCursor.getString(
                mCursor.getColumnIndex(Contract.T1dataEntry.COLUMN_NAME_GILDED)));

        mRecord.setCreated(mCursor.getInt(
                mCursor.getColumnIndex(Contract.T1dataEntry.COLUMN_NAME_CREATED)));

        mRecord.setScore(mCursor.getString(
                mCursor.getColumnIndex(Contract.T1dataEntry.COLUMN_NAME_SCORE)));

        mRecord.setPermanentLink(mCursor.getString(
                mCursor.getColumnIndex(Contract.T1dataEntry.COLUMN_NAME_PERMALINK)));

        mRecord.setSubRedditType(mCursor.getString(
                mCursor.getColumnIndex(Contract.T1dataEntry.COLUMN_NAME_SUBREDDITTYPE)));

        mRecord.setCreatedUtc(mCursor.getInt(
                mCursor.getColumnIndex(Contract.T1dataEntry.COLUMN_NAME_CREATEDUTC)));

        mRecord.setBody(mCursor.getString(
                mCursor.getColumnIndex(Contract.T1dataEntry.COLUMN_NAME_BODY)));

        mRecord.setBodyHtml(mCursor.getString(
                mCursor.getColumnIndex(Contract.T1dataEntry.COLUMN_NAME_BODY_HTML)));

        mRecord.setLinkId(mCursor.getString(
                mCursor.getColumnIndex(Contract.T1dataEntry.COLUMN_NAME_LINK_ID)));

        mRecord.setParentId(mCursor.getString(
                mCursor.getColumnIndex(Contract.T1dataEntry.COLUMN_NAME_PARENT_ID)));

        mRecord.setUrl(mCursor.getString(
                mCursor.getColumnIndex(Contract.T1dataEntry.COLUMN_NAME_URL)));

        mRecord.setDepth(mCursor.getInt(
                mCursor.getColumnIndex(Contract.T1dataEntry.COLUMN_NAME_DEPTH)));


    }

}
