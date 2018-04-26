package info.pelleritoudacity.android.rcapstone.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.firebase.jobdispatcher.FirebaseJobDispatcher;

import butterknife.ButterKnife;
import info.pelleritoudacity.android.rcapstone.R;
import info.pelleritoudacity.android.rcapstone.rest.RevokeTokenExecute;
import info.pelleritoudacity.android.rcapstone.service.FirebaseRefreshTokenService;
import info.pelleritoudacity.android.rcapstone.service.FirebaseRefreshTokenSync;
import info.pelleritoudacity.android.rcapstone.utility.Costants;
import info.pelleritoudacity.android.rcapstone.utility.PrefManager;
import timber.log.Timber;

public class LogoutActivity extends BaseActivity
        implements RevokeTokenExecute.RestRevokeCode {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        setLayoutResource(R.layout.activity_logout);
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        Timber.plant(new Timber.DebugTree());

        String token = PrefManager.getStringPref(getApplicationContext(), R.string.pref_session_access_token);
        if (!TextUtils.isEmpty(token)) {
            new RevokeTokenExecute(token, Costants.REDDIT_ACCESS_TOKEN).RevokeTokenData(this);
        }
    }


    @Override
    public void onRestCode(Integer listenerData) {
        if (listenerData == Costants.REDDIT_REVOKE_SUCCESS) {
            clearPreference();
            openHomeActivity();
            stopRefreshTokenService();
        }
    }

    private void clearPreference() {
        int[] prefStrArrays = {
                R.string.pref_login_start,
                R.string.pref_session_access_token,
                R.string.pref_login_name,
                R.string.pref_login_over18
        };

        for (int pref : prefStrArrays) {
            getApplicationContext().getSharedPreferences(getApplicationContext().getString(pref), 0).edit().clear().apply();
        }

    }

    public void openHomeActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NO_ANIMATION);
        intent.putExtra(Costants.EXTRA_LOGOUT_SUCCESS, true);
        startActivity(intent);
    }

    private  void stopRefreshTokenService(){
        FirebaseRefreshTokenSync.stopJobRefreshToken(getApplicationContext(),Costants.TOKEN_SYNC_TAG);
    }
}

