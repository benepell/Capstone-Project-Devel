package info.pelleritoudacity.android.rcapstone.data.model.record;

public class RecordAdapterTitle {

    private int score;
    private int numComments;
    private int imagePreviewWidth;
    private int imagePreviewHeight;
    private int created;

    private Long createdUtc;

    private boolean saved;

    private String imagePreviewUrl;

    private String subReddit;
    private String subRedditName;
    private String title;
    private String author;
    private String permanentLink;
    private String subRedditNamePrefix;
    private String domain;
    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getNumComments() {
        return numComments;
    }

    public void setNumComments(int numComments) {
        this.numComments = numComments;
    }

    public int getImagePreviewWidth() {
        return imagePreviewWidth;
    }

    public void setImagePreviewWidth(int imagePreviewWidth) {
        this.imagePreviewWidth = imagePreviewWidth;
    }

    public int getImagePreviewHeight() {
        return imagePreviewHeight;
    }

    public void setImagePreviewHeight(int imagePreviewHeight) {
        this.imagePreviewHeight = imagePreviewHeight;
    }

    @SuppressWarnings("unused")
    public int getCreated() {
        return created;
    }

    public void setCreated(int created) {
        this.created = created;
    }

    public boolean isSaved() {
        return saved;
    }

    public void setSaved(boolean saved) {
        this.saved = saved;
    }

    public Long getCreatedUtc() {
        return createdUtc;
    }

    public void setCreatedUtc(Long createdUtc) {
        this.createdUtc = createdUtc;
    }

    public String getImagePreviewUrl() {
        return imagePreviewUrl;
    }

    public void setImagePreviewUrl(String imagePreviewUrl) {
        this.imagePreviewUrl = imagePreviewUrl;
    }

    @SuppressWarnings("unused")
    public String getSubReddit() {
        return subReddit;
    }

    public void setSubReddit(String subReddit) {
        this.subReddit = subReddit;
    }

    public String getSubRedditName() {
        return subRedditName;
    }

    public void setSubRedditName(String subRedditName) {
        this.subRedditName = subRedditName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @SuppressWarnings("unused")
    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPermanentLink() {
        return permanentLink;
    }

    public void setPermanentLink(String permanentLink) {
        this.permanentLink = permanentLink;
    }

    public String getSubRedditNamePrefix() {
        return subRedditNamePrefix;
    }

    public void setSubRedditNamePrefix(String subRedditNamePrefix) {
        this.subRedditNamePrefix = subRedditNamePrefix;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }
}
