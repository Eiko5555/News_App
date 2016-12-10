package com.lesson_three.eiko.news_app;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.app.LoaderManager;
import android.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<List<ListNews>> {

    private String TAG = "MAinActivity";
    private static final String URL_BASE =
            "http://content.guardianapis.com/search";
    //?q=debates&api-key=test
    private ListAdapter mAdapter;
    private TextView mEmptyText;
    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list);

        mAdapter = new ListAdapter(this, new ArrayList<ListNews>());
        mListView = (ListView) findViewById(R.id.listView);
        mEmptyText = (TextView) findViewById(R.id.emptyMessage);
        View progressbar = findViewById(R.id.progressBar);

        mListView.setEmptyView(mEmptyText);
        mListView.setAdapter(mAdapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView,
                                    View view, int i, long l) {
                ListNews currentItem = mAdapter.getItem(i);
                Uri newsUri = Uri.parse(currentItem.getmUrl());
                Intent openPage = new Intent(Intent.ACTION_VIEW, newsUri);
                startActivity(openPage);
            }
        });
        ConnectivityManager connectManager = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectManager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            Log.v(TAG, "initLoader passing");
            LoaderManager loaderManager = getLoaderManager();
            loaderManager.initLoader(0, null, this);
            progressbar.setVisibility(View.GONE);
        } else {
            mEmptyText.setText("No netWork conected...");
            progressbar.setVisibility(View.GONE);
        }
    }

    @Override
    public Loader<List<ListNews>> onCreateLoader(int id, Bundle args) {
        Log.v(TAG, "onCreateLoader");
        SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(this);
//        String type = sharedPreferences.getString(
//                getString(R.string.setting_type_article_label),
//                getString(R.string.setting_type_liveblog_label));

        String orderby = sharedPreferences.getString(
                getString(R.string.setting_orderby_key),
                getString(R.string.setting_orderby_default));

        Uri baseUri = Uri.parse(URL_BASE);
        Uri.Builder uriBuilder = baseUri.buildUpon();

        uriBuilder.appendQueryParameter("format", "json");
        uriBuilder.appendQueryParameter("q", "debates");
        uriBuilder.appendQueryParameter("orderby", orderby);
        uriBuilder.appendQueryParameter("api-key", "test");
//        uriBuilder.appendQueryParameter("type","type");

        return new NewsLoader(this, uriBuilder.toString());
        //return new NewsLoader(this,URL_BASE);
    }

    @Override
    public void onLoadFinished(Loader<List<ListNews>>
                                       loader, List<ListNews> listNewses) {
       Log.v(TAG,"pass onLoadFinished");
        mEmptyText.setText("no news posted.");
        mAdapter.clear();
        if (listNewses != null && !listNewses.isEmpty()) {
            mAdapter.addAll(listNewses);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<ListNews>> loader) {
        mAdapter.clear();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_setting) {
            Intent settingIntent = new Intent(
                    this, SettingActivity.class);
            startActivity(settingIntent);
            return true;
        }
    return super.onOptionsItemSelected(item);
    }
}
