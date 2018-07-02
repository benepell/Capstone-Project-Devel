package info.pelleritoudacity.android.rcapstone.data.db.util;

import android.database.sqlite.SQLiteCursor;
import android.database.sqlite.SQLiteCursorDriver;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQuery;

import timber.log.Timber;

public class LeaklessCursor extends SQLiteCursor {

    public LeaklessCursor(SQLiteDatabase db, SQLiteCursorDriver driver,
                          String editTable, SQLiteQuery query) {
        super(driver, editTable, query);
    }

    @Override
    public void close() {
        final SQLiteDatabase db = getDatabase();
        if (db != null) {
            Timber.d("Closing LeaklessCursor: %s", db.getPath());
            db.close();

        }
    }
} 