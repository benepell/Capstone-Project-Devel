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
import com.bumptech.glide.request.Request;
import com.bumptech.glide.request.target.SizeReadyCallback;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.material_design_iconic_typeface_library.MaterialDesignIconic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import info.pelleritoudacity.android.rcapstone.R;
import info.pelleritoudacity.android.rcapstone.data.db.AppDatabase;
import info.pelleritoudacity.android.rcapstone.data.db.AppExecutors;
import info.pelleritoudacity.android.rcapstone.data.db.entry.PrefSubRedditEntry;
import info.pelleritoudacity.android.rcapstone.ui.helper.ItemTouchHelperAdapter;
import info.pelleritoudacity.android.rcapstone.ui.helper.ItemTouchHelperViewHolder;
import info.pelleritoudacity.android.rcapstone.ui.helper.OnStartDragListener;
import info.pelleritoudacity.android.rcapstone.utility.Costant;
import info.pelleritoudacity.android.rcapstone.utility.ImageUtil;
import info.pelleritoudacity.android.rcapstone.utility.MapUtil;
import info.pelleritoudacity.android.rcapstone.utility.Preference;
import info.pelleritoudacity.android.rcapstone.utility.TextUtil;

@SuppressWarnings("WeakerAccess")
public class ManageAdapter extends RecyclerView.Adapter<ManageAdapter.RedditHolder> implements ItemTouchHelperAdapter {

    private final OnStartDragListener mDragStartListener;
    private final AppDatabase mDb;

    private final OnSubScriptionClick mListener;
    private ArrayList<String> mArrayList;
    private Context mContext;
    private List<PrefSubRedditEntry> mPrefSubRedditEntry;

    public ManageAdapter( Context context,AppDatabase db, OnSubScriptionClick listener,
                         OnStartDragListener dragStartListener) {
        mContext = context;
        mDb = db;
        mListener = listener;
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
        int layoutId = R.layout.list_manage_main;
        LayoutInflater inflater = LayoutInflater.from(mContext);

        View view = inflater.inflate(layoutId, parent, false);

        return new RedditHolder(view);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onBindViewHolder(@NonNull RedditHolder holder, int position) {

        PrefSubRedditEntry r = mPrefSubRedditEntry.get(position);

        String name = r.getName();

        mArrayList.add(position, name);
        mArrayList = MapUtil.removeArrayListDuplicate(mArrayList);

        int colorImageHandle = Color.BLACK;

        if (Preference.isNightMode(mContext)) {
            colorImageHandle = Color.WHITE;
        }

        holder.mImageViewRedditHandle.setImageDrawable(new IconicsDrawable(mContext, MaterialDesignIconic.Icon.gmi_view_headline)
                .color(colorImageHandle)
                .respectFontBounds(true));

        holder.mImageViewRedditHandle.setOnTouchListener((v, motionEvent) -> {

            if (motionEvent.getActionMasked() == MotionEvent.ACTION_DOWN) {
                mDragStartListener.onStartDrag(holder);
            }
            return false;
        });

        String iconUrl = r.getImage();

        Glide.with(holder.itemView.getContext().getApplicationContext())
                .asBitmap()
                .load(iconUrl)
                .into(new Target<Bitmap>() {
                    @Override
                    public void onLoadStarted(@Nullable Drawable placeholder) {

                    }

                    @Override
                    public void onLoadFailed(@Nullable Drawable errorDrawable) {
                        holder.mImageViewRedditIcon.setBackgroundResource(R.drawable.ic_no_image);
                    }

                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        ImageUtil.createRoundImage(mContext, holder.mImageViewRedditIcon, resource);
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {

                    }

                    @Override
                    public void getSize(@NonNull SizeReadyCallback cb) {

                    }

                    @Override
                    public void removeCallback(@NonNull SizeReadyCallback cb) {

                    }

                    @Override
                    public void setRequest(@Nullable Request request) {

                    }

                    @Nullable
                    @Override
                    public Request getRequest() {
                        return null;
                    }

                    @Override
                    public void onStart() {

                    }

                    @Override
                    public void onStop() {

                    }

                    @Override
                    public void onDestroy() {

                    }
                });


        int visible = r.getVisible();

        if (visible != 0) {
            holder.mImageViewRedditStars.setImageDrawable(new IconicsDrawable(mContext, MaterialDesignIconic.Icon.gmi_star).color(Color.RED)
                    .respectFontBounds(true));

        } else {
            holder.mImageViewRedditStars.setImageDrawable(new IconicsDrawable(mContext, MaterialDesignIconic.Icon.gmi_star_border).color(Color.RED)
                    .respectFontBounds(true));

        }

        holder.mImageViewRedditStars.setOnClickListener(v -> mListener.onClickStar(visible, name));

        holder.mImageViewRedditIcon.setContentDescription(name);

        holder.mTextViewRedditName.setText(name);

    }

    @Override
    public int getItemCount() {
        return (mPrefSubRedditEntry == null) ? 0 : mPrefSubRedditEntry.size();
    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) {

        moveManage(fromPosition, toPosition);
        Collections.swap(mArrayList, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);


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

    @SuppressWarnings("WeakerAccess")
    public void moveManage(int fromPosition, int toPosition) {

        mListener.onItemMove(toPosition);

        if (fromPosition == 0 && toPosition == 0) {
            return;

        } else {

            fromPosition += 1;
            toPosition += 1;
        }


        int finalFromPosition = fromPosition;
        int finalToPosition = toPosition;

        AppExecutors.getInstance().diskIO().execute(() -> mDb.prefSubRedditDao()
                .updateRecordByManagePosition(finalToPosition, new Date(System.currentTimeMillis()), finalFromPosition));

        if (getPrefSubRedditEntry().get(0).getId() != 0) {
            int finalFromPosition1 = fromPosition;
            AppExecutors.getInstance().diskIO().execute(() -> mDb.prefSubRedditDao().updateRecordByDuplicatePosition(finalFromPosition1, getPrefSubRedditEntry().get(0).getId()));

        }
    }


    public List<PrefSubRedditEntry> getPrefSubRedditEntry() {
        return mPrefSubRedditEntry;
    }

    public void setPrefSubRedditEntry(List<PrefSubRedditEntry> entry) {
        mPrefSubRedditEntry = entry;
        notifyDataSetChanged();
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

            if (Preference.isNightMode(mContext)) {
                itemView.setBackgroundColor(ImageUtil.getColor(mContext, R.color.colorDarkBackgroundItemSelected));

            } else {
                itemView.setBackgroundColor(ImageUtil.getColor(mContext, R.color.colorBackgroundItemSelected));

            }
        }

        @Override
        public void onItemClear() {
            if (Preference.isNightMode(mContext)) {
                itemView.setBackgroundColor(ImageUtil.getColor(mContext, R.color.colorDarkBackgroundItemNoSelected));

            } else {
                itemView.setBackgroundColor(ImageUtil.getColor(mContext, R.color.colorBackgroundItemNoSelected));

            }
        }


    }


    public interface OnSubScriptionClick {
        void onClickStar(int visible, String name);

        void onItemRemove(@SuppressWarnings("unused") int position, String description);
        void onItemMove(int toPosition);
    }


}
