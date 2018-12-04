package info.pelleritoudacity.android.rcapstone.ui.fragment;



import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import info.pelleritoudacity.android.rcapstone.R;
import info.pelleritoudacity.android.rcapstone.data.model.reddit.T5;
import info.pelleritoudacity.android.rcapstone.ui.adapter.SubscribeAdapter;
import info.pelleritoudacity.android.rcapstone.utility.Costant;

public class SubscribeFragment extends Fragment implements SubscribeAdapter.OnRestCallBack {

    @SuppressWarnings({"WeakerAccess", "CanBeFinal", "unused"})
    @BindView(R.id.rv_fragment_subscribe)
    RecyclerView mRecyclerView;

    private Context mContext;
    private Unbinder unbinder;
    private T5 mModelT5;
    private OnRestCallBack mListener;
    private SubscribeAdapter mAdapter;

    public SubscribeFragment() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (OnRestCallBack) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().getLocalClassName() + "must implement OnRestCallBack");
        }
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = getActivity();

        if (getArguments() != null) {
            mModelT5 = getArguments().getParcelable(Costant.EXTRA_FRAGMENT_PARCEL_SUBSCRIBE);

        }
    }

    public static SubscribeFragment newInstance(T5 modelT5) {
        SubscribeFragment fragment = new SubscribeFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(Costant.EXTRA_FRAGMENT_PARCEL_SUBSCRIBE, modelT5);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_subscribe, container, false);

        unbinder = ButterKnife.bind(this, view);

        mContext = getActivity();

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);

        mRecyclerView.setHasFixedSize(true);

        mAdapter = new SubscribeAdapter(this, mContext, mModelT5);

        mRecyclerView.setAdapter(mAdapter);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (savedInstanceState != null) {
            mModelT5 = savedInstanceState.getParcelable(Costant.EXTRA_FRAGMENT_PARCEL_SUBSCRIBE);
        }

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putParcelable(Costant.EXTRA_FRAGMENT_PARCEL_SUBSCRIBE, mModelT5);
        super.onSaveInstanceState(outState);

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @Override
    public void onClickSubscribe(int position) {
        if (position > RecyclerView.NO_POSITION) {
            mAdapter.notifyItemChanged(position);
        }
        mListener.onClickSubscribe(position);
    }

    @Override
    public void onClickCategory(String category, String fullname, Object userIsSubscriber) {
        mListener.onClickCategory(category, fullname, userIsSubscriber);
    }

    public interface OnRestCallBack {
        void onClickSubscribe(int position);

        void onClickCategory(String category, String fullname, Object userIsSubscriber);
    }
}
