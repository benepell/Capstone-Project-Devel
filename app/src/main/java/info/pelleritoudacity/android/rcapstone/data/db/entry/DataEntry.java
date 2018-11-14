package info.pelleritoudacity.android.rcapstone.data.db.entry;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "_data")
public class DataEntry {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "_after")
    private String after;

    @ColumnInfo(name = "_dist")
    private int dist;

    @ColumnInfo(name = "_mod_hash")
    private String modHash;

    @ColumnInfo(name = "_whitelist_status")
    private String whitelistStatus;

    @ColumnInfo(name = "_childrens")
    private int childrens;

    @ColumnInfo(name = "_before")
    private String before;

    public DataEntry() {
    }

    public int getId() {
        return id;
    }

    public String getAfter() {
        return after;
    }

    public void setAfter(String after) {
        this.after = after;
    }

    public int getDist() {
        return dist;
    }

    public void setDist(int dist) {
        this.dist = dist;
    }

    public String getModHash() {
        return modHash;
    }

    public void setModHash(String modHash) {
        this.modHash = modHash;
    }

    public String getWhitelistStatus() {
        return whitelistStatus;
    }

    public void setWhitelistStatus(String whitelistStatus) {
        this.whitelistStatus = whitelistStatus;
    }

    public int getChildrens() {
        return childrens;
    }

    public void setChildrens(int childrens) {
        this.childrens = childrens;
    }

    public String getBefore() {
        return before;
    }

    public void setBefore(String before) {
        this.before = before;
    }
}
