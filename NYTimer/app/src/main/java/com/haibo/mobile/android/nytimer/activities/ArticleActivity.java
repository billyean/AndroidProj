package com.haibo.mobile.android.nytimer.activities;

import android.annotation.TargetApi;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.haibo.mobile.android.nytimer.R;

public class ArticleActivity extends AppCompatActivity {
    // WebView instance
    private WebView articleWebView;

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
}
