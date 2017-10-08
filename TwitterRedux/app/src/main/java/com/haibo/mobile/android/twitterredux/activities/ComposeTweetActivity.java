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

package com.haibo.mobile.android.twitterredux.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.haibo.mobile.android.twitterredux.R;
import com.haibo.mobile.android.twitterredux.TwitterApplication;
import com.haibo.mobile.android.twitterredux.TwitterClient;
import com.haibo.mobile.android.twitterredux.models.Tweet;
import com.haibo.mobile.android.twitterredux.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import cz.msebera.android.httpclient.Header;
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

/**
 * Created by Haibo(Tristan) Yan on 9/30/17.
 */

public class ComposeTweetActivity extends AppCompatActivity {
    private static final int TWEET_LENGTH_MAX = 140;

    private EditText etTweet;

    private TextView tvWordNumber;

    private Button btnTweet;

    private ImageView iVClose;

    private TwitterClient client;

    private TextView tvUsername;

    private TextView tvScreenName;

    private TextView tvReplyTo;

    private ImageView ivUserPic;

    private User replyToUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Parcelable parcelable = getIntent().getParcelableExtra("replyToUser");

        if (parcelable != null) {
            setContentView(R.layout.retweet_composing);
            tvReplyTo = (TextView)findViewById(R.id.tvReplyTo);
            replyToUser = Parcels.unwrap(parcelable);
            tvReplyTo.setText("In reply to " + replyToUser.getScreenName());
        } else {
            setContentView(R.layout.tweet_composing);
        }

        iVClose = (ImageView)findViewById(R.id.ibClose);
        iVClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder adb = new AlertDialog.Builder(ComposeTweetActivity.this);
                adb.setMessage(R.string.save_draft);
                adb.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SharedPreferences pref =
                                PreferenceManager.getDefaultSharedPreferences(ComposeTweetActivity.this);
                        SharedPreferences.Editor edit = pref.edit();
                        edit.putString("draftTweet", etTweet.getText().toString());
                        edit.commit();
                        finish();
                    }
                });

                adb.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
                adb.show();
            }
        });

        etTweet = (EditText)findViewById(R.id.etTweet);


        if (parcelable != null) {
            etTweet.setText("@" + replyToUser.getScreenName());
        } else {
            SharedPreferences pref =
                    PreferenceManager.getDefaultSharedPreferences(ComposeTweetActivity.this);
            etTweet.setText(pref.getString("draftTweet", ""));
        }
        tvWordNumber = (TextView)findViewById(R.id.tvWordNumber);
        tvWordNumber.setText(String.valueOf(etTweet.getText().length()));
        btnTweet = (Button)findViewById(R.id.btnTweet);
        tvUsername = (TextView)findViewById(R.id.tvUsername);
        tvScreenName = (TextView)findViewById(R.id.tvScreenName);
        ivUserPic = (ImageView) findViewById(R.id.ivUserPic);
        btnTweet = (Button)findViewById(R.id.btnTweet);

        etTweet.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                int leftChars = TWEET_LENGTH_MAX - s.length();
                tvWordNumber.setText(String.valueOf(leftChars));
                if (leftChars < 0) {
                    etTweet.setTextColor(Color.RED);
                    btnTweet.setEnabled(false);
                    tvWordNumber.setTextColor(Color.BLUE);
                } else {
                    etTweet.setTextColor(Color.BLACK);
                    btnTweet.setEnabled(true);
                    tvWordNumber.setTextColor(Color.GRAY);
                }
            }
        });

        client = TwitterApplication.getRestClient();
        client.getCurrentUser(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.d("TwitterClient", response.toString());
                final JSONObject result = response;
                ComposeTweetActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            String userName = result.getString("name");
                            String screenName = result.getString("screen_name");
                            String profileURL = result.getString("profile_image_url");

                            tvUsername.setText(userName);
                            tvScreenName.setText("@" + screenName);
                            Picasso.with(ComposeTweetActivity.this.getBaseContext())
                                    .load(profileURL)
                                    .resize(80, 80)
                                    .transform(new RoundedCornersTransformation(10, 10))
                                    .into(ivUserPic);
                        } catch (JSONException e) {
                            Log.e("ERROR", e.toString());
                            ComposeTweetActivity.this.finish();
                        }
                    }
                });
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });

        btnTweet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tweetContent = etTweet.getText().toString();
                if (replyToUser != null) {
                    client.reply(tweetContent, replyToUser.getUid(), new JsonHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                            Intent data = new Intent();
                            try {
                                Tweet tweet = Tweet.fromJSON(response);
                                data.putExtra("newTweet", Parcels.wrap(tweet));
                                setResult(RESULT_OK, data);
                                finish();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                            Intent data = new Intent();
                            data.putExtra("error", errorResponse.toString());
                            setResult(RESULT_CANCELED, data);
                            finish();
                        }
                    });
                } else {
                    client.tweet(tweetContent, new JsonHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                            Intent data = new Intent();
                            try {
                                Tweet tweet = Tweet.fromJSON(response);
                                data.putExtra("newTweet", Parcels.wrap(tweet));
                                setResult(RESULT_OK, data);
                                finish();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                            Intent data = new Intent();
                            data.putExtra("error", errorResponse.toString());
                            setResult(RESULT_CANCELED, data);
                            finish();
                        }
                    });
                }
            }
        });
    }
}
