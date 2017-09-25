package com.haibo.mobile.android.nytimer.activities;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ShareActionProvider;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.haibo.mobile.android.nytimer.R;

public class ArticleActivity extends AppCompatActivity {
    // WebView instance
    private WebView articleWebView;

    // Share action
    private ShareActionProvider miShareAction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);

        String articleURL = getIntent().getStringExtra("article_url");
        articleWebView = (WebView) findViewById(R.id.wvArticle);
        // Configure related browser settings
        articleWebView.getSettings().setLoadsImagesAutomatically(true);
        articleWebView.getSettings().setJavaScriptEnabled(true);
        articleWebView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        // Configure the client to use when opening URLs
        articleWebView.setWebViewClient(new ArticleBrowser());
        articleWebView.loadUrl(articleURL);
    }

    private class ArticleBrowser extends WebViewClient {
        @SuppressWarnings("deprecation")
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @TargetApi(Build.VERSION_CODES.N)
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            view.loadUrl(request.getUrl().toString());
            return true;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate menu resource file.
        getMenuInflater().inflate(R.menu.share_article, menu);
        // Locate MenuItem with ShareActionProvider
        MenuItem item = menu.findItem(R.id.menu_item_share);
        // Fetch reference to the share action provider
        miShareAction = (ShareActionProvider) MenuItemCompat.getActionProvider(item);

        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");

        // get reference to WebView
        WebView wvArticle = (WebView) findViewById(R.id.wvArticle);
        // pass in the URL currently being used by the WebView
        shareIntent.putExtra(Intent.EXTRA_TEXT, wvArticle.getUrl());

        miShareAction.setShareIntent(shareIntent);
        return super.onCreateOptionsMenu(menu);
    }
}
