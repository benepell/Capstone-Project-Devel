package info.pelleritoudacity.android.rcapstone.utility;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import info.pelleritoudacity.android.rcapstone.R;

public class PermissionUtils {

    public static boolean isPermissionExtStorage(Context context) {
        SharedPreferences pref = context.getSharedPreferences(context.getString(R.string.pref_write_external_storage), 0);
        return pref.getBoolean(context.getString(R.string.pref_write_external_storage), false);
    }

    public static void RequestPermissionExtStorage(Activity thisActivity) {
        if (ContextCompat.checkSelfPermission(thisActivity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            if (!ActivityCompat.shouldShowRequestPermissionRationale(thisActivity,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                ActivityCompat.requestPermissions(thisActivity,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        Costants.PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
            }
        }
    }

    public static void isDeniedPermissionExtStorage(Activity thisActivity) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(thisActivity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            thisActivity.getSharedPreferences(thisActivity.getString(R.string.pref_write_external_storage), 0).edit().clear().apply();

        }
    }

}
