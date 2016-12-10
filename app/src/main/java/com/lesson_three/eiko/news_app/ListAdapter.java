package com.lesson_three.eiko.news_app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by eiko on 12/5/2016.
 */
public class ListAdapter extends ArrayAdapter<ListNews> {

    public ListAdapter(Context context, ArrayList<ListNews> item) {
        super(context, 0, item);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.listitem, parent, false);
        }
        ListNews currentItem = getItem(position);

        TextView textTitle = (TextView) listItemView.findViewById(R.id.textTitle);
        TextView textSectionName = (TextView) listItemView.
                findViewById(R.id.textSectionName);

        String title = new String(currentItem.getmTitle());
        textTitle.setText(title);
        String sectionName = new String(currentItem.getmSectionName());
        textSectionName.setText(sectionName);

        return listItemView;
    }
}
