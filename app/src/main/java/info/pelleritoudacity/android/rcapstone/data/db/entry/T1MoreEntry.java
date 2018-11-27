package info.pelleritoudacity.android.rcapstone.data.db.entry;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;


import info.pelleritoudacity.android.rcapstone.data.db.util.DateConverter;

@Entity(tableName = "_t1More")
public class T1MoreEntry {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @TypeConverters(DateConverter.class)
    @ColumnInfo(name = "_time_last_modified")
    private long timeLastModified;

    @ColumnInfo(name = "_more_count")
    private int moreCount;

    @ColumnInfo(name = "_more_name")
    private String moreName;

    @ColumnInfo(name = "_more_id")
    private String moreId;

    @ColumnInfo(name = "_mores_parent_id")
    private String moresParentId;

    @ColumnInfo(name = "_more_depth")
    private int moreDepth;

    @ColumnInfo(name = "_more_children")
    private String moreChildren;

    public T1MoreEntry(){
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getTimeLastModified() {
        return timeLastModified;
    }

    public void setTimeLastModified(long timeLastModified) {
        this.timeLastModified = timeLastModified;
    }

    public int getMoreCount() {
        return moreCount;
    }

    public void setMoreCount(int moreCount) {
        this.moreCount = moreCount;
    }

    public String getMoreName() {
        return moreName;
    }

    public void setMoreName(String moreName) {
        this.moreName = moreName;
    }

    public String getMoreId() {
        return moreId;
    }

    public void setMoreId(String moreId) {
        this.moreId = moreId;
    }

    public String getMoresParentId() {
        return moresParentId;
    }

    public void setMoresParentId(String moresParentId) {
        this.moresParentId = moresParentId;
    }

    public int getMoreDepth() {
        return moreDepth;
    }

    public void setMoreDepth(int moreDepth) {
        this.moreDepth = moreDepth;
    }

    public String getMoreChildren() {
        return moreChildren;
    }

    public void setMoreChildren(String moreChildren) {
        this.moreChildren = moreChildren;
    }
}

