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

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.material_design_iconic_typeface_library.MaterialDesignIconic;

import java.util.ArrayList;
import java.util.Collections;

import butterknife.BindView;
import butterknife.ButterKnife;
import info.pelleritoudacity.android.rcapstone.R;
import info.pelleritoudacity.android.rcapstone.data.db.Contract;
import info.pelleritoudacity.android.rcapstone.data.db.util.DataUtils;
import info.pelleritoudacity.android.rcapstone.ui.fragment.SubScriptionsFragment;
import info.pelleritoudacity.android.rcapstone.ui.helper.ItemTouchHelperAdapter;
import info.pelleritoudacity.android.rcapstone.ui.helper.ItemTouchHelperViewHolder;
import info.pelleritoudacity.android.rcapstone.ui.helper.OnStartDragListener;
import info.pelleritoudacity.android.rcapstone.utility.Costant;
import info.pelleritoudacity.android.rcapstone.utility.ImageUtil;
import info.pelleritoudacity.android.rcapstone.utility.MapUtil;
import info.pelleritoudacity.android.rcapstone.utility.Preference;
import info.pelleritoudacity.android.rcapstone.utility.TextUtil;

public class SubScriptionsAdapter extends RecyclerView.Adapter<SubScriptionsAdapter.RedditHolder> implements ItemTouchHelperAdapter {

    private final OnStartDragListener mDragStartListener;
    private final OnSubScriptionClick onSubScriptionClick;
    private final SubScriptionsFragment mListener;
    private ArrayList<String> mArrayList;
    private Context mContext;
    private Cursor mCursor;

    public SubScriptionsAdapter(Context context, SubScriptionsFragment listener, OnSubScriptionClick subScriptionClick,
                                OnStartDragListener dragStartListener) {
        mContext = context;
        mListener = listener;
        onSubScriptionClick = subScriptionClick;
        mDragStartListener = dragStartListener;
        mArrayList = new ArrayList<>();


        if (!TextUtils.isEmpty(Preference.getSubredditKey(mContext))) {
            mArrayList.addAll(TextUtil.stringToArray(Preference.getSubredditKey(mContext)));
        }

    }

    @NonNull
    @Override
    public RedditHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        int layoutId = R.layout.list_manage_subreddit;
        LayoutInflater inflater = LayoutInflater.from(mContext);

        View view = inflater.inflate(layoutId, parent, false);

