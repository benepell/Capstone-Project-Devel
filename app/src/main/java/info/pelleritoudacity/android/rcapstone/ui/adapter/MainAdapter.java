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


import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
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

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import info.pelleritoudacity.android.rcapstone.R;
import info.pelleritoudacity.android.rcapstone.data.db.AppDatabase;
import info.pelleritoudacity.android.rcapstone.data.db.entry.T3Entry;
import info.pelleritoudacity.android.rcapstone.data.db.record.MainRecord;
import info.pelleritoudacity.android.rcapstone.data.db.util.DataUtils;
import info.pelleritoudacity.android.rcapstone.data.model.MediaModel;
import info.pelleritoudacity.android.rcapstone.data.model.record.RecordAdapter;
import info.pelleritoudacity.android.rcapstone.data.model.reddit.SubmitData;
import info.pelleritoudacity.android.rcapstone.data.model.ui.CardBottomModel;
import info.pelleritoudacity.android.rcapstone.data.rest.CommentExecute;
import info.pelleritoudacity.android.rcapstone.data.rest.DelExecute;
import info.pelleritoudacity.android.rcapstone.media.MediaPlayer;
import info.pelleritoudacity.android.rcapstone.ui.helper.ItemTouchHelperViewHolder;
import info.pelleritoudacity.android.rcapstone.ui.helper.MainHelper;
import info.pelleritoudacity.android.rcapstone.ui.helper.SelectorHelper;
import info.pelleritoudacity.android.rcapstone.utility.ActivityUI;
import info.pelleritoudacity.android.rcapstone.utility.Costant;
import info.pelleritoudacity.android.rcapstone.utility.DateUtil;
import info.pelleritoudacity.android.rcapstone.utility.ImageUtil;
import info.pelleritoudacity.android.rcapstone.utility.NetworkUtil;
import info.pelleritoudacity.android.rcapstone.utility.PermissionUtil;
import info.pelleritoudacity.android.rcapstone.utility.Preference;
import info.pelleritoudacity.android.rcapstone.utility.TextUtil;
import info.pelleritoudacity.android.rcapstone.utility.Utility;
import okhttp3.ResponseBody;

