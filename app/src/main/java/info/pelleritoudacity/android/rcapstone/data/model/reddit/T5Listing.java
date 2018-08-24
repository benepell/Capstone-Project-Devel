
package info.pelleritoudacity.android.rcapstone.data.model.reddit;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class T5Listing implements Parcelable
{

    @SerializedName("after")
    @Expose
    private String after;
    @SerializedName("dist")
    @Expose
    private Integer dist;
    @SerializedName("modhash")
    @Expose
    private String modhash;
    @SerializedName("whitelist_status")
    @Expose
    private String whitelistStatus;
    @SerializedName("children")
    @Expose
    private List<T5ListingData> children = null;
    @SerializedName("before")
    @Expose
    private String before;
    public final static Parcelable.Creator<T5Listing> CREATOR = new Creator<T5Listing>() {


        @SuppressWarnings({
                "unchecked"
        })
        public T5Listing createFromParcel(Parcel in) {
            return new T5Listing(in);
        }

        public T5Listing[] newArray(int size) {
            return (new T5Listing[size]);
        }

    }
            ;

    @SuppressWarnings("WeakerAccess")
    protected T5Listing(Parcel in) {
        this.after = ((String) in.readValue((String.class.getClassLoader())));
        this.dist = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.modhash = ((String) in.readValue((String.class.getClassLoader())));
        this.whitelistStatus = ((String) in.readValue((String.class.getClassLoader())));
        in.readList(this.children, (T5ListingData.class.getClassLoader()));
        this.before = (String) in.readValue(String.class.getClassLoader());
    }

    public T5Listing() {
    }

    public String getAfter() {
        return after;
    }

    @SuppressWarnings("unused")
    public void setAfter(String after) {
        this.after = after;
    }

    public Integer getDist() {
        return dist;
    }

    @SuppressWarnings("unused")
    public void setDist(Integer dist) {
        this.dist = dist;
    }

    public String getModhash() {
        return modhash;
    }

    @SuppressWarnings("unused")
    public void setModhash(String modhash) {
        this.modhash = modhash;
    }

    public String getWhitelistStatus() {
        return whitelistStatus;
    }

    @SuppressWarnings("unused")
    public void setWhitelistStatus(String whitelistStatus) {
        this.whitelistStatus = whitelistStatus;
    }

    public List<T5ListingData> getChildren() {
        return children;
    }

    @SuppressWarnings("unused")
    public void setChildren(List<T5ListingData> children) {
        this.children = children;
    }

    public String getBefore() {
        return before;
    }

    @SuppressWarnings("unused")
    public void setBefore(String before) {
        this.before = before;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(after);
        dest.writeValue(dist);
        dest.writeValue(modhash);
        dest.writeValue(whitelistStatus);
        dest.writeList(children);
        dest.writeValue(before);
    }

    public int describeContents() {
        return  0;
    }

}
