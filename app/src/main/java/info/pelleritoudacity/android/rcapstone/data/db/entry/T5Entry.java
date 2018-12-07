package info.pelleritoudacity.android.rcapstone.data.db.entry;



import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;
import info.pelleritoudacity.android.rcapstone.data.db.util.DateConverter;

@SuppressWarnings("ALL")
@Entity(tableName = "_t5")
public class T5Entry {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @TypeConverters(DateConverter.class)
    @ColumnInfo(name = "_time_last_modified")
    private long timeLastModified;

    @ColumnInfo(name = "_suggest_comment_sort")
    private String suggestCommentSort;

    @ColumnInfo(name = "_hide_ads")
    private int hideAds;

    @ColumnInfo(name = "_banner_img")
    private String bannerImg;

    @ColumnInfo(name = "_user_sr_theme_enabled")
    private int userSrThemeEnabled;

    @ColumnInfo(name = "_user_flair_text")
    private String userFlairText;

    @ColumnInfo(name = "_submit_text_html")
    private String submitTextHtml;

    @ColumnInfo(name = "_user_is_banned")
    private int userIsBanned;

    @ColumnInfo(name = "_wiki_enabled")
    private int wikiEnabled;

    @ColumnInfo(name = "_show_media")
    private int showMedia;

    @ColumnInfo(name = "_name_id")
    private String nameId;

    @ColumnInfo(name = "_children_id")
    private int childrenId;

    @ColumnInfo(name = "_diplay_name_prefixed")
    private String displayNamePrefixed;

    @ColumnInfo(name = "_submit_text")
    private String submitText;

    @ColumnInfo(name = "_user_can_flair_in_sr")
    private String userCanFlairInSr;

    @ColumnInfo(name = "_display_name")
    private String displayName;

    @ColumnInfo(name = "_header_img")
    private String headerImg;

    @ColumnInfo(name = "_description_html")
    private String descriptionHtml;

    @ColumnInfo(name = "_title")
    private String title;

    @ColumnInfo(name = "_collapse_deleted_comments")
    private int collapseDeletedComments;

    @ColumnInfo(name = "_user_has_favorited")
    private String userHasFavorited;

    @ColumnInfo(name = "_over18")
    private int over18;

    @ColumnInfo(name = "_public_description_html")
    private String publicDescriptionHtml;

    @ColumnInfo(name = "_allow_videos")
    private int allowVideos;

    @ColumnInfo(name = "_spoilers_enabled")
    private int spoilersEnabled;

    @ColumnInfo(name = "_icon_size")
    private int iconSize;

    @ColumnInfo(name = "_audience_target")
    private String audienceTarget;

    @ColumnInfo(name = "_notification_level")
    private String notificationLevel;

    @ColumnInfo(name = "_active_user_count")
    private String activeUserCount;

    @ColumnInfo(name = "_icon_img")
    private String iconImg;

    @ColumnInfo(name = "_header_title")
    private String headerTitle;

    @ColumnInfo(name = "_description")
    private String description;

    @ColumnInfo(name = "_user_is_muted")
    private int userIsMuted;

    @ColumnInfo(name = "_submit_link_label")
    private String submitLinkLabel;

    @ColumnInfo(name = "_accounts_active")
    private String accountsActive;

    @ColumnInfo(name = "_public_traffic")
    private int publicTraffic;

    @ColumnInfo(name = "_header_size")
    private int headerSize;

    @ColumnInfo(name = "_subscribers")
    private int subscribers;

    @ColumnInfo(name = "_user_flair_css_class")
    private String userFlairCssClass;

    @ColumnInfo(name = "_submit_text_label")
    private String submitTextLabel;

    @ColumnInfo(name = "_whitelist_status")
    private String whitelistStatus;

    @ColumnInfo(name = "_user_sr_flair_enabled")
    private String userSrFlairEnabled;

    @ColumnInfo(name = "_lang")
    private String lang;

    @ColumnInfo(name = "_user_is_moderator")
    private String userIsModerator;

    @ColumnInfo(name = "_is_enrolled_in_new_modmail")
    private String isEnrolledInNewModmail;

    @ColumnInfo(name = "_key_oolor")
    private String keyColor;

