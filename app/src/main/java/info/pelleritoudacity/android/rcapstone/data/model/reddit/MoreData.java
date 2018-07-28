
package info.pelleritoudacity.android.rcapstone.data.model.reddit;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class MoreData implements Parcelable
{

    @SerializedName("things")
    @Expose
    private List<MoreThing> things = null;
    public final static Creator<MoreData> CREATOR = new Creator<MoreData>() {


        @SuppressWarnings({
            "unchecked"
        })
        public MoreData createFromParcel(Parcel in) {
            return new MoreData(in);
        }

        public MoreData[] newArray(int size) {
            return (new MoreData[size]);
        }

    }
    ;

    MoreData(Parcel in) {
        in.readList(this.things, (MoreThing.class.getClassLoader()));
    }

    public MoreData() {
    }

    public List<MoreThing> getThings() {
        return things;
    }

    public void setThings(List<MoreThing> things) {
        this.things = things;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(things);
    }

    public int describeContents() {
        return  0;
    }

}
