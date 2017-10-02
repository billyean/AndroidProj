package com.codepath.apps.restclienttemplate.models;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Haibo(Tristan) Yan on 9/28/17.
 */
@Parcel
public class Tweet{
    String body;

    long uid;

    long retweetId;

    String createdAt;

    User user;

    boolean favorited;

    boolean retweeted;

    int retweetCount;

    int favoriteCount;

    String inReplyToUID;

    String inReplyToScreenName;

    boolean retweetedBy;

    User retweetedUser;

    String mediaURL;

    String videoURL;

    public static List<Tweet> fromJSONArray(JSONArray jsonArray) throws JSONException {
        List<Tweet> tweets = new ArrayList<>();

        for (int i = 0; i < jsonArray.length(); i++) {
            tweets.add(fromJSON(jsonArray.getJSONObject(i)));
        }
        return tweets;
    }

    public static Tweet fromJSON(JSONObject jsonObject) throws JSONException {
        Tweet tweet = new Tweet();

        if (!jsonObject.has("retweeted_status")) {

            tweet.body = jsonObject.getString("text");
            tweet.uid = jsonObject.getLong("id");
            tweet.createdAt = jsonObject.getString("created_at");
            tweet.user = User.fromJSON(jsonObject.getJSONObject("user"));
            tweet.favorited = jsonObject.getBoolean("favorited");
            tweet.favoriteCount = jsonObject.getInt("favorite_count");
            tweet.retweeted = jsonObject.getBoolean("retweeted");
            tweet.retweetCount = jsonObject.getInt("retweet_count");
            tweet.retweetedBy = false;
            if (jsonObject.has("entities")) {
                JSONObject entities = jsonObject.getJSONObject("entities");
                if (entities.has("media")) {
                    Log.i("INFO", entities.getJSONArray("media").toString());
                    tweet.mediaURL = entities.getJSONArray("media").getJSONObject(0).getString("media_url_https");
                }

                if(entities.has("urls")) {
                    JSONArray urls = entities.getJSONArray("urls");
                    if (urls.length() > 0) {
                        tweet.videoURL = urls.getJSONObject(0).getString("expanded_url");
                    }
                }
            }
        } else {
            JSONObject retweetdStatus = jsonObject.getJSONObject("retweeted_status");
            tweet.body = retweetdStatus.getString("text");
            tweet.uid = retweetdStatus.getLong("id");
            tweet.createdAt = retweetdStatus.getString("created_at");
            tweet.user = User.fromJSON(retweetdStatus.getJSONObject("user"));
            tweet.favorited = retweetdStatus.getBoolean("favorited");
            tweet.favoriteCount = retweetdStatus.getInt("favorite_count");
            tweet.retweeted = retweetdStatus.getBoolean("retweeted");
            tweet.retweetCount = retweetdStatus.getInt("retweet_count");
            tweet.retweetedBy = true;
            tweet.retweetedUser = User.fromJSON(jsonObject.getJSONObject("user"));
            tweet.setRetweetId(jsonObject.getLong("id"));
            if (retweetdStatus.has("entities")) {
                JSONObject entities = retweetdStatus.getJSONObject("entities");
                if (entities.has("media")) {
                    tweet.mediaURL = entities.getJSONArray("media").getJSONObject(0).getString("media_url_https");
                }

                if(entities.has("urls")) {
                    JSONArray urls = entities.getJSONArray("urls");
                    if (urls.length() > 0) {
                        tweet.videoURL = urls.getJSONObject(0).getString("expanded_url");
                    }
                }
            }
        }
        return tweet;
    }


    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isFavorited() {
        return favorited;
    }

    public void setFavorited(boolean favorited) {
        this.favorited = favorited;
    }

    public boolean isRetweetedBy() {
        return retweetedBy;
    }

    public void setRetweetedBy(boolean retweetedBy) {
        this.retweetedBy = retweetedBy;
    }

    public User getRetweetedUser() {
        return retweetedUser;
    }

    public void setRetweetedUser(User retweetedUser) {
        this.retweetedUser = retweetedUser;
    }

    public String getMediaURL() {
        return mediaURL;
    }

    public void setMediaURL(String mediaURL) {
        this.mediaURL = mediaURL;
    }

    public String getVideoURL() {
        return videoURL;
    }

    public void setVideoURL(String videoURL) {
        this.videoURL = videoURL;
    }

    public int getRetweetCount() {
        return retweetCount;
    }

    public void setRetweetCount(int retweetCount) {
        this.retweetCount = retweetCount;
    }

    public int getFavoriteCount() {
        return favoriteCount;
    }

    public void setFavoriteCount(int favoriteCount) {
        this.favoriteCount = favoriteCount;
    }

    public String getInReplyToUID() {
        return inReplyToUID;
    }

    public void setInReplyToUID(String inReplyToUID) {
        this.inReplyToUID = inReplyToUID;
    }

    public String getInReplyToScreenName() {
        return inReplyToScreenName;
    }

    public void setInReplyToScreenName(String inReplyToScreenName) {
        this.inReplyToScreenName = inReplyToScreenName;
    }

    public boolean isRetweeted() {
        return retweeted;
    }

    public void setRetweeted(boolean retweeted) {
        this.retweeted = retweeted;
    }

    public long getRetweetId() {
        return retweetId;
    }

    public void setRetweetId(long retweetId) {
        this.retweetId = retweetId;
    }

    public boolean hasMedia(){
        return mediaURL != null;
    }

    public String getTimeBefore() {
        DateFormat df = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy");
        try {
            Date createdDate = df.parse(createdAt);
            long diff = (Calendar.getInstance().getTime().getTime() - createdDate.getTime()) / 1000;

            if (diff < 60) {
                return String.format("%d s", diff);
            }

            diff /= 60;

            if (diff < 60) {
                return String.format("%d m", diff);
            }

            diff /= 60;

            if (diff < 24) {
                return String.format("%d h", diff);
            }

            diff /= 24;

            if (diff < 30) {
                return String.format("%d d", diff);
            }

            diff /= 30;
            return String.format("%d m", diff);

        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }
}
