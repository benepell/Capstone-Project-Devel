
package info.pelleritoudacity.android.rcapstone.data.rest;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("ALL")
public class More implements Parcelable
{

    @SerializedName("json")
    @Expose
    private MoreJson json;
    public final static Creator<More> CREATOR = new Creator<More>() {


        @SuppressWarnings({
            "unchecked"
        })
        public More createFromParcel(Parcel in) {
            return new More(in);
        }

        public More[] newArray(int size) {
            return (new More[size]);
        }

    }
    ;

    protected More(Parcel in) {
        this.json = ((MoreJson) in.readValue((MoreJson.class.getClassLoader())));
    }

    public More() {
    }

    public MoreJson getJson() {
        return json;
    }

    public void setJson(MoreJson json) {
        this.json = json;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(json);
    }

    public int describeContents() {
        return  0;
    }

}
