package info.pelleritoudacity.android.rcapstone.model.reddit;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RepliesData implements Parcelable
{

    @SerializedName("modhash")
    @Expose
    private String modhash;
    @SerializedName("dist")
    @Expose
    private Object dist;
    @SerializedName("children")
    @Expose
    private List<RepliesListing> children = null;
    @SerializedName("after")
    @Expose
    private Object after;
    @SerializedName("before")
    @Expose
    private Object before;
    public final static Parcelable.Creator<RepliesData> CREATOR = new Creator<RepliesData>() {


        @SuppressWarnings({
            "unchecked"
        })
        public RepliesData createFromParcel(Parcel in) {
            return new RepliesData(in);
        }

        public RepliesData[] newArray(int size) {
            return (new RepliesData[size]);
        }

    }
    ;

    protected RepliesData(Parcel in) {
        this.modhash = ((String) in.readValue((String.class.getClassLoader())));
        this.dist = ((Object) in.readValue((Object.class.getClassLoader())));
        in.readList(this.children, (RepliesListing.class.getClassLoader()));
        this.after = ((Object) in.readValue((Object.class.getClassLoader())));
        this.before = ((Object) in.readValue((Object.class.getClassLoader())));
    }

    public RepliesData() {
    }

    public String getModhash() {
        return modhash;
    }

    public void setModhash(String modhash) {
        this.modhash = modhash;
    }

    public Object getDist() {
        return dist;
    }

    public void setDist(Object dist) {
        this.dist = dist;
    }

    public List<RepliesListing> getChildren() {
        return children;
    }

    public void setChildren(List<RepliesListing> children) {
        this.children = children;
    }

    public Object getAfter() {
        return after;
    }

    public void setAfter(Object after) {
        this.after = after;
    }

    public Object getBefore() {
        return before;
    }

    public void setBefore(Object before) {
        this.before = before;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(modhash);
        dest.writeValue(dist);
        dest.writeList(children);
        dest.writeValue(after);
        dest.writeValue(before);
    }

    public int describeContents() {
        return  0;
    }

}
