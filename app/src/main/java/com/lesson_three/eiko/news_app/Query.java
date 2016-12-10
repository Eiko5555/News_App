package com.lesson_three.eiko.news_app;

import android.support.v4.app.LoaderManager;
import android.support.v4.graphics.drawable.TintAwareDrawable;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by eiko on 12/5/2016.
 */
public class Query {

    private Query() {
    }

    public static List<ListNews> fetchNewsData(String requestUrl) {

        String TAG = "Query";
        String jsonResponse = null;
        URL url = createUrl(requestUrl);

        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            Thread.sleep(7000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        List<ListNews> news = extractFeatureFromJson(jsonResponse);
        Log.v(TAG, "pass fetchNewsData");
        return news;
    }

    private static URL createUrl(String stringUrl) {
        URL urlquery = null;
        try {
            urlquery = new URL(stringUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return urlquery;
    }

    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponseQuery = "";
        if (url == null) {
            return jsonResponseQuery;
        }
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponseQuery = readFromStream(inputStream);
            } else {
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponseQuery;
    }

    private static String readFromStream(InputStream inputStream)
            throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(
                    inputStream, Charset.forName("UTF-8"));
            BufferedReader buffReader = new BufferedReader(inputStreamReader);
            String line = buffReader.readLine();
            while (line != null) {
                output.append(line);
                line = buffReader.readLine();
            }
        }
        return output.toString();
    }

    private static List<ListNews> extractFeatureFromJson(String newsJson) {
        if (TextUtils.isEmpty(newsJson)) {
            return null;
        }
        List<ListNews> news = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(newsJson);
            JSONObject response = jsonObject.getJSONObject("response");
            JSONArray jsonArray = response.getJSONArray("results");

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject currentItem = jsonArray.getJSONObject(i);

                String title = currentItem.getString("webTitle");
                String sectionName = currentItem.getString("sectionName");
                String url = currentItem.getString("webUrl");

                ListNews listItem = new ListNews(title, sectionName, url);
                news.add(listItem);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return news;
    }
}
