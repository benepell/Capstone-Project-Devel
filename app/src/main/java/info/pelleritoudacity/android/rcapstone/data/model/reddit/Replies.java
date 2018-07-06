package info.pelleritoudacity.android.rcapstone.data.model.reddit;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Replies implements Parcelable
{

    @SerializedName("kind")
    @Expose
    private String kind;
    @SerializedName("data")
    @Expose
    private T1Data data;
    public final static Parcelable.Creator<Replies> CREATOR = new Creator<Replies>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Replies createFromParcel(Parcel in) {
            return new Replies(in);
        }

        public Replies[] newArray(int size) {
            return (new Replies[size]);
        }

    };

    Replies(Parcel in) {
        this.kind = ((String) in.readValue((String.class.getClassLoader())));
        this.data = ((T1Data) in.readValue((T1Data.class.getClassLoader())));
    }

    public Replies() {
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public T1Data getData() {
        return data;
    }

    public void setData(T1Data data) {
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
