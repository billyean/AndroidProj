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

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.haibo.mobile.android.twitterredux.TwitterApplication;
import com.haibo.mobile.android.twitterredux.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * Created by Haibo(Tristan) Yan on 10/8/17.
 */

public class FavoriteTimelineFragment extends TweetsListFragment {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        client = TwitterApplication.getRestClient();
    }

    public static FavoriteTimelineFragment newInstance(String screenName) {
        Bundle args = new Bundle();
        args.putString("screen_name", screenName);
        FavoriteTimelineFragment fragment = new FavoriteTimelineFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void populateTweets() {
        String screenName = getArguments().getString("screen_name");
        progressUpdateListener.showProgressBar();
        client.getUserTimeline(screenName, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.d("TwitterClient", response.toString());
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                Log.d("TwitterClient", response.toString());
                final JSONArray result = response;
                try {
                    List<Tweet> newTweets = Tweet.fromJSONArray(result);
                    List<Tweet> favoriteTweets = new ArrayList<>();
                    for (Tweet tweet : newTweets) {
                        if(tweet.isFavorited()) {
                            favoriteTweets.add(tweet);
                        }
                    }
                    lastSinceId = newTweets.get(newTweets.size() - 1).getUid();
                    tweets.addAll(favoriteTweets);
                    adapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                progressUpdateListener.hideProgressBar();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                throwable.printStackTrace();
                progressUpdateListener.hideProgressBar();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                throwable.printStackTrace();
                progressUpdateListener.hideProgressBar();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                throwable.printStackTrace();
                progressUpdateListener.hideProgressBar();
            }
        }, TWEET_NUMBER_IN_PAGE, lastSinceId); // Default start first id
    }
}
