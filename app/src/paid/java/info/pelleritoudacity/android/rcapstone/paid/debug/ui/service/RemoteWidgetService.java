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


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Binder;
import android.text.TextUtils;
import android.view.View;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.squareup.picasso.Picasso;

import java.util.List;

import info.pelleritoudacity.android.rcapstone.R;
import info.pelleritoudacity.android.rcapstone.data.db.AppDatabase;
import info.pelleritoudacity.android.rcapstone.data.db.entry.T3Entry;
import info.pelleritoudacity.android.rcapstone.paid.debug.ui.widget.WidgetUtil;
import info.pelleritoudacity.android.rcapstone.utility.Costant;
import info.pelleritoudacity.android.rcapstone.utility.DateUtil;
import info.pelleritoudacity.android.rcapstone.utility.Preference;
import info.pelleritoudacity.android.rcapstone.utility.TextUtil;
import timber.log.Timber;


public class RemoteWidgetService extends RemoteViewsService {

    private AppDatabase mDb;
    private Context mContext;
    private List<T3Entry> records;

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new WidgetRemoteViewsFactory(this.getApplicationContext());
    }

    class WidgetRemoteViewsFactory implements RemoteViewsFactory {

        WidgetRemoteViewsFactory(Context context) {
            mContext = context;

        }

        @Override
        public void onCreate() {
            mDb = AppDatabase.getInstance(mContext);

        }

        @Override
        public void onDataSetChanged() {
            WidgetUtil widgetUtil = new WidgetUtil(mContext, mDb);


            final long identityToken = Binder.clearCallingIdentity();


            switch (Preference.getWidgetCategory(mContext)) {
                case Costant.CATEGORY_POPULAR:
                case Costant.CATEGORY_ALL:
                case Costant.CATEGORY_HOT:
                case Costant.CATEGORY_NEW:
                case Costant.CATEGORY_TOP:
                case Costant.CATEGORY_RISING:
                case Costant.CATEGORY_CONTROVERSIAL:
                case Costant.CATEGORY_BEST:
                    records = mDb.t3Dao().loadMainWidget(widgetUtil.getMainTarget(Preference.getWidgetCategory(mContext)), 0);

            }


            Binder.restoreCallingIdentity(identityToken);


        }

        @Override
        public void onDestroy() {
            //records = null; 
        }

        @Override
        public int getCount() {
            return records != null ? records.size() : 0;

        }

        @Override
        public RemoteViews getViewAt(int position) {

            if (records == null || records.size() == 0) return null;

            T3Entry record = records.get(position);

            int id = record.getId();
            String category = record.getSubreddit();
            String author = record.getAuthor();
            String title = record.getTitle();
            String numComments = String.valueOf(record.getNumComments());
            String categoryPrefix = record.getSubredditNamePrefix();
            String strImageUrl = record.getPreviewImageSourceUrl();
            long createdUtc = record.getCreatedUtc();


            RemoteViews views = new RemoteViews(mContext.getPackageName(),
                    R.layout.list_widget_main);


            if (Preference.getWidgetWidth(mContext) > Costant.WIDTH_ENABLE_IMAGE) {

                views.setViewVisibility(R.id.img_widget_name, View.VISIBLE);

                if (!TextUtils.isEmpty(strImageUrl)) {
                    Uri imageUri = Uri.parse(TextUtil.textFromHtml(strImageUrl));

                    try {
                        Bitmap b = Picasso.get().load(imageUri).get();
                        views.setImageViewBitmap(R.id.img_widget_name, b);


                    } catch (Exception e) {
                        Timber.e("WIDGET image error %s", e.getMessage());

                    }

                } else {
                    views.setImageViewResource(R.id.img_widget_name, R.drawable.ic_no_image);

                }

            } else {
                views.setViewVisibility(R.id.img_widget_name, View.GONE);

            }

            views.setTextViewText(R.id.text_widget_title, title);

            views.setTextViewText(R.id.text_widget_num_comments,
                    String.format(" %s %s - %s ", numComments, mContext.getString(R.string.text_comments_subreddit), DateUtil.getDiffTimeMinute(mContext, createdUtc)));

            views.setTextViewText(R.id.text_widget_author,
                    String.format("%s - %s", author, categoryPrefix));

            Intent fillInIntent = new Intent();
            fillInIntent.putExtra(Costant.EXTRA_WIDGET_ID, id);
            fillInIntent.putExtra(Costant.EXTRA_WIDGET_SERVICE_CATEGORY, category);
            views.setOnClickFillInIntent(R.id.linear_layout_widget, fillInIntent);

            return views;
        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }


    }


} 
