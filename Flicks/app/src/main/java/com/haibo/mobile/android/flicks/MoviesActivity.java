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

package com.haibo.mobile.android.flicks;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.haibo.mobile.android.flicks.adapter.MovieListAdapter;
import com.haibo.mobile.android.flicks.model.Movie;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
//import com.loopj.android.http.AsyncHttpClient;
//import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

//import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.Header;
//import okhttp3.Call;
//import okhttp3.Callback;
//import okhttp3.HttpUrl;
//import okhttp3.OkHttpClient;
//import okhttp3.Request;
//import okhttp3.Response;

/**
 * Created by Haibo(Tristan) on 9/13/17.
 */
public class MoviesActivity extends AppCompatActivity {
    private static final String SAMPLE_API_URL
            = "https://api.themoviedb.org/3/movie/now_playing?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";

    private static final String VIDEO_API_URL
            = "https://api.themoviedb.org/3/movie/%d/videos?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";

    private static final String TRAILERS_API_URL
            = "https://api.themoviedb.org/3/movie/%d/trailers?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";

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

        lvView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Movie movie = movies.get(i);
                Intent intent = new Intent(MoviesActivity.this, DetailActivity.class);
                intent.putExtra("image_path", movie.getBackdropPath());
                intent.putExtra("backdrop", movie.showBackdrop());
                intent.putExtra("title", movie.getTitle());
                intent.putExtra("release_date", movie.getReleaseDate());
                intent.putExtra("rate", movie.getRate());
                intent.putExtra("overview", movie.getOverview());
                startActivity(intent);
            }
        });


//        OkHttpClient client = new OkHttpClient.Builder().build();
////        HttpUrl.Builder builder = HttpUrl.parse(SAMPLE_API_URL).newBuilder();
////        builder.addQueryParameter("api_key", "a07e22bc18f5cb106bfe4cc1f83ad8ed");
////        String url = builder.build().toString();
//        Request request = new Request.Builder().url(SAMPLE_API_URL).build();
//
//        client.newCall(request).enqueue(new Callback() {
//
//            @Override
//            public void onFailure(Call call, IOException e) {
//                e.printStackTrace();
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                if (response.isSuccessful()) {
//                    try {
//                        String bodyString = response.body().toString();
//                        final JSONObject reply = new JSONObject(bodyString);
////                        MoviesActivity.this.runOnUiThread(new Runnable() {
////                            @Override
////                            public void run() {
////                                JSONArray result = null;
////                                try {
////                                    result = reply.getJSONArray("results");
////                                    movies.addAll(Movie.paserJsonArrayToMovies(result));
////                                    adapter.notifyDataSetChanged();
////                                    Log.d("DEBUG", movies.toString());
////                                } catch (JSONException e) {
////                                    e.printStackTrace();
////                                }
////                            }
////                        });
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        });




        final AsyncHttpClient client = new AsyncHttpClient();

        client.get(this, SAMPLE_API_URL, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    JSONArray result = response.getJSONArray("results");
                    movies.addAll(Movie.paserJsonArrayToMovies(result));
                    adapter.notifyDataSetChanged();
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
