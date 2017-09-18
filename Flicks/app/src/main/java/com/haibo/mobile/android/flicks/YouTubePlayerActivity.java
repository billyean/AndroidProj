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

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.haibo.mobile.android.flicks.model.Movie;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class YouTubePlayerActivity extends YouTubeBaseActivity {
    private static final String VIDEO_API_URL
            = "https://api.themoviedb.org/3/movie/%s/videos?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";

    private static final String TRAILERS_API_URL
            = "https://api.themoviedb.org/3/movie/%s/trailers?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";

    @BindView(R.id.player)
    YouTubePlayerView playerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_you_tube_player);
        ButterKnife.bind(this);

        final String id = getIntent().getStringExtra("id");
        final boolean backdrop = getIntent().getBooleanExtra("backdrop", false);


        OkHttpClient client = new OkHttpClient.Builder().build();
        Request request = new Request.Builder().url(String.format(TRAILERS_API_URL, id)).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    try {
                        String bodyString = new BufferedReader(response.body().charStream()).readLine();
                        JSONObject reply = new JSONObject(bodyString);
                        JSONArray result = reply.getJSONArray("youtube");
                        if (result.length() > 0) {
                            final String cue = ((JSONObject) result.get(0)).getString("source");
                            YouTubePlayerActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    playerView.initialize("AIzaSyCoSNnhvPLo6wm8teIHqu7aqYtUJOTcXvY",
                                            new YouTubePlayer.OnInitializedListener() {
                                                @Override
                                                public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                                                                    YouTubePlayer youTubePlayer, boolean b) {
                                                    // do any work here to cue video, play video, etc.
                                                    if (backdrop) {
                                                        youTubePlayer.loadVideo(cue);
                                                    } else {
                                                        youTubePlayer.cueVideo(cue);
                                                    }
                                                }

                                                @Override
                                                public void onInitializationFailure(YouTubePlayer.Provider provider,
                                                                                    YouTubeInitializationResult youTubeInitializationResult) {

                                                }
                                            });
                                }
                            });
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });

//        final AsyncHttpClient client = new AsyncHttpClient();
//        client.get(this, String.format(TRAILERS_API_URL, id), new JsonHttpResponseHandler(){
//            @Override
//            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
//                try {
//                    JSONArray result = response.getJSONArray("youtube");
//
//                    if (result.length() > 0) {
//                        final String cue = ((JSONObject)result.get(0)).getString("source");
//                        playerView.initialize("AIzaSyCoSNnhvPLo6wm8teIHqu7aqYtUJOTcXvY",
//                                new YouTubePlayer.OnInitializedListener() {
//                                    @Override
//                                    public void onInitializationSuccess(YouTubePlayer.Provider provider,
//                                                                        YouTubePlayer youTubePlayer, boolean b) {
//                                        // do any work here to cue video, play video, etc.
//                                        if (backdrop) {
//                                            youTubePlayer.loadVideo(cue);
//                                        } else {
//                                            youTubePlayer.cueVideo(cue);
//                                        }
//                                    }
//
//                                    @Override
//                                    public void onInitializationFailure(YouTubePlayer.Provider provider,
//                                                                        YouTubeInitializationResult youTubeInitializationResult) {
//
//                                    }
//                                });
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
//                super.onFailure(statusCode, headers, throwable, errorResponse);
//            }
//        });


    }
}
