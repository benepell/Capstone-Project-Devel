
package info.pelleritoudacity.android.rcapstone.data.model.reddit;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class T3 implements Parcelable
{

    @SerializedName("kind")
    @Expose
    private String kind;
    @SerializedName("data")
    @Expose
    private T3Data data;
    public final static Creator<T3> CREATOR = new Creator<T3>() {


        @SuppressWarnings({
            "unchecked"
        })
        public T3 createFromParcel(Parcel in) {
            return new T3(in);
        }

        public T3 [] newArray(int size) {
            return (new T3[size]);
        }

    }
    ;

    private T3(Parcel in) {
        this.kind = ((String) in.readValue((String.class.getClassLoader())));
        this.data = ((T3Data) in.readValue((T3Data.class.getClassLoader())));
    }

    @SuppressWarnings("unused")
    public T3() {
    }

    public String getKind() {
        return kind;
    }

    @SuppressWarnings("unused")
    public void setKind(String kind) {
        this.kind = kind;
    }

    public T3Data getData() {
        return data;
    }

    @SuppressWarnings("unused")
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
