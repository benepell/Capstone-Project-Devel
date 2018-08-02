package info.pelleritoudacity.android.rcapstone.data.model.ui;

import android.os.Parcel;
import android.os.Parcelable;
import android.widget.ImageButton;

public class CardBottomModel implements Parcelable {

    private ImageButton[] arrayButton;
    private String backgroundColor;
    private String linkComment;
    private String category;
    private boolean saved;
    private boolean online;

    private CardBottomModel(Parcel in) {
        backgroundColor = in.readString();
        linkComment = in.readString();
        category = in.readString();
        saved = in.readByte() != 0;
        online = in.readByte() != 0;
        logged = in.readByte() != 0;
    }

    public static final Creator<CardBottomModel> CREATOR = new Creator<CardBottomModel>() {
        @Override
        public CardBottomModel createFromParcel(Parcel in) {
            return new CardBottomModel(in);
        }

        @Override
        public CardBottomModel[] newArray(int size) {
            return new CardBottomModel[size];
        }
    };

    public ImageButton[] getArrayButton() {
        return arrayButton;
    }

    public void setArrayButton(ImageButton[] arrayButton) {
        this.arrayButton = arrayButton;
    }

    public String getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public String getLinkComment() {
        return linkComment;
    }

    public void setLinkComment(String linkComment) {
        this.linkComment = linkComment;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public boolean isSaved() {
        return saved;
    }

    public void setSaved(boolean saved) {
        this.saved = saved;
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }

    public boolean isLogged() {
        return logged;
    }

    public void setLogged(boolean logged) {
        this.logged = logged;
    }

    private boolean logged;


    public CardBottomModel() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(backgroundColor);
        dest.writeString(linkComment);
        dest.writeString(category);
        dest.writeByte((byte) (saved ? 1 : 0));
        dest.writeByte((byte) (online ? 1 : 0));
        dest.writeByte((byte) (logged ? 1 : 0));
    }
}
