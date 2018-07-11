
package info.pelleritoudacity.android.rcapstone.data.rest;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MoreJson implements Parcelable
{

    @SerializedName("errors")
    @Expose
    private List<Object> errors = null;
    @SerializedName("data")
    @Expose
    private MoreData data;
    public final static Creator<MoreJson> CREATOR = new Creator<MoreJson>() {


        @SuppressWarnings({
            "unchecked"
        })
        public MoreJson createFromParcel(Parcel in) {
            return new MoreJson(in);
        }

        public MoreJson[] newArray(int size) {
            return (new MoreJson[size]);
        }

    }
    ;

    protected MoreJson(Parcel in) {
        in.readList(this.errors, (Object.class.getClassLoader()));
        this.data = ((MoreData) in.readValue((MoreData.class.getClassLoader())));
    }

    public MoreJson() {
    }

    public List<Object> getErrors() {
        return errors;
    }

    public void setErrors(List<Object> errors) {
        this.errors = errors;
    }

    public MoreData getData() {
        return data;
    }

    public void setData(MoreData data) {
        this.data = data;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(errors);
        dest.writeValue(data);
    }

    public int describeContents() {
        return  0;
    }

}
