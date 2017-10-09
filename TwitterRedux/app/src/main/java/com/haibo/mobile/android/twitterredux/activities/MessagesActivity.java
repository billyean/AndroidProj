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

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.haibo.mobile.android.twitterredux.R;
import com.haibo.mobile.android.twitterredux.TwitterApplication;
import com.haibo.mobile.android.twitterredux.TwitterClient;
import com.haibo.mobile.android.twitterredux.adapters.MessageAdapter;
import com.haibo.mobile.android.twitterredux.listeners.EndlessRecyclerViewScrollListener;
import com.haibo.mobile.android.twitterredux.models.Message;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class MessagesActivity extends AppCompatActivity {
    protected static final int MESSAGES_NUMBER_IN_PAGE = 25;

    MessageAdapter adapter;

    RecyclerView rvMessages;

    TwitterClient client;

    // Store a member variable for the listener
    private EndlessRecyclerViewScrollListener scrollListener;

    protected List<Message> messages;

    protected long lastSinceId = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);

        Log.i("INFO", "onCreate");
        rvMessages = (RecyclerView)findViewById(R.id.rvMessages);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rvMessages.setLayoutManager(linearLayoutManager);

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
        rvMessages.addOnScrollListener(scrollListener);

        messages = new ArrayList<>();
        adapter = new MessageAdapter(this, messages);
        rvMessages.setAdapter(adapter);

        client = TwitterApplication.getRestClient();
        populateMessages();
    }

    public void loadNextDataFromApi(int offset) {
        // Send an API request to retrieve appropriate paginated data
        //  --> Send the request including an offset value (i.e `page`) as a query parameter.
        //  --> Deserialize and construct new model objects from the API response
        //  --> Append the new data objects to the existing set of items inside the array of items
        //  --> Notify the adapter of the new items made with `notifyItemRangeInserted()`
        populateMessages();
    }

    protected void populateMessages() {
        client.getDirectMessages(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                try {
                    List<Message> newMessages = Message.fromJSONArray(response);
                    Log.i("INFO", String.format("newMessages.size() =  %d", newMessages.size()));
                    if (newMessages.size() > 0)
                        lastSinceId = newMessages.get(newMessages.size() - 1).getMid();
                    messages.addAll(newMessages);
                    adapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        }, MESSAGES_NUMBER_IN_PAGE, lastSinceId);
    }
}
