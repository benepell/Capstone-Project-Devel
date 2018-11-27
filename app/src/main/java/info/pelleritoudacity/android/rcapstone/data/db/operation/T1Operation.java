package info.pelleritoudacity.android.rcapstone.data.db.operation;


import android.content.Context;
import android.text.TextUtils;

import java.util.List;
import java.util.Objects;

import info.pelleritoudacity.android.rcapstone.data.db.AppDatabase;
import info.pelleritoudacity.android.rcapstone.data.db.AppExecutors;
import info.pelleritoudacity.android.rcapstone.data.db.entry.PrefSubRedditEntry;
import info.pelleritoudacity.android.rcapstone.data.db.entry.T1Entry;
import info.pelleritoudacity.android.rcapstone.data.db.entry.T1MoreEntry;
import info.pelleritoudacity.android.rcapstone.data.model.reddit.MoreJson;
import info.pelleritoudacity.android.rcapstone.data.model.reddit.MoreThing;
import info.pelleritoudacity.android.rcapstone.data.model.reddit.Replies;
import info.pelleritoudacity.android.rcapstone.data.model.reddit.T1;
import info.pelleritoudacity.android.rcapstone.data.model.reddit.T1Listing;
import info.pelleritoudacity.android.rcapstone.data.model.reddit.T1ListingData;
import info.pelleritoudacity.android.rcapstone.utility.Costant;
import info.pelleritoudacity.android.rcapstone.utility.Preference;
import info.pelleritoudacity.android.rcapstone.utility.TextUtil;
import info.pelleritoudacity.android.rcapstone.utility.Utility;

public class T1Operation {

    private final Context mContext;
    private final AppDatabase mDb;


    public T1Operation(Context context, AppDatabase db) {
        mContext = context;
        mDb = db;
    }

    public void saveData(List<T1> modelT1, String strId) {
        if (!TextUtils.isEmpty(strId)) {
            deleteMore(strId);
            deleteCategory(strId);
            insertData(modelT1);
        }
    }

    public void saveMoreData(MoreJson moreJson) {
        if ((moreJson != null)) {

            AppExecutors.getInstance().diskIO().execute(() -> mDb.t1Dao().deleteRecordMoreReplies(String.valueOf(Costant.DETAIL_MORE_REPLIES)));

            insertMoreData(moreJson);
        }

    }

    private void insertMoreData(MoreJson moreJson) {

        List<MoreThing> moreThings = moreJson.getData().getThings();

        int size = moreJson.getData().getThings().size();

        T1Entry t1;

        for (int i = 0; i < size; i++) {
            t1 = new T1Entry();
            t1 = getInsertCV(moreThings.get(i).getData(), t1, i + 1, true);

            T1Entry finalT = t1;
            AppExecutors.getInstance().diskIO().execute(() -> mDb.t1Dao().insertRecord(finalT));

        }

    }

    private void insertData(List<T1> modelT1) {

        for (int x = 1; x < Objects.requireNonNull(modelT1).size(); x++) {
            T1Entry t1 = new T1Entry();
            T1MoreEntry t1MoreEntry = new T1MoreEntry();
            int i = 0;
            for (T1Listing t1Listings : modelT1.get(x).getData().getChildren()) {
                t1 = getInsertCV(t1Listings.getData(), t1, i + 1, false);
                T1Entry finalT = t1;
                AppExecutors.getInstance().diskIO().execute(() -> mDb.t1Dao().insertRecord(finalT));
                recursiveReplies(t1, t1MoreEntry, t1Listings.getData().getReplies(), i + 1);
                i++;
            }
        }

    }

