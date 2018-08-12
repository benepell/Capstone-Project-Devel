package info.pelleritoudacity.android.rcapstone.data.model.ui;

import android.os.Parcel;
import android.os.Parcelable;

public class MainModel implements Parcelable {

    private int windowPlayer;
    private int mediaType;


    private int position;
    private int positionTab;
    private long positionPlayer;
    private String category;
    private String target;
    private String querySearch;
    private boolean ima;
    private boolean autoplay;
    private boolean over18;

    public MainModel() {
    }

    private MainModel(Parcel in) {
        windowPlayer = in.readInt();
        mediaType = in.readInt();
        position = in.readInt();
        positionTab = in.readInt();
        positionPlayer = in.readLong();
        category = in.readString();
        target = in.readString();
        querySearch = in.readString();
        ima = in.readByte() != 0;
        autoplay = in.readByte() != 0;
        over18 = in.readByte() != 0;
    }

    public static final Creator<MainModel> CREATOR = new Creator<MainModel>() {
        @Override
        public MainModel createFromParcel(Parcel in) {
            return new MainModel(in);
        }

        @Override
        public MainModel[] newArray(int size) {
            return new MainModel[size];
        }
    };

    @SuppressWarnings("unused")
    public int getWindowPlayer() {
        return windowPlayer;
    }

    public void setWindowPlayer(int windowPlayer) {
        this.windowPlayer = windowPlayer;
    }

    @SuppressWarnings("unused")
    public int getMediaType() {
        return mediaType;
    }

    @SuppressWarnings("unused")
    public void setMediaType(int mediaType) {
        this.mediaType = mediaType;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    @SuppressWarnings("unused")
    public int getPositionTab() {
        return positionTab;
    }

    public void setPositionTab(int positionTab) {
        this.positionTab = positionTab;
    }

    @SuppressWarnings("unused")
    public long getPositionPlayer() {
        return positionPlayer;
    }

    public void setPositionPlayer(long positionPlayer) {
        this.positionPlayer = positionPlayer;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getQuerySearch() {
        return querySearch;
    }

    public void setQuerySearch(String querySearch) {
        this.querySearch = querySearch;
    }

    @SuppressWarnings("unused")
    public boolean isIma() {
        return ima;
    }

    public void setIma(boolean ima) {
        this.ima = ima;
    }

    @SuppressWarnings("unused")
    public boolean isAutoplay() {
        return autoplay;
    }

    public void setAutoplay(boolean autoplay) {
        this.autoplay = autoplay;
    }

    public boolean isOver18() {
        return over18;
    }

    public void setOver18(boolean over18) {
        this.over18 = over18;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(windowPlayer);
        dest.writeInt(mediaType);
        dest.writeInt(position);
        dest.writeInt(positionTab);
        dest.writeLong(positionPlayer);
        dest.writeString(category);
        dest.writeString(target);
        dest.writeString(querySearch);
        dest.writeByte((byte) (ima ? 1 : 0));
        dest.writeByte((byte) (autoplay ? 1 : 0));
        dest.writeByte((byte) (over18 ? 1 : 0));
    }
}
