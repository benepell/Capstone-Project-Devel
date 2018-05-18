
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

package info.pelleritoudacity.android.rcapstone.data;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.text.TextUtils;
import android.util.DisplayMetrics;

import java.util.ArrayList;
import java.util.List;

import info.pelleritoudacity.android.rcapstone.R;
import info.pelleritoudacity.android.rcapstone.model.Mp4;
import info.pelleritoudacity.android.rcapstone.model.RedditVideoPreview;
import info.pelleritoudacity.android.rcapstone.model.Resolution;
import info.pelleritoudacity.android.rcapstone.model.ResolutionMp4;
import info.pelleritoudacity.android.rcapstone.model.Source;
import info.pelleritoudacity.android.rcapstone.model.SourceMp4;
import info.pelleritoudacity.android.rcapstone.model.T3;
import info.pelleritoudacity.android.rcapstone.model.T3Data;
import info.pelleritoudacity.android.rcapstone.model.T3Listing;
import info.pelleritoudacity.android.rcapstone.model.T5;
import info.pelleritoudacity.android.rcapstone.model.T5Data;
import info.pelleritoudacity.android.rcapstone.model.T5Listing;
import info.pelleritoudacity.android.rcapstone.model.Variants;
import info.pelleritoudacity.android.rcapstone.utility.Costants;
import info.pelleritoudacity.android.rcapstone.utility.PrefManager;
import info.pelleritoudacity.android.rcapstone.utility.Utility;
import timber.log.Timber;

public class DataUtils {

    private final Context mContext;

    public DataUtils(Context context) {
        mContext = context;
    }

