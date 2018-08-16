package info.pelleritoudacity.android.rcapstone.data.db.Operation;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;

import java.util.List;

import info.pelleritoudacity.android.rcapstone.data.db.Contract;
import info.pelleritoudacity.android.rcapstone.data.db.util.DataUtils;
import info.pelleritoudacity.android.rcapstone.data.model.reddit.Replies;
import info.pelleritoudacity.android.rcapstone.data.model.reddit.T1;
import info.pelleritoudacity.android.rcapstone.data.model.reddit.T1Listing;
import info.pelleritoudacity.android.rcapstone.data.model.reddit.T1ListingData;
import info.pelleritoudacity.android.rcapstone.data.model.reddit.MoreJson;
import info.pelleritoudacity.android.rcapstone.data.model.reddit.MoreThing;
import info.pelleritoudacity.android.rcapstone.utility.Costant;
import info.pelleritoudacity.android.rcapstone.utility.DateUtil;
import info.pelleritoudacity.android.rcapstone.utility.Preference;
import info.pelleritoudacity.android.rcapstone.utility.TextUtil;
import info.pelleritoudacity.android.rcapstone.utility.Utility;
import timber.log.Timber;

public class T1Operation {

    private final Context mContext;


    public T1Operation(Context context) {
        mContext = context;
    }

    public boolean saveData(List<T1> modelT1, String strId) {
        if (!TextUtils.isEmpty(strId)) {
            deleteMore(strId);
            deleteCategory(strId);
            return insertData(modelT1);
        }
        return false;
    }

    public boolean saveMoreData(MoreJson moreJson) {
        try {
            if ((moreJson != null)) {

                String where = Contract.T1dataEntry.COLUMN_NAME_MORE_REPLIES.concat(" >=?");
                String[] selectArgs = {Costant.DETAIL_MORE_REPLIES};
                mContext.getContentResolver().delete(Contract.T1dataEntry.CONTENT_URI, where, selectArgs);
                return insertMoreData(moreJson);
            }

        } catch (IllegalStateException e) {
            Timber.e("save more data error %s", e.getMessage());

        }
        return false;
    }

    private boolean insertMoreData(MoreJson moreJson) {

        List<MoreThing> moreThings = moreJson.getData().getThings();

        int size = moreJson.getData().getThings().size();

        ContentValues[] arrContentValues = new ContentValues[size];

        try {
            for (int i = 0; i < size; i++) {
                arrContentValues[i] = getInsertCV(moreThings.get(i).getData(), arrContentValues[i], i + 1, true);
            }

            return mContext.getContentResolver().bulkInsert(Contract.T1dataEntry.CONTENT_URI, arrContentValues) > 0;

        } catch (IllegalStateException e) {
            Timber.e("Insert more data error %s", e.getMessage());
            return false;

        }

    }

    private boolean insertData(List<T1> modelT1) {

        if (modelT1 == null) return false;

        try {
            for (int x = 1; x < modelT1.size(); x++) {
                ContentValues cvT1 = new ContentValues();
                int i = 0;
                for (T1Listing t1Listings : modelT1.get(x).getData().getChildren()) {
                    cvT1 = getInsertCV(t1Listings.getData(), cvT1, i + 1, false);
                    mContext.getContentResolver().insert(Contract.T1dataEntry.CONTENT_URI, cvT1);
                    recursiveReplies(t1Listings.getData().getReplies(), i + 1);
                    i++;
                }
            }

            return true;

        } catch (IllegalStateException e) {
            Timber.e("Insert data error %s", e.getMessage());
            return false;

        }
    }

