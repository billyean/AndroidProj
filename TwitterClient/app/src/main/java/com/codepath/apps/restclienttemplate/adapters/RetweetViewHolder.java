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

public class RetweetViewHolder extends TweetViewHolder {
    protected TextView tvRetweetedBy;

    public RetweetViewHolder(View view) {
        super(view);
        tvRetweetedBy = (TextView)view.findViewById(R.id.tvRetweetedBy);
    }

    public TextView getTvRetweetedBy() {
        return tvRetweetedBy;
    }

    public void setTvRetweetedBy(TextView tvRetweetedBy) {
        this.tvRetweetedBy = tvRetweetedBy;
    }
}
