package com.haibo.mobile.android.nytimer.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Haibo(Tristan) Yan on 9/21/17.
 */
public class Article {
    private static final String NY_WEB_URL = "http://www.nytimes.com/";

    private String snippet;

    private String id;

    private String thumbnail;

    private String webURL;

    private String headline;

    public String getSnippet() {
        return snippet;
    }

    public String getId() {
        return id;
    }

    public String getWebURL() {
        return webURL;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public String getHeadline() {
        return headline;
    }

    public static Article fromJson(JSONObject jsonObject) throws JSONException {
        Article article = new Article();
        article.id = jsonObject.getString("_id");
        article.snippet = jsonObject.getString("snippet");
        article.webURL = jsonObject.getString("web_url");
        JSONObject headline = jsonObject.getJSONObject("headline");
        article.headline = headline.getString("main");
        JSONArray multimedia = jsonObject.getJSONArray("multimedia");
        if (multimedia.length() > 0) {
            article.thumbnail = NY_WEB_URL + multimedia.getJSONObject(0).getString("url");
        } else {
            article.thumbnail = "";
        }
        return article;
    }

    public static List<Article> fromJsonArray(JSONArray array) throws JSONException {
        List<Article> list = new ArrayList<>();
        for (int index = 0; index < array.length(); index++) {
            list.add(fromJson(array.getJSONObject(index)));
        }
        return list;
    }
}
