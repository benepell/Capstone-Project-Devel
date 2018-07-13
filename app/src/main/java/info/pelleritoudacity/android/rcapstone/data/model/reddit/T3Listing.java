package info.pelleritoudacity.android.rcapstone.data.model.reddit;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class T3Listing implements Parcelable
{

    @SerializedName("kind")
    @Expose
    private String kind;
    @SerializedName("data")
    @Expose
    private T3ListingData data;
    public final static Parcelable.Creator<T3Listing> CREATOR = new Creator<T3Listing>() {


        @SuppressWarnings({
            "unchecked"
        })
        public T3Listing createFromParcel(Parcel in) {
            return new T3Listing(in);
        }

        public T3Listing[] newArray(int size) {
            return (new T3Listing[size]);
        }

    }
    ;

    protected T3Listing(Parcel in) {
        this.kind = ((String) in.readValue((String.class.getClassLoader())));
        this.data = ((T3ListingData) in.readValue((T3ListingData.class.getClassLoader())));
    }

    public T3Listing() {
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public T3ListingData getData() {
        return data;
    }

    public void setData(T3ListingData data) {
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
