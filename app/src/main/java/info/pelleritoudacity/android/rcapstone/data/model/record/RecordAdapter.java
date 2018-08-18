package info.pelleritoudacity.android.rcapstone.data.model.record;


@SuppressWarnings("ALL")
public class RecordAdapter {

    private int idReddit;
    private int score;
    private int dirScore;
    private int numComments;
    private int videoOembedHeight;
    @SuppressWarnings("SpellCheckingInspection")
    private int videoOembedWidth;
    private int subRedditSubscriptions;

    private Long createdUtc;

    private boolean saved;

    private String imagePreviewUrl;

    private String nameIdReddit;
    private String nameReddit;
    private String subRedditIdText;
    private String subReddit;
    private String subRedditNamePrefix;
    private String domain;
    private String title;
    private String videoPreviewUrl;
    private String thumbnailUrlOembed;
    private String videoUrl;
    private String videoTypeOembed;
    private String videoFrameOembed;
    private String videoAuthorNameOembed;

    public boolean isSaved() {
        return saved;
    }

    public void setSaved(boolean saved) {
        this.saved = saved;
    }

    @SuppressWarnings("unused")
    public int getIdReddit() {
        return idReddit;
    }

    public void setIdReddit(int idReddit) {
        this.idReddit = idReddit;
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

    public int getNumComments() {
        return numComments;
    }

    public void setNumComments(int numComments) {
        this.numComments = numComments;
    }


    @SuppressWarnings("unused")
    public int getVideoOembedHeight() {
        return videoOembedHeight;
    }

    public void setVideoOembedHeight(int videoOembedHeight) {
        this.videoOembedHeight = videoOembedHeight;
    }


    public void setVideoOembedWidth(int videoOembedWidth) {
        this.videoOembedWidth = videoOembedWidth;
    }


    public int getSubRedditSubscriptions() {
        return subRedditSubscriptions;
    }

    public void setSubRedditSubscriptions(int subRedditSubscriptions) {
        this.subRedditSubscriptions = subRedditSubscriptions;
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

    public String getNameIdReddit() {
        return nameIdReddit;
    }

    public void setNameIdReddit(String nameIdReddit) {
        this.nameIdReddit = nameIdReddit;
    }

    public String getNameReddit() {
        return nameReddit;
    }

    public void setNameReddit(String nameReddit) {
        this.nameReddit = nameReddit;
    }

    @SuppressWarnings("unused")
    public String getSubRedditIdText() {
        return subRedditIdText;
    }

    public void setSubRedditIdText(String subRedditIdText) {
        this.subRedditIdText = subRedditIdText;
    }

    public String getSubReddit() {
        return subReddit;
    }

    public void setSubReddit(String subReddit) {
        this.subReddit = subReddit;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVideoPreviewUrl() {
        return videoPreviewUrl;
    }

    public void setVideoPreviewUrl(String videoPreviewUrl) {
        this.videoPreviewUrl = videoPreviewUrl;
    }

    public String getThumbnailUrlOembed() {
        return thumbnailUrlOembed;
    }

    public void setThumbnailUrlOembed(String thumbnailUrlOembed) {
        this.thumbnailUrlOembed = thumbnailUrlOembed;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getVideoTypeOembed() {
        return videoTypeOembed;
    }

    public void setVideoTypeOembed(String videoTypeOembed) {
        this.videoTypeOembed = videoTypeOembed;
    }

    public String getVideoFrameOembed() {
        return videoFrameOembed;
    }

    public void setVideoFrameOembed(String videoFrameOembed) {
        this.videoFrameOembed = videoFrameOembed;
    }

    @SuppressWarnings("unused")
    public String getVideoAuthorNameOembed() {
        return videoAuthorNameOembed;
    }

    public void setVideoAuthorNameOembed(String videoAuthorNameOembed) {
        this.videoAuthorNameOembed = videoAuthorNameOembed;
    }


}
