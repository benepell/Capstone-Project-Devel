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
import android.widget.Toast;

import com.google.android.exoplayer2.ext.ima.ImaAdsLoader;
import com.google.android.exoplayer2.ui.PlayerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import info.pelleritoudacity.android.rcapstone.R;
import info.pelleritoudacity.android.rcapstone.data.db.Record.RecordSubReddit;
import info.pelleritoudacity.android.rcapstone.media.MediaPlayer;
import info.pelleritoudacity.android.rcapstone.data.model.record.RecordAdapter;
import info.pelleritoudacity.android.rcapstone.ui.activity.SubRedditDetailActivity;
import info.pelleritoudacity.android.rcapstone.ui.fragment.SubRedditFragment;
import info.pelleritoudacity.android.rcapstone.ui.helper.ItemTouchHelperViewHolder;
import info.pelleritoudacity.android.rcapstone.ui.helper.SelectorHelper;
import info.pelleritoudacity.android.rcapstone.ui.helper.SubRedditHelper;
import info.pelleritoudacity.android.rcapstone.utility.Costant;
import info.pelleritoudacity.android.rcapstone.utility.DateUtil;
import info.pelleritoudacity.android.rcapstone.utility.ImageUtil;
import info.pelleritoudacity.android.rcapstone.utility.Preference;
import info.pelleritoudacity.android.rcapstone.utility.TextUtil;

