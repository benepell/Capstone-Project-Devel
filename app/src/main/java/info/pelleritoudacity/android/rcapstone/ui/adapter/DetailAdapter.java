package info.pelleritoudacity.android.rcapstone.ui.adapter;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import info.pelleritoudacity.android.rcapstone.R;
import info.pelleritoudacity.android.rcapstone.data.db.AppDatabase;
import info.pelleritoudacity.android.rcapstone.data.db.entry.T1Entry;
import info.pelleritoudacity.android.rcapstone.data.db.record.DetailRecord;
import info.pelleritoudacity.android.rcapstone.data.db.util.DataUtils;
import info.pelleritoudacity.android.rcapstone.data.model.record.RecordAdapterDetail;
import info.pelleritoudacity.android.rcapstone.data.model.reddit.SubmitData;
import info.pelleritoudacity.android.rcapstone.data.model.ui.CardBottomModel;
import info.pelleritoudacity.android.rcapstone.data.model.ui.DetailModel;
import info.pelleritoudacity.android.rcapstone.data.rest.CommentExecute;
import info.pelleritoudacity.android.rcapstone.data.rest.DelExecute;
import info.pelleritoudacity.android.rcapstone.ui.helper.SelectorHelper;
import info.pelleritoudacity.android.rcapstone.ui.helper.DetailHelper;
import info.pelleritoudacity.android.rcapstone.utility.Costant;
import info.pelleritoudacity.android.rcapstone.utility.DateUtil;
import info.pelleritoudacity.android.rcapstone.utility.NetworkUtil;
import info.pelleritoudacity.android.rcapstone.utility.PermissionUtil;
import info.pelleritoudacity.android.rcapstone.utility.Preference;
import info.pelleritoudacity.android.rcapstone.utility.TextUtil;
import okhttp3.ResponseBody;

public class DetailAdapter extends RecyclerView.Adapter<DetailAdapter.SubRedditDetailHolder> implements SelectorHelper.OnSelector {

    private final Context mContext;
    private final OnAdapterListener mListener;
    private int mSelectorPosition = RecyclerView.NO_POSITION;
    private final DetailModel model;
    private List<T1Entry> mEntry;
    private final AppDatabase mDb;

