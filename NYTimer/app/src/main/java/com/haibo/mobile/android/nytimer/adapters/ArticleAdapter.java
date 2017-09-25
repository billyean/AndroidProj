package com.haibo.mobile.android.nytimer.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.haibo.mobile.android.nytimer.R;
import com.haibo.mobile.android.nytimer.activities.ArticleActivity;
import com.haibo.mobile.android.nytimer.models.Article;
//import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Haibo(Tristan) Yan on 9/24/17.
 */

public class ArticleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int WITH_THUMBNAIL = 0, WITHOUT_THUMBNAIL = 1;

    private List<Article> articles;

    private Context context;

    public ArticleAdapter(Context context, List<Article> articles) {
        this.context = context;
        this.articles = articles;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView;

        switch (viewType) {
            case WITH_THUMBNAIL:
                contactView = inflater.inflate(R.layout.item_article_with_thumbnail, parent, false);
                viewHolder = new ViewHolderWithThumbnail(contactView);
                break;
            default:
                contactView = inflater.inflate(R.layout.item_article_without_thumbnail, parent, false);
                viewHolder = new ViewHolderWithoutThumbnail(contactView);
                break;
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        final Article article = articles.get(position);

        if (!article.getThumbnail().isEmpty()) {
            ViewHolderWithThumbnail withThumbnail = (ViewHolderWithThumbnail)viewHolder;

            ImageView ivArticle = withThumbnail.ivArticle;
//            Picasso.with(context).load(article.getThumbnail()).into(ivArticle);
            Glide.with(context).load(article.getThumbnail()).into(ivArticle);

            TextView tvTitle = withThumbnail.tvTitle;
            tvTitle.setText(article.getHeadline());

            TextView tvSnippet = withThumbnail.tvSnippet;;
            tvSnippet.setText(article.getSnippet());
        } else {
            ViewHolderWithoutThumbnail withoutThumbnail = (ViewHolderWithoutThumbnail)viewHolder;

            TextView tvTitle = withoutThumbnail.tvTitle;
            tvTitle.setText(article.getHeadline());

            TextView tvSnippet = withoutThumbnail.tvSnippet;;
            tvSnippet.setText(article.getSnippet());
        }

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ArticleActivity.class);
                intent.putExtra("article_url", article.getWebURL());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    @Override
    public int getItemViewType(int position) {
        Article article = articles.get(position);
        if (!article.getThumbnail().isEmpty()) {
            return WITH_THUMBNAIL;
        } else {
            return WITHOUT_THUMBNAIL;
        }
    }

    public class ViewHolderWithThumbnail extends RecyclerView.ViewHolder {
        private ImageView ivArticle;

        private TextView tvTitle;

        private TextView tvSnippet;

        public ViewHolderWithThumbnail(View itemView) {
            super(itemView);
            ivArticle = (ImageView)itemView.findViewById(R.id.ivImage);
            tvTitle = (TextView)itemView.findViewById(R.id.tvTitle);
            tvSnippet = (TextView)itemView.findViewById(R.id.tvSnippet);

        }
    }

    public class ViewHolderWithoutThumbnail extends RecyclerView.ViewHolder {
        private TextView tvTitle;

        private TextView tvSnippet;

        public ViewHolderWithoutThumbnail(View itemView) {
            super(itemView);
            tvTitle = (TextView)itemView.findViewById(R.id.tvTitle);
            tvSnippet = (TextView)itemView.findViewById(R.id.tvSnippet);

        }
    }
}
