package info.pelleritoudacity.android.rcapstone.data;

import android.database.Cursor;
import android.database.sqlite.SQLiteCursorDriver;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQuery;

public class LeaklessCursorFactory implements SQLiteDatabase.CursorFactory {
    @Override
    public Cursor newCursor(SQLiteDatabase db, SQLiteCursorDriver masterQuery,
                            String editTable, SQLiteQuery query) {
        return new LeaklessCursor(db, masterQuery, editTable, query);
    }
}