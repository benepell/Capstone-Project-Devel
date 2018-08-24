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
import android.net.Uri;

import info.pelleritoudacity.android.rcapstone.data.db.Contract;
import info.pelleritoudacity.android.rcapstone.data.db.util.DataUtils;
import info.pelleritoudacity.android.rcapstone.data.model.reddit.T5;
import info.pelleritoudacity.android.rcapstone.data.model.reddit.T5Data;
import info.pelleritoudacity.android.rcapstone.data.model.reddit.T5Listing;
import info.pelleritoudacity.android.rcapstone.utility.Costant;
import info.pelleritoudacity.android.rcapstone.utility.DateUtil;
import info.pelleritoudacity.android.rcapstone.utility.Preference;
import info.pelleritoudacity.android.rcapstone.utility.TextUtil;
import timber.log.Timber;

public class T5Operation {
    private final T5 mModelT5;
    private final Context mContext;

    public T5Operation(Context context, T5 modelT5) {
        mContext = context;
        mModelT5 = modelT5;
    }

    private int insertDataPrefSubReddit() {

        if (mModelT5 == null) return 0;

        int t5Size = mModelT5.getData().getChildren().size();
        int position = Costant.DEFAULT_SUBREDDIT_CATEGORY.split(Costant.STRING_SEPARATOR).length +1;
        ContentValues[] arrCV = new ContentValues[t5Size];

        DataUtils dataUtils = new DataUtils(mContext);

        for (int i = 0; i < t5Size; i++) {

            T5Data t5Model = mModelT5.getData().getChildren().get(i).getData();

            arrCV[i] = new ContentValues();

            dataUtils.putNullCV(arrCV[i], Contract.PrefSubRedditEntry.COLUMN_NAME_NAME,
                    TextUtil.normalizeSubRedditLink(t5Model.getUrl()));

            dataUtils.putNullCV(arrCV[i], Contract.PrefSubRedditEntry.COLUMN_NAME_IMAGE,
                    t5Model.getIconImg());

            arrCV[i].put(Contract.T5dataEntry.COLUMN_NAME_TIME_LAST_MODIFIED, DateUtil.getNowTimeStamp());

            arrCV[i].put(Contract.PrefSubRedditEntry.COLUMN_NAME_VISIBLE,
                    Costant.DEFAULT_SUBREDDIT_VISIBLE);

            arrCV[i].put(Contract.PrefSubRedditEntry.COLUMN_NAME_POSITION,
                    position + i);

            arrCV[i].put(Contract.PrefSubRedditEntry.COLUMN_NAME_BACKUP_POSITION,
                    position + i);

        }

        try {
            return mContext.getContentResolver().bulkInsert(Contract.PrefSubRedditEntry.CONTENT_URI, arrCV);

        } catch (IllegalStateException e) {
            Timber.e("insert data error %s", e.getMessage());
        }
        return 0;
    }

    private boolean insertData() {

        if (mModelT5 == null) return false;

        // reddit
        ContentValues redditCV = new ContentValues();
        redditCV.put(Contract.RedditEntry.COLUMN_NAME_KIND, mModelT5.getKind());
        redditCV.put(Contract.RedditEntry.COLUMN_NAME_DATA, 1);

        // data
        T5Listing dataModel = mModelT5.getData();
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
            dataCV.put(Contract.DataEntry.COLUMN_NAME_BEFORE, dataModel.getBefore());
        }

        int childrenId;

        int t5Size = mModelT5.getData().getChildren().size();

        DataUtils dataUtils = new DataUtils(mContext);

        ContentValues[] arrCV = new ContentValues[t5Size];

