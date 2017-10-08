package com.haibo.mobile.android.twitterredux.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.haibo.mobile.android.twitterredux.R;
import com.haibo.mobile.android.twitterredux.TwitterApplication;
import com.haibo.mobile.android.twitterredux.TwitterClient;
import com.haibo.mobile.android.twitterredux.adapters.TweetAdapter;
import com.haibo.mobile.android.twitterredux.listeners.EndlessRecyclerViewScrollListener;
import com.haibo.mobile.android.twitterredux.models.Tweet;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Haibo(Tristan) Yan on 10/6/17.
 */

public abstract class TweetsListFragment extends Fragment implements TweetAdapter.TweetAdapterListener {
    protected static final int TWEET_NUMBER_IN_PAGE = 25;

    public static final int RETWEET_REQUEST_CODE = 40;

    TweetAdapter adapter;

    RecyclerView rvTweets;

    protected List<Tweet> tweets;

    protected long lastSinceId = 1;

    // Store a member variable for the listener
    private EndlessRecyclerViewScrollListener scrollListener;

    protected TwitterClient client;

    private TweetSelectiedListener tweetSelectiedListener;

    public interface TweetSelectiedListener {
        public void onTweetSelected(Tweet tweet);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragments_tweets_list, null);

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
        adapter = new TweetAdapter(this, tweets, this);
        rvTweets.setAdapter(adapter);

        client = TwitterApplication.getRestClient();
        populateTweets();
        return view;
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

    @Override
    public void onItemSelected(View view, int position) {
        Tweet tweet = tweets.get(position);
        ((TweetSelectiedListener)getActivity()).onTweetSelected(tweet);
    }
}
