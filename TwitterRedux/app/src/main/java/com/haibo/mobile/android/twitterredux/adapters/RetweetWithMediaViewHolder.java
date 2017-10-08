package com.haibo.mobile.android.twitterredux.adapters;

import android.view.View;
import android.widget.ImageView;

import com.haibo.mobile.android.twitterredux.R;

/**
 * Created by Haibo(Tristan) Yan on 10/1/17.
 */

public class RetweetWithMediaViewHolder extends RetweetViewHolder {
    protected ImageView ivMedia;

    public RetweetWithMediaViewHolder(final View view, final TweetAdapter.TweetAdapterListener mListener) {
        super(view, mListener);
        ivMedia = (ImageView)view.findViewById(R.id.ivMedia);
    }

    public ImageView getIvMedia() {
        return ivMedia;
    }

    public void setIvMedia(ImageView ivMedia) {
        this.ivMedia = ivMedia;
    }
}
