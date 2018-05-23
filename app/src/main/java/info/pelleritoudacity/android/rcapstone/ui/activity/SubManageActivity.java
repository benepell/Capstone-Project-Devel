package info.pelleritoudacity.android.rcapstone.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import butterknife.ButterKnife;
import info.pelleritoudacity.android.rcapstone.R;
import info.pelleritoudacity.android.rcapstone.data.Contract;
import info.pelleritoudacity.android.rcapstone.ui.fragment.SubScriptionsFragment;
import info.pelleritoudacity.android.rcapstone.utility.Costants;
import info.pelleritoudacity.android.rcapstone.utility.PrefManager;
import info.pelleritoudacity.android.rcapstone.utility.Utility;
import timber.log.Timber;

public class SubManageActivity extends BaseActivity {

    private static WeakReference<Context> sWeakContext;
    private static WeakReference<android.support.v4.app.FragmentManager> sWeakFragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setLayoutResource(R.layout.activity_submanage);
        super.onCreate(savedInstanceState);

        sWeakContext = new WeakReference<>(getApplicationContext());
        sWeakFragmentManager = new WeakReference<>(getSupportFragmentManager());
        Timber.plant(new Timber.DebugTree());
        ButterKnife.bind(this);

        if (PrefManager.getBoolPref(getApplicationContext(), R.string.pref_insert_data)) {
            new RemovedItemSubRedditAsyncTask().execute();
        } else {
            Toast.makeText(getApplicationContext(), getText(R.string.text_manage_nolinks), Toast.LENGTH_LONG).show();
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        startFragment();
    }

    private void startFragment() {
        SubScriptionsFragment subScriptionsFragment = SubScriptionsFragment.newInstance();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_list_container, subScriptionsFragment).commit();

    }


    private static class RemovedItemSubRedditAsyncTask extends AsyncTask<Void, Void, Cursor> {

        @Override
        protected Cursor doInBackground(Void... voids) {
            try {
                Context context = sWeakContext.get();

                Uri uri = Contract.PrefSubRedditEntry.CONTENT_URI;
                String selection = Contract.PrefSubRedditEntry.COLUMN_NAME_REMOVED + " =? AND " + Contract.PrefSubRedditEntry.COLUMN_NAME_VISIBLE + " =?";
                String[] selectionArgs = {String.valueOf(0), String.valueOf(1)};
                return context.getContentResolver().query(uri,
                        null,
                        selection,
                        selectionArgs,
                        null);

            } catch (Exception e) {
                Timber.e("Failed to asynchronously load data. ");
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            super.onPostExecute(cursor);
            Context context = sWeakContext.get();

            try {

                int size = cursor.getCount();
                ArrayList<String> arrayList = new ArrayList<>(size);

                String name;
                if (!cursor.isClosed()) {
                    while (cursor.moveToNext()) {
                        name = cursor.getString(cursor.getColumnIndex(Contract.PrefSubRedditEntry.COLUMN_NAME_NAME));
                        name = Utility.normalizeSubRedditLink(name);
                        arrayList.add(name);
                        String strPref = Utility.arrayToString(arrayList);
                        PrefManager.putStringPref(context, R.string.pref_subreddit_key, strPref);
                    }

                    android.support.v4.app.FragmentManager fragmentManager = sWeakFragmentManager.get();
                    SubScriptionsFragment subScriptionsFragment = new SubScriptionsFragment();
                    fragmentManager.beginTransaction()
                            .replace(R.id.fragment_list_container, subScriptionsFragment).commit();

                }
            } finally {
                if ((cursor != null) && (!cursor.isClosed())) {
                    cursor.close();
                }

            }
        }
    }

    public static void manageToMainActivity(Context context) {
        context.startActivity(new Intent(context, MainActivity.class).putExtra(Costants.EXTRA_RESTORE_MANAGE, Costants.RESTORE_MANAGE_REDIRECT)
                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NO_HISTORY));
    }

}
