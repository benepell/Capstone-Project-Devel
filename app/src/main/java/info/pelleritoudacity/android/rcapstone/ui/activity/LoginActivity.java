package info.pelleritoudacity.android.rcapstone.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.webkit.WebView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import info.pelleritoudacity.android.rcapstone.R;
import info.pelleritoudacity.android.rcapstone.model.Reddit;
import info.pelleritoudacity.android.rcapstone.model.RedditAboutMe;
import info.pelleritoudacity.android.rcapstone.model.RedditAccessToken;
import info.pelleritoudacity.android.rcapstone.rest.AboutMeExecute;
import info.pelleritoudacity.android.rcapstone.rest.LoginExecute;
import info.pelleritoudacity.android.rcapstone.utility.Costants;
import info.pelleritoudacity.android.rcapstone.utility.PrefManager;
import timber.log.Timber;

public class LoginActivity extends BaseActivity
        implements LoginExecute.RestToken, AboutMeExecute.RestToken {

    @SuppressWarnings({"WeakerAccess", "CanBeFinal", "unused"})
    @BindView(R.id.webview)
    WebView mWebView;

    private boolean isStartWebView = true;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setLayoutResource(R.layout.activity_login);
        super.onCreate(savedInstanceState);

        ButterKnife.bind(this);
        Timber.plant(new Timber.DebugTree());

        if (TextUtils.isEmpty(PrefManager.getStringPref(getApplicationContext(), R.string.pref_session_access_token))) {
            loadWebView();
        } else {
//            startActivity(new Intent(this, MainActivity.class));
        new AboutMeExecute(PrefManager.getStringPref(getApplicationContext(),R.string.pref_session_access_token)).loginData(this);
        }
    }

    public void loadWebView() {

        Uri.Builder builder = new Uri.Builder();

        builder
                .scheme("https")
                .authority(Costants.REDDIT_AUTH_URL)
                .appendPath("login.compact")
                .appendQueryParameter("dest", new Uri.Builder()

                        .scheme("https")
                        .authority(Costants.REDDIT_AUTH_URL)
                        .appendPath("api")
                        .appendPath("v1")
                        .appendPath("authorize.compact")
                        .appendQueryParameter("client_id", Costants.REDDIT_CLIENT_ID)
                        .appendQueryParameter("response_type", "code")
                        .appendQueryParameter("state", Costants.REDDIT_STATE_RANDOM)

                        .appendQueryParameter("redirect_uri", new Uri.Builder()

                                .scheme("http")
                                .authority(Costants.REDDIT_ABOUT_URL)
                                .appendPath("my_redirect").build().toString()
                        )

                        .appendQueryParameter("duration", "permanent")
                        .appendQueryParameter("scope", "identity").build().toString()
                );

        String url = builder.build().toString();
        Timber.d("loaduri %s", url);

        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.loadUrl(url);

    }

    @Override
    protected void onResume() {
        super.onResume();
        if ((getIntent() != null) && (getIntent().getAction() != null) && (getIntent().getAction().equals(Intent.ACTION_VIEW))) {
            Uri uri = getIntent().getData();
            if (uri.getQueryParameter("error") != null) {
                String error = uri.getQueryParameter("error");
                Timber.e("An error has occurred :%s ", error);
            } else {
                String state = uri.getQueryParameter("state");
                if (state.equals(Costants.REDDIT_STATE_RANDOM)) {
                    String code = uri.getQueryParameter("code");
                    if (!TextUtils.isEmpty(code)) {
                        new LoginExecute(code).loginData(this);
                    }
                }
            }
        }
    }

    @Override
    public void onRestToken(RedditAccessToken listenerData) {
        if (listenerData != null) {
            String strAccessToken = listenerData.getAccess_token();
            if (!TextUtils.isEmpty(strAccessToken)) {
                PrefManager.putStringPref(getApplicationContext(), R.string.pref_session_access_token, strAccessToken);

               /* Intent intent = new Intent(this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NO_ANIMATION);
                intent.putExtra(Costants.EXTRA_LOGIN_SUCCESS, true);
                startActivity(intent);*/
            }
        }
    }

    @Override
    public void onErrorToken(Throwable t) {
        Timber.e("Error Login %s", t.getMessage());
    }

    @Override
    public void onRestAboutMe(RedditAboutMe listenerData) {
        if (listenerData != null) {
            String name = listenerData.getName();
            if(!TextUtils.isEmpty(name)){
                Timber.d("benName %s" ,name);
            }
        }
    }

    @Override
    public void onErrorAboutMe(Throwable t) {

    }
}

