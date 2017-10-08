package com.haibo.mobile.android.twitterredux.adapters;

import android.view.View;
import android.widget.ImageView;

import com.haibo.mobile.android.twitterredux.R;

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
