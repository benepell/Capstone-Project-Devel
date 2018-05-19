package info.pelleritoudacity.android.rcapstone.model.reddit;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Mp4 implements Parcelable
{

    @SerializedName("source")
    @Expose
    private SourceMp4 source;
    @SerializedName("resolutions")
    @Expose
    private List<ResolutionMp4> resolutions = null;
    public final static Parcelable.Creator<Mp4> CREATOR = new Creator<Mp4>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Mp4 createFromParcel(Parcel in) {
            return new Mp4(in);
        }

        public Mp4 [] newArray(int size) {
            return (new Mp4[size]);
        }

    }
    ;

    protected Mp4(Parcel in) {
        this.source = ((SourceMp4) in.readValue((SourceMp4.class.getClassLoader())));
        in.readList(this.resolutions, (ResolutionMp4.class.getClassLoader()));
    }

    public Mp4() {
    }

    public SourceMp4 getSource() {
        return source;
    }

    public void setSource(SourceMp4 source) {
        this.source = source;
    }

    public List<ResolutionMp4> getResolutions() {
        return resolutions;
    }

    public void setResolutions(List<ResolutionMp4> resolutions) {
        this.resolutions = resolutions;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(source);
        dest.writeList(resolutions);
    }

    public int describeContents() {
        return  0;
    }

}
