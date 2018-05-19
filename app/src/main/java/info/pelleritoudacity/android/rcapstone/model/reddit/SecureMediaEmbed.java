
package info.pelleritoudacity.android.rcapstone.model.reddit;

import android.os.Parcel;
import android.os.Parcelable;

public class SecureMediaEmbed implements Parcelable
{

    public final static Parcelable.Creator<SecureMediaEmbed> CREATOR = new Creator<SecureMediaEmbed>() {


        @SuppressWarnings({
            "unchecked"
        })
        public SecureMediaEmbed createFromParcel(Parcel in) {
            return new SecureMediaEmbed(in);
        }

        public SecureMediaEmbed[] newArray(int size) {
            return (new SecureMediaEmbed[size]);
        }

    }
    ;

    protected SecureMediaEmbed(Parcel in) {
    }

    public SecureMediaEmbed() {
    }

    public void writeToParcel(Parcel dest, int flags) {
    }

    public int describeContents() {
        return  0;
    }

}
