package info.pelleritoudacity.android.rcapstone.data.db.Record;

import android.database.Cursor;

import java.util.ArrayList;

import info.pelleritoudacity.android.rcapstone.data.db.Contract;
import info.pelleritoudacity.android.rcapstone.data.model.record.RecordAdapterSelected;

public class RecordSubRedditSelected {


    private final Cursor mCursor;
    private RecordAdapterSelected mRecord;
    private ArrayList<RecordAdapterSelected> recordList;

    public RecordSubRedditSelected(Cursor cursor) {
        mCursor = cursor;
    }

    public ArrayList<RecordAdapterSelected> getRecordList() {
        if (mCursor != null) {
            initRecord();

            if (mRecord != null) {
                recordList = new ArrayList<>(1);
                recordList.add(mRecord);

            }
        }

        return recordList;
    }

    private void initRecord() {

        mRecord = new RecordAdapterSelected();

        mRecord.setSubReddit(mCursor.getString(
                mCursor.getColumnIndex(Contract.T3dataEntry.COLUMN_NAME_SUBREDDIT)));

        mRecord.setSubRedditName(mCursor.getString(
                mCursor.getColumnIndex(Contract.T3dataEntry.COLUMN_NAME_NAME)));

        mRecord.setTitle(mCursor.getString(mCursor.getColumnIndex(
                Contract.T3dataEntry.COLUMN_NAME_TITLE)));

        mRecord.setAuthor(mCursor.getString(
                mCursor.getColumnIndex(Contract.T3dataEntry.COLUMN_NAME_AUTHOR)));

        mRecord.setCreated(mCursor.getInt(
                mCursor.getColumnIndex(Contract.T3dataEntry.COLUMN_NAME_CREATED)));

        mRecord.setPermanentLink(mCursor.getString(
                mCursor.getColumnIndex(Contract.T3dataEntry.COLUMN_NAME_PERMALINK)));

        mRecord.setSaved(mCursor.getInt(
                mCursor.getColumnIndex(Contract.T3dataEntry.COLUMN_NAME_SAVED)) != 0);

        mRecord.setSubRedditNamePrefix(mCursor.getString(
                mCursor.getColumnIndex(Contract.T3dataEntry.COLUMN_NAME_SUBREDDIT_NAME_PREFIXE)));

        mRecord.setDomain(mCursor.getString(
                mCursor.getColumnIndex(Contract.T3dataEntry.COLUMN_NAME_DOMAIN)));

        mRecord.setCreatedUtc(mCursor.getLong(
                mCursor.getColumnIndex(Contract.T3dataEntry.COLUMN_NAME_CREATED_UTC)));

        mRecord.setScore(mCursor.getInt(
                mCursor.getColumnIndex(Contract.T3dataEntry.COLUMN_NAME_SCORE)));

        mRecord.setNumComments(mCursor.getInt(
                mCursor.getColumnIndex(Contract.T3dataEntry.COLUMN_NAME_NUM_COMMENTS)));

        mRecord.setImagePreviewUrl(mCursor.getString(
                mCursor.getColumnIndex(Contract.T3dataEntry.COLUMN_NAME_PREVIEW_IMAGE_SOURCE_URL)));

        mRecord.setImagePreviewWidth(mCursor.getInt(
                mCursor.getColumnIndex(Contract.T3dataEntry.COLUMN_NAME_PREVIEW_IMAGE_SOURCE_WIDTH)));

        mRecord.setImagePreviewHeight(mCursor.getInt(
                mCursor.getColumnIndex(Contract.T3dataEntry.COLUMN_NAME_PREVIEW_IMAGE_SOURCE_HEIGHT)));


    }
}
