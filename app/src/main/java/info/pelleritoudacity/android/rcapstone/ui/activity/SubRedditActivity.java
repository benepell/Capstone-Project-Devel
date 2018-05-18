package info.pelleritoudacity.android.rcapstone.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;

import butterknife.ButterKnife;
import info.pelleritoudacity.android.rcapstone.R;
import info.pelleritoudacity.android.rcapstone.data.DataUtils;
import info.pelleritoudacity.android.rcapstone.data.T3Operation;
import info.pelleritoudacity.android.rcapstone.model.T3;
import info.pelleritoudacity.android.rcapstone.rest.SubRedditExecute;
import info.pelleritoudacity.android.rcapstone.ui.fragment.SubRedditFragment;
import info.pelleritoudacity.android.rcapstone.utility.Costants;
import info.pelleritoudacity.android.rcapstone.utility.Utility;
import timber.log.Timber;

public class SubRedditActivity extends BaseActivity
        implements SubRedditExecute.RestSubReddit{

        private String mRedditCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setLayoutResource(R.layout.activity_subreddit);
        super.onCreate(savedInstanceState);

        Timber.plant(new Timber.DebugTree());
        ButterKnife.bind(this);

        Intent intentCategoty = getIntent();

        if(intentCategoty != null){
            mRedditCategory = intentCategoty.getStringExtra(Costants.EXTRA_SUBREDDIT_CATEGORY);
        }
        initializeRestSubReddit(mRedditCategory);

        if(!Utility.isOnline(getApplicationContext())){
            startFragment(mRedditCategory);
        }
    }

    @Override
    public void onRestSubReddit(T3 listenerData) {

        if (listenerData != null) {
            T3Operation data = new T3Operation(getApplicationContext(),listenerData);
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

    private void initializeRestSubReddit(String link){
        if(!TextUtils.isEmpty(link)){
            new SubRedditExecute(link).getData(this);
        }
    }
}
