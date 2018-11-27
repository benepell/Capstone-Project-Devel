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
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.Date;

import info.pelleritoudacity.android.rcapstone.data.db.AppDatabase;
import info.pelleritoudacity.android.rcapstone.data.db.AppExecutors;
import info.pelleritoudacity.android.rcapstone.data.db.entry.DataEntry;
import info.pelleritoudacity.android.rcapstone.data.db.entry.PrefSubRedditEntry;
import info.pelleritoudacity.android.rcapstone.data.db.entry.RedditEntry;
import info.pelleritoudacity.android.rcapstone.data.db.entry.T3Entry;
import info.pelleritoudacity.android.rcapstone.data.model.ModelContent;
import info.pelleritoudacity.android.rcapstone.data.model.reddit.Media;
import info.pelleritoudacity.android.rcapstone.data.model.reddit.OembedMedia;
import info.pelleritoudacity.android.rcapstone.data.model.reddit.RedditVideoPreview;
import info.pelleritoudacity.android.rcapstone.data.model.reddit.T3;
import info.pelleritoudacity.android.rcapstone.data.model.reddit.T3Data;
import info.pelleritoudacity.android.rcapstone.data.model.reddit.T3ListingData;
import info.pelleritoudacity.android.rcapstone.data.model.reddit.Variants;
import info.pelleritoudacity.android.rcapstone.utility.Costant;
import info.pelleritoudacity.android.rcapstone.utility.Preference;
import info.pelleritoudacity.android.rcapstone.utility.TextUtil;
import info.pelleritoudacity.android.rcapstone.utility.Utility;

public class T3Operation {

    private final Context mContext;
    private final AppDatabase mDb;
    private final T3 mModelT3;

    public T3Operation(Context context, AppDatabase db, T3 modelT3) {
        mContext = context;
        mDb = db;
        mModelT3 = modelT3;
    }

    private void insertData(String target) {

        if (mModelT3 == null) return;

        // subReddit
        RedditEntry reddit = new RedditEntry();
        reddit.setKind(mModelT3.getKind());
        reddit.setData(1);


        // data
        T3Data dataModel = mModelT3.getData();
        DataEntry data = new DataEntry();
        data.setAfter(dataModel.getAfter());
        data.setDist(dataModel.getDist());
        data.setModHash(dataModel.getModhash());


        if (dataModel.getBefore() == null) {
            data.setBefore("0");
        } else {
            data.setBefore(String.valueOf(dataModel.getBefore()));
        }

        int t3Size = mModelT3.getData().getChildren().size();

        ImageContentOperation imageContentOperation = new ImageContentOperation(mContext);

        VideoContentOperation videoContentOperation = new VideoContentOperation(mContext);

        T3Entry t3;

        for (int i = 0; i < t3Size; i++) {

            T3ListingData t3Model = mModelT3.getData().getChildren().get(i).getData();

            data.setChildrens(i + 1);

            t3 = new T3Entry();

            if (t3Model != null) {

                t3 = getInsert(t3Model, t3, target, i + 1);

                if (t3Model.getPreview() != null) {

                    ArrayList<ModelContent> modelContentImageArrayList = new ArrayList<>();

                    ArrayList<ModelContent> optimizeArrayList = imageContentOperation.showImageT3(t3Model);

                    if (optimizeArrayList != null) {
                        modelContentImageArrayList.add(optimizeArrayList.get(0));
                    }

                    String previewUrl = modelContentImageArrayList.get(0).getUrl();
                    int previewWidth = modelContentImageArrayList.get(0).getWidth();
                    int previewHeight = modelContentImageArrayList.get(0).getHeight();

                    t3.setPreviewImageSourceUrl(previewUrl);
                    t3.setPreviewImageSourceWidth(previewWidth);
                    t3.setPreviewImageSourceHeight(previewHeight);

                    RedditVideoPreview redditVideoPreview = t3Model.getPreview().getRedditVideoPreview();

                    t3 = getInsertVideoPreview(redditVideoPreview, t3);


                    Variants variants = t3Model.getPreview().getImages().get(0).getVariants();

                    if (variants != null) {
                        ArrayList<ModelContent> modelContentArrayList = new ArrayList<>();
                        ArrayList<ModelContent> arrayList = videoContentOperation.showVideoContent(t3Model);
                        if (arrayList != null) {
                            modelContentArrayList.add(arrayList.get(0));

                            String videoMp4Url = modelContentArrayList.get(0).getUrl();
                            int videoMp4Width = modelContentArrayList.get(0).getWidth();
                            int videoMp4Height = modelContentArrayList.get(0).getHeight();

                            t3.setVariantVideoMp4Url(videoMp4Url);
                            t3.setVariantVideoMp4Width(videoMp4Width);
                            t3.setVariantVideoMp4Height(videoMp4Height);

                        }
                    }

                    Media mediaModel = t3Model.getMedia();

                    if (mediaModel != null) {
                        t3.setMediaType(mediaModel.getType());
                        t3 = getInsertOembedMedia(mediaModel.getOembed(), t3);

                    }
                }
                T3Entry finalT = t3;

                AppExecutors.getInstance().diskIO().execute(() -> {
                    mDb.redditDao().insertRecord(reddit);
                    mDb.dataDao().insertRecord(data);
                    mDb.t3Dao().insertRecord(finalT);

                });
            }
        }
    }


