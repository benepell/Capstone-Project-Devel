package info.pelleritoudacity.android.rcapstone.data.model.reddit;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class T1ListingData implements Parcelable {

    @SerializedName("subreddit_id")
    @Expose
    private String subredditId;
    @SerializedName("approved_at_utc")
    @Expose
    private Long approvedAtUtc;
    @SerializedName("ups")
    @Expose
    private Integer ups;
    @SerializedName("mod_reason_by")
    @Expose
    private Object modReasonBy;
    @SerializedName("banned_by")
    @Expose
    private Object bannedBy;
    @SerializedName("author_flair_type")
    @Expose
    private String authorFlairType;
    @SerializedName("removal_reason")
    @Expose
    private Object removalReason;
    @SerializedName("link_id")
    @Expose
    private String linkId;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("author_flair_template_id")
    @Expose
    private String authorFlairTemplateId;
    @SerializedName("likes")
    @Expose
    private Object likes;
    @SerializedName("no_follow")
    @Expose
    private Boolean noFollow;
    @SerializedName("replies")
    @Expose
    private Replies replies;
    @SerializedName("user_reports")
    @Expose
    private List<Object> userReports = null;
    @SerializedName("saved")
    @Expose
    private Boolean saved;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("banned_at_utc")
    @Expose
    private Long bannedAtUtc;
    @SerializedName("mod_reason_title")
    @Expose
    private Object modReasonTitle;
    @SerializedName("gilded")
    @Expose
    private Integer gilded;
    @SerializedName("archived")
    @Expose
    private Boolean archived;
    @SerializedName("report_reasons")
    @Expose
    private Object reportReasons;
    @SerializedName("author")
    @Expose
    private String author;
    @SerializedName("can_mod_post")
    @Expose
    private Boolean canModPost;
    @SerializedName("send_replies")
    @Expose
    private Boolean sendReplies;
    @SerializedName("parent_id")
    @Expose
    private String parentId;
    @SerializedName("score")
    @Expose
    private Integer score;
    @SerializedName("approved_by")
    @Expose
    private Object approvedBy;
    @SerializedName("downs")
    @Expose
    private Integer downs;
    @SerializedName("body")
    @Expose
    private String body;
    @SerializedName("edited")
    @Expose
    private Object edited;
    @SerializedName("author_flair_css_class")
    @Expose
    private Object authorFlairCssClass;
    @SerializedName("collapsed")
    @Expose
    private Boolean collapsed;
    @SerializedName("author_flair_richtext")
    @Expose
    private List<Object> authorFlairRichtext = null;
    @SerializedName("is_submitter")
    @Expose
    private Boolean isSubmitter;
    @SerializedName("collapsed_reason")
    @Expose
    private Object collapsedReason;
    @SerializedName("body_html")
    @Expose
    private String bodyHtml;
    @SerializedName("stickied")
    @Expose
    private Boolean stickied;
    @SerializedName("subreddit_type")
    @Expose
    private String subredditType;
    @SerializedName("can_gild")
    @Expose
    private Boolean canGild;
    @SerializedName("subreddit")
    @Expose
    private String subreddit;
    @SerializedName("author_flair_text_color")
    @Expose
    private Object authorFlairTextColor;
    @SerializedName("score_hidden")
    @Expose
    private Boolean scoreHidden;
    @SerializedName("permalink")
    @Expose
    private String permalink;
    @SerializedName("num_reports")
    @Expose
    private Object numReports;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("created")
    @Expose
    private Long created;
    @SerializedName("author_flair_text")
    @Expose
    private Object authorFlairText;
    @SerializedName("rte_mode")
    @Expose
    private String rteMode;
    @SerializedName("created_utc")
    @Expose
    private Long createdUtc;
    @SerializedName("subreddit_name_prefixed")
    @Expose
    private String subredditNamePrefixed;
    @SerializedName("controversiality")
    @Expose
    private Integer controversiality;
    @SerializedName("depth")
    @Expose
    private Integer depth;
    @SerializedName("author_flair_background_color")
    @Expose
    private Object authorFlairBackgroundColor;
    @SerializedName("mod_reports")
    @Expose
    private List<Object> modReports = null;
    @SerializedName("mod_note")
    @Expose
    private Object modNote;
    @SerializedName("distinguished")
    @Expose
    private Object distinguished;
    @SerializedName("count")
    @Expose
    private Integer count;
    @SerializedName("children")
    @Expose
    private List<String> children = null;
    public final static Parcelable.Creator<T1ListingData> CREATOR = new Creator<T1ListingData>() {


        @SuppressWarnings({
                "unchecked"
        })
        public T1ListingData createFromParcel(Parcel in) {
            return new T1ListingData(in);
        }

        public T1ListingData[] newArray(int size) {
            return (new T1ListingData[size]);
        }

    };

    T1ListingData(Parcel in) {
        this.subredditId = ((String) in.readValue((String.class.getClassLoader())));
        this.title = ((String) in.readValue((String.class.getClassLoader())));
        this.approvedAtUtc = (Long)in.readValue((Long.class.getClassLoader()));
        this.ups = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.modReasonBy = in.readValue((Object.class.getClassLoader()));
        this.bannedBy = in.readValue((Object.class.getClassLoader()));
        this.authorFlairType = ((String) in.readValue((String.class.getClassLoader())));
        this.removalReason = in.readValue((Object.class.getClassLoader()));
        this.linkId = ((String) in.readValue((String.class.getClassLoader())));
        this.url = ((String) in.readValue((String.class.getClassLoader())));
        this.authorFlairTemplateId = ((String) in.readValue((String.class.getClassLoader())));
        this.likes = in.readValue((Object.class.getClassLoader()));
        this.noFollow = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.replies = ((Replies) in.readValue((Replies.class.getClassLoader())));
        in.readList(this.userReports, (java.lang.Object.class.getClassLoader()));
        this.saved = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.id = ((String) in.readValue((String.class.getClassLoader())));
        this.bannedAtUtc = (Long)in.readValue((Long.class.getClassLoader()));
        this.modReasonTitle = in.readValue((Object.class.getClassLoader()));
        this.gilded = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.archived = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.reportReasons = in.readValue((Object.class.getClassLoader()));
        this.author = ((String) in.readValue((String.class.getClassLoader())));
        this.canModPost = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.sendReplies = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.parentId = ((String) in.readValue((String.class.getClassLoader())));
        this.score = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.approvedBy = in.readValue((Object.class.getClassLoader()));
        this.downs = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.body = ((String) in.readValue((String.class.getClassLoader())));
        this.edited = in.readValue((Object.class.getClassLoader()));
        this.authorFlairCssClass = in.readValue((Object.class.getClassLoader()));
        this.collapsed = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        in.readList(this.authorFlairRichtext, (java.lang.Object.class.getClassLoader()));
        this.isSubmitter = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.collapsedReason = in.readValue((Object.class.getClassLoader()));
        this.bodyHtml = ((String) in.readValue((String.class.getClassLoader())));
        this.stickied = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.subredditType = ((String) in.readValue((String.class.getClassLoader())));
        this.canGild = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.subreddit = ((String) in.readValue((String.class.getClassLoader())));
        this.authorFlairTextColor = in.readValue((Object.class.getClassLoader()));
        this.scoreHidden = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.permalink = ((String) in.readValue((String.class.getClassLoader())));
        this.numReports = in.readValue((Object.class.getClassLoader()));
        this.name = ((String) in.readValue((String.class.getClassLoader())));
        this.created = ((Long) in.readValue((Long.class.getClassLoader())));
        this.authorFlairText = in.readValue((Object.class.getClassLoader()));
        this.rteMode = ((String) in.readValue((String.class.getClassLoader())));
        this.createdUtc = ((Long) in.readValue((Long.class.getClassLoader())));
        this.subredditNamePrefixed = ((String) in.readValue((String.class.getClassLoader())));
        this.controversiality = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.depth = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.authorFlairBackgroundColor = in.readValue((Object.class.getClassLoader()));
        in.readList(this.modReports, (java.lang.Object.class.getClassLoader()));
        this.modNote = in.readValue((Object.class.getClassLoader()));
        this.distinguished = in.readValue((Object.class.getClassLoader()));
        this.count = ((Integer) in.readValue((Integer.class.getClassLoader())));
        in.readList(this.children, (java.lang.String.class.getClassLoader()));
    }

    public T1ListingData() {
    }

    public String getSubredditId() {
        return subredditId;
    }

    @SuppressWarnings("unused")
    public void setSubredditId(String subredditId) {
        this.subredditId = subredditId;
    }

    public String getTitle() {
        return title;
    }

    @SuppressWarnings("unused")
    public void setTitle(String title) {
        this.title = title;
    }

    public Long getApprovedAtUtc() {
        return approvedAtUtc;
    }

    @SuppressWarnings("unused")
    public void setApprovedAtUtc(Long approvedAtUtc) {
        this.approvedAtUtc = approvedAtUtc;
    }

    public Integer getUps() {
        return ups;
    }

    @SuppressWarnings("unused")
    public void setUps(Integer ups) {
        this.ups = ups;
    }

    @SuppressWarnings("unused")
    public Object getModReasonBy() {
        return modReasonBy;
    }

    @SuppressWarnings("unused")
    public void setModReasonBy(Object modReasonBy) {
        this.modReasonBy = modReasonBy;
    }

    @SuppressWarnings("unused")
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

    @SuppressWarnings("unused")
    public Object getRemovalReason() {
        return removalReason;
    }

    @SuppressWarnings("unused")
    public void setRemovalReason(Object removalReason) {
        this.removalReason = removalReason;
    }

    public String getLinkId() {
        return linkId;
    }

    @SuppressWarnings("unused")
    public void setLinkId(String linkId) {
        this.linkId = linkId;
    }

    public String getUrl() {
        return url;
    }

    @SuppressWarnings("unused")
    public void setUrl(String url) {
        this.url = url;
    }

    @SuppressWarnings("unused")
    public String getAuthorFlairTemplateId() {
        return authorFlairTemplateId;
    }

    @SuppressWarnings("unused")
    public void setAuthorFlairTemplateId(String authorFlairTemplateId) {
        this.authorFlairTemplateId = authorFlairTemplateId;
    }

    @SuppressWarnings("unused")
    public Object getLikes() {
        return likes;
    }

    @SuppressWarnings("unused")
    public void setLikes(Object likes) {
        this.likes = likes;
    }

    public Boolean getNoFollow() {
        return noFollow;
    }

    @SuppressWarnings("unused")
    public void setNoFollow(Boolean noFollow) {
        this.noFollow = noFollow;
    }

    public Replies getReplies() {
        return replies;
    }

    @SuppressWarnings("unused")
    public void setReplies(Replies replies) {
        this.replies = replies;
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

    public String getId() {
        return id;
    }

    @SuppressWarnings("unused")
    public void setId(String id) {
        this.id = id;
    }

    @SuppressWarnings("unused")
    public Object getBannedAtUtc() {
        return bannedAtUtc;
    }

    @SuppressWarnings("unused")
    public void setBannedAtUtc(Long bannedAtUtc) {
        this.bannedAtUtc = bannedAtUtc;
    }

    @SuppressWarnings("unused")
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

    public Boolean getArchived() {
        return archived;
    }

    @SuppressWarnings("unused")
    public void setArchived(Boolean archived) {
        this.archived = archived;
    }

    @SuppressWarnings("unused")
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

    public Boolean getCanModPost() {
        return canModPost;
    }

    @SuppressWarnings("unused")
    public void setCanModPost(Boolean canModPost) {
        this.canModPost = canModPost;
    }

    public Boolean getSendReplies() {
        return sendReplies;
    }

    @SuppressWarnings("unused")
    public void setSendReplies(Boolean sendReplies) {
        this.sendReplies = sendReplies;
    }

    public String getParentId() {
        return parentId;
    }

    @SuppressWarnings("unused")
    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public Integer getScore() {
        return score;
    }

    @SuppressWarnings("unused")
    public void setScore(Integer score) {
        this.score = score;
    }

    @SuppressWarnings("unused")
    public Object getApprovedBy() {
        return approvedBy;
    }

    @SuppressWarnings("unused")
    public void setApprovedBy(Object approvedBy) {
        this.approvedBy = approvedBy;
    }

    public Integer getDowns() {
        return downs;
    }

    @SuppressWarnings("unused")
    public void setDowns(Integer downs) {
        this.downs = downs;
    }

    public String getBody() {
        return body;
    }

    @SuppressWarnings("unused")
    public void setBody(String body) {
        this.body = body;
    }

    @SuppressWarnings("unused")
    public Object getEdited() {
        return edited;
    }

    @SuppressWarnings("unused")
    public void setEdited(Object edited) {
        this.edited = edited;
    }

    @SuppressWarnings("unused")
    public Object getAuthorFlairCssClass() {
        return authorFlairCssClass;
    }

    @SuppressWarnings("unused")
    public void setAuthorFlairCssClass(Object authorFlairCssClass) {
        this.authorFlairCssClass = authorFlairCssClass;
    }

    @SuppressWarnings("unused")
    public Boolean getCollapsed() {
        return collapsed;
    }

    @SuppressWarnings("unused")
    public void setCollapsed(Boolean collapsed) {
        this.collapsed = collapsed;
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
    public Boolean getIsSubmitter() {
        return isSubmitter;
    }

    @SuppressWarnings("unused")
    public void setIsSubmitter(Boolean isSubmitter) {
        this.isSubmitter = isSubmitter;
    }

    @SuppressWarnings("unused")
    public Object getCollapsedReason() {
        return collapsedReason;
    }

    @SuppressWarnings("unused")
    public void setCollapsedReason(Object collapsedReason) {
        this.collapsedReason = collapsedReason;
    }

    public String getBodyHtml() {
        return bodyHtml;
    }

    @SuppressWarnings("unused")
    public void setBodyHtml(String bodyHtml) {
        this.bodyHtml = bodyHtml;
    }

    public Boolean getStickied() {
        return stickied;
    }

    @SuppressWarnings("unused")
    public void setStickied(Boolean stickied) {
        this.stickied = stickied;
    }

    public String getSubredditType() {
        return subredditType;
    }

    @SuppressWarnings("unused")
    public void setSubredditType(String subredditType) {
        this.subredditType = subredditType;
    }

    public Boolean getCanGild() {
        return canGild;
    }

    @SuppressWarnings("unused")
    public void setCanGild(Boolean canGild) {
        this.canGild = canGild;
    }

    public String getSubreddit() {
        return subreddit;
    }

    @SuppressWarnings("unused")
    public void setSubreddit(String subreddit) {
        this.subreddit = subreddit;
    }

    @SuppressWarnings("unused")
    public Object getAuthorFlairTextColor() {
        return authorFlairTextColor;
    }

    @SuppressWarnings("unused")
    public void setAuthorFlairTextColor(Object authorFlairTextColor) {
        this.authorFlairTextColor = authorFlairTextColor;
    }

    public Boolean getScoreHidden() {
        return scoreHidden;
    }

    @SuppressWarnings("unused")
    public void setScoreHidden(Boolean scoreHidden) {
        this.scoreHidden = scoreHidden;
    }

    public String getPermalink() {
        return permalink;
    }

    @SuppressWarnings("unused")
    public void setPermalink(String permalink) {
        this.permalink = permalink;
    }

    @SuppressWarnings("unused")
    public Object getNumReports() {
        return numReports;
    }

    @SuppressWarnings("unused")
    public void setNumReports(Object numReports) {
        this.numReports = numReports;
    }

    public String getName() {
        return name;
    }

    @SuppressWarnings("unused")
    public void setName(String name) {
        this.name = name;
    }

    public Long getCreated() {
        return created;
    }

    @SuppressWarnings("unused")
    public void setCreated(Long created) {
        this.created = created;
    }

    @SuppressWarnings("unused")
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

    public Long getCreatedUtc() {
        return createdUtc;
    }

    @SuppressWarnings("unused")
    public void setCreatedUtc(Long createdUtc) {
        this.createdUtc = createdUtc;
    }

    @SuppressWarnings("unused")
    public String getSubredditNamePrefixed() {
        return subredditNamePrefixed;
    }

    @SuppressWarnings("unused")
    public void setSubredditNamePrefixed(String subredditNamePrefixed) {
        this.subredditNamePrefixed = subredditNamePrefixed;
    }

    @SuppressWarnings("unused")
    public Integer getControversiality() {
        return controversiality;
    }

    @SuppressWarnings({"unused", "SpellCheckingInspection"})
    public void setControversiality(Integer controversiality) {
        this.controversiality = controversiality;
    }

    public Integer getDepth() {
        return depth;
    }

    @SuppressWarnings("unused")
    public void setDepth(Integer depth) {
        this.depth = depth;
    }

    @SuppressWarnings("unused")
    public Object getAuthorFlairBackgroundColor() {
        return authorFlairBackgroundColor;
    }

    @SuppressWarnings("unused")
    public void setAuthorFlairBackgroundColor(Object authorFlairBackgroundColor) {
        this.authorFlairBackgroundColor = authorFlairBackgroundColor;
    }

    @SuppressWarnings("unused")
    public List<Object> getModReports() {
        return modReports;
    }

    @SuppressWarnings("unused")
    public void setModReports(List<Object> modReports) {
        this.modReports = modReports;
    }

    @SuppressWarnings("unused")
    public Object getModNote() {
        return modNote;
    }

    @SuppressWarnings("unused")
    public void setModNote(Object modNote) {
        this.modNote = modNote;
    }

    @SuppressWarnings("unused")
    public Object getDistinguished() {
        return distinguished;
    }

    @SuppressWarnings("unused")
    public void setDistinguished(Object distinguished) {
        this.distinguished = distinguished;
    }

    public Integer getCount() {
        return count;
    }

    @SuppressWarnings("unused")
    public void setCount(Integer count) {
        this.count = count;
    }

    public List<String> getChildren() {
        return children;
    }

    @SuppressWarnings("unused")
    public void setChildren(List<String> children) {
        this.children = children;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(subredditId);
        dest.writeValue(approvedAtUtc);
        dest.writeValue(ups);
        dest.writeValue(modReasonBy);
        dest.writeValue(bannedBy);
        dest.writeValue(authorFlairType);
        dest.writeValue(removalReason);
        dest.writeValue(linkId);
        dest.writeValue(url);
        dest.writeValue(authorFlairTemplateId);
        dest.writeValue(likes);
        dest.writeValue(noFollow);
        dest.writeValue(replies);
        dest.writeList(userReports);
        dest.writeValue(saved);
        dest.writeValue(id);
        dest.writeValue(title);
        dest.writeValue(bannedAtUtc);
        dest.writeValue(modReasonTitle);
        dest.writeValue(gilded);
        dest.writeValue(archived);
        dest.writeValue(reportReasons);
        dest.writeValue(author);
        dest.writeValue(canModPost);
        dest.writeValue(sendReplies);
        dest.writeValue(parentId);
        dest.writeValue(score);
        dest.writeValue(approvedBy);
        dest.writeValue(downs);
        dest.writeValue(body);
        dest.writeValue(edited);
        dest.writeValue(authorFlairCssClass);
        dest.writeValue(collapsed);
        dest.writeList(authorFlairRichtext);
        dest.writeValue(isSubmitter);
        dest.writeValue(collapsedReason);
        dest.writeValue(bodyHtml);
        dest.writeValue(stickied);
        dest.writeValue(subredditType);
        dest.writeValue(canGild);
        dest.writeValue(subreddit);
        dest.writeValue(authorFlairTextColor);
        dest.writeValue(scoreHidden);
        dest.writeValue(permalink);
        dest.writeValue(numReports);
        dest.writeValue(name);
        dest.writeValue(created);
        dest.writeValue(authorFlairText);
        dest.writeValue(rteMode);
        dest.writeValue(createdUtc);
        dest.writeValue(subredditNamePrefixed);
        dest.writeValue(controversiality);
        dest.writeValue(depth);
        dest.writeValue(authorFlairBackgroundColor);
        dest.writeList(modReports);
        dest.writeValue(modNote);
        dest.writeValue(distinguished);
        dest.writeValue(count);
        dest.writeList(children);
    }

    public int describeContents() {
        return 0;
    }

}
