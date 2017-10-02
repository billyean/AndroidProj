package com.codepath.apps.restclienttemplate.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.codepath.apps.restclienttemplate.TwitterApplication;
import com.codepath.apps.restclienttemplate.TwitterClient;
import com.codepath.apps.restclienttemplate.activities.ComposeTweetActivity;
import com.codepath.apps.restclienttemplate.models.Tweet;
import com.codepath.apps.restclienttemplate.R;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import java.util.List;

import cz.msebera.android.httpclient.Header;

import static com.codepath.apps.restclienttemplate.activities.TwitterActivity.RETWEET_REQUEST_CODE;

/**
 * Created by Haibo(Tristan) Yan on 10/1/17.
 */

public class ComplexRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int REQUEST_CODE = 40;

    private Activity activity;

    private List<Tweet> tweets;

    private final int TWEET_WITHOUT_MEDIA = 0, TWEET_WITH_MEDIA = 1, RETWEET_WITHOUT_MEDIA = 2,
            RETWEET_WITH_MEDIA = 3;

    public  ComplexRecyclerViewAdapter(Activity activity, List<Tweet> tweets) {
        this.activity = activity;
        this.tweets = tweets;
    }

    @Override
    public int getItemViewType(int position) {
        Tweet tweet = tweets.get(position);

        if (tweet.isRetweetedBy()) {
            if (null != tweet.getMediaURL()) {
                return RETWEET_WITH_MEDIA;
            } else {
                return RETWEET_WITHOUT_MEDIA;
            }
        } else {
            if (null != tweet.getMediaURL()) {
                return TWEET_WITH_MEDIA;
            } else {
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
                viewHolder = new RetweetWithMediaViewHolder(v1);
                break;
            case RETWEET_WITHOUT_MEDIA:
                View v2 = inflater.inflate(R.layout.item_retweet, parent, false);
                viewHolder = new RetweetViewHolder(v2);
                break;
            case TWEET_WITH_MEDIA:
                View v3 = inflater.inflate(R.layout.item_tweet_with_media, parent, false);
                viewHolder = new TweetWithMediaViewHolder(v3);
                break;
            default:
                View v4 = inflater.inflate(R.layout.item_tweet, parent, false);
                viewHolder = new TweetViewHolder(v4);
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
            case RETWEET_WITHOUT_MEDIA:
                RetweetViewHolder vh2 = (RetweetViewHolder)holder;
                configureRetweetViewHolder(vh2, position);
                break;
            case TWEET_WITH_MEDIA:
                TweetWithMediaViewHolder vh3 = (TweetWithMediaViewHolder)holder;
                configureTweetWithMediaViewHolder(vh3, position);
                break;
            default:
                TweetViewHolder vh4 = (TweetViewHolder)holder;
                configureTweetViewHolder(vh4, position);
                break;
        }
    }

    private void configureRetweetWithMediaViewHolder(RetweetWithMediaViewHolder holder, int position) {
        final Tweet tweet = tweets.get(position);
        final RetweetWithMediaViewHolder h = holder;
        holder.getTvRetweetedBy().setText(tweet.getRetweetedUser().getName() + " Retweeted");
        ImageView ivProfileImage = holder.getIvProfileImage();
        Picasso.with(activity).load(tweet.getUser().getProfileImageUrl()).into(ivProfileImage);

        holder.getTvUsername().setText(tweet.getUser().getName());
        holder.getTvAT().setText("@" + tweet.getUser().getScreenName());
        holder.getTvTweet().setText(tweet.getBody());

        holder.getTvTime().setText(tweet.getTimeBefore());
        Picasso.with(activity).load(tweet.getMediaURL()).into(holder.getIvMedia());
        if (tweet.isRetweeted()) {
            holder.getIbRetweet().setImageResource(R.drawable.retweeted);
        } else {
            holder.getIbRetweet().setImageResource(R.drawable.retweet);
        }

        if (tweet.isFavorited()) {
            holder.getIbFavorite().setImageResource(R.drawable.favorited);
        } else {
            holder.getIbFavorite().setImageResource(R.drawable.favorite);
        }

        if (0 != tweet.getRetweetCount())
            holder.getTvRetweetCount().setText(String.valueOf(tweet.getRetweetCount()));

        if (0 != tweet.getFavoriteCount())
            holder.getTvFavoriteCount().setText(String.valueOf(tweet.getFavoriteCount()));

        holder.getIbReply().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, ComposeTweetActivity.class);
                intent.putExtra("replyToUser", Parcels.wrap(tweet.getUser()));
                activity.startActivityForResult(intent, RETWEET_REQUEST_CODE);
            }
        });

        holder.getIbRetweet().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TwitterClient client = TwitterApplication.getRestClient();

                if (tweet.isRetweeted()) {
                    client.unRetweet(tweet, new JsonHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                            try {
                                TextView retweetCount = h.getTvRetweetCount();
                                tweet.setRetweetCount(tweet.getRetweetCount() - 1);
                                tweet.setRetweeted(false);
                                Tweet newRetweet = Tweet.fromJSON(response);
                                tweet.setUid(newRetweet.getUid());
                                retweetCount.setText(String.valueOf(tweet.getRetweetCount()));
                                h.getIbRetweet().setImageResource(R.drawable.retweet);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                            Toast.makeText(activity, errorResponse.toString(), Toast.LENGTH_LONG).show();
                        }
                    });
                } else {
                    client.retweet(tweet, new JsonHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                            TextView retweetCount = h.getTvRetweetCount();
                            tweet.setRetweetCount(tweet.getRetweetCount() + 1);
                            tweet.setRetweeted(true);
                            retweetCount.setText(String.valueOf(tweet.getRetweetCount()));
                            h.getIbRetweet().setImageResource(R.drawable.retweeted);
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                            Toast.makeText(activity, errorResponse.toString(), Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });

        holder.getIbFavorite().setOnClickListener(new View.OnClickListener() {
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
                            h.getIbFavorite().setImageResource(R.drawable.favorite);
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                            Toast.makeText(activity, errorResponse.toString(), Toast.LENGTH_LONG).show();
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
                            h.getIbFavorite().setImageResource(R.drawable.favorited);
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                            Toast.makeText(activity, errorResponse.toString(), Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });
    }

    private void configureRetweetViewHolder(RetweetViewHolder holder, int position) {
        final Tweet tweet = tweets.get(position);
        final RetweetViewHolder h = holder;
        holder.getTvRetweetedBy().setText(tweet.getRetweetedUser().getName() + " Retweeted");
        ImageView ivProfileImage = holder.getIvProfileImage();
        Picasso.with(activity).load(tweet.getUser().getProfileImageUrl()).into(ivProfileImage);

        holder.getTvUsername().setText(tweet.getUser().getName());
        holder.getTvAT().setText("@" + tweet.getUser().getScreenName());
        holder.getTvTweet().setText(tweet.getBody());

        holder.getTvTime().setText(tweet.getTimeBefore());
        if (tweet.isRetweeted()) {
            holder.getIbRetweet().setImageResource(R.drawable.retweeted);
        } else {
            holder.getIbRetweet().setImageResource(R.drawable.retweet);
        }

        if (tweet.isFavorited()) {
            holder.getIbFavorite().setImageResource(R.drawable.favorited);
        } else {
            holder.getIbFavorite().setImageResource(R.drawable.favorite);
        }

        if (0 != tweet.getRetweetCount())
            holder.getTvRetweetCount().setText(String.valueOf(tweet.getRetweetCount()));

        if (0 != tweet.getFavoriteCount())
            holder.getTvFavoriteCount().setText(String.valueOf(tweet.getFavoriteCount()));

        holder.getIbReply().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, ComposeTweetActivity.class);
                intent.putExtra("replyToUser", Parcels.wrap(tweet.getUser()));
                activity.startActivityForResult(intent, RETWEET_REQUEST_CODE);
            }
        });

        holder.getIbRetweet().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TwitterClient client = TwitterApplication.getRestClient();

                if (tweet.isRetweeted()) {
                    client.unRetweet(tweet, new JsonHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                            try {
                                TextView retweetCount = h.getTvRetweetCount();
                                tweet.setRetweetCount(tweet.getRetweetCount() - 1);
                                tweet.setRetweeted(false);
                                Tweet newRetweet = Tweet.fromJSON(response);
                                tweet.setUid(newRetweet.getUid());
                                retweetCount.setText(String.valueOf(tweet.getRetweetCount()));
                                h.getIbRetweet().setImageResource(R.drawable.retweet);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                            Toast.makeText(activity, errorResponse.toString(), Toast.LENGTH_LONG).show();
                        }
                    });
                } else {
                    client.retweet(tweet, new JsonHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                            TextView retweetCount = h.getTvRetweetCount();
                            tweet.setRetweetCount(tweet.getRetweetCount() + 1);
                            tweet.setRetweeted(true);
                            retweetCount.setText(String.valueOf(tweet.getRetweetCount()));
                            h.getIbRetweet().setImageResource(R.drawable.retweeted);
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                            Toast.makeText(activity, errorResponse.toString(), Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });

        holder.getIbFavorite().setOnClickListener(new View.OnClickListener() {
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
                            h.getIbFavorite().setImageResource(R.drawable.favorite);
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                            Toast.makeText(activity, errorResponse.toString(), Toast.LENGTH_LONG).show();
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
                            h.getIbFavorite().setImageResource(R.drawable.favorited);
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                            Toast.makeText(activity, errorResponse.toString(), Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });
    }

    private void configureTweetWithMediaViewHolder(TweetWithMediaViewHolder holder, int position) {
        final Tweet tweet = tweets.get(position);
        final TweetWithMediaViewHolder h = holder;
        ImageView ivProfileImage = holder.getIvProfileImage();
        Picasso.with(activity).load(tweet.getUser().getProfileImageUrl()).into(ivProfileImage);

        holder.getTvUsername().setText(tweet.getUser().getName());
        holder.getTvAT().setText("@" + tweet.getUser().getScreenName());
        holder.getTvTweet().setText(tweet.getBody());

        holder.getTvTime().setText(tweet.getTimeBefore());
        Picasso.with(activity).load(tweet.getMediaURL()).into(holder.getIvMedia());
        if (tweet.isRetweeted()) {
            holder.getIbRetweet().setImageResource(R.drawable.retweeted);
        } else {
            holder.getIbRetweet().setImageResource(R.drawable.retweet);
        }

        if (tweet.isFavorited()) {
            holder.getIbFavorite().setImageResource(R.drawable.favorited);
        } else {
            holder.getIbFavorite().setImageResource(R.drawable.favorite);
        }
        if (0 != tweet.getRetweetCount())
            holder.getTvRetweetCount().setText(String.valueOf(tweet.getRetweetCount()));

        if (0 != tweet.getFavoriteCount())
            holder.getTvFavoriteCount().setText(String.valueOf(tweet.getFavoriteCount()));

        holder.getIbReply().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, ComposeTweetActivity.class);
                intent.putExtra("replyToUser", Parcels.wrap(tweet.getUser()));
                activity.startActivityForResult(intent, RETWEET_REQUEST_CODE);
            }
        });

        holder.getIbRetweet().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TwitterClient client = TwitterApplication.getRestClient();

                if (tweet.isRetweeted()) {
                    client.unRetweet(tweet, new JsonHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                            try {
                                TextView retweetCount = h.getTvRetweetCount();
                                tweet.setRetweetCount(tweet.getRetweetCount() - 1);
                                tweet.setRetweeted(false);
                                Tweet newRetweet = Tweet.fromJSON(response);
                                tweet.setUid(newRetweet.getUid());
                                retweetCount.setText(String.valueOf(tweet.getRetweetCount()));
                                h.getIbRetweet().setImageResource(R.drawable.retweet);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                            Toast.makeText(activity, errorResponse.toString(), Toast.LENGTH_LONG).show();
                        }
                    });
                } else {
                    client.retweet(tweet, new JsonHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                            TextView retweetCount = h.getTvRetweetCount();
                            tweet.setRetweetCount(tweet.getRetweetCount() + 1);
                            tweet.setRetweeted(true);
                            retweetCount.setText(String.valueOf(tweet.getRetweetCount()));
                            h.getIbRetweet().setImageResource(R.drawable.retweeted);
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                            Toast.makeText(activity, errorResponse.toString(), Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });

        holder.getIbFavorite().setOnClickListener(new View.OnClickListener() {
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
                            h.getIbFavorite().setImageResource(R.drawable.favorite);
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                            Toast.makeText(activity, errorResponse.toString(), Toast.LENGTH_LONG).show();
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
                            h.getIbFavorite().setImageResource(R.drawable.favorited);
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                            Toast.makeText(activity, errorResponse.toString(), Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });
    }

    private void configureTweetViewHolder(TweetViewHolder holder, int position) {
        final Tweet tweet = tweets.get(position);
        final TweetViewHolder h = holder;
        ImageView ivProfileImage = holder.getIvProfileImage();
        Picasso.with(activity).load(tweet.getUser().getProfileImageUrl()).into(ivProfileImage);

        holder.getTvUsername().setText(tweet.getUser().getName());
        holder.getTvAT().setText("@" + tweet.getUser().getScreenName());
        holder.getTvTweet().setText(tweet.getBody());

        holder.getTvTime().setText(tweet.getTimeBefore());
        if (tweet.isRetweeted()) {
            holder.getIbRetweet().setImageResource(R.drawable.retweeted);
        } else {
            holder.getIbRetweet().setImageResource(R.drawable.retweet);
        }

        if (tweet.isFavorited()) {
            holder.getIbFavorite().setImageResource(R.drawable.favorited);
        } else {
            holder.getIbFavorite().setImageResource(R.drawable.favorite);
        }

        if (0 != tweet.getRetweetCount())
            holder.getTvRetweetCount().setText(String.valueOf(tweet.getRetweetCount()));

        if (0 != tweet.getFavoriteCount())
            holder.getTvFavoriteCount().setText(String.valueOf(tweet.getFavoriteCount()));

        holder.getIbReply().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, ComposeTweetActivity.class);
                intent.putExtra("replyToUser", Parcels.wrap(tweet.getUser()));
                activity.startActivityForResult(intent, RETWEET_REQUEST_CODE);
            }
        });

        holder.getIbRetweet().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TwitterClient client = TwitterApplication.getRestClient();

                if (tweet.isRetweeted()) {
                    Log.i("INFO", "call setOnClickListener 4 - unRetweet");
                    Log.i("INFO", "tweet id : " + tweet.getUid());
                    Log.i("INFO", "isRetweeted : " + tweet.isRetweeted());
                    client.unRetweet(tweet, new JsonHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                            TextView retweetCount = h.getTvRetweetCount();
                            tweet.setRetweetCount(tweet.getRetweetCount() - 1);
                            tweet.setRetweeted(false);
                            retweetCount.setText(String.valueOf(tweet.getRetweetCount()));
                            h.getIbRetweet().setImageResource(R.drawable.retweet);
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                            Toast.makeText(activity, errorResponse.toString(), Toast.LENGTH_LONG).show();
                        }
                    });
                } else {
                    client.retweet(tweet, new JsonHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                            try {
                                TextView retweetCount = h.getTvRetweetCount();
                                tweet.setRetweetCount(tweet.getRetweetCount() + 1);
                                Log.i("INFO", "retweet id : " + tweet.getUid());
                                Tweet newRetweet = Tweet.fromJSON(response);
                                Log.i("INFO", "new tweet id : " + newRetweet.getUid());
                                tweet.setUid(newRetweet.getUid());
                                tweet.setRetweeted(true);
                                retweetCount.setText(String.valueOf(tweet.getRetweetCount()));
//                                Picasso.with(activity).load(R.drawable.retweeted).into(h.getIbRetweet());
                                h.getIbRetweet().setImageResource(R.drawable.retweeted);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                            Toast.makeText(activity, errorResponse.toString(), Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });

        holder.getIbFavorite().setOnClickListener(new View.OnClickListener() {
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
                            h.getIbFavorite().setImageResource(R.drawable.favorite);
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                            Toast.makeText(activity, errorResponse.toString(), Toast.LENGTH_LONG).show();
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
                            h.getIbFavorite().setImageResource(R.drawable.favorited);
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                            Toast.makeText(activity, errorResponse.toString(), Toast.LENGTH_LONG).show();
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
