package com.haibo.mobile.android.flicks;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.haibo.mobile.android.flicks.adapter.MovieListAdapter;
import com.haibo.mobile.android.flicks.model.Movie;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * Created by Haibo(Tristan) on 9/13/17.
 */
public class MoviesActivity extends AppCompatActivity {
    private static String SAMPLE_API_URL
            = "https://api.themoviedb.org/3/movie/now_playing?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";

    List<Movie> movies;

    MovieListAdapter adapter;

    ListView lvView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);

        lvView = (ListView)findViewById(R.id.lvMovies);
        movies = new ArrayList<>();
        adapter = new MovieListAdapter(this, movies);
        lvView.setAdapter(adapter);

        AsyncHttpClient client = new AsyncHttpClient();

        client.get(this, SAMPLE_API_URL, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    JSONArray result = response.getJSONArray("results");
                    movies.addAll(Movie.paserJsonArrayToMovies(result));
                    adapter.notifyDataSetChanged();
                    Log.d("DEBUG", movies.toString());
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
}
