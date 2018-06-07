/*
 *
 * ______                _____ _
 * | ___ \              /  ___| |
 * | |_/ /___ __ _ _ __ \ `--.| |_ ___  _ __   ___
 * |    // __/ _` | '_ \ `--. \ __/ _ \| '_ \ / _ \
 * | |\ \ (_| (_| | |_) /\__/ / || (_) | | | |  __/
 * \_| \_\___\__,_| .__/\____/ \__\___/|_| |_|\___|
 *                | |
 *                |_|
 *
 * Copyright (C) 2018 Benedetto Pellerito
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package info.pelleritoudacity.android.rcapstone.ui.adapter;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.BitmapResource;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.android.exoplayer2.ext.ima.ImaAdsLoader;
import com.google.android.exoplayer2.ui.PlayerView;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.material_design_iconic_typeface_library.MaterialDesignIconic;

import butterknife.BindView;
import butterknife.ButterKnife;
import info.pelleritoudacity.android.rcapstone.R;
import info.pelleritoudacity.android.rcapstone.data.Contract;
import info.pelleritoudacity.android.rcapstone.media.MediaPlayer;
import info.pelleritoudacity.android.rcapstone.ui.fragment.SubRedditFragment;
import info.pelleritoudacity.android.rcapstone.ui.helper.ItemTouchHelperViewHolder;
import info.pelleritoudacity.android.rcapstone.utility.ImageUtils;
import info.pelleritoudacity.android.rcapstone.utility.TextUtil;
import timber.log.Timber;

import static info.pelleritoudacity.android.rcapstone.utility.DateUtils.getHourCurrentCreatedUtc;
import static info.pelleritoudacity.android.rcapstone.utility.ImageUtils.isSmallImage;
import static info.pelleritoudacity.android.rcapstone.utility.NumberUtils.numberFormat;

public class SubRedditAdapter extends RecyclerView.Adapter<SubRedditAdapter.SubRedditHolder> {

    private Cursor mCursor;
    private Context mContext;

    private MediaPlayer mMediaPlayer;
    private final ImaAdsLoader mImaAdsLoader;
    private SubRedditFragment mListener;


    public SubRedditAdapter(SubRedditFragment listener, Context context, ImaAdsLoader imaAdsLoader) {
        mContext = context;
        mImaAdsLoader = imaAdsLoader;
        mListener = listener;
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

    @Deprecated
    @Override
    public void onBindViewHolder(@NonNull SubRedditHolder holder, int position) {
        mCursor.moveToPosition(position);

        int idReddit = mCursor.getInt(
                mCursor.getColumnIndex(Contract.T3dataEntry._ID));

        String nameIdReddit = mCursor.getString(
                mCursor.getColumnIndex(Contract.T3dataEntry.COLUMN_NAME_ID));

        String subRedditIdText = mCursor.getString(
                mCursor.getColumnIndex(Contract.T3dataEntry.COLUMN_NAME_SUBREDDIT_ID));

        String subReddit = mCursor.getString(
                mCursor.getColumnIndex(Contract.T3dataEntry.COLUMN_NAME_SUBREDDIT));

        String subRedditNamePrefix = mCursor.getString(
                mCursor.getColumnIndex(Contract.T3dataEntry.COLUMN_NAME_SUBREDDIT_NAME_PREFIXE));

        int subRedditSubscriptions = mCursor.getInt(
                mCursor.getColumnIndex(Contract.T3dataEntry.COLUMN_NAME_SUBREDDIT_SUBSCRIBERS));

        @SuppressWarnings("deprecation")
        String title = TextUtil.textFromHtml(
                mCursor.getString(mCursor.getColumnIndex(Contract.T3dataEntry.COLUMN_NAME_TITLE)));

        String domain = mCursor.getString(
                mCursor.getColumnIndex(Contract.T3dataEntry.COLUMN_NAME_DOMAIN));

        Long createdUtc = mCursor.getLong(
                mCursor.getColumnIndex(Contract.T3dataEntry.COLUMN_NAME_CREATED_UTC));

        int score = mCursor.getInt(
                mCursor.getColumnIndex(Contract.T3dataEntry.COLUMN_NAME_SCORE));

        int numComments = mCursor.getInt(
                mCursor.getColumnIndex(Contract.T3dataEntry.COLUMN_NAME_NUM_COMMENTS));

        @SuppressWarnings("deprecation")
        String imagePreviewUrl = TextUtil.textFromHtml(
                mCursor.getString(mCursor.getColumnIndex(Contract.T3dataEntry.COLUMN_NAME_PREVIEW_IMAGE_SOURCE_URL)));

        int imagePreviewWidth = mCursor.getInt(
                mCursor.getColumnIndex(Contract.T3dataEntry.COLUMN_NAME_PREVIEW_IMAGE_SOURCE_WIDTH));

        int imagePreviewHeight = mCursor.getInt(
                mCursor.getColumnIndex(Contract.T3dataEntry.COLUMN_NAME_PREVIEW_IMAGE_SOURCE_HEIGHT));

        @SuppressWarnings("deprecation")
        String videoPreviewUrl = TextUtil.textFromHtml(
                mCursor.getString(mCursor.getColumnIndex(Contract.T3dataEntry.COLUMN_NAME_VARIANT_VIDEO_MP4_URL)));

        int videoPreviewWidth = mCursor.getInt(
                mCursor.getColumnIndex(Contract.T3dataEntry.COLUMN_NAME_VARIANT_VIDEO_MP4_WIDTH));

        int videoPreviewHeight = mCursor.getInt(
                mCursor.getColumnIndex(Contract.T3dataEntry.COLUMN_NAME_VARIANT_VIDEO_MP4_HEIGHT));

        String strDiffCurrentUtc;
        int hourCurrentUtc = getHourCurrentCreatedUtc(createdUtc);

        if (hourCurrentUtc < 24) {

            if (hourCurrentUtc < 1) {
                hourCurrentUtc = 1;
            }

            strDiffCurrentUtc = String.valueOf(hourCurrentUtc) + " " + mContext.getString(R.string.abbr_hour_text);

        } else {
            strDiffCurrentUtc = String.valueOf(Math.round(hourCurrentUtc / 24)) + " " + mContext.getString(R.string.abbr_day_text);

        }

        holder.mTextViewCreatedUtc.setText(strDiffCurrentUtc);

        if (!TextUtils.isEmpty(videoPreviewUrl)) {
            loadVideoFirstFrame(holder, videoPreviewUrl, videoPreviewWidth, videoPreviewHeight, title);
            holder.mImageViewSubReddit.setVisibility(View.GONE);

        } else {
            holder.mPlayerLayout.setVisibility(View.GONE);

            if (!TextUtils.isEmpty(imagePreviewUrl)){

                if (isSmallImage(mContext,imagePreviewWidth,imagePreviewHeight)){
                    holder.mImageViewSubRedditSmall.setVisibility(View.VISIBLE);

                }else {
                    holder.mImageViewSubReddit.setVisibility(View.VISIBLE);

                }

            }

        }

        if (!TextUtils.isEmpty(imagePreviewUrl)) {
            imageReddit(holder, imagePreviewUrl, imagePreviewWidth, imagePreviewHeight, title);
        }

        holder.mTextViewTitle.setText(title);

        holder.mTextViewSubRedditNamePrefix.setText(subRedditNamePrefix);

        holder.mTextViewDomain.setText(domain);

        holder.mTextViewScore.setText(numberFormat(score));

        holder.mTextViewNumComments.setText(
                String.format("%s %s", String.valueOf(numComments),
                        mContext.getString(R.string.text_comments_subreddit))
        );

        cardBottomLink(holder);

        holder.bind(holder.getAdapterPosition());

        mListener.adapterPosition(holder.getAdapterPosition(), subReddit);
    }

    private void loadVideoFirstFrame(SubRedditHolder holder, String videoPreviewUrl, int videoPreviewWidth, int videoPreviewHeight, String contentDescription) {

        Glide.with(holder.itemView.getContext().getApplicationContext())
                .asBitmap()
                .load(videoPreviewUrl)
                .into(new SimpleTarget<Bitmap>(videoPreviewWidth, videoPreviewHeight) {

                    @Override
                    public void onLoadFailed(@Nullable Drawable errorDrawable) {
                        super.onLoadFailed(errorDrawable);
                        holder.mPlayerLayout.setBackgroundResource(R.drawable.logo);
                    }

                    @Override
                    public void onLoadStarted(@Nullable Drawable placeholder) {
                        super.onLoadStarted(placeholder);
                        holder.mPlayerLayout.setBackgroundResource(R.drawable.logo);
                    }

                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, Transition<? super Bitmap> transition) {
                        holder.mPlayerLayout.setBackground(new BitmapDrawable(mContext.getResources(), resource));
                        holder.mPlayerLayout.setVisibility(View.VISIBLE);

                        holder.mImagePlay.setImageDrawable(new IconicsDrawable(mContext, MaterialDesignIconic.Icon.gmi_play_circle)
                                .color(Color.WHITE)
                                .sizeDp(72)
                                .respectFontBounds(true));

                        holder.mImagePlay.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                holder.mExoProgressBar.setVisibility(View.VISIBLE);
                                createVideo(holder, videoPreviewUrl, contentDescription);
                            }
                        });

                    }

                });

    }


    private void cardBottomLink(SubRedditHolder holder) {

        holder.mImageButtonVoteUp.setImageDrawable(new IconicsDrawable(mContext, MaterialDesignIconic.Icon.gmi_thumb_up)
                .color(Color.GRAY)
                .sizeDp(mContext.getResources().getInteger(R.integer.icon_card_bottom))
                .respectFontBounds(true));

        holder.mImageButtonVoteUp.setBackgroundColor(Color.WHITE);

        holder.mImageButtonVoteDown.setImageDrawable(new IconicsDrawable(mContext, MaterialDesignIconic.Icon.gmi_thumb_down)
                .color(Color.GRAY)
                .sizeDp(mContext.getResources().getInteger(R.integer.icon_card_bottom))
                .respectFontBounds(true));

        holder.mImageButtonVoteDown.setBackgroundColor(Color.WHITE);

        holder.mImageButtonPreferStars.setImageDrawable(new IconicsDrawable(mContext, MaterialDesignIconic.Icon.gmi_star_border)
                .color(Color.GRAY)
                .sizeDp(mContext.getResources().getInteger(R.integer.icon_card_bottom))
                .respectFontBounds(true));

        holder.mImageButtonPreferStars.setBackgroundColor(Color.WHITE);

        holder.mImageButtonShowComments.setImageDrawable(new IconicsDrawable(mContext, MaterialDesignIconic.Icon.gmi_comment_outline)
                .color(Color.GRAY)
                .sizeDp(mContext.getResources().getInteger(R.integer.icon_card_bottom))
                .respectFontBounds(true));

        holder.mImageButtonShowComments.setBackgroundColor(Color.WHITE);

        holder.mImageButtonShareSubReddit.setImageDrawable(new IconicsDrawable(mContext, MaterialDesignIconic.Icon.gmi_share)
                .color(Color.GRAY)
                .sizeDp(mContext.getResources().getInteger(R.integer.icon_card_bottom))
                .respectFontBounds(true));

        holder.mImageButtonShareSubReddit.setBackgroundColor(Color.WHITE);

    }

    private void createVideo(SubRedditHolder holder, String videoPreviewUrl, String title) {
        if (mMediaPlayer != null) {
            mMediaPlayer.releasePlayer();
        }
        mMediaPlayer = new MediaPlayer(mContext,
                mImaAdsLoader,
                holder.mPlayerView,
                holder.mExoProgressBar,
                title, holder.mTVErrorPlayer, holder.mImagePlay);

        mMediaPlayer.initPlayer(Uri.parse(videoPreviewUrl));

        holder.mPlayerLayout.setVisibility(View.VISIBLE);

    }

    private void imageReddit(SubRedditHolder holder, String imagePreviewUrl, int imagePreviewWidth, int imagePreviewHeight, String contentDescription) {

        Glide.with(holder.itemView.getContext().getApplicationContext())
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
                        if (isSmallImage(mContext, imagePreviewWidth, imagePreviewHeight)) {
                            ImageUtils.createRoundImage(mContext, holder.mImageViewSubRedditSmall, resource);
                            holder.mImageViewSubRedditSmall.setContentDescription(contentDescription);

                        } else {
                            holder.mImageViewSubReddit.setImageBitmap(resource);
                            holder.mImageViewSubReddit.setContentDescription(contentDescription);

                        }

                    }


                });

    }

    @Override
    public int getItemCount() {
        return (mCursor == null) ? 0 : mCursor.getCount();
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull SubRedditHolder holder) {
        super.onViewDetachedFromWindow(holder);
        if (mMediaPlayer != null) {
            mMediaPlayer.releasePlayer();
        }

    }


    public class SubRedditHolder extends RecyclerView.ViewHolder
            implements ItemTouchHelperViewHolder {

        @SuppressWarnings("unused")
        @BindView(R.id.tv_title)
        TextView mTextViewTitle;

        @SuppressWarnings("unused")
        @BindView(R.id.img_subreddit)
        ImageView mImageViewSubReddit;

        @SuppressWarnings("unused")
        @BindView(R.id.img_subreddit_small)
        ImageView mImageViewSubRedditSmall;

        @SuppressWarnings("unused")
        @BindView(R.id.tv_subreddit_name_prefix)
        TextView mTextViewSubRedditNamePrefix;

        @SuppressWarnings({"WeakerAccess", "CanBeFinal", "unused"})
        @BindView(R.id.id_player_layout)
        FrameLayout mPlayerLayout;

        @SuppressWarnings({"WeakerAccess", "CanBeFinal", "unused"})
        @BindView(R.id.image_subreddit_play)
        ImageView mImagePlay;

        @SuppressWarnings({"WeakerAccess", "CanBeFinal", "unused"})
        @BindView(R.id.pv_subreddit)
        PlayerView mPlayerView;

        @SuppressWarnings({"WeakerAccess", "CanBeFinal", "unused"})
        @BindView(R.id.exo_progressBar)
        ProgressBar mExoProgressBar;

        @SuppressWarnings({"WeakerAccess", "CanBeFinal", "unused"})
        @BindView(R.id.tv_error_player)
        TextView mTVErrorPlayer;

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
        @BindView(R.id.image_share_subreddit)
        ImageButton mImageButtonShareSubReddit;

        private int mPosition;

        SubRedditHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }

        public void bind(int position) {
            mPosition = position;
        }

        @Override
        public void onItemSelected() {
            itemView.setBackgroundColor(ImageUtils.getColor(mContext, R.color.colorBackgroundItemSelected));
        }

        @Override
        public void onItemClear() {
            itemView.setBackgroundColor(ImageUtils.getColor(mContext, R.color.colorBackgroundItemNoSelected));
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

    public interface OnPlayerListener {
        void exoPlayer(MediaPlayer mediaPlayer);
    }

    public interface OnAdapterListener {
        void adapterPosition(int position, String category);
    }
}
