package com.haibo.mobile.android.nytimer.networking;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

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

    private Context context;

    public ArticleHTTPClient(Context context) {
        this.context = context;
        this.client = new AsyncHttpClient();
        client.setTimeout(20 * 1000);
    }

    private String getApiUrl(String relativeUrl) {
        return API_BASE_URL + relativeUrl;
    }

    // Method for accessing the search API
    public void getArticles(final String query, int page, JsonHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("api-key", API_KEY);
        params.put("page", page);
        params.put("q", query);
        SharedPreferences pref = context.getSharedPreferences("settings", Context.MODE_PRIVATE);

        if (pref != null) {
            String beginDate = pref.getString("beginDate", "");
            if (!beginDate.isEmpty()) {
                params.put("begin_date", beginDate);
            }
            String sortOrder = pref.getString("sortOrder", "");
            if (!sortOrder.isEmpty()) {
                params.put("sort", sortOrder);
            }

            boolean arts = pref.getBoolean("arts", false);
            boolean fashionStyle = pref.getBoolean("fashionStyle", false);
            boolean sports = pref.getBoolean("sports", false);
            if (arts || fashionStyle || sports) {
                StringBuilder newsDeskBuilder = new StringBuilder("news_desk:(");
                if (arts) {
                    newsDeskBuilder.append(" \"Arts\"");
                }

                if (fashionStyle) {
                    newsDeskBuilder.append(" \"Fashion & Style\"");
                }

                if (sports) {
                    newsDeskBuilder.append(" \"Sports\"");
                }
                newsDeskBuilder.append(")");
                params.put("fq", newsDeskBuilder.toString());
            }
        }

        client.get(API_BASE_URL, params, handler);
    }
}
