package com.codepath.apps.restclienttemplate.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.restclienttemplate.R;

import org.w3c.dom.Text;

/**
 * Created by Haibo(Tristan) Yan on 10/1/17.
 */

public class RetweetWithMediaViewHolder extends RecyclerView.ViewHolder {
    private TextView tvRetweetedBy;

    private ImageView ivProfileImage;

    private TextView tvUsername;

    private TextView tvAT;

    private TextView tvTweet;

    private TextView tvTime;

    private ImageView ivMedia;

    private ImageButton ibReply;

    private ImageButton ibRetweet;

    private ImageButton ibFavorite;

    private TextView tvReplyCount;

    private TextView tvRetweetCount;

    private TextView tvFavoriteCount;

    public RetweetWithMediaViewHolder(View view) {
        super(view);

        tvRetweetedBy = (TextView)view.findViewById(R.id.tvRetweetedBy);
        ivProfileImage = (ImageView)view.findViewById(R.id.ivProfileImage);
        tvUsername = (TextView)view.findViewById(R.id.tvUsername);
        tvAT = (TextView)view.findViewById(R.id.tvAT);
        tvTweet = (TextView)view.findViewById(R.id.tvTweet);
        tvTime = (TextView)view.findViewById(R.id.tvTime);
        ivMedia = (ImageView)view.findViewById(R.id.ivMedia);
        ibReply = (ImageButton)view.findViewById(R.id.ibReply);
        ibRetweet = (ImageButton)view.findViewById(R.id.ibRetweet);
        ibFavorite = (ImageButton)view.findViewById(R.id.ibFavorite);
        tvReplyCount = (TextView)view.findViewById(R.id.tvReplyCount);
        tvRetweetCount = (TextView)view.findViewById(R.id.tvRetweetCount);
        tvFavoriteCount = (TextView)view.findViewById(R.id.tvFavoriteCount);
    }

    public TextView getTvRetweetedBy() {
        return tvRetweetedBy;
    }

    public void setTvRetweetedBy(TextView tvRetweetedBy) {
        this.tvRetweetedBy = tvRetweetedBy;
    }

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

    public ImageView getIvMedia() {
        return ivMedia;
    }

    public void setIvMedia(ImageView ivMedia) {
        this.ivMedia = ivMedia;
    }

    public ImageButton getIbReply() {
        return ibReply;
    }

    public void setIbReply(ImageButton ibReply) {
        this.ibReply = ibReply;
    }

    public ImageButton getIbRetweet() {
        return ibRetweet;
    }

    public void setIbRetweet(ImageButton ibRetweet) {
        this.ibRetweet = ibRetweet;
    }

    public ImageButton getIbFavorite() {
        return ibFavorite;
    }

    public void setIbFavorite(ImageButton ibFavorite) {
        this.ibFavorite = ibFavorite;
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
}
