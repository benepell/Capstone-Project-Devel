package info.pelleritoudacity.android.rcapstone.ui.helper;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.text.TextUtils;
import android.widget.ImageButton;

import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.material_design_iconic_typeface_library.MaterialDesignIconic;

import info.pelleritoudacity.android.rcapstone.R;
import info.pelleritoudacity.android.rcapstone.data.db.util.DataUtils;
import info.pelleritoudacity.android.rcapstone.data.model.ui.CardBottomModel;
import info.pelleritoudacity.android.rcapstone.data.rest.PrefExecute;
import info.pelleritoudacity.android.rcapstone.data.rest.VoteExecute;
import info.pelleritoudacity.android.rcapstone.utility.NetworkUtil;
import info.pelleritoudacity.android.rcapstone.utility.PermissionUtil;
import okhttp3.ResponseBody;
import timber.log.Timber;

public class SelectorHelper {
    private final Context mContext;
    private final OnSelector mListener;

    public SelectorHelper(OnSelector listener, Context context) {

        mContext = context;
        mListener = listener;
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

            buttonComments.setImageDrawable(new IconicsDrawable(mContext, MaterialDesignIconic.Icon.gmi_comment_outline)
                    .color(Color.GRAY)
                    .sizeDp(mContext.getResources().getInteger(R.integer.icon_card_bottom))
                    .respectFontBounds(true));

            buttonComments.setBackgroundColor(Color.parseColor(model.getBackgroundColor()));

            buttonOpenBrowser.setImageDrawable(new IconicsDrawable(mContext, MaterialDesignIconic.Icon.gmi_open_in_browser)
                    .color(Color.GRAY)
                    .sizeDp(mContext.getResources().getInteger(R.integer.icon_card_bottom))
                    .respectFontBounds(true));

            buttonOpenBrowser.setBackgroundColor(Color.parseColor(model.getBackgroundColor()));

            if ((model.isOnline()) && (model.isLogged())) {
                buttonVoteUp.setOnClickListener(view -> {

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
                });

                buttonVoteDown.setOnClickListener(view -> {

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
                });


                if (!model.isSaved()) {
                    buttonStars.setOnClickListener(view -> new PrefExecute(new PrefExecute.OnRestCallBack() {
                        @Override
                        public void success(ResponseBody response, int code) {
                            if (code == 200) {
                                Uri uri;
                                String groupCategory = model.getCategory().substring(0, 2);
                                int visibleStar = model.isSaved() ? 0 : 1;

                                switch (groupCategory) {
                                    case "t3":
                                        uri = info.pelleritoudacity.android.rcapstone.data.db.Contract.T3dataEntry.CONTENT_URI;
                                        new DataUtils(mContext).updateLocalDbStars(uri, visibleStar, model.getCategory());
                                        mListener.stars(model.getPosition());
                                        break;

                                    case "t1":
                                        uri = info.pelleritoudacity.android.rcapstone.data.db.Contract.T1dataEntry.CONTENT_URI;
                                        new DataUtils(mContext).updateLocalDbStars(uri, visibleStar, model.getCategory());
                                        mListener.stars(model.getPosition());
                                        break;
                                }


                            }
                        }

                        @Override
                        public void unexpectedError(Throwable tList) {
                        }

                    }, PermissionUtil.getToken(mContext), model.getCategory()).postSaveData());

                } else {

                    buttonStars.setOnClickListener(view -> new PrefExecute(new PrefExecute.OnRestCallBack() {
                        @Override
                        public void success(ResponseBody response, int code) {
                            if (code == 200) {
                                Uri uri;
                                String groupCategory = model.getCategory().substring(0, 2);
                                int visibleStar = model.isSaved() ? 0 : 1;

                                switch (groupCategory) {
                                    case "t3":
                                        uri = info.pelleritoudacity.android.rcapstone.data.db.Contract.T3dataEntry.CONTENT_URI;
                                        new DataUtils(mContext).updateLocalDbStars(uri, visibleStar, model.getCategory());
                                        mListener.stars(model.getPosition());
                                        break;

                                    case "t1":
                                        uri = info.pelleritoudacity.android.rcapstone.data.db.Contract.T1dataEntry.CONTENT_URI;
                                        new DataUtils(mContext).updateLocalDbStars(uri, visibleStar, model.getCategory());
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


            }

        }
    }

    public interface OnSelector {
        void vote(int position, int score, boolean voteUp, int dir, String category);
        void stars(int position);
    }
}

