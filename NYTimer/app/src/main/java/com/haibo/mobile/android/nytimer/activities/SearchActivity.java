package com.haibo.mobile.android.nytimer.activities;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;

import com.haibo.mobile.android.nytimer.R;
import com.haibo.mobile.android.nytimer.SettingFragment;
import com.haibo.mobile.android.nytimer.adapters.ArticleArrayAdapter;
import com.haibo.mobile.android.nytimer.models.Article;
import com.haibo.mobile.android.nytimer.networking.ArticleHTTPClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class SearchActivity extends AppCompatActivity {
    // Instance of network client
    private ArticleHTTPClient client;

    // Instance of the progress action-view
    MenuItem miActionProgressItem;

    //
    private GridView gvArticles;

    // Instance of ArticleAdapter
    private ArticleArrayAdapter articlesAdapter;

    // Instance of setting
    MenuItem settingItem;

    List<Article> articles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        // Set view for all content
        gvArticles = (GridView) findViewById(R.id.gvArticles);

        articles = new ArrayList<>();

        // initialize the adapter
        articlesAdapter = new ArticleArrayAdapter(this, articles);

        // attach the adapter to the GridView
        gvArticles.setAdapter(articlesAdapter);

        gvArticles.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(SearchActivity.this, ArticleActivity.class);
                Article article = articles.get(i);
                intent.putExtra("article_url", article.getWebURL());
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_article_list, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // perform query here
                fetchArticles(query);

                // workaround to avoid issues with some emulators and keyboard devices firing twice if a keyboard enter is used
                // see https://code.google.com/p/android/issues/detail?id=24599
                searchView.clearFocus();

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        // Store instance of the menu item containing progress
        miActionProgressItem = menu.findItem(R.id.miActionProgress);
        // Extract the action-view from the menu item
        ProgressBar v =  (ProgressBar) MenuItemCompat.getActionView(miActionProgressItem);

        settingItem = menu.findItem(R.id.action_settings);

        return super.onCreateOptionsMenu(menu);
    }

    private void fetchArticles(String query) {
        client = new ArticleHTTPClient();
        showProgressBar();

        client.getArticles(query, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    JSONArray result = response.getJSONObject("response").getJSONArray("docs");
                    articles.addAll(Article.fromJsonArray(result));
                    hideProgressBar();
                    articlesAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });
    }

    private void fetchArticles() {
        fetchArticles("");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id) {
            case R.id.action_settings:
                FragmentManager manager = getSupportFragmentManager();
                SettingFragment fragment = (SettingFragment)manager.findFragmentByTag("fragment_setting");
                return true;
        }


        return super.onOptionsItemSelected(item);
    }

    public void showProgressBar() {
        // Show progress item
        miActionProgressItem.setVisible(true);
    }

    public void hideProgressBar() {
        // Hide progress item
        miActionProgressItem.setVisible(false);
    }
}
