package info.pelleritoudacity.android.rcapstone.service;

import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;

import info.pelleritoudacity.android.rcapstone.R;
import info.pelleritoudacity.android.rcapstone.rest.RefreshTokenExecute;
import info.pelleritoudacity.android.rcapstone.utility.PrefManager;

public class FirebaseRefreshTokenService extends JobService {

    private RefreshTokenExecute restRefreshTokenExecute;

    @Override
    public boolean onStartJob(JobParameters job) {
        String refreshToken = PrefManager.getStringPref(getApplicationContext(), R.string.pref_session_refresh_token);
        restRefreshTokenExecute = new RefreshTokenExecute(refreshToken);
        restRefreshTokenExecute.syncData(getApplicationContext());
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters job) {
        if (restRefreshTokenExecute != null) {
            restRefreshTokenExecute.cancelRequest();
        }
        return true;
    }
}
