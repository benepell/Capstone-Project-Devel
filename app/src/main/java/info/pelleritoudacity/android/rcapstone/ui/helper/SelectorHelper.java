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
import info.pelleritoudacity.android.rcapstone.data.rest.PrefExecute;
import info.pelleritoudacity.android.rcapstone.data.rest.VoteExecute;
import info.pelleritoudacity.android.rcapstone.ui.activity.SubRedditActivity;
import info.pelleritoudacity.android.rcapstone.utility.NetworkUtil;
import info.pelleritoudacity.android.rcapstone.utility.PermissionUtil;

public class SelectorHelper {
    private final Context mContext;

    public SelectorHelper(Context context) {
        mContext = context;
    }

    public void cardBottomLink(ImageButton[] arrayButton, String strBackgroundColor, String linkComments, String nameReddit, boolean isSaved) {

        boolean isOnline = NetworkUtil.isOnline(mContext);
        boolean isLogin = PermissionUtil.isLogged(mContext);

        if ((arrayButton != null) && (arrayButton.length == 5)) {

            ImageButton buttonVoteUp = arrayButton[0];
            ImageButton buttonVoteDown = arrayButton[1];
            ImageButton buttonStars = arrayButton[2];
            ImageButton buttonComments = arrayButton[3];
            ImageButton buttonOpenBrowser = arrayButton[4];

            if (strBackgroundColor == null) {
                strBackgroundColor = "#FFFFFF";
            }

            buttonVoteUp.setBackgroundColor(Color.parseColor(strBackgroundColor));
            buttonVoteUp.setImageDrawable(new IconicsDrawable(mContext, MaterialDesignIconic.Icon.gmi_thumb_up)
                    .color(Color.GRAY)
                    .sizeDp(mContext.getResources().getInteger(R.integer.icon_card_bottom))
                    .respectFontBounds(true));


            buttonVoteDown.setBackgroundColor(Color.parseColor(strBackgroundColor));
            buttonVoteDown.setImageDrawable(new IconicsDrawable(mContext, MaterialDesignIconic.Icon.gmi_thumb_down)
                    .color(Color.GRAY)
                    .sizeDp(mContext.getResources().getInteger(R.integer.icon_card_bottom))
                    .respectFontBounds(true));

            if (!isSaved) {
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

            buttonStars.setBackgroundColor(Color.parseColor(strBackgroundColor));

            buttonComments.setImageDrawable(new IconicsDrawable(mContext, MaterialDesignIconic.Icon.gmi_comment_outline)
                    .color(Color.GRAY)
                    .sizeDp(mContext.getResources().getInteger(R.integer.icon_card_bottom))
                    .respectFontBounds(true));

            buttonComments.setBackgroundColor(Color.parseColor(strBackgroundColor));

            buttonOpenBrowser.setImageDrawable(new IconicsDrawable(mContext, MaterialDesignIconic.Icon.gmi_open_in_browser)
                    .color(Color.GRAY)
                    .sizeDp(mContext.getResources().getInteger(R.integer.icon_card_bottom))
                    .respectFontBounds(true));

            buttonOpenBrowser.setBackgroundColor(Color.parseColor(strBackgroundColor));

            if ((isOnline) && (isLogin)) {
                buttonVoteUp.setOnClickListener(view -> {
                    String vote = "1";

                    if (view.isActivated()) {
                        vote = "0";
                    }

                    String finalVote = vote;
                    new VoteExecute(PermissionUtil.getToken(mContext), vote, nameReddit)
                            .postData(new VoteExecute.RestAccessToken() {
                                @Override
                                public void onRestVote(int responseCode) {
                                    if (responseCode == 200) {
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

                                @Override
                                public void onErrorVote(Throwable t) {
                                }
                            });
                });

                buttonVoteDown.setOnClickListener(view -> {
                    String vote = "-1";

                    if (view.isActivated()) {
                        vote = "0";
                    }

                    String finalVote = vote;
                    new VoteExecute(PermissionUtil.getToken(mContext), vote, nameReddit)
                            .postData(new VoteExecute.RestAccessToken() {
                                @Override
                                public void onRestVote(int responseCode) {
                                    if (responseCode == 200) {
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
                                public void onErrorVote(Throwable t) {

                                }
                            });


                });

                buttonStars.setOnClickListener(view -> {
                    if (!isSaved) {
                        new PrefExecute(PermissionUtil.getToken(mContext), nameReddit).postSaveData(new PrefExecute.RestAccessToken() {
                            @Override
                            public void onRestPref(int responseCode) {

                                if (responseCode == 200) {
                                    new DataUtils(mContext).updateLocalDbStars(1, nameReddit);
                                    mContext.startActivity(new Intent(mContext, SubRedditActivity.class)
                                            .setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NO_ANIMATION));
                                }
                            }

                            @Override
                            public void onErrorPref(Throwable t) {
                            }
                        });
                    } else {
                        new PrefExecute(PermissionUtil.getToken(mContext), nameReddit).postUnSaveData(new PrefExecute.RestAccessToken() {
                            @Override
                            public void onRestPref(int responseCode) {
                                if (responseCode == 200) {
                                    new DataUtils(mContext).updateLocalDbStars(0, nameReddit);
                                    mContext.startActivity(new Intent(mContext, SubRedditActivity.class)
                                            .setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NO_ANIMATION));

                                }
                            }

                            @Override
                            public void onErrorPref(Throwable t) {
                            }
                        });
                    }


                });

                if (!TextUtils.isEmpty(linkComments)) {
                    buttonOpenBrowser.setOnClickListener(view -> mContext.startActivity(new Intent(
                            Intent.ACTION_VIEW, Uri.parse(linkComments))
                            .setFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT)));
                }

            }
        }
    }
}
