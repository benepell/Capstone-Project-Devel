
package info.pelleritoudacity.android.rcapstone.model;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data implements Parcelable
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
    private List<Children> children = null;
    @SerializedName("before")
    @Expose
    private Object before;
    public final static Parcelable.Creator<Data> CREATOR = new Creator<Data>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Data createFromParcel(Parcel in) {
            return new Data(in);
        }

        public Data[] newArray(int size) {
            return (new Data[size]);
        }

    }
            ;

    protected Data(Parcel in) {
        this.after = ((String) in.readValue((String.class.getClassLoader())));
        this.dist = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.modhash = ((String) in.readValue((String.class.getClassLoader())));
        this.whitelistStatus = ((String) in.readValue((String.class.getClassLoader())));
        in.readList(this.children, (info.pelleritoudacity.android.rcapstone.model.Children.class.getClassLoader()));
        this.before = in.readValue((Object.class.getClassLoader()));
    }

    public Data() {
    }

    public String getAfter() {
        return after;
    }

    public void setAfter(String after) {
        this.after = after;
    }

    public Integer getDist() {
        return dist;
    }

    public void setDist(Integer dist) {
        this.dist = dist;
    }

    public String getModhash() {
        return modhash;
    }

    public void setModhash(String modhash) {
        this.modhash = modhash;
    }

    public String getWhitelistStatus() {
        return whitelistStatus;
    }

    public void setWhitelistStatus(String whitelistStatus) {
        this.whitelistStatus = whitelistStatus;
    }

    public List<Children> getChildren() {
        return children;
    }

    public void setChildren(List<Children> children) {
        this.children = children;
    }

    public Object getBefore() {
        return before;
    }

    public void setBefore(Object before) {
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