import static info.pelleritoudacity.android.rcapstone.utility.NumberUtil.numberFormat;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.SubRedditHolder> implements SelectorHelper.OnSelector {

    private Context mContext;
    private final OnMainClick mMainListener;
    private MediaPlayer mMediaPlayer;
    private final ImaAdsLoader mImaAdsLoader;
    private List<T3Entry> mEntry;
    private final AppDatabase mDb;


    public MainAdapter(OnMainClick mainListener, AppDatabase db, Context context, ImaAdsLoader imaAdsLoader) {
        mMainListener = mainListener;
        mDb = db;
        mContext = context;
        mImaAdsLoader = imaAdsLoader;
    }

    @NonNull
    @Override
    public SubRedditHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        int layoutId = R.layout.list_main;
        LayoutInflater inflater = LayoutInflater.from(mContext);

        View view = inflater.inflate(layoutId, parent, false);

        return new SubRedditHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SubRedditHolder holder, int position) {

        if (mEntry != null) {
            MainRecord recordList = new MainRecord(mEntry.get(position));
            RecordAdapter record = null;
            if (recordList.getRecordList() != null) {
                record = recordList.getRecordList().get(0);

            }

            if (record != null) {

                holder.mTextViewCreatedUtc.setText(DateUtil.getDiffTimeMinute(mContext, record.getCreatedUtc()));

                MainHelper mainHelper = new MainHelper(mContext);

                int mediaType = 0;

                mediaType = Preference.isGeneralImages(mContext) && !TextUtils.isEmpty(record.getImagePreviewUrl())
                        ? Costant.MEDIA_IMAGE_FULL_TYPE : mediaType;

                mediaType = Preference.isGeneralVideos(mContext) && !TextUtils.isEmpty(record.getVideoPreviewUrl())
                        ? Costant.MEDIA_VIDEO_PREVIEW_TYPE_MP4 : mediaType;

                mediaType = Preference.isGeneralVideos(mContext) && (!TextUtils.isEmpty(record.getVideoUrl()) && !TextUtils.isEmpty(record.getVideoTypeOembed()) &&
                        record.getVideoTypeOembed().equals(Costant.BASE_TYPE_VIMEO))
                        ? Costant.MEDIA_VIDEO_TYPE_VIMEO : mediaType;

                mediaType = Preference.isGeneralVideos(mContext) && !TextUtils.isEmpty(record.getVideoFrameOembed()) &&
                        !TextUtils.isEmpty(record.getVideoTypeOembed()) &&
                        (record.getVideoTypeOembed().equals(Costant.BASE_TYPE_YOUTUBE) ||
                                record.getVideoTypeOembed().equals("m.".concat(Costant.BASE_TYPE_YOUTUBE)))
                        ? Costant.MEDIA_VIDEO_TYPE_YOUTUBE : mediaType;

                switch (mediaType) {

                    case Costant.MEDIA_IMAGE_FULL_TYPE:

                        holder.mPlayerLayout.setVisibility(View.GONE);

                        mainHelper.imageReddit(holder.mImageViewSubReddit,
                                TextUtil.textFromHtml(record.getImagePreviewUrl()),
                                TextUtil.textFromHtml(record.getTitle()));
                        holder.mImageViewSubReddit.setScaleType(ImageView.ScaleType.CENTER_CROP);
                        holder.mImageViewSubReddit.setVisibility(View.VISIBLE);
                        break;

                    case Costant.MEDIA_VIDEO_PREVIEW_TYPE_MP4:

                        if (mMediaPlayer != null) {
                            mMediaPlayer.releasePlayer();
                        }

                        MediaModel mediaModel = new MediaModel();

                        if (mImaAdsLoader != null) {
                            mediaModel.setImaAdsLoader(mImaAdsLoader);

                        }

                        mediaModel.setPlayerView(holder.mPlayerView);
                        mediaModel.setProgressBar(holder.mExoProgressBar);
                        mediaModel.setTvErrorPlayer(holder.mTVErrorPlayer);
                        mediaModel.setImagePlay(holder.mImagePlay);
                        mediaModel.setImagePreview(holder.mImagePreview);

                        mMediaPlayer = new MediaPlayer(mContext, mediaModel);
                        mMainListener.mediaPlayer(mMediaPlayer);

                        mainHelper.loadVideoFirstFrame(mMediaPlayer, holder.mPlayerLayout, holder.mImagePreview, holder.mImagePlay, holder.mExoProgressBar,
                                TextUtil.textFromHtml(record.getVideoPreviewUrl()));

                        holder.mImageViewSubReddit.setVisibility(View.GONE);
                        break;

                    case Costant.MEDIA_VIDEO_TYPE_VIMEO:

                        mainHelper.loadWebviewVimeo(holder.mWebViewVimeo,
                                TextUtil.textFromHtml(record.getVideoFrameOembed()));

                        holder.mImageViewSubReddit.setVisibility(View.GONE);
                        holder.mPlayerLayout.setVisibility(View.GONE);
                        break;

                    case Costant.MEDIA_VIDEO_TYPE_YOUTUBE:

                        mainHelper.youtubeVideoFirstFrame(holder.mPlayerLayout, holder.mImagePreview, holder.mImagePlay, holder.mExoProgressBar,
                                TextUtil.textFromHtml(record.getThumbnailUrlOembed()),
                                TextUtil.textFromHtml(record.getVideoUrl()), record.getTitle());

                        holder.mImageViewSubReddit.setVisibility(View.GONE);
                        break;

                    default: {
                        if (Preference.isGeneralImages(mContext)) {
                            holder.mImageViewSubReddit.setImageResource(R.drawable.ic_no_image);
                            holder.mImageViewSubReddit.setVisibility(View.VISIBLE);

                        } else {
                            holder.mImageViewSubReddit.setVisibility(View.INVISIBLE);

                        }
                    }
                }

                if (Utility.isTablet(mContext) && ActivityUI.isLandscapeOrientation(mContext)) {
                    holder.mImageViewSubReddit.setScaleType(ImageView.ScaleType.FIT_CENTER);

                }

                holder.mTextViewTitle.setText(TextUtil.textFromHtml(record.getTitle()));

                holder.mTextViewSubRedditNamePrefix.setText(record.getSubRedditNamePrefix());

                holder.mTextViewDomain.setText(record.getDomain());

                holder.mTextViewScore.setText(numberFormat(record.getScore()));

                holder.mTextViewNumComments.setText(
                        String.format("%s %s", String.valueOf(record.getNumComments()),
                                mContext.getString(R.string.text_comments_subreddit))
                );

                SelectorHelper mSelectorHelper = new SelectorHelper(this, mContext, mDb, holder.itemView);

                CardBottomModel cardBottomModel = new CardBottomModel();

                ImageButton[] arrayButton = new ImageButton[]{holder.mImageButtonVoteUp, holder.mImageButtonVoteDown,
                        holder.mImageButtonPreferStars, holder.mImageButtonShowComments, holder.mImageButtonOpenBrowser};

                cardBottomModel.setArrayButton(arrayButton);
                cardBottomModel.setLinkComment(
                        TextUtil.buildCommentLink(record.getSubRedditNamePrefix(), record.getNameIdReddit()));

                cardBottomModel.setPosition(position);
                cardBottomModel.setScore(record.getScore());
                cardBottomModel.setDirScore(record.getDirScore());
                cardBottomModel.setSaved(record.isSaved());
                cardBottomModel.setCategory(record.getNameReddit());
                cardBottomModel.setBackgroundColor(null);
                cardBottomModel.setOnline(NetworkUtil.isOnline(mContext));
                cardBottomModel.setLogged(PermissionUtil.isLogged(mContext));
                cardBottomModel.setTitle(record.getTitle());
                cardBottomModel.setAuthor(record.getAuthor());

                mSelectorHelper.cardBottomLink(cardBottomModel);

                holder.bind(position, record.getSubReddit(), record.getNameIdReddit(), record.getNumComments());

            }
        }

    }

    @Override
    public int getItemCount() {
        return (mEntry == null) ? 0 : mEntry.size();
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull SubRedditHolder holder) {
        super.onViewDetachedFromWindow(holder);
        if (mMediaPlayer != null) {
            mMediaPlayer.releasePlayer();
        }

    }

    @Override
    public void vote(int position, int score, boolean voteUp, int dir, String linkId) {
        new DataUtils(mContext, mDb).updateVote(Costant.DB_TABLE_T3, score, voteUp, dir, linkId);
        mMainListener.selectorChange(position);

    }

    @Override
    public void comments(View view, Dialog dialog, String title, String text, String fullname) {
        new CommentExecute(new CommentExecute.OnRestCallBack() {
            @Override
            public void success(SubmitData response, int code) {
                dialog.dismiss();
                if (response.isSuccess()) {
                    mMainListener.snackMsg(R.string.text_comment_saved);
                } else {
                    mMainListener.snackMsg(R.string.text_error_comment);
                }
            }

            @Override
            public void unexpectedError(Throwable tList) {
                dialog.dismiss();
                mMainListener.snackMsg(R.string.text_error_comment);

            }
        }, PermissionUtil.getToken(mContext), text, fullname).postData();
    }

    @Override
    public void deleteComments(View view, Dialog dialog, String fullname) {
        if (!TextUtils.isEmpty(fullname)) {

            new DelExecute(new DelExecute.OnRestCallBack() {
                @Override
                public void success(ResponseBody response, int code) {
                    if (code == 200) {
                        mMainListener.snackMsg(R.string.text_comment_deleted);

                    } else {
                        mMainListener.snackMsg(R.string.text_error_comment);

                    }

                    dialog.dismiss();

                }

                @Override
                public void unexpectedError(Throwable tList) {
                    dialog.dismiss();
                    mMainListener.snackMsg(R.string.text_error_comment);

                }
            }, PermissionUtil.getToken(mContext), fullname).delData();

        }
    }

    public List<T3Entry> getEntry() {
        return mEntry;
    }

    public void setEntry(List<T3Entry> entry) {
        mEntry = entry;
        notifyDataSetChanged();
    }


    @Override
    public void snackMsg(int resource) {
        mMainListener.snackMsg(resource);
    }


    @Override
    public void stars(int position) {
        mMainListener.selectorChange(position);
    }

    public class SubRedditHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener, ItemTouchHelperViewHolder {

        @SuppressWarnings("unused")
        @BindView(R.id.card_subreddit)
        CardView mCardView;

        @SuppressWarnings("unused")
        @BindView(R.id.tv_title)
        TextView mTextViewTitle;

        @SuppressWarnings("unused")
        @BindView(R.id.img_subreddit)
        ImageView mImageViewSubReddit;

        @SuppressWarnings("unused")
        @BindView(R.id.tv_subreddit_name_prefix)
        TextView mTextViewSubRedditNamePrefix;

        @SuppressWarnings({"WeakerAccess", "CanBeFinal", "unused"})
        @BindView(R.id.id_player_layout)
        FrameLayout mPlayerLayout;

        @SuppressWarnings({"WeakerAccess", "CanBeFinal", "unused"})
        @BindView(R.id.image_subreddit_preview)
        ImageView mImagePreview;

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

        private String mSubRedditName;

        private int mPosition;
        private String mNameRedditId;
        private int mNumComments;

        SubRedditHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);

        }

        void bind(int position, String subRedditName, String nameRedditId, int numComments) {
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

            if (mNumComments > 0) {
                mMainListener.mainClick(mPosition, mSubRedditName, mNameRedditId);

            } else {
                mMainListener.snackMsg(R.string.text_no_comments);

            }
        }


    }


    public interface OnMainClick {
        void mainClick(int position, String category, String strId);

        void selectorChange(int position);

        void mediaPlayer(MediaPlayer mediaPlayer);

        void snackMsg(int resource);

    }

}
