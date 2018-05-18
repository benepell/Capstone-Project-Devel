
package info.pelleritoudacity.android.rcapstone.model.rest;

import android.os.Parcel;
import android.os.Parcelable;

public class MediaEmbed implements Parcelable
{

    public final static Parcelable.Creator<MediaEmbed> CREATOR = new Creator<MediaEmbed>() {


        @SuppressWarnings({
            "unchecked"
        })
        public MediaEmbed createFromParcel(Parcel in) {
            return new MediaEmbed(in);
        }

        public MediaEmbed[] newArray(int size) {
            return (new MediaEmbed[size]);
        }

    }
    ;

    protected MediaEmbed(Parcel in) {
    }

    public MediaEmbed() {
    }

    public void writeToParcel(Parcel dest, int flags) {
    }

    public int describeContents() {
        return  0;
    }

}
