package info.pelleritoudacity.android.rcapstone.application;

import android.app.Application;

import timber.log.Timber;


class Apps extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Timber.plant(new Timber.DebugTree());
    }
}
