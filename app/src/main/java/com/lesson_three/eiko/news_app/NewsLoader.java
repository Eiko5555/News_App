package com.lesson_three.eiko.news_app;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import java.util.List;

/**
 * Created by eiko on 12/5/2016.
 */
public class NewsLoader extends AsyncTaskLoader<List<ListNews>> {

    private String TAG = "newsloader";
    String urls;
    public NewsLoader(Context context, String url) {
        super(context);
        urls = url;
    }

    @Override
    protected void onStartLoading() {
        Log.v(TAG, "on start loading");
        forceLoad();
    }

    @Override
    public List<ListNews> loadInBackground() {
        Log.v(TAG,"load in background");
      List<ListNews> news = Query.fetchNewsData(urls);
        return news;
    }
}
