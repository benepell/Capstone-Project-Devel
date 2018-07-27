package info.pelleritoudacity.android.rcapstone.data.model.reddit;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Media implements Parcelable
{

    @SerializedName("type")
    @Expose
    private String type;
    @SuppressWarnings("SpellCheckingInspection")
    @SerializedName("oembed")
    @Expose
    private OembedMedia oembedMedia;
    public final static Parcelable.Creator<Media> CREATOR = new Creator<Media>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Media createFromParcel(Parcel in) {
            return new Media(in);
        }

        public Media[] newArray(int size) {
            return (new Media[size]);
        }

    }
    ;

    Media(Parcel in) {
        this.type = ((String) in.readValue((String.class.getClassLoader())));
        this.oembedMedia = ((OembedMedia) in.readValue((OembedMedia.class.getClassLoader())));
    }

    public Media() {
    }

    public String getType() {
        return type;
    }

    @SuppressWarnings("unused")
    public void setType(String type) {
        this.type = type;
    }

    public OembedMedia getOembed() {
        return oembedMedia;
    }

    @SuppressWarnings("unused")
    public void setOembed(OembedMedia oembedMedia) {
        this.oembedMedia = oembedMedia;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(type);
        dest.writeValue(oembedMedia);
    }

    public int describeContents() {
        return  0;
    }

}
