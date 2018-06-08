/*
 *
 * ______                _____ _
 * | ___ \              /  ___| |
 * | |_/ /___ __ _ _ __ \ `--.| |_ ___  _ __   ___
 * |    // __/ _` | '_ \ `--. \ __/ _ \| '_ \ / _ \
 * | |\ \ (_| (_| | |_) /\__/ / || (_) | | | |  __/
 * \_| \_\___\__,_| .__/\____/ \__\___/|_| |_|\___|
 *                | |
 *                |_|
 *
 * Copyright (C) 2018 Benedetto Pellerito
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package info.pelleritoudacity.android.rcapstone.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import info.pelleritoudacity.android.rcapstone.R;
import info.pelleritoudacity.android.rcapstone.rest.RevokeTokenExecute;
import info.pelleritoudacity.android.rcapstone.service.FirebaseRefreshTokenSync;
import info.pelleritoudacity.android.rcapstone.utility.Costants;
import info.pelleritoudacity.android.rcapstone.utility.PrefManager;

public class LogoutActivity extends BaseActivity
        implements RevokeTokenExecute.RestRevokeCode {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        setLayoutResource(R.layout.activity_logout);
        super.onCreate(savedInstanceState);

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
                R.string.pref_session_expired,
                R.string.pref_time_token,
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

