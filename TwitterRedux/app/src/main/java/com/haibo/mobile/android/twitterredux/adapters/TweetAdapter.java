package com.haibo.mobile.android.twitterredux.adapters;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.haibo.mobile.android.twitterredux.R;
import com.haibo.mobile.android.twitterredux.TwitterApplication;
import com.haibo.mobile.android.twitterredux.TwitterClient;
import com.haibo.mobile.android.twitterredux.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * Created by Haibo(Tristan) Yan on 10/1/17.
 */

public class TweetAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int REQUEST_CODE = 40;

    private Fragment fragment;

    private List<Tweet> tweets;

    private TweetAdapterListener mListener;

    private final int TWEET_WITHOUT_MEDIA = 0, TWEET_WITH_MEDIA = 1, TWEET_WITH_VIDEO = 2, RETWEET_WITHOUT_MEDIA = 3,
            RETWEET_WITH_MEDIA = 4, RETWEET_WITH_VIDEO = 5;

    public interface TweetAdapterListener {
        public void onItemSelected(View view, int position);
    }

    public TweetAdapter(Fragment fragment, List<Tweet> tweets, TweetAdapterListener listener) {
        this.fragment = fragment;
        this.tweets = tweets;
        this.mListener = listener;
    }

    @Override
    public int getItemViewType(int position) {
        Tweet tweet = tweets.get(position);

        if (tweet.isRetweetedBy()) {
            if (null != tweet.getMediaURL()) {
                return RETWEET_WITH_MEDIA;
            } else if (null != tweet.getVideoURL()){
                return RETWEET_WITH_VIDEO;
            } else {
                return RETWEET_WITHOUT_MEDIA;
            }
        } else {
            if (null != tweet.getMediaURL()) {
                return TWEET_WITH_MEDIA;
            } else if (null != tweet.getVideoURL()){
                return TWEET_WITH_VIDEO;
            }  else {
                return TWEET_WITHOUT_MEDIA;
            }
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType) {
            case RETWEET_WITH_MEDIA:
                View v1 = inflater.inflate(R.layout.item_retweet_with_media, parent, false);
                viewHolder = new RetweetWithMediaViewHolder(v1, mListener);
                break;
            case RETWEET_WITH_VIDEO:
                View v2 = inflater.inflate(R.layout.item_retweet_with_video, parent, false);
                viewHolder = new RetweetWithVideoViewHolder(v2, mListener);
                break;
            case RETWEET_WITHOUT_MEDIA:
                View v3 = inflater.inflate(R.layout.item_retweet, parent, false);
                viewHolder = new RetweetViewHolder(v3, mListener);
                break;
            case TWEET_WITH_MEDIA:
                View v4 = inflater.inflate(R.layout.item_tweet_with_media, parent, false);
                viewHolder = new TweetWithMediaViewHolder(v4, mListener);
                break;
            case TWEET_WITH_VIDEO:
                View v5 = inflater.inflate(R.layout.item_tweet_with_video, parent, false);
                viewHolder = new TweetWithVideoViewHolder(v5, mListener);
                break;
            default:
                View v6 = inflater.inflate(R.layout.item_tweet, parent, false);
                viewHolder = new TweetViewHolder(v6, mListener);
                break;
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case RETWEET_WITH_MEDIA:
                RetweetWithMediaViewHolder vh1 = (RetweetWithMediaViewHolder)holder;
                configureRetweetWithMediaViewHolder(vh1, position);
                break;
            case RETWEET_WITH_VIDEO:
                RetweetWithVideoViewHolder vh2 = (RetweetWithVideoViewHolder)holder;
                configureRetweetWithVideoViewHolder(vh2, position);
                break;
            case RETWEET_WITHOUT_MEDIA:
                RetweetViewHolder vh3 = (RetweetViewHolder)holder;
                configureRetweetViewHolder(vh3, position);
                break;
            case TWEET_WITH_MEDIA:
                TweetWithMediaViewHolder vh4 = (TweetWithMediaViewHolder)holder;
                configureTweetWithMediaViewHolder(vh4, position);
                break;
            case TWEET_WITH_VIDEO:
                TweetWithVideoViewHolder vh5 = (TweetWithVideoViewHolder)holder;
                configureTweetWithVideoViewHolder(vh5, position);
                break;
            default:
                TweetViewHolder vh6 = (TweetViewHolder)holder;
                configureTweetViewHolder(vh6, position);
                break;
        }
    }

    private void configureRetweetWithMediaViewHolder(RetweetWithMediaViewHolder holder, int position) {
        configureRetweetViewHolder(holder, position);
        Tweet tweet = tweets.get(position);
        Picasso.with(fragment.getContext()).load(tweet.getMediaURL()).into(holder.getIvMedia());

    }

    private void configureRetweetViewHolder(RetweetViewHolder holder, int position) {
        configureTweetViewHolder(holder, position);
        Tweet tweet = tweets.get(position);
        holder.getTvRetweetedBy().setText(tweet.getRetweetedUser().getName() + " Retweeted");
    }

    private void configureRetweetWithVideoViewHolder(RetweetWithVideoViewHolder holder, int position) {
        try {
            configureRetweetViewHolder(holder, position);
            Tweet tweet = tweets.get(position);
            holder.vvVideo.setVideoPath(tweet.getVideoURL());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void configureTweetWithMediaViewHolder(TweetWithMediaViewHolder holder, int position) {
        configureTweetViewHolder(holder, position);
        Tweet tweet = tweets.get(position);
        Picasso.with(fragment.getContext()).load(tweet.getMediaURL()).into(holder.getIvMedia());
    }

    private void configureTweetWithVideoViewHolder(TweetWithVideoViewHolder holder, int position) {
        try {
            configureTweetViewHolder(holder, position);
            Tweet tweet = tweets.get(position);
            holder.vvVideo.setVideoPath(tweet.getVideoURL());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void configureTweetViewHolder(TweetViewHolder holder, int position) {
        final Tweet tweet = tweets.get(position);
        final TweetViewHolder h = holder;
        ImageView ivProfileImage = holder.getIvProfileImage();
        Picasso.with(fragment.getContext()).load(tweet.getUser().getProfileImageUrl()).into(ivProfileImage);

        holder.getTvUsername().setText(tweet.getUser().getName());
        holder.getTvAT().setText("@" + tweet.getUser().getScreenName());
        holder.getTvTweet().setText(tweet.getBody());

        holder.getTvTime().setText(tweet.getTimeBefore());
        if (tweet.isRetweeted()) {
            holder.getIvRetweet().setImageResource(R.drawable.retweeted);
        } else {
            holder.getIvRetweet().setImageResource(R.drawable.retweet);
        }

        if (tweet.isFavorited()) {
            holder.getIvFavorite().setImageResource(R.drawable.favorited);
        } else {
            holder.getIvFavorite().setImageResource(R.drawable.favorite);
        }

        if (0 != tweet.getRetweetCount())
            holder.getTvRetweetCount().setText(String.valueOf(tweet.getRetweetCount()));

        if (0 != tweet.getFavoriteCount())
            holder.getTvFavoriteCount().setText(String.valueOf(tweet.getFavoriteCount()));

//        holder.getIvReply().setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(activity, ComposeTweetActivity.class);
//                intent.putExtra("replyToUser", Parcels.wrap(tweet.getUser()));
//                activity.startActivityForResult(intent, RETWEET_REQUEST_CODE);
//            }
//        });

        holder.getIvRetweet().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TwitterClient client = TwitterApplication.getRestClient();

                if (tweet.isRetweeted()) {
                    client.unRetweet(tweet, new JsonHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                            TextView retweetCount = h.getTvRetweetCount();
                            tweet.setRetweetCount(tweet.getRetweetCount() - 1);
                            tweet.setRetweeted(false);
                            retweetCount.setText(String.valueOf(tweet.getRetweetCount()));
                            h.getIvRetweet().setImageResource(R.drawable.retweet);
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                            Toast.makeText(fragment.getContext(), errorResponse.toString(), Toast.LENGTH_LONG).show();
                        }
                    });
                } else {
                    client.retweet(tweet, new JsonHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                            try {
                                TextView retweetCount = h.getTvRetweetCount();
                                tweet.setRetweetCount(tweet.getRetweetCount() + 1);
                                Tweet newRetweet = Tweet.fromJSON(response);
                                tweet.setRetweetId(newRetweet.getUid());
                                tweet.setRetweeted(true);
                                retweetCount.setText(String.valueOf(tweet.getRetweetCount()));
                                h.getIvRetweet().setImageResource(R.drawable.retweeted);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                            Toast.makeText(fragment.getContext(), errorResponse.toString(), Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });

        holder.getIvFavorite().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TwitterClient client = TwitterApplication.getRestClient();

                if (tweet.isFavorited()) {
                    client.unFavorite(tweet, new JsonHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                            TextView favoriteCount = h.getTvFavoriteCount();
                            tweet.setFavoriteCount(tweet.getFavoriteCount() - 1);
                            favoriteCount.setText(String.valueOf(tweet.getFavoriteCount()));
                            h.getIvFavorite().setImageResource(R.drawable.favorite);
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                            Toast.makeText(fragment.getContext(), errorResponse.toString(), Toast.LENGTH_LONG).show();
                        }
                    });
                } else {
                    client.favorite(tweet, new JsonHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                            TextView favoriteCount = h.getTvFavoriteCount();
                            tweet.setFavoriteCount(tweet.getFavoriteCount() + 1);
                            tweet.setFavorited(true);
                            favoriteCount.setText(String.valueOf(tweet.getFavoriteCount()));
                            h.getIvFavorite().setImageResource(R.drawable.favorited);
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                            Toast.makeText(fragment.getContext(), errorResponse.toString(), Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return tweets.size();
    }
}