    private boolean isRecordData() {

        Cursor cursor = null;
        boolean isRecord = false;

        try {
            cursor = mContext.getContentResolver().query(Contract.RedditEntry.CONTENT_URI, null, null, null, null);
            isRecord = cursor != null && cursor.getCount() > 0;

        } catch (Exception e) {
            Timber.d("DATABASE isRecordData %s", e.getMessage());

        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return isRecord;
    }

    private void clearDataT5() {

        mContext.getContentResolver().delete(Contract.RedditEntry.CONTENT_URI, null, null);
        mContext.getContentResolver().delete(Contract.DataEntry.CONTENT_URI, null, null);
        mContext.getContentResolver().delete(Contract.T5dataEntry.CONTENT_URI, null, null);
    }

    private void clearDataAll() {
        clearDataT5();
        mContext.getContentResolver().delete(Contract.T3dataEntry.CONTENT_URI, null, null);
        mContext.getContentResolver().delete(Contract.PrefSubRedditEntry.CONTENT_URI, null, null);
    }


    private boolean insertDataT5(T5 reddits) {

        if (reddits == null) return false;

        // reddit
        ContentValues redditCV = new ContentValues();
        redditCV.put(Contract.RedditEntry.COLUMN_NAME_KIND, reddits.getKind());
        redditCV.put(Contract.RedditEntry.COLUMN_NAME_DATA, 1);

        // data
        T5Listing dataModel = reddits.getData();
        ContentValues dataCV = new ContentValues();
        dataCV.put(Contract.DataEntry.COLUMN_NAME_AFTER, dataModel.getAfter());
        dataCV.put(Contract.DataEntry.COLUMN_NAME_DIST, dataModel.getDist());
        dataCV.put(Contract.DataEntry.COLUMN_NAME_MODHASH, dataModel.getModhash());

        if (dataModel.getWhitelistStatus() == null) {
            dataCV.putNull(Contract.DataEntry.COLUMN_NAME_WHITELIST_STATUS);
        } else {
            dataCV.put(Contract.DataEntry.COLUMN_NAME_WHITELIST_STATUS, dataModel.getWhitelistStatus());
        }


        if (dataModel.getBefore() == null) {
            dataCV.putNull(Contract.DataEntry.COLUMN_NAME_BEFORE);
        } else {
            dataCV.put(Contract.DataEntry.COLUMN_NAME_BEFORE, (byte) dataModel.getBefore());
        }

        int childrenId;

        int t5Size = reddits.getData().getChildren().size();

        ContentValues[] arrCV = new ContentValues[t5Size];

        for (int i = 0; i < t5Size; i++) {

            childrenId = i + 1;

            dataCV.put(Contract.DataEntry.COLUMN_NAME_CHILDRENS, childrenId);

            T5Data t5Model = reddits.getData().getChildren().get(i).getData();

            arrCV[i] = new ContentValues();

            putNullCV(arrCV[i], Contract.T5dataEntry.COLUMN_NAME_SUGGESTED_COMMENT_SORT,
                    t5Model.getSuggestedCommentSort());

            arrCV[i].put(Contract.T5dataEntry.COLUMN_NAME_HIDE_ADS,
                    t5Model.getHideAds());

            putNullCV(arrCV[i], Contract.T5dataEntry.COLUMN_NAME_BANNER_IMG,
                    t5Model.getBannerImg());

            arrCV[i].put(Contract.T5dataEntry.COLUMN_NAME_USER_SR_THEME_ENABLED,
                    t5Model.getUserSrThemeEnabled());

            putNullCV(arrCV[i], Contract.T5dataEntry.COLUMN_NAME_USER_FLAIR_TEXT,
                    t5Model.getUserFlairText());

            putNullCV(arrCV[i], Contract.T5dataEntry.COLUMN_NAME_SUBMIT_TEXT_HTML,
                    t5Model.getSubmitTextHtml());

            putNullCV(arrCV[i], Contract.T5dataEntry.COLUMN_NAME_USER_IS_BANNED,
                    t5Model.getUserIsBanned());

            arrCV[i].put(Contract.T5dataEntry.COLUMN_NAME_WIKI_ENABLED,
                    t5Model.getWikiEnabled());

            arrCV[i].put(Contract.T5dataEntry.COLUMN_NAME_SHOW_MEDIA,
                    t5Model.getShowMedia());

            putNullCV(arrCV[i], Contract.T5dataEntry.COLUMN_NAME_ID,
                    t5Model.getId());

            arrCV[i].put(Contract.T5dataEntry.COLUMN_NAME_CHILDREN_ID,
                    childrenId);

            putNullCV(arrCV[i], Contract.T5dataEntry.COLUMN_NAME_DISPLAY_NAME_PREFIXED,
                    t5Model.getDisplayNamePrefixed());

            putNullCV(arrCV[i], Contract.T5dataEntry.COLUMN_NAME_HEADER_IMG,
                    t5Model.getHeaderImg());


            putNullCV(arrCV[i], Contract.T5dataEntry.COLUMN_NAME_DESCRIPTION_HTML,
                    t5Model.getDescriptionHtml());

            putNullCV(arrCV[i], Contract.T5dataEntry.COLUMN_NAME_TITLE,
                    t5Model.getTitle());

            arrCV[i].put(Contract.T5dataEntry.COLUMN_NAME_COLLAPSE_DELETED_COMMENTS,
                    t5Model.getCollapseDeletedComments());

            putNullCV(arrCV[i], Contract.T5dataEntry.COLUMN_NAME_USER_HAS_FAVORITED,
                    t5Model.getUserHasFavorited());

            arrCV[i].put(Contract.T5dataEntry.COLUMN_NAME_OVER18,
                    t5Model.getOver18());

            putNullCV(arrCV[i], Contract.T5dataEntry.COLUMN_NAME_PUBLIC_DESCRIPTION_HTML,
                    t5Model.getPublicDescriptionHtml());

            arrCV[i].put(Contract.T5dataEntry.COLUMN_NAME_ALLOW_VIDEOS,
                    t5Model.getAllowVideos());

            arrCV[i].put(Contract.T5dataEntry.COLUMN_NAME_SPOILERS_ENABLED,
                    t5Model.getSpoilersEnabled());

            putNullCV(arrCV[i], Contract.T5dataEntry.COLUMN_NAME_AUDIENCE_TARGET,
                    t5Model.getAudienceTarget());

            putNullCV(arrCV[i], Contract.T5dataEntry.COLUMN_NAME_NOTIFICATION_LEVEL,
                    t5Model.getNotificationLevel());

            putNullCV(arrCV[i], Contract.T5dataEntry.COLUMN_NAME_ACTIVE_USER_COUNT,
                    t5Model.getActiveUserCount());

            putNullCV(arrCV[i], Contract.T5dataEntry.COLUMN_NAME_ICON_IMG,
                    t5Model.getIconImg());

            putNullCV(arrCV[i], Contract.T5dataEntry.COLUMN_NAME_HEADER_TITLE,
                    t5Model.getHeaderTitle());

            putNullCV(arrCV[i], Contract.T5dataEntry.COLUMN_NAME_DESCRIPTION,
                    t5Model.getDescription());

            putNullCV(arrCV[i], Contract.T5dataEntry.COLUMN_NAME_USER_IS_MUTED,
                    t5Model.getUserIsMuted());

            putNullCV(arrCV[i], Contract.T5dataEntry.COLUMN_NAME_SUBMIT_LINK_LABEL,
                    t5Model.getSubmitLinkLabel());

            putNullCV(arrCV[i], Contract.T5dataEntry.COLUMN_NAME_ACCOUNTS_ACTIVE,
                    t5Model.getAccountsActive());

            arrCV[i].put(Contract.T5dataEntry.COLUMN_NAME_PUBLIC_TRAFFIC,
                    t5Model.getPublicTraffic());

            arrCV[i].put(Contract.T5dataEntry.COLUMN_NAME_SUBSCRIBERS,
                    t5Model.getSubscribers());

            putNullCV(arrCV[i], Contract.T5dataEntry.COLUMN_NAME_USER_FLAIR_CSS_CLASS,
                    t5Model.getUserFlairCssClass());

            putNullCV(arrCV[i], Contract.T5dataEntry.COLUMN_NAME_SUBMIT_TEXT_LABEL,
                    t5Model.getSubmitTextLabel());

            putNullCV(arrCV[i], Contract.T5dataEntry.COLUMN_NAME_WHITELIST_STATUS,
                    t5Model.getWhitelistStatus());

            putNullCV(arrCV[i], Contract.T5dataEntry.COLUMN_NAME_USER_SR_FLAIR_ENABLED,
                    t5Model.getUserSrFlairEnabled());

            putNullCV(arrCV[i], Contract.T5dataEntry.COLUMN_NAME_LANG,
                    t5Model.getLang());

            putNullCV(arrCV[i], Contract.T5dataEntry.COLUMN_NAME_USER_IS_MODERATOR,
                    t5Model.getUserIsModerator());

            putNullCV(arrCV[i], Contract.T5dataEntry.COLUMN_NAME_IS_ENROLLED_IN_NEW_MODMAIL,
                    t5Model.getIsEnrolledInNewModmail());

            putNullCV(arrCV[i], Contract.T5dataEntry.COLUMN_NAME_KEY_COLOR,
                    t5Model.getKeyColor());

            putNullCV(arrCV[i], Contract.T5dataEntry.COLUMN_NAME_NAME,
                    t5Model.getName());

            arrCV[i].put(Contract.T5dataEntry.COLUMN_NAME_USER_FLAIR_ENABLED_IN_SR,
                    t5Model.getUserFlairEnabledInSr());

            arrCV[i].put(Contract.T5dataEntry.COLUMN_NAME_ALLOW_VIDEOGIFS,
                    t5Model.getAllowVideogifs());

            putNullCV(arrCV[i], Contract.T5dataEntry.COLUMN_NAME_URL,
                    t5Model.getUrl());

            arrCV[i].put(Contract.T5dataEntry.COLUMN_NAME_QUARANTINE,
                    t5Model.getQuarantine());

            arrCV[i].put(Contract.T5dataEntry.COLUMN_NAME_CREATED,
                    t5Model.getCreated());

            arrCV[i].put(Contract.T5dataEntry.COLUMN_NAME_CREATED_UTC,
                    t5Model.getCreatedUtc());

            putNullCV(arrCV[i], Contract.T5dataEntry.COLUMN_NAME_USER_IS_CONTRIBUTOR,
                    t5Model.getUserIsContributor());

            arrCV[i].put(Contract.T5dataEntry.COLUMN_NAME_ALLOW_DISCOVERY,
                    t5Model.getAllowDiscovery());

            arrCV[i].put(Contract.T5dataEntry.COLUMN_NAME_ACCOUNTS_ACTIVE_IS_FUZZED,
                    t5Model.getAccountsActiveIsFuzzed());

            putNullCV(arrCV[i], Contract.T5dataEntry.COLUMN_NAME_ADVERTISER_CATEGORY,
                    t5Model.getAdvertiserCategory());

            putNullCV(arrCV[i], Contract.T5dataEntry.COLUMN_NAME_PUBLIC_DESCRIPTION,
                    t5Model.getPublicDescription());

            arrCV[i].put(Contract.T5dataEntry.COLUMN_NAME_LINK_FLAIR_ENABLED,
                    t5Model.getLinkFlairEnabled());

            arrCV[i].put(Contract.T5dataEntry.COLUMN_NAME_ALLOW_IMAGES,
                    t5Model.getAllowImages());

            arrCV[i].put(Contract.T5dataEntry.COLUMN_NAME_SHOW_MEDIA_PREVIEW,
                    t5Model.getShowMediaPreview());

            arrCV[i].put(Contract.T5dataEntry.COLUMN_NAME_COMMENT_SCORE_HIDE_MINS,
                    t5Model.getCommentScoreHideMins());

            putNullCV(arrCV[i], Contract.T5dataEntry.COLUMN_NAME_SUBREDDIT_TYPE,
                    t5Model.getSubredditType());

            putNullCV(arrCV[i], Contract.T5dataEntry.COLUMN_NAME_SUBMISSION_TYPE,
                    t5Model.getSubmissionType());

            putNullCV(arrCV[i], Contract.T5dataEntry.COLUMN_NAME_USER_IS_SUBSCRIBER,
                    t5Model.getUserIsSubscriber());

        }

        final Uri uriReddit = mContext.getContentResolver().insert(Contract.RedditEntry.CONTENT_URI, redditCV);
        final Uri uriData = mContext.getContentResolver().insert(Contract.DataEntry.CONTENT_URI, dataCV);
        int countT5Data = mContext.getContentResolver().bulkInsert(Contract.T5dataEntry.CONTENT_URI, arrCV);

        return uriReddit != null && uriData != null && countT5Data != 0;
    }

    private boolean insertDataPrefSubReddit(T5 reddits) {

        if (reddits == null) return false;

        int t5Size = reddits.getData().getChildren().size();
        int position;
        ContentValues[] arrCV = new ContentValues[t5Size];
        for (int i = 0; i < t5Size; i++) {

            position = i + 1;

            T5Data t5Model = reddits.getData().getChildren().get(i).getData();

            arrCV[i] = new ContentValues();

            putNullCV(arrCV[i], Contract.PrefSubRedditEntry.COLUMN_NAME_NAME,
                    Utility.normalizeSubRedditLink(t5Model.getUrl()));

            putNullCV(arrCV[i], Contract.PrefSubRedditEntry.COLUMN_NAME_IMAGE,
                    t5Model.getIconImg());

            arrCV[i].put(Contract.PrefSubRedditEntry.COLUMN_NAME_VISIBLE,
                    Costants.DEFAULT_SUBREDDIT_VISIBLE);

            arrCV[i].put(Contract.PrefSubRedditEntry.COLUMN_NAME_POSITION,
                    position);
        }

        int countPrefSubData = mContext.getContentResolver().bulkInsert(Contract.PrefSubRedditEntry.CONTENT_URI, arrCV);

        return countPrefSubData != 0;
    }

    private boolean insertDataT3(T3 subReddits) {

        if (subReddits == null) return false;

        // subReddit
        ContentValues subRedditCV = new ContentValues();
        subRedditCV.put(Contract.RedditEntry.COLUMN_NAME_KIND, subReddits.getKind());
        subRedditCV.put(Contract.RedditEntry.COLUMN_NAME_DATA, 1);

        // data
        T3Listing dataModel = subReddits.getData();
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

        int t3Size = subReddits.getData().getChildren().size();

        ContentValues[] arrT3CV = new ContentValues[t3Size];

        for (int i = 0; i < t3Size; i++) {

            childrenId = i + 1;

            T3Data t3Model = subReddits.getData().getChildren().get(i).getData();

            dataCV.put(Contract.DataEntry.COLUMN_NAME_CHILDRENS, childrenId);

            arrT3CV[i] = new ContentValues();

            if (t3Model != null) {


                arrT3CV[i].put(Contract.T3dataEntry.COLUMN_NAME_IS_CROSSPOSTABLE,
                        Utility.boolToInt(t3Model.getIsCrosspostable()));

                arrT3CV[i].put(Contract.T3dataEntry.COLUMN_NAME_SUBREDDIT_ID,
                        t3Model.getSubredditId());

                putNullCV(arrT3CV[i], Contract.T3dataEntry.COLUMN_NAME_APPROVED_AT_UTC,
                        t3Model.getApprovedAtUtc());

                arrT3CV[i].put(Contract.T3dataEntry.COLUMN_NAME_WLS,
                        t3Model.getWls());

                putNullCV(arrT3CV[i], Contract.T3dataEntry.COLUMN_NAME_MOD_REASON_BY,
                        t3Model.getModReasonBy());

                putNullCV(arrT3CV[i], Contract.T3dataEntry.COLUMN_NAME_BANNED_BY,
                        t3Model.getBannedBy());

                putNullCV(arrT3CV[i], Contract.T3dataEntry.COLUMN_NAME_NUM_REPORTS,
                        t3Model.getNumReports());

                putNullCV(arrT3CV[i], Contract.T3dataEntry.COLUMN_NAME_REMOVAL_REASON,
                        t3Model.getRemovalReason());

                putNullCV(arrT3CV[i], Contract.T3dataEntry.COLUMN_NAME_THUMBNAIL_WIDTH,
                        t3Model.getThumbnailWidth());

                putNullCV(arrT3CV[i], Contract.T3dataEntry.COLUMN_NAME_SUBREDDIT,
                        t3Model.getSubreddit());

                putNullCV(arrT3CV[i], Contract.T3dataEntry.COLUMN_NAME_SELFTEXT_HTML,
                        t3Model.getSelftextHtml());

                putNullCV(arrT3CV[i], Contract.T3dataEntry.COLUMN_NAME_SELFTEXT,
                        t3Model.getSelftext());

                putNullCV(arrT3CV[i], Contract.T3dataEntry.COLUMN_NAME_LIKES,
                        t3Model.getLikes());

                putNullCV(arrT3CV[i], Contract.T3dataEntry.COLUMN_NAME_SUGGESTED_SORT,
                        t3Model.getSuggestedSort());

                putNullCV(arrT3CV[i], Contract.T3dataEntry.COLUMN_NAME_SECURE_MEDIA,
                        t3Model.getSecureMedia());

                arrT3CV[i].put(Contract.T3dataEntry.COLUMN_NAME_IS_REDDIT_MEDIA_DOMAIN,
                        Utility.boolToInt(t3Model.getIsRedditMediaDomain()));

                arrT3CV[i].put(Contract.T3dataEntry.COLUMN_NAME_SAVED,
                        Utility.boolToInt(t3Model.getSaved()));

                arrT3CV[i].put(Contract.T3dataEntry.COLUMN_NAME_CHILDREN_ID,
                        childrenId);

                arrT3CV[i].put(Contract.T3dataEntry.COLUMN_NAME_ID,
                        t3Model.getId());

                putNullCV(arrT3CV[i], Contract.T3dataEntry.COLUMN_NAME_BANNED_AT_UTC,
                        t3Model.getBannedAtUtc());

                putNullCV(arrT3CV[i], Contract.T3dataEntry.COLUMN_NAME_MOD_REASON_TITLE,
                        t3Model.getModReasonTitle());

                putNullCV(arrT3CV[i], Contract.T3dataEntry.COLUMN_NAME_VIEW_COUNT,
                        t3Model.getViewCount());

                arrT3CV[i].put(Contract.T3dataEntry.COLUMN_NAME_ARCHIVED,
                        Utility.boolToInt(t3Model.getArchived()));

                arrT3CV[i].put(Contract.T3dataEntry.COLUMN_NAME_CLICKED,
                        Utility.boolToInt(t3Model.getClicked()));


                arrT3CV[i].put(Contract.T3dataEntry.COLUMN_NAME_NO_FOLLOW,
                        Utility.boolToInt(t3Model.getNoFollow()));

                putNullCV(arrT3CV[i], Contract.T3dataEntry.COLUMN_NAME_AUTHOR,
                        t3Model.getAuthor());

                arrT3CV[i].put(Contract.T3dataEntry.COLUMN_NAME_NUM_CROSSPOSTS,
                        t3Model.getNumCrossposts());

                putNullCV(arrT3CV[i], Contract.T3dataEntry.COLUMN_NAME_LINK_FLAIR_TEXT,
                        t3Model.getLinkFlairText());

                arrT3CV[i].put(Contract.T3dataEntry.COLUMN_NAME_CAN_MOD_POST,
                        Utility.boolToInt(t3Model.getCanModPost()));

                arrT3CV[i].put(Contract.T3dataEntry.COLUMN_NAME_SEND_REPLIES,
                        Utility.boolToInt(t3Model.getSendReplies()));

                arrT3CV[i].put(Contract.T3dataEntry.COLUMN_NAME_PINNED,
                        Utility.boolToInt(t3Model.getPinned()));

                arrT3CV[i].put(Contract.T3dataEntry.COLUMN_NAME_SCORE,
                        t3Model.getScore());

                putNullCV(arrT3CV[i], Contract.T3dataEntry.COLUMN_NAME_APPROVED_BY,
                        t3Model.getApprovedBy());

                arrT3CV[i].put(Contract.T3dataEntry.COLUMN_NAME_OVER_18,
                        t3Model.getOver18());

                putNullCV(arrT3CV[i], Contract.T3dataEntry.COLUMN_NAME_REPORT_REASONS,
                        t3Model.getReportReasons());

                putNullCV(arrT3CV[i], Contract.T3dataEntry.COLUMN_NAME_DOMAIN,
                        t3Model.getDomain());

                arrT3CV[i].put(Contract.T3dataEntry.COLUMN_NAME_HIDDEN,
                        Utility.boolToInt(t3Model.getHidden()));

                arrT3CV[i].put(Contract.T3dataEntry.COLUMN_NAME_PWLS,
                        t3Model.getPwls());

                putNullCV(arrT3CV[i], Contract.T3dataEntry.COLUMN_NAME_THUMBNAIL,
                        t3Model.getThumbnail());

                arrT3CV[i].put(Contract.T3dataEntry.COLUMN_NAME_EDITED,
                        Utility.toInt(t3Model.getEdited()));

                putNullCV(arrT3CV[i], Contract.T3dataEntry.COLUMN_NAME_LINK_FLAIR_CSS_CLASS,
                        t3Model.getLinkFlairCssClass());

                putNullCV(arrT3CV[i], Contract.T3dataEntry.COLUMN_NAME_AUTHOR_FLAIR_CSS_CLASS,
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

                putNullCV(arrT3CV[i], Contract.T3dataEntry.COLUMN_NAME_THUMBNAIL_HEIGHT,
                        t3Model.getThumbnailHeight());

                putNullCV(arrT3CV[i], Contract.T3dataEntry.COLUMN_NAME_NAME,
                        t3Model.getName());

                arrT3CV[i].put(Contract.T3dataEntry.COLUMN_NAME_SPOILER,
                        Utility.boolToInt(t3Model.getSpoiler()));

                putNullCV(arrT3CV[i], Contract.T3dataEntry.COLUMN_NAME_PERMALINK,
                        t3Model.getPermalink());

                putNullCV(arrT3CV[i], Contract.T3dataEntry.COLUMN_NAME_SUBREDDIT_TYPE,
                        t3Model.getSubredditType());

                putNullCV(arrT3CV[i], Contract.T3dataEntry.COLUMN_NAME_PARENT_WHITELIST_STATUS,
                        t3Model.getParentWhitelistStatus());

                arrT3CV[i].put(Contract.T3dataEntry.COLUMN_NAME_HIDE_SCORE,
                        Utility.boolToInt(t3Model.getHideScore()));

                arrT3CV[i].put(Contract.T3dataEntry.COLUMN_NAME_CREATED,
                        t3Model.getCreated());

                putNullCV(arrT3CV[i], Contract.T3dataEntry.COLUMN_NAME_AUTHOR_FLAIR_TEXT,
                        t3Model.getAuthorFlairText());

                putNullCV(arrT3CV[i], Contract.T3dataEntry.COLUMN_NAME_QUARANTINE,
                        Utility.boolToInt(t3Model.getQuarantine()));

                putNullCV(arrT3CV[i], Contract.T3dataEntry.COLUMN_NAME_TITLE,
                        t3Model.getTitle());

                arrT3CV[i].put(Contract.T3dataEntry.COLUMN_NAME_CREATED_UTC,
                        t3Model.getCreatedUtc());

                putNullCV(arrT3CV[i], Contract.T3dataEntry.COLUMN_NAME_SUBREDDIT_NAME_PREFIXE,
                        t3Model.getSubredditNamePrefixed());

                putNullCV(arrT3CV[i], Contract.T3dataEntry.COLUMN_NAME_UPS,
                        t3Model.getUps());

                arrT3CV[i].put(Contract.T3dataEntry.COLUMN_NAME_NUM_COMMENTS,
                        t3Model.getNumComments());

                putNullCV(arrT3CV[i], Contract.T3dataEntry.COLUMN_NAME_MEDIA,
                        t3Model.getMedia());


                arrT3CV[i].put(Contract.T3dataEntry.COLUMN_NAME_IS_SELF,
                        Utility.boolToInt(t3Model.getIsSelf()));

                putNullCV(arrT3CV[i], Contract.T3dataEntry.COLUMN_NAME_WHITELIST_STATUS,
                        t3Model.getWhitelistStatus());

                putNullCV(arrT3CV[i], Contract.T3dataEntry.COLUMN_NAME_MOD_NOTE,
                        t3Model.getModNote());


                arrT3CV[i].put(Contract.T3dataEntry.COLUMN_NAME_IS_VIDEO,
                        Utility.boolToInt(t3Model.getIsVideo()));

                putNullCV(arrT3CV[i], Contract.T3dataEntry.COLUMN_NAME_DISTINGUISHED,
                        t3Model.getDistinguished());

                String stringCategoryNormalizeSub = Utility.normalizeSubRedditLink(t3Model.getSubreddit());
                putNullCV(arrT3CV[i], Contract.T3dataEntry.COLUMN_NAME_SUBREDDIT,
                        stringCategoryNormalizeSub);

                if (t3Model.getPreview() != null) {

                    ArrayList<ImageOptimize> imageOptimizeArrayList = new ArrayList<>();

                    boolean isOriginalSizeContent = PrefManager.isGeneralSettings(mContext, mContext.getString(R.string.pref_original_size_content));

                    ArrayList<ImageOptimize> optimizeArrayList = showThumbnailImagesContent(mContext, t3Model, isOriginalSizeContent);

                    if (optimizeArrayList != null) {
                        imageOptimizeArrayList.add(optimizeArrayList.get(0));
                    }

                    String previewUrl = imageOptimizeArrayList.get(0).url;
                    int previewWidth = imageOptimizeArrayList.get(0).width;
                    int previewHeight = imageOptimizeArrayList.get(0).height;


                    putNullCV(arrT3CV[i], Contract.T3dataEntry.COLUMN_NAME_PREVIEW_IMAGE_SOURCE_URL,
                            previewUrl);

                    putNullCV(arrT3CV[i], Contract.T3dataEntry.COLUMN_NAME_PREVIEW_IMAGE_SOURCE_WIDTH,
                            previewWidth);

                    putNullCV(arrT3CV[i], Contract.T3dataEntry.COLUMN_NAME_PREVIEW_IMAGE_SOURCE_HEIGHT,
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

                        putNullCV(arrT3CV[i], Contract.T3dataEntry.COLUMN_NAME_PREVIEW_VIDEO_HLS_URL,
                                videoHlsUrl);

                        putNullCV(arrT3CV[i], Contract.T3dataEntry.COLUMN_NAME_PREVIEW_VIDEO_DASH_URL,
                                videoDashUrl);

                        putNullCV(arrT3CV[i], Contract.T3dataEntry.COLUMN_NAME_PREVIEW_VIDEO_SCRUBBER_MEDIA_URL,
                                videoScrubberMediaUrl);

                        putNullCV(arrT3CV[i], Contract.T3dataEntry.COLUMN_NAME_PREVIEW_VIDEO_FALLBACK_URL,
                                videoFallbackUrl);

                        putNullCV(arrT3CV[i], Contract.T3dataEntry.COLUMN_NAME_PREVIEW_VIDEO_TRANSCODING_STATUS,
                                videoTranscodingStatus);

                        putNullCV(arrT3CV[i], Contract.T3dataEntry.COLUMN_NAME_PREVIEW_VIDEO_DURATION,
                                videoDuration);

                        putNullCV(arrT3CV[i], Contract.T3dataEntry.COLUMN_NAME_PREVIEW_VIDEO_WIDTH,
                                videoWidth);

                        putNullCV(arrT3CV[i], Contract.T3dataEntry.COLUMN_NAME_PREVIEW_VIDEO_HEIGHT,
                                videoHeight);

                        putNullCV(arrT3CV[i], Contract.T3dataEntry.COLUMN_NAME_PREVIEW_IS_VIDEO_GIF,
                                isVideoGif);


                    }

                    Variants variants = t3Model.getPreview().getImages().get(0).getVariants();

                    if (variants != null) {
//                        variants.getMp4()

                        ArrayList<VideoOptimize> videoOptimizeArrayList = new ArrayList<>();
                        //noinspection CollectionAddAllCanBeReplacedWithConstructor
                        ArrayList<VideoOptimize> arrayList = showVideosContent(mContext, t3Model,
                                isOriginalSizeContent);
                        if (arrayList != null) {
                            videoOptimizeArrayList.add(arrayList.get(0));

                            String videoMp4Url = videoOptimizeArrayList.get(0).url;
                            int videoMp4Width = videoOptimizeArrayList.get(0).width;
                            int videoMp4Height = videoOptimizeArrayList.get(0).height;

                            putNullCV(arrT3CV[i], Contract.T3dataEntry.COLUMN_NAME_VARIANT_VIDEO_MP4_URL,
                                    videoMp4Url);

                            putNullCV(arrT3CV[i], Contract.T3dataEntry.COLUMN_NAME_VARIANT_VIDEO_MP4_WIDTH,
                                    videoMp4Width);

                            putNullCV(arrT3CV[i], Contract.T3dataEntry.COLUMN_NAME_VARIANT_VIDEO_MP4_HEIGHT,
                                    videoMp4Height);


                        }
                    }

                }

            }

        }

        final Uri uriReddit = mContext.getContentResolver().insert(Contract.RedditEntry.CONTENT_URI, subRedditCV);
        final Uri uriData = mContext.getContentResolver().insert(Contract.DataEntry.CONTENT_URI, dataCV);
        int countT3Data = mContext.getContentResolver().bulkInsert(Contract.T3dataEntry.CONTENT_URI, arrT3CV);

        return uriReddit != null && uriData != null && countT3Data != 0;
    }


    private ArrayList<ImageOptimize> showThumbnailImagesContent(Context context, T3Data t3DataChild, @SuppressWarnings("SameParameterValue") boolean originalSize) {

        int sizeResolution = 0;
        ArrayList<ImageOptimize> optimizeArrayList;
        ImageOptimize imageOptimize;

        if ((context == null) || (t3DataChild == null) || (t3DataChild.getPreview() == null)) {
            return null;
        }

        int densityDpi = context.getResources().getDisplayMetrics().densityDpi;


        if ((t3DataChild.getPreview().getImages() != null) && (!originalSize)) {

            sizeResolution = t3DataChild.getPreview().getImages().get(0).getResolutions().size();

        }

        optimizeArrayList = new ArrayList<>(1);
        imageOptimize = new ImageOptimize();
        List<Resolution> dataResolution = t3DataChild.getPreview().getImages().get(0).getResolutions();
        Source dataSource = t3DataChild.getPreview().getImages().get(0).getSource();

        int index;
        switch (densityDpi) {
            case DisplayMetrics.DENSITY_XXXHIGH:
            case DisplayMetrics.DENSITY_560:
                // XXXHDPI
                if (sizeResolution >= 6) {
                    index = 5;
                    imageOptimize.url = dataResolution.get(index).getUrl();
                    imageOptimize.width = dataResolution.get(index).getWidth();
                    imageOptimize.height = dataResolution.get(index).getHeight();
                    optimizeArrayList.add(imageOptimize);
                    return optimizeArrayList;
                }

            case DisplayMetrics.DENSITY_XXHIGH:
            case DisplayMetrics.DENSITY_360:
            case DisplayMetrics.DENSITY_400:
            case DisplayMetrics.DENSITY_420:
                // XXHDPI
                if (sizeResolution >= 5) {
                    index = 4;
                    imageOptimize.url = dataResolution.get(index).getUrl();
                    imageOptimize.width = dataResolution.get(index).getWidth();
                    imageOptimize.height = dataResolution.get(index).getHeight();
                    optimizeArrayList.add(imageOptimize);
                    return optimizeArrayList;
                }


            case DisplayMetrics.DENSITY_XHIGH:
            case DisplayMetrics.DENSITY_280:
                // XHDPI
                if (sizeResolution >= 4) {
                    index = 3;
                    imageOptimize.url = dataResolution.get(index).getUrl();
                    imageOptimize.width = dataResolution.get(index).getWidth();
                    imageOptimize.height = dataResolution.get(index).getHeight();
                    optimizeArrayList.add(imageOptimize);
                    return optimizeArrayList;
                }


            case DisplayMetrics.DENSITY_TV:
            case DisplayMetrics.DENSITY_HIGH:
                // HDPI
                if (sizeResolution >= 3) {
                    index = 2;
                    imageOptimize.url = dataResolution.get(index).getUrl();
                    imageOptimize.width = dataResolution.get(index).getWidth();
                    imageOptimize.height = dataResolution.get(index).getHeight();
                    optimizeArrayList.add(imageOptimize);
                    return optimizeArrayList;
                }


            case DisplayMetrics.DENSITY_MEDIUM:
                // MDPI
                if (sizeResolution >= 2) {
                    index = 1;
                    imageOptimize.url = dataResolution.get(index).getUrl();
                    imageOptimize.width = dataResolution.get(index).getWidth();
                    imageOptimize.height = dataResolution.get(index).getHeight();
                    optimizeArrayList.add(imageOptimize);
                    return optimizeArrayList;
                }


            case DisplayMetrics.DENSITY_LOW:
                if (sizeResolution >= 1) {
                    index = 0;
                    imageOptimize.url = dataResolution.get(index).getUrl();
                    imageOptimize.width = dataResolution.get(index).getWidth();
                    imageOptimize.height = dataResolution.get(index).getHeight();
                    optimizeArrayList.add(imageOptimize);
                    return optimizeArrayList;
                }


            default:

                imageOptimize.url = dataSource.getUrl();
                imageOptimize.width = dataSource.getWidth();
                imageOptimize.height = dataSource.getHeight();
                optimizeArrayList.add(imageOptimize);
                return optimizeArrayList;
        }
    }

    private ArrayList<VideoOptimize> showVideosContent(Context context, T3Data t3DataChild, @SuppressWarnings("SameParameterValue") boolean originalSize) {

        int sizeResolution = 0;

        Mp4 mp4 = null;
        SourceMp4 dataSourceMp4 = null;
        Variants variants;
        VideoOptimize videoOptimize;

        ArrayList<VideoOptimize> optimizeArrayList;
        List<ResolutionMp4> dataResolutionMp4s = null;

        if ((context == null) || (t3DataChild == null) || (t3DataChild.getPreview() == null)) {
            return null;
        }

        optimizeArrayList = new ArrayList<>(1);
        videoOptimize = new VideoOptimize();

        variants = t3DataChild.getPreview().getImages().get(0).getVariants();

        if (variants != null) {
            mp4 = t3DataChild.getPreview().getImages().get(0).getVariants().getMp4();
        }

        if (mp4 != null) {
            dataSourceMp4 = mp4.getSource();
            dataResolutionMp4s = mp4.getResolutions();

            if ((dataResolutionMp4s != null) && (!originalSize)) {
                sizeResolution = t3DataChild.getPreview().getImages().get(0).getVariants().getMp4().getResolutions().size();
            }
        }

        if ((dataResolutionMp4s != null) || (dataSourceMp4 != null)) {
            int index;
            int densityDpi = context.getResources().getDisplayMetrics().densityDpi;
            switch (densityDpi) {
                case DisplayMetrics.DENSITY_XXXHIGH:
                case DisplayMetrics.DENSITY_560:
                    // XXXHDPI
                    if (sizeResolution >= 6) {
                        index = 5;
                        videoOptimize.url = dataResolutionMp4s.get(index).getUrl();
                        videoOptimize.width = dataResolutionMp4s.get(index).getWidth();
                        videoOptimize.height = dataResolutionMp4s.get(index).getHeight();
                        optimizeArrayList.add(videoOptimize);
                        return optimizeArrayList;
                    }

                case DisplayMetrics.DENSITY_XXHIGH:
                case DisplayMetrics.DENSITY_360:
                case DisplayMetrics.DENSITY_400:
                case DisplayMetrics.DENSITY_420:
                    // XXHDPI
                    if (sizeResolution >= 5) {
                        index = 4;
                        videoOptimize.url = dataResolutionMp4s.get(index).getUrl();
                        videoOptimize.width = dataResolutionMp4s.get(index).getWidth();
                        videoOptimize.height = dataResolutionMp4s.get(index).getHeight();
                        optimizeArrayList.add(videoOptimize);
                        return optimizeArrayList;
                    }


                case DisplayMetrics.DENSITY_XHIGH:
                case DisplayMetrics.DENSITY_280:
                    // XHDPI
                    if (sizeResolution >= 4) {
                        index = 3;
                        videoOptimize.url = dataResolutionMp4s.get(index).getUrl();
                        videoOptimize.width = dataResolutionMp4s.get(index).getWidth();
                        videoOptimize.height = dataResolutionMp4s.get(index).getHeight();
                        optimizeArrayList.add(videoOptimize);
                        return optimizeArrayList;
                    }


                case DisplayMetrics.DENSITY_TV:
                case DisplayMetrics.DENSITY_HIGH:
                    // HDPI
                    if (sizeResolution >= 3) {
                        index = 2;
                        videoOptimize.url = dataResolutionMp4s.get(index).getUrl();
                        videoOptimize.width = dataResolutionMp4s.get(index).getWidth();
                        videoOptimize.height = dataResolutionMp4s.get(index).getHeight();
                        optimizeArrayList.add(videoOptimize);
                        return optimizeArrayList;
                    }


                case DisplayMetrics.DENSITY_MEDIUM:
                    // MDPI
                    if (sizeResolution >= 2) {
                        index = 1;
                        videoOptimize.url = dataResolutionMp4s.get(index).getUrl();
                        videoOptimize.width = dataResolutionMp4s.get(index).getWidth();
                        videoOptimize.height = dataResolutionMp4s.get(index).getHeight();
                        optimizeArrayList.add(videoOptimize);
                        return optimizeArrayList;
                    }


                case DisplayMetrics.DENSITY_LOW:
                    if (sizeResolution >= 1) {
                        index = 0;
                        videoOptimize.url = dataResolutionMp4s.get(index).getUrl();
                        videoOptimize.width = dataResolutionMp4s.get(index).getWidth();
                        videoOptimize.height = dataResolutionMp4s.get(index).getHeight();
                        optimizeArrayList.add(videoOptimize);
                        return optimizeArrayList;
                    }


                default:
                    videoOptimize.url = dataSourceMp4.getUrl();
                    videoOptimize.width = dataSourceMp4.getWidth();
                    videoOptimize.height = dataSourceMp4.getHeight();
                    optimizeArrayList.add(videoOptimize);
                    return optimizeArrayList;
            }
        }
        return null;
    }


    private void putNullCV(ContentValues contentValues, String contractEntry, Object strObject) {
        if (strObject == null) {
            contentValues.putNull(contractEntry);
        } else {
            contentValues.put(contractEntry, String.valueOf(strObject));
        }
    }

    public boolean saveDataT5(T5 reddits) {
        if (isRecordData()) clearDataT5();
        if (insertDataT5(reddits)) {

            if (!PrefManager.getBoolPref(mContext, R.string.pref_insert_data)) {
                if (insertDataPrefSubReddit(reddits)) {
                    PrefManager.putBoolPref(mContext, R.string.pref_insert_data, true);
                }
            }

            return true;
        }
        return false;
    }

    public boolean saveDataT3(T3 reddits, String category) {
        if (!TextUtils.isEmpty(category)) {
            if (isDeleteDataT3(category)) {
                deleteCategory(Contract.PATH_T3DATAS, category);
            }
            return insertDataT3(reddits);
        }
        return false;
    }

    private boolean isDeleteDataT3(String category) {
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
                int timeUpdateDatabase = PrefManager.getIntGeneralSettings(mContext, R.string.pref_sync_frequency);
                isDeleted = Utility.getSecondsTimeStamp(timestamp) > timeUpdateDatabase;
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


    public boolean deleteCategory(String contractPath, String category) {
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

        return uri != null && mContext.getContentResolver().delete(uri, where, selectionArgs) > 0;

    }

    public boolean updateManageRemoved(String category) {

        int count;

        Uri uri = Contract.PrefSubRedditEntry.CONTENT_URI;
        String where = Contract.PrefSubRedditEntry.COLUMN_NAME_NAME + " =? ";
        String[] selectionArgs = {category};

        ContentValues contentValues = new ContentValues();
        contentValues.put(Contract.PrefSubRedditEntry.COLUMN_NAME_REMOVED, Costants.REMOVED_SUBREDDIT_ITEMS);

        count = mContext.getContentResolver().update(uri, contentValues, where, selectionArgs);

        return count > 0;
    }

    public boolean updateManageRestore() {

        int count;

        Uri uri = Contract.PrefSubRedditEntry.CONTENT_URI;

        ContentValues contentValues = new ContentValues();
        contentValues.put(Contract.PrefSubRedditEntry.COLUMN_NAME_REMOVED, Costants.RESTORE_SUBREDDIT_ITEMS);

        count = mContext.getContentResolver().update(uri, contentValues, null, null);
        String stringPref = restorePrefFromDb();

        if (!TextUtils.isEmpty(restorePrefFromDb())) {

            PrefManager.putStringPref(mContext, R.string.pref_subreddit_key, stringPref);

        } else {

            return false;
        }

        return count > 0;
    }

    private String restorePrefFromDb() {
        Cursor cursor = null;
        String stringPref = "";
        try {
            cursor = mContext.getContentResolver().query(Contract.PrefSubRedditEntry.CONTENT_URI, null, null, null, null);

            if ((cursor != null) && (!cursor.isClosed())) {

                String name;
                int i = 0;

                while (cursor.moveToNext()) {
                    i += 1;
                    name = cursor.getString(cursor.getColumnIndex(Contract.PrefSubRedditEntry.COLUMN_NAME_NAME));
                    if (!TextUtils.isEmpty(name)) {
                        if (i < cursor.getCount()) {
                            //noinspection StringConcatenationInLoop
                            stringPref += name + Costants.STRING_SEPARATOR;
                        } else {
                            //noinspection StringConcatenationInLoop
                            stringPref += name;
                        }
                    }
                }
            }

        } catch (Exception e) {

            Timber.e("restore pref from db error %s", e.getMessage());

        } finally {

            if ((cursor != null) && (!cursor.isClosed())) {
                cursor.close();
            }

        }

        return stringPref;
    }

    public boolean updateVisibleStar(int visible, String category) {

        int count;

        Uri uri = Contract.PrefSubRedditEntry.CONTENT_URI;
        String where = Contract.PrefSubRedditEntry.COLUMN_NAME_NAME + " =?";
        String[] selectionArgs = {category};

        ContentValues contentValues = new ContentValues();
        contentValues.put(Contract.PrefSubRedditEntry.COLUMN_NAME_VISIBLE, visible);

        count = mContext.getContentResolver().update(uri, contentValues, where, selectionArgs);

        return count > 0;
    }


    public boolean moveManage(int fromPosition, int toPosition) {

        Cursor cursor = null;
        int count = 0;
        Uri uri;

        if ((fromPosition == 0) && (toPosition == 0)) {
            return false;

        } else {

            fromPosition += 1;
            toPosition += 1;
            uri = Contract.PrefSubRedditEntry.CONTENT_URI;
        }

        try {

            ContentValues contentValues = new ContentValues();
            contentValues.put(Contract.PrefSubRedditEntry.COLUMN_NAME_POSITION, toPosition);
            contentValues.put(Contract.PrefSubRedditEntry.COLUMN_NAME_TIME_LAST_MODIFIED, System.currentTimeMillis());

            String where = Contract.PrefSubRedditEntry.COLUMN_NAME_POSITION + " =?";
            String[] selectionArgs = {String.valueOf(fromPosition)};

            count = mContext.getContentResolver().update(uri, contentValues, where, selectionArgs);

            if (count > 0) {

                int idPosix = 0;

                String selection = Contract.PrefSubRedditEntry.COLUMN_NAME_POSITION + " =?";
                selectionArgs[0] = String.valueOf(toPosition);
                String sortOrder = Contract.PrefSubRedditEntry.COLUMN_NAME_TIME_LAST_MODIFIED + " DESC";

                cursor = mContext.getContentResolver().query(uri, null,
                        selection,
                        selectionArgs,
                        sortOrder);

                if ((cursor != null) && (!cursor.isClosed())) {

                    cursor.moveToFirst();
                    idPosix = cursor.getInt(cursor.getColumnIndex(Contract.PrefSubRedditEntry._ID));

                }

                if (idPosix != 0) {

                    where = Contract.PrefSubRedditEntry._ID + " =?";
                    selectionArgs[0] = String.valueOf(idPosix);

                    ContentValues updateDuplicateCV = new ContentValues();
                    updateDuplicateCV.put(Contract.PrefSubRedditEntry.COLUMN_NAME_POSITION, fromPosition);

                    count = mContext.getContentResolver().update(uri, updateDuplicateCV, where, selectionArgs);

                }

            } else {

                count = 0;
            }

        } catch (Exception e) {

            Timber.e("DATABASE Move Manage Db error %s", e.getMessage());

        } finally {

            if ((cursor != null) && (!cursor.isClosed())) {
                cursor.close();
            }
        }

        return count > 0;
    }


    public void clearDataPrivacy() {
        clearDataAll();
        PrefManager.putBoolPref(mContext, R.string.pref_insert_data, false);
    }

   /* private void savePrefSub(T5 reddits) {
        int size = reddits.getData().getChildren().size();
        ArrayList<String> arrayList = new ArrayList<>(size);
        String url;
        for (int i = 0; i < size; i++) {
            url = reddits.getData().getChildren().get(i).getData().getUrl();
            url = Utility.normalizeSubRedditLink(url);
            arrayList.add(url);
        }
        String strPref = Utility.arrayToString(arrayList);
        PrefManager.putStringPref(mContext, R.string.pref_subreddit_key, strPref);
    }
*/
}
