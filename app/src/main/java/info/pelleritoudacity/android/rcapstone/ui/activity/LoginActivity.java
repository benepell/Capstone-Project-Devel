package info.pelleritoudacity.android.rcapstone.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;

import butterknife.ButterKnife;
import info.pelleritoudacity.android.rcapstone.R;
import info.pelleritoudacity.android.rcapstone.model.RedditToken;
import info.pelleritoudacity.android.rcapstone.rest.AccessTokenExecute;
import info.pelleritoudacity.android.rcapstone.rest.RefreshTokenExecute;
import info.pelleritoudacity.android.rcapstone.utility.Costants;
import info.pelleritoudacity.android.rcapstone.utility.PrefManager;
import timber.log.Timber;

public class LoginActivity extends BaseActivity
        implements AccessTokenExecute.RestAccessToken, RefreshTokenExecute.RestRefreshToken {

    private boolean isStartRefresh;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setLayoutResource(R.layout.activity_login);
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        Timber.plant(new Timber.DebugTree());

        Intent intent = getIntent();


        if (intent != null) {
            isStartRefresh = intent.getBooleanExtra(Costants.EXTRA_TOKEN_REFRESH, false);
            if (isStartRefresh) {
                inizializeTokenRefresh();
            }
        }


    }

    public void loadUrl() {

        Uri.Builder builder = new Uri.Builder();

        builder
                .scheme("https")
                .authority(Costants.REDDIT_AUTH_URL)
                .appendPath("api")
                .appendPath("v1")
                .appendPath("authorize.compact")
                .appendQueryParameter("client_id", Costants.REDDIT_CLIENT_ID)
                .appendQueryParameter("response_type", "code")
                .appendQueryParameter("state", Costants.REDDIT_STATE_RANDOM)

                .appendQueryParameter("redirect_uri",
                        new Uri.Builder()

                                .scheme("http")
                                .authority(Costants.REDDIT_ABOUT_URL)
                                .appendPath("my_redirect").build().toString()
                )

                .appendQueryParameter("duration", "permanent")
                .appendQueryParameter("scope", "identity");

        String url = builder.build().toString();
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!isStartRefresh) {
            if ((getIntent() != null) && (getIntent().getAction() != null) && (getIntent().getAction().equals(Intent.ACTION_VIEW))) {
                Uri uri = getIntent().getData();
                if (uri.getQueryParameter("error") != null) {
                    String error = uri.getQueryParameter("error");
                    Timber.e("An error has occurred :%s ", error);
                } else {
                    String state = uri.getQueryParameter("state");
                    if (state.equals(Costants.REDDIT_STATE_RANDOM)) {
                        String code = uri.getQueryParameter("code");
                        PrefManager.putStringPref(getApplicationContext(), R.string.pref_session_cookie, code);
                        if (!TextUtils.isEmpty(code)) {
                            new AccessTokenExecute(code).loginData(this);

                        }
                    }
                }
            } else {
                String accessToken = PrefManager.getStringPref(getApplicationContext(), R.string.pref_session_access_token);
                if (TextUtils.isEmpty(accessToken)) {
                    loadUrl();
                    finish();
                }
            }
        }
    }

    @Override
    public void onRestAccessToken(RedditToken listenerData) {
        if (listenerData != null) {
            String strAccessToken = listenerData.getAccess_token();
            String strRefreshToken = listenerData.getRefresh_token();
            if (!TextUtils.isEmpty(strAccessToken) && !TextUtils.isEmpty(strRefreshToken)) {
                PrefManager.putStringPref(getApplicationContext(), R.string.pref_session_access_token, strAccessToken);
                PrefManager.putStringPref(getApplicationContext(), R.string.pref_session_refresh_token, strRefreshToken);

                PrefManager.putBoolPref(getApplicationContext(), R.string.pref_login_start, true);
                openHomeActivity();
            }
        }
    }

    @Override
    public void onErrorAccessToken(Throwable t) {
        Timber.e("Error Login %s", t.getMessage());
    }


    public void openHomeActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NO_ANIMATION);
        intent.putExtra(Costants.EXTRA_LOGIN_SUCCESS, true);
        startActivity(intent);
    }

    @Override
    public void onRestRefreshToken(RedditToken listenerData) {
        String newRefreshToken = listenerData.getRefresh_token();
        String newAccessToken = listenerData.getAccess_token();

        PrefManager.putStringPref(getApplicationContext(), R.string.pref_session_access_token, newAccessToken);
        PrefManager.putStringPref(getApplicationContext(), R.string.pref_session_refresh_token, newRefreshToken);
        Timber.d("inizialize Refresh Token done %s", newAccessToken);
    }

    @Override
    public void onErrorRefreshToken(Throwable t) {

    }

    private void inizializeTokenRefresh() {
        String refreshToken = PrefManager.getStringPref(getApplicationContext(), R.string.pref_session_refresh_token);
        if (!TextUtils.isEmpty(refreshToken)) {
            new RefreshTokenExecute(refreshToken).loginData(this);
        } else {
            Timber.e("Refresh Token is Empty");
        }
    }
}

