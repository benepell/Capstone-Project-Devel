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

package info.pelleritoudacity.android.rcapstone.data.model.reddit;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class T5Data implements Parcelable
{

    @SerializedName("suggested_comment_sort")
    @Expose
    private Object suggestedCommentSort;
    @SerializedName("hide_ads")
    @Expose
    private Boolean hideAds;
    @SerializedName("banner_img")
    @Expose
    private String bannerImg;
    @SerializedName("user_sr_theme_enabled")
    @Expose
    private Boolean userSrThemeEnabled;
    @SerializedName("user_flair_text")
    @Expose
    private Object userFlairText;
    @SerializedName("submit_text_html")
    @Expose
    private String submitTextHtml;
    @SerializedName("user_is_banned")
    @Expose
    private Object userIsBanned;
    @SerializedName("wiki_enabled")
    @Expose
    private Boolean wikiEnabled;
    @SerializedName("show_media")
    @Expose
    private Boolean showMedia;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("display_name_prefixed")
    @Expose
    private String displayNamePrefixed;
    @SerializedName("submit_text")
    @Expose
    private String submitText;
    @SerializedName("user_can_flair_in_sr")
    @Expose
    private Object userCanFlairInSr;
    @SerializedName("display_name")
    @Expose
    private String displayName;
    @SerializedName("header_img")
    @Expose
    private String headerImg;
    @SerializedName("description_html")
    @Expose
    private String descriptionHtml;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("collapse_deleted_comments")
    @Expose
    private Boolean collapseDeletedComments;
    @SerializedName("user_has_favorited")
    @Expose
    private Object userHasFavorited;
    @SerializedName("over18")
    @Expose
    private Boolean over18;
    @SerializedName("public_description_html")
    @Expose
    private String publicDescriptionHtml;
    @SerializedName("allow_videos")
    @Expose
    private Boolean allowVideos;
    @SerializedName("spoilers_enabled")
    @Expose
    private Boolean spoilersEnabled;
    @SerializedName("icon_size")
    @Expose
    private List<Integer> iconSize = null;
    @SerializedName("audience_target")
    @Expose
    private String audienceTarget;
    @SerializedName("notification_level")
    @Expose
    private Object notificationLevel;
    @SerializedName("active_user_count")
    @Expose
    private Object activeUserCount;
    @SerializedName("icon_img")
    @Expose
    private String iconImg;
    @SerializedName("header_title")
    @Expose
    private String headerTitle;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("user_is_muted")
    @Expose
    private Object userIsMuted;
    @SerializedName("submit_link_label")
    @Expose
    private Object submitLinkLabel;
    @SerializedName("accounts_active")
    @Expose
    private Object accountsActive;
    @SerializedName("public_traffic")
    @Expose
    private Boolean publicTraffic;
    @SerializedName("header_size")
    @Expose
    private List<Integer> headerSize = null;
    @SerializedName("subscribers")
    @Expose
    private Integer subscribers;
    @SerializedName("user_flair_css_class")
    @Expose
    private Object userFlairCssClass;
    @SerializedName("submit_text_label")
    @Expose
    private String submitTextLabel;
    @SerializedName("whitelist_status")
    @Expose
    private String whitelistStatus;
    @SerializedName("user_sr_flair_enabled")
    @Expose
    private Object userSrFlairEnabled;
    @SerializedName("lang")
    @Expose
    private String lang;
    @SerializedName("user_is_moderator")
    @Expose
    private Object userIsModerator;
    @SerializedName("is_enrolled_in_new_modmail")
    @Expose
    private Object isEnrolledInNewModmail;
    @SerializedName("key_color")
    @Expose
    private String keyColor;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("user_flair_enabled_in_sr")
    @Expose
    private Boolean userFlairEnabledInSr;
    @SerializedName("allow_videogifs")
    @Expose
    private Boolean allowVideogifs;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("quarantine")
    @Expose
    private Boolean quarantine;
    @SerializedName("created")
    @Expose
    private Double created;
    @SerializedName("created_utc")
    @Expose
    private Double createdUtc;
    @SerializedName("banner_size")
    @Expose
    private List<Integer> bannerSize = null;
    @SerializedName("user_is_contributor")
    @Expose
    private Object userIsContributor;
    @SerializedName("allow_discovery")
    @Expose
    private Boolean allowDiscovery;
    @SerializedName("accounts_active_is_fuzzed")
    @Expose
    private Boolean accountsActiveIsFuzzed;
    @SerializedName("advertiser_category")
    @Expose
    private String advertiserCategory;
    @SerializedName("public_description")
    @Expose
    private String publicDescription;
    @SerializedName("link_flair_enabled")
    @Expose
    private Boolean linkFlairEnabled;
    @SerializedName("allow_images")
    @Expose
    private Boolean allowImages;
    @SerializedName("show_media_preview")
    @Expose
    private Boolean showMediaPreview;
    @SerializedName("comment_score_hide_mins")
    @Expose
    private Integer commentScoreHideMins;
    @SerializedName("subreddit_type")
    @Expose
    private String subredditType;
    @SerializedName("submission_type")
    @Expose
    private String submissionType;
    @SerializedName("user_is_subscriber")
    @Expose
    private Object userIsSubscriber;
    public final static Parcelable.Creator<T5Data> CREATOR = new Creator<T5Data>() {


        @SuppressWarnings({
            "unchecked"
        })
        public T5Data createFromParcel(Parcel in) {
            return new T5Data(in);
        }

        public T5Data[] newArray(int size) {
            return (new T5Data[size]);
        }

    }
    ;

    @SuppressWarnings("WeakerAccess")
    protected T5Data(Parcel in) {
        this.suggestedCommentSort = in.readValue((Object.class.getClassLoader()));
        this.hideAds = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.bannerImg = ((String) in.readValue((String.class.getClassLoader())));
        this.userSrThemeEnabled = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.userFlairText = in.readValue((Object.class.getClassLoader()));
        this.submitTextHtml = ((String) in.readValue((String.class.getClassLoader())));
        this.userIsBanned = in.readValue((Object.class.getClassLoader()));
        this.wikiEnabled = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.showMedia = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.id = ((String) in.readValue((String.class.getClassLoader())));
        this.displayNamePrefixed = ((String) in.readValue((String.class.getClassLoader())));
        this.submitText = ((String) in.readValue((String.class.getClassLoader())));
        this.userCanFlairInSr = in.readValue((Object.class.getClassLoader()));
        this.displayName = ((String) in.readValue((String.class.getClassLoader())));
        this.headerImg = ((String) in.readValue((String.class.getClassLoader())));
        this.descriptionHtml = ((String) in.readValue((String.class.getClassLoader())));
        this.title = ((String) in.readValue((String.class.getClassLoader())));
        this.collapseDeletedComments = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.userHasFavorited = in.readValue((Object.class.getClassLoader()));
        this.over18 = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.publicDescriptionHtml = ((String) in.readValue((String.class.getClassLoader())));
        this.allowVideos = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.spoilersEnabled = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        in.readList(this.iconSize, (java.lang.Integer.class.getClassLoader()));
        this.audienceTarget = ((String) in.readValue((String.class.getClassLoader())));
        this.notificationLevel = in.readValue((Object.class.getClassLoader()));
        this.activeUserCount = in.readValue((Object.class.getClassLoader()));
        this.iconImg = ((String) in.readValue((String.class.getClassLoader())));
        this.headerTitle = ((String) in.readValue((String.class.getClassLoader())));
        this.description = ((String) in.readValue((String.class.getClassLoader())));
        this.userIsMuted = in.readValue((Object.class.getClassLoader()));
        this.submitLinkLabel = in.readValue((Object.class.getClassLoader()));
        this.accountsActive = in.readValue((Object.class.getClassLoader()));
        this.publicTraffic = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        in.readList(this.headerSize, (java.lang.Integer.class.getClassLoader()));
        this.subscribers = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.userFlairCssClass = in.readValue((Object.class.getClassLoader()));
        this.submitTextLabel = ((String) in.readValue((String.class.getClassLoader())));
        this.whitelistStatus = ((String) in.readValue((String.class.getClassLoader())));
        this.userSrFlairEnabled = in.readValue((Object.class.getClassLoader()));
        this.lang = ((String) in.readValue((String.class.getClassLoader())));
        this.userIsModerator = in.readValue((Object.class.getClassLoader()));
        this.isEnrolledInNewModmail = in.readValue((Object.class.getClassLoader()));
        this.keyColor = ((String) in.readValue((String.class.getClassLoader())));
        this.name = ((String) in.readValue((String.class.getClassLoader())));
        this.userFlairEnabledInSr = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.allowVideogifs = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.url = ((String) in.readValue((String.class.getClassLoader())));
        this.quarantine = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.created = ((Double) in.readValue((Double.class.getClassLoader())));
        this.createdUtc = ((Double) in.readValue((Double.class.getClassLoader())));
        in.readList(this.bannerSize, (java.lang.Integer.class.getClassLoader()));
        this.userIsContributor = in.readValue((Object.class.getClassLoader()));
        this.allowDiscovery = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.accountsActiveIsFuzzed = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.advertiserCategory = ((String) in.readValue((String.class.getClassLoader())));
        this.publicDescription = ((String) in.readValue((String.class.getClassLoader())));
        this.linkFlairEnabled = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.allowImages = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.showMediaPreview = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.commentScoreHideMins = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.subredditType = ((String) in.readValue((String.class.getClassLoader())));
        this.submissionType = ((String) in.readValue((String.class.getClassLoader())));
        this.userIsSubscriber = in.readValue((Object.class.getClassLoader()));
    }

    public T5Data() {
    }

    public Object getSuggestedCommentSort() {
        return suggestedCommentSort;
    }

    @SuppressWarnings("unused")
    public void setSuggestedCommentSort(Object suggestedCommentSort) {
        this.suggestedCommentSort = suggestedCommentSort;
    }

    public Boolean getHideAds() {
        return hideAds;
    }

    @SuppressWarnings("unused")
    public void setHideAds(Boolean hideAds) {
        this.hideAds = hideAds;
    }

    public String getBannerImg() {
        return bannerImg;
    }

    @SuppressWarnings("unused")
    public void setBannerImg(String bannerImg) {
        this.bannerImg = bannerImg;
    }

    public Boolean getUserSrThemeEnabled() {
        return userSrThemeEnabled;
    }

    @SuppressWarnings("unused")
    public void setUserSrThemeEnabled(Boolean userSrThemeEnabled) {
        this.userSrThemeEnabled = userSrThemeEnabled;
    }

    public Object getUserFlairText() {
        return userFlairText;
    }

    @SuppressWarnings("unused")
    public void setUserFlairText(Object userFlairText) {
        this.userFlairText = userFlairText;
    }

    public String getSubmitTextHtml() {
        return submitTextHtml;
    }

    @SuppressWarnings("unused")
    public void setSubmitTextHtml(String submitTextHtml) {
        this.submitTextHtml = submitTextHtml;
    }

    public Object getUserIsBanned() {
        return userIsBanned;
    }

    @SuppressWarnings("unused")
    public void setUserIsBanned(Object userIsBanned) {
        this.userIsBanned = userIsBanned;
    }

    public Boolean getWikiEnabled() {
        return wikiEnabled;
    }

    @SuppressWarnings("unused")
    public void setWikiEnabled(Boolean wikiEnabled) {
        this.wikiEnabled = wikiEnabled;
    }

    public Boolean getShowMedia() {
        return showMedia;
    }

    @SuppressWarnings("unused")
    public void setShowMedia(Boolean showMedia) {
        this.showMedia = showMedia;
    }

    public String getId() {
        return id;
    }

    @SuppressWarnings("unused")
    public void setId(String id) {
        this.id = id;
    }

    public String getDisplayNamePrefixed() {
        return displayNamePrefixed;
    }

    @SuppressWarnings("unused")
    public void setDisplayNamePrefixed(String displayNamePrefixed) {
        this.displayNamePrefixed = displayNamePrefixed;
    }

    @SuppressWarnings("unused")
    public String getSubmitText() {
        return submitText;
    }

    @SuppressWarnings("unused")
    public void setSubmitText(String submitText) {
        this.submitText = submitText;
    }

    @SuppressWarnings("unused")
    public Object getUserCanFlairInSr() {
        return userCanFlairInSr;
    }

    @SuppressWarnings("unused")
    public void setUserCanFlairInSr(Object userCanFlairInSr) {
        this.userCanFlairInSr = userCanFlairInSr;
    }

    @SuppressWarnings("unused")
    public String getDisplayName() {
        return displayName;
    }

    @SuppressWarnings("unused")
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getHeaderImg() {
        return headerImg;
    }

    @SuppressWarnings("unused")
    public void setHeaderImg(String headerImg) {
        this.headerImg = headerImg;
    }

    public String getDescriptionHtml() {
        return descriptionHtml;
    }

    @SuppressWarnings("unused")
    public void setDescriptionHtml(String descriptionHtml) {
        this.descriptionHtml = descriptionHtml;
    }

    public String getTitle() {
        return title;
    }

    @SuppressWarnings("unused")
    public void setTitle(String title) {
        this.title = title;
    }

    public Boolean getCollapseDeletedComments() {
        return collapseDeletedComments;
    }

    @SuppressWarnings("unused")
    public void setCollapseDeletedComments(Boolean collapseDeletedComments) {
        this.collapseDeletedComments = collapseDeletedComments;
    }

    public Object getUserHasFavorited() {
        return userHasFavorited;
    }

    @SuppressWarnings("unused")
    public void setUserHasFavorited(Object userHasFavorited) {
        this.userHasFavorited = userHasFavorited;
    }

    public Boolean getOver18() {
        return over18;
    }

    @SuppressWarnings("unused")
    public void setOver18(Boolean over18) {
        this.over18 = over18;
    }

    public String getPublicDescriptionHtml() {
        return publicDescriptionHtml;
    }

    @SuppressWarnings("unused")
    public void setPublicDescriptionHtml(String publicDescriptionHtml) {
        this.publicDescriptionHtml = publicDescriptionHtml;
    }

    public Boolean getAllowVideos() {
        return allowVideos;
    }

    @SuppressWarnings("unused")
    public void setAllowVideos(Boolean allowVideos) {
        this.allowVideos = allowVideos;
    }

    public Boolean getSpoilersEnabled() {
        return spoilersEnabled;
    }

    @SuppressWarnings("unused")
    public void setSpoilersEnabled(Boolean spoilersEnabled) {
        this.spoilersEnabled = spoilersEnabled;
    }

    @SuppressWarnings("unused")
    public List<Integer> getIconSize() {
        return iconSize;
    }

    @SuppressWarnings("unused")
    public void setIconSize(List<Integer> iconSize) {
        this.iconSize = iconSize;
    }

    public String getAudienceTarget() {
        return audienceTarget;
    }

    @SuppressWarnings("unused")
    public void setAudienceTarget(String audienceTarget) {
        this.audienceTarget = audienceTarget;
    }

    public Object getNotificationLevel() {
        return notificationLevel;
    }

    @SuppressWarnings("unused")
    public void setNotificationLevel(Object notificationLevel) {
        this.notificationLevel = notificationLevel;
    }

    public Object getActiveUserCount() {
        return activeUserCount;
    }

    @SuppressWarnings("unused")
    public void setActiveUserCount(Object activeUserCount) {
        this.activeUserCount = activeUserCount;
    }

    public String getIconImg() {
        return iconImg;
    }

    @SuppressWarnings("unused")
    public void setIconImg(String iconImg) {
        this.iconImg = iconImg;
    }

    public String getHeaderTitle() {
        return headerTitle;
    }

    @SuppressWarnings("unused")
    public void setHeaderTitle(String headerTitle) {
        this.headerTitle = headerTitle;
    }

    public String getDescription() {
        return description;
    }

    @SuppressWarnings("unused")
    public void setDescription(String description) {
        this.description = description;
    }

    public Object getUserIsMuted() {
        return userIsMuted;
    }

    @SuppressWarnings("unused")
    public void setUserIsMuted(Object userIsMuted) {
        this.userIsMuted = userIsMuted;
    }

    public Object getSubmitLinkLabel() {
        return submitLinkLabel;
    }

    @SuppressWarnings("unused")
    public void setSubmitLinkLabel(Object submitLinkLabel) {
        this.submitLinkLabel = submitLinkLabel;
    }

    public Object getAccountsActive() {
        return accountsActive;
    }

    @SuppressWarnings("unused")
    public void setAccountsActive(Object accountsActive) {
        this.accountsActive = accountsActive;
    }

    public Boolean getPublicTraffic() {
        return publicTraffic;
    }

    @SuppressWarnings("unused")
    public void setPublicTraffic(Boolean publicTraffic) {
        this.publicTraffic = publicTraffic;
    }

    @SuppressWarnings("unused")
    public List<Integer> getHeaderSize() {
        return headerSize;
    }

    @SuppressWarnings("unused")
    public void setHeaderSize(List<Integer> headerSize) {
        this.headerSize = headerSize;
    }

    public Integer getSubscribers() {
        return subscribers;
    }

    @SuppressWarnings("unused")
    public void setSubscribers(Integer subscribers) {
        this.subscribers = subscribers;
    }

    public Object getUserFlairCssClass() {
        return userFlairCssClass;
    }

    @SuppressWarnings("unused")
    public void setUserFlairCssClass(Object userFlairCssClass) {
        this.userFlairCssClass = userFlairCssClass;
    }

    public String getSubmitTextLabel() {
        return submitTextLabel;
    }

    @SuppressWarnings("unused")
    public void setSubmitTextLabel(String submitTextLabel) {
        this.submitTextLabel = submitTextLabel;
    }

    public String getWhitelistStatus() {
        return whitelistStatus;
    }

    @SuppressWarnings("unused")
    public void setWhitelistStatus(String whitelistStatus) {
        this.whitelistStatus = whitelistStatus;
    }

    public Object getUserSrFlairEnabled() {
        return userSrFlairEnabled;
    }

    @SuppressWarnings("unused")
    public void setUserSrFlairEnabled(Object userSrFlairEnabled) {
        this.userSrFlairEnabled = userSrFlairEnabled;
    }

    public String getLang() {
        return lang;
    }

    @SuppressWarnings("unused")
    public void setLang(String lang) {
        this.lang = lang;
    }

    public Object getUserIsModerator() {
        return userIsModerator;
    }

    @SuppressWarnings("unused")
    public void setUserIsModerator(Object userIsModerator) {
        this.userIsModerator = userIsModerator;
    }

    public Object getIsEnrolledInNewModmail() {
        return isEnrolledInNewModmail;
    }

    @SuppressWarnings("unused")
    public void setIsEnrolledInNewModmail(Object isEnrolledInNewModmail) {
        this.isEnrolledInNewModmail = isEnrolledInNewModmail;
    }

    public String getKeyColor() {
        return keyColor;
    }

    @SuppressWarnings("unused")
    public void setKeyColor(String keyColor) {
        this.keyColor = keyColor;
    }

    public String getName() {
        return name;
    }

    @SuppressWarnings("unused")
    public void setName(String name) {
        this.name = name;
    }

    public Boolean getUserFlairEnabledInSr() {
        return userFlairEnabledInSr;
    }

    @SuppressWarnings("unused")
    public void setUserFlairEnabledInSr(Boolean userFlairEnabledInSr) {
        this.userFlairEnabledInSr = userFlairEnabledInSr;
    }

    public Boolean getAllowVideogifs() {
        return allowVideogifs;
    }

    @SuppressWarnings("unused")
    public void setAllowVideogifs(Boolean allowVideogifs) {
        this.allowVideogifs = allowVideogifs;
    }

    public String getUrl() {
        return url;
    }

    @SuppressWarnings("unused")
    public void setUrl(String url) {
        this.url = url;
    }

    public Boolean getQuarantine() {
        return quarantine;
    }

    @SuppressWarnings("unused")
    public void setQuarantine(Boolean quarantine) {
        this.quarantine = quarantine;
    }

    public Double getCreated() {
        return created;
    }

    @SuppressWarnings("unused")
    public void setCreated(Double created) {
        this.created = created;
    }

    public Double getCreatedUtc() {
        return createdUtc;
    }

    @SuppressWarnings("unused")
    public void setCreatedUtc(Double createdUtc) {
        this.createdUtc = createdUtc;
    }

    @SuppressWarnings("unused")
    public List<Integer> getBannerSize() {
        return bannerSize;
    }

    @SuppressWarnings("unused")
    public void setBannerSize(List<Integer> bannerSize) {
        this.bannerSize = bannerSize;
    }

    public Object getUserIsContributor() {
        return userIsContributor;
    }

    @SuppressWarnings("unused")
    public void setUserIsContributor(Object userIsContributor) {
        this.userIsContributor = userIsContributor;
    }

    public Boolean getAllowDiscovery() {
        return allowDiscovery;
    }

    @SuppressWarnings("unused")
    public void setAllowDiscovery(Boolean allowDiscovery) {
        this.allowDiscovery = allowDiscovery;
    }

    public Boolean getAccountsActiveIsFuzzed() {
        return accountsActiveIsFuzzed;
    }

    @SuppressWarnings("unused")
    public void setAccountsActiveIsFuzzed(Boolean accountsActiveIsFuzzed) {
        this.accountsActiveIsFuzzed = accountsActiveIsFuzzed;
    }

    public String getAdvertiserCategory() {
        return advertiserCategory;
    }

    @SuppressWarnings("unused")
    public void setAdvertiserCategory(String advertiserCategory) {
        this.advertiserCategory = advertiserCategory;
    }

    public String getPublicDescription() {
        return publicDescription;
    }

    @SuppressWarnings("unused")
    public void setPublicDescription(String publicDescription) {
        this.publicDescription = publicDescription;
    }

    public Boolean getLinkFlairEnabled() {
        return linkFlairEnabled;
    }

    @SuppressWarnings("unused")
    public void setLinkFlairEnabled(Boolean linkFlairEnabled) {
        this.linkFlairEnabled = linkFlairEnabled;
    }

    public Boolean getAllowImages() {
        return allowImages;
    }

    @SuppressWarnings("unused")
    public void setAllowImages(Boolean allowImages) {
        this.allowImages = allowImages;
    }

    public Boolean getShowMediaPreview() {
        return showMediaPreview;
    }

    @SuppressWarnings("unused")
    public void setShowMediaPreview(Boolean showMediaPreview) {
        this.showMediaPreview = showMediaPreview;
    }

    public Integer getCommentScoreHideMins() {
        return commentScoreHideMins;
    }

    @SuppressWarnings("unused")
    public void setCommentScoreHideMins(Integer commentScoreHideMins) {
        this.commentScoreHideMins = commentScoreHideMins;
    }

    public String getSubredditType() {
        return subredditType;
    }

    @SuppressWarnings("unused")
    public void setSubredditType(String subredditType) {
        this.subredditType = subredditType;
    }

    public String getSubmissionType() {
        return submissionType;
    }

    @SuppressWarnings("unused")
    public void setSubmissionType(String submissionType) {
        this.submissionType = submissionType;
    }

    public Object getUserIsSubscriber() {
        return userIsSubscriber;
    }

    @SuppressWarnings("unused")
    public void setUserIsSubscriber(Object userIsSubscriber) {
        this.userIsSubscriber = userIsSubscriber;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(suggestedCommentSort);
        dest.writeValue(hideAds);
        dest.writeValue(bannerImg);
        dest.writeValue(userSrThemeEnabled);
        dest.writeValue(userFlairText);
        dest.writeValue(submitTextHtml);
        dest.writeValue(userIsBanned);
        dest.writeValue(wikiEnabled);
        dest.writeValue(showMedia);
        dest.writeValue(id);
        dest.writeValue(displayNamePrefixed);
        dest.writeValue(submitText);
        dest.writeValue(userCanFlairInSr);
        dest.writeValue(displayName);
        dest.writeValue(headerImg);
        dest.writeValue(descriptionHtml);
        dest.writeValue(title);
        dest.writeValue(collapseDeletedComments);
        dest.writeValue(userHasFavorited);
        dest.writeValue(over18);
        dest.writeValue(publicDescriptionHtml);
        dest.writeValue(allowVideos);
        dest.writeValue(spoilersEnabled);
        dest.writeList(iconSize);
        dest.writeValue(audienceTarget);
        dest.writeValue(notificationLevel);
        dest.writeValue(activeUserCount);
        dest.writeValue(iconImg);
        dest.writeValue(headerTitle);
        dest.writeValue(description);
        dest.writeValue(userIsMuted);
        dest.writeValue(submitLinkLabel);
        dest.writeValue(accountsActive);
        dest.writeValue(publicTraffic);
        dest.writeList(headerSize);
        dest.writeValue(subscribers);
        dest.writeValue(userFlairCssClass);
        dest.writeValue(submitTextLabel);
        dest.writeValue(whitelistStatus);
        dest.writeValue(userSrFlairEnabled);
        dest.writeValue(lang);
        dest.writeValue(userIsModerator);
        dest.writeValue(isEnrolledInNewModmail);
        dest.writeValue(keyColor);
        dest.writeValue(name);
        dest.writeValue(userFlairEnabledInSr);
        dest.writeValue(allowVideogifs);
        dest.writeValue(url);
        dest.writeValue(quarantine);
        dest.writeValue(created);
        dest.writeValue(createdUtc);
        dest.writeList(bannerSize);
        dest.writeValue(userIsContributor);
        dest.writeValue(allowDiscovery);
        dest.writeValue(accountsActiveIsFuzzed);
        dest.writeValue(advertiserCategory);
        dest.writeValue(publicDescription);
        dest.writeValue(linkFlairEnabled);
        dest.writeValue(allowImages);
        dest.writeValue(showMediaPreview);
        dest.writeValue(commentScoreHideMins);
        dest.writeValue(subredditType);
        dest.writeValue(submissionType);
        dest.writeValue(userIsSubscriber);
    }

    public int describeContents() {
        return  0;
    }

}
