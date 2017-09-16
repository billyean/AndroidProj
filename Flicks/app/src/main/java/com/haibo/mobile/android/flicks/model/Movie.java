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

    private final String posterPath;

    private final String title;

    private final String overview;


    public Movie(String posterPath, String title, String overview) {
        this.posterPath = posterPath;
        this.title = title;
        this.overview = overview;
    }

    public Movie(JSONObject object) throws JSONException {
        this(object.getString("poster_path"),
                object.getString("title"), object.getString("overview"));
    }

    public String getPosterPath() {
        return String.format(MOVIE_IMAGE_URL, posterPath);
    }

    public String getTitle() {
        return title;
    }

    public String getOverview() {
        return overview;
    }

    public static List<Movie> paserJsonArrayToMovies(JSONArray array) throws JSONException {
        List<Movie> movies = new ArrayList<>();

        for (int index = 0; index < array.length(); index++){
            movies.add(new Movie(array.getJSONObject(index)));
        }
        return movies;
    }
}