    private void insertReplies(T1ListingData repliesListingData, int level, int childrenId) {

        if ((repliesListingData != null) && (level > 0) && (childrenId > 0)) {

            DataUtils dataUtils = new DataUtils(mContext);

            ContentValues cv = new ContentValues();

            cv.put(Contract.T1dataEntry.COLUMN_NAME_ID,
                    repliesListingData.getId());

            cv.put(Contract.T1dataEntry.COLUMN_NAME_CHILDREN_ID,
                    childrenId);

            cv.put(Contract.T1dataEntry.COLUMN_NAME_LINK_ID,
                    repliesListingData.getLinkId());

            cv.put(Contract.T1dataEntry.COLUMN_NAME_SUBREDDITID,
                    repliesListingData.getSubredditId());

            dataUtils.putNullCV(cv, Contract.T1dataEntry.COLUMN_NAME_SUBREDDIT,
                    repliesListingData.getSubreddit());

            dataUtils.putNullCV(cv, Contract.T1dataEntry.COLUMN_NAME_PARENT_ID,
                    repliesListingData.getParentId());

            cv.put(Contract.T1dataEntry.COLUMN_NAME_TIME_LAST_MODIFIED, DateUtil.getNowTimeStamp());


            dataUtils.putNullCV(cv, Contract.T1dataEntry.COLUMN_NAME_NAME,
                    repliesListingData.getName());

            dataUtils.putNullCV(cv, Contract.T1dataEntry.COLUMN_NAME_UPS,
                    repliesListingData.getUps());

            dataUtils.putNullCV(cv, Contract.T1dataEntry.COLUMN_NAME_BODY,
                    repliesListingData.getBody());

            dataUtils.putNullCV(cv, Contract.T1dataEntry.COLUMN_NAME_DEPTH,
                    repliesListingData.getDepth());

            dataUtils.putNullCV(cv, Contract.T1dataEntry.COLUMN_NAME_BODY_HTML,
                    repliesListingData.getBodyHtml());

            // secondary add
            cv.put(Contract.T1dataEntry.COLUMN_NAME_DOWNS,
                    repliesListingData.getDowns());

            dataUtils.putNullCV(cv, Contract.T1dataEntry.COLUMN_NAME_SUBREDDIT,
                    TextUtil.normalizeSubRedditLink(repliesListingData.getSubreddit()));

            dataUtils.putNullCV(cv, Contract.T1dataEntry.COLUMN_NAME_APPROVEDATUTC,
                    repliesListingData.getApprovedAtUtc());

            cv.put(Contract.T1dataEntry.COLUMN_NAME_STICKIED,
                    Utility.boolToInt(repliesListingData.getStickied()));

            cv.put(Contract.T1dataEntry.COLUMN_NAME_SAVED,
                    Utility.boolToInt(repliesListingData.getSaved()));

            cv.put(Contract.T1dataEntry.COLUMN_NAME_ARCHIVED,
                    Utility.boolToInt(repliesListingData.getArchived()));

            cv.put(Contract.T1dataEntry.COLUMN_NAME_NOFOLLOW,
                    Utility.boolToInt(repliesListingData.getNoFollow()));

            cv.put(Contract.T1dataEntry.COLUMN_NAME_SENDREPLIES,
                    Utility.boolToInt(repliesListingData.getSendReplies()));

            cv.put(Contract.T1dataEntry.COLUMN_NAME_CANGILD,
                    Utility.boolToInt(repliesListingData.getCanGild()));

            cv.put(Contract.T1dataEntry.COLUMN_NAME_GILDED,
                    repliesListingData.getGilded());

            cv.put(Contract.T1dataEntry.COLUMN_NAME_CREATED,
                    repliesListingData.getCreated());

            cv.put(Contract.T1dataEntry.COLUMN_NAME_SCORE,
                    repliesListingData.getScore());

            dataUtils.putNullCV(cv, Contract.T1dataEntry.COLUMN_NAME_PERMALINK,
                    repliesListingData.getPermalink());

            dataUtils.putNullCV(cv, Contract.T1dataEntry.COLUMN_NAME_SUBREDDITTYPE,
                    repliesListingData.getSubredditType());

            cv.put(Contract.T1dataEntry.COLUMN_NAME_CREATEDUTC,
                    repliesListingData.getCreatedUtc());

            dataUtils.putNullCV(cv, Contract.T1dataEntry.COLUMN_NAME_AUTHOR,
                    repliesListingData.getAuthor());

            dataUtils.putNullCV(cv, Contract.T1dataEntry.COLUMN_NAME_MODNOTE,
                    Utility.boolToInt(repliesListingData.getCanModPost()));

            dataUtils.putNullCV(cv, Contract.T1dataEntry.COLUMN_NAME_HIDESCORE,
                    Utility.boolToInt(repliesListingData.getScoreHidden()));

            try {
                mContext.getContentResolver().insert(Contract.T1dataEntry.CONTENT_URI, cv);

            } catch (IllegalStateException e) {
                Timber.e("Insert Replies error %s", e.getMessage());

            }
        }

    }

