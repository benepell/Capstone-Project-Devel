package info.pelleritoudacity.android.rcapstone.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.SyncStateContract;
import android.text.TextUtils;

import java.util.List;

import info.pelleritoudacity.android.rcapstone.R;
import info.pelleritoudacity.android.rcapstone.model.reddit.Replies;
import info.pelleritoudacity.android.rcapstone.model.reddit.RepliesListing;
import info.pelleritoudacity.android.rcapstone.model.reddit.RepliesListingData;
import info.pelleritoudacity.android.rcapstone.model.reddit.T1;
import info.pelleritoudacity.android.rcapstone.model.reddit.T1Data;
import info.pelleritoudacity.android.rcapstone.model.reddit.T1ListingData;
import info.pelleritoudacity.android.rcapstone.utility.Costants;
import info.pelleritoudacity.android.rcapstone.utility.PrefManager;
import info.pelleritoudacity.android.rcapstone.utility.TextUtil;
import info.pelleritoudacity.android.rcapstone.utility.Utility;
import timber.log.Timber;

import static info.pelleritoudacity.android.rcapstone.utility.DateUtils.getSecondsTimeStamp;

public class T1Operation {

    private final Context mContext;
    private final List<T1> mModelT1;

    public T1Operation(Context context, List<T1> modelT1) {
        mContext = context;
        mModelT1 = modelT1;
    }

    private boolean insertData() {
        if ((mModelT1 != null) && (mModelT1.size() > 0)) {
            for (int i = 0; i < mModelT1.size(); i++) {
                if (!insertRecordData(i)) return false;
            }
            return true;

        }
        return false;
    }