    @SuppressWarnings("SpellCheckingInspection")
    private T3Entry getInsertOembedMedia(OembedMedia o, T3Entry t3) {

        if (o != null) {
            if (t3 == null) t3 = new T3Entry();

            t3.setMediaOembedProviderUrl(o.getProviderUrl());

            t3.setMediaOembedTitle(o.getTitle());

            t3.setMediaOembedType(o.getType());

            t3.setMediaOembedHtml(o.getHtml());

            t3.setMediaOembedAuthorName(o.getAuthorName());

            t3.setMediaOembedAuthorUrl(o.getAuthorUrl());

            t3.setMediaOembedWidth(o.getWidth());

            t3.setMediaOembedHeight(o.getHeight());

            t3.setMediaOembedThumbnailWidth(o.getThumbnailWidth());

            t3.setMediaOembedThumbnailHeight(o.getThumbnailHeight());

            t3.setMediaOembedThumbnailUrl(o.getThumbnailUrl());

            t3.setMediaOembedProviderVersion(o.getVersion());

        }
        return t3;
    }


    private T3Entry getInsertVideoPreview(RedditVideoPreview r, T3Entry t3) {

        if (r != null) {
            if (t3 == null) t3 = new T3Entry();

            t3.setPreviewVideoHlsUrl(r.getHlsUrl());
            t3.setPreviewVideoDashUrl(r.getDashUrl());
            t3.setPreviewVideoScrubberMediaUrl(r.getScrubberMediaUrl());
            t3.setPreviewVideoFallbackUrl(r.getFallbackUrl());
            t3.setPreviewVideoTransCodingStatus(r.getTranscodingStatus());
            t3.setPreviewVideoDuration(r.getDuration());
            t3.setPreviewVideoWidth(r.getWidth());
            t3.setPreviewVideoHeight(r.getHeight());
            t3.setPreviewVideoGif(Utility.boolToInt(r.getIsGif()));

        }
        return t3;

    }