    private void insertMore(T1ListingData repliesListingData) {

        if ((repliesListingData != null)) {

            int sizeCV = repliesListingData.getChildren().size();

            ContentValues cv = new ContentValues();

            int count = repliesListingData.getCount();
            String name = repliesListingData.getName();
            String strId = repliesListingData.getId();
            String strParentId = repliesListingData.getParentId();
            int depth = repliesListingData.getDepth();

            cv.put(Contract.T1MoresDataEntry.COLUMN_NAME_MORE_COUNT, count);
            cv.put(Contract.T1MoresDataEntry.COLUMN_NAME_MORE_NAME, name);
            cv.put(Contract.T1MoresDataEntry.COLUMN_NAME_MORE_ID, strId);
            cv.put(Contract.T1MoresDataEntry.COLUMN_NAME_MORES_PARENT_ID, strParentId);
            cv.put(Contract.T1MoresDataEntry.COLUMN_NAME_MORE_DEPTH, depth);

            String strMoreChildren = "";
            for (int i = 0; i < sizeCV - 1; i++) {
                //noinspection StringConcatenationInLoop
                strMoreChildren += repliesListingData.getChildren().get(i).concat(Costant.STRING_SEPARATOR);
            }
            if (sizeCV > 0) {
                strMoreChildren += repliesListingData.getChildren().get(sizeCV - 1);
                cv.put(Contract.T1MoresDataEntry.COLUMN_NAME_MORE_CHILDREN, strMoreChildren);

            }

            try {
                Uri uri = mContext.getContentResolver().insert(Contract.T1MoresDataEntry.CONTENT_URI, cv);

                if (uri != null) {
                    updateNumCommentsT1(mContext, strParentId, sizeCV, strMoreChildren);

                }

            } catch (IllegalStateException e) {
                Timber.e("Insert More error %s", e.getMessage());

            }


        }

    }

