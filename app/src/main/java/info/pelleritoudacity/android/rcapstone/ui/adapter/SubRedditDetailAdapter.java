package info.pelleritoudacity.android.rcapstone.ui.adapter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import info.pelleritoudacity.android.rcapstone.R;
import info.pelleritoudacity.android.rcapstone.data.Contract;
import info.pelleritoudacity.android.rcapstone.ui.fragment.SubRedditDetailFragment;
import info.pelleritoudacity.android.rcapstone.ui.helper.ItemTouchHelperViewHolder;
import info.pelleritoudacity.android.rcapstone.utility.Costants;
import info.pelleritoudacity.android.rcapstone.utility.DateUtils;
import info.pelleritoudacity.android.rcapstone.utility.TextUtil;
import timber.log.Timber;

public class SubRedditDetailAdapter extends RecyclerView.Adapter<SubRedditDetailAdapter.SubRedditDetailHolder> {

    private final Context mContext;
    private final SubRedditDetailFragment mListener;
    private Cursor mCursor;


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
        holder.mTextViewPostedOn.setText(String.valueOf(DateUtils.getHourCurrentCreatedUtc(createdUtc)));
        holder.mTextViewPoints.setText(score);
        if (!TextUtils.isEmpty(body)) holder.mTextViewBody.setText(TextUtil.textFromHtml(body));

        holder.mCardLinear.setPadding(Costants.LEVEL_DEPTH_PADDING * depth, 0, 0, 0);

        switch (depth) {
            case 0:
                holder.mDepthIndicator.setBackgroundColor(Color.parseColor(Costants.COLOR_INDICATOR_0));
                break;
            case 1:
                holder.mDepthIndicator.setBackgroundColor(Color.parseColor(Costants.COLOR_INDICATOR_1));
                break;
            case 2:
                holder.mDepthIndicator.setBackgroundColor(Color.parseColor(Costants.COLOR_INDICATOR_2));
                break;
            case 3:
                holder.mDepthIndicator.setBackgroundColor(Color.parseColor(Costants.COLOR_INDICATOR_3));
                break;
            default:
                holder.mDepthIndicator.setBackgroundColor(Color.parseColor(Costants.COLOR_INDICATOR_4));
                break;

        }


        holder.bind(holder.getAdapterPosition());

        mListener.adapterPosition(holder.getAdapterPosition(), subReddit);
    }

    @Override
    public int getItemCount() {
        return (mCursor == null) ? 0 : mCursor.getCount();
    }


    public class SubRedditDetailHolder extends RecyclerView.ViewHolder
            implements ItemTouchHelperViewHolder {

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
        @BindView(R.id.tv_body)
        TextView mTextViewBody;

        @SuppressWarnings("unused")
        @BindView(R.id.card_linear)
        LinearLayout mCardLinear;

        @SuppressWarnings("unused")
        @BindView(R.id.view_depth)
        View mDepthIndicator;

        private int mPosition;

        SubRedditDetailHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void onItemSelected() {

        }

        @Override
        public void onItemClear() {

        }

        void bind(int position) {
            mPosition = position;
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