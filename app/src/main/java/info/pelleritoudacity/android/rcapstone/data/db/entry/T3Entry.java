package info.pelleritoudacity.android.rcapstone.data.db.entry;


import java.util.Date;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;
import info.pelleritoudacity.android.rcapstone.data.db.util.DateConverter;

@Entity(tableName = "_t3")
public class T3Entry {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @TypeConverters(DateConverter.class)
    @ColumnInfo(name = "_time_last_modified")
    private long timeLastModified;

    @TypeConverters(DateConverter.class)
    @ColumnInfo(name = "_approved_at_utc")
    private long approvedAtUtc;

    @ColumnInfo(name = "_approved_by")
    private String approvedBy;

    @ColumnInfo(name = "_author_flair_text")
    private String authorFlairText;

    @ColumnInfo(name = "_archived")
    private int archived;

    @ColumnInfo(name = "_num_comments")
    private int numComments;

    @ColumnInfo(name = "_author")
    private String author;

    @ColumnInfo(name = "_author_flair_css_class")
    private String authorFlairCssClass;

    @TypeConverters(DateConverter.class)
    @ColumnInfo(name = "_banned_at_utc")
    private Date bannedAtUtc;

    @ColumnInfo(name = "_banned_by")
    private String bannedBy;

    @ColumnInfo(name = "_can_gild")
    private int canGild;

    @ColumnInfo(name = "_can_mod_post")
    private int canModPost;

    @ColumnInfo(name = "_children_id")
    private int childrenId;

    @ColumnInfo(name = "_clicked")
    private int clicked;

    @ColumnInfo(name = "_contest_mode")
    private int contestMode;

    @ColumnInfo(name = "_num_reports")
    private int numReports;

    @ColumnInfo(name = "_created")
    private int created;

    @TypeConverters(DateConverter.class)
    @ColumnInfo(name = "_created_utc")
    private long createdUtc;

    @ColumnInfo(name = "_distinguished")
    private String distinguished;

    @ColumnInfo(name = "_domain")
    private String domain;

    @ColumnInfo(name = "_downs")
    private int downs;

    @ColumnInfo(name = "_edited")
    private int edited;

    @ColumnInfo(name = "_gilded")
    private int gilded;

    @ColumnInfo(name = "_hidden")
    private int hidden;

    @ColumnInfo(name = "_hide_score")
    private int hideScore;

    @ColumnInfo(name = "_name_id")
    private String nameId;

    @ColumnInfo(name = "_crosspostable")
    private int crosspostable;

    @ColumnInfo(name = "_reddit_main_domain")
    private int redditMainDomain;

    @ColumnInfo(name = "_is_self")
    private int isSelf;

    @ColumnInfo(name = "_is_video")
    private int isVideo;

    @ColumnInfo(name = "_num_cross_post")
    private int numCrossPost;

    @ColumnInfo(name = "_likes")
    private String likes;

    @ColumnInfo(name = "_link_flair_css_class")
    private String linkFlairCssClass;

    @ColumnInfo(name = "_no_follow")
    private int NoFollow;

    @ColumnInfo(name = "_link_flair_text")
    private String linkFlairText;

    @ColumnInfo(name = "_locked")
    private int locked;

    @ColumnInfo(name = "_media")
    private String media;

    @ColumnInfo(name = "_media_embed")
    private String mediaEmbed;

    @ColumnInfo(name = "_mod_note")
    private String modNote;

    @ColumnInfo(name = "_mod_reason_by")
    private String modReasonBy;

    @ColumnInfo(name = "_mod_reason_title")
    private String modReasonTitle;

    @ColumnInfo(name = "_mod_reports")
    private String modReports;

    @ColumnInfo(name = "_over18")
    private int over18;

    @ColumnInfo(name = "_parent_whitelist_status")
    private String parentWhitelistStatus;

    @ColumnInfo(name = "_permalink")
    private String permalink;

    @ColumnInfo(name = "_pinned")
    private int pinned;

