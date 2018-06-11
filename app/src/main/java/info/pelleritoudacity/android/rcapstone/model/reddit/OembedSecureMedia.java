package info.pelleritoudacity.android.rcapstone.model.reddit;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OembedSecureMedia implements Parcelable
{

    @SerializedName("provider_url")
    @Expose
    private String providerUrl;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("html")
    @Expose
    private String html;
    @SerializedName("author_name")
    @Expose
    private String authorName;
    @SerializedName("height")
    @Expose
    private Integer height;
    @SerializedName("width")
    @Expose
    private Integer width;
    @SerializedName("version")
    @Expose
    private String version;
    @SerializedName("thumbnail_width")
    @Expose
    private Integer thumbnailWidth;
    @SerializedName("provider_name")
    @Expose
    private String providerName;
    @SerializedName("thumbnail_url")
    @Expose
    private String thumbnailUrl;
    @SerializedName("thumbnail_height")
    @Expose
    private Integer thumbnailHeight;
    @SerializedName("author_url")
    @Expose
    private String authorUrl;
    public final static Parcelable.Creator<OembedSecureMedia> CREATOR = new Creator<OembedSecureMedia>() {


        @SuppressWarnings({
            "unchecked"
        })
        public OembedSecureMedia createFromParcel(Parcel in) {
            return new OembedSecureMedia(in);
        }

        public OembedSecureMedia[] newArray(int size) {
            return (new OembedSecureMedia[size]);
        }

    }
    ;

    protected OembedSecureMedia(Parcel in) {
        this.providerUrl = ((String) in.readValue((String.class.getClassLoader())));
        this.title = ((String) in.readValue((String.class.getClassLoader())));
        this.type = ((String) in.readValue((String.class.getClassLoader())));
        this.html = ((String) in.readValue((String.class.getClassLoader())));
        this.authorName = ((String) in.readValue((String.class.getClassLoader())));
        this.height = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.width = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.version = ((String) in.readValue((String.class.getClassLoader())));
        this.thumbnailWidth = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.providerName = ((String) in.readValue((String.class.getClassLoader())));
        this.thumbnailUrl = ((String) in.readValue((String.class.getClassLoader())));
        this.thumbnailHeight = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.authorUrl = ((String) in.readValue((String.class.getClassLoader())));
    }

    public OembedSecureMedia() {
    }

    public String getProviderUrl() {
        return providerUrl;
    }

    public void setProviderUrl(String providerUrl) {
        this.providerUrl = providerUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Integer getThumbnailWidth() {
        return thumbnailWidth;
    }

    public void setThumbnailWidth(Integer thumbnailWidth) {
        this.thumbnailWidth = thumbnailWidth;
    }

    public String getProviderName() {
        return providerName;
    }

    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public Integer getThumbnailHeight() {
        return thumbnailHeight;
    }

    public void setThumbnailHeight(Integer thumbnailHeight) {
        this.thumbnailHeight = thumbnailHeight;
    }

    public String getAuthorUrl() {
        return authorUrl;
    }

    public void setAuthorUrl(String authorUrl) {
        this.authorUrl = authorUrl;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(providerUrl);
        dest.writeValue(title);
        dest.writeValue(type);
        dest.writeValue(html);
        dest.writeValue(authorName);
        dest.writeValue(height);
        dest.writeValue(width);
        dest.writeValue(version);
        dest.writeValue(thumbnailWidth);
        dest.writeValue(providerName);
        dest.writeValue(thumbnailUrl);
        dest.writeValue(thumbnailHeight);
        dest.writeValue(authorUrl);
    }

    public int describeContents() {
        return  0;
    }

}
