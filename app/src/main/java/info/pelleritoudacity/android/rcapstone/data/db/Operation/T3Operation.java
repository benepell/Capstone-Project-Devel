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
import info.pelleritoudacity.android.rcapstone.data.model.reddit.T3;
import info.pelleritoudacity.android.rcapstone.data.model.reddit.T3Data;
import info.pelleritoudacity.android.rcapstone.data.model.reddit.T3Listing;
import info.pelleritoudacity.android.rcapstone.data.model.reddit.Variants;
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
        T3Listing dataModel = mModelT3.getData();
        ContentValues dataCV = new ContentValues();
        dataCV.put(Contract.DataEntry.COLUMN_NAME_AFTER, dataModel.getAfter());
        dataCV.put(Contract.DataEntry.COLUMN_NAME_DIST, dataModel.getDist());
        dataCV.put(Contract.DataEntry.COLUMN_NAME_MODHASH, dataModel.getModhash());


        if (dataModel.getBefore() == null) {
            dataCV.putNull(Contract.DataEntry.COLUMN_NAME_BEFORE);
        } else {
            dataCV.put(Contract.DataEntry.COLUMN_NAME_BEFORE, (byte) dataModel.getBefore());
        }

        int childrenId;

        int t3Size = mModelT3.getData().getChildren().size();

        ContentValues[] arrT3CV = new ContentValues[t3Size];

        DataUtils dataUtils = new DataUtils(mContext);

        ImageContentOperation imageContentOperation = new ImageContentOperation(mContext);

        VideoContentOperation videoContentOperation = new VideoContentOperation(mContext);

        for (int i = 0; i < t3Size; i++) {

            childrenId = i + 1;

            T3Data t3Model = mModelT3.getData().getChildren().get(i).getData();

            dataCV.put(Contract.DataEntry.COLUMN_NAME_CHILDRENS, childrenId);

            arrT3CV[i] = new ContentValues();

            if (t3Model != null) {


                arrT3CV[i].put(Contract.T3dataEntry.COLUMN_NAME_IS_CROSSPOSTABLE,
                        Utility.boolToInt(t3Model.getIsCrosspostable()));

                arrT3CV[i].put(Contract.T3dataEntry.COLUMN_NAME_SUBREDDIT_ID,
                        t3Model.getSubredditId());

                dataUtils.putNullCV(arrT3CV[i], Contract.T3dataEntry.COLUMN_NAME_APPROVED_AT_UTC,
                        t3Model.getApprovedAtUtc());

                arrT3CV[i].put(Contract.T3dataEntry.COLUMN_NAME_WLS,
                        t3Model.getWls());

                dataUtils.putNullCV(arrT3CV[i], Contract.T3dataEntry.COLUMN_NAME_MOD_REASON_BY,
                        t3Model.getModReasonBy());

                dataUtils.putNullCV(arrT3CV[i], Contract.T3dataEntry.COLUMN_NAME_BANNED_BY,
                        t3Model.getBannedBy());

                dataUtils.putNullCV(arrT3CV[i], Contract.T3dataEntry.COLUMN_NAME_NUM_REPORTS,
                        t3Model.getNumReports());

                dataUtils.putNullCV(arrT3CV[i], Contract.T3dataEntry.COLUMN_NAME_REMOVAL_REASON,
                        t3Model.getRemovalReason());

                dataUtils.putNullCV(arrT3CV[i], Contract.T3dataEntry.COLUMN_NAME_THUMBNAIL_WIDTH,
                        t3Model.getThumbnailWidth());

                dataUtils.putNullCV(arrT3CV[i], Contract.T3dataEntry.COLUMN_NAME_SUBREDDIT,
                        t3Model.getSubreddit());

                dataUtils.putNullCV(arrT3CV[i], Contract.T3dataEntry.COLUMN_NAME_SELFTEXT_HTML,
                        t3Model.getSelftextHtml());

                dataUtils.putNullCV(arrT3CV[i], Contract.T3dataEntry.COLUMN_NAME_SELFTEXT,
                        t3Model.getSelftext());

                dataUtils.putNullCV(arrT3CV[i], Contract.T3dataEntry.COLUMN_NAME_LIKES,
                        t3Model.getLikes());

                dataUtils.putNullCV(arrT3CV[i], Contract.T3dataEntry.COLUMN_NAME_SUGGESTED_SORT,
                        t3Model.getSuggestedSort());

                arrT3CV[i].put(Contract.T3dataEntry.COLUMN_NAME_IS_REDDIT_MEDIA_DOMAIN,
                        Utility.boolToInt(t3Model.getIsRedditMediaDomain()));

                arrT3CV[i].put(Contract.T3dataEntry.COLUMN_NAME_SAVED,
                        Utility.boolToInt(t3Model.getSaved()));

                arrT3CV[i].put(Contract.T3dataEntry.COLUMN_NAME_CHILDREN_ID,
                        childrenId);

                arrT3CV[i].put(Contract.T3dataEntry.COLUMN_NAME_ID,
                        t3Model.getId());

                dataUtils.putNullCV(arrT3CV[i], Contract.T3dataEntry.COLUMN_NAME_BANNED_AT_UTC,
                        t3Model.getBannedAtUtc());

                dataUtils.putNullCV(arrT3CV[i], Contract.T3dataEntry.COLUMN_NAME_MOD_REASON_TITLE,
                        t3Model.getModReasonTitle());

                dataUtils.putNullCV(arrT3CV[i], Contract.T3dataEntry.COLUMN_NAME_VIEW_COUNT,
                        t3Model.getViewCount());

                arrT3CV[i].put(Contract.T3dataEntry.COLUMN_NAME_ARCHIVED,
                        Utility.boolToInt(t3Model.getArchived()));

                arrT3CV[i].put(Contract.T3dataEntry.COLUMN_NAME_CLICKED,
                        Utility.boolToInt(t3Model.getClicked()));


                arrT3CV[i].put(Contract.T3dataEntry.COLUMN_NAME_NO_FOLLOW,
                        Utility.boolToInt(t3Model.getNoFollow()));

                dataUtils.putNullCV(arrT3CV[i], Contract.T3dataEntry.COLUMN_NAME_AUTHOR,
                        t3Model.getAuthor());

                arrT3CV[i].put(Contract.T3dataEntry.COLUMN_NAME_NUM_CROSSPOSTS,
                        t3Model.getNumCrossposts());

                dataUtils.putNullCV(arrT3CV[i], Contract.T3dataEntry.COLUMN_NAME_LINK_FLAIR_TEXT,
                        t3Model.getLinkFlairText());

                arrT3CV[i].put(Contract.T3dataEntry.COLUMN_NAME_CAN_MOD_POST,
                        Utility.boolToInt(t3Model.getCanModPost()));

                arrT3CV[i].put(Contract.T3dataEntry.COLUMN_NAME_SEND_REPLIES,
                        Utility.boolToInt(t3Model.getSendReplies()));

                arrT3CV[i].put(Contract.T3dataEntry.COLUMN_NAME_PINNED,
                        Utility.boolToInt(t3Model.getPinned()));

                arrT3CV[i].put(Contract.T3dataEntry.COLUMN_NAME_SCORE,
                        t3Model.getScore());

                dataUtils.putNullCV(arrT3CV[i], Contract.T3dataEntry.COLUMN_NAME_APPROVED_BY,
                        t3Model.getApprovedBy());

                arrT3CV[i].put(Contract.T3dataEntry.COLUMN_NAME_OVER_18,
                        t3Model.getOver18());

                dataUtils.putNullCV(arrT3CV[i], Contract.T3dataEntry.COLUMN_NAME_REPORT_REASONS,
                        t3Model.getReportReasons());

                dataUtils.putNullCV(arrT3CV[i], Contract.T3dataEntry.COLUMN_NAME_DOMAIN,
                        t3Model.getDomain());

                arrT3CV[i].put(Contract.T3dataEntry.COLUMN_NAME_HIDDEN,
                        Utility.boolToInt(t3Model.getHidden()));

                arrT3CV[i].put(Contract.T3dataEntry.COLUMN_NAME_PWLS,
                        t3Model.getPwls());

                dataUtils.putNullCV(arrT3CV[i], Contract.T3dataEntry.COLUMN_NAME_THUMBNAIL,
                        t3Model.getThumbnail());

                arrT3CV[i].put(Contract.T3dataEntry.COLUMN_NAME_EDITED,
                        Utility.toInt(t3Model.getEdited()));

                dataUtils.putNullCV(arrT3CV[i], Contract.T3dataEntry.COLUMN_NAME_LINK_FLAIR_CSS_CLASS,
                        t3Model.getLinkFlairCssClass());

                dataUtils.putNullCV(arrT3CV[i], Contract.T3dataEntry.COLUMN_NAME_AUTHOR_FLAIR_CSS_CLASS,
                        t3Model.getAuthorFlairCssClass());

                arrT3CV[i].put(Contract.T3dataEntry.COLUMN_NAME_CONTEST_MODE,
                        Utility.boolToInt(t3Model.getContestMode()));

                arrT3CV[i].put(Contract.T3dataEntry.COLUMN_NAME_GILDED,
                        t3Model.getGilded());

                arrT3CV[i].put(Contract.T3dataEntry.COLUMN_NAME_LOCKED,
                        Utility.boolToInt(t3Model.getLocked()));

                arrT3CV[i].put(Contract.T3dataEntry.COLUMN_NAME_DOWNS,
                        t3Model.getDowns());

                arrT3CV[i].put(Contract.T3dataEntry.COLUMN_NAME_STICKIED,
                        Utility.boolToInt(t3Model.getStickied()));

                arrT3CV[i].put(Contract.T3dataEntry.COLUMN_NAME_VISITED,
                        Utility.boolToInt(t3Model.getVisited()));

                arrT3CV[i].put(Contract.T3dataEntry.COLUMN_NAME_CAN_GILD,
                        Utility.boolToInt(t3Model.getCanGild()));

                dataUtils.putNullCV(arrT3CV[i], Contract.T3dataEntry.COLUMN_NAME_THUMBNAIL_HEIGHT,
                        t3Model.getThumbnailHeight());

                dataUtils.putNullCV(arrT3CV[i], Contract.T3dataEntry.COLUMN_NAME_NAME,
                        t3Model.getName());

                arrT3CV[i].put(Contract.T3dataEntry.COLUMN_NAME_SPOILER,
                        Utility.boolToInt(t3Model.getSpoiler()));

                dataUtils.putNullCV(arrT3CV[i], Contract.T3dataEntry.COLUMN_NAME_PERMALINK,
                        t3Model.getPermalink());

                dataUtils.putNullCV(arrT3CV[i], Contract.T3dataEntry.COLUMN_NAME_SUBREDDIT_TYPE,
                        t3Model.getSubredditType());

                dataUtils.putNullCV(arrT3CV[i], Contract.T3dataEntry.COLUMN_NAME_PARENT_WHITELIST_STATUS,
                        t3Model.getParentWhitelistStatus());

                arrT3CV[i].put(Contract.T3dataEntry.COLUMN_NAME_HIDE_SCORE,
                        Utility.boolToInt(t3Model.getHideScore()));

                arrT3CV[i].put(Contract.T3dataEntry.COLUMN_NAME_URL,
                        t3Model.getUrl());

                arrT3CV[i].put(Contract.T3dataEntry.COLUMN_NAME_CREATED,
                        t3Model.getCreated());

                dataUtils.putNullCV(arrT3CV[i], Contract.T3dataEntry.COLUMN_NAME_AUTHOR_FLAIR_TEXT,
                        t3Model.getAuthorFlairText());

                dataUtils.putNullCV(arrT3CV[i], Contract.T3dataEntry.COLUMN_NAME_QUARANTINE,
                        Utility.boolToInt(t3Model.getQuarantine()));

                dataUtils.putNullCV(arrT3CV[i], Contract.T3dataEntry.COLUMN_NAME_TITLE,
                        t3Model.getTitle());

                arrT3CV[i].put(Contract.T3dataEntry.COLUMN_NAME_CREATED_UTC,
                        t3Model.getCreatedUtc());

                dataUtils.putNullCV(arrT3CV[i], Contract.T3dataEntry.COLUMN_NAME_SUBREDDIT_NAME_PREFIXE,
                        t3Model.getSubredditNamePrefixed());

                dataUtils.putNullCV(arrT3CV[i], Contract.T3dataEntry.COLUMN_NAME_UPS,
                        t3Model.getUps());

                arrT3CV[i].put(Contract.T3dataEntry.COLUMN_NAME_NUM_COMMENTS,
                        t3Model.getNumComments());
                // todo change media
            /*    dataUtils.putNullCV(arrT3CV[i], Contract.T3dataEntry.COLUMN_NAME_MEDIA,
                        t3Model.getMedia());
*/

                arrT3CV[i].put(Contract.T3dataEntry.COLUMN_NAME_IS_SELF,
                        Utility.boolToInt(t3Model.getIsSelf()));

                dataUtils.putNullCV(arrT3CV[i], Contract.T3dataEntry.COLUMN_NAME_WHITELIST_STATUS,
                        t3Model.getWhitelistStatus());

                dataUtils.putNullCV(arrT3CV[i], Contract.T3dataEntry.COLUMN_NAME_MOD_NOTE,
                        t3Model.getModNote());


                arrT3CV[i].put(Contract.T3dataEntry.COLUMN_NAME_IS_VIDEO,
                        Utility.boolToInt(t3Model.getIsVideo()));

                dataUtils.putNullCV(arrT3CV[i], Contract.T3dataEntry.COLUMN_NAME_DISTINGUISHED,
                        t3Model.getDistinguished());

                String stringCategoryNormalizeSub = TextUtil.normalizeSubRedditLink(t3Model.getSubreddit());
                dataUtils.putNullCV(arrT3CV[i], Contract.T3dataEntry.COLUMN_NAME_SUBREDDIT,
                        stringCategoryNormalizeSub);

                dataUtils.putNullCV(arrT3CV[i], Contract.T3dataEntry.COLUMN_NAME_TARGET,
                        target);

                dataUtils.putNullCV(arrT3CV[i], Contract.T3dataEntry.COLUMN_NAME_SORT_BY,
                        Preference.getSubredditSort(mContext));

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

                    String videoHlsUrl;
                    String videoDashUrl;
                    String videoScrubberMediaUrl;
                    String videoFallbackUrl;
                    String videoTranscodingStatus;
                    int videoDuration;
                    int videoWidth;
                    int videoHeight;
                    boolean isVideoGif;
                    if (redditVideoPreview != null) {
                        videoHlsUrl = redditVideoPreview.getHlsUrl();
                        videoDashUrl = redditVideoPreview.getDashUrl();
                        videoScrubberMediaUrl = redditVideoPreview.getScrubberMediaUrl();
                        videoFallbackUrl = redditVideoPreview.getFallbackUrl();
                        videoTranscodingStatus = redditVideoPreview.getTranscodingStatus();
                        videoDuration = redditVideoPreview.getDuration();
                        videoWidth = redditVideoPreview.getHeight();
                        videoHeight = redditVideoPreview.getWidth();
                        isVideoGif = redditVideoPreview.getIsGif();

                        dataUtils.putNullCV(arrT3CV[i], Contract.T3dataEntry.COLUMN_NAME_PREVIEW_VIDEO_HLS_URL,
                                videoHlsUrl);

                        dataUtils.putNullCV(arrT3CV[i], Contract.T3dataEntry.COLUMN_NAME_PREVIEW_VIDEO_DASH_URL,
                                videoDashUrl);

                        dataUtils.putNullCV(arrT3CV[i], Contract.T3dataEntry.COLUMN_NAME_PREVIEW_VIDEO_SCRUBBER_MEDIA_URL,
                                videoScrubberMediaUrl);

                        dataUtils.putNullCV(arrT3CV[i], Contract.T3dataEntry.COLUMN_NAME_PREVIEW_VIDEO_FALLBACK_URL,
                                videoFallbackUrl);

                        dataUtils.putNullCV(arrT3CV[i], Contract.T3dataEntry.COLUMN_NAME_PREVIEW_VIDEO_TRANSCODING_STATUS,
                                videoTranscodingStatus);

                        dataUtils.putNullCV(arrT3CV[i], Contract.T3dataEntry.COLUMN_NAME_PREVIEW_VIDEO_DURATION,
                                videoDuration);

                        dataUtils.putNullCV(arrT3CV[i], Contract.T3dataEntry.COLUMN_NAME_PREVIEW_VIDEO_WIDTH,
                                videoWidth);

                        dataUtils.putNullCV(arrT3CV[i], Contract.T3dataEntry.COLUMN_NAME_PREVIEW_VIDEO_HEIGHT,
                                videoHeight);

                        dataUtils.putNullCV(arrT3CV[i], Contract.T3dataEntry.COLUMN_NAME_PREVIEW_IS_VIDEO_GIF,
                                isVideoGif);


                    }

                    Variants variants = t3Model.getPreview().getImages().get(0).getVariants();

                    if (variants != null) {

                        ArrayList<ModelContent> modelContentArrayList = new ArrayList<>();
                        //noinspection CollectionAddAllCanBeReplacedWithConstructor
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

                        OembedMedia oembedMediaModel = mediaModel.getOembed();

                        if (oembedMediaModel != null) {

                            dataUtils.putNullCV(arrT3CV[i], Contract.T3dataEntry.COLUMN_NAME_MEDIA_OEMBED_PROVIDER_URL,
                                    oembedMediaModel.getProviderUrl());

                            dataUtils.putNullCV(arrT3CV[i], Contract.T3dataEntry.COLUMN_NAME_MEDIA_OEMBED_TITLE,
                                    oembedMediaModel.getTitle());

                            dataUtils.putNullCV(arrT3CV[i], Contract.T3dataEntry.COLUMN_NAME_MEDIA_OEMBED_TYPE,
                                    oembedMediaModel.getType());

                            dataUtils.putNullCV(arrT3CV[i], Contract.T3dataEntry.COLUMN_NAME_MEDIA_OEMBED_HTML,
                                    oembedMediaModel.getHtml());

                            dataUtils.putNullCV(arrT3CV[i], Contract.T3dataEntry.COLUMN_NAME_MEDIA_OEMBED_AUTHOR_NAME,
                                    oembedMediaModel.getAuthorName());

                            dataUtils.putNullCV(arrT3CV[i], Contract.T3dataEntry.COLUMN_NAME_MEDIA_OEMBED_AUTHOR_URL,
                                    oembedMediaModel.getAuthorUrl());

                            dataUtils.putNullCV(arrT3CV[i], Contract.T3dataEntry.COLUMN_NAME_MEDIA_OEMBED_WIDTH,
                                    oembedMediaModel.getWidth());

                            dataUtils.putNullCV(arrT3CV[i], Contract.T3dataEntry.COLUMN_NAME_MEDIA_OEMBED_HEIGHT,
                                    oembedMediaModel.getHeight());

                            dataUtils.putNullCV(arrT3CV[i], Contract.T3dataEntry.COLUMN_NAME_MEDIA_OEMBED_THUMBNAIL_WIDTH,
                                    oembedMediaModel.getThumbnailWidth());

                            dataUtils.putNullCV(arrT3CV[i], Contract.T3dataEntry.COLUMN_NAME_MEDIA_OEMBED_THUMBNAIL_HEIGHT,
                                    oembedMediaModel.getThumbnailHeight());

                            dataUtils.putNullCV(arrT3CV[i], Contract.T3dataEntry.COLUMN_NAME_MEDIA_OEMBED_THUMBNAIL_URL,
                                    oembedMediaModel.getThumbnailUrl());

                            dataUtils.putNullCV(arrT3CV[i], Contract.T3dataEntry.COLUMN_NAME_MEDIA_OEMBED_VERSION,
                                    oembedMediaModel.getVersion());

                        }

                    }

                }
            }

        }

        final Uri uriReddit = mContext.getContentResolver().insert(Contract.RedditEntry.CONTENT_URI, subRedditCVT3);
        final Uri uriData = mContext.getContentResolver().insert(Contract.DataEntry.CONTENT_URI, dataCV);
        int countT3Data = mContext.getContentResolver().bulkInsert(Contract.T3dataEntry.CONTENT_URI, arrT3CV);

        return uriReddit != null && uriData != null && countT3Data != 0;
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

    public boolean isDeleteData(String category, String target) {
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

    public void deleteCategory(String contractPath, String category, String target) {
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

        if (uri != null) {
            mContext.getContentResolver().delete(uri, where, selectionArgs);
        }

    }

    public void clearData() {
        mContext.getContentResolver().delete(Contract.T3dataEntry.CONTENT_URI, null, null);
        mContext.getContentResolver().delete(Contract.PrefSubRedditEntry.CONTENT_URI, null, null);
    }


}
