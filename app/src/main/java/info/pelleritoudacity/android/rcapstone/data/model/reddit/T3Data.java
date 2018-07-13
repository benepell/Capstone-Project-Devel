package info.pelleritoudacity.android.rcapstone.data.model.reddit;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class T3Data implements Parcelable
{

    @SerializedName("modhash")
    @Expose
    private String modhash;
    @SerializedName("dist")
    @Expose
    private Integer dist;
    @SerializedName("children")
    @Expose
    private List<T3Listing> children = null;
    @SerializedName("after")
    @Expose
    private String after;
    @SerializedName("before")
    @Expose
    private Object before;
    public final static Parcelable.Creator<T3Data> CREATOR = new Creator<T3Data>() {


        @SuppressWarnings({
            "unchecked"
        })
        public T3Data createFromParcel(Parcel in) {
            return new T3Data(in);
        }

        public T3Data[] newArray(int size) {
            return (new T3Data[size]);
        }

    }
    ;

    protected T3Data(Parcel in) {
        this.modhash = ((String) in.readValue((String.class.getClassLoader())));
        this.dist = ((Integer) in.readValue((Integer.class.getClassLoader())));
        in.readList(this.children, (T3Listing.class.getClassLoader()));
        this.after = ((String) in.readValue((String.class.getClassLoader())));
        this.before = ((Object) in.readValue((Object.class.getClassLoader())));
    }

    public T3Data() {
    }

    public String getModhash() {
        return modhash;
    }

    public void setModhash(String modhash) {
        this.modhash = modhash;
    }

    public Integer getDist() {
        return dist;
    }

    public void setDist(Integer dist) {
        this.dist = dist;
    }

    public List<T3Listing> getChildren() {
        return children;
    }

    public void setChildren(List<T3Listing> children) {
        this.children = children;
    }

    public String getAfter() {
        return after;
    }

    public void setAfter(String after) {
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
