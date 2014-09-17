package com.collusion.serviceassistant.ReturnVisits;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.collusion.serviceassistant.FloatingActionButton;
import com.collusion.serviceassistant.R;
import com.collusion.serviceassistant.operations.FileOperations;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class ReturnVisits extends Fragment {

    FloatingActionButton mFab;
    ListView list;
    File root = android.os.Environment.getExternalStorageDirectory();
    String file2 = root.getAbsolutePath() + "/ServiceAssistantReturnVisits/";
    private ListView mainListView;
    private ArrayAdapter<String> listAdapter;
    Spinner filter;
    Spinner sorter;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_return_visits, container, false);
        return rootView;
    }

    public void onViewCreated(View view, Bundle savedInstanceState)
    {

        mFab = (FloatingActionButton)getView().findViewById(R.id.fabbutton);
        mFab.setOnClickListener(addFAB);

        try {
            setHistory("Any", "Name");
        } catch (IOException e) {
            e.printStackTrace();
        }


        filter = (Spinner) getView().findViewById(R.id.filter);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.daysofweekany, R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
        filter.setAdapter(adapter);

        sorter = (Spinner) getView().findViewById(R.id.sort);
        ArrayAdapter<CharSequence> adapters = ArrayAdapter.createFromResource(getActivity(),
                R.array.sort, R.layout.simple_spinner_item);
        adapters.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
        sorter.setAdapter(adapters);

        filter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String filterstr = filter.getSelectedItem().toString();
                String sort = sorter.getSelectedItem().toString();
                Log.i("Spinner", filterstr);
                Log.i("Spinner", sort);
                try {
                    setHistory(filterstr, sort);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        sorter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String filterstr = filter.getSelectedItem().toString();
                String sort = sorter.getSelectedItem().toString();
                Log.i("Spinner", filterstr);
                Log.i("Spinner", sort);
                try {
                    setHistory(filterstr, sort);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public String readFileLineNumber(File file, int number) throws IOException {
        FileInputStream fs= new FileInputStream(file);
        BufferedReader br = new BufferedReader(new InputStreamReader(fs));
        for(int i = 0; i < number; ++i)
            br.readLine();
        String lineIWant = br.readLine();
        return lineIWant;
    }

    View.OnClickListener addFAB = new View.OnClickListener() {
        public void onClick(View v) {
            Log.i("Button", "Clicked");
            Fragment fragment = new ReturnVisitAdd();
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.frame_container, fragment).commit();

        }
    };

    public void setHistory(String filter, String sort) throws IOException {
        FileOperations FO = new FileOperations();
        ArrayList<String> Name = new ArrayList<String>();
        ArrayList<String> Address = new ArrayList<String>();
        ArrayList<String> filenameList = new ArrayList<String>();
        ArrayList<String> latitude = new ArrayList<String>();
        ArrayList<String> longitude = new ArrayList<String>();
        ArrayList<String> latlng = new ArrayList<String>();
        ArrayList<String> dayofWeek = new ArrayList<String>();
        ArrayList<String> distances = new ArrayList<String>();

        List<ReturnVisit> listRV = new ArrayList<ReturnVisit>();

        Double distance;

        List<String> files = FO.getFileList(file2);
        String[] Files = new String[files.size()];
        files.toArray(Files);
        Log.i("RV", Files.toString());

        Arrays.sort(Files);
        Log.i("RV", Files.toString());
        Integer lengthFiles = Array.getLength(Files);
        Integer i = 0;
        while (i < lengthFiles)
        {
            String filename = Files[i];
            java.io.File file1 = new java.io.File(root.getAbsolutePath() + "/ServiceAssistantReturnVisits/"+ filename);
            Log.i("INFO", file1.toString());
            String name = readFileLineNumber(file1, 0);
            String address = readFileLineNumber(file1, 1);
            String latitudeStr = readFileLineNumber(file1, 5);
            String longitudeStr = readFileLineNumber(file1, 4);
            String ltlng = readFileLineNumber(file1, 7);
            String dayofweekSTR = readFileLineNumber(file1, 6);

            if (latitudeStr != null)
            {
                double lat = Double.parseDouble(latitudeStr);
                double longi = Double.parseDouble(longitudeStr);
                distance = distance(lat,longi);
            }
            else
            {
                distance = 0.0;
            }

            String distanceStr = Double.toString(distance);

            if (filter.equals("Any"))
            {
                listRV.add(new ReturnVisit(name, address, filename, latitudeStr, longitudeStr, ltlng, dayofweekSTR, distanceStr));
            }
            else
            {
                if (dayofweekSTR.equals(filter))
                    {
                        listRV.add(new ReturnVisit(name, address, filename, latitudeStr, longitudeStr, ltlng, dayofweekSTR, distanceStr));
                    }
                else
                    {
                        Log.i("RV", "No match");
                    }
            }

            /*
            Name.add(name);
            Address.add(address);
            filenameList.add(filename);
            latitude.add(latitudeStr);
            longitude.add(longitudeStr);
            latlng.add(ltlng);
            dayofWeek.add(dayofweekSTR);
            distances.add(distanceStr);*/

            Log.i("RV", name);
            i++;
        }
        if (sort.equals("Name")) {
            Collections.sort(listRV, ReturnVisit.nameSort);

            for (ReturnVisit name : listRV) {
                Name.add(name.getName());
                Address.add(name.getAddress());
                filenameList.add(name.getFilename());
                latitude.add(name.getLatitude());
                longitude.add(name.getLongitude());
                latlng.add(name.getLatlong());
                dayofWeek.add(name.getDayofWeek());
                distances.add(name.getDistances());
            }
        }
        else if (sort.equals("Distance"))
        {
            Collections.sort(listRV);

            for (ReturnVisit name : listRV) {
                Name.add(name.getName());
                Address.add(name.getAddress());
                filenameList.add(name.getFilename());
                latitude.add(name.getLatitude());
                longitude.add(name.getLongitude());
                latlng.add(name.getLatlong());
                dayofWeek.add(name.getDayofWeek());
                distances.add(name.getDistances());
            }
        }

        String[] name = new String[Name.size()];
        Name.toArray(name);
        String[] address = new String[Address.size()];
        Address.toArray(address);
        String[] filesArray = new String[filenameList.size()];
        filenameList.toArray(filesArray);

        String[] lat = new String[latitude.size()];
        latitude.toArray(lat);

        String[] longi = new String[longitude.size()];
        longitude.toArray(longi);

        String[] latl0ng = new String[latlng.size()];
        latlng.toArray(latl0ng);

        String[] dayOfWeek = new String[dayofWeek.size()];
        dayofWeek.toArray(dayOfWeek);

        String[] distancesArray = new String[distances.size()];
        distances.toArray(distancesArray);

        Collections.sort(listRV);
        System.out.println(" ");
        for(ReturnVisit a: listRV)//printing the sorted list of ages
            System.out.print(a.getReturnVisitName() +"  : "+
                    a.getDistances() + "\n");

        ReturnVisitList adapter = new
                ReturnVisitList(getActivity(), name, address, filesArray, lat, longi, latl0ng, dayOfWeek, distancesArray, listRV);
        list = (ListView)getView().findViewById(R.id.rvListView);
        list.addHeaderView(new View(getActivity()));
        list.addFooterView(new View(getActivity()));
        list.setAdapter(adapter);
        list.setClickable(true);
        View view = getView();
    }


    public double distance(double lat2, double long2)
    {
        LocationManager lm = (LocationManager)getActivity().getSystemService(Context.LOCATION_SERVICE);
        Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        double longitude = location.getLongitude();
        double latitude = location.getLatitude();
        Log.i("Numbers", Double.toString(latitude));
        Log.i("Numbers", Double.toString(longitude));
        Log.i("Numbers", Double.toString(lat2));
        Log.i("Numbers", Double.toString(long2));

        double R = 6371; // km
        double er1 = Math.toRadians(latitude);
        double er2 = Math.toRadians(lat2);
        double er3 = Math.toRadians(lat2 - latitude);
        double er4 = Math.toRadians(long2 - longitude);

        double a = Math.sin(er3/2) * Math.sin(er3/2) +
                Math.cos(er1) * Math.cos(er2) *
                        Math.sin(er4/2) * Math.sin(er4/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));

        double d = R * c;
        d = d * 0.6214;
        d = (double)Math.round(d * 100) / 100;
        Log.i("Distance", Double.toString(d));
        return d;
    }
}
