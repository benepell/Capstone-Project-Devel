package info.pelleritoudacity.android.rcapstone.data.db.record;


import java.util.ArrayList;

import info.pelleritoudacity.android.rcapstone.data.db.entry.T1Entry;
import info.pelleritoudacity.android.rcapstone.data.model.record.RecordAdapterDetail;

public class DetailRecord {

    private final T1Entry t1;
    private RecordAdapterDetail mRecord;
    private ArrayList<RecordAdapterDetail> recordList;

    public DetailRecord(T1Entry t1) {
        this.t1 = t1;
    }

    public ArrayList<RecordAdapterDetail> getRecordList() {
        if (t1 != null) {
            initRecord();

            if (mRecord != null) {
                recordList = new ArrayList<>(1);
                recordList.add(mRecord);
            }
        }

        return recordList;
    }

    private void initRecord() {

        mRecord = new RecordAdapterDetail();

        mRecord.setId(t1.getId());

        mRecord.setSubReddit(t1.getSubreddit());

        mRecord.setStrId(t1.getNameId());

        mRecord.setChildrenId(t1.getChildrenId());

        mRecord.setSubRedditId(t1.getNameId());

        mRecord.setLinkId(String.valueOf(t1.getLinkId()));

        mRecord.setSubRedditName(t1.getName());

        mRecord.setUps(String.valueOf(t1.getUps()));

        mRecord.setTitle(t1.getTitle());

        mRecord.setVoteDowns(t1.getDowns());

        mRecord.setApprovedAtUtc(t1.getApprovedAtUtc());

        mRecord.setAuthor(t1.getAuthor());

        mRecord.setStickied(String.valueOf(t1.getStickied()));

        mRecord.setSaved(t1.getSaved() != 0);

        mRecord.setArchived(t1.getArchived() != 0);

        mRecord.setNoFollow(t1.getNoFollow() != 0);

        mRecord.setSendReplies(t1.getSendReplies() != 0);

        mRecord.setCanGild(t1.getCanGild() != 0);

        mRecord.setModNote(Integer.parseInt(t1.getModNote()) != 0);

        mRecord.setHideScore(t1.getHideScore() != 0);

        mRecord.setGilded(String.valueOf(t1.getCanGild()));

        mRecord.setCreated(t1.getCreated());

        mRecord.setScore(t1.getScore());

        mRecord.setDirScore(t1.getDirScore());

        mRecord.setPermanentLink(t1.getPermalink());

        mRecord.setSubRedditType(t1.getSubredditType());

        mRecord.setCreatedUtc(t1.getCreatedUtc());

        mRecord.setBody(t1.getBody());

        mRecord.setBodyHtml(t1.getBodyHtml());

        mRecord.setLinkId(String.valueOf(t1.getLinkId()));

        mRecord.setParentId(t1.getParentId());

        mRecord.setUrl(t1.getUrl());

        mRecord.setDepth(t1.getDepth());

        mRecord.setNumComments(t1.getNumComments());

        mRecord.setMoreComments(t1.getMoreComments());

    }

}
