package com.haibo.mobile.android.twitterredux.adapters;

import android.view.View;
import android.widget.TextView;

import com.haibo.mobile.android.twitterredux.R;

/**
 * Created by Haibo(Tristan) Yan on 10/1/17.
 */

public class RetweetViewHolder extends TweetViewHolder {
    protected TextView tvRetweetedBy;

    public RetweetViewHolder(final View view, final TweetAdapter.TweetAdapterListener mListener) {
        super(view, mListener);
        tvRetweetedBy = (TextView)view.findViewById(R.id.tvRetweetedBy);
    }

    public TextView getTvRetweetedBy() {
        return tvRetweetedBy;
    }

    public void setTvRetweetedBy(TextView tvRetweetedBy) {
        this.tvRetweetedBy = tvRetweetedBy;
    }
}
