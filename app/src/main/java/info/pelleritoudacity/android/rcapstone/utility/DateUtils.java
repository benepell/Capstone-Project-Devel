package info.pelleritoudacity.android.rcapstone.utility;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class DateUtils {

    public static int getHourCurrentCreatedUtc(long createdUtc) {
        Long now = System.currentTimeMillis();
        Long diffTime = now / 1000 - createdUtc;
        return Math.round(diffTime / 3600);
    }

    public static int getSecondsTimeStamp(String timestamp) {

        Date date;
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(
                    "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
            date = dateFormat.parse(timestamp);
            Long diffTime = System.currentTimeMillis() - date.getTime();

            return (int) TimeUnit.MILLISECONDS.toSeconds(diffTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

}