    private boolean insertRecordData(int indexRoot) {

        if (mModelT1 == null) return false;

        ContentValues arrayCVT1 = new ContentValues();
        arrayCVT1.put(Contract.RedditEntry.COLUMN_NAME_KIND, mModelT1.get(indexRoot).getKind());
        arrayCVT1.put(Contract.RedditEntry.COLUMN_NAME_DATA, indexRoot + 1);

        // data
        T1Data dataModel = mModelT1.get(indexRoot).getData();
        ContentValues dataCV = new ContentValues();
        dataCV.put(Contract.DataEntry.COLUMN_NAME_DIST, dataModel.getDist());
        dataCV.put(Contract.DataEntry.COLUMN_NAME_MODHASH, dataModel.getModhash());

        if (dataModel.getAfter() == null) {
            dataCV.putNull(Contract.DataEntry.COLUMN_NAME_AFTER);
        } else {
            dataCV.put(Contract.DataEntry.COLUMN_NAME_AFTER, (byte) dataModel.getAfter());
        }

        if (dataModel.getBefore() == null) {
            dataCV.putNull(Contract.DataEntry.COLUMN_NAME_BEFORE);
        } else {
            dataCV.put(Contract.DataEntry.COLUMN_NAME_BEFORE, (byte) dataModel.getBefore());
        }

        int childrenId;

        int t1Size = mModelT1.get(indexRoot).getData().getChildren().size();

        ContentValues[] arrT1CV = new ContentValues[t1Size];

        for (int j = 0; j < t1Size; j++) {

            childrenId = j + 1;

            T1ListingData t1Model = mModelT1.get(indexRoot).getData().getChildren().get(j).getData();

            dataCV.put(Contract.DataEntry.COLUMN_NAME_CHILDRENS, childrenId);

            arrT1CV[j] = new ContentValues();

            DataUtils dataUtils = new DataUtils(mContext);

            if (t1Model != null) {

                arrT1CV[j].put(Contract.T1dataEntry.COLUMN_NAME_ID,
                        t1Model.getId());

                arrT1CV[j].put(Contract.T1dataEntry.COLUMN_NAME_LINK_ID,
                        t1Model.getLinkId());

                arrT1CV[j].put(Contract.T1dataEntry.COLUMN_NAME_URL,
                        t1Model.getUrl());

                arrT1CV[j].put(Contract.T1dataEntry.COLUMN_NAME_CHILDREN_ID,
                        childrenId);

                arrT1CV[j].put(Contract.T1dataEntry.COLUMN_NAME_SUBREDDITID,
                        t1Model.getSubredditId());

                dataUtils.putNullCV(arrT1CV[j], Contract.T1dataEntry.COLUMN_NAME_SUBREDDIT,
                        t1Model.getSubreddit());

                dataUtils.putNullCV(arrT1CV[j], Contract.T1dataEntry.COLUMN_NAME_NAME,
                        t1Model.getName());

                dataUtils.putNullCV(arrT1CV[j], Contract.T1dataEntry.COLUMN_NAME_UPS,
                        t1Model.getUps());

                dataUtils.putNullCV(arrT1CV[j], Contract.T1dataEntry.COLUMN_NAME_BODY,
                        t1Model.getBody());

                dataUtils.putNullCV(arrT1CV[j], Contract.T1dataEntry.COLUMN_NAME_DEPTH,
                        t1Model.getDepth());

                Timber.d("Xbody %s", t1Model.getBody());

                dataUtils.putNullCV(arrT1CV[j], Contract.T1dataEntry.COLUMN_NAME_BODY_HTML,
                        t1Model.getBodyHtml());

                dataUtils.putNullCV(arrT1CV[j], Contract.T1dataEntry.COLUMN_NAME_PARENT_ID,
                        t1Model.getParentId());

                dataUtils.putNullCV(arrT1CV[j], Contract.T1dataEntry.COLUMN_NAME_TITLE,
                        t1Model.getTitle());

                arrT1CV[j].put(Contract.T1dataEntry.COLUMN_NAME_DOWNS,
                        t1Model.getDowns());

                dataUtils.putNullCV(arrT1CV[j], Contract.T1dataEntry.COLUMN_NAME_SUBREDDIT,
                        TextUtil.normalizeSubRedditLink(t1Model.getSubreddit()));

                dataUtils.putNullCV(arrT1CV[j], Contract.T1dataEntry.COLUMN_NAME_APPROVEDATUTC,
                        t1Model.getApprovedAtUtc());

                arrT1CV[j].put(Contract.T1dataEntry.COLUMN_NAME_STICKIED,
                        Utility.boolToInt(t1Model.getStickied()));

                arrT1CV[j].put(Contract.T1dataEntry.COLUMN_NAME_SAVED,
                        Utility.boolToInt(t1Model.getSaved()));

                arrT1CV[j].put(Contract.T1dataEntry.COLUMN_NAME_ARCHIVED,
                        Utility.boolToInt(t1Model.getArchived()));

                arrT1CV[j].put(Contract.T1dataEntry.COLUMN_NAME_NOFOLLOW,
                        Utility.boolToInt(t1Model.getNoFollow()));

                arrT1CV[j].put(Contract.T1dataEntry.COLUMN_NAME_SENDREPLIES,
                        Utility.boolToInt(t1Model.getSendReplies()));

                arrT1CV[j].put(Contract.T1dataEntry.COLUMN_NAME_CANGILD,
                        Utility.boolToInt(t1Model.getCanGild()));

                arrT1CV[j].put(Contract.T1dataEntry.COLUMN_NAME_GILDED,
                        t1Model.getGilded());

                arrT1CV[j].put(Contract.T1dataEntry.COLUMN_NAME_CREATED,
                        t1Model.getCreated());

                arrT1CV[j].put(Contract.T1dataEntry.COLUMN_NAME_SCORE,
                        t1Model.getScore());

                dataUtils.putNullCV(arrT1CV[j], Contract.T1dataEntry.COLUMN_NAME_PERMALINK,
                        t1Model.getPermalink());

                dataUtils.putNullCV(arrT1CV[j], Contract.T1dataEntry.COLUMN_NAME_SUBREDDITTYPE,
                        t1Model.getSubredditType());

                arrT1CV[j].put(Contract.T1dataEntry.COLUMN_NAME_CREATEDUTC,
                        t1Model.getCreatedUtc());

                dataUtils.putNullCV(arrT1CV[j], Contract.T1dataEntry.COLUMN_NAME_AUTHOR,
                        t1Model.getAuthor());

                dataUtils.putNullCV(arrT1CV[j], Contract.T1dataEntry.COLUMN_NAME_MODNOTE,
                        Utility.boolToInt(t1Model.getCanModPost()));

                dataUtils.putNullCV(arrT1CV[j], Contract.T1dataEntry.COLUMN_NAME_HIDESCORE,
                        Utility.boolToInt(t1Model.getScoreHidden()));

                repliesDepth(t1Model.getReplies(), childrenId, Integer.parseInt(Costants.LIMIT_DEPTH_RESULTS));


//                    Timber.d("JSON %s", t1Model.getReplies().getData().getChildren().get(0).getData().getReplies().getData().getChildren().get(0).getData().getDepth());
            }

        }


        final Uri uriReddit = mContext.getContentResolver().insert(Contract.RedditEntry.CONTENT_URI, arrayCVT1);
        final Uri uriData = mContext.getContentResolver().insert(Contract.DataEntry.CONTENT_URI, dataCV);
        int countT1Data = mContext.getContentResolver().bulkInsert(Contract.T1dataEntry.CONTENT_URI, arrT1CV);

        return uriReddit != null && uriData != null && countT1Data != 0;
    }

