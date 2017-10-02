package com.codepath.apps.restclienttemplate.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.restclienttemplate.R;


/**
 * Created by Haibo(Tristan) Yan on 10/1/17.
 */

public class TweetViewHolder extends RecyclerView.ViewHolder {
    private ImageView ivProfileImage;

    public ImageView getIvProfileImage() {
        return ivProfileImage;
    }

    public void setIvProfileImage(ImageView ivProfileImage) {
        this.ivProfileImage = ivProfileImage;
    }

    public TextView getTvUsername() {
        return tvUsername;
    }

    public void setTvUsername(TextView tvUsername) {
        this.tvUsername = tvUsername;
    }

    public TextView getTvAT() {
        return tvAT;
    }

    public void setTvAT(TextView tvAT) {
        this.tvAT = tvAT;
    }

    public TextView getTvTweet() {
        return tvTweet;
    }

    public void setTvTweet(TextView tvTweet) {
        this.tvTweet = tvTweet;
    }

    public TextView getTvTime() {
        return tvTime;
    }

    public void setTvTime(TextView tvTime) {
        this.tvTime = tvTime;
    }

    public ImageView getIvReply() {
        return ivReply;
    }

    public void setIvReply(ImageView ivReply) {
        this.ivReply = ivReply;
    }

    public ImageView getIvRetweet() {
        return ivRetweet;
    }

    public void setIvRetweet(ImageView ivRetweet) {
        this.ivRetweet = ivRetweet;
    }

    public ImageView getIvFavorite() {
        return ivFavorite;
    }

    public void setIvFavorite(ImageView ivFavorite) {
        this.ivFavorite = ivFavorite;
    }

    public TextView getTvReplyCount() {
        return tvReplyCount;
    }

    public void setTvReplyCount(TextView tvReplyCount) {
        this.tvReplyCount = tvReplyCount;
    }

    public TextView getTvRetweetCount() {
        return tvRetweetCount;
    }

    public void setTvRetweetCount(TextView tvRetweetCount) {
        this.tvRetweetCount = tvRetweetCount;
    }

    public TextView getTvFavoriteCount() {
        return tvFavoriteCount;
    }

    public void setTvFavoriteCount(TextView tvFavoriteCount) {
        this.tvFavoriteCount = tvFavoriteCount;
    }

    protected TextView tvUsername;

    protected TextView tvAT;

    protected TextView tvTweet;

    protected TextView tvTime;

    protected ImageView ivReply;

    protected ImageView ivRetweet;

    protected ImageView ivFavorite;

    protected TextView tvReplyCount;

    protected TextView tvRetweetCount;

    protected TextView tvFavoriteCount;

    public TweetViewHolder(View view) {
        super(view);

        ivProfileImage = (ImageView)view.findViewById(R.id.ivProfileImage);
        tvUsername = (TextView)view.findViewById(R.id.tvUsername);
        tvAT = (TextView)view.findViewById(R.id.tvAT);
        tvTweet = (TextView)view.findViewById(R.id.tvTweet);
        tvTime = (TextView)view.findViewById(R.id.tvTime);
        ivReply = (ImageView)view.findViewById(R.id.ivReply);
        ivReply.setClickable(true);
        ivRetweet = (ImageView)view.findViewById(R.id.ivRetweet);
        ivRetweet.setClickable(true);
        ivFavorite = (ImageView)view.findViewById(R.id.ivFavorite);
        ivFavorite.setClickable(true);
        tvReplyCount = (TextView)view.findViewById(R.id.tvReplyCount);
        tvRetweetCount = (TextView)view.findViewById(R.id.tvRetweetCount);
        tvFavoriteCount = (TextView)view.findViewById(R.id.tvFavoriteCount);
    }
}