        for (int i = 0; i < t5Size; i++) {

            childrenId = i + 1;

            dataCV.put(Contract.DataEntry.COLUMN_NAME_CHILDRENS, childrenId);

            T5Data t5Model = mModelT5.getData().getChildren().get(i).getData();

            arrCV[i] = new ContentValues();

            dataUtils.putNullCV(arrCV[i], Contract.T5dataEntry.COLUMN_NAME_SUGGESTED_COMMENT_SORT,
                    t5Model.getSuggestedCommentSort());

            arrCV[i].put(Contract.T5dataEntry.COLUMN_NAME_HIDE_ADS,
                    t5Model.getHideAds());

            dataUtils.putNullCV(arrCV[i], Contract.T5dataEntry.COLUMN_NAME_BANNER_IMG,
                    t5Model.getBannerImg());

            arrCV[i].put(Contract.T5dataEntry.COLUMN_NAME_USER_SR_THEME_ENABLED,
                    t5Model.getUserSrThemeEnabled());

            dataUtils.putNullCV(arrCV[i], Contract.T5dataEntry.COLUMN_NAME_USER_FLAIR_TEXT,
                    t5Model.getUserFlairText());

            dataUtils.putNullCV(arrCV[i], Contract.T5dataEntry.COLUMN_NAME_SUBMIT_TEXT_HTML,
                    t5Model.getSubmitTextHtml());

            dataUtils.putNullCV(arrCV[i], Contract.T5dataEntry.COLUMN_NAME_USER_IS_BANNED,
                    t5Model.getUserIsBanned());

            arrCV[i].put(Contract.T5dataEntry.COLUMN_NAME_WIKI_ENABLED,
                    t5Model.getWikiEnabled());

            arrCV[i].put(Contract.T5dataEntry.COLUMN_NAME_SHOW_MEDIA,
                    t5Model.getShowMedia());

            dataUtils.putNullCV(arrCV[i], Contract.T5dataEntry.COLUMN_NAME_ID,
                    t5Model.getId());

            arrCV[i].put(Contract.T5dataEntry.COLUMN_NAME_CHILDREN_ID,
                    childrenId);

            dataUtils.putNullCV(arrCV[i], Contract.T5dataEntry.COLUMN_NAME_DISPLAY_NAME_PREFIXED,
                    t5Model.getDisplayNamePrefixed());

            dataUtils.putNullCV(arrCV[i], Contract.T5dataEntry.COLUMN_NAME_HEADER_IMG,
                    t5Model.getHeaderImg());


            dataUtils.putNullCV(arrCV[i], Contract.T5dataEntry.COLUMN_NAME_DESCRIPTION_HTML,
                    t5Model.getDescriptionHtml());

            dataUtils.putNullCV(arrCV[i], Contract.T5dataEntry.COLUMN_NAME_TITLE,
                    t5Model.getTitle());

            arrCV[i].put(Contract.T5dataEntry.COLUMN_NAME_COLLAPSE_DELETED_COMMENTS,
                    t5Model.getCollapseDeletedComments());

            dataUtils.putNullCV(arrCV[i], Contract.T5dataEntry.COLUMN_NAME_USER_HAS_FAVORITED,
                    t5Model.getUserHasFavorited());

            arrCV[i].put(Contract.T5dataEntry.COLUMN_NAME_OVER18,
                    t5Model.getOver18());

            dataUtils.putNullCV(arrCV[i], Contract.T5dataEntry.COLUMN_NAME_PUBLIC_DESCRIPTION_HTML,
                    t5Model.getPublicDescriptionHtml());

            arrCV[i].put(Contract.T5dataEntry.COLUMN_NAME_ALLOW_VIDEOS,
                    t5Model.getAllowVideos());

            arrCV[i].put(Contract.T5dataEntry.COLUMN_NAME_SPOILERS_ENABLED,
                    t5Model.getSpoilersEnabled());

            dataUtils.putNullCV(arrCV[i], Contract.T5dataEntry.COLUMN_NAME_AUDIENCE_TARGET,
                    t5Model.getAudienceTarget());

            dataUtils.putNullCV(arrCV[i], Contract.T5dataEntry.COLUMN_NAME_NOTIFICATION_LEVEL,
                    t5Model.getNotificationLevel());

            dataUtils.putNullCV(arrCV[i], Contract.T5dataEntry.COLUMN_NAME_ACTIVE_USER_COUNT,
                    t5Model.getActiveUserCount());

            dataUtils.putNullCV(arrCV[i], Contract.T5dataEntry.COLUMN_NAME_ICON_IMG,
                    t5Model.getIconImg());

            dataUtils.putNullCV(arrCV[i], Contract.T5dataEntry.COLUMN_NAME_HEADER_TITLE,
                    t5Model.getHeaderTitle());

            dataUtils.putNullCV(arrCV[i], Contract.T5dataEntry.COLUMN_NAME_DESCRIPTION,
                    t5Model.getDescription());

            dataUtils.putNullCV(arrCV[i], Contract.T5dataEntry.COLUMN_NAME_USER_IS_MUTED,
                    t5Model.getUserIsMuted());

            dataUtils.putNullCV(arrCV[i], Contract.T5dataEntry.COLUMN_NAME_SUBMIT_LINK_LABEL,
                    t5Model.getSubmitLinkLabel());

            dataUtils.putNullCV(arrCV[i], Contract.T5dataEntry.COLUMN_NAME_ACCOUNTS_ACTIVE,
                    t5Model.getAccountsActive());

            arrCV[i].put(Contract.T5dataEntry.COLUMN_NAME_PUBLIC_TRAFFIC,
                    t5Model.getPublicTraffic());

            arrCV[i].put(Contract.T5dataEntry.COLUMN_NAME_SUBSCRIBERS,
                    t5Model.getSubscribers());

            dataUtils.putNullCV(arrCV[i], Contract.T5dataEntry.COLUMN_NAME_USER_FLAIR_CSS_CLASS,
                    t5Model.getUserFlairCssClass());

            dataUtils.putNullCV(arrCV[i], Contract.T5dataEntry.COLUMN_NAME_SUBMIT_TEXT_LABEL,
                    t5Model.getSubmitTextLabel());

            dataUtils.putNullCV(arrCV[i], Contract.T5dataEntry.COLUMN_NAME_WHITELIST_STATUS,
                    t5Model.getWhitelistStatus());

            dataUtils.putNullCV(arrCV[i], Contract.T5dataEntry.COLUMN_NAME_USER_SR_FLAIR_ENABLED,
                    t5Model.getUserSrFlairEnabled());

            dataUtils.putNullCV(arrCV[i], Contract.T5dataEntry.COLUMN_NAME_LANG,
                    t5Model.getLang());

            dataUtils.putNullCV(arrCV[i], Contract.T5dataEntry.COLUMN_NAME_USER_IS_MODERATOR,
                    t5Model.getUserIsModerator());

            dataUtils.putNullCV(arrCV[i], Contract.T5dataEntry.COLUMN_NAME_IS_ENROLLED_IN_NEW_MODMAIL,
                    t5Model.getIsEnrolledInNewModmail());

            dataUtils.putNullCV(arrCV[i], Contract.T5dataEntry.COLUMN_NAME_KEY_COLOR,
                    t5Model.getKeyColor());

            dataUtils.putNullCV(arrCV[i], Contract.T5dataEntry.COLUMN_NAME_NAME,
                    t5Model.getName());

            arrCV[i].put(Contract.T5dataEntry.COLUMN_NAME_USER_FLAIR_ENABLED_IN_SR,
                    t5Model.getUserFlairEnabledInSr());

            arrCV[i].put(Contract.T5dataEntry.COLUMN_NAME_ALLOW_VIDEOGIFS,
                    t5Model.getAllowVideogifs());

            dataUtils.putNullCV(arrCV[i], Contract.T5dataEntry.COLUMN_NAME_URL,
                    t5Model.getUrl());

            arrCV[i].put(Contract.T5dataEntry.COLUMN_NAME_QUARANTINE,
                    t5Model.getQuarantine());

            arrCV[i].put(Contract.T5dataEntry.COLUMN_NAME_CREATED,
                    t5Model.getCreated());

            arrCV[i].put(Contract.T5dataEntry.COLUMN_NAME_CREATED_UTC,
                    t5Model.getCreatedUtc());

            dataUtils.putNullCV(arrCV[i], Contract.T5dataEntry.COLUMN_NAME_USER_IS_CONTRIBUTOR,
                    t5Model.getUserIsContributor());

            arrCV[i].put(Contract.T5dataEntry.COLUMN_NAME_ALLOW_DISCOVERY,
                    t5Model.getAllowDiscovery());

            arrCV[i].put(Contract.T5dataEntry.COLUMN_NAME_ACCOUNTS_ACTIVE_IS_FUZZED,
                    t5Model.getAccountsActiveIsFuzzed());

            dataUtils.putNullCV(arrCV[i], Contract.T5dataEntry.COLUMN_NAME_ADVERTISER_CATEGORY,
                    t5Model.getAdvertiserCategory());

            dataUtils.putNullCV(arrCV[i], Contract.T5dataEntry.COLUMN_NAME_PUBLIC_DESCRIPTION,
                    t5Model.getPublicDescription());

            arrCV[i].put(Contract.T5dataEntry.COLUMN_NAME_LINK_FLAIR_ENABLED,
                    t5Model.getLinkFlairEnabled());

            arrCV[i].put(Contract.T5dataEntry.COLUMN_NAME_ALLOW_IMAGES,
                    t5Model.getAllowImages());

            arrCV[i].put(Contract.T5dataEntry.COLUMN_NAME_SHOW_MEDIA_PREVIEW,
                    t5Model.getShowMediaPreview());

            arrCV[i].put(Contract.T5dataEntry.COLUMN_NAME_COMMENT_SCORE_HIDE_MINS,
                    t5Model.getCommentScoreHideMins());

            dataUtils.putNullCV(arrCV[i], Contract.T5dataEntry.COLUMN_NAME_SUBREDDIT_TYPE,
                    t5Model.getSubredditType());

            dataUtils.putNullCV(arrCV[i], Contract.T5dataEntry.COLUMN_NAME_SUBMISSION_TYPE,
                    t5Model.getSubmissionType());

            dataUtils.putNullCV(arrCV[i], Contract.T5dataEntry.COLUMN_NAME_SORT_BY,
                    Preference.getSubredditSort(mContext));

            dataUtils.putNullCV(arrCV[i], Contract.T5dataEntry.COLUMN_NAME_USER_IS_SUBSCRIBER,
                    t5Model.getUserIsSubscriber());

        }

