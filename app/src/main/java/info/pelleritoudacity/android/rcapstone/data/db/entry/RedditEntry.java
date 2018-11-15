package info.pelleritoudacity.android.rcapstone.data.db.entry;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;


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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKind() {

        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }
}
