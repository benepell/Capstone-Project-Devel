package info.pelleritoudacity.android.rcapstone.ui.helper;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.material_design_iconic_typeface_library.MaterialDesignIconic;

import java.util.Objects;

import info.pelleritoudacity.android.rcapstone.R;
import info.pelleritoudacity.android.rcapstone.data.db.AppDatabase;
import info.pelleritoudacity.android.rcapstone.data.db.util.DataUtils;
import info.pelleritoudacity.android.rcapstone.data.model.ui.CardBottomModel;
import info.pelleritoudacity.android.rcapstone.data.rest.PrefExecute;
import info.pelleritoudacity.android.rcapstone.data.rest.VoteExecute;
import info.pelleritoudacity.android.rcapstone.utility.NetworkUtil;
import info.pelleritoudacity.android.rcapstone.utility.PermissionUtil;
import info.pelleritoudacity.android.rcapstone.utility.Preference;
import okhttp3.ResponseBody;
import timber.log.Timber;

public class SelectorHelper {
    private final Context mContext;
    private final OnSelector mListener;
    private final View mItemView;
    private final AppDatabase mDb;
    private boolean isDeleteComment;

    public SelectorHelper(OnSelector listener, Context context, AppDatabase db, View itemView) {

        mContext = context;
        mDb = db;
        mListener = listener;
        mItemView = itemView;
    }

