package info.pelleritoudacity.android.rcapstone.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;

import java.util.List;

import butterknife.BindView;
import info.pelleritoudacity.android.rcapstone.R;
import info.pelleritoudacity.android.rcapstone.data.db.Operation.T1Operation;
import info.pelleritoudacity.android.rcapstone.data.model.reddit.T1;
import info.pelleritoudacity.android.rcapstone.data.rest.More;
import info.pelleritoudacity.android.rcapstone.data.rest.RestDetailExecute;
import info.pelleritoudacity.android.rcapstone.data.rest.RestMoreExecute;
import info.pelleritoudacity.android.rcapstone.ui.fragment.SubRedditDetailFragment;
import info.pelleritoudacity.android.rcapstone.ui.fragment.SubRedditSelectedFragment;
import info.pelleritoudacity.android.rcapstone.utility.Costant;
import info.pelleritoudacity.android.rcapstone.utility.NetworkUtil;
import info.pelleritoudacity.android.rcapstone.utility.PermissionUtil;
import info.pelleritoudacity.android.rcapstone.utility.Preference;

public class SubRedditDetailActivity extends BaseActivity
        implements RestDetailExecute.RestSubReddit,
        SubRedditDetailFragment.OnFragmentInteractionListener, RestMoreExecute.RestSubRedditMore, SwipeRefreshLayout.OnRefreshListener {

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
    private String mStrArrId;
    private FragmentTransaction mMoreFragmentTransaction;


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
                mStrId = Preference.getLastComment(mContext);
                mCategory = Preference.getLastCategory(mContext);
            }

        }

        if (mContext != null) {
            mSwipeRefreshLayout.setRefreshing(true);
            onRefresh();

            Preference.setLastComment(mContext, mStrId);
            initRest(mCategory, mStrId, PermissionUtil.getToken(mContext), NetworkUtil.isOnline(mContext),  mStrArrId);

        }

    }

    @Override
    public void onRestSubReddit(List<T1> listenerData) {
        if ((listenerData != null) && (mStrId != null)) {
            T1Operation data = new T1Operation(getApplicationContext());
            if (data.saveData(listenerData, mStrId)) {
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


    private void initRest(String category, String strId, String tokenLogin, boolean stateNetworkOnline, String strArrId) {
        if (!TextUtils.isEmpty(strId)) {
            if (stateNetworkOnline) {
                if (TextUtils.isEmpty(strArrId)) {
                    new RestDetailExecute(mContext,
                            tokenLogin,
                            category,
                            strId).getData(this);

                } else {

                    new RestMoreExecute(mContext, tokenLogin,  strArrId)
                            .getMoreData(this);
                }
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
    public void moreComments(String category, String strId,  String strArrId) {
        if (!TextUtils.isEmpty(strArrId)) {
            mCategory = category;
            mStrId = strId;
            mStrArrId = strArrId;
            mSwipeRefreshLayout.setRefreshing(true);
            onRefresh();
        }

    }

    @Override
    public void childMoreFragment(FragmentTransaction child) {
        mMoreFragmentTransaction = child;
    }

    @Override
    public void onRefresh() {
        if (!NetworkUtil.isOnline(mContext)) {
            mSwipeRefreshLayout.setRefreshing(false);
            Snackbar.make(mContainer, R.string.error_refresh_offline, Snackbar.LENGTH_LONG).show();

        } else if (mContext != null) {
            initRest(Preference.getLastCategory(mContext), Preference.getLastComment(mContext),
                    PermissionUtil.getToken(mContext), NetworkUtil.isOnline(mContext),  mStrArrId);

        }
    }

    @Override
    public void onRestSubRedditMore(More listenerData, String mStrArrid) {
        if (listenerData != null) {
            if (listenerData.getJson().getData() != null) {


                T1Operation t1moreOperation = new T1Operation(getApplicationContext());

                if (t1moreOperation.saveMoreData(listenerData.getJson(), mStrArrId)) {
                    if ((mMoreFragmentTransaction != null) && Preference.isMoreFragmentTransaction(mContext)) {
                        mMoreFragmentTransaction.commit();
                        Preference.setMoreFragmentTransaction(mContext, false);

                    }

                    if (mSwipeRefreshLayout != null) {
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                }

            }

        }

    }

    @Override
    public void onErrorSubRedditMore(Throwable t) {

    }
}
