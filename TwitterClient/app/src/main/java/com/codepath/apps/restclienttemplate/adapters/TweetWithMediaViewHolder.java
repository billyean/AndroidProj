package com.codepath.apps.restclienttemplate.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.restclienttemplate.R;

/**
 * Created by Haibo(Tristan) Yan on 10/1/17.
 */

public class TweetWithMediaViewHolder extends TweetViewHolder {
    protected ImageView ivMedia;

    public TweetWithMediaViewHolder(View view) {
        super(view);
        ivMedia = (ImageView)view.findViewById(R.id.ivMedia);
    }

    public ImageView getIvMedia() {
        return ivMedia;
    }

    public void setIvMedia(ImageView ivMedia) {
        this.ivMedia = ivMedia;
    }

}
