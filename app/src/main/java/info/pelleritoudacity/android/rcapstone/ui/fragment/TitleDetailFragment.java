package info.pelleritoudacity.android.rcapstone.ui.fragment;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import info.pelleritoudacity.android.rcapstone.R;
import info.pelleritoudacity.android.rcapstone.data.db.AppDatabase;
import info.pelleritoudacity.android.rcapstone.data.db.entry.T3Entry;
import info.pelleritoudacity.android.rcapstone.data.db.viewmodel.T3TitleDetailViewModel;
import info.pelleritoudacity.android.rcapstone.data.db.viewmodel.T3TitleDetailViewModelFactory;
import info.pelleritoudacity.android.rcapstone.ui.adapter.TitleDetailAdapter;
import info.pelleritoudacity.android.rcapstone.utility.Costant;
import info.pelleritoudacity.android.rcapstone.utility.Preference;
import info.pelleritoudacity.android.rcapstone.utility.Utility;
import timber.log.Timber;

public class TitleDetailFragment extends Fragment
        implements TitleDetailAdapter.OnVoteChange {

    @SuppressWarnings({"WeakerAccess", "CanBeFinal", "unused"})
    @BindView(R.id.rv_fragment_detail_title)
    RecyclerView mRecyclerView;

    private Context mContext;
    private OnFragmentInteractionListener mListener;
    private Unbinder unbinder;

    private String mStrId;

    private TitleDetailAdapter mAdapter;
    private AppDatabase mDb;

    public TitleDetailFragment() {
    }

    public static TitleDetailFragment newInstance(String strId) {
        TitleDetailFragment fragment = new TitleDetailFragment();
        Bundle bundle = new Bundle();
        Timber.d("Benny mod title detail strid:%s",strId);
        bundle.putString(Costant.EXTRA_FRAGMENT_SUBREDDIT_SELECTED, strId);
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

        mContext = getActivity();

        if (getArguments() != null) {
            mStrId = getArguments().getString(Costant.EXTRA_FRAGMENT_SUBREDDIT_SELECTED);
        }

        mDb = AppDatabase.getInstance(getActivity());
        setupViewModel();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_detail_title, container, false);

        unbinder = ButterKnife.bind(this, view);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mRecyclerView.setHasFixedSize(true);

        mAdapter = new TitleDetailAdapter(this, mContext,mDb);

        mRecyclerView.setAdapter(mAdapter);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (savedInstanceState != null) {
            mStrId = savedInstanceState.getString(Costant.EXTRA_FRAGMENT_STRING_ID);
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(Costant.EXTRA_FRAGMENT_STRING_ID, mStrId);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void selectorChange(int position) {
    }

    @Override
    public void snackMsg(int resource) {
        mListener.snackMsg(resource);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void snackMsg(int resource);
    }

    private void setupViewModel(){
        T3Entry entry = new T3Entry();
        entry.setNameId(mStrId);
        entry.setOver18(Utility.boolToInt(Preference.isLoginOver18(getActivity())));
        T3TitleDetailViewModelFactory factory = new T3TitleDetailViewModelFactory(mDb,entry);
        T3TitleDetailViewModel viewModel = ViewModelProviders.of(this,factory).get(T3TitleDetailViewModel.class);
        viewModel.getTask().observe(this, t3Entries -> {
            if((t3Entries!=null)&&(mAdapter!=null)){
                mAdapter.setEntry(t3Entries);
            }
        });
    }
}
