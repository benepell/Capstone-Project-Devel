
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
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;

import java.util.ArrayList;

import info.pelleritoudacity.android.rcapstone.R;
import info.pelleritoudacity.android.rcapstone.model.Data;
import info.pelleritoudacity.android.rcapstone.model.Reddit;
import info.pelleritoudacity.android.rcapstone.model.T5Data;
import info.pelleritoudacity.android.rcapstone.utility.PrefManager;
import info.pelleritoudacity.android.rcapstone.utility.Utility;


public class DataUtils {

    private final Context mContext;

    public DataUtils(Context context) {
        mContext = context;
    }

    private boolean isRecordData() {

        Cursor cursor = mContext.getContentResolver().query(Contract.RedditEntry.CONTENT_URI, null, null, null, null);
        boolean isRecord = ((cursor != null) && (cursor.getCount() > 0));

        if (cursor != null) cursor.close();
        return isRecord;
    }

    private void clearData() {

        mContext.getContentResolver().delete(Contract.RedditEntry.CONTENT_URI, null, null);
        mContext.getContentResolver().delete(Contract.DataEntry.CONTENT_URI, null, null);
        mContext.getContentResolver().delete(Contract.T5dataEntry.CONTENT_URI, null, null);
    }

    private boolean insertData(Reddit reddits) {

        if (reddits == null) return false;

        // reddit
        ContentValues redditCV = new ContentValues();
        redditCV.put(Contract.RedditEntry.COLUMN_NAME_KIND, reddits.getKind());
        redditCV.put(Contract.RedditEntry.COLUMN_NAME_DATA, 1);

        // data
        Data dataModel = reddits.getData();
        ContentValues dataCV = new ContentValues();
        dataCV.put(Contract.DataEntry.COLUMN_NAME_AFTER, dataModel.getAfter());
        dataCV.put(Contract.DataEntry.COLUMN_NAME_DIST, dataModel.getDist());
        dataCV.put(Contract.DataEntry.COLUMN_NAME_MODHASH, dataModel.getModhash());

        if (dataModel.getWhitelistStatus() == null) {
            dataCV.putNull(Contract.DataEntry.COLUMN_NAME_WHITELIST_STATUS);
        } else {
            dataCV.put(Contract.DataEntry.COLUMN_NAME_WHITELIST_STATUS, dataModel.getWhitelistStatus());
        }

        dataCV.put(Contract.DataEntry.COLUMN_NAME_CHILDRENS, 0);

        if (dataModel.getBefore() == null) {
            dataCV.putNull(Contract.DataEntry.COLUMN_NAME_BEFORE);
        } else {
            dataCV.put(Contract.DataEntry.COLUMN_NAME_BEFORE, (byte) dataModel.getBefore());
        }

        int childrenId;

        int t5Size = reddits.getData().getChildren().size();

        ContentValues[] arrT5CV = new ContentValues[t5Size];

        for (int i = 0; i < t5Size; i++) {

            childrenId = i;

            T5Data t5Model = reddits.getData().getChildren().get(i).getData();

            arrT5CV[i] = new ContentValues();

            putNullCV(arrT5CV[i], Contract.T5dataEntry.COLUMN_NAME_SUGGESTED_COMMENT_SORT,
                    t5Model.getSuggestedCommentSort());

            arrT5CV[i].put(Contract.T5dataEntry.COLUMN_NAME_HIDE_ADS,
                    t5Model.getHideAds());

            arrT5CV[i].put(Contract.T5dataEntry.COLUMN_NAME_BANNER_IMG,
                    t5Model.getBannerImg());

            arrT5CV[i].put(Contract.T5dataEntry.COLUMN_NAME_USER_SR_THEME_ENABLED,
                    t5Model.getUserSrThemeEnabled());

            putNullCV(arrT5CV[i], Contract.T5dataEntry.COLUMN_NAME_USER_FLAIR_TEXT,
                    t5Model.getUserFlairText());

            arrT5CV[i].put(Contract.T5dataEntry.COLUMN_NAME_SUBMIT_TEXT_HTML,
                    t5Model.getSubmitTextHtml());

            putNullCV(arrT5CV[i], Contract.T5dataEntry.COLUMN_NAME_USER_IS_BANNED,
                    t5Model.getUserIsBanned());

            arrT5CV[i].put(Contract.T5dataEntry.COLUMN_NAME_WIKI_ENABLED,
                    t5Model.getWikiEnabled());

            arrT5CV[i].put(Contract.T5dataEntry.COLUMN_NAME_SHOW_MEDIA,
                    t5Model.getShowMedia());

            arrT5CV[i].put(Contract.T5dataEntry.COLUMN_NAME_ID,
                    t5Model.getId());

            arrT5CV[i].put(Contract.T5dataEntry.COLUMN_NAME_CHILDREN_ID,
                    childrenId);

            arrT5CV[i].put(Contract.T5dataEntry.COLUMN_NAME_DISPLAY_NAME_PREFIXED,
                    t5Model.getDisplayNamePrefixed());

            arrT5CV[i].put(Contract.T5dataEntry.COLUMN_NAME_HEADER_IMG,
                    t5Model.getHeaderImg());


            arrT5CV[i].put(Contract.T5dataEntry.COLUMN_NAME_DESCRIPTION_HTML,
                    t5Model.getDescriptionHtml());

            arrT5CV[i].put(Contract.T5dataEntry.COLUMN_NAME_TITLE,
                    t5Model.getTitle());

            arrT5CV[i].put(Contract.T5dataEntry.COLUMN_NAME_COLLAPSE_DELETED_COMMENTS,
                    t5Model.getCollapseDeletedComments());

            putNullCV(arrT5CV[i], Contract.T5dataEntry.COLUMN_NAME_USER_HAS_FAVORITED,
                    t5Model.getUserHasFavorited());

            arrT5CV[i].put(Contract.T5dataEntry.COLUMN_NAME_OVER18,
                    t5Model.getOver18());

            arrT5CV[i].put(Contract.T5dataEntry.COLUMN_NAME_PUBLIC_DESCRIPTION_HTML,
                    t5Model.getPublicDescriptionHtml());

            arrT5CV[i].put(Contract.T5dataEntry.COLUMN_NAME_ALLOW_VIDEOS,
                    t5Model.getAllowVideos());

            arrT5CV[i].put(Contract.T5dataEntry.COLUMN_NAME_SPOILERS_ENABLED,
                    t5Model.getSpoilersEnabled());

            arrT5CV[i].put(Contract.T5dataEntry.COLUMN_NAME_AUDIENCE_TARGET,
                    t5Model.getAudienceTarget());

            putNullCV(arrT5CV[i], Contract.T5dataEntry.COLUMN_NAME_NOTIFICATION_LEVEL,
                    t5Model.getNotificationLevel());

            putNullCV(arrT5CV[i], Contract.T5dataEntry.COLUMN_NAME_ACTIVE_USER_COUNT,
                    t5Model.getActiveUserCount());

            arrT5CV[i].put(Contract.T5dataEntry.COLUMN_NAME_ICON_IMG,
                    t5Model.getIconImg());

            arrT5CV[i].put(Contract.T5dataEntry.COLUMN_NAME_HEADER_TITLE,
                    t5Model.getHeaderTitle());

            arrT5CV[i].put(Contract.T5dataEntry.COLUMN_NAME_DESCRIPTION,
                    t5Model.getDescription());

            putNullCV(arrT5CV[i], Contract.T5dataEntry.COLUMN_NAME_USER_IS_MUTED,
                    t5Model.getUserIsMuted());

            putNullCV(arrT5CV[i], Contract.T5dataEntry.COLUMN_NAME_SUBMIT_LINK_LABEL,
                    t5Model.getSubmitLinkLabel());

            putNullCV(arrT5CV[i], Contract.T5dataEntry.COLUMN_NAME_ACCOUNTS_ACTIVE,
                    t5Model.getAccountsActive());

            arrT5CV[i].put(Contract.T5dataEntry.COLUMN_NAME_PUBLIC_TRAFFIC,
                    t5Model.getPublicTraffic());

            arrT5CV[i].put(Contract.T5dataEntry.COLUMN_NAME_SUBSCRIBERS,
                    t5Model.getSubscribers());

            putNullCV(arrT5CV[i], Contract.T5dataEntry.COLUMN_NAME_USER_FLAIR_CSS_CLASS,
                    t5Model.getUserFlairCssClass());

            arrT5CV[i].put(Contract.T5dataEntry.COLUMN_NAME_SUBMIT_TEXT_LABEL,
                    t5Model.getSubmitTextLabel());

            arrT5CV[i].put(Contract.T5dataEntry.COLUMN_NAME_WHITELIST_STATUS,
                    t5Model.getWhitelistStatus());

            putNullCV(arrT5CV[i], Contract.T5dataEntry.COLUMN_NAME_USER_SR_FLAIR_ENABLED,
                    t5Model.getUserSrFlairEnabled());

            arrT5CV[i].put(Contract.T5dataEntry.COLUMN_NAME_LANG,
                    t5Model.getLang());

            putNullCV(arrT5CV[i], Contract.T5dataEntry.COLUMN_NAME_USER_IS_MODERATOR,
                    t5Model.getUserIsModerator());

            putNullCV(arrT5CV[i], Contract.T5dataEntry.COLUMN_NAME_IS_ENROLLED_IN_NEW_MODMAIL,
                    t5Model.getIsEnrolledInNewModmail());

            arrT5CV[i].put(Contract.T5dataEntry.COLUMN_NAME_KEY_COLOR,
                    t5Model.getKeyColor());

            arrT5CV[i].put(Contract.T5dataEntry.COLUMN_NAME_NAME,
                    t5Model.getName());

            arrT5CV[i].put(Contract.T5dataEntry.COLUMN_NAME_USER_FLAIR_ENABLED_IN_SR,
                    t5Model.getUserFlairEnabledInSr());

            arrT5CV[i].put(Contract.T5dataEntry.COLUMN_NAME_ALLOW_VIDEOGIFS,
                    t5Model.getAllowVideogifs());

            arrT5CV[i].put(Contract.T5dataEntry.COLUMN_NAME_URL,
                    t5Model.getUrl());

            arrT5CV[i].put(Contract.T5dataEntry.COLUMN_NAME_QUARANTINE,
                    t5Model.getQuarantine());

            arrT5CV[i].put(Contract.T5dataEntry.COLUMN_NAME_CREATED,
                    t5Model.getCreated());

            arrT5CV[i].put(Contract.T5dataEntry.COLUMN_NAME_CREATED_UTC,
                    t5Model.getCreatedUtc());

            putNullCV(arrT5CV[i], Contract.T5dataEntry.COLUMN_NAME_USER_IS_CONTRIBUTOR,
                    t5Model.getUserIsContributor());

            arrT5CV[i].put(Contract.T5dataEntry.COLUMN_NAME_ALLOW_DISCOVERY,
                    t5Model.getAllowDiscovery());

            arrT5CV[i].put(Contract.T5dataEntry.COLUMN_NAME_ACCOUNTS_ACTIVE_IS_FUZZED,
                    t5Model.getAccountsActiveIsFuzzed());

            arrT5CV[i].put(Contract.T5dataEntry.COLUMN_NAME_ADVERTISER_CATEGORY,
                    t5Model.getAdvertiserCategory());

            arrT5CV[i].put(Contract.T5dataEntry.COLUMN_NAME_PUBLIC_DESCRIPTION,
                    t5Model.getPublicDescription());

            arrT5CV[i].put(Contract.T5dataEntry.COLUMN_NAME_LINK_FLAIR_ENABLED,
                    t5Model.getLinkFlairEnabled());

            arrT5CV[i].put(Contract.T5dataEntry.COLUMN_NAME_ALLOW_IMAGES,
                    t5Model.getAllowImages());

            arrT5CV[i].put(Contract.T5dataEntry.COLUMN_NAME_SHOW_MEDIA_PREVIEW,
                    t5Model.getShowMediaPreview());

            arrT5CV[i].put(Contract.T5dataEntry.COLUMN_NAME_COMMENT_SCORE_HIDE_MINS,
                    t5Model.getCommentScoreHideMins());

            arrT5CV[i].put(Contract.T5dataEntry.COLUMN_NAME_SUBREDDIT_TYPE,
                    t5Model.getSubredditType());

            arrT5CV[i].put(Contract.T5dataEntry.COLUMN_NAME_SUBMISSION_TYPE,
                    t5Model.getSubmissionType());

            putNullCV(arrT5CV[i], Contract.T5dataEntry.COLUMN_NAME_USER_IS_SUBSCRIBER,
                    t5Model.getUserIsSubscriber());
        }

        final Uri uriReddit = mContext.getContentResolver().insert(Contract.RedditEntry.CONTENT_URI, redditCV);
        final Uri uriData = mContext.getContentResolver().insert(Contract.DataEntry.CONTENT_URI, dataCV);
        int countT5Data = mContext.getContentResolver().bulkInsert(Contract.T5dataEntry.CONTENT_URI, arrT5CV);

        return (uriReddit != null) && (uriData != null) && (countT5Data != 0);
    }

