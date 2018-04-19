package info.pelleritoudacity.android.rcapstone.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.material_design_iconic_typeface_library.MaterialDesignIconic;

import java.util.ArrayList;
import java.util.Collections;

import butterknife.BindView;
import butterknife.ButterKnife;
import info.pelleritoudacity.android.rcapstone.R;
import info.pelleritoudacity.android.rcapstone.data.Contract;
import info.pelleritoudacity.android.rcapstone.ui.helper.ItemTouchHelperAdapter;
import info.pelleritoudacity.android.rcapstone.ui.helper.ItemTouchHelperViewHolder;
import info.pelleritoudacity.android.rcapstone.ui.helper.OnStartDragListener;
import info.pelleritoudacity.android.rcapstone.utility.PrefManager;
import info.pelleritoudacity.android.rcapstone.utility.Utility;

public class SubScriptionsAdapter extends RecyclerView.Adapter<SubScriptionsAdapter.RedditHolder> implements ItemTouchHelperAdapter {

    private final OnStartDragListener mDragStartListener;
    // todo remove static arraylist use persistant
    private final ArrayList<String> mArrayList;
    private Context mContext;
    private Cursor mCursor;
    private Handler mHandler = null;

    public SubScriptionsAdapter(Context context,
                                OnStartDragListener dragStartListener) {
        mDragStartListener = dragStartListener;
        mContext = context;
        mArrayList = new ArrayList<>();
        if (!TextUtils.isEmpty(PrefManager.getStringPref(mContext, R.string.pref_subreddit_key))) {
            mArrayList.addAll(Utility.stringToArray(PrefManager.getStringPref(mContext, R.string.pref_subreddit_key)));
        }

    }

    @NonNull
    @Override
    public RedditHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        int layoutId = R.layout.list_reddit;
        LayoutInflater inflater = LayoutInflater.from(mContext);

        View view = inflater.inflate(layoutId, parent, false);

        return new RedditHolder(view);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onBindViewHolder(@NonNull RedditHolder holder, int position) {
        mCursor.moveToPosition(position);

        int idReddit = mCursor.getInt(mCursor.getColumnIndex(Contract.T5dataEntry._ID));
        String nameIdReddit = mCursor.getString(mCursor.getColumnIndex(Contract.T5dataEntry.COLUMN_NAME_ID));

        String titleReddit;
        if (TextUtils.isEmpty(PrefManager.getStringPref(mContext, R.string.pref_subreddit_key))) {

            titleReddit = mCursor.getString(mCursor.getColumnIndex(Contract.T5dataEntry.COLUMN_NAME_TITLE));
            mArrayList.add(position, titleReddit);
        } else {
            if (position < mArrayList.size()) {
                titleReddit = mArrayList.get(position);
            } else {
                titleReddit = mCursor.getString(mCursor.getColumnIndex(Contract.T5dataEntry.COLUMN_NAME_TITLE));
                mArrayList.add(position, titleReddit);
            }
        }

        holder.mImageViewRedditHandle.setImageDrawable(new IconicsDrawable(mContext, MaterialDesignIconic.Icon.gmi_view_headline)
                .respectFontBounds(true));

        holder.mImageViewRedditHandle.setOnTouchListener((v, motionEvent) -> {
            if (motionEvent.getActionMasked() == MotionEvent.ACTION_DOWN) {
                mDragStartListener.onStartDrag(holder);
            }
            v.getParent().requestDisallowInterceptTouchEvent(true);
            return false;
        });

        holder.mTextViewRedditName.setText(titleReddit);

    }


    @Override
    public int getItemCount() {
        return (mCursor == null) ? 0 : mCursor.getCount();
    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        Collections.swap(mArrayList, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
        String string = Utility.arrayToString(mArrayList);

        if (!TextUtils.isEmpty(string)) {
            PrefManager.putStringPref(mContext, R.string.pref_subreddit_key, string);
        }

    }

    @Override
    public void onItemDismiss(int position) {
        mArrayList.remove(position);
        notifyItemRemoved(position);

        String string = Utility.arrayToString(mArrayList);

        if (!TextUtils.isEmpty(string)) {
            PrefManager.putStringPref(mContext, R.string.pref_subreddit_key, string);
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


        RedditHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);

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
