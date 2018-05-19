package info.pelleritoudacity.android.rcapstone.model.reddit;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Preview implements Parcelable
{

    @SerializedName("images")
    @Expose
    private List<Image> images = null;
    @SerializedName("reddit_video_preview")
    @Expose
    private RedditVideoPreview redditVideoPreview;
    @SerializedName("enabled")
    @Expose
    private Boolean enabled;

    public final static Parcelable.Creator<Preview> CREATOR = new Creator<Preview>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Preview createFromParcel(Parcel in) {
            return new Preview(in);
        }

        public Preview[] newArray(int size) {
            return (new Preview[size]);
        }

    }
    ;

    protected Preview(Parcel in) {
        in.readList(this.images, (Image.class.getClassLoader()));
        this.enabled = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.redditVideoPreview = ((RedditVideoPreview) in.readValue((RedditVideoPreview.class.getClassLoader())));
    }

    public Preview() {
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public RedditVideoPreview getRedditVideoPreview() {
        return redditVideoPreview;
    }

    public void setRedditVideoPreview(RedditVideoPreview redditVideoPreview) {
        this.redditVideoPreview = redditVideoPreview;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(images);
        dest.writeValue(redditVideoPreview);
        dest.writeValue(enabled);
    }

    public int describeContents() {
        return  0;
    }

}
