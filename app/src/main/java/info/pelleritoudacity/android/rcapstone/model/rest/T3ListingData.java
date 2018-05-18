
package info.pelleritoudacity.android.rcapstone.model.rest;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class T3ListingData implements Parcelable
{

    @SerializedName("kind")
    @Expose
    private String kind;
    @SerializedName("data")
    @Expose
    private T3Data data;
    public final static Parcelable.Creator<T3ListingData> CREATOR = new Creator<T3ListingData>() {


        @SuppressWarnings({
            "unchecked"
        })
        public T3ListingData createFromParcel(Parcel in) {
            return new T3ListingData(in);
        }

        public T3ListingData[] newArray(int size) {
            return (new T3ListingData[size]);
        }

    }
    ;

    protected T3ListingData(Parcel in) {
        this.kind = ((String) in.readValue((String.class.getClassLoader())));
        this.data = ((T3Data) in.readValue((T3Data.class.getClassLoader())));
    }

    public T3ListingData() {
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public T3Data getData() {
        return data;
    }

    public void setData(T3Data data) {
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
