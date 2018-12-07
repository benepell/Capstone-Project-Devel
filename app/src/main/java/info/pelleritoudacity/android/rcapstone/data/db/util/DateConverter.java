package info.pelleritoudacity.android.rcapstone.data.db.util;


import java.util.Date;

import androidx.room.TypeConverter;

@SuppressWarnings("ALL")
public class DateConverter {
    @TypeConverter
    public static Date toDate(Long timestamp) {
        return timestamp == null ? null : new Date(timestamp);
    }

    @TypeConverter
    public static Long toTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }
}
