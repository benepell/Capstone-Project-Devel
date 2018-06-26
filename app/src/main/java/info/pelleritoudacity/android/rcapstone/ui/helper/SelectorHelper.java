package info.pelleritoudacity.android.rcapstone.ui.helper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.nfc.Tag;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageButton;
import android.widget.Toast;

import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.material_design_iconic_typeface_library.MaterialDesignIconic;

import java.util.List;

import info.pelleritoudacity.android.rcapstone.R;
import info.pelleritoudacity.android.rcapstone.data.DataUtils;
import info.pelleritoudacity.android.rcapstone.rest.VoteExecute;
import info.pelleritoudacity.android.rcapstone.utility.Costants;
import info.pelleritoudacity.android.rcapstone.utility.PermissionUtils;
import info.pelleritoudacity.android.rcapstone.utility.TextUtil;
import timber.log.Timber;

public class SelectorHelper {
    Context mContext;

    public SelectorHelper(Context context) {
        mContext = context;
    }

    public void cardBottomLink(ImageButton[] arrayButton, String strBackgroundColor, String linkComments, String nameReddit) {

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

            buttonVoteUp.setOnClickListener(view -> {
                if (PermissionUtils.isLogged(mContext)) {
                    String vote = "1";

                    if (view.isActivated()) {
                        vote = "0";
                    }

                    String finalVote = vote;
                    new VoteExecute(PermissionUtils.getToken(mContext), vote, nameReddit)
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


                } else {
                    Toast.makeText(mContext, "Please Login ", Toast.LENGTH_LONG).show();
                }
            });

            buttonVoteDown.setBackgroundColor(Color.parseColor(strBackgroundColor));
            buttonVoteDown.setImageDrawable(new IconicsDrawable(mContext, MaterialDesignIconic.Icon.gmi_thumb_down)
                    .color(Color.GRAY)
                    .sizeDp(mContext.getResources().getInteger(R.integer.icon_card_bottom))
                    .respectFontBounds(true));

            buttonVoteDown.setOnClickListener(view -> {
                if (PermissionUtils.isLogged(mContext)) {
                    String vote = "-1";

                    if (view.isActivated()) {
                        vote = "0";
                    }

                    String finalVote = vote;
                    new VoteExecute(PermissionUtils.getToken(mContext), vote, nameReddit)
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

                } else {
                    Toast.makeText(mContext, "Please Login ", Toast.LENGTH_LONG).show();
                }
            });

            buttonStars.setImageDrawable(new IconicsDrawable(mContext, MaterialDesignIconic.Icon.gmi_star_border)
                    .color(Color.GRAY)
                    .sizeDp(mContext.getResources().getInteger(R.integer.icon_card_bottom))
                    .respectFontBounds(true));

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

            if (!TextUtils.isEmpty(linkComments)) {
                buttonOpenBrowser.setOnClickListener(view -> mContext.startActivity(new Intent(
                        Intent.ACTION_VIEW, Uri.parse(linkComments))
                        .setFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT)));
            }
        }
    }

}
