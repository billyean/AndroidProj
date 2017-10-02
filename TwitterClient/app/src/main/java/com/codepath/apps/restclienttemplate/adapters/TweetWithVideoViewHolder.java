package com.codepath.apps.restclienttemplate.adapters;

import android.view.View;
import android.widget.VideoView;

import com.codepath.apps.restclienttemplate.R;
/**
 * Created by Haibo(Tristan) Yan on 10/1/17.
 */

public class TweetWithVideoViewHolder extends TweetViewHolder {
    protected VideoView vvVideo;

    public TweetWithVideoViewHolder(View view) {
        super(view);
        vvVideo = (VideoView)view.findViewById(R.id.vvVideo);
    }
}
