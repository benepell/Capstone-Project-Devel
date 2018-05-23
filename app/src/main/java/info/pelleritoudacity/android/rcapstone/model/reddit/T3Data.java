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

package info.pelleritoudacity.android.rcapstone.model.reddit;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class T3Data  implements Parcelable
{

    @SerializedName("is_crosspostable")
    @Expose
    private Boolean isCrosspostable;
    @SerializedName("subreddit_id")
    @Expose
    private String subredditId;
    @SerializedName("approved_at_utc")
    @Expose
    private Object approvedAtUtc;
    @SerializedName("wls")
    @Expose
    private Integer wls;
    @SerializedName("mod_reason_by")
    @Expose
    private Object modReasonBy;
    @SerializedName("banned_by")
    @Expose
    private Object bannedBy;
    @SerializedName("num_reports")
    @Expose
    private Object numReports;
    @SerializedName("removal_reason")
    @Expose
    private Object removalReason;
    @SerializedName("thumbnail_width")
    @Expose
    private Object thumbnailWidth;
    @SerializedName("subreddit")
    @Expose
    private String subreddit;
    @SerializedName("selftext_html")
    @Expose
    private String selftextHtml;
    @SerializedName("selftext")
    @Expose
    private String selftext;
    @SerializedName("likes")
    @Expose
    private Object likes;
    @SerializedName("suggested_sort")
    @Expose
    private Object suggestedSort;
    @SerializedName("user_reports")
    @Expose
    private List<Object> userReports = null;
    @SerializedName("secure_media")
    @Expose
    private Object secureMedia;
    @SerializedName("is_reddit_media_domain")
    @Expose
    private Boolean isRedditMediaDomain;
    @SerializedName("saved")
    @Expose
    private Boolean saved;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("banned_at_utc")
    @Expose
    private Object bannedAtUtc;
    @SerializedName("mod_reason_title")
    @Expose
    private Object modReasonTitle;
    @SerializedName("view_count")
    @Expose
    private Object viewCount;
    @SerializedName("archived")
    @Expose
    private Boolean archived;
    @SerializedName("clicked")
    @Expose
    private Boolean clicked;
    @SerializedName("no_follow")
    @Expose
    private Boolean noFollow;
    @SerializedName("author")
    @Expose
    private String author;
    @SerializedName("num_crossposts")
    @Expose
    private Integer numCrossposts;
    @SerializedName("link_flair_text")
    @Expose
    private Object linkFlairText;
    @SerializedName("can_mod_post")
    @Expose
    private Boolean canModPost;
    @SerializedName("send_replies")
    @Expose
    private Boolean sendReplies;
    @SerializedName("pinned")
    @Expose
    private Boolean pinned;
    @SerializedName("score")
    @Expose
    private Integer score;
    @SerializedName("approved_by")
    @Expose
    private Object approvedBy;
    @SerializedName("over_18")
    @Expose
    private Boolean over18;
    @SerializedName("report_reasons")
    @Expose
    private Object reportReasons;
    @SerializedName("domain")
    @Expose
    private String domain;
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

    @SerializedName("edited")
    @Expose
    private Object edited;

    @SerializedName("link_flair_css_class")
    @Expose
    private Object linkFlairCssClass;
    @SerializedName("author_flair_css_class")
    @Expose
    private String authorFlairCssClass;
    @SerializedName("contest_mode")
    @Expose
    private Boolean contestMode;
    @SerializedName("gilded")
    @Expose
    private Integer gilded;
    @SerializedName("locked")
    @Expose
    private Boolean locked;
    @SerializedName("downs")
    @Expose
    private Integer downs;
    @SerializedName("mod_reports")
    @Expose
    private List<Object> modReports = null;
    @SerializedName("subreddit_subscribers")
    @Expose
    private Integer subredditSubscribers;
    @SerializedName("secure_media_embed")
    @Expose
    private SecureMediaEmbed secureMediaEmbed;
    @SerializedName("media_embed")
    @Expose
    private MediaEmbed mediaEmbed;
    @SerializedName("stickied")
    @Expose
    private Boolean stickied;
    @SerializedName("visited")
    @Expose
    private Boolean visited;
    @SerializedName("can_gild")
    @Expose
    private Boolean canGild;
    @SerializedName("thumbnail_height")
    @Expose
    private Object thumbnailHeight;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("spoiler")
    @Expose
    private Boolean spoiler;
    @SerializedName("permalink")
    @Expose
    private String permalink;
    @SerializedName("subreddit_type")
    @Expose
    private String subredditType;
    @SerializedName("parent_whitelist_status")
    @Expose
    private String parentWhitelistStatus;
    @SerializedName("hide_score")
    @Expose
    private Boolean hideScore;
    @SerializedName("created")
    @Expose
    private Double created;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("author_flair_text")
    @Expose
    private String authorFlairText;
    @SerializedName("quarantine")
    @Expose
    private Boolean quarantine;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("created_utc")
    @Expose
    private Double createdUtc;
    @SerializedName("subreddit_name_prefixed")
    @Expose
    private String subredditNamePrefixed;
    @SerializedName("ups")
    @Expose
    private Integer ups;
    @SerializedName("num_comments")
    @Expose
    private Integer numComments;
    @SerializedName("media")
    @Expose
    private Object media;
    @SerializedName("is_self")
    @Expose
    private Boolean isSelf;
    @SerializedName("whitelist_status")
    @Expose
    private String whitelistStatus;
    @SerializedName("mod_note")
    @Expose
    private Object modNote;
    @SerializedName("is_video")
    @Expose
    private Boolean isVideo;
    @SerializedName("distinguished")
    @Expose
    private String distinguished;
    public final static Parcelable.Creator<T3Data > CREATOR = new Creator<T3Data >() {


        @SuppressWarnings({
                "unchecked"
        })
        public T3Data  createFromParcel(Parcel in) {
            return new T3Data (in);
        }

        public T3Data [] newArray(int size) {
            return (new T3Data [size]);
        }

    }
            ;

    protected T3Data (Parcel in) {
        this.isCrosspostable = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.subredditId = ((String) in.readValue((String.class.getClassLoader())));
        this.approvedAtUtc = in.readValue((Object.class.getClassLoader()));
        this.wls = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.modReasonBy = in.readValue((Object.class.getClassLoader()));
        this.bannedBy = in.readValue((Object.class.getClassLoader()));
        this.numReports = in.readValue((Object.class.getClassLoader()));
        this.removalReason = in.readValue((Object.class.getClassLoader()));
        this.thumbnailWidth = in.readValue((Object.class.getClassLoader()));
        this.subreddit = ((String) in.readValue((String.class.getClassLoader())));
        this.selftextHtml = ((String) in.readValue((String.class.getClassLoader())));
        this.selftext = ((String) in.readValue((String.class.getClassLoader())));
        this.likes = in.readValue((Object.class.getClassLoader()));
        this.suggestedSort = in.readValue((Object.class.getClassLoader()));
        in.readList(this.userReports, (java.lang.Object.class.getClassLoader()));
        this.secureMedia = in.readValue((Object.class.getClassLoader()));
        this.isRedditMediaDomain = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.saved = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.id = ((String) in.readValue((String.class.getClassLoader())));
        this.bannedAtUtc = in.readValue((Object.class.getClassLoader()));
        this.modReasonTitle = in.readValue((Object.class.getClassLoader()));
        this.viewCount = in.readValue((Object.class.getClassLoader()));
        this.archived = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.clicked = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.noFollow = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.author = ((String) in.readValue((String.class.getClassLoader())));
        this.numCrossposts = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.linkFlairText = in.readValue((Object.class.getClassLoader()));
        this.canModPost = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.sendReplies = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.pinned = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.score = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.approvedBy = in.readValue((Object.class.getClassLoader()));
        this.over18 = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.reportReasons = in.readValue((Object.class.getClassLoader()));
        this.domain = ((String) in.readValue((String.class.getClassLoader())));
        this.hidden = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.preview = ((Preview) in.readValue((Preview.class.getClassLoader())));
        this.pwls = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.thumbnail = ((String) in.readValue((String.class.getClassLoader())));
        this.edited = in.readValue((Integer.class.getClassLoader()));
        this.linkFlairCssClass = in.readValue((Object.class.getClassLoader()));
        this.authorFlairCssClass = ((String) in.readValue((String.class.getClassLoader())));
        this.contestMode = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.gilded = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.locked = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.downs = ((Integer) in.readValue((Integer.class.getClassLoader())));
        in.readList(this.modReports, (java.lang.Object.class.getClassLoader()));
        this.subredditSubscribers = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.secureMediaEmbed = ((SecureMediaEmbed) in.readValue((SecureMediaEmbed.class.getClassLoader())));
        this.mediaEmbed = ((MediaEmbed) in.readValue((MediaEmbed.class.getClassLoader())));
        this.stickied = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.visited = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.canGild = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.thumbnailHeight = in.readValue((Object.class.getClassLoader()));
        this.name = ((String) in.readValue((String.class.getClassLoader())));
        this.spoiler = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.permalink = ((String) in.readValue((String.class.getClassLoader())));
        this.subredditType = ((String) in.readValue((String.class.getClassLoader())));
        this.parentWhitelistStatus = ((String) in.readValue((String.class.getClassLoader())));
        this.hideScore = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.created = ((Double) in.readValue((Double.class.getClassLoader())));
        this.url = ((String) in.readValue((String.class.getClassLoader())));
        this.authorFlairText = ((String) in.readValue((String.class.getClassLoader())));
        this.quarantine = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.title = ((String) in.readValue((String.class.getClassLoader())));
        this.createdUtc = ((Double) in.readValue((Double.class.getClassLoader())));
        this.subredditNamePrefixed = ((String) in.readValue((String.class.getClassLoader())));
        this.ups = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.numComments = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.media = in.readValue((Object.class.getClassLoader()));
        this.isSelf = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.whitelistStatus = ((String) in.readValue((String.class.getClassLoader())));
        this.modNote = in.readValue((Object.class.getClassLoader()));
        this.isVideo = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.distinguished = ((String) in.readValue((String.class.getClassLoader())));
    }

    public T3Data () {
    }

    public Boolean getIsCrosspostable() {
        return isCrosspostable;
    }

    public void setIsCrosspostable(Boolean isCrosspostable) {
        this.isCrosspostable = isCrosspostable;
    }

    public String getSubredditId() {
        return subredditId;
    }

    public void setSubredditId(String subredditId) {
        this.subredditId = subredditId;
    }

    public Object getApprovedAtUtc() {
        return approvedAtUtc;
    }

    public void setApprovedAtUtc(Object approvedAtUtc) {
        this.approvedAtUtc = approvedAtUtc;
    }

    public Integer getWls() {
        return wls;
    }

    public void setWls(Integer wls) {
        this.wls = wls;
    }

    public Object getModReasonBy() {
        return modReasonBy;
    }

    public void setModReasonBy(Object modReasonBy) {
        this.modReasonBy = modReasonBy;
    }

    public Object getBannedBy() {
        return bannedBy;
    }

    public void setBannedBy(Object bannedBy) {
        this.bannedBy = bannedBy;
    }

    public Object getNumReports() {
        return numReports;
    }

    public void setNumReports(Object numReports) {
        this.numReports = numReports;
    }

    public Object getRemovalReason() {
        return removalReason;
    }

    public void setRemovalReason(Object removalReason) {
        this.removalReason = removalReason;
    }

    public Object getThumbnailWidth() {
        return thumbnailWidth;
    }

    public void setThumbnailWidth(Object thumbnailWidth) {
        this.thumbnailWidth = thumbnailWidth;
    }

    public String getSubreddit() {
        return subreddit;
    }

    public void setSubreddit(String subreddit) {
        this.subreddit = subreddit;
    }

    public String getSelftextHtml() {
        return selftextHtml;
    }

    public void setSelftextHtml(String selftextHtml) {
        this.selftextHtml = selftextHtml;
    }

    public String getSelftext() {
        return selftext;
    }

    public void setSelftext(String selftext) {
        this.selftext = selftext;
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

    public List<Object> getUserReports() {
        return userReports;
    }

    public void setUserReports(List<Object> userReports) {
        this.userReports = userReports;
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

    public Boolean getSaved() {
        return saved;
    }

    public void setSaved(Boolean saved) {
        this.saved = saved;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Object getBannedAtUtc() {
        return bannedAtUtc;
    }

    public void setBannedAtUtc(Object bannedAtUtc) {
        this.bannedAtUtc = bannedAtUtc;
    }

    public Object getModReasonTitle() {
        return modReasonTitle;
    }

    public void setModReasonTitle(Object modReasonTitle) {
        this.modReasonTitle = modReasonTitle;
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

    public Boolean getClicked() {
        return clicked;
    }

    public void setClicked(Boolean clicked) {
        this.clicked = clicked;
    }

    public Boolean getNoFollow() {
        return noFollow;
    }

    public void setNoFollow(Boolean noFollow) {
        this.noFollow = noFollow;
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

    public Boolean getSendReplies() {
        return sendReplies;
    }

    public void setSendReplies(Boolean sendReplies) {
        this.sendReplies = sendReplies;
    }

    public Boolean getPinned() {
        return pinned;
    }

    public void setPinned(Boolean pinned) {
        this.pinned = pinned;
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

    public Boolean getOver18() {
        return over18;
    }

    public void setOver18(Boolean over18) {
        this.over18 = over18;
    }

    public Object getReportReasons() {
        return reportReasons;
    }

    public void setReportReasons(Object reportReasons) {
        this.reportReasons = reportReasons;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
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

    public Object getLinkFlairCssClass() {
        return linkFlairCssClass;
    }

    public void setLinkFlairCssClass(Object linkFlairCssClass) {
        this.linkFlairCssClass = linkFlairCssClass;
    }

    public String getAuthorFlairCssClass() {
        return authorFlairCssClass;
    }

    public void setAuthorFlairCssClass(String authorFlairCssClass) {
        this.authorFlairCssClass = authorFlairCssClass;
    }

    public Boolean getContestMode() {
        return contestMode;
    }

    public void setContestMode(Boolean contestMode) {
        this.contestMode = contestMode;
    }

    public Integer getGilded() {
        return gilded;
    }

    public void setGilded(Integer gilded) {
        this.gilded = gilded;
    }

    public Boolean getLocked() {
        return locked;
    }

    public void setLocked(Boolean locked) {
        this.locked = locked;
    }

    public Integer getDowns() {
        return downs;
    }

    public void setDowns(Integer downs) {
        this.downs = downs;
    }

    public List<Object> getModReports() {
        return modReports;
    }

    public void setModReports(List<Object> modReports) {
        this.modReports = modReports;
    }

    public Integer getSubredditSubscribers() {
        return subredditSubscribers;
    }

    public void setSubredditSubscribers(Integer subredditSubscribers) {
        this.subredditSubscribers = subredditSubscribers;
    }

    public SecureMediaEmbed getSecureMediaEmbed() {
        return secureMediaEmbed;
    }

    public void setSecureMediaEmbed(SecureMediaEmbed secureMediaEmbed) {
        this.secureMediaEmbed = secureMediaEmbed;
    }

    public MediaEmbed getMediaEmbed() {
        return mediaEmbed;
    }

    public void setMediaEmbed(MediaEmbed mediaEmbed) {
        this.mediaEmbed = mediaEmbed;
    }

    public Boolean getStickied() {
        return stickied;
    }

    public void setStickied(Boolean stickied) {
        this.stickied = stickied;
    }

    public Boolean getVisited() {
        return visited;
    }

    public void setVisited(Boolean visited) {
        this.visited = visited;
    }

    public Boolean getCanGild() {
        return canGild;
    }

    public void setCanGild(Boolean canGild) {
        this.canGild = canGild;
    }

    public Object getThumbnailHeight() {
        return thumbnailHeight;
    }

    public void setThumbnailHeight(Object thumbnailHeight) {
        this.thumbnailHeight = thumbnailHeight;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getSpoiler() {
        return spoiler;
    }

    public void setSpoiler(Boolean spoiler) {
        this.spoiler = spoiler;
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

    public Boolean getHideScore() {
        return hideScore;
    }

    public void setHideScore(Boolean hideScore) {
        this.hideScore = hideScore;
    }

    public Double getCreated() {
        return created;
    }

    public void setCreated(Double created) {
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

    public Boolean getQuarantine() {
        return quarantine;
    }

    public void setQuarantine(Boolean quarantine) {
        this.quarantine = quarantine;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getCreatedUtc() {
        return createdUtc;
    }

    public void setCreatedUtc(Double createdUtc) {
        this.createdUtc = createdUtc;
    }

    public String getSubredditNamePrefixed() {
        return subredditNamePrefixed;
    }

    public void setSubredditNamePrefixed(String subredditNamePrefixed) {
        this.subredditNamePrefixed = subredditNamePrefixed;
    }

    public Integer getUps() {
        return ups;
    }

    public void setUps(Integer ups) {
        this.ups = ups;
    }

    public Integer getNumComments() {
        return numComments;
    }

    public void setNumComments(Integer numComments) {
        this.numComments = numComments;
    }

    public Object getMedia() {
        return media;
    }

    public void setMedia(Object media) {
        this.media = media;
    }

    public Boolean getIsSelf() {
        return isSelf;
    }

    public void setIsSelf(Boolean isSelf) {
        this.isSelf = isSelf;
    }

    public String getWhitelistStatus() {
        return whitelistStatus;
    }

    public void setWhitelistStatus(String whitelistStatus) {
        this.whitelistStatus = whitelistStatus;
    }

    public Object getModNote() {
        return modNote;
    }

    public void setModNote(Object modNote) {
        this.modNote = modNote;
    }

    public Boolean getIsVideo() {
        return isVideo;
    }

    public void setIsVideo(Boolean isVideo) {
        this.isVideo = isVideo;
    }

    public String getDistinguished() {
        return distinguished;
    }

    public void setDistinguished(String distinguished) {
        this.distinguished = distinguished;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(isCrosspostable);
        dest.writeValue(subredditId);
        dest.writeValue(approvedAtUtc);
        dest.writeValue(wls);
        dest.writeValue(modReasonBy);
        dest.writeValue(bannedBy);
        dest.writeValue(numReports);
        dest.writeValue(removalReason);
        dest.writeValue(thumbnailWidth);
        dest.writeValue(subreddit);
        dest.writeValue(selftextHtml);
        dest.writeValue(selftext);
        dest.writeValue(likes);
        dest.writeValue(suggestedSort);
        dest.writeList(userReports);
        dest.writeValue(secureMedia);
        dest.writeValue(isRedditMediaDomain);
        dest.writeValue(saved);
        dest.writeValue(id);
        dest.writeValue(bannedAtUtc);
        dest.writeValue(modReasonTitle);
        dest.writeValue(viewCount);
        dest.writeValue(archived);
        dest.writeValue(clicked);
        dest.writeValue(noFollow);
        dest.writeValue(author);
        dest.writeValue(numCrossposts);
        dest.writeValue(linkFlairText);
        dest.writeValue(canModPost);
        dest.writeValue(sendReplies);
        dest.writeValue(pinned);
        dest.writeValue(score);
        dest.writeValue(approvedBy);
        dest.writeValue(over18);
        dest.writeValue(reportReasons);
        dest.writeValue(domain);
        dest.writeValue(hidden);
        dest.writeValue(pwls);
        dest.writeValue(thumbnail);
        dest.writeValue(edited);
        dest.writeValue(linkFlairCssClass);
        dest.writeValue(authorFlairCssClass);
        dest.writeValue(contestMode);
        dest.writeValue(gilded);
        dest.writeValue(locked);
        dest.writeValue(downs);
        dest.writeList(modReports);
        dest.writeValue(subredditSubscribers);
        dest.writeValue(secureMediaEmbed);
        dest.writeValue(mediaEmbed);
        dest.writeValue(stickied);
        dest.writeValue(visited);
        dest.writeValue(canGild);
        dest.writeValue(thumbnailHeight);
        dest.writeValue(name);
        dest.writeValue(spoiler);
        dest.writeValue(permalink);
        dest.writeValue(subredditType);
        dest.writeValue(parentWhitelistStatus);
        dest.writeValue(hideScore);
        dest.writeValue(created);
        dest.writeValue(url);
        dest.writeValue(authorFlairText);
        dest.writeValue(quarantine);
        dest.writeValue(title);
        dest.writeValue(createdUtc);
        dest.writeValue(subredditNamePrefixed);
        dest.writeValue(ups);
        dest.writeValue(numComments);
        dest.writeValue(media);
        dest.writeValue(isSelf);
        dest.writeValue(whitelistStatus);
        dest.writeValue(modNote);
        dest.writeValue(isVideo);
        dest.writeValue(distinguished);
    }

    public int describeContents() {
        return  0;
    }

}
