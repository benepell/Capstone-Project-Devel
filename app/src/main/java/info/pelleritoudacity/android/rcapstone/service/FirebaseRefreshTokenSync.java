package info.pelleritoudacity.android.rcapstone.service;

import android.content.Context;
import android.support.annotation.NonNull;

import com.firebase.jobdispatcher.Constraint;
import com.firebase.jobdispatcher.Driver;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.Trigger;

import info.pelleritoudacity.android.rcapstone.R;
import info.pelleritoudacity.android.rcapstone.utility.Costants;
import info.pelleritoudacity.android.rcapstone.utility.PrefManager;

public class FirebaseRefreshTokenSync {
    private static boolean sInit;

    private static void scheduleFirebaseJobDispatcherSync(@NonNull final Context context, int sync_interval) {

        if (sync_interval > 0) {
            int sync_flex = sync_interval / 2;

            Driver driver = new GooglePlayDriver(context);
            FirebaseJobDispatcher dispatcher = new FirebaseJobDispatcher(driver);
            Job syncJob = dispatcher.newJobBuilder()
                    .setService(FirebaseRefreshTokenService.class)
                    .setTag(Costants.TOKEN_SYNC_TAG)
                    .setConstraints(Constraint.ON_ANY_NETWORK)
                    .setLifetime(Lifetime.FOREVER)
                    .setRecurring(true)
                    .setTrigger(Trigger.executionWindow(
                            sync_interval,
                            sync_interval + sync_flex))
                    .setReplaceCurrent(true)
                    .build();

            dispatcher.schedule(syncJob);
        }
    }

    public static void initialize(Context context) {
        if (sInit) return;
        sInit = true;
        int interval = PrefManager.getIntPref(context, R.string.pref_session_expired);
        scheduleFirebaseJobDispatcherSync(context, interval);
    }

    public static void stopJobRefreshToken(Context context, String tag) {
        Driver driver = new GooglePlayDriver(context);
        FirebaseJobDispatcher dispatcher = new FirebaseJobDispatcher(driver);
        dispatcher.cancel(tag);
    }
}