    private void putNullCV(ContentValues contentValues, String contractEntry, Object strObject) {
        if (strObject == null) {
            contentValues.putNull(contractEntry);
        } else {
            contentValues.put(contractEntry, String.valueOf(strObject));
        }

    }

    private void prefInsertDb() {
        SharedPreferences pref = mContext.getSharedPreferences(mContext.getString(R.string.pref_insert_data), 0);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean(mContext.getString(R.string.pref_insert_data), true);
        editor.apply();

    }

    @SuppressWarnings("WeakerAccess")
    protected void clearPreferenceDb() {
        SharedPreferences pref = mContext.getSharedPreferences(mContext.getString(R.string.pref_insert_data), 0);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean(mContext.getString(R.string.pref_insert_data), false);
        editor.apply();
    }

    public boolean saveData(Reddit reddits) {
        if (isRecordData()) clearData();
        if (insertData(reddits)) {
            savePrefSub(reddits);
            prefInsertDb();
            return true;
        }
        return false;
    }

    public void ClearDataPrivacy() {
        clearData();
        clearPreferenceDb();
    }

    private void savePrefSub(Reddit reddits){
        int size = reddits.getData().getChildren().size();
        ArrayList<String> arrayList =  new ArrayList<>(size);

        for (int i = 0; i <size ; i++) {
           arrayList.add(reddits.getData().getChildren().get(i).getData().getTitle());
        }

        String strPref = Utility.arrayToString(arrayList);
        PrefManager.putStringPref(mContext,R.string.pref_subreddit_key,strPref);
    }
}
