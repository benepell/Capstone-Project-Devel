package info.pelleritoudacity.android.rcapstone.ui.adapter;


import android.content.Context;
import android.graphics.LightingColorFilter;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import info.pelleritoudacity.android.rcapstone.R;
import info.pelleritoudacity.android.rcapstone.data.model.reddit.T5;
import info.pelleritoudacity.android.rcapstone.data.rest.PostExecute;
import info.pelleritoudacity.android.rcapstone.ui.helper.ItemTouchHelperViewHolder;
import info.pelleritoudacity.android.rcapstone.utility.DateUtil;
import info.pelleritoudacity.android.rcapstone.utility.ImageUtil;
import info.pelleritoudacity.android.rcapstone.utility.PermissionUtil;

public class SubscribeAdapter extends RecyclerView.Adapter<SubscribeAdapter.SubscribeHolder> {

    private final Context mContext;
    private final T5 mModelT5;
    private final OnRestCallBack mListener;

    public SubscribeAdapter(OnRestCallBack listener, Context context, T5 modelT5) {
        mListener = listener;
        mContext = context;
        mModelT5 = modelT5;
    }

    @NonNull
    @Override
    public SubscribeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int layoutId = R.layout.list_subscribe;
        LayoutInflater inflater = LayoutInflater.from(mContext);

        View view = inflater.inflate(layoutId, parent, false);

        return new SubscribeHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SubscribeAdapter.SubscribeHolder holder, int position) {

        String fullname = null;
        String displayNamePrefix = null;
        String title = null;
        Integer subscribers = null;
        Double createUtc = null;
        Object userIsSubscriber = null;

        if (mModelT5 != null) {

            fullname = mModelT5.getData().getChildren().get(position).getData().getName();
            displayNamePrefix = mModelT5.getData().getChildren().get(position).getData().getDisplayNamePrefixed();
            title = mModelT5.getData().getChildren().get(position).getData().getTitle();
            subscribers = mModelT5.getData().getChildren().get(position).getData().getSubscribers();
            createUtc = mModelT5.getData().getChildren().get(position).getData().getCreatedUtc();
            userIsSubscriber = mModelT5.getData().getChildren().get(position).getData().getUserIsSubscriber();

        }

        if (userIsSubscriber == null) {
            holder.mButtonIsSubscribe.setText(R.string.text_pvt);

        } else if (userIsSubscriber.equals(true)) {
            holder.mButtonIsSubscribe.setText(R.string.text_unsubscribe);
            holder.mButtonIsSubscribe.getBackground().setColorFilter(new LightingColorFilter(0xFF5252, 0xFF5252));
            String finalFullname = fullname;
            holder.mButtonIsSubscribe.setOnClickListener(v -> postSubscribe(mContext, mModelT5, false, finalFullname, position));


        } else if (userIsSubscriber.equals(false)) {
            holder.mButtonIsSubscribe.setText(R.string.text_subscribe);
            String finalFullname1 = fullname;
            holder.mButtonIsSubscribe.setOnClickListener(v -> postSubscribe(mContext, mModelT5, true, finalFullname1, position));
        }


        if (TextUtils.isEmpty(displayNamePrefix)) {
            holder.mTextViewSubscribeName.setText(R.string.text_no_subscribers);

        } else {
            holder.mTextViewSubscribeName.setText(displayNamePrefix);

        }

        if (TextUtils.isEmpty(title)) {
            holder.mTextViewTitle.setText(R.string.text_no_title);

        } else {
            holder.mTextViewTitle.setText(title);

        }


        if (subscribers != null) {
            holder.mTextViewSubscriberNo.setText(String.format(mContext.getString(R.string.text_subscribers), String.valueOf(subscribers)));

        } else {
            holder.mTextViewSubscriberNo.setText(mContext.getString(R.string.text_no_subscribers));


        }

        if (createUtc != null) {
            holder.mTextViewCreatedUtc.setText(DateUtil.timeFormat(String.valueOf(createUtc)));

        }

        holder.bind(displayNamePrefix, fullname, userIsSubscriber);
    }

    @Override
    public int getItemCount() {
        return (mModelT5 != null) ? mModelT5.getData().getChildren().size() : 0;
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull SubscribeAdapter.SubscribeHolder holder) {
        super.onViewDetachedFromWindow(holder);

    }


    public class SubscribeHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener, ItemTouchHelperViewHolder {

        @SuppressWarnings("unused")
        @BindView(R.id.tv_subscribe_title)
        TextView mTextViewTitle;

        @SuppressWarnings("unused")
        @BindView(R.id.tv_is_subscribe)
        Button mButtonIsSubscribe;

        @SuppressWarnings("unused")
        @BindView(R.id.tv_subscribe_name)
        TextView mTextViewSubscribeName;

        @SuppressWarnings("unused")
        @BindView(R.id.tv_subscribers)
        TextView mTextViewSubscriberNo;

        @SuppressWarnings("unused")
        @BindView(R.id.tv_subscribe_created_utc)
        TextView mTextViewCreatedUtc;

        @SuppressWarnings("unused")
        @BindView(R.id.tv_is_over18)
        TextView mTextViewIsOver;

        private String mName;
        private String mFullname;
        private Object mUserIsSubscriber;

        SubscribeHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onItemSelected() {
            itemView.setBackgroundColor(ImageUtil.getColor(mContext, R.color.colorBackgroundItemSelected));
        }

        @Override
        public void onItemClear() {
            itemView.setBackgroundColor(ImageUtil.getColor(mContext, R.color.colorBackgroundItemNoSelected));
        }

        @Override
        public void onClick(View view) {
                mListener.onClickCategory(mName, mFullname,mUserIsSubscriber);

        }

        void bind(String name, String fullname, Object userIsSubscriber) {
            mName = name;
            mFullname = fullname;
            mUserIsSubscriber = userIsSubscriber;
        }


    }

    private void postSubscribe(Context context, T5 model, boolean subscribe, String fullname, int position) {

            String action = (subscribe) ? "sub" : "unsub";

            new PostExecute((response, code) -> {
                if (code == 200) {
                    if (subscribe) {
                        model.getData().getChildren().get(position).getData().setUserIsSubscriber(Boolean.TRUE);

                    } else {
                        model.getData().getChildren().get(position).getData().setUserIsSubscriber(Boolean.FALSE);

                    }
                    mListener.onClickSubscribe(position);

                }
            }, PermissionUtil.getToken(context), action, fullname).postSubcribe();

    }

    public interface OnRestCallBack {
        void onClickSubscribe(int position);

        void onClickCategory(String category, String fullname, Object userIsSubscriber);
    }
}