        try {
            final Uri uriReddit = mContext.getContentResolver().insert(Contract.RedditEntry.CONTENT_URI, redditCV);
            final Uri uriData = mContext.getContentResolver().insert(Contract.DataEntry.CONTENT_URI, dataCV);
            int countT5Data = mContext.getContentResolver().bulkInsert(Contract.T5dataEntry.CONTENT_URI, arrCV);

            return uriReddit != null && uriData != null && countT5Data != 0;

        } catch (IllegalStateException e) {
            Timber.e("Insert data error %s", e.getMessage());

        }

        return false;

    }

    public void saveData() {
        DataUtils dataUtils = new DataUtils(mContext);
        if (!Preference.isInsertPrefs(mContext)) {
            clearData();
        }

        if (insertData()) {
            // todo add flag add default category
            int startPosition = insertDataPrefSubReddit();
            if (startPosition > 0) {
                Preference.setInsertPrefs(mContext, true);
                dataUtils.insertDefaultSubreddit();
            }

        }
    }

    public void clearData() {
        try {
            mContext.getContentResolver().delete(Contract.RedditEntry.CONTENT_URI, null, null);
            mContext.getContentResolver().delete(Contract.DataEntry.CONTENT_URI, null, null);
            mContext.getContentResolver().delete(Contract.T5dataEntry.CONTENT_URI, null, null);

        } catch (IllegalStateException e) {
            Timber.e("clear data error %s", e.getMessage());

        }

    }


}
