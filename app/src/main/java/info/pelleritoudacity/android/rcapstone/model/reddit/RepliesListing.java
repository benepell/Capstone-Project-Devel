package info.pelleritoudacity.android.rcapstone.model.reddit;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RepliesListing implements Parcelable
{

    @SerializedName("kind")
    @Expose
    private String kind;
    @SerializedName("data")
    @Expose
    private RepliesListingData data;
    public final static Parcelable.Creator<RepliesListing> CREATOR = new Creator<RepliesListing>() {


        @SuppressWarnings({
            "unchecked"
        })
        public RepliesListing createFromParcel(Parcel in) {
            return new RepliesListing(in);
        }

        public RepliesListing[] newArray(int size) {
            return (new RepliesListing[size]);
        }

    }
    ;

    protected RepliesListing(Parcel in) {
        this.kind = ((String) in.readValue((String.class.getClassLoader())));
        this.data = ((RepliesListingData) in.readValue((RepliesListingData.class.getClassLoader())));
    }

    public RepliesListing() {
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public RepliesListingData getData() {
        return data;
    }

    public void setData(RepliesListingData data) {
        this.data = data;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(kind);
        dest.writeValue(data);
    }

    public int describeContents() {
        return  0;
    }

}