    @ColumnInfo(name = "_pwls")
    private int pwls;

    @ColumnInfo(name = "_quarantine")
    private int quarantine;

    @ColumnInfo(name = "_removal_reason")
    private int removalReason;

    @ColumnInfo(name = "_report_reason")
    private int reportReason;

    @ColumnInfo(name = "_saved")
    private int saved;

    @ColumnInfo(name = "_score")
    private int score;

    @ColumnInfo(name = "_dir_score")
    private int dirScore;

    @ColumnInfo(name = "_secure_media")
    private String secureMedia;

    @ColumnInfo(name = "_secure_media_embed")
    private String secureMediaEmbed;

    @ColumnInfo(name = "_self_text")
    private String selfText;

    @ColumnInfo(name = "_self_text_html")
    private String selfTextHtml;

    @ColumnInfo(name = "_send_replies")
    private int sendReplies;

    @ColumnInfo(name = "_spoiler")
    private int spoiler;

    @ColumnInfo(name = "_stickied")
    private int stickied;

    @ColumnInfo(name = "_subbreddit")
    private String subreddit;

    @ColumnInfo(name = "_subreddit_id")
    private String subredditId;

    @ColumnInfo(name = "_subreddit_name_prefix")
    private String subredditNamePrefix;

    @ColumnInfo(name = "_subreddit_type")
    private String subredditType;

    @ColumnInfo(name = "_suggested_sort")
    private String suggestedSort;

    @ColumnInfo(name = "_thumbnail")
    private String thumbnail;

    @ColumnInfo(name = "_thumbnail_width")
    private String thumbnailWidth;

    @ColumnInfo(name = "_thumbnail_height")
    private String thumbnailHeight;

    @ColumnInfo(name = "_title")
    private String title;

    @ColumnInfo(name = "_ups")
    private int ups;

    @ColumnInfo(name = "_url")
    private String url;

    @ColumnInfo(name = "_name")
    private String name;

    @ColumnInfo(name = "_user_reports")
    private String userReports;

    @ColumnInfo(name = "_view_count")
    private String viewCount;

    @ColumnInfo(name = "_visited")
    private int visited;

    @ColumnInfo(name = "_whitelist_status")
    private String whitelistStatus;

    @ColumnInfo(name = "_wls")
    private int wls;

    @ColumnInfo(name = "_target")
    private String target;

    @ColumnInfo(name = "_sort_by")
    private String sortBy;

    @ColumnInfo(name = "_preview_image_source_url")
    private String previewImageSourceUrl;

    @ColumnInfo(name = "_preview_image_source_width")
    private int previewImageSourceWidth;

    @ColumnInfo(name = "_preview_image_source_height")
    private int previewImageSourceHeight;

    @ColumnInfo(name = "_preview_video_hls_url")
    private String previewVideoHlsUrl;

    @ColumnInfo(name = "_preview_video_dash_url")
    private String previewVideoDashUrl;

    @ColumnInfo(name = "_preview_video_scrubber_media_url")
    private String previewVideoScrubberMediaUrl;

    @ColumnInfo(name = "_preview_video_fallback_url")
    private String previewVideoFallbackUrl;

    @ColumnInfo(name = "_preview_video_transcoding_status")
    private String previewVideoTransCodingStatus;

    @ColumnInfo(name = "_preview_video_duration")
    private int previewVideoDuration;

    @ColumnInfo(name = "_preview_video_width")
    private int previewVideoWidth;

    @ColumnInfo(name = "_preview_video_height")
    private int previewVideoHeight;

    @ColumnInfo(name = "_preview_video_gif")
    private int previewVideoGif;

    @ColumnInfo(name = "_preview_video_mp4_url")
    private String previewVideoMp4Url;

    @ColumnInfo(name = "_preview_video_mp4_width")
    private int previewVideoMp4Width;

    @ColumnInfo(name = "_preview_video_mp4_height")
    private int previewVideoMp4Height;

    @ColumnInfo(name = "_variant_video_mp4_url")
    private String variantVideoMp4Url;

