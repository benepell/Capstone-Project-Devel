package info.pelleritoudacity.android.rcapstone.data.model.reddit;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

@SuppressWarnings("ALL")
public class SubmitData implements Parcelable {

    @SerializedName("jquery")
    @Expose
    private List<List<String>> jquery = null;

    private boolean success;

    public final static Parcelable.Creator<SubmitData> CREATOR = new Creator<SubmitData>() {


        @SuppressWarnings({
                "unchecked"
        })
        public SubmitData createFromParcel(Parcel in) {
            return new SubmitData(in);
        }

        public SubmitData[] newArray(int size) {
            return (new SubmitData[size]);
        }

    };

    protected SubmitData(Parcel in) {

        in.readList(this.jquery, String.class.getClassLoader());
        this.success = ((Boolean) in.readValue((Boolean.class.getClassLoader())));

    }


    public SubmitData(boolean success, List<List<String>> jquery) {
        this.success = success;
        this.jquery = jquery;
    }

    public List<List<String>> getJquery() {
        return jquery;
    }

    public void setJquery(List<List<String>> jquery) {
        this.jquery = jquery;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void writeToParcel(Parcel dest, int flags) {

        dest.writeList(jquery);
        dest.writeValue(success);
    }

    public int describeContents() {
        return 0;
    }

}