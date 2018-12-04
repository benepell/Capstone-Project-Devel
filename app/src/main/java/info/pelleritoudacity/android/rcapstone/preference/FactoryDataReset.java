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

package info.pelleritoudacity.android.rcapstone.preference;


import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;


import com.bumptech.glide.Glide;

import androidx.appcompat.app.AlertDialog;
import androidx.preference.DialogPreference;
import info.pelleritoudacity.android.rcapstone.R;
import info.pelleritoudacity.android.rcapstone.data.db.AppDatabase;
import info.pelleritoudacity.android.rcapstone.data.db.AppExecutors;
import info.pelleritoudacity.android.rcapstone.data.db.util.DataUtils;
import info.pelleritoudacity.android.rcapstone.data.rest.RevokeTokenExecute;
import info.pelleritoudacity.android.rcapstone.service.FirebaseRefreshTokenSync;
import info.pelleritoudacity.android.rcapstone.ui.activity.MainActivity;
import info.pelleritoudacity.android.rcapstone.utility.Preference;
import timber.log.Timber;

public class FactoryDataReset extends DialogPreference {

    private final Context mContext;
    private final AppDatabase mDb;

    public FactoryDataReset(Context context,AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        mDb = AppDatabase.getInstance(context);
    }

    @Override
    protected void onClick() {
        int theme = R.style.confirmDialogLight;
        if (Preference.isNightMode(mContext)) {
            theme = R.style.confirmDialogDark;
        }
        AlertDialog.Builder dialog = new AlertDialog.Builder(getContext(), theme);
        dialog.setTitle(R.string.title_dialog_confirm);
        dialog.setMessage(R.string.text_clear_data);
        dialog.setCancelable(true);

        dialog.setPositiveButton(R.string.text_positive_dialog_confirm, (dialog1, which) ->
                resetTask()

        );

        dialog.setNegativeButton(R.string.text_dialog_confirm_no_reset, (dlg, which) -> dlg.cancel());

        AlertDialog al = dialog.create();
        al.show();
    }

    private void resetTask() {

        if (mContext != null) {
            if (Preference.isLoginStart(mContext)) {
                FirebaseRefreshTokenSync.stopLogin(mContext);

                String token = Preference.getSessionAccessToken(mContext);
                new RevokeTokenExecute(new RevokeTokenExecute.OnRestCallBack() {
                    @Override
                    public void success(String response, int code) {
                        Timber.d("Reset  code:%s", code);
                    }

                    @Override
                    public void unexpectedError(Throwable tList) {
                        Timber.e("Reset Error %s", tList.getMessage());
                    }
                }, token).RevokeTokenData();
            }

            AppExecutors.getInstance().diskIO().execute(() -> Glide.get(mContext).clearDiskCache());

            Preference.clearGeneralSettings(mContext);
            Preference.clearAll(mContext);
            new DataUtils(mContext,mDb).clearDataPrivacy();

            Preference.setFactoryDataReset(mContext, true);
            mContext.startActivity(new Intent(mContext, MainActivity.class)
                    .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            );
        }

    }


}