    private ContentValues getInsertCV(T1ListingData t1ListingData, ContentValues cv,
                                      int childrenId, boolean isMore) {
        if (t1ListingData != null) {
            cv = new ContentValues();

            DataUtils dataUtils = new DataUtils(mContext);

            if (isMore) {
                cv.put(Contract.T1dataEntry.COLUMN_NAME_MORE_REPLIES, childrenId + Costant.DETAIL_MORE_REPLIES);

            }

            cv.put(Contract.T1dataEntry.COLUMN_NAME_TIME_LAST_MODIFIED,
                    System.currentTimeMillis());

            cv.put(Contract.T1dataEntry.COLUMN_NAME_ID,
                    t1ListingData.getId());

            cv.put(Contract.T1dataEntry.COLUMN_NAME_TIME_LAST_MODIFIED,
                    System.currentTimeMillis());

            cv.put(Contract.T1dataEntry.COLUMN_NAME_LINK_ID,
                    t1ListingData.getLinkId());

            cv.put(Contract.T1dataEntry.COLUMN_NAME_URL,
                    t1ListingData.getUrl());

            cv.put(Contract.T1dataEntry.COLUMN_NAME_CHILDREN_ID,
                    childrenId);

            cv.put(Contract.T1dataEntry.COLUMN_NAME_SUBREDDITID,
                    t1ListingData.getSubredditId());

            dataUtils.putNullCV(cv, Contract.T1dataEntry.COLUMN_NAME_SUBREDDIT,
                    t1ListingData.getSubreddit());

            dataUtils.putNullCV(cv, Contract.T1dataEntry.COLUMN_NAME_NAME,
                    t1ListingData.getName());

            dataUtils.putNullCV(cv, Contract.T1dataEntry.COLUMN_NAME_UPS,
                    t1ListingData.getUps());

            dataUtils.putNullCV(cv, Contract.T1dataEntry.COLUMN_NAME_BODY,
                    t1ListingData.getBody());

            dataUtils.putNullCV(cv, Contract.T1dataEntry.COLUMN_NAME_DEPTH,
                    t1ListingData.getDepth());

            dataUtils.putNullCV(cv, Contract.T1dataEntry.COLUMN_NAME_BODY_HTML,
                    t1ListingData.getBodyHtml());

            dataUtils.putNullCV(cv, Contract.T1dataEntry.COLUMN_NAME_PARENT_ID,
                    t1ListingData.getParentId());

            dataUtils.putNullCV(cv, Contract.T1dataEntry.COLUMN_NAME_TITLE,
                    t1ListingData.getTitle());

            cv.put(Contract.T1dataEntry.COLUMN_NAME_DOWNS,
                    t1ListingData.getDowns());

            dataUtils.putNullCV(cv, Contract.T1dataEntry.COLUMN_NAME_SUBREDDIT,
                    TextUtil.normalizeSubRedditLink(t1ListingData.getSubreddit()));

            dataUtils.putNullCV(cv, Contract.T1dataEntry.COLUMN_NAME_APPROVEDATUTC,
                    t1ListingData.getApprovedAtUtc());

            if (t1ListingData.getStickied() != null) {
                cv.put(Contract.T1dataEntry.COLUMN_NAME_STICKIED,
                        Utility.boolToInt(t1ListingData.getStickied()));

            }

            cv.put(Contract.T1dataEntry.COLUMN_NAME_SAVED,
                    Utility.boolToInt(t1ListingData.getSaved()));

            cv.put(Contract.T1dataEntry.COLUMN_NAME_ARCHIVED,
                    Utility.boolToInt(t1ListingData.getArchived()));

            cv.put(Contract.T1dataEntry.COLUMN_NAME_NOFOLLOW,
                    Utility.boolToInt(t1ListingData.getNoFollow()));

            cv.put(Contract.T1dataEntry.COLUMN_NAME_SENDREPLIES,
                    Utility.boolToInt(t1ListingData.getSendReplies()));

            cv.put(Contract.T1dataEntry.COLUMN_NAME_CANGILD,
                    Utility.boolToInt(t1ListingData.getCanGild()));

            cv.put(Contract.T1dataEntry.COLUMN_NAME_GILDED,
                    t1ListingData.getGilded());

            cv.put(Contract.T1dataEntry.COLUMN_NAME_CREATED,
                    t1ListingData.getCreated());

            cv.put(Contract.T1dataEntry.COLUMN_NAME_SCORE,
                    t1ListingData.getScore());

            dataUtils.putNullCV(cv, Contract.T1dataEntry.COLUMN_NAME_PERMALINK,
                    t1ListingData.getPermalink());

            dataUtils.putNullCV(cv, Contract.T1dataEntry.COLUMN_NAME_SUBREDDITTYPE,
                    t1ListingData.getSubredditType());

            cv.put(Contract.T1dataEntry.COLUMN_NAME_CREATEDUTC,
                    t1ListingData.getCreatedUtc());

            dataUtils.putNullCV(cv, Contract.T1dataEntry.COLUMN_NAME_AUTHOR,
                    t1ListingData.getAuthor());

            dataUtils.putNullCV(cv, Contract.T1dataEntry.COLUMN_NAME_MODNOTE,
                    Utility.boolToInt(t1ListingData.getCanModPost()));

            dataUtils.putNullCV(cv, Contract.T1dataEntry.COLUMN_NAME_SORT_BY,
                    Preference.getSubredditSort(mContext));

            dataUtils.putNullCV(cv, Contract.T1dataEntry.COLUMN_NAME_HIDESCORE,
                    Utility.boolToInt(t1ListingData.getScoreHidden()));
        }

        return cv;
    }

    private void recursiveReplies(Replies r, int childrenId) {
        Replies replies = getReplies(r, childrenId);
        while ((replies != null)) {
            replies = getReplies(replies, childrenId);
        }
    }

