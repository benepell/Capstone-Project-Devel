package info.pelleritoudacity.android.rcapstone.model.rest;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Variants implements Parcelable
{

    @SerializedName("gif")
    @Expose
    private Gif gif;
    @SerializedName("mp4")
    @Expose
    private Mp4 mp4;
    public final static Parcelable.Creator<Variants> CREATOR = new Creator<Variants>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Variants createFromParcel(Parcel in) {
            return new Variants(in);
        }

        public Variants[] newArray(int size) {
            return (new Variants[size]);
        }

    }
            ;

    protected Variants(Parcel in) {
        this.gif = ((Gif) in.readValue((Gif.class.getClassLoader())));
        this.mp4 = ((Mp4) in.readValue((Mp4.class.getClassLoader())));
    }

    public Variants() {
    }

    public Gif getGif() {
        return gif;
    }

    public void setGif(Gif gif) {
        this.gif = gif;
    }

    public Mp4 getMp4() {
        return mp4;
    }

    public void setMp4(Mp4 mp4) {
        this.mp4 = mp4;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(gif);
        dest.writeValue(mp4);
    }

    public int describeContents() {
        return  0;
    }

}
