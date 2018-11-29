package info.pelleritoudacity.android.rcapstone.data.db.record;

import java.util.ArrayList;

import info.pelleritoudacity.android.rcapstone.data.db.entry.T3Entry;
import info.pelleritoudacity.android.rcapstone.data.model.record.RecordAdapterTitle;

public class TitleDetailRecord {


    private final T3Entry t3;
    private RecordAdapterTitle mRecord;
    private ArrayList<RecordAdapterTitle> recordList;

    public TitleDetailRecord(T3Entry t3) {
        this.t3 = t3;
    }

    public ArrayList<RecordAdapterTitle> getRecordList() {
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

        mRecord = new RecordAdapterTitle();

        mRecord.setSubReddit(t3.getSubreddit());

        mRecord.setSubRedditName(t3.getName());

        mRecord.setTitle(t3.getTitle());

        mRecord.setAuthor(t3.getAuthor());

        mRecord.setCreated(t3.getCreated());

        mRecord.setPermanentLink(t3.getPermalink());

        mRecord.setSaved(t3.getSaved() != 0);

        mRecord.setSubRedditNamePrefix(t3.getSubredditNamePrefix());

        mRecord.setDomain(t3.getDomain());

        mRecord.setCreatedUtc(t3.getCreatedUtc());

        mRecord.setScore(t3.getScore());

        mRecord.setNumComments(t3.getNumComments());

        mRecord.setImagePreviewUrl(t3.getPreviewImageSourceUrl());

        mRecord.setImagePreviewWidth(t3.getPreviewImageSourceWidth());

        mRecord.setImagePreviewHeight(t3.getPreviewImageSourceHeight());

    }
}
