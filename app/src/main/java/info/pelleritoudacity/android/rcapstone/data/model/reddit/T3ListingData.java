package info.pelleritoudacity.android.rcapstone.data.model.reddit;

import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class T3ListingData implements Parcelable {

    @SerializedName("approved_at_utc")
    @Expose
    private Object approvedAtUtc;
    @SerializedName("subreddit")
    @Expose
    private String subreddit;
    @SerializedName("selftext")
    @Expose
    private String selftext;
    @SerializedName("user_reports")
    @Expose
    private List<Object> userReports = null;
    @SerializedName("saved")
    @Expose
    private Boolean saved;
    @SerializedName("mod_reason_title")
    @Expose
    private Object modReasonTitle;
    @SerializedName("thumbnail_width")
    @Expose
    private Object thumbnailWidth;
    @SerializedName("gilded")
    @Expose
    private Integer gilded;
    @SerializedName("clicked")
    @Expose
    private Boolean clicked;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("link_flair_richtext")
    @Expose
    private List<Object> linkFlairRichtext = null;
    @SerializedName("subreddit_name_prefixed")
    @Expose
    private String subredditNamePrefixed;
    @SerializedName("hidden")
    @Expose
    private Boolean hidden;
    @SerializedName("preview")
    @Expose
    private Preview preview;
    @SerializedName("pwls")
    @Expose
    private Integer pwls;
    @SerializedName("thumbnail")
    @Expose
    private String thumbnail;
    @SerializedName("link_flair_css_class")
    @Expose
    private Object linkFlairCssClass;
    @SerializedName("downs")
    @Expose
    private Integer downs;
    @SerializedName("parent_whitelist_status")
    @Expose
    private String parentWhitelistStatus;
    @SerializedName("hide_score")
    @Expose
    private Boolean hideScore;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("quarantine")
    @Expose
    private Boolean quarantine;
    @SerializedName("link_flair_text_color")
    @Expose
    private String linkFlairTextColor;
    @SerializedName("author_flair_background_color")
    @Expose
    private Object authorFlairBackgroundColor;
    @SerializedName("subreddit_type")
    @Expose
    private String subredditType;
    @SerializedName("ups")
    @Expose
    private Integer ups;
    @SerializedName("domain")
    @Expose
    private String domain;
    @SerializedName("media_embed")
    @Expose
    private MediaEmbed mediaEmbed;
    @SerializedName("author_flair_template_id")
    @Expose
    private Object authorFlairTemplateId;
    @SerializedName("is_original_content")
    @Expose
    private Boolean isOriginalContent;
    @SerializedName("secure_media")
    @Expose
    private Object secureMedia;
    @SerializedName("is_reddit_media_domain")
    @Expose
    private Boolean isRedditMediaDomain;
    @SerializedName("category")
    @Expose
    private Object category;
    @SerializedName("secure_media_embed")
    @Expose
    private SecureMediaEmbed secureMediaEmbed;
    @SerializedName("link_flair_text")
    @Expose
    private Object linkFlairText;
    @SerializedName("can_mod_post")
    @Expose
    private Boolean canModPost;
    @SerializedName("score")
    @Expose
    private Integer score;
    @SerializedName("approved_by")
    @Expose
    private Object approvedBy;
    @SerializedName("edited")
    @Expose
    private Object edited;
    @SerializedName("author_flair_css_class")
    @Expose
    private Object authorFlairCssClass;
    @SerializedName("author_flair_richtext")
    @Expose
    private List<Object> authorFlairRichtext = null;
    @SerializedName("content_categories")
    @Expose
    private Object contentCategories;
    @SerializedName("is_self")
    @Expose
    private Boolean isSelf;
    @SerializedName("mod_note")
    @Expose
    private Object modNote;
    @SerializedName("created")
    @Expose
    private Integer created;
    @SerializedName("link_flair_type")
    @Expose
    private String linkFlairType;
    @SerializedName("wls")
    @Expose
    private Integer wls;
    @SerializedName("author_id")
    @Expose
    private String authorId;
    @SerializedName("post_categories")
    @Expose
    private Object postCategories;
    @SerializedName("banned_by")
    @Expose
    private Object bannedBy;
    @SerializedName("author_flair_type")
    @Expose
    private String authorFlairType;
    @SerializedName("contest_mode")
    @Expose
    private Boolean contestMode;
    @SerializedName("selftext_html")
    @Expose
    private Object selftextHtml;
    @SerializedName("likes")
    @Expose
    private Object likes;
    @SerializedName("suggested_sort")
    @Expose
    private Object suggestedSort;
    @SerializedName("banned_at_utc")
    @Expose
    private Object bannedAtUtc;
    @SerializedName("view_count")
    @Expose
    private Object viewCount;
    @SerializedName("archived")
    @Expose
    private Boolean archived;
    @SerializedName("no_follow")
    @Expose
    private Boolean noFollow;
    @SerializedName("is_crosspostable")
    @Expose
    private Boolean isCrosspostable;
    @SerializedName("pinned")
    @Expose
    private Boolean pinned;
    @SerializedName("over_18")
    @Expose
    private Boolean over18;
    @SerializedName("media")
    @Expose
    private Media media;
    @SerializedName("media_only")
    @Expose
    private Boolean mediaOnly;
    @SerializedName("thumbnail_height")
    @Expose
    private Object thumbnailHeight;
    @SerializedName("can_gild")
    @Expose
    private Boolean canGild;
    @SerializedName("spoiler")
    @Expose
    private Boolean spoiler;
    @SerializedName("locked")
    @Expose
    private Boolean locked;
    @SerializedName("author_flair_text")
    @Expose
    private Object authorFlairText;
    @SerializedName("rte_mode")
    @Expose
    private String rteMode;
    @SerializedName("visited")
    @Expose
    private Boolean visited;
    @SerializedName("num_reports")
    @Expose
    private Object numReports;
    @SerializedName("distinguished")
    @Expose
    private Object distinguished;
    @SerializedName("subreddit_id")
    @Expose
    private String subredditId;
    @SerializedName("mod_reason_by")
    @Expose
    private Object modReasonBy;
    @SerializedName("removal_reason")
    @Expose
    private Object removalReason;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("report_reasons")
    @Expose
    private Object reportReasons;
    @SerializedName("author")
    @Expose
    private String author;
    @SerializedName("num_crossposts")
    @Expose
    private Integer numCrossposts;
    @SerializedName("num_comments")
    @Expose
    private Integer numComments;
    @SerializedName("send_replies")
    @Expose
    private Boolean sendReplies;
    @SerializedName("author_flair_text_color")
    @Expose
    private Object authorFlairTextColor;
    @SerializedName("permalink")
    @Expose
    private String permalink;
    @SerializedName("whitelist_status")
    @Expose
    private String whitelistStatus;
    @SerializedName("stickied")
    @Expose
    private Boolean stickied;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("subreddit_subscribers")
    @Expose
    private Integer subredditSubscribers;
    @SerializedName("created_utc")
    @Expose
    private Integer createdUtc;
    @SerializedName("mod_reports")
    @Expose
    private List<Object> modReports = null;
    @SerializedName("is_video")
    @Expose
    private Boolean isVideo;
    public final static Parcelable.Creator<T3ListingData> CREATOR = new Creator<T3ListingData>() {


        @SuppressWarnings({
                "unchecked"
        })
        public T3ListingData createFromParcel(Parcel in) {
            return new T3ListingData(in);
        }

        public T3ListingData[] newArray(int size) {
            return (new T3ListingData[size]);
        }

    };

    protected T3ListingData(Parcel in) {
        this.approvedAtUtc = ((Object) in.readValue((Object.class.getClassLoader())));
        this.subreddit = ((String) in.readValue((String.class.getClassLoader())));
        this.selftext = ((String) in.readValue((String.class.getClassLoader())));
        in.readList(this.userReports, (java.lang.Object.class.getClassLoader()));
        this.saved = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.modReasonTitle = ((Object) in.readValue((Object.class.getClassLoader())));
        this.gilded = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.clicked = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.title = ((String) in.readValue((String.class.getClassLoader())));
        in.readList(this.linkFlairRichtext, (java.lang.Object.class.getClassLoader()));
        this.subredditNamePrefixed = ((String) in.readValue((String.class.getClassLoader())));
        this.hidden = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.thumbnailWidth = in.readValue((Object.class.getClassLoader()));
        this.thumbnailHeight = in.readValue((Object.class.getClassLoader()));
        this.preview = ((Preview) in.readValue((Preview.class.getClassLoader())));
        this.thumbnail = ((String) in.readValue((String.class.getClassLoader())));
        this.pwls = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.linkFlairCssClass = ((Object) in.readValue((Object.class.getClassLoader())));
        this.downs = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.parentWhitelistStatus = ((String) in.readValue((String.class.getClassLoader())));
        this.hideScore = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.name = ((String) in.readValue((String.class.getClassLoader())));
        this.quarantine = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.linkFlairTextColor = ((String) in.readValue((String.class.getClassLoader())));
        this.authorFlairBackgroundColor = ((Object) in.readValue((Object.class.getClassLoader())));
        this.subredditType = ((String) in.readValue((String.class.getClassLoader())));
        this.ups = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.domain = ((String) in.readValue((String.class.getClassLoader())));
        this.mediaEmbed = ((MediaEmbed) in.readValue((MediaEmbed.class.getClassLoader())));
        this.authorFlairTemplateId = ((Object) in.readValue((Object.class.getClassLoader())));
        this.isOriginalContent = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.secureMedia = ((Object) in.readValue((Object.class.getClassLoader())));
        this.isRedditMediaDomain = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.category = ((Object) in.readValue((Object.class.getClassLoader())));
        this.secureMediaEmbed = ((SecureMediaEmbed) in.readValue((SecureMediaEmbed.class.getClassLoader())));
        this.linkFlairText = ((Object) in.readValue((Object.class.getClassLoader())));
        this.canModPost = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.score = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.approvedBy = ((Object) in.readValue((Object.class.getClassLoader())));
        this.thumbnail = ((String) in.readValue((String.class.getClassLoader())));
        this.edited = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.authorFlairCssClass = ((Object) in.readValue((Object.class.getClassLoader())));
        in.readList(this.authorFlairRichtext, (java.lang.Object.class.getClassLoader()));
        this.contentCategories = ((Object) in.readValue((Object.class.getClassLoader())));
        this.isSelf = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.modNote = ((Object) in.readValue((Object.class.getClassLoader())));
        this.created = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.linkFlairType = ((String) in.readValue((String.class.getClassLoader())));
        this.wls = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.authorId = ((String) in.readValue((String.class.getClassLoader())));
        this.postCategories = ((Object) in.readValue((Object.class.getClassLoader())));
        this.bannedBy = ((Object) in.readValue((Object.class.getClassLoader())));
        this.authorFlairType = ((String) in.readValue((String.class.getClassLoader())));
        this.contestMode = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.selftextHtml = ((Object) in.readValue((Object.class.getClassLoader())));
        this.likes = ((Object) in.readValue((Object.class.getClassLoader())));
        this.suggestedSort = ((Object) in.readValue((Object.class.getClassLoader())));
        this.bannedAtUtc = ((Object) in.readValue((Object.class.getClassLoader())));
        this.viewCount = ((Object) in.readValue((Object.class.getClassLoader())));
        this.archived = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.noFollow = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.isCrosspostable = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.pinned = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.over18 = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.media = ((Media) in.readValue((Media.class.getClassLoader())));
        this.mediaOnly = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.canGild = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.spoiler = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.locked = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.authorFlairText = ((Object) in.readValue((Object.class.getClassLoader())));
        this.rteMode = ((String) in.readValue((String.class.getClassLoader())));
        this.visited = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.numReports = ((Object) in.readValue((Object.class.getClassLoader())));
        this.distinguished = ((Object) in.readValue((Object.class.getClassLoader())));
        this.subredditId = ((String) in.readValue((String.class.getClassLoader())));
        this.modReasonBy = ((Object) in.readValue((Object.class.getClassLoader())));
        this.removalReason = ((Object) in.readValue((Object.class.getClassLoader())));
        this.id = ((String) in.readValue((String.class.getClassLoader())));
        this.reportReasons = ((Object) in.readValue((Object.class.getClassLoader())));
        this.author = ((String) in.readValue((String.class.getClassLoader())));
        this.numCrossposts = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.numComments = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.sendReplies = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.authorFlairTextColor = ((Object) in.readValue((Object.class.getClassLoader())));
        this.permalink = ((String) in.readValue((String.class.getClassLoader())));
        this.whitelistStatus = ((String) in.readValue((String.class.getClassLoader())));
        this.stickied = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.url = ((String) in.readValue((String.class.getClassLoader())));
        this.subredditSubscribers = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.createdUtc = ((Integer) in.readValue((Integer.class.getClassLoader())));
        in.readList(this.modReports, (java.lang.Object.class.getClassLoader()));
        this.isVideo = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
    }

    public T3ListingData() {
    }

    public Object getApprovedAtUtc() {
        return approvedAtUtc;
    }

    public void setApprovedAtUtc(Object approvedAtUtc) {
        this.approvedAtUtc = approvedAtUtc;
    }

    public String getSubreddit() {
        return subreddit;
    }

    public void setSubreddit(String subreddit) {
        this.subreddit = subreddit;
    }

    public String getSelftext() {
        return selftext;
    }

    public void setSelftext(String selftext) {
        this.selftext = selftext;
    }

    public List<Object> getUserReports() {
        return userReports;
    }

    public void setUserReports(List<Object> userReports) {
        this.userReports = userReports;
    }

    public Boolean getSaved() {
        return saved;
    }

    public void setSaved(Boolean saved) {
        this.saved = saved;
    }

    public Object getModReasonTitle() {
        return modReasonTitle;
    }

    public void setModReasonTitle(Object modReasonTitle) {
        this.modReasonTitle = modReasonTitle;
    }

    public Integer getGilded() {
        return gilded;
    }

    public void setGilded(Integer gilded) {
        this.gilded = gilded;
    }

    public Boolean getClicked() {
        return clicked;
    }

    public void setClicked(Boolean clicked) {
        this.clicked = clicked;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Object> getLinkFlairRichtext() {
        return linkFlairRichtext;
    }

    public void setLinkFlairRichtext(List<Object> linkFlairRichtext) {
        this.linkFlairRichtext = linkFlairRichtext;
    }

    public String getSubredditNamePrefixed() {
        return subredditNamePrefixed;
    }

    public void setSubredditNamePrefixed(String subredditNamePrefixed) {
        this.subredditNamePrefixed = subredditNamePrefixed;
    }

    public Boolean getHidden() {
        return hidden;
    }

    public void setHidden(Boolean hidden) {
        this.hidden = hidden;
    }

    public Preview getPreview() {
        return preview;
    }

    public void setPreview(Preview preview) {
        this.preview = preview;
    }

    public Integer getPwls() {
        return pwls;
    }

    public void setPwls(Integer pwls) {
        this.pwls = pwls;
    }

    public Object getThumbnailWidth() {
        return thumbnailWidth;
    }

    public void setThumbnailWidth(Object thumbnailWidth) {
        this.thumbnailWidth = thumbnailWidth;
    }

    public Object getThumbnailHeight() {
        return thumbnailHeight;
    }

    public void setThumbnailHeight(Object thumbnailHeight) {
        this.thumbnailHeight = thumbnailHeight;
    }


    public Object getLinkFlairCssClass() {
        return linkFlairCssClass;
    }

    public void setLinkFlairCssClass(Object linkFlairCssClass) {
        this.linkFlairCssClass = linkFlairCssClass;
    }

    public Integer getDowns() {
        return downs;
    }

    public void setDowns(Integer downs) {
        this.downs = downs;
    }

    public String getParentWhitelistStatus() {
        return parentWhitelistStatus;
    }

    public void setParentWhitelistStatus(String parentWhitelistStatus) {
        this.parentWhitelistStatus = parentWhitelistStatus;
    }

    public Boolean getHideScore() {
        return hideScore;
    }

    public void setHideScore(Boolean hideScore) {
        this.hideScore = hideScore;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getQuarantine() {
        return quarantine;
    }

    public void setQuarantine(Boolean quarantine) {
        this.quarantine = quarantine;
    }

    public String getLinkFlairTextColor() {
        return linkFlairTextColor;
    }

    public void setLinkFlairTextColor(String linkFlairTextColor) {
        this.linkFlairTextColor = linkFlairTextColor;
    }

    public Object getAuthorFlairBackgroundColor() {
        return authorFlairBackgroundColor;
    }

    public void setAuthorFlairBackgroundColor(Object authorFlairBackgroundColor) {
        this.authorFlairBackgroundColor = authorFlairBackgroundColor;
    }

    public String getSubredditType() {
        return subredditType;
    }

    public void setSubredditType(String subredditType) {
        this.subredditType = subredditType;
    }

    public Integer getUps() {
        return ups;
    }

    public void setUps(Integer ups) {
        this.ups = ups;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public MediaEmbed getMediaEmbed() {
        return mediaEmbed;
    }

    public void setMediaEmbed(MediaEmbed mediaEmbed) {
        this.mediaEmbed = mediaEmbed;
    }

    public Object getAuthorFlairTemplateId() {
        return authorFlairTemplateId;
    }

    public void setAuthorFlairTemplateId(Object authorFlairTemplateId) {
        this.authorFlairTemplateId = authorFlairTemplateId;
    }

    public Boolean getIsOriginalContent() {
        return isOriginalContent;
    }

    public void setIsOriginalContent(Boolean isOriginalContent) {
        this.isOriginalContent = isOriginalContent;
    }

    public Object getSecureMedia() {
        return secureMedia;
    }

    public void setSecureMedia(Object secureMedia) {
        this.secureMedia = secureMedia;
    }

    public Boolean getIsRedditMediaDomain() {
        return isRedditMediaDomain;
    }

    public void setIsRedditMediaDomain(Boolean isRedditMediaDomain) {
        this.isRedditMediaDomain = isRedditMediaDomain;
    }

    public Object getCategory() {
        return category;
    }

    public void setCategory(Object category) {
        this.category = category;
    }

    public SecureMediaEmbed getSecureMediaEmbed() {
        return secureMediaEmbed;
    }

    public void setSecureMediaEmbed(SecureMediaEmbed secureMediaEmbed) {
        this.secureMediaEmbed = secureMediaEmbed;
    }

    public Object getLinkFlairText() {
        return linkFlairText;
    }

    public void setLinkFlairText(Object linkFlairText) {
        this.linkFlairText = linkFlairText;
    }

    public Boolean getCanModPost() {
        return canModPost;
    }

    public void setCanModPost(Boolean canModPost) {
        this.canModPost = canModPost;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Object getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(Object approvedBy) {
        this.approvedBy = approvedBy;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public Object getEdited() {
        return edited;
    }

    public void setEdited(Object edited) {
        this.edited = edited;
    }

    public Object getAuthorFlairCssClass() {
        return authorFlairCssClass;
    }

    public void setAuthorFlairCssClass(Object authorFlairCssClass) {
        this.authorFlairCssClass = authorFlairCssClass;
    }

    public List<Object> getAuthorFlairRichtext() {
        return authorFlairRichtext;
    }

    public void setAuthorFlairRichtext(List<Object> authorFlairRichtext) {
        this.authorFlairRichtext = authorFlairRichtext;
    }

    public Object getContentCategories() {
        return contentCategories;
    }

    public void setContentCategories(Object contentCategories) {
        this.contentCategories = contentCategories;
    }

    public Boolean getIsSelf() {
        return isSelf;
    }

    public void setIsSelf(Boolean isSelf) {
        this.isSelf = isSelf;
    }

    public Object getModNote() {
        return modNote;
    }

    public void setModNote(Object modNote) {
        this.modNote = modNote;
    }

    public Integer getCreated() {
        return created;
    }

    public void setCreated(Integer created) {
        this.created = created;
    }

    public String getLinkFlairType() {
        return linkFlairType;
    }

    public void setLinkFlairType(String linkFlairType) {
        this.linkFlairType = linkFlairType;
    }

    public Integer getWls() {
        return wls;
    }

    public void setWls(Integer wls) {
        this.wls = wls;
    }

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public Object getPostCategories() {
        return postCategories;
    }

    public void setPostCategories(Object postCategories) {
        this.postCategories = postCategories;
    }

    public Object getBannedBy() {
        return bannedBy;
    }

    public void setBannedBy(Object bannedBy) {
        this.bannedBy = bannedBy;
    }

    public String getAuthorFlairType() {
        return authorFlairType;
    }

    public void setAuthorFlairType(String authorFlairType) {
        this.authorFlairType = authorFlairType;
    }

    public Boolean getContestMode() {
        return contestMode;
    }

    public void setContestMode(Boolean contestMode) {
        this.contestMode = contestMode;
    }

    public Object getSelftextHtml() {
        return selftextHtml;
    }

    public void setSelftextHtml(Object selftextHtml) {
        this.selftextHtml = selftextHtml;
    }

    public Object getLikes() {
        return likes;
    }

    public void setLikes(Object likes) {
        this.likes = likes;
    }

    public Object getSuggestedSort() {
        return suggestedSort;
    }

    public void setSuggestedSort(Object suggestedSort) {
        this.suggestedSort = suggestedSort;
    }

    public Object getBannedAtUtc() {
        return bannedAtUtc;
    }

    public void setBannedAtUtc(Object bannedAtUtc) {
        this.bannedAtUtc = bannedAtUtc;
    }

    public Object getViewCount() {
        return viewCount;
    }

    public void setViewCount(Object viewCount) {
        this.viewCount = viewCount;
    }

    public Boolean getArchived() {
        return archived;
    }

    public void setArchived(Boolean archived) {
        this.archived = archived;
    }

    public Boolean getNoFollow() {
        return noFollow;
    }

    public void setNoFollow(Boolean noFollow) {
        this.noFollow = noFollow;
    }

    public Boolean getIsCrosspostable() {
        return isCrosspostable;
    }

    public void setIsCrosspostable(Boolean isCrosspostable) {
        this.isCrosspostable = isCrosspostable;
    }

    public Boolean getPinned() {
        return pinned;
    }

    public void setPinned(Boolean pinned) {
        this.pinned = pinned;
    }

    public Boolean getOver18() {
        return over18;
    }

    public void setOver18(Boolean over18) {
        this.over18 = over18;
    }

    public Media getMedia() {
        return media;
    }

    public void setMedia(Media media) {
        this.media = media;
    }

    public Boolean getMediaOnly() {
        return mediaOnly;
    }

    public void setMediaOnly(Boolean mediaOnly) {
        this.mediaOnly = mediaOnly;
    }

    public Boolean getCanGild() {
        return canGild;
    }

    public void setCanGild(Boolean canGild) {
        this.canGild = canGild;
    }

    public Boolean getSpoiler() {
        return spoiler;
    }

    public void setSpoiler(Boolean spoiler) {
        this.spoiler = spoiler;
    }

    public Boolean getLocked() {
        return locked;
    }

    public void setLocked(Boolean locked) {
        this.locked = locked;
    }

    public Object getAuthorFlairText() {
        return authorFlairText;
    }

    public void setAuthorFlairText(Object authorFlairText) {
        this.authorFlairText = authorFlairText;
    }

    public String getRteMode() {
        return rteMode;
    }

    public void setRteMode(String rteMode) {
        this.rteMode = rteMode;
    }

    public Boolean getVisited() {
        return visited;
    }

    public void setVisited(Boolean visited) {
        this.visited = visited;
    }

    public Object getNumReports() {
        return numReports;
    }

    public void setNumReports(Object numReports) {
        this.numReports = numReports;
    }

    public Object getDistinguished() {
        return distinguished;
    }

    public void setDistinguished(Object distinguished) {
        this.distinguished = distinguished;
    }

    public String getSubredditId() {
        return subredditId;
    }

    public void setSubredditId(String subredditId) {
        this.subredditId = subredditId;
    }

    public Object getModReasonBy() {
        return modReasonBy;
    }

    public void setModReasonBy(Object modReasonBy) {
        this.modReasonBy = modReasonBy;
    }

    public Object getRemovalReason() {
        return removalReason;
    }

    public void setRemovalReason(Object removalReason) {
        this.removalReason = removalReason;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Object getReportReasons() {
        return reportReasons;
    }

    public void setReportReasons(Object reportReasons) {
        this.reportReasons = reportReasons;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Integer getNumCrossposts() {
        return numCrossposts;
    }

    public void setNumCrossposts(Integer numCrossposts) {
        this.numCrossposts = numCrossposts;
    }

    public Integer getNumComments() {
        return numComments;
    }

    public void setNumComments(Integer numComments) {
        this.numComments = numComments;
    }

    public Boolean getSendReplies() {
        return sendReplies;
    }

    public void setSendReplies(Boolean sendReplies) {
        this.sendReplies = sendReplies;
    }

    public Object getAuthorFlairTextColor() {
        return authorFlairTextColor;
    }

    public void setAuthorFlairTextColor(Object authorFlairTextColor) {
        this.authorFlairTextColor = authorFlairTextColor;
    }

    public String getPermalink() {
        return permalink;
    }

    public void setPermalink(String permalink) {
        this.permalink = permalink;
    }

    public String getWhitelistStatus() {
        return whitelistStatus;
    }

    public void setWhitelistStatus(String whitelistStatus) {
        this.whitelistStatus = whitelistStatus;
    }

    public Boolean getStickied() {
        return stickied;
    }

    public void setStickied(Boolean stickied) {
        this.stickied = stickied;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getSubredditSubscribers() {
        return subredditSubscribers;
    }

    public void setSubredditSubscribers(Integer subredditSubscribers) {
        this.subredditSubscribers = subredditSubscribers;
    }

    public Integer getCreatedUtc() {
        return createdUtc;
    }

    public void setCreatedUtc(Integer createdUtc) {
        this.createdUtc = createdUtc;
    }

    public List<Object> getModReports() {
        return modReports;
    }

    public void setModReports(List<Object> modReports) {
        this.modReports = modReports;
    }

    public Boolean getIsVideo() {
        return isVideo;
    }

    public void setIsVideo(Boolean isVideo) {
        this.isVideo = isVideo;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(approvedAtUtc);
        dest.writeValue(subreddit);
        dest.writeValue(selftext);
        dest.writeList(userReports);
        dest.writeValue(saved);
        dest.writeValue(modReasonTitle);
        dest.writeValue(gilded);
        dest.writeValue(clicked);
        dest.writeValue(title);
        dest.writeList(linkFlairRichtext);
        dest.writeValue(subredditNamePrefixed);
        dest.writeValue(hidden);
        dest.writeValue(preview);
        dest.writeValue(thumbnail);
        dest.writeValue(thumbnailWidth);
        dest.writeValue(thumbnailHeight);
        dest.writeValue(pwls);
        dest.writeValue(linkFlairCssClass);
        dest.writeValue(downs);
        dest.writeValue(parentWhitelistStatus);
        dest.writeValue(hideScore);
        dest.writeValue(name);
        dest.writeValue(quarantine);
        dest.writeValue(linkFlairTextColor);
        dest.writeValue(authorFlairBackgroundColor);
        dest.writeValue(subredditType);
        dest.writeValue(ups);
        dest.writeValue(domain);
        dest.writeValue(mediaEmbed);
        dest.writeValue(authorFlairTemplateId);
        dest.writeValue(isOriginalContent);
        dest.writeValue(secureMedia);
        dest.writeValue(isRedditMediaDomain);
        dest.writeValue(category);
        dest.writeValue(secureMediaEmbed);
        dest.writeValue(linkFlairText);
        dest.writeValue(canModPost);
        dest.writeValue(score);
        dest.writeValue(approvedBy);
        dest.writeValue(thumbnail);
        dest.writeValue(edited);
        dest.writeValue(authorFlairCssClass);
        dest.writeList(authorFlairRichtext);
        dest.writeValue(contentCategories);
        dest.writeValue(isSelf);
        dest.writeValue(modNote);
        dest.writeValue(created);
        dest.writeValue(linkFlairType);
        dest.writeValue(wls);
        dest.writeValue(authorId);
        dest.writeValue(postCategories);
        dest.writeValue(bannedBy);
        dest.writeValue(authorFlairType);
        dest.writeValue(contestMode);
        dest.writeValue(selftextHtml);
        dest.writeValue(likes);
        dest.writeValue(suggestedSort);
        dest.writeValue(bannedAtUtc);
        dest.writeValue(viewCount);
        dest.writeValue(archived);
        dest.writeValue(noFollow);
        dest.writeValue(isCrosspostable);
        dest.writeValue(pinned);
        dest.writeValue(over18);
        dest.writeValue(media);
        dest.writeValue(mediaOnly);
        dest.writeValue(canGild);
        dest.writeValue(spoiler);
        dest.writeValue(locked);
        dest.writeValue(authorFlairText);
        dest.writeValue(rteMode);
        dest.writeValue(visited);
        dest.writeValue(numReports);
        dest.writeValue(distinguished);
        dest.writeValue(subredditId);
        dest.writeValue(modReasonBy);
        dest.writeValue(removalReason);
        dest.writeValue(id);
        dest.writeValue(reportReasons);
        dest.writeValue(author);
        dest.writeValue(numCrossposts);
        dest.writeValue(numComments);
        dest.writeValue(sendReplies);
        dest.writeValue(authorFlairTextColor);
        dest.writeValue(permalink);
        dest.writeValue(whitelistStatus);
        dest.writeValue(stickied);
        dest.writeValue(url);
        dest.writeValue(subredditSubscribers);
        dest.writeValue(createdUtc);
        dest.writeList(modReports);
        dest.writeValue(isVideo);
    }

    public int describeContents() {
        return 0;
    }

}
