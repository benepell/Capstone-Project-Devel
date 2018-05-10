
package info.pelleritoudacity.android.rcapstone.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("ALL")
public class T5ListingData implements Parcelable
{

    @SerializedName("kind")
    @Expose
    private String kind;
    @SerializedName("data")
    @Expose
    private T5Data data;
    public final static Parcelable.Creator<T5ListingData> CREATOR = new Creator<T5ListingData>() {


        @SuppressWarnings({
            "unchecked"
        })
        public T5ListingData createFromParcel(Parcel in) {
            return new T5ListingData(in);
        }

        public T5ListingData[] newArray(int size) {
            return (new T5ListingData[size]);
        }

    }
    ;

    @SuppressWarnings("WeakerAccess")
    protected T5ListingData(Parcel in) {
        this.kind = ((String) in.readValue((String.class.getClassLoader())));
        this.data = ((T5Data) in.readValue((T5Data.class.getClassLoader())));
    }

    public T5ListingData() {
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
