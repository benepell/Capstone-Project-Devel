package info.pelleritoudacity.android.rcapstone.data.db.entry;



import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;
import info.pelleritoudacity.android.rcapstone.data.db.util.DateConverter;

@Entity(tableName = "_pref_sub_reddit")
public class PrefSubRedditEntry {

    @ColumnInfo(name = "id")
    private int id;

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "_name")
    private String name = "";

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

    @TypeConverters(DateConverter.class)
    @ColumnInfo(name = "_time_last_modified")
    private long timeLastModified;

    public PrefSubRedditEntry() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
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

    public long getTimeLastModified() {
        return timeLastModified;
    }

    public void setTimeLastModified(long timeLastModified) {
        this.timeLastModified = timeLastModified;
    }
}
