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

package com.haibo.mobile.android.twitterredux.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.haibo.mobile.android.twitterredux.R;

/**
 * Created by Haibo(Tristan) Yan on 10/8/17.
 */

public class MessageViewHolder extends RecyclerView.ViewHolder {
    private ImageView ivProfileImage;

    private TextView tvUserName;

    private TextView tvAT;

    private TextView tvMessage;

    private TextView tvTime;

    public MessageViewHolder(View itemView) {
        super(itemView);

        ivProfileImage = (ImageView)itemView.findViewById(R.id.ivProfileImage);
        tvUserName = (TextView)itemView.findViewById(R.id.tvUserName);
        tvAT = (TextView)itemView.findViewById(R.id.tvAT);
        tvMessage = (TextView)itemView.findViewById(R.id.tvMessage);
        tvTime = (TextView)itemView.findViewById(R.id.tvTime);
    }

    public ImageView getIvProfileImage() {
        return ivProfileImage;
    }

    public void setIvProfileImage(ImageView ivProfileImage) {
        this.ivProfileImage = ivProfileImage;
    }

    public TextView getTvUserName() {
        return tvUserName;
    }

    public void setTvUserName(TextView tvUserName) {
        this.tvUserName = tvUserName;
    }

    public TextView getTvAT() {
        return tvAT;
    }

    public void setTvAT(TextView tvAT) {
        this.tvAT = tvAT;
    }

    public TextView getTvMessage() {
        return tvMessage;
    }

    public void setTvMessage(TextView tvMessage) {
        this.tvMessage = tvMessage;
    }

    public TextView getTvTime() {
        return tvTime;
    }

    public void setTvTime(TextView tvTime) {
        this.tvTime = tvTime;
    }
}
