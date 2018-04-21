package info.pelleritoudacity.android.rcapstone.ui.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import info.pelleritoudacity.android.rcapstone.R;
import info.pelleritoudacity.android.rcapstone.model.Login;
import info.pelleritoudacity.android.rcapstone.rest.LoginExecute;
import info.pelleritoudacity.android.rcapstone.utility.Costants;
import info.pelleritoudacity.android.rcapstone.utility.PrefManager;
import timber.log.Timber;

public class LoginActivity extends BaseActivity
        implements LoginExecute.RestLogin {

    @SuppressWarnings({"WeakerAccess", "CanBeFinal", "unused"})
    @BindView(R.id.input_username)
    EditText mUsernameText;

    @SuppressWarnings({"WeakerAccess", "CanBeFinal", "unused"})
    @BindView(R.id.input_password)
    EditText mPasswordText;

    @SuppressWarnings({"WeakerAccess", "CanBeFinal", "unused"})
    @BindView(R.id.btn_login)
    Button mLoginButton;

    @SuppressWarnings({"WeakerAccess", "CanBeFinal", "unused"})
    @BindView(R.id.link_signup)
    TextView mSignupLink;

    private String mUsername = "benepell";
    private String mModHash;
    private String mCookie;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setLayoutResource(R.layout.activity_login);
        super.onCreate(savedInstanceState);

        ButterKnife.bind(this);
        Timber.plant(new Timber.DebugTree());


        mLoginButton.setOnClickListener(v -> login());

        mSignupLink.setOnClickListener(v -> {

            // Start the Signup activity
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivityForResult(intent, Costants.LOGIN_REQUEST_SIGNUP);
            finish();
        });


    }

    public void login() {
        if (!validate()) {
            onLoginFailed();
            return;
        }

        mLoginButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this,
                R.style.AppTheme_NoActionBar);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage(getString(R.string.progress_login_text));
        progressDialog.show();

        mUsername = mUsernameText.getText().toString();
        String password = mPasswordText.getText().toString();

        new LoginExecute(mUsername, password).loginData(this);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Costants.LOGIN_REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {

                // TODO: Implement successful signup logic here
                // By default we just finish the Activity and log them in automatically
                this.finish();
            }
        }
    }

    @Override
    public void onBackPressed() {
        // Disable going back to the MainActivity
        moveTaskToBack(true);
    }

    public void onLoginSuccess(String username, String modHash, String cookie) {
        mLoginButton.setEnabled(true);
        PrefManager.putStringPref(getApplicationContext(), R.string.pref_session_username, username);
        PrefManager.putStringPref(getApplicationContext(), R.string.pref_session_modhash, modHash);
        PrefManager.putStringPref(getApplicationContext(), R.string.pref_session_cookie, cookie);
        Timber.d("login success " + username + " " + modHash + " " + cookie);
        finish();
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();
        mLoginButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String username = mUsernameText.getText().toString();
        String password = mPasswordText.getText().toString();

        if (TextUtils.isEmpty(username) && (username.length() < 6)) {
            mUsernameText.setError("enter a valid username ");
            valid = false;
        } else {
            mUsernameText.setError(null);
        }

        if (password.isEmpty() || password.length() < 6) {
            mPasswordText.setError("between > 6 alphanumeric characters");
            valid = false;
        } else {
            mPasswordText.setError(null);
        }

        return valid;
    }

    @Override
    public void onRestLogin(Login listenerData) {

        if (listenerData.getJson().getData() != null) {
            mModHash = listenerData.getJson().getData().getModhash();
            mCookie = listenerData.getJson().getData().getCookie();
        }

        Timber.d("login %s", listenerData.getJson().toString());

        if (!TextUtils.isEmpty(mUsername) && (!TextUtils.isEmpty(mModHash) && (!TextUtils.isEmpty(mCookie)))) {
            onLoginSuccess(mUsername, mModHash, mCookie);
        } else {
            onLoginFailed();
        }
    }

    @Override
    public void onErrorLogin(Throwable t) {
        Timber.d("login error %s ", t.getMessage());
    }
}

