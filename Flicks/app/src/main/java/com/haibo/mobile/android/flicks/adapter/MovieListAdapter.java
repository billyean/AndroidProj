/*
 *     Copyright [2017] [Haibo(Tristan) Yan]
 *
 *     Licensed under the Apache License, Version 2.0 (the "License");
 *     you may not use this file except in compliance with the License.
 *     You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *     Unless required by applicable law or agreed to in writing, software
 *     distributed under the License is distributed on an "AS IS" BASIS,
 *     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *     See the License for the specific language governing permissions and
 *     limitations under the License.
 */
package com.haibo.mobile.android.flicks.adapter;

import android.content.Context;
import android.provider.ContactsContract;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.haibo.mobile.android.flicks.R;
import com.haibo.mobile.android.flicks.model.Movie;
import com.squareup.picasso.Picasso;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

import java.util.List;

import static android.content.res.Configuration.ORIENTATION_LANDSCAPE;

/**
 * Created by Haibo(Tristan) Yan on 9/15/17.
 */

public class MovieListAdapter extends ArrayAdapter<Movie> {
    private Context context;

    public MovieListAdapter(@NonNull Context context, @NonNull List<Movie> movies) {
        super(context, R.layout.movie, movies);
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Movie movie = getItem(position);
        ViewHolder viewHolder1 = null;
        BackdropViewHolder viewHolder2 = null;
        View view1 = null, view2 = null;

        if (null == convertView) {
            if (movie.showBackdrop()) {
                view2 = LayoutInflater.from(getContext()).inflate(R.layout.backdrop, null);
                viewHolder2 = new BackdropViewHolder();
                viewHolder2.ivBackdrop = (ImageView) view2.findViewById(R.id.ivBackdrop);
                view2.setTag(R.layout.backdrop, viewHolder2);

                if (context.getResources().getConfiguration().orientation == ORIENTATION_LANDSCAPE) {
                    viewHolder2.tvTitle = (TextView) view2.findViewById(R.id.tvTitle);
                    viewHolder2.tvOverview = (TextView) view2.findViewById(R.id.tvOverview);
                }

                convertView = view2;
            } else {
                view1 = LayoutInflater.from(getContext()).inflate(R.layout.movie, null);
                viewHolder1 = new ViewHolder();
                viewHolder1.ivMovie = (ImageView) view1.findViewById(R.id.ivMovie);
                viewHolder1.tvTitle = (TextView) view1.findViewById(R.id.tvTitle);
                viewHolder1.tvOverview = (TextView) view1.findViewById(R.id.tvOverview);
                view1.setTag(R.layout.movie, viewHolder1);
                convertView = view1;
            }
        } else {
            if (movie.showBackdrop()) {
                view2 = convertView;
                if (view2.getTag(R.layout.backdrop) == null) {
                    view2 = LayoutInflater.from(getContext()).inflate(R.layout.backdrop, null);
                    viewHolder2 = new BackdropViewHolder();
                    viewHolder2.ivBackdrop = (ImageView) view2.findViewById(R.id.ivBackdrop);
                    view2.setTag(R.layout.backdrop, viewHolder2);
                    convertView = view2;
                    if (context.getResources().getConfiguration().orientation == ORIENTATION_LANDSCAPE) {
                        viewHolder2.tvTitle = (TextView) view2.findViewById(R.id.tvTitle);
                        viewHolder2.tvOverview = (TextView) view2.findViewById(R.id.tvOverview);
                    }
                }
                viewHolder2 = (BackdropViewHolder) view2.getTag(R.layout.backdrop);
            } else {
                view1 = convertView;
                if (view1.getTag(R.layout.movie) == null) {
                    view1 = LayoutInflater.from(getContext()).inflate(R.layout.movie, null);
                    viewHolder1 = new ViewHolder();
                    viewHolder1.ivMovie = (ImageView) view1.findViewById(R.id.ivMovie);
                    viewHolder1.tvTitle = (TextView) view1.findViewById(R.id.tvTitle);
                    viewHolder1.tvOverview = (TextView) view1.findViewById(R.id.tvOverview);
                    view1.setTag(R.layout.movie, viewHolder1);
                    convertView = view1;
                }
                viewHolder1 = (ViewHolder) view1.getTag(R.layout.movie);
            }
        }

        Picasso.with(context).load(movie.showBackdrop() ? movie.getBackdropPath() : movie.getPosterPath())
                .placeholder(R.drawable.default_movie)
                .transform(new RoundedCornersTransformation(10, 10))
                .into(movie.showBackdrop() ? viewHolder2.ivBackdrop : viewHolder1.ivMovie);

        if (movie.showBackdrop()) {
            if (context.getResources().getConfiguration().orientation == ORIENTATION_LANDSCAPE) {
                viewHolder2.tvTitle.setText(movie.getTitle());
                viewHolder2.tvOverview.setText(movie.getOverview());
            }
        } else {
            viewHolder1.tvTitle.setText(movie.getTitle());
            viewHolder1.tvOverview.setText(movie.getOverview());
        }
        return convertView;
    }

    static class ViewHolder {
        ImageView ivMovie;

        TextView tvTitle;

        TextView tvOverview;
    }

    static class BackdropViewHolder {
        ImageView ivBackdrop;

        TextView tvTitle;

        TextView tvOverview;
    }
}
