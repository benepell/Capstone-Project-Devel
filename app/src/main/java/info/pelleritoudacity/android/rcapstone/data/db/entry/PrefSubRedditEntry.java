package info.pelleritoudacity.android.rcapstone.data.db.entry;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "_pref_sub_reddit")
public class PrefSubRedditEntry {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "_name")
    private String name;

    @ColumnInfo(name = "_image")
    private String image;

    @ColumnInfo(name = "_visible")
    private int visible;

    @ColumnInfo(name = "_removed")
    private int removed;

    @ColumnInfo(name = "_position")
    private int position;

    @ColumnInfo(name = "_backup_position")
    private int backupPosition;

    @ColumnInfo(name = "_time_last_modified")
    private Date timeLastModified;

    public PrefSubRedditEntry() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getVisible() {
        return visible;
    }

    public void setVisible(int visible) {
        this.visible = visible;
    }

    public int getRemoved() {
        return removed;
    }

    public void setRemoved(int removed) {
        this.removed = removed;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getBackupPosition() {
        return backupPosition;
    }

    public void setBackupPosition(int backupPosition) {
        this.backupPosition = backupPosition;
    }

    public Date getTimeLastModified() {
        return timeLastModified;
    }

    public void setTimeLastModified(Date timeLastModified) {
        this.timeLastModified = timeLastModified;
    }
}
