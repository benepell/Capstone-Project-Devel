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

package info.pelleritoudacity.android.rcapstone.data.db.operation;

import android.content.Context;


import info.pelleritoudacity.android.rcapstone.data.db.AppDatabase;
import info.pelleritoudacity.android.rcapstone.data.db.AppExecutors;
import info.pelleritoudacity.android.rcapstone.data.db.entry.DataEntry;
import info.pelleritoudacity.android.rcapstone.data.db.entry.PrefSubRedditEntry;
import info.pelleritoudacity.android.rcapstone.data.db.entry.RedditEntry;
import info.pelleritoudacity.android.rcapstone.data.db.entry.T5Entry;
import info.pelleritoudacity.android.rcapstone.data.model.reddit.T5;
import info.pelleritoudacity.android.rcapstone.data.model.reddit.T5Data;
import info.pelleritoudacity.android.rcapstone.data.model.reddit.T5Listing;
import info.pelleritoudacity.android.rcapstone.utility.Costant;
import info.pelleritoudacity.android.rcapstone.utility.Preference;
import info.pelleritoudacity.android.rcapstone.utility.TextUtil;
import info.pelleritoudacity.android.rcapstone.utility.Utility;

public class T5Operation {
    private final T5 mModelT5;
    private final Context mContext;
    private final AppDatabase mDb;

    public T5Operation(Context context, AppDatabase db, T5 modelT5) {
        mContext = context;
        mDb = db;
        mModelT5 = modelT5;
    }

    private void insertDataPrefSubReddit() {

        if (mModelT5 == null) return;

        int t5Size = mModelT5.getData().getChildren().size();
        int position = Costant.DEFAULT_SUBREDDIT_CATEGORY.split(Costant.STRING_SEPARATOR).length + 1;

        PrefSubRedditEntry p;

        for (int i = 0; i < t5Size; i++) {

            T5Data t5Model = mModelT5.getData().getChildren().get(i).getData();

            p = new PrefSubRedditEntry();

            p.setName(TextUtil.normalizeSubRedditLink(t5Model.getUrl()));

            p.setImage(t5Model.getIconImg());

            p.setTimeLastModified(System.currentTimeMillis());

            p.setVisible(Costant.DEFAULT_SUBREDDIT_VISIBLE);

            p.setPosition(position + i);

            p.setBackupPosition(position + i);

            PrefSubRedditEntry finalP = p;
            AppExecutors.getInstance().diskIO().execute(() -> mDb.prefSubRedditDao().insertRecord(finalP));
        }

    }

