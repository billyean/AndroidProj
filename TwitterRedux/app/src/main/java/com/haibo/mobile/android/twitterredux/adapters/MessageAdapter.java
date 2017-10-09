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

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.haibo.mobile.android.twitterredux.R;
import com.haibo.mobile.android.twitterredux.models.Message;
import com.haibo.mobile.android.twitterredux.models.Tweet;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Haibo(Tristan) Yan on 10/8/17.
 */

public class MessageAdapter extends RecyclerView.Adapter {
    private List<Message> messages;

    private Context context;

    public MessageAdapter(Context context, List<Message> messages) {
        this.context = context;
        this.messages = messages;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_message, parent, false);
        MessageViewHolder viewHolder = new MessageViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MessageViewHolder viewHolder = (MessageViewHolder)holder;
        Message message = messages.get(position);
        Picasso.with(context).load(message.getRecipient().getProfileImageUrl()).into(viewHolder.getIvProfileImage());
        viewHolder.getTvUserName().setText(message.getRecipient().getName());
        viewHolder.getTvAT().setText("@" + message.getRecipient().getScreenName());
        viewHolder.getTvTime().setText(message.getTimeBefore());
        viewHolder.getTvMessage().setText(message.getText());
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }
}
