package info.pelleritoudacity.android.rcapstone.data.model.reddit;

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
    @SuppressWarnings("SpellCheckingInspection")
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

    T3ListingData(Parcel in) {
        this.approvedAtUtc = in.readValue((Object.class.getClassLoader()));
        this.subreddit = ((String) in.readValue((String.class.getClassLoader())));
        this.selftext = ((String) in.readValue((String.class.getClassLoader())));
        in.readList(this.userReports, (java.lang.Object.class.getClassLoader()));
        this.saved = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.modReasonTitle = in.readValue((Object.class.getClassLoader()));
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
        this.linkFlairCssClass = in.readValue((Object.class.getClassLoader()));
        this.downs = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.parentWhitelistStatus = ((String) in.readValue((String.class.getClassLoader())));
        this.hideScore = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.name = ((String) in.readValue((String.class.getClassLoader())));
        this.quarantine = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.linkFlairTextColor = ((String) in.readValue((String.class.getClassLoader())));
        this.authorFlairBackgroundColor = in.readValue((Object.class.getClassLoader()));
        this.subredditType = ((String) in.readValue((String.class.getClassLoader())));
        this.ups = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.domain = ((String) in.readValue((String.class.getClassLoader())));
        this.mediaEmbed = ((MediaEmbed) in.readValue((MediaEmbed.class.getClassLoader())));
        this.authorFlairTemplateId = in.readValue((Object.class.getClassLoader()));
        this.isOriginalContent = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.secureMedia = in.readValue((Object.class.getClassLoader()));
        this.isRedditMediaDomain = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.category = in.readValue((Object.class.getClassLoader()));
        this.secureMediaEmbed = ((SecureMediaEmbed) in.readValue((SecureMediaEmbed.class.getClassLoader())));
        this.linkFlairText = in.readValue((Object.class.getClassLoader()));
        this.canModPost = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.score = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.approvedBy = in.readValue((Object.class.getClassLoader()));
        this.thumbnail = ((String) in.readValue((String.class.getClassLoader())));
        this.edited = in.readValue((Integer.class.getClassLoader()));
        this.authorFlairCssClass = in.readValue((Object.class.getClassLoader()));
        in.readList(this.authorFlairRichtext, (java.lang.Object.class.getClassLoader()));
        this.contentCategories = in.readValue((Object.class.getClassLoader()));
        this.isSelf = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.modNote = in.readValue((Object.class.getClassLoader()));
        this.created = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.linkFlairType = ((String) in.readValue((String.class.getClassLoader())));
        this.wls = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.authorId = ((String) in.readValue((String.class.getClassLoader())));
        this.postCategories = in.readValue((Object.class.getClassLoader()));
        this.bannedBy = in.readValue((Object.class.getClassLoader()));
        this.authorFlairType = ((String) in.readValue((String.class.getClassLoader())));
        this.contestMode = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.selftextHtml = in.readValue((Object.class.getClassLoader()));
        this.likes = in.readValue((Object.class.getClassLoader()));
        this.suggestedSort = in.readValue((Object.class.getClassLoader()));
        this.bannedAtUtc = in.readValue((Object.class.getClassLoader()));
        this.viewCount = in.readValue((Object.class.getClassLoader()));
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
        this.authorFlairText = in.readValue((Object.class.getClassLoader()));
        this.rteMode = ((String) in.readValue((String.class.getClassLoader())));
        this.visited = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.numReports = in.readValue((Object.class.getClassLoader()));
        this.distinguished = in.readValue((Object.class.getClassLoader()));
        this.subredditId = ((String) in.readValue((String.class.getClassLoader())));
        this.modReasonBy = in.readValue((Object.class.getClassLoader()));
        this.removalReason = in.readValue((Object.class.getClassLoader()));
        this.id = ((String) in.readValue((String.class.getClassLoader())));
        this.reportReasons = in.readValue((Object.class.getClassLoader()));
        this.author = ((String) in.readValue((String.class.getClassLoader())));
        this.numCrossposts = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.numComments = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.sendReplies = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.authorFlairTextColor = in.readValue((Object.class.getClassLoader()));
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

    @SuppressWarnings("unused")
    public void setApprovedAtUtc(Object approvedAtUtc) {
        this.approvedAtUtc = approvedAtUtc;
    }

    public String getSubreddit() {
        return subreddit;
    }

    @SuppressWarnings("unused")
    public void setSubreddit(String subreddit) {
        this.subreddit = subreddit;
    }

    public String getSelftext() {
        return selftext;
    }

    @SuppressWarnings("unused")
    public void setSelftext(String selftext) {
        this.selftext = selftext;
    }

    @SuppressWarnings("unused")
    public List<Object> getUserReports() {
        return userReports;
    }

    @SuppressWarnings("unused")
    public void setUserReports(List<Object> userReports) {
        this.userReports = userReports;
    }

    public Boolean getSaved() {
        return saved;
    }

    @SuppressWarnings("unused")
    public void setSaved(Boolean saved) {
        this.saved = saved;
    }

    public Object getModReasonTitle() {
        return modReasonTitle;
    }

    @SuppressWarnings("unused")
    public void setModReasonTitle(Object modReasonTitle) {
        this.modReasonTitle = modReasonTitle;
    }

    public Integer getGilded() {
        return gilded;
    }

    @SuppressWarnings("unused")
    public void setGilded(Integer gilded) {
        this.gilded = gilded;
    }

    public Boolean getClicked() {
        return clicked;
    }

    @SuppressWarnings("unused")
    public void setClicked(Boolean clicked) {
        this.clicked = clicked;
    }

    public String getTitle() {
        return title;
    }

    @SuppressWarnings("unused")
    public void setTitle(String title) {
        this.title = title;
    }

    @SuppressWarnings("unused")
    public List<Object> getLinkFlairRichtext() {
        return linkFlairRichtext;
    }

    @SuppressWarnings("unused")
    public void setLinkFlairRichtext(List<Object> linkFlairRichtext) {
        this.linkFlairRichtext = linkFlairRichtext;
    }

    public String getSubredditNamePrefixed() {
        return subredditNamePrefixed;
    }

    @SuppressWarnings("unused")
    public void setSubredditNamePrefixed(String subredditNamePrefixed) {
        this.subredditNamePrefixed = subredditNamePrefixed;
    }

    public Boolean getHidden() {
        return hidden;
    }

    @SuppressWarnings("unused")
    public void setHidden(Boolean hidden) {
        this.hidden = hidden;
    }

    public Preview getPreview() {
        return preview;
    }

    @SuppressWarnings("unused")
    public void setPreview(Preview preview) {
        this.preview = preview;
    }

    public Integer getPwls() {
        return pwls;
    }

    @SuppressWarnings("unused")
    public void setPwls(Integer pwls) {
        this.pwls = pwls;
    }

    public Object getThumbnailWidth() {
        return thumbnailWidth;
    }

    @SuppressWarnings("unused")
    public void setThumbnailWidth(Object thumbnailWidth) {
        this.thumbnailWidth = thumbnailWidth;
    }

    public Object getThumbnailHeight() {
        return thumbnailHeight;
    }

    @SuppressWarnings("unused")
    public void setThumbnailHeight(Object thumbnailHeight) {
        this.thumbnailHeight = thumbnailHeight;
    }


    public Object getLinkFlairCssClass() {
        return linkFlairCssClass;
    }

    @SuppressWarnings("unused")
    public void setLinkFlairCssClass(Object linkFlairCssClass) {
        this.linkFlairCssClass = linkFlairCssClass;
    }

    public Integer getDowns() {
        return downs;
    }

    @SuppressWarnings("unused")
    public void setDowns(Integer downs) {
        this.downs = downs;
    }

    public String getParentWhitelistStatus() {
        return parentWhitelistStatus;
    }

    @SuppressWarnings("unused")
    public void setParentWhitelistStatus(String parentWhitelistStatus) {
        this.parentWhitelistStatus = parentWhitelistStatus;
    }

    public Boolean getHideScore() {
        return hideScore;
    }

    @SuppressWarnings("unused")
    public void setHideScore(Boolean hideScore) {
        this.hideScore = hideScore;
    }

    public String getName() {
        return name;
    }

    @SuppressWarnings("unused")
    public void setName(String name) {
        this.name = name;
    }

    public Boolean getQuarantine() {
        return quarantine;
    }

    @SuppressWarnings("unused")
    public void setQuarantine(Boolean quarantine) {
        this.quarantine = quarantine;
    }

    @SuppressWarnings("unused")
    public String getLinkFlairTextColor() {
        return linkFlairTextColor;
    }

    @SuppressWarnings("unused")
    public void setLinkFlairTextColor(String linkFlairTextColor) {
        this.linkFlairTextColor = linkFlairTextColor;
    }

    @SuppressWarnings("unused")
    public Object getAuthorFlairBackgroundColor() {
        return authorFlairBackgroundColor;
    }

    @SuppressWarnings("unused")
    public void setAuthorFlairBackgroundColor(Object authorFlairBackgroundColor) {
        this.authorFlairBackgroundColor = authorFlairBackgroundColor;
    }

    public String getSubredditType() {
        return subredditType;
    }

    @SuppressWarnings("unused")
    public void setSubredditType(String subredditType) {
        this.subredditType = subredditType;
    }

    public Integer getUps() {
        return ups;
    }

    @SuppressWarnings("unused")
    public void setUps(Integer ups) {
        this.ups = ups;
    }

    public String getDomain() {
        return domain;
    }

    @SuppressWarnings("unused")
    public void setDomain(String domain) {
        this.domain = domain;
    }

    @SuppressWarnings("unused")
    public MediaEmbed getMediaEmbed() {
        return mediaEmbed;
    }

    @SuppressWarnings("unused")
    public void setMediaEmbed(MediaEmbed mediaEmbed) {
        this.mediaEmbed = mediaEmbed;
    }

    @SuppressWarnings("unused")
    public Object getAuthorFlairTemplateId() {
        return authorFlairTemplateId;
    }

    @SuppressWarnings("unused")
    public void setAuthorFlairTemplateId(Object authorFlairTemplateId) {
        this.authorFlairTemplateId = authorFlairTemplateId;
    }

    @SuppressWarnings("unused")
    public Boolean getIsOriginalContent() {
        return isOriginalContent;
    }

    @SuppressWarnings("unused")
    public void setIsOriginalContent(Boolean isOriginalContent) {
        this.isOriginalContent = isOriginalContent;
    }

    @SuppressWarnings("unused")
    public Object getSecureMedia() {
        return secureMedia;
    }

    @SuppressWarnings("unused")
    public void setSecureMedia(Object secureMedia) {
        this.secureMedia = secureMedia;
    }

    public Boolean getIsRedditMediaDomain() {
        return isRedditMediaDomain;
    }

    @SuppressWarnings("unused")
    public void setIsRedditMediaDomain(Boolean isRedditMediaDomain) {
        this.isRedditMediaDomain = isRedditMediaDomain;
    }

    @SuppressWarnings("unused")
    public Object getCategory() {
        return category;
    }

    @SuppressWarnings("unused")
    public void setCategory(Object category) {
        this.category = category;
    }

    @SuppressWarnings("unused")
    public SecureMediaEmbed getSecureMediaEmbed() {
        return secureMediaEmbed;
    }

    @SuppressWarnings("unused")
    public void setSecureMediaEmbed(SecureMediaEmbed secureMediaEmbed) {
        this.secureMediaEmbed = secureMediaEmbed;
    }

    public Object getLinkFlairText() {
        return linkFlairText;
    }

    @SuppressWarnings("unused")
    public void setLinkFlairText(Object linkFlairText) {
        this.linkFlairText = linkFlairText;
    }

    public Boolean getCanModPost() {
        return canModPost;
    }

    @SuppressWarnings("unused")
    public void setCanModPost(Boolean canModPost) {
        this.canModPost = canModPost;
    }

    public Integer getScore() {
        return score;
    }

    @SuppressWarnings("unused")
    public void setScore(Integer score) {
        this.score = score;
    }

    public Object getApprovedBy() {
        return approvedBy;
    }

    @SuppressWarnings("unused")
    public void setApprovedBy(Object approvedBy) {
        this.approvedBy = approvedBy;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    @SuppressWarnings("unused")
    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public Object getEdited() {
        return edited;
    }

    @SuppressWarnings("unused")
    public void setEdited(Object edited) {
        this.edited = edited;
    }

    public Object getAuthorFlairCssClass() {
        return authorFlairCssClass;
    }

    @SuppressWarnings("unused")
    public void setAuthorFlairCssClass(Object authorFlairCssClass) {
        this.authorFlairCssClass = authorFlairCssClass;
    }

    @SuppressWarnings("unused")
    public List<Object> getAuthorFlairRichtext() {
        return authorFlairRichtext;
    }

    @SuppressWarnings("unused")
    public void setAuthorFlairRichtext(List<Object> authorFlairRichtext) {
        this.authorFlairRichtext = authorFlairRichtext;
    }

    @SuppressWarnings("unused")
    public Object getContentCategories() {
        return contentCategories;
    }

    @SuppressWarnings("unused")
    public void setContentCategories(Object contentCategories) {
        this.contentCategories = contentCategories;
    }

    public Boolean getIsSelf() {
        return isSelf;
    }

    @SuppressWarnings("unused")
    public void setIsSelf(Boolean isSelf) {
        this.isSelf = isSelf;
    }

    public Object getModNote() {
        return modNote;
    }

    @SuppressWarnings("unused")
    public void setModNote(Object modNote) {
        this.modNote = modNote;
    }

    public Integer getCreated() {
        return created;
    }

    @SuppressWarnings("unused")
    public void setCreated(Integer created) {
        this.created = created;
    }

    @SuppressWarnings("unused")
    public String getLinkFlairType() {
        return linkFlairType;
    }

    @SuppressWarnings("unused")
    public void setLinkFlairType(String linkFlairType) {
        this.linkFlairType = linkFlairType;
    }

    public Integer getWls() {
        return wls;
    }

    @SuppressWarnings("unused")
    public void setWls(Integer wls) {
        this.wls = wls;
    }

    @SuppressWarnings("unused")
    public String getAuthorId() {
        return authorId;
    }

    @SuppressWarnings("unused")
    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    @SuppressWarnings("unused")
    public Object getPostCategories() {
        return postCategories;
    }

    @SuppressWarnings("unused")
    public void setPostCategories(Object postCategories) {
        this.postCategories = postCategories;
    }

    public Object getBannedBy() {
        return bannedBy;
    }

    @SuppressWarnings("unused")
    public void setBannedBy(Object bannedBy) {
        this.bannedBy = bannedBy;
    }

    @SuppressWarnings("unused")
    public String getAuthorFlairType() {
        return authorFlairType;
    }

    @SuppressWarnings("unused")
    public void setAuthorFlairType(String authorFlairType) {
        this.authorFlairType = authorFlairType;
    }

    public Boolean getContestMode() {
        return contestMode;
    }

    @SuppressWarnings("unused")
    public void setContestMode(Boolean contestMode) {
        this.contestMode = contestMode;
    }

    public Object getSelftextHtml() {
        return selftextHtml;
    }

    @SuppressWarnings("unused")
    public void setSelftextHtml(Object selftextHtml) {
        this.selftextHtml = selftextHtml;
    }

    public Object getLikes() {
        return likes;
    }

    @SuppressWarnings("unused")
    public void setLikes(Object likes) {
        this.likes = likes;
    }

    public Object getSuggestedSort() {
        return suggestedSort;
    }

    @SuppressWarnings("unused")
    public void setSuggestedSort(Object suggestedSort) {
        this.suggestedSort = suggestedSort;
    }

    public Object getBannedAtUtc() {
        return bannedAtUtc;
    }

    @SuppressWarnings("unused")
    public void setBannedAtUtc(Object bannedAtUtc) {
        this.bannedAtUtc = bannedAtUtc;
    }

    public Object getViewCount() {
        return viewCount;
    }

    @SuppressWarnings("unused")
    public void setViewCount(Object viewCount) {
        this.viewCount = viewCount;
    }

    public Boolean getArchived() {
        return archived;
    }

    @SuppressWarnings("unused")
    public void setArchived(Boolean archived) {
        this.archived = archived;
    }

    public Boolean getNoFollow() {
        return noFollow;
    }

    @SuppressWarnings("unused")
    public void setNoFollow(Boolean noFollow) {
        this.noFollow = noFollow;
    }

    public Boolean getIsCrosspostable() {
        return isCrosspostable;
    }

    @SuppressWarnings("unused")
    public void setIsCrosspostable(Boolean isCrosspostable) {
        this.isCrosspostable = isCrosspostable;
    }

    public Boolean getPinned() {
        return pinned;
    }

    @SuppressWarnings("unused")
    public void setPinned(Boolean pinned) {
        this.pinned = pinned;
    }

    public Boolean getOver18() {
        return over18;
    }

    @SuppressWarnings("unused")
    public void setOver18(Boolean over18) {
        this.over18 = over18;
    }

    public Media getMedia() {
        return media;
    }

    @SuppressWarnings("unused")
    public void setMedia(Media media) {
        this.media = media;
    }

    @SuppressWarnings("unused")
    public Boolean getMediaOnly() {
        return mediaOnly;
    }

    @SuppressWarnings("unused")
    public void setMediaOnly(Boolean mediaOnly) {
        this.mediaOnly = mediaOnly;
    }

    public Boolean getCanGild() {
        return canGild;
    }

    @SuppressWarnings("unused")
    public void setCanGild(Boolean canGild) {
        this.canGild = canGild;
    }

    public Boolean getSpoiler() {
        return spoiler;
    }

    @SuppressWarnings("unused")
    public void setSpoiler(Boolean spoiler) {
        this.spoiler = spoiler;
    }

    public Boolean getLocked() {
        return locked;
    }

    @SuppressWarnings("unused")
    public void setLocked(Boolean locked) {
        this.locked = locked;
    }

    public Object getAuthorFlairText() {
        return authorFlairText;
    }

    @SuppressWarnings("unused")
    public void setAuthorFlairText(Object authorFlairText) {
        this.authorFlairText = authorFlairText;
    }

    @SuppressWarnings("unused")
    public String getRteMode() {
        return rteMode;
    }

    @SuppressWarnings("unused")
    public void setRteMode(String rteMode) {
        this.rteMode = rteMode;
    }

    public Boolean getVisited() {
        return visited;
    }

    @SuppressWarnings("unused")
    public void setVisited(Boolean visited) {
        this.visited = visited;
    }

    public Object getNumReports() {
        return numReports;
    }

    @SuppressWarnings("unused")
    public void setNumReports(Object numReports) {
        this.numReports = numReports;
    }

    public Object getDistinguished() {
        return distinguished;
    }

    @SuppressWarnings("unused")
    public void setDistinguished(Object distinguished) {
        this.distinguished = distinguished;
    }

    public String getSubredditId() {
        return subredditId;
    }

    @SuppressWarnings("unused")
    public void setSubredditId(String subredditId) {
        this.subredditId = subredditId;
    }

    public Object getModReasonBy() {
        return modReasonBy;
    }

    @SuppressWarnings("unused")
    public void setModReasonBy(Object modReasonBy) {
        this.modReasonBy = modReasonBy;
    }

    public Object getRemovalReason() {
        return removalReason;
    }

    @SuppressWarnings("unused")
    public void setRemovalReason(Object removalReason) {
        this.removalReason = removalReason;
    }

    public String getId() {
        return id;
    }

    @SuppressWarnings("unused")
    public void setId(String id) {
        this.id = id;
    }

    public Object getReportReasons() {
        return reportReasons;
    }

    @SuppressWarnings("unused")
    public void setReportReasons(Object reportReasons) {
        this.reportReasons = reportReasons;
    }

    public String getAuthor() {
        return author;
    }

    @SuppressWarnings("unused")
    public void setAuthor(String author) {
        this.author = author;
    }

    public Integer getNumCrossposts() {
        return numCrossposts;
    }

    @SuppressWarnings("unused")
    public void setNumCrossposts(Integer numCrossposts) {
        this.numCrossposts = numCrossposts;
    }

    public Integer getNumComments() {
        return numComments;
    }

    @SuppressWarnings("unused")
    public void setNumComments(Integer numComments) {
        this.numComments = numComments;
    }

    public Boolean getSendReplies() {
        return sendReplies;
    }

    @SuppressWarnings("unused")
    public void setSendReplies(Boolean sendReplies) {
        this.sendReplies = sendReplies;
    }

    @SuppressWarnings("unused")
    public Object getAuthorFlairTextColor() {
        return authorFlairTextColor;
    }

    @SuppressWarnings("unused")
    public void setAuthorFlairTextColor(Object authorFlairTextColor) {
        this.authorFlairTextColor = authorFlairTextColor;
    }

    public String getPermalink() {
        return permalink;
    }

    @SuppressWarnings("unused")
    public void setPermalink(String permalink) {
        this.permalink = permalink;
    }

    public String getWhitelistStatus() {
        return whitelistStatus;
    }

    @SuppressWarnings("unused")
    public void setWhitelistStatus(String whitelistStatus) {
        this.whitelistStatus = whitelistStatus;
    }

    public Boolean getStickied() {
        return stickied;
    }

    @SuppressWarnings("unused")
    public void setStickied(Boolean stickied) {
        this.stickied = stickied;
    }

    public String getUrl() {
        return url;
    }

    @SuppressWarnings("unused")
    public void setUrl(String url) {
        this.url = url;
    }

    @SuppressWarnings("unused")
    public Integer getSubredditSubscribers() {
        return subredditSubscribers;
    }

    @SuppressWarnings("unused")
    public void setSubredditSubscribers(Integer subredditSubscribers) {
        this.subredditSubscribers = subredditSubscribers;
    }

    public Integer getCreatedUtc() {
        return createdUtc;
    }

    @SuppressWarnings("unused")
    public void setCreatedUtc(Integer createdUtc) {
        this.createdUtc = createdUtc;
    }

    @SuppressWarnings("unused")
    public List<Object> getModReports() {
        return modReports;
    }

    @SuppressWarnings("unused")
    public void setModReports(List<Object> modReports) {
        this.modReports = modReports;
    }

    public Boolean getIsVideo() {
        return isVideo;
    }

    @SuppressWarnings("unused")
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
