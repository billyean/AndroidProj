package com.codepath.apps.restclienttemplate.activities;

import android.support.v4.app.DialogFragment;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.restclienttemplate.TwitterClient;
import com.codepath.apps.restclienttemplate.models.User;

/**
 * Created by Haibo(Tristan) Yan on 10/1/17.
 */

public class ComposeTweetDialogFragment extends DialogFragment {
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
}
