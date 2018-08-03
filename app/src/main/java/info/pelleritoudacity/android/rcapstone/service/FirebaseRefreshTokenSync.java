/*
 *
 * ______                _____ _
 * | ___ \              /  ___| |
 * | |_/ /___ __ _ _ __ \ `--.| |_ ___  _ __   ___
 * |    // __/ _` | '_ \ `--. \ __/ _ \| '_ \ / _ \
 * | |\ \ (_| (_| | |_) /\__/ / || (_) | | | |  __/
 * \_| \_\___\__,_| .__/\____/ \__\___/|_| |_|\___|
 *                | |
 *                |_|
 *
 * Copyright (C) 2018 Benedetto Pellerito
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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

import info.pelleritoudacity.android.rcapstone.utility.Costant;

public class FirebaseRefreshTokenSync {
    private static boolean sInit;

    private static void scheduleFirebaseJobDispatcherSync(@NonNull final Context context, int sync_interval) {

        if (sync_interval > 0) {
            int sync_flex = sync_interval / 2;

            Driver driver = new GooglePlayDriver(context);
            FirebaseJobDispatcher dispatcher = new FirebaseJobDispatcher(driver);
            Job syncJob = dispatcher.newJobBuilder()
                    .setService(FirebaseRefreshTokenService.class)
                    .setTag(Costant.TOKEN_SYNC_TAG)
                    .setConstraints(Constraint.ON_UNMETERED_NETWORK)
                    .setLifetime(Lifetime.UNTIL_NEXT_BOOT)
                    .setRecurring(false)
                    .setTrigger(Trigger.executionWindow(
                            sync_interval,
                            sync_interval + sync_flex))
                    .setReplaceCurrent(false)
                    .build();

            dispatcher.schedule(syncJob);
        }
    }

    public static void init(Context context, int interval) {
        if (sInit) return;
        sInit = true;
        scheduleFirebaseJobDispatcherSync(context, interval);
    }

    @SuppressWarnings("SameParameterValue")
    private static void stopJobRefreshToken(Context context, String tag) {
        Driver driver = new GooglePlayDriver(context);
        FirebaseJobDispatcher dispatcher = new FirebaseJobDispatcher(driver);
        dispatcher.cancel(tag);
    }

    public static void stopLogin(Context context) {
        stopJobRefreshToken(context, Costant.TOKEN_SYNC_TAG);

    }

}
