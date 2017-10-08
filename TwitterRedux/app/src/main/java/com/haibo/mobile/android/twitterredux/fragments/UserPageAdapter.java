/*
 * Copyright (c) [2017] [Haibo(Tristan) Yan]
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.haibo.mobile.android.twitterredux.fragments;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.haibo.mobile.android.twitterredux.R;

/**
 * Created by Haibo(Tristan) Yan on 10/8/17.
 */

public class UserPageAdapter extends FragmentPagerAdapter {
    private final String[] tabTitles;

    private Context context;

    private Long userId;

    private String screenName;

    public UserPageAdapter(FragmentManager fm, Context context, Long userId, String screenName) {
        super(fm);
        this.context = context;
        tabTitles = new String[] {
                context.getResources().getString(R.string.tab_tweets),
                context.getResources().getString(R.string.tab_photos),
                context.getResources().getString(R.string.tab_favorites)
        };
        this.userId = userId;
        this.screenName = screenName;
    }
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return UserTimelineFragment.newInstance(screenName);
            case 1:
                return PhotoFragment.newInstance(screenName);
            case 2:
                return FavoriteTimelineFragment.newInstance(screenName);
        }
        return null;
    }

    @Override
    public int getCount() {
        return tabTitles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }
}
