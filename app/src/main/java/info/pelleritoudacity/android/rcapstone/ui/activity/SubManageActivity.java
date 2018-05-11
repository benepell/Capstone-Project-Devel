package info.pelleritoudacity.android.rcapstone.ui.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import butterknife.ButterKnife;
import info.pelleritoudacity.android.rcapstone.R;
import info.pelleritoudacity.android.rcapstone.ui.fragment.SubScriptionsFragment;
import info.pelleritoudacity.android.rcapstone.utility.PrefManager;
import timber.log.Timber;

public class SubManageActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setLayoutResource(R.layout.activity_submanage);
        super.onCreate(savedInstanceState);

        Timber.plant(new Timber.DebugTree());
        ButterKnife.bind(this);

//        initializeToolBar();

        if(PrefManager.getBoolPref(getApplicationContext(),R.string.pref_insert_data)){
            startFragment();
        }else {
            Toast.makeText(getApplicationContext(),getText(R.string.text_manage_nolinks),Toast.LENGTH_LONG).show();
        }

    }

    private void initializeToolBar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

    }

    private void startFragment() {
        SubScriptionsFragment subScriptionsFragment = new SubScriptionsFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_list_container, subScriptionsFragment).commit();

    }

}
