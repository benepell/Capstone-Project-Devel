package info.pelleritoudacity.android.rcapstone.data.db.Record;

import android.database.Cursor;

import java.util.ArrayList;

import info.pelleritoudacity.android.rcapstone.data.db.Contract;
import info.pelleritoudacity.android.rcapstone.data.model.record.RecordAdapter;

public class RecordSubReddit {

    private final Cursor mCursor;
    private RecordAdapter mRecord;
    private ArrayList<RecordAdapter> recordList;

    public RecordSubReddit(Cursor cursor) {
        mCursor = cursor;
    }

    public ArrayList<RecordAdapter> getRecordList() {
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
        mRecord = new RecordAdapter();

        mRecord.setIdReddit(mCursor.getInt(
                mCursor.getColumnIndex(Contract.T3dataEntry._ID)));

        mRecord.setNameIdReddit(mCursor.getString(
                mCursor.getColumnIndex(Contract.T3dataEntry.COLUMN_NAME_ID)));

        mRecord.setNameReddit(mCursor.getString(
                mCursor.getColumnIndex(Contract.T3dataEntry.COLUMN_NAME_NAME)));

        mRecord.setSubRedditIdText(mCursor.getString(
                mCursor.getColumnIndex(Contract.T3dataEntry.COLUMN_NAME_SUBREDDIT_ID)));

        mRecord.setSubReddit(mCursor.getString(
                mCursor.getColumnIndex(Contract.T3dataEntry.COLUMN_NAME_SUBREDDIT)));

        mRecord.setSubRedditNamePrefix(mCursor.getString(
                mCursor.getColumnIndex(Contract.T3dataEntry.COLUMN_NAME_SUBREDDIT_NAME_PREFIXE)));

        mRecord.setSubRedditSubscriptions(mCursor.getInt(
                mCursor.getColumnIndex(Contract.T3dataEntry.COLUMN_NAME_SUBREDDIT_SUBSCRIBERS)));

        mRecord.setSaved(mCursor.getInt(
                mCursor.getColumnIndex(Contract.T3dataEntry.COLUMN_NAME_SAVED)) != 0);

        mRecord.setTitle(mCursor.getString(
                mCursor.getColumnIndex(Contract.T3dataEntry.COLUMN_NAME_TITLE)));

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

        mRecord.setVideoPreviewUrl(mCursor.getString(
                mCursor.getColumnIndex(Contract.T3dataEntry.COLUMN_NAME_VARIANT_VIDEO_MP4_URL)));

        mRecord.setVideoPreviewWidth(mCursor.getInt(
                mCursor.getColumnIndex(Contract.T3dataEntry.COLUMN_NAME_VARIANT_VIDEO_MP4_WIDTH)));

        mRecord.setVideoPreviewHeight(mCursor.getInt(
                mCursor.getColumnIndex(Contract.T3dataEntry.COLUMN_NAME_VARIANT_VIDEO_MP4_HEIGHT)));

        mRecord.setVideoUrl(mCursor.getString(
                mCursor.getColumnIndex(Contract.T3dataEntry.COLUMN_NAME_URL)));

        mRecord.setVideoTypeOembed(mCursor.getString(
                mCursor.getColumnIndex(Contract.T3dataEntry.COLUMN_NAME_MEDIA_TYPE)));

        mRecord.setVideoFrameOembed(mCursor.getString(
                mCursor.getColumnIndex(Contract.T3dataEntry.COLUMN_NAME_MEDIA_OEMBED_HTML)));

        mRecord.setVideoAuthorNameOembed(mCursor.getString(
                mCursor.getColumnIndex(Contract.T3dataEntry.COLUMN_NAME_MEDIA_OEMBED_AUTHOR_NAME)));

        mRecord.setVideoOembedWidth(mCursor.getInt(
                mCursor.getColumnIndex(Contract.T3dataEntry.COLUMN_NAME_MEDIA_OEMBED_WIDTH)));

        mRecord.setVideoOembedHeight(mCursor.getInt(
                mCursor.getColumnIndex(Contract.T3dataEntry.COLUMN_NAME_MEDIA_OEMBED_HEIGHT)));

        mRecord.setThumbnailUrlOembed(mCursor.getString(
                mCursor.getColumnIndex(Contract.T3dataEntry.COLUMN_NAME_MEDIA_OEMBED_THUMBNAIL_URL)));

        mRecord.setThumbnailOembedWidth(mCursor.getInt(
                mCursor.getColumnIndex(Contract.T3dataEntry.COLUMN_NAME_MEDIA_OEMBED_THUMBNAIL_WIDTH)));

        mRecord.setThumbnailOembedHeight(mCursor.getInt(
                mCursor.getColumnIndex(Contract.T3dataEntry.COLUMN_NAME_MEDIA_OEMBED_THUMBNAIL_HEIGHT)));


    }
}
