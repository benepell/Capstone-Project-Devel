package info.pelleritoudacity.android.rcapstone.ui.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import butterknife.ButterKnife;
import info.pelleritoudacity.android.rcapstone.R;
import info.pelleritoudacity.android.rcapstone.ui.fragment.SubScriptionsFragment;
import timber.log.Timber;

public class SubManageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submanage);

        Timber.plant(new Timber.DebugTree());
        ButterKnife.bind(this);

        initializeToolBar();

        startFragment();
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
