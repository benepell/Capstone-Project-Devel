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

package info.pelleritoudacity.android.rcapstone.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.WindowManager;

import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.material_design_iconic_typeface_library.MaterialDesignIconic;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import info.pelleritoudacity.android.rcapstone.R;
import info.pelleritoudacity.android.rcapstone.data.db.Contract;
import info.pelleritoudacity.android.rcapstone.data.db.Operation.T5Operation;
import info.pelleritoudacity.android.rcapstone.data.model.reddit.T5;
import info.pelleritoudacity.android.rcapstone.data.rest.CategoryExecute;
import info.pelleritoudacity.android.rcapstone.ui.fragment.ManageFragment;
import info.pelleritoudacity.android.rcapstone.utility.NetworkUtil;
import info.pelleritoudacity.android.rcapstone.utility.Preference;
import info.pelleritoudacity.android.rcapstone.utility.TextUtil;
import timber.log.Timber;

public class ManageActivity extends AppCompatActivity implements CategoryExecute.OnRestCallBack {

    @SuppressWarnings({"WeakerAccess", "CanBeFinal", "unused"})
    @BindView(R.id.nested_scrollview_submanage)
    public NestedScrollView mNestedScrollView;

    @SuppressWarnings({"WeakerAccess", "CanBeFinal", "unused"})
    @BindView(R.id.submanage_container)
    public CoordinatorLayout mContainer;

    private WeakReference<Context> mWeakContext;
    private boolean isAdded;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            setTheme(R.style.AppThemeSettingsDark);

        }
        setContentView(R.layout.activity_manage);

        Timber.plant(new Timber.DebugTree());
        ButterKnife.bind(this);

        mWeakContext = new WeakReference<>(getApplicationContext());

        updateOperation();

    }

    private void updateOperation() {
        if (Preference.isInsertPrefs(getApplicationContext())) {
            new ManageAsyncTask(mWeakContext).execute();

        } else if ((!Preference.isInsertPrefs(getApplicationContext()) && (NetworkUtil.isOnline(getApplicationContext())))) {
            new CategoryExecute(this).syncData();

        } else {
            Snackbar.make(mContainer, R.string.text_manage_nolinks, Snackbar.LENGTH_LONG).show();

        }
        if (!isAdded) {
            startFragment(false);

        }
    }

    private void startFragment(boolean restoreManage) {
        if (!getSupportFragmentManager().isStateSaved()) {

            ManageFragment manageFragment = ManageFragment.newInstance(restoreManage);
            if (restoreManage) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_list_container, manageFragment).commit();

            } else {
                getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(
                                android.R.anim.slide_in_left,
                                android.R.anim.slide_out_right,
                                R.anim.layout_animation_from_right,
                                R.anim.layout_animation_from_right)
                        .replace(R.id.fragment_list_container, manageFragment).commit();
                isAdded = true;
            }

        } else if (mNestedScrollView == null) {
            isAdded = true;
            updateOperation();

        }
    }

    @Override
    public void success(T5 response, int code) {
        T5Operation data = new T5Operation(mWeakContext.get(), response);
        data.saveData();
        startFragment(false);
    }

    private static class ManageAsyncTask extends AsyncTask<Void, Void, Cursor> {

        private final WeakReference<Context> mWeakContext;

        ManageAsyncTask(WeakReference<Context> weakContext) {
            mWeakContext = weakContext;
        }

        @Override
        protected Cursor doInBackground(Void... voids) {

            try {

                Context context = mWeakContext.get();

                Uri uri = Contract.PrefSubRedditEntry.CONTENT_URI;
                String selection = Contract.PrefSubRedditEntry.COLUMN_NAME_REMOVED + " =?" + " AND " +
                        Contract.PrefSubRedditEntry.COLUMN_NAME_VISIBLE + " =?";
                String[] selectionArgs = {String.valueOf(0), String.valueOf(1)};

                return context.getContentResolver().query(uri,
                        null,
                        selection,
                        selectionArgs,
                        null);

            } catch (Exception e) {
                Timber.e("Failed to asynchronously load data. ");
                return null;
            }
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            super.onPostExecute(cursor);

            Context context = mWeakContext.get();

            try {
                ArrayList<String> arrayList = new ArrayList<>(cursor.getCount());

                String name;
                if (!cursor.isClosed()) {
                    while (cursor.moveToNext()) {
                        name = TextUtil.normalizeSubRedditLink(
                                cursor.getString(cursor.getColumnIndex(Contract.PrefSubRedditEntry.COLUMN_NAME_NAME)));

                        arrayList.add(name);
                        Preference.setSubredditKey(context, TextUtil.arrayToString(arrayList));
                    }
                }

            } finally {
                if ((cursor != null) && (!cursor.isClosed())) {
                    cursor.close();
                }

            }
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.manage_menu, menu);
        MenuItem menuItemRestore = menu.findItem(R.id.menu_action_restore);
        menuItemRestore.setIcon(
                new IconicsDrawable(getApplicationContext(), MaterialDesignIconic.Icon.gmi_undo)
                        .colorRes(R.color.white)
                        .sizeDp(24)
                        .respectFontBounds(true));
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu_action_restore:
                startFragment(true);
                return true;

            case R.id.menu_action_login:
                if (NetworkUtil.isOnline(this)) {
                    startActivity(new Intent(this, LoginActivity.class));
                } else {
                    Snackbar.make(mContainer, R.string.text_no_network, Snackbar.LENGTH_LONG).show();
                }
                return true;

            case R.id.menu_action_logout:
                startActivity(new Intent(this, LoginActivity.class));
                return true;

            case R.id.menu_action_refresh:
                if (NetworkUtil.isOnline(this)) {
                    recreate();
                } else {
                    Snackbar.make(mContainer, R.string.text_no_network, Snackbar.LENGTH_LONG).show();
                }
                return true;

            case R.id.menu_action_settings:
                startActivity(new Intent(this, SettingsActivity.class));
                return true;
        }
        return false;

    }
}
