package info.pelleritoudacity.android.rcapstone.ui.adapter;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import info.pelleritoudacity.android.rcapstone.R;
import info.pelleritoudacity.android.rcapstone.data.Contract;
import info.pelleritoudacity.android.rcapstone.ui.helper.ItemTouchHelperViewHolder;
import info.pelleritoudacity.android.rcapstone.utility.Utility;
import timber.log.Timber;

public class SubRedditAdapter extends RecyclerView.Adapter<SubRedditAdapter.SubRedditHolder> {

    private Cursor mCursor;
    private Context mContext;


    public SubRedditAdapter(Context context) {
        mContext = context;
        // todo add listener ....
        ArrayList<String> arrayList = new ArrayList<>();
    }

    @NonNull
    @Override
    public SubRedditHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        int layoutId = R.layout.list_subreddit;
        LayoutInflater inflater = LayoutInflater.from(mContext);

        View view = inflater.inflate(layoutId, parent, false);

        return new SubRedditHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SubRedditHolder holder, int position) {
        mCursor.moveToPosition(position);

        int idReddit = mCursor.getInt(mCursor.getColumnIndex(Contract.T3dataEntry._ID));
        String nameIdReddit = mCursor.getString(mCursor.getColumnIndex(Contract.T3dataEntry.COLUMN_NAME_ID));

        String subRedditIdText = mCursor.getString(mCursor.getColumnIndex(Contract.T3dataEntry.COLUMN_NAME_SUBREDDIT_ID));
        String subReddit = mCursor.getString(mCursor.getColumnIndex(Contract.T3dataEntry.COLUMN_NAME_SUBREDDIT));
        int subRedditSubscriptions = mCursor.getInt(mCursor.getColumnIndex(Contract.T3dataEntry.COLUMN_NAME_SUBREDDIT_SUBSCRIBERS));


        String title = mCursor.getString(mCursor.getColumnIndex(Contract.T3dataEntry.COLUMN_NAME_TITLE));
        String imagePreviewUrl = mCursor.getString(mCursor.getColumnIndex(Contract.T3dataEntry.COLUMN_NAME_PREVIEW_IMAGE_SOURCE_URL));
        int imagePreviewWidth = mCursor.getInt(mCursor.getColumnIndex(Contract.T3dataEntry.COLUMN_NAME_PREVIEW_IMAGE_SOURCE_WIDTH));
        int imagePreviewHeight = mCursor.getInt(mCursor.getColumnIndex(Contract.T3dataEntry.COLUMN_NAME_PREVIEW_IMAGE_SOURCE_HEIGHT));
        String domain = mCursor.getString(mCursor.getColumnIndex(Contract.T3dataEntry.COLUMN_NAME_DOMAIN));
        Long createdUtc = mCursor.getLong(mCursor.getColumnIndex(Contract.T3dataEntry.COLUMN_NAME_CREATED_UTC));
        int score = mCursor.getInt(mCursor.getColumnIndex(Contract.T3dataEntry.COLUMN_NAME_SCORE));
        int numComments = mCursor.getInt(mCursor.getColumnIndex(Contract.T3dataEntry.COLUMN_NAME_NUM_COMMENTS));

        int hourCurrentUtc = Utility.getHourCurrentCreatedUtc(createdUtc);
        String strDiffCurrentUtc;

        if(hourCurrentUtc<24){
            if(hourCurrentUtc<1) hourCurrentUtc = 1;
            strDiffCurrentUtc = String.valueOf(hourCurrentUtc)+ " " + mContext.getString(R.string.abbr_hour_text);
        }else {
            strDiffCurrentUtc = String.valueOf(Math.round(hourCurrentUtc/24))+ " " + mContext.getString(R.string.abbr_day_text);
        }

        holder.mTextViewCreatedUtc.setText(strDiffCurrentUtc);

        if(!TextUtils.isEmpty(imagePreviewUrl)){

            Glide.with(holder.itemView.getContext().getApplicationContext())
                    .asBitmap()
                    .load(Utility.textFromHtml(imagePreviewUrl) )
                    .into(new SimpleTarget<Bitmap>(imagePreviewWidth,imagePreviewHeight) {
                        @Override
                        public void onLoadFailed(@Nullable Drawable errorDrawable) {
                            super.onLoadFailed(errorDrawable);
                            holder.mImageViewSubReddit.setImageResource(R.drawable.logo);
                        }

                        @Override
                        public void onLoadStarted(@Nullable Drawable placeholder) {
                            super.onLoadStarted(placeholder);
                            holder.mImageViewSubReddit.setImageResource(R.drawable.logo);
                        }

                        @Override
                        public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                            holder.mImageViewSubReddit.setImageBitmap(resource);
                        }
                    });

            holder.mImageViewSubReddit.setVisibility(View.VISIBLE);

        }else {
            holder.mImageViewSubReddit.setVisibility(View.GONE);
        }


















        holder.mTextViewTitle.setText(title);
        holder.mTextViewSubReddit.setText(subReddit);
        holder.mTextViewDomain.setText(domain.replaceAll("\\..*$", ""));
        holder.mTextViewScore.setText(Utility.numberFormat(score));
        holder.mTextViewNumComments.setText(String.format("%s %s", String.valueOf(numComments), mContext.getString(R.string.text_comments_subreddit)));

        holder.bind(holder.getAdapterPosition());
    }

    @Override
    public int getItemCount() {
        return (mCursor == null) ? 0 : mCursor.getCount();
    }

    public class SubRedditHolder extends RecyclerView.ViewHolder
            implements ItemTouchHelperViewHolder {

        @SuppressWarnings("unused")
        @BindView(R.id.tv_title)
        TextView mTextViewTitle;

        @SuppressWarnings("unused")
        @BindView(R.id.tv_subreddit)
        TextView mTextViewSubReddit;

        @SuppressWarnings("unused")
        @BindView(R.id.img_subreddit)
        ImageView mImageViewSubReddit;

        @SuppressWarnings("unused")
        @BindView(R.id.tv_domain)
        TextView mTextViewDomain;

        @SuppressWarnings("unused")
        @BindView(R.id.tv_created_utc)
        TextView mTextViewCreatedUtc;

        @SuppressWarnings("unused")
        @BindView(R.id.tv_score)
        TextView mTextViewScore;

        @SuppressWarnings("unused")
        @BindView(R.id.tv_num_comments)
        TextView mTextViewNumComments;

        private int mPosition;

        SubRedditHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);

        }

        public void bind(int position){
            mPosition = position;
        }


        @Override
        public void onItemSelected() {
            itemView.setBackgroundColor(Utility.getColor(mContext, R.color.colorBackgroundItemSelected));
        }

        @Override
        public void onItemClear() {
            itemView.setBackgroundColor(Utility.getColor(mContext, R.color.colorBackgroundItemNoSelected));
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
            this.notifyDataSetChanged();
        }
        return temp;
    }


}
