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

package com.haibo.mobile.android.twitterredux;

import android.content.Context;

import com.haibo.mobile.android.twitterredux.models.Tweet;
import com.codepath.oauth.OAuthBaseClient;
import com.github.scribejava.apis.FlickrApi;
import com.github.scribejava.apis.TwitterApi;
import com.github.scribejava.core.builder.api.BaseApi;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * Created by Haibo(Tristan) Yan on 10/7/17.
 */

public class TwitterClient extends OAuthBaseClient {
    public static final BaseApi REST_API_INSTANCE = TwitterApi.instance(); // Change this
    public static final String REST_URL = "https://api.twitter.com/1.1"; // Change this, base API URL
    public static final String REST_CONSUMER_KEY = "rlWMPh3gz1jeoE0ArS7io8hVT";       // Change this
    public static final String REST_CONSUMER_SECRET = "FnGTPUKj1giaamWrSvidHTTTb8ZQENjNlHgIIgegMEiLmXBRIr"; // Change this

    // Landing page to indicate the OAuth flow worked in case Chrome for Android 25+ blocks navigation back to the app.
    public static final String FALLBACK_URL = "https://github.com/billyean/codepath";

    // See https://developer.chrome.com/multidevice/android/intents
    public static final String REST_CALLBACK_URL_TEMPLATE = "intent://%s#Intent;action=android.intent.action.VIEW;scheme=%s;package=%s;S.browser_fallback_url=%s;end";

    public TwitterClient(Context context) {
        super(context, REST_API_INSTANCE,
                REST_URL,
                REST_CONSUMER_KEY,
                REST_CONSUMER_SECRET,
                String.format(REST_CALLBACK_URL_TEMPLATE, context.getString(R.string.intent_host),
                        context.getString(R.string.intent_scheme), context.getPackageName(), FALLBACK_URL));
    }

    // CHANGE THIS
    // DEFINE METHODS for different API endpoints here
    public void getHomeTimeline(AsyncHttpResponseHandler handler, int count, long sinceId) {
        String apiUrl = getApiUrl("statuses/home_timeline.json");
        // Can specify query string params directly or through RequestParams.
        RequestParams params = new RequestParams();
        params.put("count", count);
        params.put("since_id", sinceId);
        client.get(apiUrl, params, handler);
    }

    public void getMentionsTimeline (AsyncHttpResponseHandler handler, int count, long sinceId) {
        String apiUrl = getApiUrl("statuses/mentions_timeline.json");
        // Can specify query string params directly or through RequestParams.
        RequestParams params = new RequestParams();
        params.put("count", count);
        params.put("since_id", sinceId);
        client.get(apiUrl, params, handler);
    }

	/* 1. Define the endpoint URL with getApiUrl and pass a relative path to the endpoint
	 * 	  i.e getApiUrl("statuses/home_timeline.json");
	 * 2. Define the parameters to pass to the request (query or body)
	 *    i.e RequestParams params = new RequestParams("foo", "bar");
	 * 3. Define the request method and make a call to the client
	 *    i.e client.get(apiUrl, params, handler);
	 *    i.e client.post(apiUrl, params, handler);
	 */

    public void getCurrentUser(AsyncHttpResponseHandler handler) {
        String apiUrl = getApiUrl("account/verify_credentials.json");
        RequestParams params = new RequestParams();
        client.get(apiUrl, params, handler);
    }

    public void retweet(Tweet tweet, AsyncHttpResponseHandler handler) {
        String apiUrl = getApiUrl("statuses/retweet/" + tweet.getUid() + ".json");
        RequestParams params = new RequestParams();
        client.post(apiUrl, params, handler);
    }

    public void unRetweet(Tweet tweet, AsyncHttpResponseHandler handler) {
        String apiUrl = getApiUrl("statuses/destroy/" + tweet.getRetweetId() + ".json");
        RequestParams params = new RequestParams();
        client.post(apiUrl, params, handler);
    }

    public void favorite(Tweet tweet, AsyncHttpResponseHandler handler) {
        String apiUrl = getApiUrl("favorites/create.json");
        RequestParams params = new RequestParams();
        params.put("id", tweet.getUid());
        client.post(apiUrl, params, handler);
    }

    public void unFavorite(Tweet tweet, AsyncHttpResponseHandler handler) {
        String apiUrl = getApiUrl("favorites/destroy.json");
        RequestParams params = new RequestParams();
        params.put("id", tweet.getUid());
        client.post(apiUrl, params, handler);
    }

    public void tweet(String tweetContent, AsyncHttpResponseHandler handler) {
        reply(tweetContent, -1, handler);
    }

    public void reply(String tweetContent, long replyToId, AsyncHttpResponseHandler handler) {
        String apiUrl = getApiUrl("statuses/update.json");
        RequestParams params = new RequestParams();
        params.put("status", tweetContent);

        if (replyToId > 0) {
            params.put("in_reply_to_status_id", replyToId);
        }
        client.post(apiUrl, params, handler);
    }
}
