package com.haibo.mobile.android.flicks.model;

/**
 * Created by hyan on 9/13/17.
 */

public class Movie {
    private final String posterPath;

    private final String title;

    private final String overview;


    public Movie(String posterPath, String title, String overview) {
        this.posterPath = posterPath;
        this.title = title;
        this.overview = overview;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getTitle() {
        return title;
    }

    public String getOverview() {
        return overview;
    }
}
