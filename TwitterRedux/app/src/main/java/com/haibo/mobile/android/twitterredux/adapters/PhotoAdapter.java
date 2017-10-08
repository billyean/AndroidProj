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

import com.haibo.mobile.android.twitterredux.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Haibo(Tristan) Yan on 10/8/17.
 */

public class PhotoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<String> photoImageURLs;

    private Context context;

    public PhotoAdapter(Context context, List<String> photoImageURLs){
        this.context = context;
        this.photoImageURLs = photoImageURLs;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v1 = inflater.inflate(R.layout.item_photo, parent, false);
        return new PhotoViewHolder(v1);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        PhotoViewHolder photoViewHolder = (PhotoViewHolder)holder;
        ImageView ivPhoto = photoViewHolder.getIvPhoto();
        Picasso.with(context).load(photoImageURLs.get(position)).into(ivPhoto);
    }

    @Override
    public int getItemCount() {
        return photoImageURLs.size();
    }
}
