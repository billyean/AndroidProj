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

import java.util.List;

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
        ViewHolder viewHolder;
        View view;

        if (null == convertView) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.movie, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.ivMovie = (ImageView)view.findViewById(R.id.ivMovie);
            viewHolder.tvTitle = (TextView)view.findViewById(R.id.tvTitle);
            viewHolder.tvOverview = (TextView)view.findViewById(R.id.tvOverview);
            view.setTag(R.layout.movie, viewHolder);
        } else {
            view = convertView;
            viewHolder = (ViewHolder)view.getTag(R.layout.movie);
        }

        Picasso.with(context).load(movie.getPosterPath()).into(viewHolder.ivMovie);
        viewHolder.tvTitle.setText(movie.getTitle());
        viewHolder.tvOverview.setText(movie.getOverview());
        return view;
    }

    static class ViewHolder {
        ImageView ivMovie;

        TextView tvTitle;

        TextView tvOverview;
    }
}
