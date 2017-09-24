package com.haibo.mobile.android.nytimer;

import java.util.List;

/**
 * Created by Haibo(Tristan) Yan on 9/23/17.
 * Setting for New York Times App, format of every seeting should be same as NY times's API spec
 * see http://developer.nytimes.com/article_search_v2.json#/Console/GET/articlesearch.json
 */
public class Setting {
    // Format YYYYYMMDD
    private String beginDate;

    // either oldest or newest
    private String sort;

    // Arts, Fashion & Style, Sports
    private List<String> newsDeskValues;

    public String getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public List<String> getNewsDeskValues() {
        return newsDeskValues;
    }

    public void setNewsDeskValues(List<String> newsDeskValues) {
        this.newsDeskValues = newsDeskValues;
    }
}
