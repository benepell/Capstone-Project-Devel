package info.pelleritoudacity.android.rcapstone.data.model.ui;

import android.os.Parcel;
import android.os.Parcelable;

public class DetailModel implements Parcelable{

    private int id;
    private int position;
    private String strId = null;
    private String strArrId;
    private String strLinkId;

    public DetailModel() {
    }

    protected DetailModel(Parcel in) {
        id = in.readInt();
        position = in.readInt();
        strId = in.readString();
        strArrId = in.readString();
        strLinkId = in.readString();
    }

    public static final Creator<DetailModel> CREATOR = new Creator<DetailModel>() {
        @Override
        public DetailModel createFromParcel(Parcel in) {
            return new DetailModel(in);
        }

        @Override
        public DetailModel[] newArray(int size) {
            return new DetailModel[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getStrId() {
        return strId;
    }

    public void setStrId(String strId) {
        this.strId = strId;
    }

    public String getStrArrId() {
        return strArrId;
    }

    public void setStrArrId(String strArrId) {
        this.strArrId = strArrId;
    }

    public String getStrLinkId() {
        return strLinkId;
    }

    public void setStrLinkId(String strLinkId) {
        this.strLinkId = strLinkId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeInt(position);
        parcel.writeString(strId);
        parcel.writeString(strArrId);
        parcel.writeString(strLinkId);
    }
}
