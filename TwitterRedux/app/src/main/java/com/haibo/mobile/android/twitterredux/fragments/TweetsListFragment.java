package com.haibo.mobile.android.twitterredux.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.haibo.mobile.android.twitterredux.R;
import com.haibo.mobile.android.twitterredux.TwitterClient;
import com.haibo.mobile.android.twitterredux.adapters.ComplexRecyclerViewAdapter;
import com.haibo.mobile.android.twitterredux.listeners.EndlessRecyclerViewScrollListener;
import com.haibo.mobile.android.twitterredux.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * Created by Haibo(Tristan) Yan on 10/6/17.
 */

public abstract class TweetsListFragment extends Fragment {
    protected static final int TWEET_NUMBER_IN_PAGE = 25;

    protected static final int NEW_TWEET_REQUEST_CODE = 20;

    protected static final int RETWEET_REQUEST_CODE = 40;

    ComplexRecyclerViewAdapter adapter;

    RecyclerView rvTweets;

    protected List<Tweet> tweets;

    protected long lastSinceId = 1;

    // Store a member variable for the listener
    private EndlessRecyclerViewScrollListener scrollListener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragments_tweets_list, container);

        rvTweets = (RecyclerView)view.findViewById(R.id.rvTweets);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rvTweets.setLayoutManager(linearLayoutManager);
        // Retain an instance so that you can call `resetState()` for fresh searches
        scrollListener = new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to the bottom of the list
                loadNextDataFromApi(page);
            }
        };
        // Adds the scroll listener to RecyclerView
        rvTweets.addOnScrollListener(scrollListener);

        tweets = new ArrayList<>();
        adapter = new ComplexRecyclerViewAdapter(this, tweets);
        rvTweets.setAdapter(adapter);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    public void loadNextDataFromApi(int offset) {
        // Send an API request to retrieve appropriate paginated data
        //  --> Send the request including an offset value (i.e `page`) as a query parameter.
        //  --> Deserialize and construct new model objects from the API response
        //  --> Append the new data objects to the existing set of items inside the array of items
        //  --> Notify the adapter of the new items made with `notifyItemRangeInserted()`
        populateTweets();
    }

    protected abstract void populateTweets() ;
}
