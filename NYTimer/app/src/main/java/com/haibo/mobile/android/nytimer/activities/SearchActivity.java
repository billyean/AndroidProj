package com.haibo.mobile.android.nytimer.activities;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.haibo.mobile.android.nytimer.R;
import com.haibo.mobile.android.nytimer.fragments.SettingFragment;
import com.haibo.mobile.android.nytimer.adapters.ArticleAdapter;
import com.haibo.mobile.android.nytimer.listeners.EndlessRecyclerViewScrollListener;
import com.haibo.mobile.android.nytimer.listeners.SearchUpdateListener;
import com.haibo.mobile.android.nytimer.models.Article;
import com.haibo.mobile.android.nytimer.networking.ArticleHTTPClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class SearchActivity extends AppCompatActivity implements SearchUpdateListener {
    // Instance of network client
    private ArticleHTTPClient client;

    // Instance of the progress action-view
    MenuItem miActionProgressItem;

    // Instance of GridView
//    private GridView gvArticles;
    // Instance of RecyclerView
    private RecyclerView rvArticles;

    // Instance of ArticleAdapter
//    private ArticleArrayAdapter articlesAdapter;
    private ArticleAdapter articlesAdapter;

    // Instance of setting
    MenuItem settingItem;

    // Search result;
    List<Article> articles;

    // Query string
    private String query;

    // Store a member variable for the listener
//    private EndlessScrollListener scrollListener;
    private EndlessRecyclerViewScrollListener scrollListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        // Set view for all content
//        gvArticles = (GridView) findViewById(R.id.gvArticles);
        rvArticles = (RecyclerView) findViewById(R.id.rvArticles);

        articles = new ArrayList<>();

//        // initialize the adapter
//        articlesAdapter = new ArticleArrayAdapter(this, articles);
//
//        // attach the adapter to the GridView
//        gvArticles.setAdapter(articlesAdapter);
//        gvArticles.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Intent intent = new Intent(SearchActivity.this, ArticleActivity.class);
//                Article article = articles.get(i);
//                intent.putExtra("article_url", article.getWebURL());
//                startActivity(intent);
//            }
//        });
//
//        scrollListener = new EndlessScrollListener() {
//            @Override
//            public boolean onLoadMore(int page, int totalItemsCount) {
//                loadNextDataFromApi(page);
//                return true;
//            }
//        };
//        gvArticles.setOnScrollListener(scrollListener);
        articlesAdapter = new ArticleAdapter(this, articles);
        rvArticles.setAdapter(articlesAdapter);

        StaggeredGridLayoutManager gridLayoutManager =
                new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        rvArticles.setLayoutManager(gridLayoutManager);

        scrollListener = new EndlessRecyclerViewScrollListener(gridLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to the bottom of the list
                loadNextDataFromApi(page);
            }
        };
        rvArticles.addOnScrollListener(scrollListener);
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
                SearchActivity.this.query = query;

                // Need clear the result when do query again.
                reset();

                // perform query here
                fetchArticles(query, 0);

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
        ProgressBar v = (ProgressBar) MenuItemCompat.getActionView(miActionProgressItem);

        settingItem = menu.findItem(R.id.action_settings);

        return super.onCreateOptionsMenu(menu);
    }

    private void fetchArticles(String query, int page) {
        client = new ArticleHTTPClient(this);
        showProgressBar();

        client.getArticles(query, page, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    JSONArray result = response.getJSONObject("response").getJSONArray("docs");
                    int curSize = articles.size();
                    List<Article> newItems = Article.fromJsonArray(result);
                    articles.addAll(newItems);
                    hideProgressBar();
                    articlesAdapter.notifyItemRangeInserted(curSize, newItems.size());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                hideProgressBar();
                Toast.makeText(getBaseContext(), errorResponse.toString(), Toast.LENGTH_LONG).show();
//                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });
    }

    private void fetchArticles() {
        fetchArticles("", 0);
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
                SettingFragment fragment = (SettingFragment) manager.findFragmentByTag("fragment_setting");
                if (null == fragment) {
                    fragment = SettingFragment.newInstance();
                }
                fragment.show(manager, "fragment_setting");
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

    @Override
    public void updateSearchResult() {
        // perform query here
        reset();
        if (query != null && !query.isEmpty()) {
            fetchArticles(query, 0);
        }
    }

    private void reset() {
        // 1. First, clear the array of data
        articles.clear();
        // 2. Notify the adapter of the update
        articlesAdapter.notifyDataSetChanged(); // or notifyItemRangeRemoved
        // 3. Reset endless scroll listener when performing a new search
        scrollListener.resetState();
    }

    public void loadNextDataFromApi(int offset) {
        fetchArticles(query, offset);
    }

    private Boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }

    public boolean isOnline() {
        Runtime runtime = Runtime.getRuntime();
        try {
            Process ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8");
            int exitValue = ipProcess.waitFor();
            return (exitValue == 0);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }
}
