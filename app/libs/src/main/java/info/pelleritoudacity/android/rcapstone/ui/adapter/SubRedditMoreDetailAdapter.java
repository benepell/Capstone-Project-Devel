package info.pelleritoudacity.android.rcapstone.ui.adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import info.pelleritoudacity.android.rcapstone.R;
import info.pelleritoudacity.android.rcapstone.ui.fragment.SubRedditMoreDetailFragment;

public class SubRedditMoreDetailAdapter extends RecyclerView.Adapter<SubRedditMoreDetailAdapter.SubRedditMoreDetailHolder> {

    private Cursor mCursor;
    private SubRedditMoreDetailFragment mListener;
    private final Context mContext;

    public SubRedditMoreDetailAdapter(SubRedditMoreDetailFragment listener) {
        mListener = listener;
        mContext = listener.getActivity();
    }

    @NonNull
    @Override
    public SubRedditMoreDetailHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int layoutId = R.layout.list_subreddit_more_detail;
        LayoutInflater inflater = LayoutInflater.from(mContext);

        View view = inflater.inflate(layoutId, parent, false);

        return new SubRedditMoreDetailHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SubRedditMoreDetailHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return (mCursor == null) ? 0 : mCursor.getCount();
    }

    public class SubRedditMoreDetailHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        /*@SuppressWarnings("unused")
        @BindView(R.id.tv_author)
        TextView mTextViewAuthorDetail;
*/

        SubRedditMoreDetailHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);

        }

        void bind() {
        }


        @Override
        public void onClick(View view) {

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

    public interface OnAdapterListener {

        void adapterPosition(int position, String category);

        void clickSelector(int position, int itemCount);
    }
}
