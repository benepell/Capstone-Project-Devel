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

package info.pelleritoudacity.android.rcapstone.ui.view;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.text.TextUtils;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Objects;

import info.pelleritoudacity.android.rcapstone.utility.Preference;

public class Tab implements TabLayout.OnTabSelectedListener {

    private final OnTabListener mListener;
    private final TabLayout mTabLayout;
    private final ArrayList<String> mTabList;
    private final ArrayList<String> mTabHistory;
    private final Context mContext;

    public Tab(OnTabListener listener, Context context, TabLayout tabLayout, ArrayList<String> tabList) {
        mListener = listener;
        mTabLayout = tabLayout;
        mTabList = tabList;
        mTabHistory = new ArrayList<>();
        mContext = context;
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        int position = tab.getPosition();

        addHistory(mTabList.get(position));

        mListener.tabSelected(mTabList.get(position));
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {
    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {
        mListener.tabReselected(tab.getPosition(), mTabList.get(tab.getPosition()));
    }

    public void initTab() {
        createTab();
        mTabLayout.setTabGravity(android.support.design.widget.TabLayout.GRAVITY_FILL);
        mTabLayout.addOnTabSelectedListener(this);


    }

    private void createTab() {
        if (mTabList != null) {
            mTabLayout.removeAllTabs();
            for (String string : mTabList) {
                mTabLayout.addTab(mTabLayout.newTab().setText(string));
            }
        }
    }

    public void positionSelected(String category) {
        if (!TextUtils.isEmpty(category)) {
            int indexText = mTabList.indexOf(category);

            if (indexText >= 0) {
                Objects.requireNonNull(mTabLayout.getTabAt(indexText)).select();

            }

        }
    }

    private void addHistory(String category) {
        if (mTabHistory.size() == 0) {
            mTabHistory.add(mTabList.get(0));
        }

        mTabHistory.add(category);

    }

    public String getHistoryPosition() {
        int size;
        String category = null;

        size = mTabHistory.size();

        if (size > 1) {
            category = mTabHistory.get(size - 2);
            mTabHistory.remove(size - 1);
            mTabHistory.remove(size - 2);
        }
        return category;
    }

    public void updateTabPosition() {
        if (Preference.getLastCategory(mContext) != null) {

            int position = mTabList.indexOf(Preference.getLastCategory(mContext));
            if ((position > 0) && (mTabLayout != null) && (position < mTabLayout.getTabCount())) {
                position -= 1;
                int right = ((ViewGroup) mTabLayout.getChildAt(0)).getChildAt(position).getRight();
                mTabLayout.scrollTo(right, 0);
                addHistory(Preference.getLastCategory(mContext));

            }
        }
    }

    public interface OnTabListener {
        void tabSelected(String category);

        void tabReselected(int position, String category);
    }

}
