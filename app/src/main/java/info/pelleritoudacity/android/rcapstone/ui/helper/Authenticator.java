package info.pelleritoudacity.android.rcapstone.ui.helper;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;

import info.pelleritoudacity.android.rcapstone.R;
import info.pelleritoudacity.android.rcapstone.data.rest.RefreshTokenExecute;
import info.pelleritoudacity.android.rcapstone.service.FirebaseRefreshTokenSync;
import info.pelleritoudacity.android.rcapstone.ui.activity.LoginActivity;
import info.pelleritoudacity.android.rcapstone.utility.Costant;
import info.pelleritoudacity.android.rcapstone.utility.PrefManager;
import info.pelleritoudacity.android.rcapstone.utility.Preference;

import static info.pelleritoudacity.android.rcapstone.utility.SessionUtil.getRedditSessionExpired;

public class Authenticator {

    private final Context mContext;


    public Authenticator(Context context) {
        mContext = context;
    }

    public void initLogin(CoordinatorLayout container, Intent intent) {

        if (intent != null) {
            int stateLogin = intent.getIntExtra(Costant.EXTRA_LOGIN_SUCCESS, 0);
            boolean isLogged = false;
            switch (stateLogin) {
                case Costant.PROCESS_LOGIN_OK:
                    isLogged = true;
                    Snackbar.make(container,
                            R.string.text_login_success, Snackbar.LENGTH_LONG).show();

                    break;
                case Costant.PROCESS_LOGOUT_OK:
                    isLogged = false;
                    Snackbar.make(container,
                            R.string.text_logout_success, Snackbar.LENGTH_LONG).show();

                    break;
                case Costant.PROCESS_LOGIN_ERROR:
                case Costant.PROCESS_LOGOUT_ERROR:
                    isLogged = false;
                    PrefManager.clearPreferenceLogin(mContext);

                    Snackbar snackbar = Snackbar
                            .make(container, R.string.text_logout_error, Snackbar.LENGTH_LONG)
                            .setAction(R.string.text_log_in, view -> mContext.startActivity(new Intent(mContext, LoginActivity.class)));
                    snackbar.show();

                    break;
            }
            loginFirebaseDispatcher(mContext, isLogged);
        }
    }

    private void loginFirebaseDispatcher(Context context, boolean logged) {

        if (logged) {

            int redditSessionExpired = getRedditSessionExpired(context);
            if (redditSessionExpired <= Costant.SESSION_TIMEOUT_DEFAULT) {

                String strRefreshToken = Preference.getSessionRefreshToken(mContext);
                new RefreshTokenExecute(strRefreshToken).syncData(context);

            } else {
                FirebaseRefreshTokenSync.stopLogin(context);

            }
        }
    }


}
