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

package info.pelleritoudacity.android.rcapstone.paid.debug.ui.service;


import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import java.util.Objects;

import info.pelleritoudacity.android.rcapstone.data.db.AppDatabase;
import info.pelleritoudacity.android.rcapstone.paid.debug.ui.activity.OptionWidgetActivity;
import info.pelleritoudacity.android.rcapstone.paid.debug.ui.widget.WidgetUtil;
import info.pelleritoudacity.android.rcapstone.utility.Costant;
import info.pelleritoudacity.android.rcapstone.utility.Preference;

public class WidgetService extends IntentService {

    private static AppDatabase mDb;

    public WidgetService() {
        super("WidgetService");
    }


    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        if (Objects.requireNonNull(intent).getBooleanExtra(Costant.EXTRA_WIDGET_SERVICE, false)) {
            update(getApplicationContext());

        } else {
            stop(getApplicationContext());
            update(getApplicationContext());

        }
    }

    public static void start(Context context) {
        Intent intent = new Intent(context, WidgetService.class);
        intent.putExtra(Costant.EXTRA_WIDGET_SERVICE, true);
        context.startService(intent);

    }

    private static void stop(Context context) {
        Intent intent = new Intent(context, WidgetService.class);
        intent.putExtra(Costant.EXTRA_WIDGET_SERVICE, false);
        context.stopService(intent);
    }


    private static void update(Context context) {

        mDb = AppDatabase.getInstance(context);

        if (TextUtils.isEmpty(Preference.getWidgetCategory(context))) {
            context.startActivity(new Intent(context, OptionWidgetActivity.class)
                    .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            );

        } else {
            new WidgetUtil(context, mDb).updateData(Preference.getWidgetCategory(context));
        }


    }

}