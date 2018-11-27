package info.pelleritoudacity.android.rcapstone.data.db.entry;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;


import info.pelleritoudacity.android.rcapstone.data.db.util.DateConverter;

@Entity(tableName = "_t1")
public class T1Entry {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @TypeConverters(DateConverter.class)
    @ColumnInfo(name = "_time_last_modified")
    private long timeLastModified;

    @ColumnInfo(name = "_more_replies")
    private int moreReplies;

    @ColumnInfo(name = "_more_level_replies")
    private int moreLevelReplies;

    @ColumnInfo(name = "_is_crosspostable")
    private int isCrosspostable;

    @ColumnInfo(name = "_subbreddit")
    private String subreddit;

    @ColumnInfo(name = "_subreddit_id")
    private String subredditId;

    @TypeConverters(DateConverter.class)
    @ColumnInfo(name = "_approved_at_utc")
    private Long approvedAtUtc;

    @ColumnInfo(name = "_wls")
    private int wls;

    @ColumnInfo(name = "_mod_reason_by")
    private String modReasonBy;

    @ColumnInfo(name = "_banned_by")
    private String bannedBy;

    @ColumnInfo(name = "_num_reports")
    private String numReports;

    @ColumnInfo(name = "_link_id")
    private String linkId;

    @ColumnInfo(name = "_removal_reason")
    private int removalReason;

    @ColumnInfo(name = "_thumbnail")
    private String thumbnail;

    @ColumnInfo(name = "_thumbnail_width")
    private String thumbnailWidth;

    @ColumnInfo(name = "_thumbnail_height")
    private String thumbnailHeight;

    @ColumnInfo(name = "_self_text_html")
    private String selfTextHtml;

    @ColumnInfo(name = "_parent_id")
    private String parentId;

    @ColumnInfo(name = "_likes")
    private String likes;

    @ColumnInfo(name = "_suggested_sort")
    private String suggestedSort;

    @ColumnInfo(name = "_user_reports")
    private String userReports;

    @ColumnInfo(name = "_secure_media")
    private String secureMedia;

    @ColumnInfo(name = "_is_reddit_media_domain")
    private int isRedditMediaDomain;

    @ColumnInfo(name = "_saved")
    private int saved;

    @ColumnInfo(name = "_name_id")
    private String nameId;

    @ColumnInfo(name = "_children_id")
    private String childrenId;

    @TypeConverters(DateConverter.class)
    @ColumnInfo(name = "_banned_at_utc")
    private long bannedAtUtc;

    @ColumnInfo(name = "_mod_reason_title")
    private String modReasonTitle;

    @ColumnInfo(name = "_view_count")
    private String viewCount;

    @ColumnInfo(name = "_archived")
    private int archived;

    @ColumnInfo(name = "_clicked")
    private int clicked;

    @ColumnInfo(name = "_no_follow")
    private int noFollow;

    @ColumnInfo(name = "_author")
    private String author;

    @ColumnInfo(name = "_num_cross_posts")
    private int numCrossPosts;

    @ColumnInfo(name = "_link_flair_text")
    private String linkFlairText;

    @ColumnInfo(name = "_can_mod_post")
    private int canModPost;

    @ColumnInfo(name = "_send_replies")
    private int sendReplies;

    @ColumnInfo(name = "_pinned")
    private int pinned;

    @ColumnInfo(name = "_dir_score")
    private int dirScore;

    @ColumnInfo(name = "_score")
    private int score;

    @ColumnInfo(name = "_approved_by")
    private String approvedBy;

    @ColumnInfo(name = "_over18")
    private int over18;

    @ColumnInfo(name = "_report_reason")
    private int reportReason;

    @ColumnInfo(name = "_domain")
    private String domain;

    @ColumnInfo(name = "_hidden")
    private int hidden;

    @ColumnInfo(name = "_preview")
    private String preview;

    @ColumnInfo(name = "_pwls")
    private int pwls;

    @ColumnInfo(name = "_edited")
    private int edited;

    @ColumnInfo(name = "_link_flair_css_class")
    private String linkFlairCssClass;

    @ColumnInfo(name = "_contest_mode")
    private int contestMode;

    @ColumnInfo(name = "_gilded")
    private int gilded;

    @ColumnInfo(name = "_locked")
    private int locked;

