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
import android.content.Intent;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.exoplayer2.ext.ima.ImaAdsLoader;
import com.google.android.exoplayer2.ui.PlayerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import info.pelleritoudacity.android.rcapstone.R;
import info.pelleritoudacity.android.rcapstone.data.Contract;
import info.pelleritoudacity.android.rcapstone.media.MediaPlayer;
import info.pelleritoudacity.android.rcapstone.ui.activity.SubRedditDetailActivity;
import info.pelleritoudacity.android.rcapstone.ui.fragment.SubRedditFragment;
import info.pelleritoudacity.android.rcapstone.ui.helper.ItemTouchHelperViewHolder;
import info.pelleritoudacity.android.rcapstone.ui.helper.SelectorHelper;
import info.pelleritoudacity.android.rcapstone.ui.helper.SubRedditHelper;
import info.pelleritoudacity.android.rcapstone.utility.Costants;
import info.pelleritoudacity.android.rcapstone.utility.ImageUtils;
import info.pelleritoudacity.android.rcapstone.utility.Preference;
import info.pelleritoudacity.android.rcapstone.utility.TextUtil;

import static info.pelleritoudacity.android.rcapstone.utility.DateUtils.getStringCurrentCreatedUtd;
import static info.pelleritoudacity.android.rcapstone.utility.ImageUtils.isSmallImage;
import static info.pelleritoudacity.android.rcapstone.utility.NumberUtils.numberFormat;

public class SubRedditAdapter extends RecyclerView.Adapter<SubRedditAdapter.SubRedditHolder> {

    private Cursor mCursor;
    private Context mContext;

    private MediaPlayer mMediaPlayer;
    private final ImaAdsLoader mImaAdsLoader;
    private final SubRedditFragment mListener;
    private ImageButton[] mArrayButton;

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

