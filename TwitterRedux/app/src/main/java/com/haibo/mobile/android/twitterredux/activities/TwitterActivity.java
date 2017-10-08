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

package com.haibo.mobile.android.twitterredux.activities;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.haibo.mobile.android.twitterredux.R;
import com.haibo.mobile.android.twitterredux.fragments.TweetsListFragment;
import com.haibo.mobile.android.twitterredux.fragments.TweetsPageAdapter;
import com.haibo.mobile.android.twitterredux.models.Tweet;


public class TwitterActivity extends AppCompatActivity implements TweetsListFragment.TweetSelectiedListener {

    public static final int NEW_TWEET_REQUEST_CODE = 20;

    public static final int PROFILE_REQUEST_CODE = 40;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tweets);

        // Find the toolbar view inside the activity layout
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setLogo(R.drawable.twitter_icon);
//        toolbar.setTitle(R.string.app_name);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        setSupportActionBar(toolbar);

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new TweetsPageAdapter(getSupportFragmentManager(),
                TwitterActivity.this));

        // Give the TabLayout the ViewPager
        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_tweets, menu);
        MenuItem newTweetItem = menu.findItem(R.id.newTweet);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.newTweet:
                Intent newTweetIntent = new Intent(this, ComposeTweetActivity.class);
                startActivityForResult(newTweetIntent, NEW_TWEET_REQUEST_CODE);
                return true;
            case R.id.profile:
                Intent showProfileIntent = new Intent(this, ProfileActivity.class);
                startActivity(showProfileIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    @Override
    public void onTweetSelected(Tweet tweet) {

    }
}
