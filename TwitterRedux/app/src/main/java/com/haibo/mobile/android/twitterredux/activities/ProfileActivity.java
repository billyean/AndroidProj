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

import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.haibo.mobile.android.twitterredux.R;
import com.haibo.mobile.android.twitterredux.TwitterApplication;
import com.haibo.mobile.android.twitterredux.TwitterClient;
import com.haibo.mobile.android.twitterredux.fragments.TweetsPageAdapter;
import com.haibo.mobile.android.twitterredux.fragments.UserPageAdapter;
import com.haibo.mobile.android.twitterredux.fragments.UserTimelineFragment;
import com.haibo.mobile.android.twitterredux.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

public class ProfileActivity extends AppCompatActivity implements ProgressUpdateListener {
    protected TwitterClient client;

    private ImageView ivProfileBG;

    private ImageView ivProfile;

    private TextView tvName;

    private TextView tvScreenName;

    private TextView tvDesc;

    private TextView tvFollowings;

    private TextView tvFollowers;

    // Instance of the progress action-view
    private MenuItem miActionProgressItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Long userId = getIntent().getLongExtra("user_id", 0);
        String screenName = getIntent().getStringExtra("screen_name");

        client = TwitterApplication.getRestClient();

        Log.i("INFO", String.format("user_id = %d, screenName = %s", userId, screenName));

        ivProfileBG = (ImageView) findViewById(R.id.ivProfileBG);
        ivProfile = (ImageView) findViewById(R.id.ivProfile);
        tvName = (TextView) findViewById(R.id.tvName);
        tvScreenName = (TextView) findViewById(R.id.tvScreenName);
        tvDesc = (TextView) findViewById(R.id.tvDesc);
        tvFollowings = (TextView) findViewById(R.id.tvFollowings);
        tvFollowers = (TextView) findViewById(R.id.tvFollowers);


        client.getUserProfile(new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    User user = User.fromJSON(response);
//                    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//                    toolbar.setTitle(user.getScreenName());

                    Picasso.with(ProfileActivity.this).load(user.getProfileBannerUrl()).into(ivProfileBG);
                    Picasso.with(ProfileActivity.this).load(user.getProfileImageUrl())
                            .resize(45, 45)
                            .transform(new RoundedCornersTransformation(6, 6)).into(ivProfile);
                    tvName.setText(user.getName());
                    tvScreenName.setText("@" + user.getScreenName());
                    tvDesc.setText(user.getDescription());
                    tvFollowers.setText(String.valueOf(user.getFollowers()));
                    tvFollowings.setText(String.valueOf(user.getFollowings()));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        }, userId, screenName);

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new UserPageAdapter(getSupportFragmentManager(),
                ProfileActivity.this, userId, screenName, this));

        // Give the TabLayout the ViewPager
        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);
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
