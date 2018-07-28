
package info.pelleritoudacity.android.rcapstone.data.model.reddit;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MoreThing implements Parcelable
{

    @SerializedName("kind")
    @Expose
    private String kind;
    @SerializedName("data")
    @Expose
    private T1ListingData data;
    public final static Creator<MoreThing> CREATOR = new Creator<MoreThing>() {


        @SuppressWarnings({
            "unchecked"
        })
        public MoreThing createFromParcel(Parcel in) {
            return new MoreThing(in);
        }

        public MoreThing[] newArray(int size) {
            return (new MoreThing[size]);
        }

    }
    ;

    MoreThing(Parcel in) {
        this.kind = ((String) in.readValue((String.class.getClassLoader())));
        this.data = ((T1ListingData) in.readValue((T1ListingData.class.getClassLoader())));
    }

    public MoreThing() {
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
