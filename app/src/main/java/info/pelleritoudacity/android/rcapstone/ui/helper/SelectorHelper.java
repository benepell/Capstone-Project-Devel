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
import info.pelleritoudacity.android.rcapstone.ui.activity.MainActivity;
import info.pelleritoudacity.android.rcapstone.utility.NetworkUtil;
import info.pelleritoudacity.android.rcapstone.utility.PermissionUtil;
import okhttp3.ResponseBody;

public class SelectorHelper {
    private final Context mContext;

    public SelectorHelper(Context context) {
        mContext = context;
    }

    public void cardBottomLink(CardBottomModel model){

        model.setOnline(NetworkUtil.isOnline(mContext));
        model.setLogged(PermissionUtil.isLogged(mContext));

        if ((model.getArrayButton() != null) && (model.getArrayButton().length == 5)) {

            ImageButton buttonVoteUp = model.getArrayButton()[0];
            ImageButton buttonVoteDown = model.getArrayButton()[1];
            ImageButton buttonStars = model.getArrayButton()[2];
            ImageButton buttonComments = model.getArrayButton()[3];
            ImageButton buttonOpenBrowser = model.getArrayButton()[4];

            if (model.getBackgroundColor() == null) {
              model.setBackgroundColor("#FFFFFF");              }

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
                    String vote = "1";

                    if (view.isActivated()) {
                        vote = "0";
                    }

                    String finalVote = vote;
                    new VoteExecute(new VoteExecute.OnRestCallBack() {
                        @Override
                        public void unexpectedError(Throwable tList) {

                        }

                        @Override
                        public void success(ResponseBody response, int code) {
                            if (code == 200) {
                                buttonVoteDown.setColorFilter(Color.GRAY);
                                buttonVoteUp.setActivated(true);
                                if (finalVote.equals("1")) {
                                    DataUtils dataUtils = new DataUtils(mContext);
                                    buttonVoteUp.setColorFilter(Color.BLUE);

                                } else {
                                    buttonVoteUp.setActivated(false);
                                    buttonVoteUp.setColorFilter(Color.GRAY);

                                }

                            }

                        }

                    }, PermissionUtil.getToken(mContext), vote, model.getCategory())
                            .postData();
                });

                buttonVoteDown.setOnClickListener(view -> {
                    String vote = "-1";

                    if (view.isActivated()) {
                        vote = "0";
                    }

                    String finalVote = vote;
                    new VoteExecute(new VoteExecute.OnRestCallBack() {
                        @Override
                        public void success(ResponseBody response,int code) {
                            if (code == 200) {
                                buttonVoteUp.setColorFilter(Color.GRAY);
                                buttonVoteDown.setActivated(true);
                                if (finalVote.equals("-1")) {
                                    DataUtils dataUtils = new DataUtils(mContext);
                                    buttonVoteDown.setColorFilter(Color.BLUE);
                                } else {
                                    buttonVoteDown.setActivated(false);
                                    buttonVoteDown.setColorFilter(Color.GRAY);

                                }

                            }
                        }

                        @Override
                        public void unexpectedError(Throwable tList) {

                        }


                    }, PermissionUtil.getToken(mContext), vote,model.getCategory())
                            .postData();
                });

                buttonStars.setOnClickListener(view -> {
                    if (!model.isSaved()) {

                        new PrefExecute(new PrefExecute.OnRestCallBack() {
                            @Override
                            public void success(ResponseBody response,int code) {
                                if (code == 200) {
                                    new DataUtils(mContext).updateLocalDbStars(1, model.getCategory());
                                    mContext.startActivity(new Intent(mContext, MainActivity.class)
                                            .setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_CLEAR_TASK |
                                                    Intent.FLAG_ACTIVITY_NO_ANIMATION));
                                }
                            }

                            @Override
                            public void unexpectedError(Throwable tList) {

                            }

                        }, PermissionUtil.getToken(mContext), model.getCategory()).postSaveData();


                    } else {

                        new PrefExecute(new PrefExecute.OnRestCallBack() {
                            @Override
                            public void success(ResponseBody response,int code) {
                                if (code == 200) {
                                    new DataUtils(mContext).updateLocalDbStars(0, model.getCategory());
                                    mContext.startActivity(new Intent(mContext, MainActivity.class)
                                            .setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_CLEAR_TASK |
                                                    Intent.FLAG_ACTIVITY_NO_ANIMATION));

                                }
                            }

                            @Override
                            public void unexpectedError(Throwable tList) {

                            }


                        }, PermissionUtil.getToken(mContext),model.getCategory()).postUnSaveData();

                    }
                });

                if (!TextUtils.isEmpty(model.getLinkComment())) {
                    buttonOpenBrowser.setOnClickListener(view -> mContext.startActivity(new Intent(
                            Intent.ACTION_VIEW, Uri.parse(model.getLinkComment()))
                            .setFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT)));
                }


            }

        }
    }
}

