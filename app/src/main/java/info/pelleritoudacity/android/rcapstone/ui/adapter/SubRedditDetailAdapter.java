package info.pelleritoudacity.android.rcapstone.ui.adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
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
import info.pelleritoudacity.android.rcapstone.data.Contract;
import info.pelleritoudacity.android.rcapstone.ui.fragment.SubRedditDetailFragment;
import info.pelleritoudacity.android.rcapstone.ui.helper.SelectorHelper;
import info.pelleritoudacity.android.rcapstone.ui.helper.SubRedditDetailHelper;
import info.pelleritoudacity.android.rcapstone.utility.DateUtil;
import info.pelleritoudacity.android.rcapstone.utility.Preference;
import info.pelleritoudacity.android.rcapstone.utility.TextUtil;

public class SubRedditDetailAdapter extends RecyclerView.Adapter<SubRedditDetailAdapter.SubRedditDetailHolder> {

    private final Context mContext;
    private final SubRedditDetailFragment mListener;
    private Cursor mCursor;
    private ImageButton[] mArrayButton;
    private static int mSelectorPosition = RecyclerView.NO_POSITION;


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

        mArrayButton = new ImageButton[]{holder.mImageButtonVoteUp, holder.mImageButtonVoteDown,
                holder.mImageButtonPreferStars, holder.mImageButtonShowComments, holder.mImageButtonOpenBrowser};

        mCursor.moveToPosition(position);

        String subReddit = mCursor.getString(
                mCursor.getColumnIndex(Contract.T1dataEntry.COLUMN_NAME_SUBREDDIT));

        String strId = mCursor.getString(
                mCursor.getColumnIndex(Contract.T1dataEntry.COLUMN_NAME_ID));

        String childrenId = mCursor.getString(
                mCursor.getColumnIndex(Contract.T1dataEntry.COLUMN_NAME_CHILDREN_ID));

        String subRedditId = mCursor.getString(
                mCursor.getColumnIndex(Contract.T1dataEntry.COLUMN_NAME_SUBREDDITID));

        String subRedditName = mCursor.getString(
                mCursor.getColumnIndex(Contract.T1dataEntry.COLUMN_NAME_NAME));

        String ups = mCursor.getString(mCursor.getColumnIndex(
                Contract.T1dataEntry.COLUMN_NAME_UPS));

        String title = mCursor.getString(mCursor.getColumnIndex(
                Contract.T1dataEntry.COLUMN_NAME_TITLE));

        int voteDowns = mCursor.getInt(mCursor.getColumnIndex(
                Contract.T1dataEntry.COLUMN_NAME_DOWNS));

        String approvedAtUtc = mCursor.getString(
                mCursor.getColumnIndex(Contract.T1dataEntry.COLUMN_NAME_APPROVEDATUTC));

        String author = mCursor.getString(
                mCursor.getColumnIndex(Contract.T1dataEntry.COLUMN_NAME_AUTHOR));

        String stickied = mCursor.getString(
                mCursor.getColumnIndex(Contract.T1dataEntry.COLUMN_NAME_STICKIED));

        boolean isSaved = mCursor.getInt(
                mCursor.getColumnIndex(Contract.T1dataEntry.COLUMN_NAME_SAVED)) != 0;

        boolean isArchived = mCursor.getInt(
                mCursor.getColumnIndex(Contract.T1dataEntry.COLUMN_NAME_ARCHIVED)) != 0;

        boolean isNoFollow = mCursor.getInt(
                mCursor.getColumnIndex(Contract.T1dataEntry.COLUMN_NAME_NOFOLLOW)) != 0;

        boolean isSendReplies = mCursor.getInt(
                mCursor.getColumnIndex(Contract.T1dataEntry.COLUMN_NAME_SENDREPLIES)) != 0;

        boolean isCanGild = mCursor.getInt(
                mCursor.getColumnIndex(Contract.T1dataEntry.COLUMN_NAME_CANGILD)) != 0;

        boolean isModNote = mCursor.getInt(
                mCursor.getColumnIndex(Contract.T1dataEntry.COLUMN_NAME_MODNOTE)) != 0;

        boolean isHideScore = mCursor.getInt(
                mCursor.getColumnIndex(Contract.T1dataEntry.COLUMN_NAME_HIDESCORE)) != 0;

        String gilded = mCursor.getString(
                mCursor.getColumnIndex(Contract.T1dataEntry.COLUMN_NAME_GILDED));

