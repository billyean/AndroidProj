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

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;

import com.haibo.mobile.android.twitterredux.R;
import com.haibo.mobile.android.twitterredux.fragments.TweetsListFragment;
import com.haibo.mobile.android.twitterredux.fragments.TweetsPageAdapter;
import com.haibo.mobile.android.twitterredux.models.Tweet;
import com.haibo.mobile.android.twitterredux.models.User;
import com.haibo.mobile.android.twitterredux.utils.NetworkUtilities;

import org.parceler.Parcels;

public class TwitterActivity extends AppCompatActivity implements
        TweetsListFragment.TweetSelectiedListener, TweetsListFragment.ReplyTweetListener,
        TweetsListFragment.ProfileSelectiedListener, ProgressUpdateListener {
    public static final int RETWEET_REQUEST_CODE = 40;

    public static final int NEW_TWEET_REQUEST_CODE = 20;

    public static final int PROFILE_REQUEST_CODE = 60;

    // Instance of the progress action-view
    private MenuItem miActionProgressItem;

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
                TwitterActivity.this, this));

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

    @Override
    public void replyTweet(Tweet tweet) {
        Intent intent = new Intent(this, ComposeTweetActivity.class);
        intent.putExtra("replyToUser", Parcels.wrap(tweet.getUser()));
        startActivityForResult(intent, RETWEET_REQUEST_CODE);
    }

    @Override
    public void onProfileSelected(User user) {
        Intent showProfileIntent = new Intent(this, ProfileActivity.class);
        showProfileIntent.putExtra("user_id", user.getUid());
        showProfileIntent.putExtra("screen_name", user.getScreenName());
        startActivity(showProfileIntent);
    }

    public void checkNetworkAvailable() {
        if (NetworkUtilities.isNetworkAvailable(this)) {
            final AlertDialog alertDialog = new AlertDialog.Builder(TwitterActivity.this).create();
            alertDialog.setTitle("Alert Dialog");
            alertDialog.setMessage("Mobile network is not available.");

            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    alertDialog.dismiss();
                }
            });

            alertDialog.show();
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // Store instance of the menu item containing progress
        miActionProgressItem = menu.findItem(R.id.miActionProgress);
        // Extract the action-view from the menu item
        ProgressBar v =  (ProgressBar) MenuItemCompat.getActionView(miActionProgressItem);
        // Return to finish
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void showProgressBar() {
        // Show progress item
        if (miActionProgressItem!= null)
            miActionProgressItem.setVisible(true);
    }

    @Override
    public void hideProgressBar() {
        // Hide progress item
        if (miActionProgressItem!= null)
            miActionProgressItem.setVisible(false);
    }
}
