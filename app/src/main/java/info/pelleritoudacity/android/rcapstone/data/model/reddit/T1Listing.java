package info.pelleritoudacity.android.rcapstone.data.model.reddit;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class T1Listing implements Parcelable
{

    @SerializedName("kind")
    @Expose
    private String kind;
    @SerializedName("data")
    @Expose
    private T1ListingData data;
    public final static Parcelable.Creator<T1Listing> CREATOR = new Creator<T1Listing>() {


        @SuppressWarnings({
            "unchecked"
        })
        public T1Listing createFromParcel(Parcel in) {
            return new T1Listing(in);
        }

        public T1Listing[] newArray(int size) {
            return (new T1Listing[size]);
        }

    }
    ;

    protected T1Listing(Parcel in) {
        this.kind = ((String) in.readValue((String.class.getClassLoader())));
        this.data = ((T1ListingData) in.readValue((T1ListingData.class.getClassLoader())));
    }

    public T1Listing() {
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public T1ListingData getData() {
        return data;
    }

    public void setData(T1ListingData data) {
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