        int created = mCursor.getInt(
                mCursor.getColumnIndex(Contract.T1dataEntry.COLUMN_NAME_CREATED));

        String score = mCursor.getString(
                mCursor.getColumnIndex(Contract.T1dataEntry.COLUMN_NAME_SCORE));

        String permanentLink = mCursor.getString(
                mCursor.getColumnIndex(Contract.T1dataEntry.COLUMN_NAME_PERMALINK));

        String subRedditType = mCursor.getString(
                mCursor.getColumnIndex(Contract.T1dataEntry.COLUMN_NAME_SUBREDDITTYPE));

        int createdUtc = mCursor.getInt(
                mCursor.getColumnIndex(Contract.T1dataEntry.COLUMN_NAME_CREATEDUTC));

        String body = mCursor.getString(
                mCursor.getColumnIndex(Contract.T1dataEntry.COLUMN_NAME_BODY));

        String bodyHtml = mCursor.getString(
                mCursor.getColumnIndex(Contract.T1dataEntry.COLUMN_NAME_BODY_HTML));

        String linkId = mCursor.getString(
                mCursor.getColumnIndex(Contract.T1dataEntry.COLUMN_NAME_LINK_ID));

        String parentId = mCursor.getString(
                mCursor.getColumnIndex(Contract.T1dataEntry.COLUMN_NAME_PARENT_ID));

        String url = mCursor.getString(
                mCursor.getColumnIndex(Contract.T1dataEntry.COLUMN_NAME_URL));

        int depth = mCursor.getInt(
                mCursor.getColumnIndex(Contract.T1dataEntry.COLUMN_NAME_DEPTH));


        if (!TextUtils.isEmpty(author)) holder.mTextViewAuthor.setText(author + ":");

        holder.mTextViewPostedOn.setText(DateUtil.getDiffTimeMinute(createdUtc));

        holder.mTextViewPoints.setText(score);

        if (!TextUtils.isEmpty(title)) holder.mTextViewBody.setText(TextUtil.textFromHtml(title));

        if (!TextUtils.isEmpty(body)) holder.mTextViewBody.setText(TextUtil.textFromHtml(body));

        holder.mTextViewBody.setClickable(Preference.isGeneralLinks(mContext));

        if (Preference.isGeneralLinks(mContext)) {
            holder.mTextViewBody.setClickable(true);
            holder.mTextViewBody.setAutoLinkMask(Linkify.ALL);
        } else {
            holder.mTextViewBody.setClickable(false);
        }

        SubRedditDetailHelper subRedditDetailHelper = new SubRedditDetailHelper(mContext);
        subRedditDetailHelper.initDepthIndicator(holder.mDepthIndicator, holder.mCardLinear, depth, true, false);
        String strBackGroundColor = subRedditDetailHelper.initDepthIndicator(holder.mDepthSelect, holder.mSelectorContainer, depth, true, true);

        SelectorHelper selectorHelper = new SelectorHelper(mContext);
        selectorHelper.cardBottomLink(mArrayButton, strBackGroundColor,
                TextUtil.buildCommentDetailLink(permanentLink), subRedditName);

        if (holder.getAdapterPosition() != mSelectorPosition) {
            holder.mSelectorContainer.setVisibility(View.GONE);

        } else {
            holder.mSelectorContainer.setVisibility(View.VISIBLE);
        }


        mListener.adapterPosition(holder.getAdapterPosition(), subReddit);
    }

    @Override
    public int getItemCount() {
        return (mCursor == null) ? 0 : mCursor.getCount();
    }


    public class SubRedditDetailHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        @SuppressWarnings("unused")
        @BindView(R.id.tv_author)
        TextView mTextViewAuthor;

        @SuppressWarnings("unused")
        @BindView(R.id.tv_postedOn)
        TextView mTextViewPostedOn;

        @SuppressWarnings("unused")
        @BindView(R.id.tv_points)
        TextView mTextViewPoints;

        @SuppressWarnings("unused")
        @BindView(R.id.tv_title_detail)
        TextView mTextViewTitleDetail;

        @SuppressWarnings("unused")
        @BindView(R.id.tv_body)
        TextView mTextViewBody;

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

            mTextViewBody.setOnClickListener(view -> {
                clickSelector(getAdapterPosition());
                notifyDataSetChanged();

            });
        }

        void bind() {
        }

        @Override
        public void onClick(View view) {
            clickSelector(getAdapterPosition());
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
    }
}