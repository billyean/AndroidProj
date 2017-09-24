package com.haibo.mobile.android.nytimer.networking;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by Haibo(Tristan) Yan on 9/21/17.
 */

public class ArticleHTTPClient {
    private static final String API_BASE_URL = "https://api.nytimes.com/svc/search/v2/articlesearch.json";

    private static final String API_KEY = "5e45d5632c1542c0b00a8a6348890343";

    private AsyncHttpClient client;

    public ArticleHTTPClient() {
        this.client = new AsyncHttpClient();
        client.setTimeout(20 * 1000);
    }

    private String getApiUrl(String relativeUrl) {
        return API_BASE_URL + relativeUrl;
    }

    // Method for accessing the search API
    public void getArticles(final String query, JsonHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("api-key", API_KEY);
        params.put("page", 0);
        if (query != null && query.length() > 0) {
            params.put("q", query);
        }
        client.get(API_BASE_URL, params, handler);
    }
}
