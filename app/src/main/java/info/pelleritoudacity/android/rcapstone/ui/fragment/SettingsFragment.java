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

package info.pelleritoudacity.android.rcapstone.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;

import com.google.android.exoplayer2.util.Util;

import info.pelleritoudacity.android.rcapstone.R;
import info.pelleritoudacity.android.rcapstone.utility.Utility;

public class SettingsFragment extends PreferenceFragmentCompat  {

    private String mAppVersionName;
    private OnSettingsFragmentInteraction mListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (OnSettingsFragmentInteraction) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().getLocalClassName() + "must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.pref_general_settings, rootKey);
        applyTheme();
        prefMail();
        prefVersion();
        prefOver18();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    private void prefMail() {
        final Preference mailTo = findPreference("pref_contact");
        mailTo.setOnPreferenceClickListener(preference -> {
            int androidVersionCode = 0;
            String hwInfo = null;
            try {
                androidVersionCode = Util.SDK_INT;
                hwInfo = Util.MANUFACTURER + " - " + Util.MODEL;
                mAppVersionName = Utility.appVersionName(getActivity());
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }

            String textMail = "Version: " +
                    mAppVersionName + "\n" +
                    "Model: " +
                    hwInfo + "\n" +
                    "Api: " +
                    String.valueOf(androidVersionCode) + "\n\n";

            Intent mailIntent = new Intent(Intent.ACTION_SEND);
            mailIntent.setType("message/rfc822");
            mailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{getString(R.string.app_support_mail)});
            mailIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.subject_support_mail) + " " + getString(R.string.app_name));
            mailIntent.putExtra(Intent.EXTRA_TEXT, textMail);
            startActivity(Intent.createChooser(mailIntent, getString(R.string.text_mail_intent)));
            return true;
        });

    }

    private void prefVersion() {
        final Preference prefVersion = findPreference(getString(R.string.pref_app_version));

        try {
            mAppVersionName = Utility.appVersionName(getActivity());
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (mAppVersionName != null) {
            prefVersion.setTitle("Version: " + mAppVersionName);
        }
    }

    private void prefOver18() {
        final Preference prefAdultFilter = findPreference(getString(R.string.pref_adult_filter));
        if (getActivity() != null) {
            boolean isOver18 = info.pelleritoudacity.android.rcapstone.utility.Preference.isLoginOver18(getContext());
            boolean isLogged = info.pelleritoudacity.android.rcapstone.utility.Preference.isLoginStart(getContext());

            if (isLogged) {

                if (isOver18) {
                    prefAdultFilter.setEnabled(true);

                } else {
                    prefAdultFilter.setEnabled(false);
                    prefAdultFilter.setSummary(R.string.reddit_adultcontent_text);
                }
            } else {
                prefAdultFilter.setEnabled(false);
                prefAdultFilter.setSummary(R.string.reddit_nologinadultcontent_text);
            }

        }
    }

    private void applyTheme() {
        final Preference preference = findPreference(getString(R.string.pref_night_mode));
        preference.setOnPreferenceChangeListener((preference1, newValue) -> {
            mListener.applyTheme(true);
            return true;
        });

    }

    public interface OnSettingsFragmentInteraction {
        void applyTheme(boolean b);
    }
}