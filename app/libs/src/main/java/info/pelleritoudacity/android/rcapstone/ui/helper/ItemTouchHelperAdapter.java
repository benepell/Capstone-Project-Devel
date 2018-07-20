package info.pelleritoudacity.android.rcapstone.ui.helper;

public interface ItemTouchHelperAdapter {

    void onItemMove(int fromPosition, int toPosition);

    void onItemDismiss(int position);
}