    private void insertReplies(T1Entry t1, T1ListingData repliesListingData, int level, int childrenId) {

        if ((repliesListingData != null) && (level > 0) && (childrenId > 0)) {

            t1.setNameId(repliesListingData.getId());

            t1.setChildrenId(String.valueOf(childrenId));

            t1.setLinkId(repliesListingData.getLinkId());

            t1.setSubredditId(repliesListingData.getSubredditId());

            t1.setSubreddit(repliesListingData.getSubreddit());

            t1.setParentId(repliesListingData.getParentId());

            t1.setTimeLastModified(System.currentTimeMillis());

            t1.setName(repliesListingData.getName());

            t1.setUps(repliesListingData.getUps());

            t1.setBody(repliesListingData.getBody());

            t1.setDepth(repliesListingData.getDepth());

            t1.setBodyHtml(repliesListingData.getBodyHtml());

            // secondary add
            t1.setDowns(repliesListingData.getDowns());

            t1.setSubreddit(TextUtil.normalizeSubRedditLink(repliesListingData.getSubreddit()));

            if (repliesListingData.getApprovedAtUtc() != null) {
                t1.setApprovedAtUtc(repliesListingData.getApprovedAtUtc());
            }

            t1.setStickied(Utility.boolToInt(repliesListingData.getStickied()));

            t1.setSaved(Utility.boolToInt(repliesListingData.getSaved()));

            t1.setArchived(Utility.boolToInt(repliesListingData.getArchived()));

            t1.setNoFollow(Utility.boolToInt(repliesListingData.getNoFollow()));

            t1.setSendReplies(Utility.boolToInt(repliesListingData.getSendReplies()));

            t1.setCanGild(Utility.boolToInt(repliesListingData.getCanGild()));

            t1.setCanModPost(repliesListingData.getGilded());

            t1.setCreated(repliesListingData.getCreated());

            t1.setScore(repliesListingData.getScore());

            t1.setPermalink(repliesListingData.getPermalink());

            t1.setSubredditType(repliesListingData.getSubredditType());

            t1.setCreatedUtc(repliesListingData.getCreatedUtc());

            t1.setAuthor(repliesListingData.getAuthor());

            t1.setModNote(String.valueOf(Utility.boolToInt(repliesListingData.getCanModPost())));

            t1.setHideScore(Utility.boolToInt(repliesListingData.getScoreHidden()));

        }

    }


    private void insertMore(T1MoreEntry t1More, T1ListingData repliesListingData) {

        if ((repliesListingData != null)) {

            int sizeCV = repliesListingData.getChildren().size();

            int count = repliesListingData.getCount();
            String name = repliesListingData.getName();
            String strId = repliesListingData.getId();
            String strParentId = repliesListingData.getParentId();
            int depth = repliesListingData.getDepth();

            t1More.setMoreCount(count);
            t1More.setMoreName(name);
            t1More.setMoreId(strId);
            t1More.setMoresParentId(strParentId);
            t1More.setMoreDepth(depth);

            String strMoreChildren = "";
            for (int i = 0; i < sizeCV - 1; i++) {
                //noinspection StringConcatenationInLoop
                strMoreChildren += repliesListingData.getChildren().get(i).concat(Costant.STRING_SEPARATOR);
            }
            if (sizeCV > 0) {
                strMoreChildren += repliesListingData.getChildren().get(sizeCV - 1);
                t1More.setMoreChildren(strMoreChildren);

            }

            updateNumCommentsT1(strParentId, sizeCV, strMoreChildren);

        }
    }

    private T1Entry getInsertCV(T1ListingData t1ListingData, T1Entry t1,
                                int childrenId, boolean isMore) {
        if (t1ListingData != null) {

            if (isMore) {
                t1.setMoreReplies(childrenId + Costant.DETAIL_MORE_REPLIES);

            }

            t1.setTimeLastModified(System.currentTimeMillis());

            t1.setNameId(t1ListingData.getId());

            t1.setTimeLastModified(System.currentTimeMillis());

            t1.setLinkId(t1ListingData.getLinkId());

            t1.setUrl(t1ListingData.getUrl());

            t1.setChildrenId(String.valueOf(childrenId));

            t1.setSubredditId(t1ListingData.getSubredditId());

            t1.setSubreddit(t1ListingData.getSubreddit());

            t1.setName(t1ListingData.getName());

            if (t1ListingData.getUps() != null) {
                t1.setUps(t1ListingData.getUps());

            }

            t1.setBody(t1ListingData.getBody());

            t1.setDepth(t1ListingData.getDepth());

            t1.setBodyHtml(t1ListingData.getBodyHtml());

            t1.setParentId(t1ListingData.getParentId());

            t1.setTitle(t1ListingData.getTitle());

            if (t1ListingData.getDowns() != null) {
                t1.setDowns(Utility.boolToInt(t1ListingData.getStickied()));

            }

            t1.setSubreddit(TextUtil.normalizeSubRedditLink(t1ListingData.getSubreddit()));

            if (t1ListingData.getApprovedAtUtc() != null) {
                t1.setApprovedAtUtc(t1ListingData.getApprovedAtUtc());
            }

            if (t1ListingData.getStickied() != null) {
                t1.setStickied(Utility.boolToInt(t1ListingData.getStickied()));

            }

            t1.setSaved(Utility.boolToInt(t1ListingData.getSaved()));

            t1.setArchived(Utility.boolToInt(t1ListingData.getArchived()));

            t1.setNoFollow(Utility.boolToInt(t1ListingData.getNoFollow()));

            t1.setSendReplies(Utility.boolToInt(t1ListingData.getSendReplies()));

            t1.setCanGild(Utility.boolToInt(t1ListingData.getCanGild()));

            if (t1ListingData.getGilded() != null) {
                t1.setCanModPost(t1ListingData.getGilded());
            }

            if (t1ListingData.getCreated() != null) {
                t1.setCreated(t1ListingData.getCreated());
            }

            if (t1ListingData.getScore() != null) {
                t1.setScore(t1ListingData.getScore());
            }

            t1.setPermalink(t1ListingData.getPermalink());

            t1.setSubredditType(t1ListingData.getSubredditType());

            if(t1ListingData.getApprovedAtUtc()!=null) {
                t1.setCreatedUtc(t1ListingData.getCreatedUtc());
            }

            t1.setAuthor(t1ListingData.getAuthor());

            t1.setModNote(String.valueOf(Utility.boolToInt(t1ListingData.getCanModPost())));

            t1.setSortBy(Preference.getSubredditSort(mContext));

            t1.setHideScore(Utility.boolToInt(t1ListingData.getScoreHidden()));
        }

        return t1;
    }

