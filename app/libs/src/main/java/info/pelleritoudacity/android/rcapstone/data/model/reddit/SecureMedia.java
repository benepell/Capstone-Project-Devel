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

    SecureMedia(Parcel in) {
        this.type = ((String) in.readValue((String.class.getClassLoader())));
        this.oembedSecureMedia = ((OembedSecureMedia) in.readValue((OembedSecureMedia.class.getClassLoader())));
    }

    public SecureMedia() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public OembedSecureMedia getOembedSecureMedia() {
        return oembedSecureMedia;
    }

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