import static info.pelleritoudacity.android.rcapstone.utility.ImageUtil.isSmallImage;
import static info.pelleritoudacity.android.rcapstone.utility.NumberUtil.numberFormat;

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

        RecordSubReddit recordList = new RecordSubReddit(mCursor);
        RecordAdapter record = null;
        if (recordList.getRecordList() != null) {
            record = recordList.getRecordList().get(0);

        }

        if (record != null) {
            holder.mTextViewCreatedUtc.setText(DateUtil.getDiffTimeMinute(mContext,record.getCreatedUtc()));

            SubRedditHelper subRedditHelper = new SubRedditHelper(mContext);

            int mediaType = 0;

            mediaType = (Preference.isGeneralImages(mContext)) && (!TextUtils.isEmpty(record.getImagePreviewUrl())) && (!isSmallImage(mContext, record.getImagePreviewWidth(), record.getImagePreviewHeight()))
                    ? Costant.MEDIA_IMAGE_FULL_TYPE : mediaType;

            mediaType = (Preference.isGeneralVideos(mContext)) && (!TextUtils.isEmpty(record.getVideoPreviewUrl()))
                    ? Costant.MEDIA_VIDEO_PREVIEW_TYPE_MP4 : mediaType;

            mediaType = (Preference.isGeneralVideos(mContext)) && (!TextUtils.isEmpty(record.getVideoUrl()) && (!TextUtils.isEmpty(record.getVideoTypeOembed())) &&
                    (record.getVideoTypeOembed().equals(Costant.BASE_TYPE_VIMEO)))
                    ? Costant.MEDIA_VIDEO_TYPE_VIMEO : mediaType;

            mediaType = (Preference.isGeneralVideos(mContext)) && (!TextUtils.isEmpty(record.getVideoFrameOembed()) &&
                    (!TextUtils.isEmpty(record.getVideoTypeOembed())) && (record.getVideoTypeOembed().equals(Costant.BASE_TYPE_YOUTUBE)))
                    ? Costant.MEDIA_VIDEO_TYPE_YOUTUBE : mediaType;

            switch (mediaType) {

                case Costant.MEDIA_IMAGE_FULL_TYPE:

                    holder.mPlayerLayout.setVisibility(View.GONE);

                    subRedditHelper.imageReddit(holder.mImageViewSubReddit, holder.mImageViewSubRedditSmall,
                            TextUtil.textFromHtml(record.getImagePreviewUrl()), record.getImagePreviewWidth(), record.getImagePreviewHeight(),
                            TextUtil.textFromHtml(record.getTitle()));

                    holder.mImageViewSubReddit.setVisibility(View.VISIBLE);
                    break;

                case Costant.MEDIA_VIDEO_PREVIEW_TYPE_MP4:

                    if (mMediaPlayer != null) {
                        mMediaPlayer.releasePlayer();
                    }

                    mMediaPlayer = new MediaPlayer(mContext,
                            mImaAdsLoader,
                            holder.mPlayerView,
                            holder.mExoProgressBar,
                            TextUtil.textFromHtml(record.getTitle()), holder.mTVErrorPlayer, holder.mImagePlay);

                    subRedditHelper.loadVideoFirstFrame(mMediaPlayer, holder.mPlayerLayout, holder.mImagePlay, holder.mExoProgressBar,
                            TextUtil.textFromHtml(record.getVideoPreviewUrl()), record.getVideoPreviewWidth(), record.getVideoPreviewHeight());

                    holder.mImageViewSubReddit.setVisibility(View.GONE);
                    break;

                case Costant.MEDIA_VIDEO_TYPE_VIMEO:

                    subRedditHelper.loadWebviewVimeo(holder.mWebViewVimeo,
                            TextUtil.textFromHtml(record.getVideoFrameOembed()));

                    holder.mImageViewSubReddit.setVisibility(View.GONE);
                    holder.mPlayerLayout.setVisibility(View.GONE);
                    break;

                case Costant.MEDIA_VIDEO_TYPE_YOUTUBE:

                    subRedditHelper.youtubeVideoFirstFrame(holder.mPlayerLayout, holder.mImagePlay, holder.mExoProgressBar,
                            TextUtil.textFromHtml(record.getThumbnailUrlOembed()),
                            record.getThumbnailOembedWidth(), record.getThumbnailOembedHeight(),
                            TextUtil.textFromHtml(record.getVideoUrl()), record.getVideoOembedWidth(),
                            record.getVideoOembedHeight(),record.getTitle());

                    holder.mImageViewSubReddit.setVisibility(View.GONE);
                    break;

                default: {
                }
            }

            if ((!TextUtils.isEmpty(record.getImagePreviewUrl())) &&
                    (isSmallImage(mContext, record.getImagePreviewWidth(), record.getImagePreviewHeight()))) {

                subRedditHelper.imageReddit(holder.mImageViewSubReddit, holder.mImageViewSubRedditSmall,
                        TextUtil.textFromHtml(record.getImagePreviewUrl()), record.getImagePreviewWidth(), record.getImagePreviewHeight(),
                        TextUtil.textFromHtml(record.getTitle()));

                if (Preference.isGeneralImages(mContext)) {
                    holder.mImageViewSubRedditSmall.setVisibility(View.VISIBLE);

                }
            }

            holder.mTextViewTitle.setText(TextUtil.textFromHtml(record.getTitle()));

            holder.mTextViewSubRedditNamePrefix.setText(record.getSubRedditNamePrefix());

            holder.mTextViewDomain.setText(record.getDomain());

            holder.mTextViewScore.setText(numberFormat(record.getScore()));

            holder.mTextViewNumComments.setText(
                    String.format("%s %s", String.valueOf(record.getNumComments()),
                            mContext.getString(R.string.text_comments_subreddit))
            );

            SelectorHelper selectorHelper = new SelectorHelper(mContext);

            selectorHelper.cardBottomLink(mArrayButton, null,
                    TextUtil.buildCommentLink(record.getSubRedditNamePrefix(), record.getNameIdReddit()), record.getNameReddit());


            holder.bind(holder.getAdapterPosition(), record.getSubReddit(), record.getNameIdReddit(), record.getNumComments());


            mListener.adapterPosition(holder.getAdapterPosition(), record.getSubReddit());
        }
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
        @BindView(R.id.webview_vimeo_subreddit)
        WebView mWebViewVimeo;

        private int mPosition;
        private String mSubRedditName;
        private String mNameRedditId;
        private int mNumComments;

        SubRedditHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        public void bind(int position, String subRedditName, String nameRedditId, int numComments) {
            mPosition = position;
            mSubRedditName = subRedditName;
            mNameRedditId = nameRedditId;
            mNumComments = numComments;
        }

        @Override
        public void onItemSelected() {
            itemView.setBackgroundColor(ImageUtil.getColor(mContext, R.color.colorBackgroundItemSelected));
        }

        @Override
        public void onItemClear() {
            itemView.setBackgroundColor(ImageUtil.getColor(mContext, R.color.colorBackgroundItemNoSelected));
        }

        @Override
        public void onClick(View view) {

            if(mNumComments >0){
                mContext.startActivity(new Intent(mContext, SubRedditDetailActivity.class)
                        .putExtra(Costant.EXTRA_SUBREDDIT_DETAIL_POSITION, mPosition)
                        .putExtra(Costant.EXTRA_SUBREDDIT_DETAIL_CATEGORY, mSubRedditName)
                        .putExtra(Costant.EXTRA_SUBREDDIT_DETAIL_STR_ID, mNameRedditId));

            }else {
                Toast.makeText(mContext,mContext.getString(R.string.text_no_comments),Toast.LENGTH_LONG).show();
            }

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
