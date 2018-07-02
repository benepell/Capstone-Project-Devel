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
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.preference.DialogPreference;
import android.util.AttributeSet;

import com.bumptech.glide.Glide;

import java.lang.ref.WeakReference;

import info.pelleritoudacity.android.rcapstone.R;
import info.pelleritoudacity.android.rcapstone.data.db.util.DataUtils;
import info.pelleritoudacity.android.rcapstone.data.rest.RevokeTokenExecute;
import info.pelleritoudacity.android.rcapstone.ui.activity.MainActivity;
import info.pelleritoudacity.android.rcapstone.utility.Costant;
import info.pelleritoudacity.android.rcapstone.utility.Preference;

public class DialogConfirm extends DialogPreference {

    private static WeakReference<Context> sWeakReference;

    public DialogConfirm(Context context, AttributeSet attrs) {
        super(context, attrs);
        sWeakReference = new WeakReference<>(context);
    }

    @Override
    protected void onClick() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(getContext(), R.style.confirmDialog);
        dialog.setTitle(R.string.title_dialog_confirm);
        dialog.setMessage(R.string.text_clear_data);
        dialog.setCancelable(true);
        dialog.setPositiveButton(R.string.text_positive_dialog_confirm, (dialog1, which) -> new ResetAsyncTask().execute());
        dialog.setNegativeButton(R.string.text_dialog_confirm_no_reset, (dlg, which) -> dlg.cancel());

        AlertDialog al = dialog.create();
        al.show();
    }

    private static class ResetAsyncTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Context context = sWeakReference.get();
            if (context != null) {

                if (Preference.isLoginStart(context)) {
                    String token = Preference.getSessionAccessToken(context);
                    new RevokeTokenExecute(token, Costant.REDDIT_ACCESS_TOKEN).RevokeTokenSync(context);
                }

                Preference.clearGeneralSettings(context);
                Preference.clearAll(context);
                new DataUtils(context).clearDataPrivacy();

            }
        }


        @Override
        protected Void doInBackground(Void... params) {
            Context context = sWeakReference.get();
            if (context != null) {
                Glide.get(context).clearDiskCache();
            }

            return null;
        }


        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Context context = sWeakReference.get();
            if (context != null) {
                Preference.setClearData(context,true);
                MainActivity.homeActivity(context);
            }
        }
    }

}
