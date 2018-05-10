
package info.pelleritoudacity.android.rcapstone.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class T3 implements Parcelable
{

    @SerializedName("kind")
    @Expose
    private String kind;
    @SerializedName("data")
    @Expose
    private T3Listing data;
    public final static Parcelable.Creator<T3> CREATOR = new Creator<T3>() {


        @SuppressWarnings({
            "unchecked"
        })
        public T3 createFromParcel(Parcel in) {
            return new T3(in);
        }

        public T3[] newArray(int size) {
            return (new T3[size]);
        }

    }
    ;

    @SuppressWarnings("WeakerAccess")
    protected T3(Parcel in) {
        this.kind = ((String) in.readValue((String.class.getClassLoader())));
        this.data = ((T3Listing) in.readValue((T3Listing.class.getClassLoader())));
    }

    public T3() {
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public T3Listing getData() {
        return data;
    }

    public void setData(T3Listing data) {
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
