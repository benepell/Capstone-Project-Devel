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

package info.pelleritoudacity.android.rcapstone.activity;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import java.util.Arrays;

import butterknife.ButterKnife;
import info.pelleritoudacity.android.rcapstone.R;
import info.pelleritoudacity.android.rcapstone.data.Contract;
import info.pelleritoudacity.android.rcapstone.data.DataUtils;
import info.pelleritoudacity.android.rcapstone.model.Reddit;
import info.pelleritoudacity.android.rcapstone.rest.RestExecute;
import info.pelleritoudacity.android.rcapstone.service.FirebaseJobDispatcherSync;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity implements RestExecute.RestData {

    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = getApplicationContext();
        Timber.plant(new Timber.DebugTree());
        ButterKnife.bind(this);
        FirebaseJobDispatcherSync.initialize(this);

        new RestExecute().loadData(this);


        /*Cursor cursor = mContext.getContentResolver().query(Contract.T5dataEntry.CONTENT_URI, null, null, null, null);
        boolean isRecord = ((cursor != null) && (cursor.getCount() > 0));
        if (isRecord) {
            int val = cursor.getCount();
            Timber.d("record data count: %s", val);
        }
        if (cursor != null) cursor.close();
*/
    }

    @Override
    public void onRestData(Reddit listenerData) {
        if (listenerData != null) {
            DataUtils dataUtils = new DataUtils(mContext);
            if (dataUtils.saveDB(listenerData)) {
//                startFragmentDb();
            } else {
                shownError(R.string.error_state_critical, "Insert Data");
            }
        }
    }


    @Override
    public void onErrorData(Throwable t) {
        Timber.d("onErrorData %s ", t.getMessage() + Arrays.toString(t.getStackTrace()));
    }

    private void shownError(int resourceError, String details) {
//        mErrorText.setVisibility(View.VISIBLE);
        if (details == null) details = "...";
//        mErrorText.setText(getString(resourceError, details));
//        mErrorText.setTag(resourceError);
    }

}