    private void insertData() {

        if (mModelT5 == null) return;

        // subReddit
        RedditEntry reddit = new RedditEntry();
        reddit.setKind(mModelT5.getKind());
        reddit.setData(1);


        // data
        T5Listing dataModel = mModelT5.getData();
        DataEntry data = new DataEntry();
        data.setAfter(dataModel.getAfter());
        data.setDist(dataModel.getDist());
        data.setModHash(dataModel.getModhash());


        if (dataModel.getWhitelistStatus() == null) {
            data.setWhitelistStatus("0");
        } else {
            data.setWhitelistStatus(dataModel.getWhitelistStatus());
        }


        if (dataModel.getBefore() == null) {
            data.setBefore("0");
        } else {
            data.setBefore(String.valueOf(dataModel.getBefore()));
        }

        int childrenId;

        int t5Size = mModelT5.getData().getChildren().size();

        T5Entry t5;

        for (int i = 0; i < t5Size; i++) {

            childrenId = i + 1;

            data.setChildrens(i + 1);

            T5Data t5Model = mModelT5.getData().getChildren().get(i).getData();

            t5 = new T5Entry();

            t5.setSuggestCommentSort(String.valueOf(t5Model.getSuggestedCommentSort()));

            t5.setHideAds(Utility.boolToInt(t5Model.getHideAds()));

            t5.setBannerImg(t5Model.getBannerImg());

            t5.setUserSrThemeEnabled(Utility.boolToInt(t5Model.getUserSrThemeEnabled()));

            t5.setUserFlairText(String.valueOf(t5Model.getUserFlairText()));

            t5.setSubmitTextHtml(t5Model.getSubmitTextHtml());

            t5.setUserIsBanned(Utility.toInt(t5Model.getUserIsBanned()));

            t5.setWikiEnabled(Utility.boolToInt(t5Model.getWikiEnabled()));

            t5.setShowMedia(Utility.boolToInt(t5Model.getShowMedia()));

            t5.setNameId(t5Model.getId());

            t5.setChildrenId(childrenId);

            t5.setDisplayNamePrefixed(t5Model.getDisplayNamePrefixed());

            t5.setHeaderImg(t5Model.getHeaderImg());

            t5.setDescriptionHtml(t5Model.getDescriptionHtml());

            t5.setTitle(t5Model.getTitle());

            t5.setCollapseDeletedComments(Utility.boolToInt(t5Model.getCollapseDeletedComments()));

            t5.setUserHasFavorited(String.valueOf(t5Model.getUserHasFavorited()));

            t5.setOver18(Utility.boolToInt(t5Model.getOver18()));

            t5.setPublicDescriptionHtml(t5Model.getPublicDescriptionHtml());

            t5.setAllowVideos(Utility.boolToInt(t5Model.getAllowVideos()));

            t5.setSpoilersEnabled(Utility.boolToInt(t5Model.getSpoilersEnabled()));

            t5.setAudienceTarget(t5Model.getAudienceTarget());

            t5.setNotificationLevel(String.valueOf(t5Model.getNotificationLevel()));

            t5.setActiveUserCount(String.valueOf(t5Model.getActiveUserCount()));

            t5.setIconImg(t5Model.getIconImg());

            t5.setHeaderTitle(t5Model.getHeaderTitle());

            t5.setDescription(t5Model.getDescription());

            t5.setUserIsMuted(Utility.toInt(t5Model.getUserIsMuted()));

            t5.setSubmitLinkLabel(String.valueOf(t5Model.getSubmitLinkLabel()));

            t5.setAccountsActive(String.valueOf(t5Model.getAccountsActive()));

            t5.setPublicTraffic(Utility.boolToInt(t5Model.getPublicTraffic()));

            t5.setSubscribers(t5Model.getSubscribers());

            t5.setUserFlairCssClass(String.valueOf(t5Model.getUserFlairCssClass()));

            t5.setSubmitTextLabel(t5Model.getSubmitTextLabel());

            t5.setWhitelistStatus(t5Model.getWhitelistStatus());

            t5.setUserSrFlairEnabled(String.valueOf(t5Model.getUserSrFlairEnabled()));

            t5.setLang(t5Model.getLang());

            t5.setUserIsModerator(String.valueOf(t5Model.getUserIsModerator()));

            t5.setIsEnrolledInNewModmail(String.valueOf(t5Model.getIsEnrolledInNewModmail()));

            t5.setKeyColor(t5Model.getKeyColor());

            t5.setName(t5Model.getName());

            t5.setUserFlairEnabledInSr(Utility.boolToInt(t5Model.getUserFlairEnabledInSr()));

            t5.setAllowVideoGifs(Utility.boolToInt(t5Model.getAllowVideogifs()));

            t5.setUrl(t5Model.getUrl());

            t5.setQuarantine(Utility.boolToInt(t5Model.getQuarantine()));

            t5.setCreated(t5Model.getCreated());

//            t5.setCreatedUtc(t5Model.getCreatedUtc());

            t5.setUserIsContributor(String.valueOf(t5Model.getUserIsContributor()));

            t5.setAllowDiscovery(Utility.boolToInt(t5Model.getAllowDiscovery()));

            t5.setAccountsActiveIsFuzzed(Utility.boolToInt(t5Model.getAccountsActiveIsFuzzed()));

            t5.setAdvertiserCategory(t5Model.getAdvertiserCategory());

            t5.setPublicDescription(t5Model.getPublicDescription());

            t5.setLinkFlairEnabled(Utility.boolToInt(t5Model.getLinkFlairEnabled()));

            t5.setAllowImages(Utility.boolToInt(t5Model.getAllowImages()));

            t5.setShowMediaPreview(Utility.boolToInt(t5Model.getShowMediaPreview()));

            t5.setCommentScoreHideMins(t5Model.getCommentScoreHideMins());

            t5.setSubredditType(t5Model.getSubredditType());

            t5.setSubmissionType(t5Model.getSubmissionType());

            t5.setSortBy(Preference.getSubredditSort(mContext));

            t5.setUserIsSubscriber(String.valueOf(t5Model.getUserIsSubscriber()));


            T5Entry finalT = t5;

            AppExecutors.getInstance().diskIO().execute(() -> {
                mDb.redditDao().insertRecord(reddit);
                mDb.dataDao().insertRecord(data);
                mDb.t5Dao().insertRecord(finalT);
            });

        }

    }

    public void saveData() {
        if (!Preference.isInsertPrefs(mContext)) {
            clearData();
        }

        insertData();
        insertDataPrefSubReddit();
        Preference.setInsertPrefs(mContext, true);
        insertDefaultSubreddit();

    }

    public void insertDefaultSubreddit() {
        PrefSubRedditEntry p;

        String[] arrCategory = Costant.DEFAULT_SUBREDDIT_CATEGORY.split(Costant.STRING_SEPARATOR);
        String[] arrIcon = Costant.DEFAULT_SUBREDDIT_ICON.split(Costant.STRING_SEPARATOR);
        int size = arrCategory.length;

        for (int i = 0; i < size; i++) {
            p = new PrefSubRedditEntry();
            p.setPosition(1 + i);
            p.setBackupPosition(1 + i);
            p.setName(arrCategory[i]);
            p.setImage(arrIcon[i]);
            p.setVisible(Costant.DEFAULT_SUBREDDIT_VISIBLE);

            PrefSubRedditEntry finalP = p;
            AppExecutors.getInstance().diskIO().execute(() -> mDb.prefSubRedditDao().insertRecord(finalP));
        }

    }

    public void clearData() {
        RedditEntry reddit = new RedditEntry();
        DataEntry dataEntry = new DataEntry();
        T5Entry t5Entry = new T5Entry();

        AppExecutors.getInstance().diskIO().execute(() -> {
            mDb.redditDao().deleteRecord(reddit);
            mDb.dataDao().deleteRecord(dataEntry);
            mDb.t5Dao().deleteRecord(t5Entry);
        });
    }

}
