package info.pelleritoudacity.android.rcapstone.data.db.record;


import java.util.ArrayList;

import info.pelleritoudacity.android.rcapstone.data.db.entry.T3Entry;
import info.pelleritoudacity.android.rcapstone.data.model.record.RecordAdapter;

public class MainRecord {

    private final T3Entry t3;
    private RecordAdapter mRecord;
    private ArrayList<RecordAdapter> recordList;

    public MainRecord(T3Entry t3) {
        this.t3 = t3;
    }

    public ArrayList<RecordAdapter> getRecordList() {
        if (t3 != null) {
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

        mRecord.setIdReddit(t3.getId());

        mRecord.setNameIdReddit(t3.getNameId());

        mRecord.setNameReddit(t3.getName());

        mRecord.setSubRedditIdText(t3.getSubreddit());

        mRecord.setSubReddit(t3.getSubreddit());

        mRecord.setSubRedditNamePrefix(t3.getSubredditNamePrefix());

        mRecord.setSaved(t3.getSaved() != 0);

        mRecord.setTitle(t3.getTitle());

        mRecord.setAuthor(t3.getAuthor());

        mRecord.setDomain(t3.getDomain());

//        mRecord.setCreatedUtc(  t3.getCreatedUtc());

        mRecord.setScore(t3.getScore());

        mRecord.setDirScore(t3.getDirScore());

        mRecord.setNumComments(t3.getNumComments());

        mRecord.setImagePreviewUrl(t3.getPreviewImageSourceUrl());

        mRecord.setVideoPreviewUrl(t3.getPreviewVideoMp4Url());

        mRecord.setVideoUrl(t3.getUrl());

        mRecord.setVideoTypeOembed(t3.getMediaOembedType());

        mRecord.setVideoFrameOembed(t3.getMediaOembedHtml());

        mRecord.setVideoAuthorNameOembed(t3.getMediaOembedAuthorName());

        mRecord.setVideoOembedWidth(t3.getMediaOembedWidth());

        mRecord.setVideoOembedHeight(t3.getMediaOembedHeight());

        mRecord.setThumbnailUrlOembed(t3.getThumbnail());

    }
}
