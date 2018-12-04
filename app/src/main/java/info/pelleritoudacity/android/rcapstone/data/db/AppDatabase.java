package info.pelleritoudacity.android.rcapstone.data.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import info.pelleritoudacity.android.rcapstone.BuildConfig;
import info.pelleritoudacity.android.rcapstone.data.db.dao.DataDao;
import info.pelleritoudacity.android.rcapstone.data.db.dao.PrefSubRedditDao;
import info.pelleritoudacity.android.rcapstone.data.db.dao.RedditDao;
import info.pelleritoudacity.android.rcapstone.data.db.dao.T1Dao;
import info.pelleritoudacity.android.rcapstone.data.db.dao.T1MoreDao;
import info.pelleritoudacity.android.rcapstone.data.db.dao.T3Dao;
import info.pelleritoudacity.android.rcapstone.data.db.dao.T5Dao;
import info.pelleritoudacity.android.rcapstone.data.db.entry.DataEntry;
import info.pelleritoudacity.android.rcapstone.data.db.entry.PrefSubRedditEntry;
import info.pelleritoudacity.android.rcapstone.data.db.entry.RedditEntry;
import info.pelleritoudacity.android.rcapstone.data.db.entry.T1Entry;
import info.pelleritoudacity.android.rcapstone.data.db.entry.T1MoreEntry;
import info.pelleritoudacity.android.rcapstone.data.db.entry.T3Entry;
import info.pelleritoudacity.android.rcapstone.data.db.entry.T5Entry;
import info.pelleritoudacity.android.rcapstone.data.db.util.DateConverter;
import timber.log.Timber;

@Database(entities = {DataEntry.class, RedditEntry.class, PrefSubRedditEntry.class, T1Entry.class, T3Entry.class, T5Entry.class, T1MoreEntry.class},
        version = 1, exportSchema = false)
@TypeConverters(DateConverter.class)
public abstract class AppDatabase extends RoomDatabase {

    private static final Object LOCK = new Object();
    private static AppDatabase sInstance;

    public static AppDatabase getInstance(Context context) {
        if (sInstance == null) {
            synchronized (LOCK) {
                Timber.d("Creating new database instance");
                sInstance = Room.databaseBuilder(context.getApplicationContext(),
                        AppDatabase.class, BuildConfig.APPLICATION_ID.concat(".provider"))
                        .fallbackToDestructiveMigration()
                        .build();

            }
        }
        Timber.d("Getting the database instance %s", BuildConfig.APPLICATION_ID.concat(".provider"));
        return sInstance;
    }

    public abstract DataDao dataDao();

    public abstract RedditDao redditDao();

    public abstract PrefSubRedditDao prefSubRedditDao();

    public abstract T1Dao t1Dao();

    public abstract T1MoreDao t1MoreDao();

    public abstract T3Dao t3Dao();

    public abstract T5Dao t5Dao();

}
