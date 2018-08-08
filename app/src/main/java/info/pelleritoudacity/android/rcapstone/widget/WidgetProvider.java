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

package info.pelleritoudacity.android.rcapstone.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.TaskStackBuilder;
import android.view.View;
import android.widget.RemoteViews;

import java.util.Objects;

import info.pelleritoudacity.android.rcapstone.R;
import info.pelleritoudacity.android.rcapstone.service.RemoteWidgetService;
import info.pelleritoudacity.android.rcapstone.service.WidgetService;
import info.pelleritoudacity.android.rcapstone.ui.activity.MainActivity;
import info.pelleritoudacity.android.rcapstone.ui.activity.SettingsActivity;
import info.pelleritoudacity.android.rcapstone.utility.Costant;
import info.pelleritoudacity.android.rcapstone.utility.Preference;

public class WidgetProvider extends AppWidgetProvider {

    private RemoteViews viewsUpdateRecipeWidget(Context context) {

        int id = Preference.getWidgetId(context);

        String title = context.getString(R.string.app_name) + ": " + Costant.CATEGORY_POPULAR;

        RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.widget_main);

        rv.setTextViewText(R.id.widget_title, title);

        rv.setImageViewResource(R.id.widget_settings, R.drawable.ic_widget_settings);
        Intent settingsIntent = new Intent(context, SettingsActivity.class);
        PendingIntent settingsPendingIntent = PendingIntent.getActivity(context, 0,
                settingsIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        rv.setOnClickPendingIntent(R.id.widget_settings, settingsPendingIntent);

        rv.setImageViewResource(R.id.widget_refresh, R.drawable.ic_widget_refresh);
        Intent refreshIntent = new Intent(context, WidgetService.class);
        refreshIntent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        PendingIntent refreshPendingIntent = PendingIntent.getService(context, 0, refreshIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        rv.setOnClickPendingIntent(R.id.widget_refresh, refreshPendingIntent);

        if (id > 0) {

            rv.setViewVisibility(R.id.widget_text_error, View.GONE);
            rv.setViewVisibility(R.id.widget_listView, View.VISIBLE);

            String category = Preference.getGeneralSettingsWidgetCategory(context.getApplicationContext());

            Intent serviceIntent = new Intent(context, RemoteWidgetService.class);
            serviceIntent.putExtra(Costant.EXTRA_WIDGET_SERVICE_CATEGORY, category);
            rv.setRemoteAdapter(R.id.widget_listView, serviceIntent);

            Intent clickIntent = new Intent(context, MainActivity.class);
            PendingIntent clickPendingIntentTemplate = TaskStackBuilder.create(context)
                    .addNextIntentWithParentStack(clickIntent)
                    .getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
            rv.setPendingIntentTemplate(R.id.widget_listView, clickPendingIntentTemplate);


        } else {
            rv.setViewVisibility(R.id.widget_listView, View.GONE);
            rv.setViewVisibility(R.id.widget_text_error, View.VISIBLE);
            rv.setTextViewText(R.id.widget_text_error, "Widget initialize error, please start first " + context.getString(R.string.app_name));

            Intent clickIntent = new Intent(context, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,
                    clickIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            rv.setOnClickPendingIntent(R.id.widget_container, pendingIntent);

        }

        return rv;
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(context, WidgetProvider.class));

        String action = intent.getAction();
        if (Objects.equals(action, AppWidgetManager.ACTION_APPWIDGET_UPDATE)) {

            for (int appWidgetId : appWidgetIds) {
                appWidgetManager.partiallyUpdateAppWidget(appWidgetId, viewsUpdateRecipeWidget(context));
            }
            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.widget_listView);
        }
        super.onReceive(context, intent);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
//        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.widget_listView);
//        appWidgetManager.updateAppWidget(appWidgetIds, viewsUpdateRecipeWidget(context));
    }

    @Override
    public void onEnabled(Context context) {
    }

    @Override
    public void onDisabled(Context context) {
    }


}

