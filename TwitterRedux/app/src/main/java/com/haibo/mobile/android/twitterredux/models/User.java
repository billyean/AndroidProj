package com.haibo.mobile.android.twitterredux.models;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

/**
 * Created by Haibo(Tristan) Yan on 10/6/17.
 */

@Parcel
public class User {
    private String name;

    private long uid;

    private String screenName;

    public String profileImageUrl;

    private String profileBannerUrl;

    private String description;

    private Long followings;

    private Long followers;

    public static User fromJSON(JSONObject jsonObject) throws JSONException {
        User user = new User();

        user.name = jsonObject.getString("name");
        user.uid = jsonObject.getLong("id");
        user.screenName = jsonObject.getString("screen_name");
        user.profileImageUrl = jsonObject.getString("profile_image_url");
        if (jsonObject.has("profile_banner_url"))
            user.profileBannerUrl = jsonObject.getString("profile_banner_url");
        user.description = jsonObject.getString("description");
        if (jsonObject.has("friends_count"))
            user.followings = jsonObject.getLong("friends_count");
        if (jsonObject.has("followers_count"))
            user.followers = jsonObject.getLong("followers_count");
        return user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public String getScreenName() {
        return screenName;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public String getProfileBannerUrl() {
        return profileBannerUrl;
    }

    public void setProfileBannerUrl(String profileBannerUrl) {
        this.profileBannerUrl = profileBannerUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getFollowings() {
        return followings;
    }

    public void setFollowings(Long followings) {
        this.followings = followings;
    }

    public Long getFollowers() {
        return followers;
    }

    public void setFollowers(Long followers) {
        this.followers = followers;
    }
}
