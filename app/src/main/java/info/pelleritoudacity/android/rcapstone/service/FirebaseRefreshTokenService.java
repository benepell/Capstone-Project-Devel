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