    @Override
    public void onBindViewHolder(@NonNull SubRedditHolder holder, int position) {

        mArrayButton = new ImageButton[]{holder.mImageButtonVoteUp, holder.mImageButtonVoteDown,
                holder.mImageButtonPreferStars, holder.mImageButtonShowComments, holder.mImageButtonOpenBrowser};

        mCursor.moveToPosition(position);

        int idReddit = mCursor.getInt(
                mCursor.getColumnIndex(Contract.T3dataEntry._ID));

        String nameIdReddit = mCursor.getString(
                mCursor.getColumnIndex(Contract.T3dataEntry.COLUMN_NAME_ID));

        String nameReddit = mCursor.getString(
                mCursor.getColumnIndex(Contract.T3dataEntry.COLUMN_NAME_NAME));

        String subRedditIdText = mCursor.getString(
                mCursor.getColumnIndex(Contract.T3dataEntry.COLUMN_NAME_SUBREDDIT_ID));

        String subReddit = mCursor.getString(
                mCursor.getColumnIndex(Contract.T3dataEntry.COLUMN_NAME_SUBREDDIT));

        String subRedditNamePrefix = mCursor.getString(
                mCursor.getColumnIndex(Contract.T3dataEntry.COLUMN_NAME_SUBREDDIT_NAME_PREFIXE));

        int subRedditSubscriptions = mCursor.getInt(
                mCursor.getColumnIndex(Contract.T3dataEntry.COLUMN_NAME_SUBREDDIT_SUBSCRIBERS));

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

        String imagePreviewUrl = TextUtil.textFromHtml(
                mCursor.getString(mCursor.getColumnIndex(Contract.T3dataEntry.COLUMN_NAME_PREVIEW_IMAGE_SOURCE_URL)));

        int imagePreviewWidth = mCursor.getInt(
                mCursor.getColumnIndex(Contract.T3dataEntry.COLUMN_NAME_PREVIEW_IMAGE_SOURCE_WIDTH));

        int imagePreviewHeight = mCursor.getInt(
                mCursor.getColumnIndex(Contract.T3dataEntry.COLUMN_NAME_PREVIEW_IMAGE_SOURCE_HEIGHT));

        String videoPreviewUrl = TextUtil.textFromHtml(
                mCursor.getString(mCursor.getColumnIndex(Contract.T3dataEntry.COLUMN_NAME_VARIANT_VIDEO_MP4_URL)));

        int videoPreviewWidth = mCursor.getInt(
                mCursor.getColumnIndex(Contract.T3dataEntry.COLUMN_NAME_VARIANT_VIDEO_MP4_WIDTH));

        int videoPreviewHeight = mCursor.getInt(
                mCursor.getColumnIndex(Contract.T3dataEntry.COLUMN_NAME_VARIANT_VIDEO_MP4_HEIGHT));


        String videoUrl = TextUtil.textFromHtml(
                mCursor.getString(mCursor.getColumnIndex(Contract.T3dataEntry.COLUMN_NAME_URL)));


        String videoTypeOembed = mCursor.getString(mCursor.getColumnIndex(Contract.T3dataEntry.COLUMN_NAME_MEDIA_TYPE));

        String videoFrameOembed = TextUtil.textFromHtml(
                mCursor.getString(mCursor.getColumnIndex(Contract.T3dataEntry.COLUMN_NAME_MEDIA_OEMBED_HTML)));

        String videoAuthorNameOembed = TextUtil.textFromHtml(
                mCursor.getString(mCursor.getColumnIndex(Contract.T3dataEntry.COLUMN_NAME_MEDIA_OEMBED_AUTHOR_NAME)));

        int videoOembedWidth = mCursor.getInt(
                mCursor.getColumnIndex(Contract.T3dataEntry.COLUMN_NAME_MEDIA_OEMBED_WIDTH));

        int videoOembedHeight = mCursor.getInt(
                mCursor.getColumnIndex(Contract.T3dataEntry.COLUMN_NAME_MEDIA_OEMBED_HEIGHT));

        String thumbnailUrlOembed = TextUtil.textFromHtml(
                mCursor.getString(mCursor.getColumnIndex(Contract.T3dataEntry.COLUMN_NAME_MEDIA_OEMBED_THUMBNAIL_URL)));

        int thumbnailOembedWidth = mCursor.getInt(
                mCursor.getColumnIndex(Contract.T3dataEntry.COLUMN_NAME_MEDIA_OEMBED_THUMBNAIL_WIDTH));

        int thumbnailOembedHeight = mCursor.getInt(
                mCursor.getColumnIndex(Contract.T3dataEntry.COLUMN_NAME_MEDIA_OEMBED_THUMBNAIL_HEIGHT));

        holder.mTextViewCreatedUtc.setText(getStringCurrentCreatedUtd(mContext,createdUtc));
        SubRedditHelper subRedditHelper = new SubRedditHelper(mContext);

        int mediaType = 0;

        mediaType = (!TextUtils.isEmpty(imagePreviewUrl)) && (!isSmallImage(mContext, imagePreviewWidth, imagePreviewHeight))
                ? Costants.MEDIA_IMAGE_FULL_TYPE : mediaType;

        mediaType = (!TextUtils.isEmpty(videoPreviewUrl))
                ? Costants.MEDIA_VIDEO_PREVIEW_TYPE_MP4 : mediaType;

        mediaType = (!TextUtils.isEmpty(videoUrl) && (!TextUtils.isEmpty(videoTypeOembed)) &&
                (videoTypeOembed.equals(Costants.BASE_TYPE_VIMEO)))
                ? Costants.MEDIA_VIDEO_TYPE_VIMEO : mediaType;

        mediaType = (!TextUtils.isEmpty(videoFrameOembed) &&
                (!TextUtils.isEmpty(videoTypeOembed)) && (videoTypeOembed.equals(Costants.BASE_TYPE_YOUTUBE)))
                ? Costants.MEDIA_VIDEO_TYPE_YOUTUBE : mediaType;

        switch (mediaType) {

            case Costants.MEDIA_IMAGE_FULL_TYPE:

                holder.mPlayerLayout.setVisibility(View.GONE);
                holder.mWebViewYoutube.setVisibility(View.GONE);

                subRedditHelper.imageReddit(holder.mImageViewSubReddit, holder.mImageViewSubRedditSmall,
                        imagePreviewUrl, imagePreviewWidth, imagePreviewHeight, title);

                holder.mImageViewSubReddit.setVisibility(View.VISIBLE);
                break;

            case Costants.MEDIA_VIDEO_PREVIEW_TYPE_MP4:

                if (mMediaPlayer != null) {
                    mMediaPlayer.releasePlayer();
                }

                mMediaPlayer = new MediaPlayer(mContext,
                        mImaAdsLoader,
                        holder.mPlayerView,
                        holder.mExoProgressBar,
                        title, holder.mTVErrorPlayer, holder.mImagePlay);

                subRedditHelper.loadVideoFirstFrame(mMediaPlayer, holder.mPlayerLayout, holder.mImagePlay, holder.mExoProgressBar,
                        videoPreviewUrl, videoPreviewWidth, videoPreviewHeight);

                holder.mImageViewSubReddit.setVisibility(View.GONE);
                break;

            case Costants.MEDIA_VIDEO_TYPE_VIMEO:

                subRedditHelper.loadWebviewYoutube(holder.mWebViewYoutube, videoFrameOembed);

                holder.mImageViewSubReddit.setVisibility(View.GONE);
                holder.mPlayerLayout.setVisibility(View.GONE);
                break;

            case Costants.MEDIA_VIDEO_TYPE_YOUTUBE:

                if (Preference.isYoutubePlayer(mContext)) {
                    subRedditHelper.youtubeVideoFirstFrame(holder.mPlayerLayout, holder.mImagePlay, holder.mExoProgressBar,
                            thumbnailUrlOembed, thumbnailOembedWidth, thumbnailOembedHeight,
                            videoUrl, videoOembedWidth, videoOembedHeight);

                    holder.mWebViewYoutube.setVisibility(View.GONE);
                } else {
                    subRedditHelper.loadWebviewYoutube(holder.mWebViewYoutube, videoFrameOembed);

                }

                holder.mImageViewSubReddit.setVisibility(View.GONE);
                break;

            default: {
            }
        }

        if ((!TextUtils.isEmpty(imagePreviewUrl)) &&
                (isSmallImage(mContext, imagePreviewWidth, imagePreviewHeight))) {

            subRedditHelper.imageReddit(holder.mImageViewSubReddit, holder.mImageViewSubRedditSmall,
                    imagePreviewUrl, imagePreviewWidth, imagePreviewHeight, title);

            holder.mImageViewSubRedditSmall.setVisibility(View.VISIBLE);
        }

        holder.mTextViewTitle.setText(title);

        holder.mTextViewSubRedditNamePrefix.setText(subRedditNamePrefix);

        holder.mTextViewDomain.setText(domain);

        holder.mTextViewScore.setText(numberFormat(score));

        holder.mTextViewNumComments.setText(
                String.format("%s %s", String.valueOf(numComments),
                        mContext.getString(R.string.text_comments_subreddit))
        );

        SelectorHelper selectorHelper = new SelectorHelper(mContext);

        selectorHelper.cardBottomLink(mArrayButton,null,
                TextUtil.buildCommentLink(subRedditNamePrefix, nameIdReddit), nameReddit);


        holder.bind(holder.getAdapterPosition(), subReddit, nameIdReddit);


        mListener.adapterPosition(holder.getAdapterPosition(), subReddit);
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
            implements View.OnClickListener, ItemTouchHelperViewHolder {

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
        @BindView(R.id.image_open_browser)
        ImageButton mImageButtonOpenBrowser;

        @SuppressWarnings("unused")
        @BindView(R.id.webview_youtube_subreddit)
        WebView mWebViewYoutube;

        private int mPosition;
        private String mSubRedditName;
        private String mNameRedditId;

        SubRedditHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        public void bind(int position, String subRedditName, String nameRedditId) {
            mPosition = position;
            mSubRedditName = subRedditName;
            mNameRedditId = nameRedditId;
        }

        @Override
        public void onItemSelected() {
            itemView.setBackgroundColor(ImageUtils.getColor(mContext, R.color.colorBackgroundItemSelected));
        }

        @Override
        public void onItemClear() {
            itemView.setBackgroundColor(ImageUtils.getColor(mContext, R.color.colorBackgroundItemNoSelected));
        }

        @Override
        public void onClick(View view) {
            mContext.startActivity(new Intent(mContext, SubRedditDetailActivity.class)
                    .putExtra(Costants.EXTRA_SUBREDDIT_DETAIL_POSITION, mPosition)
                    .putExtra(Costants.EXTRA_SUBREDDIT_DETAIL_CATEGORY, mSubRedditName)
                    .putExtra(Costants.EXTRA_SUBREDDIT_DETAIL_STR_ID, mNameRedditId));

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
