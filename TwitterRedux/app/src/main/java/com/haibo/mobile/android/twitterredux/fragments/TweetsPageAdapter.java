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
import android.util.Log;

import com.haibo.mobile.android.twitterredux.R;
import com.haibo.mobile.android.twitterredux.activities.ProgressUpdateListener;

/**
 * Created by Haibo(Tristan) Yan on 10/8/17.
 */

public class TweetsPageAdapter extends FragmentPagerAdapter {
    private final String[] tabTitles;

    private Context context;

    private ProgressUpdateListener progressUpdateListener;

    public TweetsPageAdapter(FragmentManager fm, Context context, ProgressUpdateListener progressUpdateListener) {
        super(fm);
        this.context = context;
        tabTitles = new String[] {
            context.getResources().getString(R.string.tab_home_timeline),
                context.getResources().getString(R.string.tab_mentions_timeline)
        };
        this.progressUpdateListener = progressUpdateListener;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                HomeTimelineFragment homeTimelineFragment = HomeTimelineFragment.newInstance();
                homeTimelineFragment.setProgressUpdateListener(progressUpdateListener);
                return homeTimelineFragment;
            case 1:
                MentionsTimelineFragment mentionsTimelineFragment = MentionsTimelineFragment.newInstance();
                mentionsTimelineFragment.setProgressUpdateListener(progressUpdateListener);
                return mentionsTimelineFragment;
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