    @SuppressWarnings("ConstantConditions")
    private Replies getReplies(Replies replies, int childrenId) {
        boolean added;
        if ((replies != null) && (replies.getData() != null)) {

            List<T1Listing> listings = replies.getData().getChildren();

            for (T1Listing t1Listings : listings) {
                if (t1Listings.getData().getReplies() != null) {
                    added = true;
                    insertReplies(t1Listings.getData(), t1Listings.getData().getDepth(), childrenId);

                    if (t1Listings.getData().getReplies().getData() != null) {

                        List<T1Listing> mores = t1Listings.getData().getReplies().getData().getChildren();

                        for (T1Listing t1more : mores) {

                            if ((t1Listings.getData().getReplies() != null) &&
                                    (t1more.getKind().contains(Costant.DEFAULT_MORE_KIND))) {
                                insertMore(t1more.getData());

                            }

                        }
                    } else if (!added) {
                        if ((t1Listings.getData() != null) && (t1Listings.getData().getReplies() != null) &&
                                (t1Listings.getKind().contains(Costant.DEFAULT_COMMENT_KIND))) {
                            insertReplies(t1Listings.getData(), t1Listings.getData().getDepth(), childrenId);

                        }
                    }
                }

                if ((t1Listings.getData().getReplies() != null) &&
                        (t1Listings.getData().getReplies().getKind().contains(Costant.DEFAULT_LISTING_KIND))) {
                    return t1Listings.getData().getReplies();

                } else //noinspection ConstantConditions
                    if ((t1Listings != null) &&
                            (t1Listings.getKind().contains(Costant.DEFAULT_MORE_KIND))) {
                        insertMore(t1Listings.getData());

                        return t1Listings.getData().getReplies();
                    }
            }

        }

        return null;
    }


    private void updateNumCommentsT1(Context context, String parentId, int count, String
            strMoreChildren) {

        if (!TextUtils.isEmpty(parentId) && count > 0) {

            String strRemoveT1ParentId = parentId.replaceAll(Costant.STR_PARENT_COMMENT, "");
            String strRemoveTParentId = strRemoveT1ParentId.replaceAll(Costant.STR_PARENT_LINK, "");
            ContentValues cvUpdate = new ContentValues();
            cvUpdate.put(Contract.T1dataEntry.COLUMN_NAME_NUMCOMMENTS, count);
            cvUpdate.put(Contract.T1dataEntry.COLUMN_NAME_MORECOMMENTS, strMoreChildren);

            try {
                context.getContentResolver().update(Contract.T1dataEntry.CONTENT_URI,
                        cvUpdate, Contract.T1dataEntry.COLUMN_NAME_ID + " =?",
                        new String[]{String.valueOf(strRemoveTParentId)});

            } catch (IllegalStateException e) {
                Timber.e("update num comments error %s", e.getMessage());
            }

        }

    }

    private void deleteCategory(String id) {
        String where;
        Uri uri;
        String[] selectionArgs = {id, Costant.STR_PARENT_LINK + id};
        uri = Contract.T1dataEntry.CONTENT_URI;
        where = Contract.T1dataEntry.COLUMN_NAME_ID + " =?" + " OR " +
                Contract.T1dataEntry.COLUMN_NAME_LINK_ID + " =?";

        try {
            mContext.getContentResolver().delete(uri, where, selectionArgs);

        } catch (IllegalStateException e) {
            Timber.e("delete category error %s", e.getMessage());

        }

    }

    private void deleteMore(String id) {
        Uri uri = Contract.T1MoresDataEntry.CONTENT_URI;
        String where = Contract.T1MoresDataEntry.COLUMN_NAME_MORE_ID + " >=?";
        String[] selectionArgs = {id};

        try {
            mContext.getContentResolver().delete(uri, where, selectionArgs);

        } catch (IllegalStateException e) {
            Timber.e("delete more error %s", e.getMessage());
        }


    }

    public void clearData() {
        try {
            mContext.getContentResolver().delete(Contract.T1dataEntry.CONTENT_URI, null, null);
            mContext.getContentResolver().delete(Contract.T1MoresDataEntry.CONTENT_URI, null, null);
            mContext.getContentResolver().delete(Contract.PrefSubRedditEntry.CONTENT_URI, null, null);

        } catch (IllegalStateException e) {
            Timber.e("clear data error %s", e.getMessage());
        }
    }
}
