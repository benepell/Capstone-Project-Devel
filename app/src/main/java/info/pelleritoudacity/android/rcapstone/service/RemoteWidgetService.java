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
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Binder;
import android.text.TextUtils;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.squareup.picasso.Picasso;

import info.pelleritoudacity.android.rcapstone.R;
import info.pelleritoudacity.android.rcapstone.data.db.Contract;
import info.pelleritoudacity.android.rcapstone.utility.Costant;
import info.pelleritoudacity.android.rcapstone.utility.DateUtil;
import info.pelleritoudacity.android.rcapstone.utility.Preference;
import info.pelleritoudacity.android.rcapstone.utility.TextUtil;
import info.pelleritoudacity.android.rcapstone.widget.WidgetUtil;
import timber.log.Timber;

public class RemoteWidgetService extends RemoteViewsService {

    private String mCategory;

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        if (intent != null) {
            mCategory = intent.getStringExtra(Costant.EXTRA_WIDGET_SERVICE_CATEGORY);
        }
        return new WidgetRemoteViewsFactory(this.getApplicationContext(), mCategory);
    }

    class WidgetRemoteViewsFactory implements RemoteViewsFactory {
        final Context mContext;
        final String mCategory;
        Cursor mCursor;

        WidgetRemoteViewsFactory(Context context, String category) {
            mContext = context;
            mCategory = category;
        }

        @Override
        public void onCreate() {
        }

        @Override
        public void onDataSetChanged() {

            final long identityToken = Binder.clearCallingIdentity();

            try {

                String selection = null;
                String[] selectionArgs = null;

                WidgetUtil widgetUtil = new WidgetUtil(mContext);


                switch (mCategory) {
                    case Costant.CATEGORY_POPULAR:
                    case Costant.CATEGORY_ALL:
                    case Costant.CATEGORY_HOT:
                        selection = Contract.T3dataEntry.COLUMN_NAME_TARGET + " =?" + " AND " +
                                Contract.T3dataEntry.COLUMN_NAME_OVER_18 + " =?";
                        selectionArgs = new String[]{widgetUtil.getMainTarget(mCategory), "0"};
                }

                mCursor = mContext.getContentResolver().query(Contract.T3dataEntry.CONTENT_URI,
                        null,
                        selection, selectionArgs, null);


            } catch (Exception e) {
                Timber.e("widget datasetchange error %s", e.getMessage());

            } finally {
                if (mCursor != null) {
                    mCursor.close();
                }
                Binder.restoreCallingIdentity(identityToken);
            }

        }

        @Override
        public void onDestroy() {
            if (mCursor != null) mCursor.close();
        }

        @Override
        public int getCount() {
            return mCursor != null ? mCursor.getCount() : 0;

        }

        @Override
        public RemoteViews getViewAt(int position) {

            if (mCursor == null || mCursor.getCount() == 0) {
                Preference.setWidgetId(mContext, 0);
                return null;
            }

            mCursor.moveToPosition(position);

            int id = mCursor.getInt(mCursor.getColumnIndex(Contract.T3dataEntry._ID));
            String category = mCursor.getString(mCursor.getColumnIndex(Contract.T3dataEntry.COLUMN_NAME_SUBREDDIT));
            String author = mCursor.getString(mCursor.getColumnIndex(Contract.T3dataEntry.COLUMN_NAME_AUTHOR));
            String title = mCursor.getString(mCursor.getColumnIndex(Contract.T3dataEntry.COLUMN_NAME_TITLE));
            String numComments = mCursor.getString(mCursor.getColumnIndex(Contract.T3dataEntry.COLUMN_NAME_NUM_COMMENTS));
            String categoryPrefix = mCursor.getString(mCursor.getColumnIndex(Contract.T3dataEntry.COLUMN_NAME_SUBREDDIT_NAME_PREFIXE));
            String strImageUrl = mCursor.getString(mCursor.getColumnIndex(Contract.T3dataEntry.COLUMN_NAME_PREVIEW_IMAGE_SOURCE_URL));
            long createdUtc = mCursor.getLong(mCursor.getColumnIndex(Contract.T3dataEntry.COLUMN_NAME_CREATED_UTC));

            Preference.setWidgetId(mContext, id);

            RemoteViews views = new RemoteViews(mContext.getPackageName(),
                    R.layout.list_widget);

            if (!TextUtils.isEmpty(strImageUrl)) {
                Uri imageUri = Uri.parse(TextUtil.textFromHtml(strImageUrl));

                try {
                    Bitmap b = Picasso.get().load(imageUri).get();
                    views.setImageViewBitmap(R.id.img_widget_name, b);

                } catch (Exception e) {
                    Timber.e("WIDGET image error %s", e.getMessage());

                }

            } else {
                views.setImageViewResource(R.id.img_widget_name, R.drawable.no_image);

            }

            views.setTextViewText(R.id.text_widget_author, author);
            views.setTextViewText(R.id.text_widget_title, title);

            views.setTextViewText(R.id.text_widget_num_comments,
                    String.format(" %s %s ", numComments, mContext.getString(R.string.text_comments_subreddit)));

            views.setTextViewText(R.id.text_widget_utc, DateUtil.getDiffTimeMinute(mContext, createdUtc));

            views.setTextViewText(R.id.text_widget_category,
                    String.format(" - %s", categoryPrefix));

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
