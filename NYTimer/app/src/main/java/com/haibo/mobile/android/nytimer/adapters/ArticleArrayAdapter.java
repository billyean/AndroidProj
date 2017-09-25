package com.haibo.mobile.android.nytimer.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.haibo.mobile.android.nytimer.R;
import com.haibo.mobile.android.nytimer.models.Article;
//import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Haibo(Tristan) Yan on 9/23/17.
 */

public class ArticleArrayAdapter extends ArrayAdapter<Article> {
    public ArticleArrayAdapter(@NonNull Context context,
                               @NonNull List<Article> objects) {
        super(context, android.R.layout.simple_list_item_1, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Article article = this.getItem(position);

        if (null == convertView) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item_article_without_thumbnail, parent, false);
        }

        ImageView imageView = (ImageView)convertView.findViewById(R.id.ivImage);
        imageView.setImageResource(0);

        if (!article.getThumbnail().isEmpty()) {
            imageView.getLayoutParams().width = 200;
            imageView.getLayoutParams().height = 200;
//            Picasso.with(getContext()).load(article.getThumbnail()).into(imageView);
            Glide.with(getContext()).load(article.getThumbnail()).into(imageView);
        }

        TextView tvTitle = (TextView)convertView.findViewById(R.id.tvTitle);
        tvTitle.setText(article.getHeadline());

        TextView tvSnippet = (TextView)convertView.findViewById(R.id.tvSnippet);
        tvSnippet.setText(article.getSnippet());

        return convertView;
    }
}
