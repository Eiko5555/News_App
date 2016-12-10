package com.lesson_three.eiko.news_app;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

/**
 * Created by eiko on 12/6/2016.
 */
public class WebIntent  extends AsyncTaskLoader<List<ListNews>> {

    private static final String LOG = WebIntent.class.getName();
    private String mUrl;

    public WebIntent(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<ListNews> loadInBackground() {
        if (mUrl == null) {
            return null;
        }
        List<ListNews> news = Query.fetchNewsData(mUrl);
        return news;
    }
}

