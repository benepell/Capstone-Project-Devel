package info.pelleritoudacity.android.rcapstone.ui.activity;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.View;

import java.util.List;

import butterknife.BindView;
import info.pelleritoudacity.android.rcapstone.R;
import info.pelleritoudacity.android.rcapstone.data.db.Contract;
import info.pelleritoudacity.android.rcapstone.data.db.Operation.T1Operation;
import info.pelleritoudacity.android.rcapstone.data.db.util.DataUtils;
import info.pelleritoudacity.android.rcapstone.data.model.reddit.T1;
import info.pelleritoudacity.android.rcapstone.data.model.ui.DetailModel;
import info.pelleritoudacity.android.rcapstone.data.rest.More;
import info.pelleritoudacity.android.rcapstone.data.rest.RestDetailExecute;
import info.pelleritoudacity.android.rcapstone.data.rest.RestMoreExecute;
import info.pelleritoudacity.android.rcapstone.ui.fragment.SubRedditDetailFragment;
import info.pelleritoudacity.android.rcapstone.ui.fragment.SubRedditSelectedFragment;
import info.pelleritoudacity.android.rcapstone.ui.helper.MenuBase;
import info.pelleritoudacity.android.rcapstone.ui.helper.SubRedditDetailHelper;
import info.pelleritoudacity.android.rcapstone.utility.Costant;
import info.pelleritoudacity.android.rcapstone.utility.NetworkUtil;
import info.pelleritoudacity.android.rcapstone.utility.PermissionUtil;
import info.pelleritoudacity.android.rcapstone.utility.Preference;
import timber.log.Timber;

