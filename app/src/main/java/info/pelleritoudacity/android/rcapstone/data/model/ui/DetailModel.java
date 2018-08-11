package info.pelleritoudacity.android.rcapstone.data.model.ui;

import android.os.Parcel;
import android.os.Parcelable;

public class DetailModel implements Parcelable{

    private int id;
    private int position;
    private int target;
    private String category;
    private String strId = null;
    private String strArrId;
    private String strLinkId;
    private String strQuerySearch;

    public DetailModel() {
    }

    private DetailModel(Parcel in) {
        id = in.readInt();
        position = in.readInt();
        category = in.readString();
        strId = in.readString();
        strArrId = in.readString();
        strLinkId = in.readString();
        target = in.readInt();
        strQuerySearch = in.readString();
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

    @SuppressWarnings("unused")
    public int getPosition() {
        return position;
    }

    @SuppressWarnings("unused")
    public void setPosition(int position) {
        this.position = position;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
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

    public int getTarget() {
        return target;
    }

    public void setTarget(int target) {
        this.target = target;
    }

    public String getStrQuerySearch() {
        return strQuerySearch;
    }

    public void setStrQuerySearch(String strQuerySearch) {
        this.strQuerySearch = strQuerySearch;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeInt(position);
        parcel.writeString(category);
        parcel.writeString(strId);
        parcel.writeString(strArrId);
        parcel.writeString(strLinkId);
        parcel.writeInt(target);
        parcel.writeString(strQuerySearch);
    }
}