    @ColumnInfo(name = "_name")
    private String name;

    @ColumnInfo(name = "_user_flair_enabled_in_sr")
    private int userFlairEnabledInSr;

    @ColumnInfo(name = "_allow_videogifs")
    private int allowVideoGifs;

    @ColumnInfo(name = "_url")
    private String url;

    @ColumnInfo(name = "_quarantine")
    private int quarantine;

    @ColumnInfo(name = "_created")
    private double created;

    @TypeConverters(DateConverter.class)
    @ColumnInfo(name = "_created_utc")
    private long createdUtc;

    @ColumnInfo(name = "_banner_size")
    private int bannerSize;

    @ColumnInfo(name = "_user_is_contributor")
    private String userIsContributor;

    @ColumnInfo(name = "_allow_discovery")
    private int allowDiscovery;

    @ColumnInfo(name = "_accounts_active_is_fuzzed")
    private int accountsActiveIsFuzzed;

    @ColumnInfo(name = "_advertiser_category")
    private String advertiserCategory;

    @ColumnInfo(name = "_public_description")
    private String publicDescription;

    @ColumnInfo(name = "_link_flair_enabled")
    private int linkFlairEnabled;

    @ColumnInfo(name = "_allow_images")
    private int allowImages;

    @ColumnInfo(name = "_show_media_preview")
    private int showMediaPreview;

    @ColumnInfo(name = "_comment_score_hide_mins")
    private int commentScoreHideMins;

    @ColumnInfo(name = "_subreddit_type")
    private String subredditType;

    @ColumnInfo(name = "_submission_type")
    private String submissionType;

    @ColumnInfo(name = "_user_is_subscriber")
    private String userIsSubscriber;

    @ColumnInfo(name = "_sort_by")
    private String sortBy;

    public T5Entry() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getTimeLastModified() {
        return timeLastModified;
    }

    public void setTimeLastModified(long timeLastModified) {
        this.timeLastModified = timeLastModified;
    }

    public String getSuggestCommentSort() {
        return suggestCommentSort;
    }

    public void setSuggestCommentSort(String suggestCommentSort) {
        this.suggestCommentSort = suggestCommentSort;
    }

    public int getHideAds() {
        return hideAds;
    }

    public void setHideAds(int hideAds) {
        this.hideAds = hideAds;
    }

    public String getBannerImg() {
        return bannerImg;
    }

    public void setBannerImg(String bannerImg) {
        this.bannerImg = bannerImg;
    }

    public int getUserSrThemeEnabled() {
        return userSrThemeEnabled;
    }

    public void setUserSrThemeEnabled(int userSrThemeEnabled) {
        this.userSrThemeEnabled = userSrThemeEnabled;
    }

    public String getUserFlairText() {
        return userFlairText;
    }

    public void setUserFlairText(String userFlairText) {
        this.userFlairText = userFlairText;
    }

    public String getSubmitTextHtml() {
        return submitTextHtml;
    }

    public void setSubmitTextHtml(String submitTextHtml) {
        this.submitTextHtml = submitTextHtml;
    }

    public int getUserIsBanned() {
        return userIsBanned;
    }

    public void setUserIsBanned(int userIsBanned) {
        this.userIsBanned = userIsBanned;
    }

    public int getWikiEnabled() {
        return wikiEnabled;
    }

    public void setWikiEnabled(int wikiEnabled) {
        this.wikiEnabled = wikiEnabled;
    }

    public int getShowMedia() {
        return showMedia;
    }

    public void setShowMedia(int showMedia) {
        this.showMedia = showMedia;
    }

    public String getNameId() {
        return nameId;
    }

    public void setNameId(String nameId) {
        this.nameId = nameId;
    }

    public int getChildrenId() {
        return childrenId;
    }

    public void setChildrenId(int childrenId) {
        this.childrenId = childrenId;
    }

    public String getDisplayNamePrefixed() {
        return displayNamePrefixed;
    }

    public void setDisplayNamePrefixed(String displayNamePrefixed) {
        this.displayNamePrefixed = displayNamePrefixed;
    }

    public String getSubmitText() {
        return submitText;
    }

    public void setSubmitText(String submitText) {
        this.submitText = submitText;
    }