    private void repliesDepth(Replies replies, int childrenId, int limit) {
        if ((replies != null) && (limit > 1)) {
            if (replies.getData() != null) {
                List<RepliesListing> listDepthL1 = replies.getData().getChildren();

                for (int i1 = 0; i1 < listDepthL1.size(); i1++) {
                    insertReplies(listDepthL1.get(i1).getData(), listDepthL1.get(i1).getData().getDepth(), childrenId);
                    Timber.d("JSON REPLIES DEPTH LEVEL 1 %s", listDepthL1.get(i1).getData().getDepth());

                    if ((listDepthL1.get(i1).getData().getReplies() != null) && (limit > 2)) {
                        List<RepliesListing> listDepthL2 = listDepthL1.get(i1).getData().getReplies().getData().getChildren();

                        for (int i2 = 0; i2 < listDepthL2.size(); i2++) {
                            insertReplies(listDepthL2.get(i2).getData(), listDepthL2.get(i2).getData().getDepth(), childrenId);
                            Timber.d("JSON REPLIES DEPTH LEVEL 2 %s", listDepthL2.get(i2).getData().getDepth());

                            if ((listDepthL2.get(i2).getData().getReplies() != null) && (limit > 3)) {
                                List<RepliesListing> listDepthL3 = listDepthL2.get(i2).getData().getReplies().getData().getChildren();

                                for (int i3 = 0; i3 < listDepthL3.size(); i3++) {
                                    insertReplies(listDepthL3.get(i3).getData(), listDepthL3.get(i3).getData().getDepth(), childrenId);
                                    Timber.d("JSON REPLIES DEPTH LEVEL 3 %s", listDepthL3.get(i3).getData().getDepth());

                                    if ((listDepthL3.get(i3).getData().getReplies() != null) && (limit > 4)) {
                                        List<RepliesListing> listDepthL4 = listDepthL3.get(i3).getData().getReplies().getData().getChildren();

                                        for (int i4 = 0; i4 < listDepthL4.size(); i4++) {
                                            insertReplies(listDepthL4.get(i4).getData(), listDepthL4.get(i4).getData().getDepth(), childrenId);
                                            Timber.d("JSON REPLIES DEPTH LEVEL 4 %s", listDepthL4.get(i4).getData().getDepth());

                                            if ((listDepthL4.get(i4).getData().getReplies() != null) && (limit > 5)) {
                                                List<RepliesListing> listDepthL5 = listDepthL4.get(i4).getData().getReplies().getData().getChildren();

                                                for (int i5 = 0; i5 < listDepthL5.size(); i5++) {
                                                    insertReplies(listDepthL5.get(i5).getData(), listDepthL5.get(i5).getData().getDepth(), childrenId);
                                                    Timber.d("JSON REPLIES DEPTH LEVEL 5 %s", listDepthL5.get(i5).getData().getDepth());

                                                    if ((listDepthL5.get(i5).getData().getReplies() != null) && (limit > 6)) {
                                                        List<RepliesListing> listDepthL6 = listDepthL5.get(i5).getData().getReplies().getData().getChildren();

                                                        for (int i6 = 0; i6 < listDepthL6.size(); i6++) {
                                                            insertReplies(listDepthL6.get(i6).getData(), listDepthL5.get(i6).getData().getDepth(), childrenId);
                                                            Timber.d("JSON REPLIES DEPTH LEVEL 6 %s", listDepthL6.get(i6).getData().getDepth());

                                                            if ((listDepthL6.get(i6).getData().getReplies() != null) && (limit > 7)) {
                                                                List<RepliesListing> listDepthL7 = listDepthL6.get(i6).getData().getReplies().getData().getChildren();

                                                                for (int i7 = 0; i7 < listDepthL7.size(); i7++) {
                                                                    insertReplies(listDepthL7.get(i7).getData(), listDepthL7.get(i7).getData().getDepth(), childrenId);
                                                                    Timber.d("JSON REPLIES DEPTH LEVEL 7 %s", listDepthL7.get(i7).getData().getDepth());

                                                                    if ((listDepthL7.get(i7).getData().getReplies() != null) && (limit > 8)) {
                                                                        List<RepliesListing> listDepthL8 = listDepthL7.get(i7).getData().getReplies().getData().getChildren();

                                                                        for (int i8 = 0; i8 < listDepthL8.size(); i8++) {
                                                                            insertReplies(listDepthL8.get(i8).getData(), listDepthL8.get(i8).getData().getDepth(), childrenId);
                                                                            Timber.d("JSON REPLIES DEPTH LEVEL 8 %s", listDepthL8.get(i8).getData().getDepth());

                                                                            if ((listDepthL8.get(i8).getData().getReplies() != null) && (limit > 9)) {
                                                                                List<RepliesListing> listDepthL9 = listDepthL8.get(i8).getData().getReplies().getData().getChildren();

                                                                                for (int i9 = 0; i9 < listDepthL9.size(); i9++) {
                                                                                    insertReplies(listDepthL9.get(i9).getData(), listDepthL9.get(i9).getData().getDepth(), childrenId);
                                                                                    Timber.d("JSON REPLIES DEPTH LEVEL 9 %s", listDepthL9.get(i9).getData().getDepth());

                                                                                    if ((listDepthL9.get(i9).getData().getReplies() != null) && (limit > 10)) {
                                                                                        List<RepliesListing> listDepthL10 = listDepthL9.get(i9).getData().getReplies().getData().getChildren();

                                                                                        for (int i10 = 0; i10 < listDepthL10.size(); i10++) {
                                                                                            insertReplies(listDepthL10.get(i10).getData(), listDepthL10.get(i10).getData().getDepth(), childrenId);
                                                                                            Timber.d("JSON REPLIES DEPTH LEVEL 10 %s", listDepthL10.get(i10).getData().getDepth());

                                                                                        }
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }

                                                }
                                            }

                                        }

                                    }

                                }

                            }
                        }
                    }

                }

            }

        }

    }


    private void insertReplies(RepliesListingData repliesListingData, int level, int childrenId) {

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

            dataUtils.putNullCV(cv, Contract.T1dataEntry.COLUMN_NAME_NAME,
                    repliesListingData.getName());

            dataUtils.putNullCV(cv, Contract.T1dataEntry.COLUMN_NAME_UPS,
                    repliesListingData.getUps());

            dataUtils.putNullCV(cv, Contract.T1dataEntry.COLUMN_NAME_PARENT_ID,
                    repliesListingData.getParentId());

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


            Uri uriReplies = mContext.getContentResolver().insert(Contract.T1dataEntry.CONTENT_URI, cv);
        }

    }

    public boolean saveData(String strId) {
        if (!TextUtils.isEmpty(strId)) {
            if (isDeleteData(strId)) {
                deleteCategory(strId);
            }
            return insertData();
        }
        return false;
    }

    public boolean isDeleteData(String strId) {
        String timestamp = null;
        Uri uri = Contract.T1dataEntry.CONTENT_URI;
        String selection = Contract.T1dataEntry.COLUMN_NAME_SUBREDDIT + " =?";
        String[] selectionArgs = {strId};

        Cursor cursor = null;
        boolean isDeleted = false;
        try {

            cursor = mContext.getContentResolver().query(uri, null, selection, selectionArgs, null);

            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                timestamp = cursor.getString(cursor.getColumnIndex(Contract.T1dataEntry.COLUMN_NAME_TIME_LAST_MODIFIED));
            }

            if (!TextUtils.isEmpty(timestamp)) {
                int timeUpdateDatabase = PrefManager.getIntGeneralSettings(mContext, R.string.pref_sync_frequency);
                isDeleted = getSecondsTimeStamp(timestamp) > timeUpdateDatabase;
            }

        } catch (Exception e) {

            Timber.d("DATABASE isDeleteT1 %s", e.getMessage());

        } finally {
            if ((cursor != null) && (!cursor.isClosed())) {
                cursor.close();
            }
        }

        return isDeleted;
    }

    public void deleteCategory(String id) {
        String where;
        Uri uri;
        String[] selectionArgs = {id};

        uri = Contract.T1dataEntry.CONTENT_URI;
        where = Contract.T1dataEntry.COLUMN_NAME_SUBREDDITNAMEPREFIXED + " =?";

        if (uri != null) {
            mContext.getContentResolver().delete(uri, where, selectionArgs);
        }

    }

    public void clearData() {
        mContext.getContentResolver().delete(Contract.T1dataEntry.CONTENT_URI, null, null);
        mContext.getContentResolver().delete(Contract.PrefSubRedditEntry.CONTENT_URI, null, null);
    }


}
