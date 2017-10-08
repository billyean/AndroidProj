package com.haibo.mobile.android.twitterredux.adapters;

import android.view.View;
import android.widget.VideoView;

import com.haibo.mobile.android.twitterredux.R;

/**
 * Created by Haibo(Tristan) Yan on 10/1/17.
 */

public class RetweetWithVideoViewHolder extends RetweetViewHolder {
    protected VideoView vvVideo;

    public RetweetWithVideoViewHolder(final View view, final TweetAdapter.TweetAdapterListener mListener) {
        super(view, mListener);
        vvVideo = (VideoView)view.findViewById(R.id.vvVideo);
    }
}
