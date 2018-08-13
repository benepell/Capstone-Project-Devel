package info.pelleritoudacity.android.rcapstone.ui.adapter;


import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import info.pelleritoudacity.android.rcapstone.R;
import info.pelleritoudacity.android.rcapstone.data.db.Contract;
import info.pelleritoudacity.android.rcapstone.data.db.Record.TitleDetailRecord;
import info.pelleritoudacity.android.rcapstone.data.db.util.DataUtils;
import info.pelleritoudacity.android.rcapstone.data.model.record.RecordAdapterTitle;
import info.pelleritoudacity.android.rcapstone.data.model.ui.CardBottomModel;
import info.pelleritoudacity.android.rcapstone.ui.helper.SelectorHelper;
import info.pelleritoudacity.android.rcapstone.ui.helper.TitleDetailHelper;
import info.pelleritoudacity.android.rcapstone.utility.DateUtil;
import info.pelleritoudacity.android.rcapstone.utility.ImageUtil;
import info.pelleritoudacity.android.rcapstone.utility.NetworkUtil;
import info.pelleritoudacity.android.rcapstone.utility.PermissionUtil;
import info.pelleritoudacity.android.rcapstone.utility.Preference;
import info.pelleritoudacity.android.rcapstone.utility.TextUtil;
import info.pelleritoudacity.android.rcapstone.utility.Utility;

import static info.pelleritoudacity.android.rcapstone.utility.NumberUtil.numberFormat;

public class TitleDetailAdapter extends RecyclerView.Adapter<TitleDetailAdapter.SubRedditSelectedHolder> implements SelectorHelper.OnSelector {

    private final Context mContext;
    private Cursor mCursor;
    private final OnVoteChange mListener;

    public TitleDetailAdapter(OnVoteChange listener, Context context) {
        mListener = listener;
        mContext = context;
    }

    @NonNull
    @Override
    public SubRedditSelectedHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int layoutId = R.layout.list_detail_title;
        LayoutInflater inflater = LayoutInflater.from(mContext);

        View view = inflater.inflate(layoutId, parent, false);

        return new SubRedditSelectedHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SubRedditSelectedHolder holder, int position) {

        mCursor.moveToPosition(position);

        TitleDetailRecord recordList = new TitleDetailRecord(mCursor);

        RecordAdapterTitle record = null;
        if (recordList.getRecordList() != null) {
            record = recordList.getRecordList().get(0);
        }

        if (record != null) {


            holder.mTextViewTitle.setText(record.getTitle());
            holder.mTextViewTitle.setClickable(Preference.isGeneralLinks(mContext));

            holder.mTextViewCreatedUtc.setText(DateUtil.getDiffTimeMinute(mContext, record.getCreatedUtc()));

            holder.mTextViewSubRedditNamePrefix.setText(record.getSubRedditNamePrefix());

            holder.mTextViewDomain.setText(record.getDomain());

            holder.mTextViewScore.setText(numberFormat(record.getScore()));

            holder.mTextViewNumComments.setText(
                    String.format("%s %s", String.valueOf(record.getNumComments()),
                            mContext.getString(R.string.text_comments_subreddit))
            );

            String strBackGroundColor = ImageUtil.getStringFromColor(mContext, R.color.colorBackground);
            if (Preference.isNightMode(mContext)) {
                strBackGroundColor = ImageUtil.getStringFromColor(mContext, R.color.colorBackgroundDark);
            }

            ImageButton[] arrayButton = new ImageButton[]{holder.mImageButtonVoteUp, holder.mImageButtonVoteDown,
                    holder.mImageButtonPreferStars, holder.mImageButtonShowComments, holder.mImageButtonOpenBrowser};

            if (!Utility.isTablet(mContext)) {
                SelectorHelper selectorHelper = new SelectorHelper(this, mContext,holder.itemView);

                CardBottomModel cardBottomModel = new CardBottomModel();
                cardBottomModel.setPosition(holder.getAdapterPosition());
                cardBottomModel.setScore(record.getScore());
                cardBottomModel.setArrayButton(arrayButton);
                cardBottomModel.setBackgroundColor(strBackGroundColor);
                cardBottomModel.setLinkComment(TextUtil.buildCommentDetailLink(record.getPermanentLink()));
                cardBottomModel.setCategory(record.getSubRedditName());
                cardBottomModel.setSaved(record.isSaved());
                cardBottomModel.setLogged(PermissionUtil.isLogged(mContext));
                cardBottomModel.setOnline(NetworkUtil.isOnline(mContext));

                selectorHelper.cardBottomLink(cardBottomModel);

            } else {
                for (ImageButton view : arrayButton) {
                    view.setVisibility(View.GONE);
                }
            }

            TitleDetailHelper helper = new TitleDetailHelper(mContext);

            if ((Preference.isGeneralImages(mContext)) &&
                    (!TextUtils.isEmpty(record.getImagePreviewUrl()))) {

                helper.imageReddit(holder.mImageViewSubRedditSmall, record.getImagePreviewUrl(),
                        record.getTitle(), record.getImagePreviewWidth(), record.getImagePreviewHeight());

            }

        }
    }

    @Override
    public int getItemCount() {
        return (mCursor == null) ? 0 : mCursor.getCount();
    }

    @Override
    public void vote(int position, int score, boolean voteUp, int dir, String linkId) {
        new DataUtils(mContext).updateVote(Contract.T3dataEntry.CONTENT_URI, score, voteUp, dir, linkId);
        mListener.selectorChange(position);
    }

    @Override
    public void stars(int position) {
        mListener.selectorChange(position);
    }

    public class SubRedditSelectedHolder extends RecyclerView.ViewHolder {

        @SuppressWarnings("unused")
        @BindView(R.id.tv_title_selected)
        TextView mTextViewTitle;

        @SuppressWarnings("unused")
        @BindView(R.id.img_subreddit_small_selected)
        ImageView mImageViewSubRedditSmall;

        @SuppressWarnings("unused")
        @BindView(R.id.tv_subreddit_name_prefix_selected)
        TextView mTextViewSubRedditNamePrefix;

        @SuppressWarnings("unused")
        @BindView(R.id.tv_domain_selected)
        TextView mTextViewDomain;

        @SuppressWarnings("unused")
        @BindView(R.id.tv_created_utc_selected)
        TextView mTextViewCreatedUtc;

        @SuppressWarnings("unused")
        @BindView(R.id.tv_score_selected)
        TextView mTextViewScore;

        @SuppressWarnings("unused")
        @BindView(R.id.tv_num_comments_selected)
        TextView mTextViewNumComments;

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

        SubRedditSelectedHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

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

    public interface OnVoteChange {
        void selectorChange(int position);
    }
}