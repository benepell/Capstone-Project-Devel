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
package info.pelleritoudacity.android.rcapstone.utility;

import android.content.Context;

import info.pelleritoudacity.android.rcapstone.R;

public class SessionUtil {

    public static int getRedditSessionExpired(Context context) {
        if (context != null) {
            Long now = System.currentTimeMillis();
            Long lastTimeLogin = PrefManager.getLongPref(context, R.string.pref_time_token);
            Long secondTime = (now - lastTimeLogin) / 1000;
            int expiredRedditAuth = PrefManager.getIntPref(context, R.string.pref_session_expired);
            int sessionTimeout = Costant.SESSION_TIMEOUT_DEFAULT;
            if ((now > 0) && (lastTimeLogin > 0) && (lastTimeLogin < now) && (expiredRedditAuth > 0)) {
                sessionTimeout = expiredRedditAuth - secondTime.intValue();
            }
            return (sessionTimeout < Costant.SESSION_TIMEOUT_DEFAULT) ? Costant.SESSION_TIMEOUT_DEFAULT : sessionTimeout;
        }
        return 0;
    }


}