    private T3Entry getInsert(T3ListingData t3ListingData, T3Entry t3,
                              String target, int childrenId) {

        if (t3ListingData != null) {

            if (t3 == null) t3 = new T3Entry();

            t3.setTimeLastModified(System.currentTimeMillis());

            t3.setCrosspostable(Utility.boolToInt(t3ListingData.getIsCrosspostable()));

            t3.setSubredditId(t3ListingData.getSubredditId());

            if(t3ListingData.getApprovedAtUtc()!=null) {
                t3.setApprovedAtUtc(t3ListingData.getApprovedAtUtc());
            }

            t3.setWls(t3ListingData.getWls());

            t3.setModReasonBy(String.valueOf(t3ListingData.getModReasonBy()));

            t3.setBannedBy(String.valueOf(t3ListingData.getBannedBy()));

            if (t3ListingData.getNumReports() != null) {
                t3.setNumReports(Integer.parseInt(String.valueOf(t3ListingData.getNumReports())));
            }
            if (t3ListingData.getRemovalReason() != null) {
                t3.setRemovalReason(Integer.parseInt(String.valueOf(t3ListingData.getRemovalReason())));
            }
            t3.setThumbnailWidth(String.valueOf(t3ListingData.getThumbnailWidth()));

            t3.setSubreddit(t3ListingData.getSubreddit());

            t3.setSelfTextHtml(String.valueOf(t3ListingData.getSelftextHtml()));

            t3.setSelfText(t3ListingData.getSelftext());

            t3.setLikes(String.valueOf(t3ListingData.getLikes()));

            t3.setSuggestedSort(String.valueOf(t3ListingData.getSuggestedSort()));

            t3.setRedditMainDomain(Utility.boolToInt(t3ListingData.getIsRedditMediaDomain()));

            t3.setSaved(Utility.boolToInt(t3ListingData.getSaved()));

            t3.setChildrenId(childrenId);

            t3.setNameId(t3ListingData.getId());

            if (t3ListingData.getBannedAtUtc() != null) {
                t3.setBannedAtUtc(new Date((long) t3ListingData.getBannedAtUtc()));
            }
            t3.setModReasonTitle(String.valueOf(t3ListingData.getModReasonTitle()));

            t3.setViewCount(String.valueOf(t3ListingData.getViewCount()));

            t3.setArchived(Utility.boolToInt(t3ListingData.getArchived()));

            t3.setClicked(Utility.boolToInt(t3ListingData.getClicked()));

            t3.setNoFollow(Utility.boolToInt(t3ListingData.getNoFollow()));

            t3.setAuthor(t3ListingData.getAuthor());

            t3.setNumCrossPost(t3ListingData.getNumCrossposts());

            t3.setLinkFlairText(String.valueOf(t3ListingData.getLinkFlairText()));

            t3.setCanModPost(Utility.boolToInt(t3ListingData.getCanModPost()));

            t3.setSendReplies(Utility.boolToInt(t3ListingData.getSendReplies()));

            t3.setPinned(Utility.boolToInt(t3ListingData.getPinned()));

            t3.setScore(t3ListingData.getScore());

            t3.setApprovedBy(String.valueOf(t3ListingData.getApprovedBy()));

            t3.setOver18(Utility.boolToInt(t3ListingData.getOver18()));
            if (t3ListingData.getReportReasons() != null) {
                t3.setReportReason(Integer.parseInt(String.valueOf(t3ListingData.getReportReasons())));
            }
            t3.setDomain(t3ListingData.getDomain());

            t3.setHidden(Utility.boolToInt(t3ListingData.getHidden()));

            t3.setPwls(t3ListingData.getPwls());

            t3.setThumbnail(t3ListingData.getThumbnail());

            t3.setEdited(Utility.toInt(t3ListingData.getEdited()));

            t3.setLinkFlairCssClass(String.valueOf(t3ListingData.getLinkFlairCssClass()));

            t3.setAuthorFlairCssClass(String.valueOf(t3ListingData.getAuthorFlairCssClass()));

            t3.setContestMode(Utility.boolToInt(t3ListingData.getContestMode()));

            t3.setGilded(t3ListingData.getGilded());

            t3.setLocked(Utility.boolToInt(t3ListingData.getLocked()));

            t3.setDowns(t3ListingData.getDowns());

            t3.setStickied(Utility.boolToInt(t3ListingData.getStickied()));

            t3.setVisited(Utility.boolToInt(t3ListingData.getVisited()));

            t3.setCanGild(Utility.boolToInt(t3ListingData.getCanGild()));

            t3.setThumbnailHeight(String.valueOf(t3ListingData.getThumbnailHeight()));

            t3.setSpoiler(Utility.boolToInt(t3ListingData.getSpoiler()));

            t3.setPermalink(t3ListingData.getPermalink());

            t3.setSubredditType(t3ListingData.getSubredditType());

            t3.setWhitelistStatus(t3ListingData.getParentWhitelistStatus());

            t3.setHideScore(Utility.boolToInt(t3ListingData.getHideScore()));

            t3.setUrl(t3ListingData.getUrl());

            t3.setCreated(t3ListingData.getCreated());

            t3.setAuthorFlairText(String.valueOf(t3ListingData.getAuthorFlairText()));

            t3.setQuarantine(Utility.boolToInt(t3ListingData.getQuarantine()));

            t3.setTitle(t3ListingData.getTitle());

            t3.setCreatedUtc(t3ListingData.getCreatedUtc());

            t3.setSubredditNamePrefix(t3ListingData.getSubredditNamePrefixed());

            t3.setUps(t3ListingData.getUps());

            t3.setNumComments(t3ListingData.getNumComments());

            t3.setIsSelf(Utility.boolToInt(t3ListingData.getIsSelf()));

            t3.setWhitelistStatus(t3ListingData.getWhitelistStatus());

            t3.setModNote(String.valueOf(t3ListingData.getModNote()));

            t3.setIsVideo(Utility.boolToInt(t3ListingData.getIsVideo()));

            t3.setDistinguished(String.valueOf(t3ListingData.getDistinguished()));

            String stringCategoryNormalizeSub = TextUtil.normalizeSubRedditLink(t3ListingData.getSubreddit());
            t3.setSubreddit(stringCategoryNormalizeSub);
            t3.setTarget(target);
            t3.setSortBy(Preference.getSubredditSort(mContext));

        }
        return t3;
    }

    public void saveData(String category, String target) {
        if (!TextUtils.isEmpty(category)) {
            deleteCategory(category);

            insertData(target);
        }
    }


    private void deleteCategory(String category) {

        switch (category) {

            case Costant.DB_TABLE_T3:

                AppExecutors.getInstance().diskIO().execute(() -> mDb.t3Dao().deleteRecordByCategory(category));
                break;

            case Costant.DB_TABLE_PREFSUBREDDIT:

                AppExecutors.getInstance().diskIO().execute(() -> mDb.prefSubRedditDao().deleteRecordByCategory(category));
                break;

        }

    }

    public void clearData() {
        T3Entry t3Entry = new T3Entry();
        PrefSubRedditEntry prefSubRedditEntry = new PrefSubRedditEntry();

        AppExecutors.getInstance().diskIO().execute(() -> {
            mDb.t3Dao().deleteRecord(t3Entry);
            mDb.prefSubRedditDao().deleteRecord(prefSubRedditEntry);
        });

    }

}