    public String getUserCanFlairInSr() {
        return userCanFlairInSr;
    }

    public void setUserCanFlairInSr(String userCanFlairInSr) {
        this.userCanFlairInSr = userCanFlairInSr;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getHeaderImg() {
        return headerImg;
    }

    public void setHeaderImg(String headerImg) {
        this.headerImg = headerImg;
    }

    public String getDescriptionHtml() {
        return descriptionHtml;
    }

    public void setDescriptionHtml(String descriptionHtml) {
        this.descriptionHtml = descriptionHtml;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getCollapseDeletedComments() {
        return collapseDeletedComments;
    }

    public void setCollapseDeletedComments(int collapseDeletedComments) {
        this.collapseDeletedComments = collapseDeletedComments;
    }

    public String getUserHasFavorited() {
        return userHasFavorited;
    }

    public void setUserHasFavorited(String userHasFavorited) {
        this.userHasFavorited = userHasFavorited;
    }

    public int getOver18() {
        return over18;
    }

    public void setOver18(int over18) {
        this.over18 = over18;
    }

    public String getPublicDescriptionHtml() {
        return publicDescriptionHtml;
    }

    public void setPublicDescriptionHtml(String publicDescriptionHtml) {
        this.publicDescriptionHtml = publicDescriptionHtml;
    }

    public int getAllowVideos() {
        return allowVideos;
    }

    public void setAllowVideos(int allowVideos) {
        this.allowVideos = allowVideos;
    }

    public int getSpoilersEnabled() {
        return spoilersEnabled;
    }

    public void setSpoilersEnabled(int spoilersEnabled) {
        this.spoilersEnabled = spoilersEnabled;
    }

    public int getIconSize() {
        return iconSize;
    }

    public void setIconSize(int iconSize) {
        this.iconSize = iconSize;
    }

    public String getAudienceTarget() {
        return audienceTarget;
    }

    public void setAudienceTarget(String audienceTarget) {
        this.audienceTarget = audienceTarget;
    }

    public String getNotificationLevel() {
        return notificationLevel;
    }

    public void setNotificationLevel(String notificationLevel) {
        this.notificationLevel = notificationLevel;
    }

    public String getActiveUserCount() {
        return activeUserCount;
    }

    public void setActiveUserCount(String activeUserCount) {
        this.activeUserCount = activeUserCount;
    }

    public String getIconImg() {
        return iconImg;
    }

    public void setIconImg(String iconImg) {
        this.iconImg = iconImg;
    }

    public String getHeaderTitle() {
        return headerTitle;
    }

    public void setHeaderTitle(String headerTitle) {
        this.headerTitle = headerTitle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getUserIsMuted() {
        return userIsMuted;
    }

    public void setUserIsMuted(int userIsMuted) {
        this.userIsMuted = userIsMuted;
    }

    public String getSubmitLinkLabel() {
        return submitLinkLabel;
    }

    public void setSubmitLinkLabel(String submitLinkLabel) {
        this.submitLinkLabel = submitLinkLabel;
    }

    public String getAccountsActive() {
        return accountsActive;
    }

    public void setAccountsActive(String accountsActive) {
        this.accountsActive = accountsActive;
    }

    public int getPublicTraffic() {
        return publicTraffic;
    }

    public void setPublicTraffic(int publicTraffic) {
        this.publicTraffic = publicTraffic;
    }

    public int getHeaderSize() {
        return headerSize;
    }

    public void setHeaderSize(int headerSize) {
        this.headerSize = headerSize;
    }

    public int getSubscribers() {
        return subscribers;
    }

    public void setSubscribers(int subscribers) {
        this.subscribers = subscribers;
    }

    public String getUserFlairCssClass() {
        return userFlairCssClass;
    }

    public void setUserFlairCssClass(String userFlairCssClass) {
        this.userFlairCssClass = userFlairCssClass;
    }

    public String getSubmitTextLabel() {
        return submitTextLabel;
    }

    public void setSubmitTextLabel(String submitTextLabel) {
        this.submitTextLabel = submitTextLabel;
    }

    public String getWhitelistStatus() {
        return whitelistStatus;
    }

    public void setWhitelistStatus(String whitelistStatus) {
        this.whitelistStatus = whitelistStatus;
    }

    public String getUserSrFlairEnabled() {
        return userSrFlairEnabled;
    }

    public void setUserSrFlairEnabled(String userSrFlairEnabled) {
        this.userSrFlairEnabled = userSrFlairEnabled;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getUserIsModerator() {
        return userIsModerator;
    }

    public void setUserIsModerator(String userIsModerator) {
        this.userIsModerator = userIsModerator;
    }

    public String getIsEnrolledInNewModmail() {
        return isEnrolledInNewModmail;
    }

    public void setIsEnrolledInNewModmail(String isEnrolledInNewModmail) {
        this.isEnrolledInNewModmail = isEnrolledInNewModmail;
    }

    public String getKeyColor() {
        return keyColor;
    }

    public void setKeyColor(String keyColor) {
        this.keyColor = keyColor;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getUserFlairEnabledInSr() {
        return userFlairEnabledInSr;
    }

    public void setUserFlairEnabledInSr(int userFlairEnabledInSr) {
        this.userFlairEnabledInSr = userFlairEnabledInSr;
    }

    public int getAllowVideoGifs() {
        return allowVideoGifs;
    }

    public void setAllowVideoGifs(int allowVideoGifs) {
        this.allowVideoGifs = allowVideoGifs;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getQuarantine() {
        return quarantine;
    }

    public void setQuarantine(int quarantine) {
        this.quarantine = quarantine;
    }

    public double getCreated() {
        return created;
    }

    public void setCreated(double created) {
        this.created = created;
    }

    public long getCreatedUtc() {
        return createdUtc;
    }

    public void setCreatedUtc(long createdUtc) {
        this.createdUtc = createdUtc;
    }

    public int getBannerSize() {
        return bannerSize;
    }

    public void setBannerSize(int bannerSize) {
        this.bannerSize = bannerSize;
    }

    public String getUserIsContributor() {
        return userIsContributor;
    }

    public void setUserIsContributor(String userIsContributor) {
        this.userIsContributor = userIsContributor;
    }

    public int getAllowDiscovery() {
        return allowDiscovery;
    }

    public void setAllowDiscovery(int allowDiscovery) {
        this.allowDiscovery = allowDiscovery;
    }

    public int getAccountsActiveIsFuzzed() {
        return accountsActiveIsFuzzed;
    }

    public void setAccountsActiveIsFuzzed(int accountsActiveIsFuzzed) {
        this.accountsActiveIsFuzzed = accountsActiveIsFuzzed;
    }

    public String getAdvertiserCategory() {
        return advertiserCategory;
    }

    public void setAdvertiserCategory(String advertiserCategory) {
        this.advertiserCategory = advertiserCategory;
    }

    public String getPublicDescription() {
        return publicDescription;
    }

    public void setPublicDescription(String publicDescription) {
        this.publicDescription = publicDescription;
    }

    public int getLinkFlairEnabled() {
        return linkFlairEnabled;
    }

    public void setLinkFlairEnabled(int linkFlairEnabled) {
        this.linkFlairEnabled = linkFlairEnabled;
    }

    public int getAllowImages() {
        return allowImages;
    }

    public void setAllowImages(int allowImages) {
        this.allowImages = allowImages;
    }

    public int getShowMediaPreview() {
        return showMediaPreview;
    }

    public void setShowMediaPreview(int showMediaPreview) {
        this.showMediaPreview = showMediaPreview;
    }

    public int getCommentScoreHideMins() {
        return commentScoreHideMins;
    }

    public void setCommentScoreHideMins(int commentScoreHideMins) {
        this.commentScoreHideMins = commentScoreHideMins;
    }

    public String getSubredditType() {
        return subredditType;
    }

    public void setSubredditType(String subredditType) {
        this.subredditType = subredditType;
    }

    public String getSubmissionType() {
        return submissionType;
    }

    public void setSubmissionType(String submissionType) {
        this.submissionType = submissionType;
    }

    public String getUserIsSubscriber() {
        return userIsSubscriber;
    }

    public void setUserIsSubscriber(String userIsSubscriber) {
        this.userIsSubscriber = userIsSubscriber;
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }
}
