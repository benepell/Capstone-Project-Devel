package info.pelleritoudacity.android.rcapstone.data.model.reddit;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class T1Data implements Parcelable
{

    @SerializedName("modhash")
    @Expose
    private String modhash;
    @SerializedName("dist")
    @Expose
    private Integer dist;
    @SerializedName("children")
    @Expose
    private List<T1Listing> children = null;
    @SerializedName("after")
    @Expose
    private Object after;
    @SerializedName("before")
    @Expose
    private Object before;
    public final static Parcelable.Creator<T1Data> CREATOR = new Creator<T1Data>() {


        @SuppressWarnings({
            "unchecked"
        })
        public T1Data createFromParcel(Parcel in) {
            return new T1Data(in);
        }

        public T1Data[] newArray(int size) {
            return (new T1Data[size]);
        }

    }
    ;

    T1Data(Parcel in) {
        this.modhash = ((String) in.readValue((String.class.getClassLoader())));
        this.dist = ((Integer) in.readValue((Integer.class.getClassLoader())));
        in.readList(this.children, (T1Listing.class.getClassLoader()));
        this.after = in.readValue((Object.class.getClassLoader()));
        this.before = in.readValue((Object.class.getClassLoader()));
    }

    public T1Data() {
    }

    @SuppressWarnings("unused")
    public String getModhash() {
        return modhash;
    }

    @SuppressWarnings("unused")
    public void setModhash(String modhash) {
        this.modhash = modhash;
    }

    @SuppressWarnings("unused")
    public Integer getDist() {
        return dist;
    }

    @SuppressWarnings("unused")
    public void setDist(Integer dist) {
        this.dist = dist;
    }

    public List<T1Listing> getChildren() {
        return children;
    }

    @SuppressWarnings("unused")
    public void setChildren(List<T1Listing> children) {
        this.children = children;
    }

    @SuppressWarnings("unused")
    public Object getAfter() {
        return after;
    }

    @SuppressWarnings("unused")
    public void setAfter(Object after) {
        this.after = after;
    }

    @SuppressWarnings("unused")
    public Object getBefore() {
        return before;
    }

    @SuppressWarnings("unused")
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