    @ColumnInfo(name = "_variant_video_mp4_width")
    private int variantVideoMp4Width;

    @ColumnInfo(name = "_variant_video_mp4_height")
    private int variantVideoMp4Height;

    @ColumnInfo(name = "_media_type")
    private String mediaType;

    @ColumnInfo(name = "_media_is_video")
    private int mediaIsVideo;

    @ColumnInfo(name = "_media_oembed_provider_url")
    private String mediaOembedProviderUrl;

    @ColumnInfo(name = "_media_oembed_title")
    private String mediaOembedTitle;

    @ColumnInfo(name = "_media_oembed_type")
    private String mediaOembedType;

    @ColumnInfo(name = "_media_oembed_html")
    private String mediaOembedHtml;

    @ColumnInfo(name = "_media_oembed_author_name")
    private String mediaOembedAuthorName;

    @ColumnInfo(name = "_media_oembed_width")
    private int mediaOembedWidth;

    @ColumnInfo(name = "_media_oembed_height")
    private int mediaOembedHeight;

    @ColumnInfo(name = "_media_oembed_thumbnail_url")
    private String mediaOembedThumbnailUrl;

    @ColumnInfo(name = "_media_oembed_thumbnail_width")
    private int mediaOembedThumbnailWidth;

    @ColumnInfo(name = "_media_oembed_thumbnail_heith")
    private int mediaOembedThumbnailHeight;

    @ColumnInfo(name = "_media_oembed_provider_name")
    private String mediaOembedProviderName;

    @ColumnInfo(name = "_media_oembed_provider_version")
    private String mediaOembedProviderVersion;

    @ColumnInfo(name = "_media_oembed_author_url")
    private String mediaOembedAuthorUrl;