        return new RedditHolder(view);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onBindViewHolder(@NonNull RedditHolder holder, int position) {
        mCursor.moveToPosition(position);

        String name = mCursor.getString(mCursor.getColumnIndex(Contract.PrefSubRedditEntry.COLUMN_NAME_NAME));

        mArrayList.add(position, name);
        mArrayList = MapUtil.removeArrayListDuplicate(mArrayList);

        int colorImageHandle = Color.BLACK;

        if(Preference.isNightMode(mContext)){
            colorImageHandle = Color.WHITE;
        }

        holder.mImageViewRedditHandle.setImageDrawable(new IconicsDrawable(mContext, MaterialDesignIconic.Icon.gmi_view_headline)
                .color(colorImageHandle)
                .respectFontBounds(true));


        holder.mImageViewRedditHandle.setOnTouchListener((v, motionEvent) -> {
            if (motionEvent.getActionMasked() == MotionEvent.ACTION_DOWN) {
                mDragStartListener.onStartDrag(holder);
            }
//            v.getParent().requestDisallowInterceptTouchEvent(true);
            return false;
        });

        String iconUrl = mCursor.getString(mCursor.getColumnIndex(Contract.PrefSubRedditEntry.COLUMN_NAME_IMAGE));

        Glide.with(holder.itemView.getContext().getApplicationContext())
                .asBitmap()
                .load(iconUrl)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onLoadFailed(@Nullable Drawable errorDrawable) {
                        super.onLoadFailed(errorDrawable);
                        holder.mImageViewRedditIcon.setBackgroundResource(R.drawable.logo);
                    }

                    @Override
                    public void onLoadStarted(@Nullable Drawable placeholder) {
                        super.onLoadStarted(placeholder);
                        holder.mImageViewRedditIcon.setBackgroundResource(R.drawable.logo);
                    }

                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, Transition<? super Bitmap> transition) {
                        ImageUtil.createRoundImage(mContext, holder.mImageViewRedditIcon, resource);
                    }
                });

        int visible = mCursor.getInt(mCursor.getColumnIndex(Contract.PrefSubRedditEntry.COLUMN_NAME_VISIBLE));

        if (visible != 0) {
            holder.mImageViewRedditStars.setImageDrawable(new IconicsDrawable(mContext, MaterialDesignIconic.Icon.gmi_star).color(Color.RED)
                    .respectFontBounds(true));

        } else {
            holder.mImageViewRedditStars.setImageDrawable(new IconicsDrawable(mContext, MaterialDesignIconic.Icon.gmi_star_border).color(Color.RED)
                    .respectFontBounds(true));

        }

        holder.mImageViewRedditStars.setOnClickListener(v -> onSubScriptionClick.onClickStar(visible, name));

        holder.mImageViewRedditIcon.setContentDescription(name);


        holder.mTextViewRedditName.setText(name);

    }

    @Override
    public int getItemCount() {
        return (mCursor == null) ? 0 : mCursor.getCount();
    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        DataUtils moveData = new DataUtils(mContext);

        if (moveData.moveManage(fromPosition, toPosition)) {
            Collections.swap(mArrayList, fromPosition, toPosition);
            notifyItemMoved(fromPosition, toPosition);

        }

        String string = TextUtil.arrayToString(mArrayList);

        if (!TextUtils.isEmpty(string)) {
            Preference.setSubredditKey(mContext, string);
        }

    }

    @Override
    public void onItemDismiss(int position) {
        if (getItemCount() > Costant.DEFAULT_SUBREDDIT_ITEMS) {

            String description = mArrayList.get(position);

            mListener.onItemRemove(position, description);

            mArrayList.remove(position);
            notifyItemRemoved(position);

            mArrayList = MapUtil.removeArrayListDuplicate(mArrayList);
            String string = TextUtil.arrayToString(mArrayList);

            if (!TextUtils.isEmpty(string)) {
                Preference.setSubredditKey(mContext, string);
            }

        }
    }


    public class RedditHolder extends RecyclerView.ViewHolder
            implements ItemTouchHelperViewHolder {

        @SuppressWarnings("unused")
        @BindView(R.id.tv_reddit_name)
        TextView mTextViewRedditName;

        @SuppressWarnings("unused")
        @BindView(R.id.img_handle)
        ImageView mImageViewRedditHandle;

        @SuppressWarnings("unused")
        @BindView(R.id.img_icon)
        ImageView mImageViewRedditIcon;

        @SuppressWarnings("unused")
        @BindView(R.id.img_manage_stars)
        ImageView mImageViewRedditStars;


        RedditHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);

        }

        @Override
        public void onItemSelected() {

            if(Preference.isNightMode(mContext)){
                itemView.setBackgroundColor(ImageUtil.getColor(mContext, R.color.colorDarkBackgroundItemSelected));

            }else {
                itemView.setBackgroundColor(ImageUtil.getColor(mContext, R.color.colorBackgroundItemSelected));

            }
        }

        @Override
        public void onItemClear() {
            if(Preference.isNightMode(mContext)){
                itemView.setBackgroundColor(ImageUtil.getColor(mContext, R.color.colorDarkBackgroundItemNoSelected));

            }else {
                itemView.setBackgroundColor(ImageUtil.getColor(mContext, R.color.colorBackgroundItemNoSelected));

            }
        }

        public void bind() {
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

    public interface OnSubScriptionClick {
        void onClickStar(int visible, String name);

        void onItemRemove(int position, String description);
    }
}
