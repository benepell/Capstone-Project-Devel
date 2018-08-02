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
import android.widget.Toast;

import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import info.pelleritoudacity.android.rcapstone.R;
import info.pelleritoudacity.android.rcapstone.data.db.Contract;
import info.pelleritoudacity.android.rcapstone.data.db.Operation.T1Operation;
import info.pelleritoudacity.android.rcapstone.data.db.util.DataUtils;
import info.pelleritoudacity.android.rcapstone.data.model.reddit.T1;
import info.pelleritoudacity.android.rcapstone.data.model.ui.DetailModel;
import info.pelleritoudacity.android.rcapstone.data.model.reddit.More;
import info.pelleritoudacity.android.rcapstone.data.rest.RestMoreExecute;
import info.pelleritoudacity.android.rcapstone.data.rest.RestDetailExecute;
import info.pelleritoudacity.android.rcapstone.ui.fragment.DetailFragment;
import info.pelleritoudacity.android.rcapstone.ui.fragment.TitleDetailFragment;
import info.pelleritoudacity.android.rcapstone.ui.helper.DetailHelper;
import info.pelleritoudacity.android.rcapstone.ui.helper.MenuBase;
import info.pelleritoudacity.android.rcapstone.utility.Costant;
import info.pelleritoudacity.android.rcapstone.utility.NetworkUtil;
import info.pelleritoudacity.android.rcapstone.utility.PermissionUtil;
import info.pelleritoudacity.android.rcapstone.utility.Preference;

public class DetailActivity extends BaseActivity
        implements RestDetailExecute.OnRestCallBack,
        DetailFragment.OnFragmentInteractionListener, RestMoreExecute.OnRestCallBack,
        SwipeRefreshLayout.OnRefreshListener, SearchView.OnQueryTextListener {

    @SuppressWarnings({"WeakerAccess", "CanBeFinal", "unused"})
    @BindView(R.id.detail_container)
    public CoordinatorLayout mContainer;

    @SuppressWarnings({"WeakerAccess", "CanBeFinal", "unused"})
    @BindView(R.id.nested_scrollview_detail)
    public NestedScrollView mNestedScrollView;

    @SuppressWarnings({"WeakerAccess", "CanBeFinal", "unused"})
    @BindView(R.id.swipe_refresh_detail)
    public SwipeRefreshLayout mSwipeRefreshLayout;

    private Context mContext;
    private DetailModel model;
    private DetailHelper mDetailHelper;
    private long startTimeoutRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setLayoutResource(R.layout.activity_detail);
        super.onCreate(savedInstanceState);

        mContext = getApplicationContext();
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mDetailHelper = new DetailHelper(mContext);

        if (savedInstanceState == null) {
            model = mDetailHelper.initModelTarget(getIntent());

            mSwipeRefreshLayout.setRefreshing(true);
            onRefresh();
        } else {
            model = savedInstanceState.getParcelable(Costant.EXTRA_PARCEL_ACTIVITY_DETAIL);

        }

        Preference.setLastComment(mContext, Objects.requireNonNull(model).getStrId());

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

            case Costant.DETAIL_TARGET_NO_UPDATE:

                if ((mSwipeRefreshLayout != null) && (mSwipeRefreshLayout.isRefreshing())) {
                    mSwipeRefreshLayout.setRefreshing(false);
                }
                startFragment(model, false);

                break;

            case Costant.DETAIL_TARGET:
                if (System.currentTimeMillis() - startTimeoutRefresh > Costant.DEFAULT_OPERATION_REFRESH) {
                    startTimeoutRefresh = System.currentTimeMillis();
                    new RestDetailExecute(this, mContext, tokenLogin, model).getData();

                } else {
                    startFragment(model, false);
                    mSwipeRefreshLayout.setRefreshing(false);

                }
                break;

            case Costant.MORE_DETAIL_TARGET:
                if (System.currentTimeMillis() - startTimeoutRefresh > Costant.DEFAULT_OPERATION_REFRESH) {
                    startTimeoutRefresh = System.currentTimeMillis();
                    new RestMoreExecute(this, mContext, tokenLogin, model).getMoreData();

                } else {
                    startFragment(model, true);
                    mSwipeRefreshLayout.setRefreshing(false);

                }
                break;

            case Costant.SEARCH_DETAIL_TARGET:
                startFragment(model, false);
                break;

            case Costant.MORE_SEARCH_DETAIL_TARGET:
                startFragment(model, false);
                break;

            case Costant.FAVORITE_DETAIL_TARGET:
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
    protected boolean onPrepareOptionsPanel(View view, Menu menu) {

        if (menu.findItem(R.id.menu_action_search) == null) {
            getMenuInflater().inflate(R.menu.menu_search, menu);
            MenuBase menuBase = new MenuBase(mContext, R.layout.activity_detail);
            menuBase.menuItemSearch(this, getComponentName(), menu);
        }

        return super.onPrepareOptionsPanel(view, menu);
    }

    @Override
    public boolean onQueryTextSubmit(String s) {

        Intent intent = new Intent(getApplicationContext(), DetailActivity.class);

        model.setStrQuerySearch(s);

        switch (model.getTarget()) {
            case Costant.DETAIL_TARGET_NO_UPDATE:
            case Costant.DETAIL_TARGET:
                model.setTarget(Costant.SEARCH_DETAIL_TARGET);
                intent.putExtra(Costant.EXTRA_PARCEL_DETAIL_MODEL, model);
                break;

            case Costant.MORE_DETAIL_TARGET:
                model.setTarget(Costant.MORE_SEARCH_DETAIL_TARGET);
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

        if (!getSupportFragmentManager().isStateSaved()) {

            if (moreFragment) {
                DetailFragment fragment = DetailFragment.newInstance(m);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_detail_container, fragment).commit();

            } else {

                TitleDetailFragment titleDetailFragment = TitleDetailFragment.newInstance(m.getStrId());
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_title_container, titleDetailFragment).commit();

                DetailFragment fragment = DetailFragment.newInstance(m);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_detail_container, fragment).commit();

            }
        }

        if ((mSwipeRefreshLayout != null) && (mSwipeRefreshLayout.isRefreshing())) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }

    private boolean isUpdateData() {
        return new DataUtils(mContext).isSyncDataDetail(Contract.T1dataEntry.CONTENT_URI,
                model, Preference.getGeneralSettingsSyncFrequency(mContext));
    }


    @Override
    public void success(List<T1> response, int code) {
        if ((response != null) && (model.getStrId() != null)) {
            T1Operation data = new T1Operation(getApplicationContext());
            if (data.saveData(response, model.getStrId())) {
                startFragment(model, false);
            } else {
                Snackbar.make(mContainer, R.string.error_state_critical, Snackbar.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void success(More response, int code) {
        if (response != null) {
            if (response.getJson().getData() != null) {

                T1Operation t1moreOperation = new T1Operation(getApplicationContext());

                if (t1moreOperation.saveMoreData(response.getJson(), model.getStrId())) {

                    startFragment(model, true);

                }

            }

        }

    }
}