    @ColumnInfo(name = "_downs")
    private int downs;

    @ColumnInfo(name = "_body")
    private String body;

    @ColumnInfo(name = "_mod_reports")
    private String modReports;

    @ColumnInfo(name = "_sub_reddit_subscribers")
    private String subRedditSubscribers;

    @ColumnInfo(name = "_secure_media_embed")
    private String secureMediaEmbed;

    @ColumnInfo(name = "_body_html")
    private String bodyHtml;

    @ColumnInfo(name = "_stickied")
    private int stickied;

    @ColumnInfo(name = "_visited")
    private int visited;

    @ColumnInfo(name = "_can_gild")
    private int canGild;

    @ColumnInfo(name = "_name")
    private String name;

    @ColumnInfo(name = "_permalink")
    private String permalink;

    @ColumnInfo(name = "_subreddit_type")
    private String subredditType;

    @ColumnInfo(name = "_parent_whitelist_status")
    private String parentWhitelistStatus;

    @ColumnInfo(name = "_hide_score")
    private int hideScore;

    @ColumnInfo(name = "_created")
    private Long created;

    @ColumnInfo(name = "_url")
    private String url;

    @ColumnInfo(name = "_author_flair_text")
    private String authorFlairText;

    @ColumnInfo(name = "_quarantine")
    private int quarantine;

    @ColumnInfo(name = "_depth")
    private int depth;

    @ColumnInfo(name = "_title")
    private String title;

    @TypeConverters(DateConverter.class)
    @ColumnInfo(name = "_created_utc")
    private Long createdUtc;

    @ColumnInfo(name = "_ups")
    private int ups;

    @ColumnInfo(name = "_num_comments")
    private int numComments;

    @ColumnInfo(name = "_more_comments")
    private String moreComments;

    @ColumnInfo(name = "_is_self")
    private int isSelf;

    @ColumnInfo(name = "_whitelist_status")
    private String whitelistStatus;

    @ColumnInfo(name = "_distinguished")
    private String distinguished;

    @ColumnInfo(name = "_mod_note")
    private String modNote;

    @ColumnInfo(name = "_media")
    private String media;

    @ColumnInfo(name = "_is_video")
    private int isVideo;

    @ColumnInfo(name = "_self_text")
    private String selfText;

    @ColumnInfo(name = "_author_flair_css_class")
    private String authorFlairCssClass;

    @ColumnInfo(name = "_sort_by")
    private String sortBy;

