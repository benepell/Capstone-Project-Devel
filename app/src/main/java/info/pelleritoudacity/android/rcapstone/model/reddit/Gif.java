package info.pelleritoudacity.android.rcapstone.model.reddit;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Gif implements Parcelable
{

    @SerializedName("source")
    @Expose
    private SourceGif source;
    @SerializedName("resolutions")
    @Expose
    private List<ResolutionGif> resolutions = null;
    public final static Parcelable.Creator<Gif> CREATOR = new Creator<Gif>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Gif createFromParcel(Parcel in) {
            return new Gif(in);
        }

        public Gif[] newArray(int size) {
            return (new Gif[size]);
        }

    }
    ;

    protected Gif(Parcel in) {
        this.source = ((SourceGif) in.readValue((SourceGif.class.getClassLoader())));
        in.readList(this.resolutions, (ResolutionGif.class.getClassLoader()));
    }

    public Gif() {
    }

    public SourceGif getSource() {
        return source;
    }

    public void setSource(SourceGif source) {
        this.source = source;
    }

    public List<ResolutionGif> getResolutions() {
        return resolutions;
    }

    public void setResolutions(List<ResolutionGif> resolutions) {
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
