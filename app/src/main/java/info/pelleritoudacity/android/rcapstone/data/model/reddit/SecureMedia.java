package info.pelleritoudacity.android.rcapstone.data.model.reddit;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SecureMedia implements Parcelable
{

    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("oembedSecureMedia")
    @Expose
    private OembedSecureMedia oembedSecureMedia;
    public final static Parcelable.Creator<SecureMedia> CREATOR = new Creator<SecureMedia>() {


        @SuppressWarnings({
            "unchecked"
        })
        public SecureMedia createFromParcel(Parcel in) {
            return new SecureMedia(in);
        }

        public SecureMedia[] newArray(int size) {
            return (new SecureMedia[size]);
        }

    }
    ;

    private SecureMedia(Parcel in) {
        this.type = ((String) in.readValue((String.class.getClassLoader())));
        this.oembedSecureMedia = ((OembedSecureMedia) in.readValue((OembedSecureMedia.class.getClassLoader())));
    }

    @SuppressWarnings("unused")
    public SecureMedia() {
    }

    @SuppressWarnings("unused")
    public String getType() {
        return type;
    }

    @SuppressWarnings("unused")
    public void setType(String type) {
        this.type = type;
    }

    @SuppressWarnings({"unused", "SpellCheckingInspection"})
    public OembedSecureMedia getOembedSecureMedia() {
        return oembedSecureMedia;
    }

    @SuppressWarnings("unused")
    public void setOembedSecureMedia(OembedSecureMedia oembedSecureMedia) {
        this.oembedSecureMedia = oembedSecureMedia;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(type);
        dest.writeValue(oembedSecureMedia);
    }

    public int describeContents() {
        return  0;
    }

}
