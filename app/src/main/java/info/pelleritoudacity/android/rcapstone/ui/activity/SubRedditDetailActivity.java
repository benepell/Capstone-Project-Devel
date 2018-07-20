package info.pelleritoudacity.android.rcapstone.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
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
import info.pelleritoudacity.android.rcapstone.utility.TextUtil;
import timber.log.Timber;

public class SubRedditDetailActivity extends BaseActivity
        implements RestDetailExecute.RestSubReddit,
        SubRedditDetailFragment.OnFragmentInteractionListener, RestMoreExecute.RestSubRedditMore,
        SwipeRefreshLayout.OnRefreshListener {

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
    private String mStrLinkId;
    private int mId;
    private int mPosition;


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

        Preference.setLastComment(mContext, mStrId);

        if (savedInstanceState != null) {
            mStrId = savedInstanceState.getString(Costant.EXTRA_SUBREDDIT_DETAIL_STR_ID);
            mStrLinkId = savedInstanceState.getString(Costant.EXTRA_FRAGMENT_SUBREDDIT_DETAIL_LINKID);
            mStrArrId = savedInstanceState.getString(Costant.EXTRA_FRAGMENT_SUBREDDIT_DETAIL_ARRID);
            mId = savedInstanceState.getInt(Costant.EXTRA_FRAGMENT_SUBREDDIT_DETAIL_ID);
            mPosition = savedInstanceState.getInt(Costant.EXTRA_FRAGMENT_SUBREDDIT_DETAIL_POSITION);

        }

        mSwipeRefreshLayout.setRefreshing(true);
        onRefresh();

    }

    @Override
    public void onRestSubReddit(List<T1> listenerData) {
        if ((listenerData != null) && (mStrId != null)) {
            T1Operation data = new T1Operation(getApplicationContext());
            if (data.saveData(listenerData, mStrId)) {
                startFragment(mPosition, mStrId);
                mSwipeRefreshLayout.setRefreshing(false);
            } else {
                Snackbar.make(mContainer, R.string.error_state_critical, Snackbar.LENGTH_LONG).show();
            }
        }
    }

    private void startFragment(int position, String strId) {
        SubRedditSelectedFragment subRedditSelectedFragment = SubRedditSelectedFragment.newInstance(strId);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_subreddit_selected_container, subRedditSelectedFragment).commitAllowingStateLoss();

        SubRedditDetailFragment fragment = SubRedditDetailFragment.newInstance(position, strId, 0, null, null);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_subreddit_detail_container, fragment).commitAllowingStateLoss();

    }

    private void startMoreFragment(int position, String strId, int id, String strArrId, String strLinkId) {
        SubRedditDetailFragment fragment = SubRedditDetailFragment.newInstance(position, strId, id, strArrId, strLinkId);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_subreddit_detail_container, fragment).commitAllowingStateLoss();

    }


    private void initRest(String strId, String strArrId, String strLinkId, String category, String tokenLogin, boolean stateNetworkOnline) {
        if (!TextUtils.isEmpty(strId)) {
            if (stateNetworkOnline) {
                if (TextUtils.isEmpty(strArrId)) {
                    new RestDetailExecute(mContext,
                            tokenLogin,
                            category,
                            strId).getData(this);

                } else {
                    new RestMoreExecute(mContext, tokenLogin, strArrId, strLinkId)
                            .getMoreData(this);

                }
            } else {
                startFragment(mPosition, strId);

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
    public void onClickMore(int position, int id, String linkId, String strId, String strArrId) {
        mPosition = position;
        mId = id;
        mStrId = strId;
        mStrLinkId = linkId;
        mStrArrId = strArrId;
        mSwipeRefreshLayout.setRefreshing(true);
        onRefresh();
    }

    @Override
    public void onRefresh() {
        if (!NetworkUtil.isOnline(mContext)) {
            mSwipeRefreshLayout.setRefreshing(false);
            Snackbar.make(mContainer, R.string.error_refresh_offline, Snackbar.LENGTH_LONG).show();

        } else if (!TextUtils.isEmpty(mStrArrId)) {
            // todo review here
            initRest(mStrId, mStrArrId, mStrLinkId, Preference.getLastCategory(mContext),
                    PermissionUtil.getToken(mContext), NetworkUtil.isOnline(mContext));

        } else {
            initRest(Preference.getLastComment(mContext), null, null, Preference.getLastCategory(mContext),
                    PermissionUtil.getToken(mContext), NetworkUtil.isOnline(mContext));

        }
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString(Costant.EXTRA_SUBREDDIT_DETAIL_STR_ID, mStrId);
        outState.putString(Costant.EXTRA_FRAGMENT_SUBREDDIT_DETAIL_ARRID, mStrArrId);
        outState.putString(Costant.EXTRA_FRAGMENT_SUBREDDIT_DETAIL_LINKID, mStrLinkId);
        outState.putInt(Costant.EXTRA_FRAGMENT_SUBREDDIT_DETAIL_ID, mId);
        outState.putInt(Costant.EXTRA_FRAGMENT_SUBREDDIT_DETAIL_POSITION, mPosition);

        super.onSaveInstanceState(outState);

    }

    @Override
    public void onRestSubRedditMore(More listenerData, String mStrArrid) {
        if (listenerData != null) {
            if (listenerData.getJson().getData() != null) {

                T1Operation t1moreOperation = new T1Operation(getApplicationContext());

                if (t1moreOperation.saveMoreData(listenerData.getJson(), mStrArrId)) {

                    startMoreFragment(mPosition, mStrId, mId, mStrArrId, mStrLinkId);

                    if (mSwipeRefreshLayout != null) {
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                }

            }

        }

    }

    @Override
    public void onErrorSubRedditMore(Throwable t) {
        Timber.e("subredddit more error %s", t.getCause());
    }

    @Override
    public void onBackPressed() {
        if (!TextUtils.isEmpty(mStrArrId)) {
            mStrArrId = null;
            startFragment(mPosition, mStrId);
        } else {
            super.onBackPressed();
        }
    }

}
