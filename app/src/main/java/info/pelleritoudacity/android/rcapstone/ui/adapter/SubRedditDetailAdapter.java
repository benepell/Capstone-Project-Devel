package info.pelleritoudacity.android.rcapstone.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.lang.ref.WeakReference;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import info.pelleritoudacity.android.rcapstone.R;
import info.pelleritoudacity.android.rcapstone.data.db.Contract;
import info.pelleritoudacity.android.rcapstone.data.db.Record.RecordSubRedditDetail;
import info.pelleritoudacity.android.rcapstone.data.db.util.DataUtils;
import info.pelleritoudacity.android.rcapstone.data.model.record.RecordAdapterDetail;
import info.pelleritoudacity.android.rcapstone.data.rest.RevokeTokenExecute;
import info.pelleritoudacity.android.rcapstone.ui.activity.SubManageActivity;
import info.pelleritoudacity.android.rcapstone.ui.activity.SubRedditActivity;
import info.pelleritoudacity.android.rcapstone.ui.activity.SubRedditDetailActivity;
import info.pelleritoudacity.android.rcapstone.ui.fragment.SubRedditDetailFragment;
import info.pelleritoudacity.android.rcapstone.ui.helper.SelectorHelper;
import info.pelleritoudacity.android.rcapstone.ui.helper.SubRedditDetailHelper;
import info.pelleritoudacity.android.rcapstone.utility.Costant;
import info.pelleritoudacity.android.rcapstone.utility.DateUtil;
import info.pelleritoudacity.android.rcapstone.utility.Preference;
import info.pelleritoudacity.android.rcapstone.utility.TextUtil;
import timber.log.Timber;

public class SubRedditDetailAdapter extends RecyclerView.Adapter<SubRedditDetailAdapter.SubRedditDetailHolder> {

    private final Context mContext;
    private final SubRedditDetailFragment mListener;
    private Cursor mCursor;
    private int mSelectorPosition = RecyclerView.NO_POSITION;
    private String mStrArrId;

    public SubRedditDetailAdapter(SubRedditDetailFragment listener) {
        mListener = listener;
        mContext = listener.getActivity();
    }


    @NonNull
    @Override
    public SubRedditDetailHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int layoutId = R.layout.list_subreddit_detail;
        LayoutInflater inflater = LayoutInflater.from(mContext);

        View view = inflater.inflate(layoutId, parent, false);

        return new SubRedditDetailHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SubRedditDetailHolder holder, int position) {

        ImageButton[] mArrayButton = new ImageButton[]{holder.mImageButtonVoteUp, holder.mImageButtonVoteDown,
                holder.mImageButtonPreferStars, holder.mImageButtonShowComments, holder.mImageButtonOpenBrowser};

        mCursor.moveToPosition(position);

        RecordSubRedditDetail recordList = new RecordSubRedditDetail(mCursor);
        RecordAdapterDetail record = null;
        if (recordList.getRecordList() != null) {
            record = recordList.getRecordList().get(0);

        }

        if (record != null) {

            String numCom = String.valueOf(record.getNumComments());
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

            SubRedditDetailHelper subRedditDetailHelper = new SubRedditDetailHelper(mContext);
            subRedditDetailHelper.initDepthIndicator(holder.mDepthIndicator, holder.mCardLinear, record.getDepth(), true, false);
            String strBackGroundColor = subRedditDetailHelper.initDepthIndicator(holder.mDepthSelect, holder.mSelectorContainer, record.getDepth(), true, true);

            SelectorHelper selectorHelper = new SelectorHelper(mContext);
            selectorHelper.cardBottomLink(mArrayButton, strBackGroundColor,
                    TextUtil.buildCommentDetailLink(record.getPermanentLink()), record.getSubRedditName());

            if (holder.getAdapterPosition() != mSelectorPosition) {
                holder.mSelectorContainer.setVisibility(View.GONE);

            } else {
                holder.mSelectorContainer.setVisibility(View.VISIBLE);
            }

            String strId = record.getLinkId().replaceAll(Costant.STR_PARENT_LINK, "");
            String category = record.getSubReddit();
            String strArrId = record.getMoreComments();
            String strLinkId = record.getLinkId();
            if (record.getNumComments() > 0) {

                holder.mTextViewReplies.setText(String.format("%s %s", String.valueOf(record.getNumComments()), mContext.getString(R.string.text_more_replies)));
                holder.mTextViewReplies.setVisibility(View.VISIBLE);

                holder.mTextViewReplies.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if (Preference.isLoginStart(mContext)) {

                            Intent moreIntent = new Intent(mContext, SubRedditDetailActivity.class);
                            moreIntent.putExtra(Costant.EXTRA_SUBREDDIT_DETAIL_STR_ID, strId);
                            moreIntent.putExtra(Costant.EXTRA_MORE_DETAIL_STRING_LINKID, strLinkId);
                            moreIntent.putExtra(Costant.EXTRA_MORE_DETAIL_STRING_ARRID, strArrId);
                            moreIntent.putExtra(Costant.EXTRA_SUBREDDIT_DETAIL_CATEGORY, category);
                            // moreIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK );
                            mContext.startActivity(moreIntent);

                            holder.mTextViewReplies.setVisibility(View.INVISIBLE);
                        } else {
                            Toast.makeText(mContext, mContext.getString(R.string.text_start_login), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            } else {
                holder.mTextViewReplies.setVisibility(View.GONE);

            }

            mListener.adapterPosition(holder.getAdapterPosition(), record.getSubReddit());
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

        void bind() {
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

        void adapterPosition(int position, String category);

        void clickSelector(int position, int itemCount);
    }

}