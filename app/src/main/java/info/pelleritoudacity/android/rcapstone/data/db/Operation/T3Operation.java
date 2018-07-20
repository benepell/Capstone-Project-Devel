/*
 *
 * ______                _____ _
 * | ___ \              /  ___| |
 * | |_/ /___ __ _ _ __ \ `--.| |_ ___  _ __   ___
 * |    // __/ _` | '_ \ `--. \ __/ _ \| '_ \ / _ \
 * | |\ \ (_| (_| | |_) /\__/ / || (_) | | | |  __/
 * \_| \_\___\__,_| .__/\____/ \__\___/|_| |_|\___|
 *                | |
 *                |_|
 *
 * Copyright (C) 2018 Benedetto Pellerito
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package info.pelleritoudacity.android.rcapstone.data.db.Operation;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.text.TextUtils;

import java.util.ArrayList;

import info.pelleritoudacity.android.rcapstone.data.db.Contract;
import info.pelleritoudacity.android.rcapstone.data.db.util.DataUtils;
import info.pelleritoudacity.android.rcapstone.data.model.ModelContent;
import info.pelleritoudacity.android.rcapstone.data.model.reddit.Media;
import info.pelleritoudacity.android.rcapstone.data.model.reddit.OembedMedia;
import info.pelleritoudacity.android.rcapstone.data.model.reddit.RedditVideoPreview;
import info.pelleritoudacity.android.rcapstone.data.model.reddit.T1ListingData;
import info.pelleritoudacity.android.rcapstone.data.model.reddit.T3;
import info.pelleritoudacity.android.rcapstone.data.model.reddit.T3Data;
import info.pelleritoudacity.android.rcapstone.data.model.reddit.T3Listing;
import info.pelleritoudacity.android.rcapstone.data.model.reddit.T3ListingData;
import info.pelleritoudacity.android.rcapstone.data.model.reddit.Variants;
import info.pelleritoudacity.android.rcapstone.utility.DateUtil;
import info.pelleritoudacity.android.rcapstone.utility.Preference;
import info.pelleritoudacity.android.rcapstone.utility.TextUtil;
import info.pelleritoudacity.android.rcapstone.utility.Utility;
import timber.log.Timber;

import static info.pelleritoudacity.android.rcapstone.utility.DateUtil.getSecondsTimeStamp;

public class T3Operation {

    private final Context mContext;
    private final T3 mModelT3;

    public T3Operation(Context context, T3 modelT3) {
        mContext = context;
        mModelT3 = modelT3;
    }

    private boolean insertData(String target) {

        if (mModelT3 == null) return false;

        // subReddit
        ContentValues subRedditCVT3 = new ContentValues();
        subRedditCVT3.put(Contract.RedditEntry.COLUMN_NAME_KIND, mModelT3.getKind());
        subRedditCVT3.put(Contract.RedditEntry.COLUMN_NAME_DATA, 1);

        // data
        T3Data dataModel = mModelT3.getData();
        ContentValues dataCV = new ContentValues();
        dataCV.put(Contract.DataEntry.COLUMN_NAME_AFTER, dataModel.getAfter());
        dataCV.put(Contract.DataEntry.COLUMN_NAME_DIST, dataModel.getDist());
        dataCV.put(Contract.DataEntry.COLUMN_NAME_MODHASH, dataModel.getModhash());


        if (dataModel.getBefore() == null) {
            dataCV.putNull(Contract.DataEntry.COLUMN_NAME_BEFORE);
        } else {
            dataCV.put(Contract.DataEntry.COLUMN_NAME_BEFORE, (byte) dataModel.getBefore());
        }

        int t3Size = mModelT3.getData().getChildren().size();

        ContentValues[] arrT3CV = new ContentValues[t3Size];

        DataUtils dataUtils = new DataUtils(mContext);

        ImageContentOperation imageContentOperation = new ImageContentOperation(mContext);

        VideoContentOperation videoContentOperation = new VideoContentOperation(mContext);

        for (int i = 0; i < t3Size; i++) {

            T3ListingData t3Model = mModelT3.getData().getChildren().get(i).getData();

            dataCV.put(Contract.DataEntry.COLUMN_NAME_CHILDRENS, i + 1);

            arrT3CV[i] = new ContentValues();

            if (t3Model != null) {

                // todo insert here insertCV
                arrT3CV[i] = getInsertCV(mContext, t3Model, arrT3CV[i], target, i + 1);

                if (t3Model.getPreview() != null) {

                    ArrayList<ModelContent> modelContentImageArrayList = new ArrayList<>();

                    boolean isOriginalSizeContent = Preference.isOriginalSizeContent(mContext);

                    ArrayList<ModelContent> optimizeArrayList = imageContentOperation.showImageT3(t3Model, isOriginalSizeContent);

                    if (optimizeArrayList != null) {
                        modelContentImageArrayList.add(optimizeArrayList.get(0));
                    }

                    String previewUrl = modelContentImageArrayList.get(0).getUrl();
                    int previewWidth = modelContentImageArrayList.get(0).getWidth();
                    int previewHeight = modelContentImageArrayList.get(0).getHeight();

                    dataUtils.putNullCV(arrT3CV[i], Contract.T3dataEntry.COLUMN_NAME_PREVIEW_IMAGE_SOURCE_URL,
                            previewUrl);

                    dataUtils.putNullCV(arrT3CV[i], Contract.T3dataEntry.COLUMN_NAME_PREVIEW_IMAGE_SOURCE_WIDTH,
                            previewWidth);

                    dataUtils.putNullCV(arrT3CV[i], Contract.T3dataEntry.COLUMN_NAME_PREVIEW_IMAGE_SOURCE_HEIGHT,
                            previewHeight);


                    RedditVideoPreview redditVideoPreview = t3Model.getPreview().getRedditVideoPreview();

                    arrT3CV[i] = getInsertVideoPreviewCV(mContext, redditVideoPreview, arrT3CV[i]);


                    Variants variants = t3Model.getPreview().getImages().get(0).getVariants();

                    if (variants != null) {

                        ArrayList<ModelContent> modelContentArrayList = new ArrayList<>();
                        ArrayList<ModelContent> arrayList = videoContentOperation.showVideoContent(t3Model, isOriginalSizeContent);
                        if (arrayList != null) {
                            modelContentArrayList.add(arrayList.get(0));

                            String videoMp4Url = modelContentArrayList.get(0).getUrl();
                            int videoMp4Width = modelContentArrayList.get(0).getWidth();
                            int videoMp4Height = modelContentArrayList.get(0).getHeight();

                            dataUtils.putNullCV(arrT3CV[i], Contract.T3dataEntry.COLUMN_NAME_VARIANT_VIDEO_MP4_URL,
                                    videoMp4Url);

                            dataUtils.putNullCV(arrT3CV[i], Contract.T3dataEntry.COLUMN_NAME_VARIANT_VIDEO_MP4_WIDTH,
                                    videoMp4Width);

                            dataUtils.putNullCV(arrT3CV[i], Contract.T3dataEntry.COLUMN_NAME_VARIANT_VIDEO_MP4_HEIGHT,
                                    videoMp4Height);

                        }
                    }

                    Media mediaModel = t3Model.getMedia();

                    if (mediaModel != null) {
                        dataUtils.putNullCV(arrT3CV[i], Contract.T3dataEntry.COLUMN_NAME_MEDIA_TYPE,
                                mediaModel.getType());

                        arrT3CV[i] = getInsertOembedMediaCV(mContext, mediaModel.getOembed(), arrT3CV[i]);

                    }

                }
            }

        }

        try {
            final Uri uriReddit = mContext.getContentResolver().insert(Contract.RedditEntry.CONTENT_URI, subRedditCVT3);
            final Uri uriData = mContext.getContentResolver().insert(Contract.DataEntry.CONTENT_URI, dataCV);
            int countT3Data = mContext.getContentResolver().bulkInsert(Contract.T3dataEntry.CONTENT_URI, arrT3CV);

            return uriReddit != null && uriData != null && countT3Data != 0;

        } catch (IllegalStateException e) {
            Timber.e("insert data error %s", e.getCause());

        }

        return false;

    }


    private ContentValues getInsertOembedMediaCV(Context context, OembedMedia o, ContentValues cv) {

        if (o != null) {
            if (cv == null) cv = new ContentValues();

            DataUtils dataUtils = new DataUtils(context);

            dataUtils.putNullCV(cv, Contract.T3dataEntry.COLUMN_NAME_MEDIA_OEMBED_PROVIDER_URL,
                    o.getProviderUrl());

            dataUtils.putNullCV(cv, Contract.T3dataEntry.COLUMN_NAME_MEDIA_OEMBED_TITLE,
                    o.getTitle());

            dataUtils.putNullCV(cv, Contract.T3dataEntry.COLUMN_NAME_MEDIA_OEMBED_TYPE,
                    o.getType());

            dataUtils.putNullCV(cv, Contract.T3dataEntry.COLUMN_NAME_MEDIA_OEMBED_HTML,
                    o.getHtml());

            dataUtils.putNullCV(cv, Contract.T3dataEntry.COLUMN_NAME_MEDIA_OEMBED_AUTHOR_NAME,
                    o.getAuthorName());

            dataUtils.putNullCV(cv, Contract.T3dataEntry.COLUMN_NAME_MEDIA_OEMBED_AUTHOR_URL,
                    o.getAuthorUrl());

            dataUtils.putNullCV(cv, Contract.T3dataEntry.COLUMN_NAME_MEDIA_OEMBED_WIDTH,
                    o.getWidth());

            dataUtils.putNullCV(cv, Contract.T3dataEntry.COLUMN_NAME_MEDIA_OEMBED_HEIGHT,
                    o.getHeight());

            dataUtils.putNullCV(cv, Contract.T3dataEntry.COLUMN_NAME_MEDIA_OEMBED_THUMBNAIL_WIDTH,
                    o.getThumbnailWidth());

            dataUtils.putNullCV(cv, Contract.T3dataEntry.COLUMN_NAME_MEDIA_OEMBED_THUMBNAIL_HEIGHT,
                    o.getThumbnailHeight());

            dataUtils.putNullCV(cv, Contract.T3dataEntry.COLUMN_NAME_MEDIA_OEMBED_THUMBNAIL_URL,
                    o.getThumbnailUrl());

            dataUtils.putNullCV(cv, Contract.T3dataEntry.COLUMN_NAME_MEDIA_OEMBED_VERSION,
                    o.getVersion());

        }
        return cv;
    }


    private ContentValues getInsertVideoPreviewCV(Context context, RedditVideoPreview r, ContentValues cv) {

        if (r != null) {
            if (cv == null) cv = new ContentValues();

            DataUtils dataUtils = new DataUtils(context);

            dataUtils.putNullCV(cv, Contract.T3dataEntry.COLUMN_NAME_PREVIEW_VIDEO_HLS_URL,
                    r.getHlsUrl());

            dataUtils.putNullCV(cv, Contract.T3dataEntry.COLUMN_NAME_PREVIEW_VIDEO_DASH_URL,
                    r.getDashUrl());

            dataUtils.putNullCV(cv, Contract.T3dataEntry.COLUMN_NAME_PREVIEW_VIDEO_SCRUBBER_MEDIA_URL,
                    r.getScrubberMediaUrl());

            dataUtils.putNullCV(cv, Contract.T3dataEntry.COLUMN_NAME_PREVIEW_VIDEO_FALLBACK_URL,
                    r.getFallbackUrl());

            dataUtils.putNullCV(cv, Contract.T3dataEntry.COLUMN_NAME_PREVIEW_VIDEO_TRANSCODING_STATUS,
                    r.getTranscodingStatus());

            dataUtils.putNullCV(cv, Contract.T3dataEntry.COLUMN_NAME_PREVIEW_VIDEO_DURATION,
                    r.getDuration());

            dataUtils.putNullCV(cv, Contract.T3dataEntry.COLUMN_NAME_PREVIEW_VIDEO_WIDTH,
                    r.getWidth());

            dataUtils.putNullCV(cv, Contract.T3dataEntry.COLUMN_NAME_PREVIEW_VIDEO_HEIGHT,
                    r.getHeight());

            dataUtils.putNullCV(cv, Contract.T3dataEntry.COLUMN_NAME_PREVIEW_IS_VIDEO_GIF,
                    r.getIsGif());
        }
        return cv;

    }

    private ContentValues getInsertCV(Context context, T3ListingData
            t3ListingData, ContentValues cv,
                                      String target, int childrenId) {

        if (t3ListingData != null) {
            if (cv == null) cv = new ContentValues();

            DataUtils dataUtils = new DataUtils(context);

            cv.put(Contract.T3dataEntry.COLUMN_NAME_IS_CROSSPOSTABLE,
                    Utility.boolToInt(t3ListingData.getIsCrosspostable()));

            cv.put(Contract.T3dataEntry.COLUMN_NAME_SUBREDDIT_ID,
                    t3ListingData.getSubredditId());

            dataUtils.putNullCV(cv, Contract.T3dataEntry.COLUMN_NAME_APPROVED_AT_UTC,
                    t3ListingData.getApprovedAtUtc());

            cv.put(Contract.T3dataEntry.COLUMN_NAME_WLS,
                    t3ListingData.getWls());

            dataUtils.putNullCV(cv, Contract.T3dataEntry.COLUMN_NAME_MOD_REASON_BY,
                    t3ListingData.getModReasonBy());

            dataUtils.putNullCV(cv, Contract.T3dataEntry.COLUMN_NAME_BANNED_BY,
                    t3ListingData.getBannedBy());

            dataUtils.putNullCV(cv, Contract.T3dataEntry.COLUMN_NAME_NUM_REPORTS,
                    t3ListingData.getNumReports());

            dataUtils.putNullCV(cv, Contract.T3dataEntry.COLUMN_NAME_REMOVAL_REASON,
                    t3ListingData.getRemovalReason());

            dataUtils.putNullCV(cv, Contract.T3dataEntry.COLUMN_NAME_THUMBNAIL_WIDTH,
                    t3ListingData.getThumbnailWidth());

            dataUtils.putNullCV(cv, Contract.T3dataEntry.COLUMN_NAME_SUBREDDIT,
                    t3ListingData.getSubreddit());

            dataUtils.putNullCV(cv, Contract.T3dataEntry.COLUMN_NAME_SELFTEXT_HTML,
                    t3ListingData.getSelftextHtml());

            dataUtils.putNullCV(cv, Contract.T3dataEntry.COLUMN_NAME_SELFTEXT,
                    t3ListingData.getSelftext());

            dataUtils.putNullCV(cv, Contract.T3dataEntry.COLUMN_NAME_LIKES,
                    t3ListingData.getLikes());

            dataUtils.putNullCV(cv, Contract.T3dataEntry.COLUMN_NAME_SUGGESTED_SORT,
                    t3ListingData.getSuggestedSort());

            cv.put(Contract.T3dataEntry.COLUMN_NAME_IS_REDDIT_MEDIA_DOMAIN,
                    Utility.boolToInt(t3ListingData.getIsRedditMediaDomain()));

            cv.put(Contract.T3dataEntry.COLUMN_NAME_SAVED,
                    Utility.boolToInt(t3ListingData.getSaved()));

            cv.put(Contract.T3dataEntry.COLUMN_NAME_CHILDREN_ID,
                    childrenId);

            cv.put(Contract.T3dataEntry.COLUMN_NAME_ID,
                    t3ListingData.getId());

            dataUtils.putNullCV(cv, Contract.T3dataEntry.COLUMN_NAME_BANNED_AT_UTC,
                    t3ListingData.getBannedAtUtc());

            dataUtils.putNullCV(cv, Contract.T3dataEntry.COLUMN_NAME_MOD_REASON_TITLE,
                    t3ListingData.getModReasonTitle());

            dataUtils.putNullCV(cv, Contract.T3dataEntry.COLUMN_NAME_VIEW_COUNT,
                    t3ListingData.getViewCount());

            cv.put(Contract.T3dataEntry.COLUMN_NAME_ARCHIVED,
                    Utility.boolToInt(t3ListingData.getArchived()));

            cv.put(Contract.T3dataEntry.COLUMN_NAME_CLICKED,
                    Utility.boolToInt(t3ListingData.getClicked()));


            cv.put(Contract.T3dataEntry.COLUMN_NAME_NO_FOLLOW,
                    Utility.boolToInt(t3ListingData.getNoFollow()));

            dataUtils.putNullCV(cv, Contract.T3dataEntry.COLUMN_NAME_AUTHOR,
                    t3ListingData.getAuthor());

            cv.put(Contract.T3dataEntry.COLUMN_NAME_NUM_CROSSPOSTS,
                    t3ListingData.getNumCrossposts());

            dataUtils.putNullCV(cv, Contract.T3dataEntry.COLUMN_NAME_LINK_FLAIR_TEXT,
                    t3ListingData.getLinkFlairText());

            cv.put(Contract.T3dataEntry.COLUMN_NAME_CAN_MOD_POST,
                    Utility.boolToInt(t3ListingData.getCanModPost()));

            cv.put(Contract.T3dataEntry.COLUMN_NAME_SEND_REPLIES,
                    Utility.boolToInt(t3ListingData.getSendReplies()));

            cv.put(Contract.T3dataEntry.COLUMN_NAME_PINNED,
                    Utility.boolToInt(t3ListingData.getPinned()));

            cv.put(Contract.T3dataEntry.COLUMN_NAME_SCORE,
                    t3ListingData.getScore());

            dataUtils.putNullCV(cv, Contract.T3dataEntry.COLUMN_NAME_APPROVED_BY,
                    t3ListingData.getApprovedBy());

            cv.put(Contract.T3dataEntry.COLUMN_NAME_OVER_18,
                    t3ListingData.getOver18());

            dataUtils.putNullCV(cv, Contract.T3dataEntry.COLUMN_NAME_REPORT_REASONS,
                    t3ListingData.getReportReasons());

            dataUtils.putNullCV(cv, Contract.T3dataEntry.COLUMN_NAME_DOMAIN,
                    t3ListingData.getDomain());

            cv.put(Contract.T3dataEntry.COLUMN_NAME_HIDDEN,
                    Utility.boolToInt(t3ListingData.getHidden()));

            cv.put(Contract.T3dataEntry.COLUMN_NAME_PWLS,
                    t3ListingData.getPwls());

            dataUtils.putNullCV(cv, Contract.T3dataEntry.COLUMN_NAME_THUMBNAIL,
                    t3ListingData.getThumbnail());

            cv.put(Contract.T3dataEntry.COLUMN_NAME_EDITED,
                    Utility.toInt(t3ListingData.getEdited()));

            dataUtils.putNullCV(cv, Contract.T3dataEntry.COLUMN_NAME_LINK_FLAIR_CSS_CLASS,
                    t3ListingData.getLinkFlairCssClass());

            dataUtils.putNullCV(cv, Contract.T3dataEntry.COLUMN_NAME_AUTHOR_FLAIR_CSS_CLASS,
                    t3ListingData.getAuthorFlairCssClass());

            cv.put(Contract.T3dataEntry.COLUMN_NAME_CONTEST_MODE,
                    Utility.boolToInt(t3ListingData.getContestMode()));

            cv.put(Contract.T3dataEntry.COLUMN_NAME_GILDED,
                    t3ListingData.getGilded());

            cv.put(Contract.T3dataEntry.COLUMN_NAME_LOCKED,
                    Utility.boolToInt(t3ListingData.getLocked()));

            cv.put(Contract.T3dataEntry.COLUMN_NAME_DOWNS,
                    t3ListingData.getDowns());

            cv.put(Contract.T3dataEntry.COLUMN_NAME_STICKIED,
                    Utility.boolToInt(t3ListingData.getStickied()));

            cv.put(Contract.T3dataEntry.COLUMN_NAME_VISITED,
                    Utility.boolToInt(t3ListingData.getVisited()));

            cv.put(Contract.T3dataEntry.COLUMN_NAME_CAN_GILD,
                    Utility.boolToInt(t3ListingData.getCanGild()));

            dataUtils.putNullCV(cv, Contract.T3dataEntry.COLUMN_NAME_THUMBNAIL_HEIGHT,
                    t3ListingData.getThumbnailHeight());

            dataUtils.putNullCV(cv, Contract.T3dataEntry.COLUMN_NAME_NAME,
                    t3ListingData.getName());

            cv.put(Contract.T3dataEntry.COLUMN_NAME_SPOILER,
                    Utility.boolToInt(t3ListingData.getSpoiler()));

            dataUtils.putNullCV(cv, Contract.T3dataEntry.COLUMN_NAME_PERMALINK,
                    t3ListingData.getPermalink());

            dataUtils.putNullCV(cv, Contract.T3dataEntry.COLUMN_NAME_SUBREDDIT_TYPE,
                    t3ListingData.getSubredditType());

            dataUtils.putNullCV(cv, Contract.T3dataEntry.COLUMN_NAME_PARENT_WHITELIST_STATUS,
                    t3ListingData.getParentWhitelistStatus());

            cv.put(Contract.T3dataEntry.COLUMN_NAME_HIDE_SCORE,
                    Utility.boolToInt(t3ListingData.getHideScore()));

            cv.put(Contract.T3dataEntry.COLUMN_NAME_URL,
                    t3ListingData.getUrl());

            cv.put(Contract.T3dataEntry.COLUMN_NAME_CREATED,
                    t3ListingData.getCreated());

            dataUtils.putNullCV(cv, Contract.T3dataEntry.COLUMN_NAME_AUTHOR_FLAIR_TEXT,
                    t3ListingData.getAuthorFlairText());

            dataUtils.putNullCV(cv, Contract.T3dataEntry.COLUMN_NAME_QUARANTINE,
                    Utility.boolToInt(t3ListingData.getQuarantine()));

            dataUtils.putNullCV(cv, Contract.T3dataEntry.COLUMN_NAME_TITLE,
                    t3ListingData.getTitle());

            cv.put(Contract.T3dataEntry.COLUMN_NAME_CREATED_UTC,
                    t3ListingData.getCreatedUtc());

            dataUtils.putNullCV(cv, Contract.T3dataEntry.COLUMN_NAME_SUBREDDIT_NAME_PREFIXE,
                    t3ListingData.getSubredditNamePrefixed());

            dataUtils.putNullCV(cv, Contract.T3dataEntry.COLUMN_NAME_UPS,
                    t3ListingData.getUps());

            cv.put(Contract.T3dataEntry.COLUMN_NAME_NUM_COMMENTS,
                    t3ListingData.getNumComments());
            // todo change media
            /*    dataUtils.putNullCV(cv, Contract.T3dataEntry.COLUMN_NAME_MEDIA,
                        t3ListingData.getMedia());
*/

            cv.put(Contract.T3dataEntry.COLUMN_NAME_IS_SELF,
                    Utility.boolToInt(t3ListingData.getIsSelf()));

            dataUtils.putNullCV(cv, Contract.T3dataEntry.COLUMN_NAME_WHITELIST_STATUS,
                    t3ListingData.getWhitelistStatus());

            dataUtils.putNullCV(cv, Contract.T3dataEntry.COLUMN_NAME_MOD_NOTE,
                    t3ListingData.getModNote());


            cv.put(Contract.T3dataEntry.COLUMN_NAME_IS_VIDEO,
                    Utility.boolToInt(t3ListingData.getIsVideo()));

            dataUtils.putNullCV(cv, Contract.T3dataEntry.COLUMN_NAME_DISTINGUISHED,
                    t3ListingData.getDistinguished());

            String stringCategoryNormalizeSub = TextUtil.normalizeSubRedditLink(t3ListingData.getSubreddit());
            dataUtils.putNullCV(cv, Contract.T3dataEntry.COLUMN_NAME_SUBREDDIT,
                    stringCategoryNormalizeSub);

            dataUtils.putNullCV(cv, Contract.T3dataEntry.COLUMN_NAME_TARGET,
                    target);

            dataUtils.putNullCV(cv, Contract.T3dataEntry.COLUMN_NAME_SORT_BY,
                    Preference.getSubredditSort(context));


        }
        return cv;
    }


    public boolean saveData(String category, String target) {
        if (!TextUtils.isEmpty(category)) {
            if (isDeleteData(category, target)) {
                deleteCategory(Contract.PATH_T3DATAS, category, target);
            }
            return insertData(target);
        }
        return false;
    }

    private boolean isDeleteData(String category, String target) {
        String timestamp = null;
        Uri uri = Contract.T3dataEntry.CONTENT_URI;
        String selection = Contract.T3dataEntry.COLUMN_NAME_SUBREDDIT + " =?";
        String[] selectionArgs = {category};

        Cursor cursor = null;
        boolean isDeleted = false;
        try {

            cursor = mContext.getContentResolver().query(uri, null, selection, selectionArgs, null);

            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                timestamp = cursor.getString(cursor.getColumnIndex(Contract.T3dataEntry.COLUMN_NAME_TIME_LAST_MODIFIED));
            }

            if (!TextUtils.isEmpty(timestamp)) {
                int timeUpdateDatabase = Preference.getGeneralSettingsSyncFrequency(mContext);
                isDeleted = getSecondsTimeStamp(timestamp) > timeUpdateDatabase;
            }

        } catch (Exception e) {

            Timber.d("DATABASE isDeleteT3 %s", e.getMessage());

        } finally {
            if ((cursor != null) && (!cursor.isClosed())) {
                cursor.close();
            }
        }

        return isDeleted;
    }

    private void deleteCategory(String contractPath, String category, String target) {
        String where;
        Uri uri;
        String[] selectionArgs = {category};

        switch (contractPath) {

            case Contract.PATH_T3DATAS:
                uri = Contract.T3dataEntry.CONTENT_URI;
                where = Contract.T3dataEntry.COLUMN_NAME_SUBREDDIT + " =?";
                break;

            case Contract.PATH_PREFSUBREDDIT:
                uri = Contract.PrefSubRedditEntry.CONTENT_URI;
                where = Contract.PrefSubRedditEntry.COLUMN_NAME_NAME + " =?";
                break;

            default:
                Timber.d("DeleteCategory: contract path not present");
                uri = null;
                where = null;
        }

        try {
            if (uri != null) {
                mContext.getContentResolver().delete(uri, where, selectionArgs);
            }
        } catch (IllegalStateException e) {
            Timber.e("delete category error %s", e.getCause());

        }

    }

    public void clearData() {
        try {
            mContext.getContentResolver().delete(Contract.T3dataEntry.CONTENT_URI, null, null);
            mContext.getContentResolver().delete(Contract.PrefSubRedditEntry.CONTENT_URI, null, null);

        } catch (IllegalStateException e) {
            Timber.e("clear data error %s", e.getCause());

        }

    }


}
