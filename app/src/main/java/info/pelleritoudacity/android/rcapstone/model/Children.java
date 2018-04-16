
package info.pelleritoudacity.android.rcapstone.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Children implements Parcelable
{

    @SerializedName("kind")
    @Expose
    private String kind;
    @SerializedName("data")
    @Expose
    private T5Data data;
    public final static Parcelable.Creator<Children> CREATOR = new Creator<Children>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Children createFromParcel(Parcel in) {
            return new Children(in);
        }

        public Children[] newArray(int size) {
            return (new Children[size]);
        }

    }
    ;

    @SuppressWarnings("WeakerAccess")
    protected Children(Parcel in) {
        this.kind = ((String) in.readValue((String.class.getClassLoader())));
        this.data = ((T5Data) in.readValue((T5Data.class.getClassLoader())));
    }

    public Children() {
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public T5Data getData() {
        return data;
    }

    public void setData(T5Data data) {
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
