package info.pelleritoudacity.android.rcapstone.ui.adapter;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import info.pelleritoudacity.android.rcapstone.R;
import info.pelleritoudacity.android.rcapstone.data.db.Record.DetailRecord;
import info.pelleritoudacity.android.rcapstone.data.model.record.RecordAdapterDetail;
import info.pelleritoudacity.android.rcapstone.data.model.ui.CardBottomModel;
import info.pelleritoudacity.android.rcapstone.data.model.ui.DetailModel;
import info.pelleritoudacity.android.rcapstone.ui.fragment.DetailFragment;
import info.pelleritoudacity.android.rcapstone.ui.helper.SelectorHelper;
import info.pelleritoudacity.android.rcapstone.ui.helper.DetailHelper;
import info.pelleritoudacity.android.rcapstone.utility.Costant;
import info.pelleritoudacity.android.rcapstone.utility.DateUtil;
import info.pelleritoudacity.android.rcapstone.utility.ImageUtil;
import info.pelleritoudacity.android.rcapstone.utility.NetworkUtil;
import info.pelleritoudacity.android.rcapstone.utility.PermissionUtil;
import info.pelleritoudacity.android.rcapstone.utility.Preference;
import info.pelleritoudacity.android.rcapstone.utility.TextUtil;
import info.pelleritoudacity.android.rcapstone.utility.Utility;

public class DetailAdapter extends RecyclerView.Adapter<DetailAdapter.SubRedditDetailHolder> {

    private final Context mContext;
    private final DetailFragment mListener;
    private Cursor mCursor;
    private int mSelectorPosition = RecyclerView.NO_POSITION;
    private final DetailModel model;

    public DetailAdapter(DetailFragment listener, DetailModel detailModel) {
        mListener = listener;
        mContext = listener.getActivity();
        model = detailModel;
    }


    @NonNull
    @Override
    public SubRedditDetailHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int layoutId = R.layout.list_detail;
        LayoutInflater inflater = LayoutInflater.from(mContext);

        View view = inflater.inflate(layoutId, parent, false);

