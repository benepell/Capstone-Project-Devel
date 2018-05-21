package info.pelleritoudacity.android.rcapstone.ui.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.media.session.MediaButtonReceiver;
import android.support.v4.media.session.MediaSessionCompat;
import android.text.TextUtils;

import butterknife.ButterKnife;
import info.pelleritoudacity.android.rcapstone.R;
import info.pelleritoudacity.android.rcapstone.data.T3Operation;
import info.pelleritoudacity.android.rcapstone.model.reddit.T3;
import info.pelleritoudacity.android.rcapstone.rest.SubRedditExecute;
import info.pelleritoudacity.android.rcapstone.ui.fragment.SubRedditFragment;
import info.pelleritoudacity.android.rcapstone.utility.Costants;
import info.pelleritoudacity.android.rcapstone.utility.Utility;
import timber.log.Timber;

public class SubRedditActivity extends BaseActivity
        implements SubRedditExecute.RestSubReddit {

    private String mRedditCategory;
    public static MediaSessionCompat sMediaSessionCompat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setLayoutResource(R.layout.activity_subreddit);
        super.onCreate(savedInstanceState);

        Timber.plant(new Timber.DebugTree());
        ButterKnife.bind(this);

        Intent intentCategory = getIntent();

        if (intentCategory != null) {
            mRedditCategory = intentCategory.getStringExtra(Costants.EXTRA_SUBREDDIT_CATEGORY);
        }

        initializeRestSubReddit(mRedditCategory);

        if (!Utility.isOnline(getApplicationContext())) {
            startFragment(mRedditCategory);
        }

    }

    @Override
    public void onRestSubReddit(T3 listenerData) {

        if (listenerData != null) {
            T3Operation data = new T3Operation(getApplicationContext(), listenerData);
            data.saveData(mRedditCategory);

            if (data.saveData(mRedditCategory)) {
                startFragment(mRedditCategory);
            } else {
                Snackbar.make(findViewById(R.id.subreddit_container), R.string.error_state_critical, Snackbar.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onErrorSubReddit(Throwable t) {

    }

    private void startFragment(String link) {
        SubRedditFragment subRedditFragment = SubRedditFragment.newInstance(link);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_subreddit_container, subRedditFragment).commit();

    }

    private void initializeRestSubReddit(String link) {
        if (!TextUtils.isEmpty(link)) {
            new SubRedditExecute(link).getData(this);
        }
    }

    public static class MediaReceiver extends BroadcastReceiver {

        public MediaReceiver() {
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            if (sMediaSessionCompat != null) {
                MediaButtonReceiver.handleIntent(sMediaSessionCompat, intent);
            }
        }

    }

}