    public T1Entry() {
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

    public int getMoreReplies() {
        return moreReplies;
    }

    public void setMoreReplies(int moreReplies) {
        this.moreReplies = moreReplies;
    }

    public int getMoreLevelReplies() {
        return moreLevelReplies;
    }

    public void setMoreLevelReplies(int moreLevelReplies) {
        this.moreLevelReplies = moreLevelReplies;
    }

    public int getIsCrosspostable() {
        return isCrosspostable;
    }

    public void setIsCrosspostable(int isCrosspostable) {
        this.isCrosspostable = isCrosspostable;
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

    public Long getApprovedAtUtc() {
        return approvedAtUtc;
    }

    public void setApprovedAtUtc(Long approvedAtUtc) {
        this.approvedAtUtc = approvedAtUtc;
    }

    public int getWls() {
        return wls;
    }

    public void setWls(int wls) {
        this.wls = wls;
    }

    public String getModReasonBy() {
        return modReasonBy;
    }

    public void setModReasonBy(String modReasonBy) {
        this.modReasonBy = modReasonBy;
    }

    public String getBannedBy() {
        return bannedBy;
    }

    public void setBannedBy(String bannedBy) {
        this.bannedBy = bannedBy;
    }

    public String getNumReports() {
        return numReports;
    }

    public void setNumReports(String numReports) {
        this.numReports = numReports;
    }

    public String getLinkId() {
        return linkId;
    }

    public void setLinkId(String linkId) {
        this.linkId = linkId;
    }

    public int getRemovalReason() {
        return removalReason;
    }

    public void setRemovalReason(int removalReason) {
        this.removalReason = removalReason;
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

    public String getSelfTextHtml() {
        return selfTextHtml;
    }

    public void setSelfTextHtml(String selfTextHtml) {
        this.selfTextHtml = selfTextHtml;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getLikes() {
        return likes;
    }

    public void setLikes(String likes) {
        this.likes = likes;
    }

    public String getSuggestedSort() {
        return suggestedSort;
    }

    public void setSuggestedSort(String suggestedSort) {
        this.suggestedSort = suggestedSort;
    }

    public String getUserReports() {
        return userReports;
    }

    public void setUserReports(String userReports) {
        this.userReports = userReports;
    }

    public String getSecureMedia() {
        return secureMedia;
    }

    public void setSecureMedia(String secureMedia) {
        this.secureMedia = secureMedia;
    }

    public int getIsRedditMediaDomain() {
        return isRedditMediaDomain;
    }

    public void setIsRedditMediaDomain(int isRedditMediaDomain) {
        this.isRedditMediaDomain = isRedditMediaDomain;
    }

    public int getSaved() {
        return saved;
    }

    public void setSaved(int saved) {
        this.saved = saved;
    }

    public String getNameId() {
        return nameId;
    }

    public void setNameId(String nameId) {
        this.nameId = nameId;
    }

    public String getChildrenId() {
        return childrenId;
    }

    public void setChildrenId(String childrenId) {
        this.childrenId = childrenId;
    }

    public long getBannedAtUtc() {
        return bannedAtUtc;
    }

    public void setBannedAtUtc(long bannedAtUtc) {
        this.bannedAtUtc = bannedAtUtc;
    }

    public String getModReasonTitle() {
        return modReasonTitle;
    }

    public void setModReasonTitle(String modReasonTitle) {
        this.modReasonTitle = modReasonTitle;
    }

    public String getViewCount() {
        return viewCount;
    }

    public void setViewCount(String viewCount) {
        this.viewCount = viewCount;
    }

    public int getArchived() {
        return archived;
    }

    public void setArchived(int archived) {
        this.archived = archived;
    }

    public int getClicked() {
        return clicked;
    }

    public void setClicked(int clicked) {
        this.clicked = clicked;
    }

    public int getNoFollow() {
        return noFollow;
    }

    public void setNoFollow(int noFollow) {
        this.noFollow = noFollow;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getNumCrossPosts() {
        return numCrossPosts;
    }

    public void setNumCrossPosts(int numCrossPosts) {
        this.numCrossPosts = numCrossPosts;
    }

    public String getLinkFlairText() {
        return linkFlairText;
    }

    public void setLinkFlairText(String linkFlairText) {
        this.linkFlairText = linkFlairText;
    }

    public int getCanModPost() {
        return canModPost;
    }

    public void setCanModPost(int canModPost) {
        this.canModPost = canModPost;
    }

    public int getSendReplies() {
        return sendReplies;
    }

    public void setSendReplies(int sendReplies) {
        this.sendReplies = sendReplies;
    }

    public int getPinned() {
        return pinned;
    }

    public void setPinned(int pinned) {
        this.pinned = pinned;
    }

    public int getDirScore() {
        return dirScore;
    }

    public void setDirScore(int dirScore) {
        this.dirScore = dirScore;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(String approvedBy) {
        this.approvedBy = approvedBy;
    }

    public int getOver18() {
        return over18;
    }

    public void setOver18(int over18) {
        this.over18 = over18;
    }

    public int getReportReason() {
        return reportReason;
    }

    public void setReportReason(int reportReason) {
        this.reportReason = reportReason;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public int getHidden() {
        return hidden;
    }

    public void setHidden(int hidden) {
        this.hidden = hidden;
    }

    public String getPreview() {
        return preview;
    }

    public void setPreview(String preview) {
        this.preview = preview;
    }

    public int getPwls() {
        return pwls;
    }

    public void setPwls(int pwls) {
        this.pwls = pwls;
    }

    public int getEdited() {
        return edited;
    }

    public void setEdited(int edited) {
        this.edited = edited;
    }

    public String getLinkFlairCssClass() {
        return linkFlairCssClass;
    }

    public void setLinkFlairCssClass(String linkFlairCssClass) {
        this.linkFlairCssClass = linkFlairCssClass;
    }

    public int getContestMode() {
        return contestMode;
    }

    public void setContestMode(int contestMode) {
        this.contestMode = contestMode;
    }

    public int getGilded() {
        return gilded;
    }

    public void setGilded(int gilded) {
        this.gilded = gilded;
    }

    public int getLocked() {
        return locked;
    }

    public void setLocked(int locked) {
        this.locked = locked;
    }

    public int getDowns() {
        return downs;
    }

    public void setDowns(int downs) {
        this.downs = downs;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getModReports() {
        return modReports;
    }

    public void setModReports(String modReports) {
        this.modReports = modReports;
    }

    public String getSubRedditSubscribers() {
        return subRedditSubscribers;
    }

    public void setSubRedditSubscribers(String subRedditSubscribers) {
        this.subRedditSubscribers = subRedditSubscribers;
    }

    public String getSecureMediaEmbed() {
        return secureMediaEmbed;
    }

    public void setSecureMediaEmbed(String secureMediaEmbed) {
        this.secureMediaEmbed = secureMediaEmbed;
    }

    public String getBodyHtml() {
        return bodyHtml;
    }

    public void setBodyHtml(String bodyHtml) {
        this.bodyHtml = bodyHtml;
    }

    public int getStickied() {
        return stickied;
    }

    public void setStickied(int stickied) {
        this.stickied = stickied;
    }

    public int getVisited() {
        return visited;
    }

    public void setVisited(int visited) {
        this.visited = visited;
    }

    public int getCanGild() {
        return canGild;
    }

    public void setCanGild(int canGild) {
        this.canGild = canGild;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPermalink() {
        return permalink;
    }

    public void setPermalink(String permalink) {
        this.permalink = permalink;
    }

    public String getSubredditType() {
        return subredditType;
    }

    public void setSubredditType(String subredditType) {
        this.subredditType = subredditType;
    }

    public String getParentWhitelistStatus() {
        return parentWhitelistStatus;
    }

    public void setParentWhitelistStatus(String parentWhitelistStatus) {
        this.parentWhitelistStatus = parentWhitelistStatus;
    }

    public int getHideScore() {
        return hideScore;
    }

    public void setHideScore(int hideScore) {
        this.hideScore = hideScore;
    }

    public Long getCreated() {
        return created;
    }

    public void setCreated(Long created) {
        this.created = created;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAuthorFlairText() {
        return authorFlairText;
    }

    public void setAuthorFlairText(String authorFlairText) {
        this.authorFlairText = authorFlairText;
    }

    public int getQuarantine() {
        return quarantine;
    }

    public void setQuarantine(int quarantine) {
        this.quarantine = quarantine;
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getCreatedUtc() {
        return createdUtc;
    }

    public void setCreatedUtc(Long createdUtc) {
        this.createdUtc = createdUtc;
    }

    public int getUps() {
        return ups;
    }

    public void setUps(int ups) {
        this.ups = ups;
    }

    public int getNumComments() {
        return numComments;
    }

    public void setNumComments(int numComments) {
        this.numComments = numComments;
    }

    public String getMoreComments() {
        return moreComments;
    }

    public void setMoreComments(String moreComments) {
        this.moreComments = moreComments;
    }

    public int getIsSelf() {
        return isSelf;
    }

    public void setIsSelf(int isSelf) {
        this.isSelf = isSelf;
    }

    public String getWhitelistStatus() {
        return whitelistStatus;
    }

    public void setWhitelistStatus(String whitelistStatus) {
        this.whitelistStatus = whitelistStatus;
    }

    public String getDistinguished() {
        return distinguished;
    }

    public void setDistinguished(String distinguished) {
        this.distinguished = distinguished;
    }

    public String getModNote() {
        return modNote;
    }

    public void setModNote(String modNote) {
        this.modNote = modNote;
    }

    public String getMedia() {
        return media;
    }

    public void setMedia(String media) {
        this.media = media;
    }

    public int getIsVideo() {
        return isVideo;
    }

    public void setIsVideo(int isVideo) {
        this.isVideo = isVideo;
    }

    public String getSelfText() {
        return selfText;
    }

    public void setSelfText(String selfText) {
        this.selfText = selfText;
    }

    public String getAuthorFlairCssClass() {
        return authorFlairCssClass;
    }

    public void setAuthorFlairCssClass(String authorFlairCssClass) {
        this.authorFlairCssClass = authorFlairCssClass;
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }
}