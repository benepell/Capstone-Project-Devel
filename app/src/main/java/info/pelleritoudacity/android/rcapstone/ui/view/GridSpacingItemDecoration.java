package info.pelleritoudacity.android.rcapstone.ui.view;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {
 
    private final int mSpanCount;
    private final float mItemSize;
 
    public GridSpacingItemDecoration(int spanCount, int itemSize) {
        this.mSpanCount = spanCount;
        mItemSize = itemSize;
    } 
 
    @Override 
    public void getItemOffsets(@NonNull final Rect outRect, @NonNull final View view, @NonNull RecyclerView parent,
                               @NonNull RecyclerView.State state) {
        final int position = parent.getChildLayoutPosition(view);
        final int column = position % mSpanCount;
        final int parentWidth = parent.getWidth();
        int spacing = (int) (parentWidth - (mItemSize * mSpanCount)) / (mSpanCount + 1);
        outRect.left = spacing - column * spacing / mSpanCount;
        outRect.right = (column + 1) * spacing / mSpanCount;
 
        if (position < mSpanCount) {
            outRect.top = spacing;
        } 
        outRect.bottom = spacing;
    } 
} 