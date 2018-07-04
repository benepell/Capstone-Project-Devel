package info.pelleritoudacity.android.rcapstone.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;

import java.util.List;

import butterknife.BindView;
import info.pelleritoudacity.android.rcapstone.R;
import info.pelleritoudacity.android.rcapstone.data.db.Operation.T1Operation;
import info.pelleritoudacity.android.rcapstone.data.model.reddit.T1;
import info.pelleritoudacity.android.rcapstone.data.rest.SubRedditDetailExecute;
import info.pelleritoudacity.android.rcapstone.ui.fragment.SubRedditDetailFragment;
import info.pelleritoudacity.android.rcapstone.ui.fragment.SubRedditSelectedFragment;
import info.pelleritoudacity.android.rcapstone.utility.Costant;
import info.pelleritoudacity.android.rcapstone.utility.NetworkUtil;
import info.pelleritoudacity.android.rcapstone.utility.PermissionUtil;
import info.pelleritoudacity.android.rcapstone.utility.Preference;

public class SubRedditDetailActivity extends BaseActivity
        implements SubRedditDetailExecute.RestSubReddit,
        SubRedditDetailFragment.OnFragmentInteractionListener, SwipeRefreshLayout.OnRefreshListener {

    @SuppressWarnings("unused")
    @BindView(R.id.subreddit_detail_container)
    CoordinatorLayout mContainer;

    @SuppressWarnings("unused")
    @BindView(R.id.nested_scrollview_detail)
    NestedScrollView mNestedScrollView;

    @SuppressWarnings("unused")
    @BindView(R.id.swipe_refresh_subreddit_detail)
    SwipeRefreshLayout mSwipeRefreshLayout;

    private String mStrId = null;
    private String mCategory;
    private Context mContext;
    private boolean isRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setLayoutResource(R.layout.activity_sub_reddit_detail);
        super.onCreate(savedInstanceState);

        mContext = getApplicationContext();
        Intent intent = getIntent();

        mSwipeRefreshLayout.setOnRefreshListener(this);

        if (intent != null) {
            mStrId = intent.getStringExtra(Costant.EXTRA_SUBREDDIT_DETAIL_STR_ID);
            mCategory = intent.getStringExtra(Costant.EXTRA_SUBREDDIT_DETAIL_CATEGORY);

            if (intent.getBooleanExtra(Costant.EXTRA_ACTIVITY_SUBREDDIT_DETAIL_REFRESH, false)) {
                isRefresh = true;
                mStrId = Preference.getLastComment(mContext);
                mCategory = Preference.getLastCategory(mContext);
            }

        }

        if (mContext != null) {
            mSwipeRefreshLayout.setRefreshing(true);
            onRefresh();

            Preference.setLastComment(mContext, mStrId);
            initRest(mCategory, mStrId, PermissionUtil.getToken(mContext), NetworkUtil.isOnline(mContext));

        }

    }

    @Override
    public void onRestSubReddit(List<T1> listenerData) {
        if ((listenerData != null) && (mStrId != null)) {
            T1Operation data = new T1Operation(getApplicationContext(), listenerData);
            if (data.saveData(mStrId)) {
                startFragment(mStrId);
                mSwipeRefreshLayout.setRefreshing(false);
            } else {
                Snackbar.make(mContainer, R.string.error_state_critical, Snackbar.LENGTH_LONG).show();
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

    private void initRest(String category, String strId, String tokenLogin, boolean stateNetworkOnline) {
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

    @Override
    public void clickSelector(int position, int itemCount) {
        if (position == itemCount - 1) {
            mNestedScrollView.smoothScrollBy(0, mNestedScrollView.getBottom());
        }
    }

    @Override
    public void onRefresh() {
        if (!NetworkUtil.isOnline(mContext)) {
            mSwipeRefreshLayout.setRefreshing(false);
            Snackbar.make(mContainer, R.string.error_refresh_offline, Snackbar.LENGTH_LONG).show();

        } else if (mContext != null) {
            initRest(Preference.getLastCategory(mContext), Preference.getLastComment(mContext),
                    PermissionUtil.getToken(mContext), NetworkUtil.isOnline(mContext));
        }
    }
}
