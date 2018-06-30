package info.pelleritoudacity.android.rcapstone.ui.adapter;


import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.android.exoplayer2.ui.PlayerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import info.pelleritoudacity.android.rcapstone.R;
import info.pelleritoudacity.android.rcapstone.data.Contract;
import info.pelleritoudacity.android.rcapstone.ui.fragment.SubRedditSelectedFragment;
import info.pelleritoudacity.android.rcapstone.ui.helper.SelectorHelper;
import info.pelleritoudacity.android.rcapstone.utility.DateUtil;
import info.pelleritoudacity.android.rcapstone.utility.ImageUtil;
import info.pelleritoudacity.android.rcapstone.utility.TextUtil;

import static info.pelleritoudacity.android.rcapstone.utility.ImageUtil.isSmallImage;
import static info.pelleritoudacity.android.rcapstone.utility.NumberUtil.numberFormat;

public class SubRedditSelectedAdapter extends RecyclerView.Adapter<SubRedditSelectedAdapter.SubRedditSelectedHolder> {

    private final Context mContext;
    private final SubRedditSelectedFragment mListener;
    private Cursor mCursor;
    private ImageButton[] mArrayButton;
    private static int mSelectorPosition = RecyclerView.NO_POSITION;


    public SubRedditSelectedAdapter(SubRedditSelectedFragment listener) {
        mListener = listener;
        mContext = listener.getActivity();
    }

    @NonNull
    @Override
    public SubRedditSelectedHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int layoutId = R.layout.list_subreddit_selected;
        LayoutInflater inflater = LayoutInflater.from(mContext);

        View view = inflater.inflate(layoutId, parent, false);

        return new SubRedditSelectedHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull SubRedditSelectedHolder holder, int position) {

        mArrayButton = new ImageButton[]{holder.mImageButtonVoteUp, holder.mImageButtonVoteDown,
                holder.mImageButtonPreferStars, holder.mImageButtonShowComments, holder.mImageButtonOpenBrowser};
        mCursor.moveToPosition(position);

        String subReddit = mCursor.getString(
                mCursor.getColumnIndex(Contract.T3dataEntry.COLUMN_NAME_SUBREDDIT));

        String subRedditName = mCursor.getString(
                mCursor.getColumnIndex(Contract.T3dataEntry.COLUMN_NAME_NAME));

        String title = mCursor.getString(mCursor.getColumnIndex(
                Contract.T3dataEntry.COLUMN_NAME_TITLE));

        String author = mCursor.getString(
                mCursor.getColumnIndex(Contract.T3dataEntry.COLUMN_NAME_AUTHOR));
        int created = mCursor.getInt(
                mCursor.getColumnIndex(Contract.T3dataEntry.COLUMN_NAME_CREATED));

        String permanentLink = mCursor.getString(
                mCursor.getColumnIndex(Contract.T3dataEntry.COLUMN_NAME_PERMALINK));

        String subRedditNamePrefix = mCursor.getString(
                mCursor.getColumnIndex(Contract.T3dataEntry.COLUMN_NAME_SUBREDDIT_NAME_PREFIXE));

        String domain = mCursor.getString(
                mCursor.getColumnIndex(Contract.T3dataEntry.COLUMN_NAME_DOMAIN));

        Long createdUtc = mCursor.getLong(
                mCursor.getColumnIndex(Contract.T3dataEntry.COLUMN_NAME_CREATED_UTC));

        int score = mCursor.getInt(
                mCursor.getColumnIndex(Contract.T3dataEntry.COLUMN_NAME_SCORE));

        int numComments = mCursor.getInt(
                mCursor.getColumnIndex(Contract.T3dataEntry.COLUMN_NAME_NUM_COMMENTS));

        String imagePreviewUrl = TextUtil.textFromHtml(
                mCursor.getString(mCursor.getColumnIndex(Contract.T3dataEntry.COLUMN_NAME_PREVIEW_IMAGE_SOURCE_URL)));

        int imagePreviewWidth = mCursor.getInt(
                mCursor.getColumnIndex(Contract.T3dataEntry.COLUMN_NAME_PREVIEW_IMAGE_SOURCE_WIDTH));

        int imagePreviewHeight = mCursor.getInt(
                mCursor.getColumnIndex(Contract.T3dataEntry.COLUMN_NAME_PREVIEW_IMAGE_SOURCE_HEIGHT));


        holder.mTextViewTitle.setText(title);
        holder.mTextViewCreatedUtc.setText(DateUtil.getDiffTimeMinute(createdUtc));

        holder.mTextViewSubRedditNamePrefix.setText(subRedditNamePrefix);

        holder.mTextViewDomain.setText(domain);

        holder.mTextViewScore.setText(numberFormat(score));

        holder.mTextViewNumComments.setText(
                String.format("%s %s", String.valueOf(numComments),
                        mContext.getString(R.string.text_comments_subreddit))
        );


        String strBackGroundColor = "#FFFFFF";
        SelectorHelper selectorHelper = new SelectorHelper(mContext);
        selectorHelper.cardBottomLink(mArrayButton, strBackGroundColor,
                TextUtil.buildCommentDetailLink(permanentLink), subRedditName);


        Glide.with(mContext)
                .asBitmap()
                .load(imagePreviewUrl)
                .into(new SimpleTarget<Bitmap>(imagePreviewWidth, imagePreviewHeight) {

                    @Override
                    public void onLoadFailed(@Nullable Drawable errorDrawable) {
                        super.onLoadFailed(errorDrawable);
                        holder.mImageViewSubRedditSmall.setImageResource(R.drawable.logo);


                    }

                    @Override
                    public void onLoadStarted(@Nullable Drawable placeholder) {
                        super.onLoadStarted(placeholder);
                       holder.mImageViewSubRedditSmall.setImageResource(R.drawable.logo);

                    }

                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, Transition<? super Bitmap> transition) {
                            ImageUtil.createRoundImage(mContext, holder.mImageViewSubRedditSmall, resource);
                            holder.mImageViewSubRedditSmall.setContentDescription(title);
                            holder.mImageViewSubRedditSmall.setVisibility(View.VISIBLE);


                    }
                });


    }

    @Override
    public int getItemCount() {
        return (mCursor == null) ? 0 : mCursor.getCount();
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

        void bind() {
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


}