    public void cardBottomLink(CardBottomModel model) {

        model.setOnline(NetworkUtil.isOnline(mContext));
        model.setLogged(PermissionUtil.isLogged(mContext));

        if ((model.getArrayButton() != null) && (model.getArrayButton().length == 5)) {

            ImageButton buttonVoteUp = model.getArrayButton()[0];
            ImageButton buttonVoteDown = model.getArrayButton()[1];
            ImageButton buttonStars = model.getArrayButton()[2];
            ImageButton buttonComments = model.getArrayButton()[3];
            ImageButton buttonOpenBrowser = model.getArrayButton()[4];

            if (model.getBackgroundColor() == null) {
                model.setBackgroundColor("#FFFFFF");
            }

            buttonVoteUp.setBackgroundColor(Color.parseColor(model.getBackgroundColor()));
            buttonVoteUp.setImageDrawable(new IconicsDrawable(mContext, MaterialDesignIconic.Icon.gmi_thumb_up)
                    .color(Color.GRAY)
                    .sizeDp(mContext.getResources().getInteger(R.integer.icon_card_bottom))
                    .respectFontBounds(true));

            buttonVoteDown.setBackgroundColor(Color.parseColor(model.getBackgroundColor()));
            buttonVoteDown.setImageDrawable(new IconicsDrawable(mContext, MaterialDesignIconic.Icon.gmi_thumb_down)
                    .color(Color.GRAY)
                    .sizeDp(mContext.getResources().getInteger(R.integer.icon_card_bottom))
                    .respectFontBounds(true));


            switch (model.getDirScore()) {
                case 1:
                    buttonVoteDown.setColorFilter(Color.GRAY);
                    buttonVoteUp.setActivated(true);
                    buttonVoteUp.setColorFilter(Color.BLUE);

                    break;
                case -1:
                    buttonVoteUp.setColorFilter(Color.GRAY);
                    buttonVoteDown.setActivated(true);
                    buttonVoteDown.setColorFilter(Color.BLUE);
                    Timber.d("UNSAVE %s", model.getPosition());
                    break;

                case 0:
                    buttonVoteDown.setActivated(false);
                    buttonVoteUp.setActivated(false);
                    buttonVoteDown.setColorFilter(Color.GRAY);
                    buttonVoteUp.setColorFilter(Color.GRAY);
                    break;

            }

            if (!model.isSaved()) {
                buttonStars.setImageDrawable(new IconicsDrawable(mContext, MaterialDesignIconic.Icon.gmi_star_border)
                        .color(Color.GRAY)
                        .sizeDp(mContext.getResources().getInteger(R.integer.icon_card_bottom))
                        .respectFontBounds(true));

            } else {
                buttonStars.setImageDrawable(new IconicsDrawable(mContext, MaterialDesignIconic.Icon.gmi_star)
                        .color(Color.RED)
                        .sizeDp(mContext.getResources().getInteger(R.integer.icon_card_bottom))
                        .respectFontBounds(true));

            }

            buttonStars.setBackgroundColor(Color.parseColor(model.getBackgroundColor()));
            //  todo ...livedata.... fix getAuthor
            if (model.getAuthor() != null) {
                if (Preference.getSessionUsername(mContext).compareTo(model.getAuthor()) != 0) {
                    buttonComments.setImageDrawable(new IconicsDrawable(mContext, MaterialDesignIconic.Icon.gmi_comment_outline)
                            .color(Color.GRAY)
                            .sizeDp(mContext.getResources().getInteger(R.integer.icon_card_bottom))
                            .respectFontBounds(true));
                    isDeleteComment = false;

                } else {
                    buttonComments.setImageDrawable(new IconicsDrawable(mContext, MaterialDesignIconic.Icon.gmi_comment_outline)
                            .color(Color.RED)
                            .sizeDp(mContext.getResources().getInteger(R.integer.icon_card_bottom))
                            .respectFontBounds(true));

                    isDeleteComment = true;
                }
            }

            buttonComments.setBackgroundColor(Color.parseColor(model.getBackgroundColor()));

            buttonOpenBrowser.setImageDrawable(new IconicsDrawable(mContext, MaterialDesignIconic.Icon.gmi_open_in_browser)
                    .color(Color.GRAY)
                    .sizeDp(mContext.getResources().getInteger(R.integer.icon_card_bottom))
                    .respectFontBounds(true));

            buttonOpenBrowser.setBackgroundColor(Color.parseColor(model.getBackgroundColor()));

            if ((model.isOnline())) {
                buttonVoteUp.setOnClickListener(view -> {

                    if (model.isLogged()) {
                        int dir = 1;

                        if (view.isActivated()) {
                            dir = 0;
                        }

                        int finalDir = dir;
                        new VoteExecute(new VoteExecute.OnRestCallBack() {
                            @Override
                            public void unexpectedError(Throwable tList) {

                            }

                            @Override
                            public void success(ResponseBody response, int code) {
                                if (code == 200) {
                                    mListener.vote(model.getPosition(), model.getScore(), true, finalDir, model.getCategory());

                                }

                            }

                        }, PermissionUtil.getToken(mContext), dir, model.getCategory())
                                .postData();
                    } else {
                        mListener.snackMsg(R.string.text_start_login);

                    }
                });


                buttonVoteDown.setOnClickListener(view -> {
                    if (model.isLogged()) {
                        int dir = -1;
                        if (view.isActivated()) {
                            dir = 0;
                        }
                        int finalDir = dir;
                        new VoteExecute(new VoteExecute.OnRestCallBack() {
                            @Override
                            public void success(ResponseBody response, int code) {
                                if (code == 200) {
                                    mListener.vote(model.getPosition(), model.getScore(), false, finalDir, model.getCategory());

                                }
                            }

                            @Override
                            public void unexpectedError(Throwable tList) {

                            }

                        }, PermissionUtil.getToken(mContext), dir, model.getCategory())
                                .postData();
                    } else {
                        mListener.snackMsg(R.string.text_start_login);

                    }

                });


                if (!model.isSaved()) {
                    buttonStars.setOnClickListener(view -> {
                        if (model.isLogged()) {
                            new PrefExecute(new PrefExecute.OnRestCallBack() {
                                @Override
                                public void success(ResponseBody response, int code) {
                                    if (code == 200) {
                                        String groupCategory = model.getCategory().substring(0, 2);
                                        int visibleStar = model.isSaved() ? 0 : 1;

                                        switch (groupCategory) {
                                            case "t3":
                                                new DataUtils(mContext, mDb).updateLocalDbStars(visibleStar, model.getCategory());
                                                mListener.stars(model.getPosition());
                                                break;

                                            case "t1":
                                                new DataUtils(mContext, mDb).updateLocalDbStars(visibleStar, model.getCategory());
                                                mListener.stars(model.getPosition());
                                                break;
                                        }


                                    }
                                }

                                @Override
                                public void unexpectedError(Throwable tList) {
                                }

                            }, PermissionUtil.getToken(mContext), model.getCategory()).postSaveData();

                        } else {
                            mListener.snackMsg(R.string.text_start_login);

                        }

                    });

                } else {

                    buttonStars.setOnClickListener(view -> new PrefExecute(new PrefExecute.OnRestCallBack() {
                        @Override
                        public void success(ResponseBody response, int code) {
                            if (code == 200) {
                                String groupCategory = model.getCategory().substring(0, 2);
                                int visibleStar = model.isSaved() ? 0 : 1;

                                switch (groupCategory) {
                                    case "t3":
                                        new DataUtils(mContext, mDb).updateLocalDbStars(visibleStar, model.getCategory());
                                        mListener.stars(model.getPosition());
                                        break;

                                    case "t1":
                                        new DataUtils(mContext, mDb).updateLocalDbStars(visibleStar, model.getCategory());
                                        mListener.stars(model.getPosition());
                                        break;
                                }


                            }
                        }

                        @Override
                        public void unexpectedError(Throwable tList) {
                        }

                    }, PermissionUtil.getToken(mContext), model.getCategory()).postUnSaveData());

                }


                if (!TextUtils.isEmpty(model.getLinkComment())) {
                    buttonOpenBrowser.setOnClickListener(view -> mContext.startActivity(new Intent(
                            Intent.ACTION_VIEW, Uri.parse(model.getLinkComment()))
                            .setFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT)));
                }

                buttonComments.setOnClickListener(view -> {

                            if (model.isLogged()) {
                                getUserComment(model.getTitle(), model.getCategory());

                            } else {
                                mListener.snackMsg(R.string.text_start_login);

                            }
                        }
                );

            }

        }
    }

    private void getUserComment(String title, String fullname) {
        final Dialog dialog = new Dialog(mContext);
        dialog.setContentView(R.layout.comment_input_dialog);

        int width = (int) (mContext.getResources().getDisplayMetrics().widthPixels * 0.95);
        int height = (int) (mContext.getResources().getDisplayMetrics().heightPixels * 0.6);

        Objects.requireNonNull(dialog.getWindow()).setLayout(width, height);
        dialog.show();

        TextView tvTitle = dialog.findViewById(R.id.title_comment_selector);
        EditText editText = dialog.findViewById(R.id.edit_comment_selector);
        Button cancel = dialog.findViewById(R.id.button_cancel_selector);
        Button button = dialog.findViewById(R.id.button_comment_selector);
        Button delete = dialog.findViewById(R.id.button_delete_selector);

        if (Preference.isNightMode(mContext)) {
            tvTitle.setBackgroundColor(Color.DKGRAY);

        }

        if (editText != null && button != null && cancel != null && tvTitle != null && delete != null) {

            if (isDeleteComment) {
                delete.setVisibility(View.VISIBLE);

                delete.setOnClickListener(view -> mListener.deleteComments(mItemView, dialog, fullname));

            } else {
                delete.setVisibility(View.GONE);

            }

            tvTitle.setText(title);

            cancel.setOnClickListener(view -> dialog.cancel());

            button.setOnClickListener(view -> {

                        String text = editText.getText().toString();
                        if (!TextUtils.isEmpty(text)) {
                            mListener.comments(mItemView, dialog, title, editText.getText().toString(), fullname);

                        } else {
                            mListener.snackMsg(R.string.editext_no_comment);

                        }

                    }

            );
        }

    }


    public interface OnSelector {
        void vote(int position, int score, boolean voteUp, int dir, String category);

        @SuppressWarnings("unused")
        void comments(View view, Dialog dialog, String title, String text, String fullname);

        void deleteComments(View view, Dialog dialog, String fullname);

        void snackMsg(int resource);

        void stars(int position);
    }
}

