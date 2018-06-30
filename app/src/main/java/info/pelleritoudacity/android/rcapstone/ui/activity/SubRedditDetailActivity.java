package info.pelleritoudacity.android.rcapstone.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.os.Bundle;
import android.text.TextUtils;

import java.util.List;

import info.pelleritoudacity.android.rcapstone.R;
import info.pelleritoudacity.android.rcapstone.data.T1Operation;
import info.pelleritoudacity.android.rcapstone.model.reddit.T1;
import info.pelleritoudacity.android.rcapstone.rest.SubRedditDetailExecute;
import info.pelleritoudacity.android.rcapstone.ui.fragment.SubRedditDetailFragment;
import info.pelleritoudacity.android.rcapstone.ui.fragment.SubRedditSelectedFragment;
import info.pelleritoudacity.android.rcapstone.utility.Costant;
import info.pelleritoudacity.android.rcapstone.utility.NetworkUtil;
import info.pelleritoudacity.android.rcapstone.utility.PermissionUtil;

public class SubRedditDetailActivity extends BaseActivity
        implements SubRedditDetailExecute.RestSubReddit {

    private String mStrId = null;
    private String mCategory;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setLayoutResource(R.layout.activity_sub_reddit_detail);
        super.onCreate(savedInstanceState);

        mContext = getApplicationContext();
        Intent intent = getIntent();

        if (intent != null) {
            mStrId = intent.getStringExtra(Costant.EXTRA_SUBREDDIT_DETAIL_STR_ID);
            mCategory = intent.getStringExtra(Costant.EXTRA_SUBREDDIT_DETAIL_CATEGORY);
        }

        initRest(mCategory,mStrId, PermissionUtil.getToken(mContext), NetworkUtil.isOnline(mContext));

    }

    @Override
    public void onRestSubReddit(List<T1> listenerData) {
        if ((listenerData != null) && (mStrId != null)) {
            T1Operation data = new T1Operation(getApplicationContext(), listenerData);
            if (data.saveData(mStrId)) {
                startFragment(mStrId);
            } else {
                Snackbar.make(findViewById(R.id.fragment_subreddit_detail_container), R.string.error_state_critical, Snackbar.LENGTH_LONG).show();
            }
        }
    }

    private void startFragment(String category) {
        if (!getSupportFragmentManager().isStateSaved()) {
            SubRedditSelectedFragment subRedditSelectedFragment = SubRedditSelectedFragment.newInstance(category);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_subreddit_selected_container, subRedditSelectedFragment).commit();
            SubRedditDetailFragment fragment = SubRedditDetailFragment.newInstance(category);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_subreddit_detail_container, fragment).commit();
        }
    }

    private void initRest(String category,String strId,String tokenLogin, boolean stateNetworkOnline) {
        if (!TextUtils.isEmpty(strId)) {
            if (stateNetworkOnline) {
                new SubRedditDetailExecute(mContext,
                        tokenLogin,
                        category,
                        strId).getData(this);
            } else {
                startFragment(strId);

            }
        }
    }


    @Override
    public void onErrorSubReddit(Throwable t) {

    }
}