    private void recursiveReplies(T1Entry t1, T1MoreEntry t1MoreEntry, Replies r, int childrenId) {
        Replies replies = getReplies(t1, t1MoreEntry, r, childrenId);
        while ((replies != null)) {
            replies = getReplies(t1, t1MoreEntry, replies, childrenId);
        }
    }

    @SuppressWarnings("ConstantConditions")
    private Replies getReplies(T1Entry t1, T1MoreEntry t1MoreEntry, Replies replies, int childrenId) {
        boolean added;
        if ((replies != null) && (replies.getData() != null)) {

            List<T1Listing> listings = replies.getData().getChildren();

            for (T1Listing t1Listings : listings) {
                if (t1Listings.getData().getReplies() != null) {
                    added = true;
                    insertReplies(t1, t1Listings.getData(), t1Listings.getData().getDepth(), childrenId);

                    if (t1Listings.getData().getReplies().getData() != null) {

                        List<T1Listing> mores = t1Listings.getData().getReplies().getData().getChildren();

                        for (T1Listing t1more : mores) {

                            if ((t1Listings.getData().getReplies() != null) &&
                                    (t1more.getKind().contains(Costant.DEFAULT_MORE_KIND))) {
                                insertMore(t1MoreEntry, t1more.getData());

                            }

                        }
                    } else if (!added) {
                        if ((t1Listings.getData() != null) && (t1Listings.getData().getReplies() != null) &&
                                (t1Listings.getKind().contains(Costant.DEFAULT_COMMENT_KIND))) {
                            insertReplies(t1, t1Listings.getData(), t1Listings.getData().getDepth(), childrenId);

                        }
                    }
                }

                if ((t1Listings.getData().getReplies() != null) &&
                        (t1Listings.getData().getReplies().getKind().contains(Costant.DEFAULT_LISTING_KIND))) {
                    return t1Listings.getData().getReplies();

                } else //noinspection ConstantConditions
                    if ((t1Listings != null) &&
                            (t1Listings.getKind().contains(Costant.DEFAULT_MORE_KIND))) {
                        insertMore(t1MoreEntry, t1Listings.getData());

                        return t1Listings.getData().getReplies();
                    }
            }

        }

        return null;
    }


    private void updateNumCommentsT1(String parentId, int count, String
            strMoreChildren) {

        if (!TextUtils.isEmpty(parentId) && count > 0) {

            String strRemoveT1ParentId = parentId.replaceAll(Costant.STR_PARENT_COMMENT, "");
            String strRemoveTParentId = strRemoveT1ParentId.replaceAll(Costant.STR_PARENT_LINK, "");

            AppExecutors.getInstance().diskIO().execute(() -> mDb.t1Dao().updateRecordMoreComments(count, strMoreChildren, strRemoveTParentId));

        }

    }

    private void deleteCategory(String id) {
        AppExecutors.getInstance().diskIO().execute(() -> mDb.t1Dao().deleteRecordByCategory(id, Costant.STR_PARENT_LINK + id));

    }

    private void deleteMore(String id) {
        AppExecutors.getInstance().diskIO().execute(() -> mDb.t1Dao().deleteRecordByNameId(id));

    }

    public void clearData() {
        T1Entry t1 = new T1Entry();
        T1MoreEntry t1More = new T1MoreEntry();
        PrefSubRedditEntry prefSubReddit = new PrefSubRedditEntry();

        AppExecutors.getInstance().diskIO().execute(() -> {
            mDb.t1Dao().deleteRecord(t1);
            mDb.t1MoreDao().deleteRecord(t1More);
            mDb.prefSubRedditDao().deleteRecord(prefSubReddit);
        });
    }

}
