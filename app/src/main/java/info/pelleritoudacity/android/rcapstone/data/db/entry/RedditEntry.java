package info.pelleritoudacity.android.rcapstone.data.db.entry;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@SuppressWarnings("unused")
@Entity(tableName = "_reddit")
public class RedditEntry {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "_kind")
    private String kind;

    @ColumnInfo(name = "_data")
    private int data;

    public RedditEntry() {
    }

    @SuppressWarnings("unused")
    public int getId() {
        return id;
    }

    @SuppressWarnings("unused")
    public void setId(int id) {
        this.id = id;
    }

    @SuppressWarnings("unused")
    public String getKind() {

        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    @SuppressWarnings("unused")
    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }
}