    public T3Entry() {
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

    public long getApprovedAtUtc() {
        return approvedAtUtc;
    }

    public void setApprovedAtUtc(long approvedAtUtc) {
        this.approvedAtUtc = approvedAtUtc;
    }

    public String getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(String approvedBy) {
        this.approvedBy = approvedBy;
    }

    public int getArchived() {
        return archived;
    }

    public void setArchived(int archived) {
        this.archived = archived;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAuthorFlairCssClass() {
        return authorFlairCssClass;
    }

    public void setAuthorFlairCssClass(String authorFlairCssClass) {
        this.authorFlairCssClass = authorFlairCssClass;
    }

    public Date getBannedAtUtc() {
        return bannedAtUtc;
    }

    public void setBannedAtUtc(Date bannedAtUtc) {
        this.bannedAtUtc = bannedAtUtc;
    }

    public String getBannedBy() {
        return bannedBy;
    }

    public void setBannedBy(String bannedBy) {
        this.bannedBy = bannedBy;
    }

    public int getCanGild() {
        return canGild;
    }

    public void setCanGild(int canGild) {
        this.canGild = canGild;
    }

    public int getCanModPost() {
        return canModPost;
    }

    public void setCanModPost(int canModPost) {
        this.canModPost = canModPost;
    }

    public int getChildrenId() {
        return childrenId;
    }

    public void setChildrenId(int childrenId) {
        this.childrenId = childrenId;
    }

    public int getClicked() {
        return clicked;
    }

    public void setClicked(int clicked) {
        this.clicked = clicked;
    }

    public int getContestMode() {
        return contestMode;
    }

    public void setContestMode(int contestMode) {
        this.contestMode = contestMode;
    }

    public int getCreated() {
        return created;
    }

    public void setCreated(int created) {
        this.created = created;
    }

    public long getCreatedUtc() {
        return createdUtc;
    }

    public void setCreatedUtc(long createdUtc) {
        this.createdUtc = createdUtc;
    }

    public String getDistinguished() {
        return distinguished;
    }

    public void setDistinguished(String distinguished) {
        this.distinguished = distinguished;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public int getDowns() {
        return downs;
    }

    public void setDowns(int downs) {
        this.downs = downs;
    }

    public int getEdited() {
        return edited;
    }

    public void setEdited(int edited) {
        this.edited = edited;
    }

    public int getGilded() {
        return gilded;
    }

    public void setGilded(int gilded) {
        this.gilded = gilded;
    }

    public int getHidden() {
        return hidden;
    }

    public void setHidden(int hidden) {
        this.hidden = hidden;
    }

    public int getHideScore() {
        return hideScore;
    }

    public void setHideScore(int hideScore) {
        this.hideScore = hideScore;
    }

    public String getNameId() {
        return nameId;
    }

    public void setNameId(String nameId) {
        this.nameId = nameId;
    }

    public int getCrosspostable() {
        return crosspostable;
    }

    public void setCrosspostable(int crosspostable) {
        this.crosspostable = crosspostable;
    }

    public int getRedditMainDomain() {
        return redditMainDomain;
    }

    public void setRedditMainDomain(int redditMainDomain) {
        this.redditMainDomain = redditMainDomain;
    }

    public int getIsSelf() {
        return isSelf;
    }

    public void setIsSelf(int isSelf) {
        this.isSelf = isSelf;
    }

    public int getIsVideo() {
        return isVideo;
    }

    public void setIsVideo(int isVideo) {
        this.isVideo = isVideo;
    }

    public String getLikes() {
        return likes;
    }

    public void setLikes(String likes) {
        this.likes = likes;
    }

    public String getLinkFlairCssClass() {
        return linkFlairCssClass;
    }

    public void setLinkFlairCssClass(String linkFlairCssClass) {
        this.linkFlairCssClass = linkFlairCssClass;
    }

    public String getLinkFlairText() {
        return linkFlairText;
    }

    public void setLinkFlairText(String linkFlairText) {
        this.linkFlairText = linkFlairText;
    }

    public int getLocked() {
        return locked;
    }

    public void setLocked(int locked) {
        this.locked = locked;
    }

    public String getMedia() {
        return media;
    }

    public void setMedia(String media) {
        this.media = media;
    }

    public String getMediaEmbed() {
        return mediaEmbed;
    }

    public void setMediaEmbed(String mediaEmbed) {
        this.mediaEmbed = mediaEmbed;
    }

    public String getModNote() {
        return modNote;
    }

    public void setModNote(String modNote) {
        this.modNote = modNote;
    }

    public String getModReasonBy() {
        return modReasonBy;
    }

    public void setModReasonBy(String modReasonBy) {
        this.modReasonBy = modReasonBy;
    }

    public String getModReasonTitle() {
        return modReasonTitle;
    }

    public void setModReasonTitle(String modReasonTitle) {
        this.modReasonTitle = modReasonTitle;
    }

    public String getModReports() {
        return modReports;
    }

    public void setModReports(String modReports) {
        this.modReports = modReports;
    }

    public int getOver18() {
        return over18;
    }

    public void setOver18(int over18) {
        this.over18 = over18;
    }

    public String getParentWhitelistStatus() {
        return parentWhitelistStatus;
    }

    public void setParentWhitelistStatus(String parentWhitelistStatus) {
        this.parentWhitelistStatus = parentWhitelistStatus;
    }

    public String getPermalink() {
        return permalink;
    }

    public void setPermalink(String permalink) {
        this.permalink = permalink;
    }

    public int getPinned() {
        return pinned;
    }

    public void setPinned(int pinned) {
        this.pinned = pinned;
    }

    public int getPwls() {
        return pwls;
    }

    public void setPwls(int pwls) {
        this.pwls = pwls;
    }

    public int getQuarantine() {
        return quarantine;
    }

    public void setQuarantine(int quarantine) {
        this.quarantine = quarantine;
    }

    public int getRemovalReason() {
        return removalReason;
    }

    public void setRemovalReason(int removalReason) {
        this.removalReason = removalReason;
    }

    public int getReportReason() {
        return reportReason;
    }

    public void setReportReason(int reportReason) {
        this.reportReason = reportReason;
    }

    public int getSaved() {
        return saved;
    }

    public void setSaved(int saved) {
        this.saved = saved;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getDirScore() {
        return dirScore;
    }

    public void setDirScore(int dirScore) {
        this.dirScore = dirScore;
    }

    public String getSecureMedia() {
        return secureMedia;
    }

    public void setSecureMedia(String secureMedia) {
        this.secureMedia = secureMedia;
    }

    public String getSecureMediaEmbed() {
        return secureMediaEmbed;
    }

    public void setSecureMediaEmbed(String secureMediaEmbed) {
        this.secureMediaEmbed = secureMediaEmbed;
    }

    public String getSelfText() {
        return selfText;
    }

    public void setSelfText(String selfText) {
        this.selfText = selfText;
    }

    public String getSelfTextHtml() {
        return selfTextHtml;
    }

    public void setSelfTextHtml(String selfTextHtml) {
        this.selfTextHtml = selfTextHtml;
    }

    public int getSendReplies() {
        return sendReplies;
    }

    public void setSendReplies(int sendReplies) {
        this.sendReplies = sendReplies;
    }

    public int getSpoiler() {
        return spoiler;
    }

    public void setSpoiler(int spoiler) {
        this.spoiler = spoiler;
    }

    public int getStickied() {
        return stickied;
    }

    public void setStickied(int stickied) {
        this.stickied = stickied;
    }

    public String getSubreddit() {
        return subreddit;
    }

    public void setSubreddit(String subreddit) {
        this.subreddit = subreddit;
    }

    public String getSubredditId() {
        return subredditId;
    }

    public void setSubredditId(String subredditId) {
        this.subredditId = subredditId;
    }

    public String getSubredditNamePrefix() {
        return subredditNamePrefix;
    }

    public void setSubredditNamePrefix(String subredditNamePrefix) {
        this.subredditNamePrefix = subredditNamePrefix;
    }

    public String getSubredditType() {
        return subredditType;
    }

    public void setSubredditType(String subredditType) {
        this.subredditType = subredditType;
    }

    public String getSuggestedSort() {
        return suggestedSort;
    }

    public void setSuggestedSort(String suggestedSort) {
        this.suggestedSort = suggestedSort;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getThumbnailWidth() {
        return thumbnailWidth;
    }

    public void setThumbnailWidth(String thumbnailWidth) {
        this.thumbnailWidth = thumbnailWidth;
    }

    public String getThumbnailHeight() {
        return thumbnailHeight;
    }

    public void setThumbnailHeight(String thumbnailHeight) {
        this.thumbnailHeight = thumbnailHeight;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getUps() {
        return ups;
    }

    public void setUps(int ups) {
        this.ups = ups;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUserReports() {
        return userReports;
    }

    public void setUserReports(String userReports) {
        this.userReports = userReports;
    }

    public String getViewCount() {
        return viewCount;
    }

    public void setViewCount(String viewCount) {
        this.viewCount = viewCount;
    }

    public int getVisited() {
        return visited;
    }

    public void setVisited(int visited) {
        this.visited = visited;
    }

    public String getWhitelistStatus() {
        return whitelistStatus;
    }

    public void setWhitelistStatus(String whitelistStatus) {
        this.whitelistStatus = whitelistStatus;
    }

    public int getWls() {
        return wls;
    }

    public void setWls(int wls) {
        this.wls = wls;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public String getPreviewImageSourceUrl() {
        return previewImageSourceUrl;
    }

    public void setPreviewImageSourceUrl(String previewImageSourceUrl) {
        this.previewImageSourceUrl = previewImageSourceUrl;
    }

    public int getPreviewImageSourceWidth() {
        return previewImageSourceWidth;
    }

    public void setPreviewImageSourceWidth(int previewImageSourceWidth) {
        this.previewImageSourceWidth = previewImageSourceWidth;
    }

    public int getPreviewImageSourceHeight() {
        return previewImageSourceHeight;
    }

    public void setPreviewImageSourceHeight(int previewImageSourceHeight) {
        this.previewImageSourceHeight = previewImageSourceHeight;
    }

    public String getPreviewVideoHlsUrl() {
        return previewVideoHlsUrl;
    }

    public void setPreviewVideoHlsUrl(String previewVideoHlsUrl) {
        this.previewVideoHlsUrl = previewVideoHlsUrl;
    }

    public String getPreviewVideoDashUrl() {
        return previewVideoDashUrl;
    }

    public void setPreviewVideoDashUrl(String previewVideoDashUrl) {
        this.previewVideoDashUrl = previewVideoDashUrl;
    }

    public String getPreviewVideoScrubberMediaUrl() {
        return previewVideoScrubberMediaUrl;
    }

    public void setPreviewVideoScrubberMediaUrl(String previewVideoScrubberMediaUrl) {
        this.previewVideoScrubberMediaUrl = previewVideoScrubberMediaUrl;
    }

    public String getPreviewVideoFallbackUrl() {
        return previewVideoFallbackUrl;
    }

    public void setPreviewVideoFallbackUrl(String previewVideoFallbackUrl) {
        this.previewVideoFallbackUrl = previewVideoFallbackUrl;
    }

    public String getPreviewVideoTransCodingStatus() {
        return previewVideoTransCodingStatus;
    }

    public void setPreviewVideoTransCodingStatus(String previewVideoTransCodingStatus) {
        this.previewVideoTransCodingStatus = previewVideoTransCodingStatus;
    }

    public int getPreviewVideoDuration() {
        return previewVideoDuration;
    }

    public void setPreviewVideoDuration(int previewVideoDuration) {
        this.previewVideoDuration = previewVideoDuration;
    }

    public int getPreviewVideoWidth() {
        return previewVideoWidth;
    }

    public void setPreviewVideoWidth(int previewVideoWidth) {
        this.previewVideoWidth = previewVideoWidth;
    }

    public int getPreviewVideoHeight() {
        return previewVideoHeight;
    }

    public void setPreviewVideoHeight(int previewVideoHeight) {
        this.previewVideoHeight = previewVideoHeight;
    }

    public int getPreviewVideoGif() {
        return previewVideoGif;
    }

    public void setPreviewVideoGif(int previewVideoGif) {
        this.previewVideoGif = previewVideoGif;
    }

    public String getPreviewVideoMp4Url() {
        return previewVideoMp4Url;
    }

    public void setPreviewVideoMp4Url(String previewVideoMp4Url) {
        this.previewVideoMp4Url = previewVideoMp4Url;
    }

    public int getPreviewVideoMp4Width() {
        return previewVideoMp4Width;
    }

    public void setPreviewVideoMp4Width(int previewVideoMp4Width) {
        this.previewVideoMp4Width = previewVideoMp4Width;
    }

    public int getPreviewVideoMp4Height() {
        return previewVideoMp4Height;
    }

    public void setPreviewVideoMp4Height(int previewVideoMp4Height) {
        this.previewVideoMp4Height = previewVideoMp4Height;
    }

    public String getVariantVideoMp4Url() {
        return variantVideoMp4Url;
    }

    public void setVariantVideoMp4Url(String variantVideoMp4Url) {
        this.variantVideoMp4Url = variantVideoMp4Url;
    }

    public int getVariantVideoMp4Width() {
        return variantVideoMp4Width;
    }

    public void setVariantVideoMp4Width(int variantVideoMp4Width) {
        this.variantVideoMp4Width = variantVideoMp4Width;
    }

    public int getVariantVideoMp4Height() {
        return variantVideoMp4Height;
    }

    public void setVariantVideoMp4Height(int variantVideoMp4Height) {
        this.variantVideoMp4Height = variantVideoMp4Height;
    }

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    public int getMediaIsVideo() {
        return mediaIsVideo;
    }

    public void setMediaIsVideo(int mediaIsVideo) {
        this.mediaIsVideo = mediaIsVideo;
    }

    public String getMediaOembedProviderUrl() {
        return mediaOembedProviderUrl;
    }

    public void setMediaOembedProviderUrl(String mediaOembedProviderUrl) {
        this.mediaOembedProviderUrl = mediaOembedProviderUrl;
    }

    public String getMediaOembedTitle() {
        return mediaOembedTitle;
    }

    public void setMediaOembedTitle(String mediaOembedTitle) {
        this.mediaOembedTitle = mediaOembedTitle;
    }

    public String getMediaOembedType() {
        return mediaOembedType;
    }

    public void setMediaOembedType(String mediaOembedType) {
        this.mediaOembedType = mediaOembedType;
    }

    public String getMediaOembedHtml() {
        return mediaOembedHtml;
    }

    public void setMediaOembedHtml(String mediaOembedHtml) {
        this.mediaOembedHtml = mediaOembedHtml;
    }

    public String getMediaOembedAuthorName() {
        return mediaOembedAuthorName;
    }

    public void setMediaOembedAuthorName(String mediaOembedAuthorName) {
        this.mediaOembedAuthorName = mediaOembedAuthorName;
    }

    public int getMediaOembedWidth() {
        return mediaOembedWidth;
    }

    public void setMediaOembedWidth(int mediaOembedWidth) {
        this.mediaOembedWidth = mediaOembedWidth;
    }

    public int getMediaOembedHeight() {
        return mediaOembedHeight;
    }

    public void setMediaOembedHeight(int mediaOembedHeight) {
        this.mediaOembedHeight = mediaOembedHeight;
    }

    public int getMediaOembedThumbnailWidth() {
        return mediaOembedThumbnailWidth;
    }

    public void setMediaOembedThumbnailWidth(int mediaOembedThumbnailWidth) {
        this.mediaOembedThumbnailWidth = mediaOembedThumbnailWidth;
    }

    public int getMediaOembedThumbnailHeight() {
        return mediaOembedThumbnailHeight;
    }

    public void setMediaOembedThumbnailHeight(int mediaOembedThumbnailHeight) {
        this.mediaOembedThumbnailHeight = mediaOembedThumbnailHeight;
    }

    public String getMediaOembedProviderName() {
        return mediaOembedProviderName;
    }

    public void setMediaOembedProviderName(String mediaOembedProviderName) {
        this.mediaOembedProviderName = mediaOembedProviderName;
    }

    public String getMediaOembedProviderVersion() {
        return mediaOembedProviderVersion;
    }

    public void setMediaOembedProviderVersion(String mediaOembedProviderVersion) {
        this.mediaOembedProviderVersion = mediaOembedProviderVersion;
    }

    public String getMediaOembedAuthorUrl() {
        return mediaOembedAuthorUrl;
    }

    public void setMediaOembedAuthorUrl(String mediaOembedAuthorUrl) {
        this.mediaOembedAuthorUrl = mediaOembedAuthorUrl;
    }

    public int getNumReports() {
        return numReports;
    }

    public void setNumReports(int numReports) {
        this.numReports = numReports;
    }

    public int getNoFollow() {
        return NoFollow;
    }

    public void setNoFollow(int noFollow) {
        NoFollow = noFollow;
    }

    public int getNumCrossPost() {
        return numCrossPost;
    }

    public void setNumCrossPost(int numCrossPost) {
        this.numCrossPost = numCrossPost;
    }

    public String getAuthorFlairText() {
        return authorFlairText;
    }

    public void setAuthorFlairText(String authorFlairText) {
        this.authorFlairText = authorFlairText;
    }

    public int getNumComments() {
        return numComments;
    }

    public void setNumComments(int numComments) {
        this.numComments = numComments;
    }

    public String getMediaOembedThumbnailUrl() {
        return mediaOembedThumbnailUrl;
    }

    public void setMediaOembedThumbnailUrl(String mediaOembedThumbnailUrl) {
        this.mediaOembedThumbnailUrl = mediaOembedThumbnailUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}