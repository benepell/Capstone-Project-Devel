package info.pelleritoudacity.android.rcapstone.model.reddit;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class T1ListingData  implements Parcelable {

    @SerializedName("subreddit_id")
    @Expose
    private String subredditId;
    @SerializedName("approved_at_utc")
    @Expose
    private Object approvedAtUtc;
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
    // todo replies problem
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
    private Object bannedAtUtc;
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
    private Double created;
    @SerializedName("author_flair_text")
    @Expose
    private Object authorFlairText;
    @SerializedName("rte_mode")
    @Expose
    private String rteMode;
    @SerializedName("created_utc")
    @Expose
    private Double createdUtc;
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

    protected T1ListingData(Parcel in) {
        this.subredditId = ((String) in.readValue((String.class.getClassLoader())));
        this.title = ((String) in.readValue((String.class.getClassLoader())));
        this.approvedAtUtc = ((Object) in.readValue((Object.class.getClassLoader())));
        this.ups = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.modReasonBy = ((Object) in.readValue((Object.class.getClassLoader())));
        this.bannedBy = ((Object) in.readValue((Object.class.getClassLoader())));
        this.authorFlairType = ((String) in.readValue((String.class.getClassLoader())));
        this.removalReason = ((Object) in.readValue((Object.class.getClassLoader())));
        this.linkId = ((String) in.readValue((String.class.getClassLoader())));
        this.url = ((String) in.readValue((String.class.getClassLoader())));
        this.authorFlairTemplateId = ((String) in.readValue((String.class.getClassLoader())));
        this.likes = ((Object) in.readValue((Object.class.getClassLoader())));
        this.noFollow = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.replies = ((Replies) in.readValue((Replies.class.getClassLoader())));
        in.readList(this.userReports, (java.lang.Object.class.getClassLoader()));
        this.saved = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.id = ((String) in.readValue((String.class.getClassLoader())));
        this.bannedAtUtc = ((Object) in.readValue((Object.class.getClassLoader())));
        this.modReasonTitle = ((Object) in.readValue((Object.class.getClassLoader())));
        this.gilded = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.archived = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.reportReasons = ((Object) in.readValue((Object.class.getClassLoader())));
        this.author = ((String) in.readValue((String.class.getClassLoader())));
        this.canModPost = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.sendReplies = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.parentId = ((String) in.readValue((String.class.getClassLoader())));
        this.score = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.approvedBy = ((Object) in.readValue((Object.class.getClassLoader())));
        this.downs = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.body = ((String) in.readValue((String.class.getClassLoader())));
        this.edited = ((Object) in.readValue((Object.class.getClassLoader())));
        this.authorFlairCssClass = ((Object) in.readValue((Object.class.getClassLoader())));
        this.collapsed = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        in.readList(this.authorFlairRichtext, (java.lang.Object.class.getClassLoader()));
        this.isSubmitter = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.collapsedReason = ((Object) in.readValue((Object.class.getClassLoader())));
        this.bodyHtml = ((String) in.readValue((String.class.getClassLoader())));
        this.stickied = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.subredditType = ((String) in.readValue((String.class.getClassLoader())));
        this.canGild = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.subreddit = ((String) in.readValue((String.class.getClassLoader())));
        this.authorFlairTextColor = ((Object) in.readValue((Object.class.getClassLoader())));
        this.scoreHidden = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.permalink = ((String) in.readValue((String.class.getClassLoader())));
        this.numReports = ((Object) in.readValue((Object.class.getClassLoader())));
        this.name = ((String) in.readValue((String.class.getClassLoader())));
        this.created = ((Double) in.readValue((Double.class.getClassLoader())));
        this.authorFlairText = ((Object) in.readValue((Object.class.getClassLoader())));
        this.rteMode = ((String) in.readValue((String.class.getClassLoader())));
        this.createdUtc = ((Double) in.readValue((Double.class.getClassLoader())));
        this.subredditNamePrefixed = ((String) in.readValue((String.class.getClassLoader())));
        this.controversiality = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.depth = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.authorFlairBackgroundColor = ((Object) in.readValue((Object.class.getClassLoader())));
        in.readList(this.modReports, (java.lang.Object.class.getClassLoader()));
        this.modNote = ((Object) in.readValue((Object.class.getClassLoader())));
        this.distinguished = ((Object) in.readValue((Object.class.getClassLoader())));
        this.count = ((Integer) in.readValue((Integer.class.getClassLoader())));
        in.readList(this.children, (java.lang.String.class.getClassLoader()));
    }

    public T1ListingData() {
    }

    public String getSubredditId() {
        return subredditId;
    }

    public void setSubredditId(String subredditId) {
        this.subredditId = subredditId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Object getApprovedAtUtc() {
        return approvedAtUtc;
    }

    public void setApprovedAtUtc(Object approvedAtUtc) {
        this.approvedAtUtc = approvedAtUtc;
    }

    public Integer getUps() {
        return ups;
    }

    public void setUps(Integer ups) {
        this.ups = ups;
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

    public String getAuthorFlairType() {
        return authorFlairType;
    }

    public void setAuthorFlairType(String authorFlairType) {
        this.authorFlairType = authorFlairType;
    }

    public Object getRemovalReason() {
        return removalReason;
    }

    public void setRemovalReason(Object removalReason) {
        this.removalReason = removalReason;
    }

    public String getLinkId() {
        return linkId;
    }

    public void setLinkId(String linkId) {
        this.linkId = linkId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAuthorFlairTemplateId() {
        return authorFlairTemplateId;
    }

    public void setAuthorFlairTemplateId(String authorFlairTemplateId) {
        this.authorFlairTemplateId = authorFlairTemplateId;
    }

    public Object getLikes() {
        return likes;
    }

    public void setLikes(Object likes) {
        this.likes = likes;
    }

    public Boolean getNoFollow() {
        return noFollow;
    }

    public void setNoFollow(Boolean noFollow) {
        this.noFollow = noFollow;
    }

    public Replies getReplies() {
        return replies;
    }

    public void setReplies(Replies replies) {
        this.replies = replies;
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

    public Integer getGilded() {
        return gilded;
    }

    public void setGilded(Integer gilded) {
        this.gilded = gilded;
    }

    public Boolean getArchived() {
        return archived;
    }

    public void setArchived(Boolean archived) {
        this.archived = archived;
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

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
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

    public Integer getDowns() {
        return downs;
    }

    public void setDowns(Integer downs) {
        this.downs = downs;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
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

    public Boolean getCollapsed() {
        return collapsed;
    }

    public void setCollapsed(Boolean collapsed) {
        this.collapsed = collapsed;
    }

    public List<Object> getAuthorFlairRichtext() {
        return authorFlairRichtext;
    }

    public void setAuthorFlairRichtext(List<Object> authorFlairRichtext) {
        this.authorFlairRichtext = authorFlairRichtext;
    }

    public Boolean getIsSubmitter() {
        return isSubmitter;
    }

    public void setIsSubmitter(Boolean isSubmitter) {
        this.isSubmitter = isSubmitter;
    }

    public Object getCollapsedReason() {
        return collapsedReason;
    }

    public void setCollapsedReason(Object collapsedReason) {
        this.collapsedReason = collapsedReason;
    }

    public String getBodyHtml() {
        return bodyHtml;
    }

    public void setBodyHtml(String bodyHtml) {
        this.bodyHtml = bodyHtml;
    }

    public Boolean getStickied() {
        return stickied;
    }

    public void setStickied(Boolean stickied) {
        this.stickied = stickied;
    }

    public String getSubredditType() {
        return subredditType;
    }

    public void setSubredditType(String subredditType) {
        this.subredditType = subredditType;
    }

    public Boolean getCanGild() {
        return canGild;
    }

    public void setCanGild(Boolean canGild) {
        this.canGild = canGild;
    }

    public String getSubreddit() {
        return subreddit;
    }

    public void setSubreddit(String subreddit) {
        this.subreddit = subreddit;
    }

    public Object getAuthorFlairTextColor() {
        return authorFlairTextColor;
    }

    public void setAuthorFlairTextColor(Object authorFlairTextColor) {
        this.authorFlairTextColor = authorFlairTextColor;
    }

    public Boolean getScoreHidden() {
        return scoreHidden;
    }

    public void setScoreHidden(Boolean scoreHidden) {
        this.scoreHidden = scoreHidden;
    }

    public String getPermalink() {
        return permalink;
    }

    public void setPermalink(String permalink) {
        this.permalink = permalink;
    }

    public Object getNumReports() {
        return numReports;
    }

    public void setNumReports(Object numReports) {
        this.numReports = numReports;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getCreated() {
        return created;
    }

    public void setCreated(Double created) {
        this.created = created;
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

    public Integer getControversiality() {
        return controversiality;
    }

    public void setControversiality(Integer controversiality) {
        this.controversiality = controversiality;
    }

    public Integer getDepth() {
        return depth;
    }

    public void setDepth(Integer depth) {
        this.depth = depth;
    }

    public Object getAuthorFlairBackgroundColor() {
        return authorFlairBackgroundColor;
    }

    public void setAuthorFlairBackgroundColor(Object authorFlairBackgroundColor) {
        this.authorFlairBackgroundColor = authorFlairBackgroundColor;
    }

    public List<Object> getModReports() {
        return modReports;
    }

    public void setModReports(List<Object> modReports) {
        this.modReports = modReports;
    }

    public Object getModNote() {
        return modNote;
    }

    public void setModNote(Object modNote) {
        this.modNote = modNote;
    }

    public Object getDistinguished() {
        return distinguished;
    }

    public void setDistinguished(Object distinguished) {
        this.distinguished = distinguished;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List<String> getChildren() {
        return children;
    }

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
