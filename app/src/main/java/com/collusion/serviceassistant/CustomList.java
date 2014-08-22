package com.collusion.serviceassistant;

import android.app.Activity;
import android.view.LayoutInflater;


import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class CustomList extends ArrayAdapter<String> {
    private final Activity context;
    private final String[] web;
    private final String[] data;
    public CustomList(Activity context,
                      String[] web, String[] data) {
        super(context, R.layout.simplerow, web);
        this.context = context;
        this.web = web;
        this.data = data;

    }
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.simplerow, null, true);
        TextView txtTitle = (TextView) rowView.findViewById(R.id.month);
        TextView txtData = (TextView) rowView.findViewById(R.id.data);
        txtTitle.setText(web[position]);
        txtData.setText(data[position]);
        return rowView;
    }
}