    public DetailAdapter(OnAdapterListener listener, Context context, AppDatabase db, DetailModel detailModel) {
        mListener = listener;
        mContext = context;
        mDb = db;
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

        T1Entry t =  getEntry().get(position);

        DetailRecord recordList = new DetailRecord(t);
        RecordAdapterDetail record = null;
        if (recordList.getRecordList() != null) {
            record = recordList.getRecordList().get(0);

        }

        if (record != null) {

            if (!TextUtils.isEmpty(record.getAuthor())) {
                holder.mTextViewAuthorDetail.setText(record.getAuthor().concat(":"));

            }

            holder.mTextViewPostedOnDetail.setText(DateUtil.getDiffTimeMinute(mContext, record.getCreated()));

            holder.mTextViewPointsDetail.setText(String.valueOf(record.getScore()));

            if (!TextUtils.isEmpty(record.getTitle()))
                holder.mTextViewBodyDetail.setText(record.getTitle());

            if (!TextUtils.isEmpty(record.getBody())) {
                holder.mTextViewBodyDetail.setText(record.getBody());

            }

            if (Preference.isGeneralLinks(mContext)) {
                holder.mTextViewBodyDetail.setClickable(true);
            } else {
                holder.mTextViewBodyDetail.setClickable(false);
            }
            DetailHelper detailHelper = new DetailHelper(mContext);
            detailHelper.initDepthIndicator(holder.mDepthIndicator, holder.mCardLinear, record.getDepth(), true, false);
            String strBackGroundColor = detailHelper.initDepthIndicator(holder.mDepthSelect, holder.mSelectorContainer, record.getDepth(), true, true);

            SelectorHelper selectorHelper = new SelectorHelper(this, mContext,mDb, holder.itemView);

            ImageButton[] arrayButton = new ImageButton[]{holder.mImageButtonVoteUp, holder.mImageButtonVoteDown,
                    holder.mImageButtonPreferStars, holder.mImageButtonShowComments, holder.mImageButtonOpenBrowser};

            CardBottomModel cardBottomModel = new CardBottomModel();
            cardBottomModel.setPosition(holder.getAdapterPosition());
            cardBottomModel.setScore(record.getScore());
            cardBottomModel.setDirScore(record.getDirScore());
            cardBottomModel.setArrayButton(arrayButton);
            cardBottomModel.setBackgroundColor(strBackGroundColor);
            cardBottomModel.setLinkComment(TextUtil.buildCommentDetailLink(record.getPermanentLink()));
            cardBottomModel.setCategory(record.getSubRedditName());
            cardBottomModel.setSaved(record.isSaved());
            cardBottomModel.setOnline(NetworkUtil.isOnline(mContext));
            cardBottomModel.setLogged(PermissionUtil.isLogged(mContext));
            cardBottomModel.setTitle(record.getBody());
            cardBottomModel.setAuthor(record.getAuthor());

            selectorHelper.cardBottomLink(cardBottomModel);

            if (position != mSelectorPosition) {
                holder.mSelectorContainer.setVisibility(View.GONE);

            } else {
                holder.mSelectorContainer.setVisibility(View.VISIBLE);
            }

            if (record.isSaved()) {
                holder.mCardLinear.setBackgroundColor(Color.YELLOW);
            }

            int id = record.getId();
            String strId = record.getLinkId().replaceAll(Costant.STR_PARENT_LINK, "");
            String strLinkId = record.getLinkId();
            String strArrId = record.getMoreComments();

            if ((record.getNumComments() > 0) && (TextUtils.isEmpty(model.getStrLinkId()))) {

                holder.mTextViewReplies.setText(String.format("%s %s",
                        String.valueOf(record.getNumComments()), mContext.getString(R.string.text_more_replies)));
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

            holder.mTextViewBodyDetail.setOnClickListener(view -> {
                clickSelector(position);
                mListener.clickSelector(position, getItemCount());
                notifyDataSetChanged();
            });

            holder.bind(position, getItemCount());

        }
    }

    @Override
    public int getItemCount() {
        return (getEntry() == null) ? 0 : getEntry().size();
    }

    @Override
    public void vote(int position, int score, boolean voteUp, int dir, String linkId) {
        new DataUtils(mContext,mDb).updateVote(Costant.DB_TABLE_T1, score, voteUp, dir, linkId);
        mListener.selectorChange(position);
    }

    @Override
    public void comments(View view, Dialog dialog, String title, String text, String fullname) {
        new CommentExecute(new CommentExecute.OnRestCallBack() {
            @Override
            public void success(SubmitData response, int code) {
                dialog.dismiss();
                if(response.isSuccess()){
                    Snackbar.make(view, mContext.getString(R.string.text_comment_saved), Snackbar.LENGTH_LONG).show();
                }else {
                    Snackbar.make(view, mContext.getString(R.string.text_error_comment), Snackbar.LENGTH_LONG).show();
                }
            }

            @Override
            public void unexpectedError(Throwable tList) {
                dialog.dismiss();
                Snackbar.make(view, mContext.getString(R.string.text_error_comment), Snackbar.LENGTH_LONG).show();

            }
        }, PermissionUtil.getToken(mContext),text,fullname ).postData();
    }

    @Override
    public void deleteComments(View view, Dialog dialog, String fullname) {
        if(!TextUtils.isEmpty(fullname)) {

            new DelExecute(new DelExecute.OnRestCallBack() {
                @Override
                public void success(ResponseBody response, int code) {
                    if(code==200){
                        Snackbar.make(view, mContext.getString(R.string.text_comment_deleted), Snackbar.LENGTH_LONG).show();

                    }else {
                        Snackbar.make(view, mContext.getString(R.string.text_error_comment), Snackbar.LENGTH_LONG).show();

                    }

                    dialog.dismiss();

                }

                @Override
                public void unexpectedError(Throwable tList) {
                    dialog.dismiss();
                    Snackbar.make(view, mContext.getString(R.string.text_error_comment), Snackbar.LENGTH_LONG).show();

                }
            },PermissionUtil.getToken(mContext),fullname).delData();

        }
    }

    @Override
    public void snackMsg(int resource) {
       mListener.snackMsg(resource);
    }

    @Override
    public void stars(int position) {
        mListener.selectorChange(position);
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

        private  int mPosition;
        private int mItemCount;

        SubRedditDetailHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            clickSelector(mPosition);
            mListener.clickSelector(mPosition, mItemCount);
            notifyDataSetChanged();

        }

        void bind(int position, int itemCount) {
            mPosition = position;
            mItemCount = itemCount;
        }

    }

    private void clickSelector(int position) {
        if (mSelectorPosition != RecyclerView.NO_POSITION) {
            mSelectorPosition = RecyclerView.NO_POSITION;
        } else {
            mSelectorPosition = position;
        }
    }

    public interface OnAdapterListener {

        void clickSelector(int position, int itemCount);

        void onClickMore(DetailModel detailModel);

        void snackMsg(int resource);

        void selectorChange(int position);
    }

    @SuppressWarnings("WeakerAccess")
    public List<T1Entry> getEntry() {
        return mEntry;
    }

    public void setEntry(List<T1Entry> entry) {
        mEntry = entry;
        notifyDataSetChanged();
    }


}