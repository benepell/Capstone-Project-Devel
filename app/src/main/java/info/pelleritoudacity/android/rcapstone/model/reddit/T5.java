
package info.pelleritoudacity.android.rcapstone.model.reddit;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class T5 implements Parcelable
{

    @SerializedName("kind")
    @Expose
    private String kind;
    @SerializedName("data")
    @Expose
    private T5Listing data;
    public final static Parcelable.Creator<T5> CREATOR = new Creator<T5>() {


        @SuppressWarnings({
            "unchecked"
        })
        public T5 createFromParcel(Parcel in) {
            return new T5(in);
        }

        public T5[] newArray(int size) {
            return (new T5[size]);
        }

    }
    ;

    @SuppressWarnings("WeakerAccess")
    protected T5(Parcel in) {
        this.kind = ((String) in.readValue((String.class.getClassLoader())));
        this.data = ((T5Listing) in.readValue((T5Listing.class.getClassLoader())));
    }

    public T5() {
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public T5Listing getData() {
        return data;
    }

    public void setData(T5Listing data) {
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