        return new SubRedditDetailHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SubRedditDetailHolder holder, int position) {

        mCursor.moveToPosition(position);

        DetailRecord recordList = new DetailRecord(mCursor);
        RecordAdapterDetail record = null;
        if (recordList.getRecordList() != null) {
            record = recordList.getRecordList().get(0);

        }

        if (record != null) {

            if (!TextUtils.isEmpty(record.getAuthor())) {
                holder.mTextViewAuthorDetail.setText(record.getAuthor().concat(":"));

            }

            holder.mTextViewPostedOnDetail.setText(DateUtil.getDiffTimeMinute(mContext, record.getCreated()));

            holder.mTextViewPointsDetail.setText(record.getScore());
            if (!TextUtils.isEmpty(record.getTitle()))
                holder.mTextViewBodyDetail.setText(TextUtil.textFromHtml(record.getTitle()));

            if (!TextUtils.isEmpty(record.getBody())) {
                holder.mTextViewBodyDetail.setText(TextUtil.textFromHtml(record.getBody()));

            }

            holder.mTextViewBodyDetail.setClickable(Preference.isGeneralLinks(mContext));

            if (Preference.isGeneralLinks(mContext)) {
                holder.mTextViewBodyDetail.setClickable(true);
                holder.mTextViewBodyDetail.setAutoLinkMask(Linkify.ALL);
            } else {
                holder.mTextViewBodyDetail.setClickable(false);
            }

            DetailHelper detailHelper = new DetailHelper(mContext);
            detailHelper.initDepthIndicator(holder.mDepthIndicator, holder.mCardLinear, record.getDepth(), true, false);
            String strBackGroundColor = detailHelper.initDepthIndicator(holder.mDepthSelect, holder.mSelectorContainer, record.getDepth(), true, true);

            SelectorHelper selectorHelper = new SelectorHelper(mContext);

            ImageButton[] arrayButton = new ImageButton[]{holder.mImageButtonVoteUp, holder.mImageButtonVoteDown,
                    holder.mImageButtonPreferStars, holder.mImageButtonShowComments, holder.mImageButtonOpenBrowser};

            CardBottomModel cardBottomModel = new CardBottomModel();
            cardBottomModel.setArrayButton(arrayButton);
            cardBottomModel.setBackgroundColor(strBackGroundColor);
            cardBottomModel.setLinkComment(TextUtil.buildCommentDetailLink(record.getPermanentLink()));
            cardBottomModel.setCategory(record.getSubRedditName());
            cardBottomModel.setSaved(record.isSaved());
            cardBottomModel.setOnline(NetworkUtil.isOnline(mContext));
            cardBottomModel.setLogged(PermissionUtil.isLogged(mContext));

            selectorHelper.cardBottomLink(cardBottomModel);

            if (holder.getAdapterPosition() != mSelectorPosition) {
                holder.mSelectorContainer.setVisibility(View.GONE);

            } else {
                holder.mSelectorContainer.setVisibility(View.VISIBLE);
            }

            if(record.isSaved()){
                holder.mCardLinear.setBackgroundColor(Color.YELLOW);
            }

            int id = record.getId();
            String strId = record.getLinkId().replaceAll(Costant.STR_PARENT_LINK, "");
            String strLinkId = record.getLinkId();
            String strArrId = record.getMoreComments();

            if ((record.getNumComments() > 0) && (TextUtils.isEmpty(model.getStrLinkId()))) {

                holder.mTextViewReplies.setText(String.format("%s %s", String.valueOf(record.getNumComments()), mContext.getString(R.string.text_more_replies)));
                holder.mTextViewReplies.setVisibility(View.VISIBLE);

                holder.mTextViewReplies.setOnClickListener(view -> {

                    if (Preference.isLoginStart(mContext)) {
                        holder.mTextViewReplies.setVisibility(View.INVISIBLE);

                        model.setId(id);
                        model.setStrId(strId);
                        model.setStrLinkId(strLinkId);
                        model.setStrArrId(strArrId);

                        mListener.onClickMore(model);
                    } else {
                        Snackbar.make(holder.itemView, R.string.text_start_login, Snackbar.LENGTH_LONG).show();

                    }
                });

            } else {
                holder.mTextViewReplies.setVisibility(View.GONE);

            }

        }
    }

    @Override
    public int getItemCount() {
        return (mCursor == null) ? 0 : mCursor.getCount();
    }

    public class SubRedditDetailHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        @SuppressWarnings("unused")
        @BindView(R.id.tv_author)
        TextView mTextViewAuthorDetail;

        @SuppressWarnings("unused")
        @BindView(R.id.tv_postedOn)
        TextView mTextViewPostedOnDetail;

        @SuppressWarnings("unused")
        @BindView(R.id.tv_points)
        TextView mTextViewPointsDetail;

        @SuppressWarnings("unused")
        @BindView(R.id.tv_body)
        TextView mTextViewBodyDetail;

        @SuppressWarnings("unused")
        @BindView(R.id.tv_replies)
        TextView mTextViewReplies;

        @SuppressWarnings("unused")
        @BindView(R.id.card_linear)
        LinearLayout mCardLinear;

        @SuppressWarnings("unused")
        @BindView(R.id.view_depth)
        View mDepthIndicator;

        @SuppressWarnings("unused")
        @BindView(R.id.view_select_depth)
        View mDepthSelect;

        @SuppressWarnings("unused")
        @BindView(R.id.container_selector)
        LinearLayout mSelectorContainer;

        @SuppressWarnings("unused")
        @BindView(R.id.image_vote_up)
        ImageButton mImageButtonVoteUp;

        @SuppressWarnings("unused")
        @BindView(R.id.image_vote_down)
        ImageButton mImageButtonVoteDown;

        @SuppressWarnings("unused")
        @BindView(R.id.image_prefer_stars)
        ImageButton mImageButtonPreferStars;

        @SuppressWarnings("unused")
        @BindView(R.id.image_show_comments)
        ImageButton mImageButtonShowComments;

        @SuppressWarnings("unused")
        @BindView(R.id.image_open_browser)
        ImageButton mImageButtonOpenBrowser;

        SubRedditDetailHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);

            mTextViewBodyDetail.setOnClickListener(view -> {
                clickSelector(getAdapterPosition());
                mListener.clickSelector(getAdapterPosition(), getItemCount());
                notifyDataSetChanged();

            });
        }

        @Override
        public void onClick(View view) {
            clickSelector(getAdapterPosition());
            mListener.clickSelector(getAdapterPosition(), getItemCount());
            notifyDataSetChanged();
        }

    }

    private void clickSelector(int position) {
        if (mSelectorPosition != RecyclerView.NO_POSITION) {
            mSelectorPosition = RecyclerView.NO_POSITION;
        } else {
            mSelectorPosition = position;
        }
    }

    @SuppressWarnings("UnusedReturnValue")
    public Cursor swapCursor(Cursor c) {
        if (mCursor == c) {
            return null;
        }
        Cursor temp = mCursor;
        this.mCursor = c;

        if (c != null) {
            notifyDataSetChanged();
        }
        return temp;
    }

    public interface OnAdapterListener {

        void clickSelector(int position, int itemCount);

        void onClickMore(DetailModel detailModel);

    }

}