public class SubRedditDetailActivity extends BaseActivity
        implements RestDetailExecute.RestSubReddit,
        SubRedditDetailFragment.OnFragmentInteractionListener, RestMoreExecute.RestSubRedditMore,
        SwipeRefreshLayout.OnRefreshListener, NestedScrollView.OnScrollChangeListener, SearchView.OnQueryTextListener {

    @SuppressWarnings("unused")
    @BindView(R.id.subreddit_detail_container)
    CoordinatorLayout mContainer;

    @SuppressWarnings("unused")
    @BindView(R.id.nested_scrollview_detail)
    NestedScrollView mNestedScrollView;

    @SuppressWarnings("unused")
    @BindView(R.id.swipe_refresh_subreddit_detail)
    SwipeRefreshLayout mSwipeRefreshLayout;

    private Context mContext;
    private DetailModel model;
    private SubRedditDetailHelper mDetailHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setLayoutResource(R.layout.activity_sub_reddit_detail);
        super.onCreate(savedInstanceState);

        mContext = getApplicationContext();
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mDetailHelper = new SubRedditDetailHelper(mContext);

        if (savedInstanceState == null) {
            model = mDetailHelper.initModelTarget(getIntent());

        } else {
            model = savedInstanceState.getParcelable(Costant.EXTRA_PARCEL_ACTIVITY_DETAIL);

        }

        /*if (Objects.requireNonNull(model).getId() == 0) {
            mNestedScrollView.setOnScrollChangeListener(this);

        }
*/
        mSwipeRefreshLayout.setRefreshing(true);
        onRefresh();

    }

    @Override
    public void onRestSubReddit(List<T1> listenerData) {
        if ((listenerData != null) && (model.getStrId() != null)) {
            T1Operation data = new T1Operation(getApplicationContext());
            if (data.saveData(listenerData, model.getStrId())) {
                startFragment(model, false);
            } else {
                Snackbar.make(mContainer, R.string.error_state_critical, Snackbar.LENGTH_LONG).show();
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
    public void onClickMore(DetailModel detailModel) {
        model = detailModel;
        mSwipeRefreshLayout.setRefreshing(true);
        onRefresh();
    }

    @Override
    public void onRefresh() {

        if (mNestedScrollView != null) {
            if (mNestedScrollView.getChildAt(0).getScrollY() > 0) {
                mNestedScrollView.getChildAt(0).setScrollY(mNestedScrollView.getTop());
            }
        }

        String tokenLogin = PermissionUtil.getToken(mContext);

        switch (mDetailHelper.getJob(model, isUpdateData(), NetworkUtil.isOnline(mContext))) {

            case Costant.TARGET_DETAIL_NO_UPDATE:

                if ((mSwipeRefreshLayout != null) && (mSwipeRefreshLayout.isRefreshing())) {
                    mSwipeRefreshLayout.setRefreshing(false);
                }
                startFragment(model, false);

                break;

            case Costant.TARGET_DETAIL:
                new RestDetailExecute(mContext, tokenLogin, model).getData(this);
                break;

            case Costant.TARGET_MORE_DETAIL:
                new RestMoreExecute(mContext, tokenLogin, model).getMoreData(this);
                break;

            case Costant.TARGET_DETAIL_SEARCH:
                startFragment(model, false);
                break;

            case Costant.TARGET_MORE_DETAIL_SEARCH:
                startFragment(model, false);
                break;

        }


    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(Costant.EXTRA_PARCEL_ACTIVITY_DETAIL, model);
        super.onSaveInstanceState(outState);

    }

    @Override
    public void onRestSubRedditMore(More listenerData, String strArrId) {
        if (listenerData != null) {
            if (listenerData.getJson().getData() != null) {

                T1Operation t1moreOperation = new T1Operation(getApplicationContext());

                if (t1moreOperation.saveMoreData(listenerData.getJson(), strArrId)) {

                    startFragment(model, true);

                }

            }

        }

    }

    @Override
    public void onErrorSubRedditMore(Throwable t) {
        Timber.e("sub reddit more error %s", t.getMessage());
    }


    @Override
    protected boolean onPrepareOptionsPanel(View view, Menu menu) {

        if (menu.findItem(R.id.menu_action_search) == null) {
            getMenuInflater().inflate(R.menu.menu_search, menu);
            MenuBase menuBase = new MenuBase(mContext, R.layout.activity_sub_reddit_detail);
            menuBase.menuItemSearch(this, getComponentName(), menu);
        }

        return super.onPrepareOptionsPanel(view, menu);
    }


    @Override
    public void onBackPressed() {
        if (model.getTarget() != Costant.TARGET_DETAIL) {
            model.setTarget(Costant.TARGET_DETAIL);
            startFragment(model, false);

            if (Preference.getMoreNestedPositionHeight(mContext) > 0) {
                mNestedScrollView.getChildAt(0).setScrollY(Preference.getMoreNestedPositionHeight(mContext));
            }

        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
        if (scrollY > 0) {
            Preference.setMoreNestedPositionHeight(mContext, scrollY);
        }
    }

    @Override
    public boolean onQueryTextSubmit(String s) {

        Intent intent = new Intent(getApplicationContext(), SubRedditDetailActivity.class);

        model.setStrQuerySearch(s);

        switch (model.getTarget()) {
            case Costant.TARGET_DETAIL_NO_UPDATE:
            case Costant.TARGET_DETAIL:
                model.setTarget(Costant.TARGET_DETAIL_SEARCH);
                intent.putExtra(Costant.EXTRA_PARCEL_DETAIL_MODEL, model);
                break;

            case Costant.TARGET_MORE_DETAIL:
                model.setTarget(Costant.TARGET_MORE_DETAIL_SEARCH);
                intent.putExtra(Costant.EXTRA_PARCEL_MORE_DETAIL_MODEL, model);
                break;

            default:
                return false;
        }
        startActivity(intent);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        return false;
    }

    private void startFragment(DetailModel m, boolean moreFragment) {

        if (moreFragment) {
            SubRedditDetailFragment fragment = SubRedditDetailFragment.newInstance(m);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_subreddit_detail_container, fragment).commitAllowingStateLoss();

        } else {
            SubRedditSelectedFragment subRedditSelectedFragment = SubRedditSelectedFragment.newInstance(m.getStrId());
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_subreddit_selected_container, subRedditSelectedFragment).commitAllowingStateLoss();

            SubRedditDetailFragment fragment = SubRedditDetailFragment.newInstance(m);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_subreddit_detail_container, fragment).commitAllowingStateLoss();

        }

        if ((mSwipeRefreshLayout != null) && (mSwipeRefreshLayout.isRefreshing())) {
            mSwipeRefreshLayout.setRefreshing(false);
        }

    }

    private boolean isUpdateData() {
        return new DataUtils(mContext).isSyncDataDetail(Contract.T1dataEntry.CONTENT_URI,
                model, Preference.getGeneralSettingsSyncFrequency(mContext));
    }


}
