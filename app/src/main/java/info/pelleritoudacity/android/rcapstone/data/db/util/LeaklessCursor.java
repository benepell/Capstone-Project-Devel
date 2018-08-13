package info.pelleritoudacity.android.rcapstone.data.db.util;

import android.database.sqlite.SQLiteCursor;
import android.database.sqlite.SQLiteCursorDriver;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQuery;

class LeaklessCursor extends SQLiteCursor {

    public LeaklessCursor( SQLiteCursorDriver driver,
                          String editTable, SQLiteQuery query) {
        super(driver, editTable, query);
    }

    @Override
    public void close() {
        final SQLiteDatabase db = getDatabase();
        if (db != null) {
            db.close();

        }
    }

} 