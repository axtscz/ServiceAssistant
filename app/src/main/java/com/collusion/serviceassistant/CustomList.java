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
    private final String[] filenames;
    public CustomList(Activity context,
                      String[] web, String[] data, String[] filenames) {
        super(context, R.layout.simplerow, web);
        this.context = context;
        this.web = web;
        this.data = data;
        this.filenames = filenames;

    }
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.simplerow, null, true);
        TextView txtTitle = (TextView) rowView.findViewById(R.id.month);
        TextView txtData = (TextView) rowView.findViewById(R.id.data);
        TextView txtFilename = (TextView) rowView.findViewById(R.id.filename);
        txtTitle.setText(web[position]);
        txtData.setText(data[position]);
        txtFilename.setText(filenames[position]);
        return rowView;
    }
}
