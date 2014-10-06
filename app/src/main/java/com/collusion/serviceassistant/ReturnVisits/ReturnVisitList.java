package com.collusion.serviceassistant.ReturnVisits;

import android.app.Activity;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.collusion.serviceassistant.R;

import java.util.List;

public class ReturnVisitList extends ArrayAdapter<String> {

    private final Activity context;
    private final String[] name;
    private final String[] address;
    private final String[] filename;
    private final String[] longitude;
    private final String[] latitude;
    private final String[] latlong;
    private final String[] dayofWeek;
    private final String[] distances;
    List<ReturnVisit> listRV;
    public ReturnVisitList(Activity context,
                      String[] name, String[] address, String[] filename, String[] latitude, String[] longitude, String[] latlong, String[] dayofWeek, String[] distances, List<ReturnVisit> listRV) {
        super(context, R.layout.simplerow, name);
        this.context = context;
        this.name = name;
        this.address = address;
        this.filename = filename;
        this.longitude = longitude;
        this.latitude = latitude;
        this.latlong = latlong;
        this.dayofWeek = dayofWeek;
        this.distances = distances;
        this.listRV = listRV;

    }
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.rv_list_item, null, true);
        TextView txtTitle = (TextView) rowView.findViewById(R.id.nameView);
        TextView txtData = (TextView) rowView.findViewById(R.id.addressView);
        TextView txtFilename = (TextView) rowView.findViewById(R.id.rvFileName);
        TextView longi = (TextView) rowView.findViewById(R.id.longitude);
        TextView lat = (TextView) rowView.findViewById(R.id.latitude);
        TextView latlongtv = (TextView) rowView.findViewById(R.id.latlong);


        txtTitle.setText(name[position]);
        String addressstr = address[position];
        if (addressstr.length() == 0)
        {
            txtData.setText("Missing Information");
        }
        else
        {
            txtData.setText(address[position]);
        }
        String day = dayofWeek[position];
        day.trim();
        Log.i("DAYS", day);

        if (day.equals("Monday"))
        {

            TextView daytv = (TextView) rowView.findViewById(R.id.monday);
            daytv.setTextColor(Color.rgb(0,0,0));
        }
        else if (day.equals("Tuesday"))
        {
            TextView daytv = (TextView) rowView.findViewById(R.id.Tuesday);
            daytv.setTextColor(Color.rgb(0,0,0));
        }
        else if (day.equals("Wednesday"))
        {
            TextView daytv = (TextView) rowView.findViewById(R.id.wednesday);
            daytv.setTextColor(Color.rgb(0,0,0));
        }
        else if (day.equals("Thursday"))
        {
            TextView daytv = (TextView) rowView.findViewById(R.id.thursday);
            daytv.setTextColor(Color.rgb(0,0,0));
        }
        else if (day.equals("Friday"))
        {
            TextView daytv = (TextView) rowView.findViewById(R.id.friday);
            daytv.setTextColor(Color.rgb(0,0,0));
        }
        else if (day.equals("Saturday"))
        {
            TextView daytv = (TextView) rowView.findViewById(R.id.saturday);
            daytv.setTextColor(Color.rgb(0,0,0));
        }
        else if (day.equals("Sunday"))
        {
            TextView daytv = (TextView) rowView.findViewById(R.id.sunday);
            daytv.setTextColor(Color.rgb(0,0,0));
        }
        else
        {
            Log.i("INFO", "Went to else, but why?");
        }

        txtFilename.setText(filename[position]);
        longi.setText(longitude[position]);
        lat.setText(latitude[position]);
        latlongtv.setText(latlong[position]);
        latlongtv.setText(latlong[position]);

        TextView distance = (TextView) rowView.findViewById(R.id.distance);
        distance.setText(distances[position] + " mile(s)");
        return rowView;
    }
}
