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

package com.haibo.mobile.android.flicks.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Haibo(Tristan) on 9/13/17.
 */
public class Movie {
    private static final String MOVIE_IMAGE_URL = "https://image.tmdb.org/t/p/w342/%s";

    private static final String BACKDROP_IMAGE_URL = "https://image.tmdb.org/t/p/w1280/%s";

    private final String posterPath;

    private final String backdropPath;

    private final String title;

    private final String overview;

    private final String id;

    private final double rate;

    private final String releaseDate;

    public Movie(String id, String posterPath, String backdropPath, String title, String overview,
                 double rate, String releaseDate) {
        this.id = id;
        this.posterPath = posterPath;
        this.backdropPath = backdropPath;
        this.title = title;
        this.overview = overview;
        this.rate = rate;
        this.releaseDate = releaseDate;
    }

    public Movie(JSONObject object) throws JSONException {
        this(object.getString("id"), object.getString("poster_path"),
                object.getString("backdrop_path"), object.getString("title"),
                object.getString("overview"), object.getDouble("vote_average"),
                object.getString("release_date"));
    }

    public String getId() {
        return id;
    }

    public String getPosterPath() {
        return String.format(MOVIE_IMAGE_URL, posterPath);
    }

    public String getBackdropPath() {
        return String.format(BACKDROP_IMAGE_URL, backdropPath);
    }

    public String getTitle() {
        return title;
    }

    public String getOverview() {
        return overview;
    }

    public double getRate() {
        return rate;
    }

    public boolean showBackdrop() { return rate > 5.0; }

    public String getReleaseDate() {
        return releaseDate;
    }

    public static List<Movie> paserJsonArrayToMovies(JSONArray array) throws JSONException {
        List<Movie> movies = new ArrayList<>();

        for (int index = 0; index < array.length(); index++){
            movies.add(new Movie(array.getJSONObject(index)));
        }
        return movies;
    }
}
