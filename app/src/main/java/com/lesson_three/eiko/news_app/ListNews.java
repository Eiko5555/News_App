package com.lesson_three.eiko.news_app;

/**
 * Created by eiko on 12/5/2016.
 */
public class ListNews {

    private String mTitle;
    private String mSectionName;
    private String mUrl;

    public ListNews(String title, String sectionName, String url) {
        mTitle = title;
        mSectionName = sectionName;
        mUrl = url;
    }

    public String getmTitle() {
        return mTitle;
    }

    public String getmSectionName() {
        return mSectionName;
    }

    public String getmUrl() {
        return mUrl;
    }
}
