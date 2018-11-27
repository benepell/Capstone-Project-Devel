package info.pelleritoudacity.android.rcapstone.ui.fragment;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import info.pelleritoudacity.android.rcapstone.R;
import info.pelleritoudacity.android.rcapstone.data.db.AppDatabase;
import info.pelleritoudacity.android.rcapstone.data.db.entry.T1Entry;
import info.pelleritoudacity.android.rcapstone.data.db.viewmodel.T1TargetViewModel;
import info.pelleritoudacity.android.rcapstone.data.db.viewmodel.T1TargetViewModelFactory;
import info.pelleritoudacity.android.rcapstone.data.model.ui.DetailModel;
import info.pelleritoudacity.android.rcapstone.ui.adapter.DetailAdapter;
import info.pelleritoudacity.android.rcapstone.utility.Costant;
import info.pelleritoudacity.android.rcapstone.utility.Preference;
import info.pelleritoudacity.android.rcapstone.utility.Utility;

public class DetailFragment extends Fragment
        implements DetailAdapter.OnAdapterListener {

    @SuppressWarnings({"WeakerAccess", "CanBeFinal", "unused"})
    @BindView(R.id.rv_fragment_detail)
    RecyclerView mRecyclerView;

    private Unbinder unbinder;
    private DetailAdapter mAdapter;
    private OnFragmentInteractionListener mListener;
    private DetailModel model;
    private AppDatabase mDb;

    public DetailFragment() {
    }

    public static DetailFragment newInstance(DetailModel m) {
        DetailFragment fragment = new DetailFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(Costant.EXTRA_FRAGMENT_PARCEL_SUBREDDIT_DETAIL, m);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (OnFragmentInteractionListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().getLocalClassName() + "must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            model = getArguments().getParcelable(Costant.EXTRA_FRAGMENT_PARCEL_SUBREDDIT_DETAIL);
        }

        mDb = AppDatabase.getInstance(getActivity());
        setupViewModel();

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_detail, container, false);

        unbinder = ButterKnife.bind(this, view);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(Objects.requireNonNull(getActivity()), LinearLayoutManager.VERTICAL);

        dividerItemDecoration.setDrawable(Objects.requireNonNull(getActivity()).getResources().getDrawable(R.drawable.divider_decoration));

        mRecyclerView.addItemDecoration(dividerItemDecoration);

        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mRecyclerView.setLayoutManager(layoutManager);

        mRecyclerView.setHasFixedSize(true);

        mAdapter = new DetailAdapter(this, getActivity(),mDb, model);

        mRecyclerView.setAdapter(mAdapter);

        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (savedInstanceState != null) {
            model = savedInstanceState.getParcelable(Costant.EXTRA_FRAGMENT_PARCEL_SUBREDDIT_DETAIL);

        }

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putParcelable(Costant.EXTRA_FRAGMENT_PARCEL_SUBREDDIT_DETAIL, model);
        super.onSaveInstanceState(outState);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @Override
    public void clickSelector(int position, int itemCount) {
        mListener.clickSelector(position, itemCount);
    }

    @Override
    public void onClickMore(DetailModel detailModel) {
        mListener.onClickMore(detailModel);
    }

    @Override
    public void snackMsg(int resource) {
       mListener.snackMsg(resource);
    }

    @Override
    public void selectorChange(int position) {
   }

    private void setupViewModel(){
        T1Entry entry = new T1Entry();
        entry.setNameId(model.getStrId());
        entry.setId(model.getId());
        entry.setOver18(Utility.boolToInt(Preference.isLoginOver18(getActivity())));

        T1TargetViewModelFactory factory = new T1TargetViewModelFactory(mDb,entry,model.getStrQuerySearch(),model.getTarget());
        T1TargetViewModel viewModel = ViewModelProviders.of(this,factory).get(T1TargetViewModel.class);
        viewModel.getTask().observe(this, t1Entries -> {
            if((t1Entries!=null)&&(mAdapter!=null)){
                mAdapter.setEntry(t1Entries);
                mListener.detailFragmentResult(t1Entries.size());
            }
        });

    }

    public interface OnFragmentInteractionListener {
        void clickSelector(int position, int itemCount);

        void onClickMore(DetailModel detailModel);

        void snackMsg(int resource);
        void detailFragmentResult(int count);
    }

}
