package com.collusion.serviceassistant.ReturnVisits;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Paint;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.NumberPicker;
import android.widget.Spinner;

import com.collusion.serviceassistant.R;
import com.collusion.serviceassistant.operations.FileOperations;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;

import static android.location.LocationManager.GPS_PROVIDER;

public class ReturnVisitAdd extends Fragment {

    LocationManager lm;
    Location location;
    double longitude;
    double latitude;
    String longstr;
    String latstr;
    String defaultcity;
    String defaultstate;
    String state;
    String city;
    SharedPreferences.Editor sp;
    String mainaddress;


    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_return_visit_add, container, false);
        return rootView;
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {

        defaultcity = PreferenceManager.getDefaultSharedPreferences(getActivity())
                .getString("default_city", null);
        defaultstate = PreferenceManager.getDefaultSharedPreferences(getActivity())
                .getString("default_state", null);

        lm = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

        location = lm.getLastKnownLocation(GPS_PROVIDER);
        longitude = location.getLongitude();
        latitude = location.getLatitude();
        longstr = String.valueOf(longitude);
        latstr = String.valueOf(latitude);



        Spinner spinner = (Spinner) getView().findViewById(R.id.filter);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.daysofweek, R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);


        Spinner placed = (Spinner) getView().findViewById(R.id.placementSpin);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(getActivity(),
                R.array.publications, R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
        placed.setAdapter(adapter1);

        Button savebutton = (Button) getView().findViewById(R.id.saveButton1);
        savebutton.setOnClickListener(save);

        ImageButton location = (ImageButton) getView().findViewById(R.id.location);
        location.setOnClickListener(loc);

    }

    View.OnClickListener loc = new View.OnClickListener() {
        public void onClick(View v) {
            LocationManager lm = (LocationManager)getActivity().getSystemService(Context.LOCATION_SERVICE);
            Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            longitude = location.getLongitude();
            latitude = location.getLatitude();
            try {
                String address = geocoder(latitude, longitude);
                mainaddress = address;
                EditText addressET = (EditText)getView().findViewById(R.id.Address);
                String getText = addressET.getText().toString();
                Log.i("INFO", getText);
                if (getText.equals(""))
                {
                    addressET.setText(mainaddress);
                }
                else {
                    Log.i("ELSE", "ELSE");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    };

        View.OnClickListener save = new View.OnClickListener() {
            public void onClick(View v) {
                String address;
                String one;
                EditText name1 = (EditText) getView().findViewById(R.id.Name);
                EditText Address1 = (EditText) getView().findViewById(R.id.Address);
                EditText time1 = (EditText) getView().findViewById(R.id.time);
                EditText date1 = (EditText) getView().findViewById(R.id.date);
                Spinner spinner = (Spinner) getView().findViewById(R.id.filter);
                if (Address1.getText().toString() == "") {
                    Fragment fragment = new ReturnVisits();
                    FragmentManager fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.frame_container, fragment).commit();
                } else {
                    FileOperations FO = new FileOperations();
                    String dayofWeek = spinner.getSelectedItem().toString();
                    Log.i("DAY", dayofWeek);
                    String name = name1.getText().toString();
                    if (mainaddress != null) {
                        address = mainaddress;
                    }
                    else {
                        address = Address1.getText().toString();
                    }
                    String time = time1.getText().toString();
                    String date = date1.getText().toString();
                    String[] array = getResources().getStringArray(R.array.publications);
                    Spinner placementspin = (Spinner)getView().findViewById(R.id.placementSpin);
                    //int picked = np.getValue();
                    String placement = placementspin.getSelectedItem().toString();
                    longstr = Double.toString(longitude);
                    latstr = Double.toString(latitude);
                    EditText notesET = (EditText)getView().findViewById(R.id.notes);
                    String notes = notesET.getText().toString();

                    File root = android.os.Environment.getExternalStorageDirectory();
                    java.io.File file1 = new java.io.File(root.getAbsolutePath() + "/ServiceAssistantReturnVisits/" + name + ".txt");
                    if (name != null) {
                        if (address != null) {
                            if (dayofWeek != null) {
                                    FO.writeRV(file1, name, address, date, placement, longstr, latstr, dayofWeek, notes);

                                    Fragment fragment = new ReturnVisits();
                                    FragmentManager fragmentManager = getFragmentManager();
                                    fragmentManager.beginTransaction()
                                            .replace(R.id.frame_container, fragment).commit();
                            }
                        }
                    } else {
                        Fragment fragment = new ReturnVisits();
                        FragmentManager fragmentManager = getFragmentManager();
                        fragmentManager.beginTransaction()
                                .replace(R.id.frame_container, fragment).commit();
                    }

                }
            }
        };



    public String geocoder(double lat, double longi) throws IOException {
        Geocoder geo = new Geocoder(getActivity().getApplicationContext());
        List<Address> addresses = geo.getFromLocation(lat, longi, 1);
        Address address = addresses.get(0);
        if (address != null) {
            String addressText = String.format(
                    "%s, %s, %s",
                    // If there's a street address, add it
                    address.getMaxAddressLineIndex() > 0 ?
                            address.getAddressLine(0) : "",
                    // Locality is usually a city
                    address.getLocality(),
                    // The country of the address
                    address.getAdminArea(),
                    // Get Country
                    address.getCountryCode()
            );
            Log.i("Address", addressText);
            return addressText;
        }
        return null;
    }
    public String getfromAddress(String address) throws IOException {
        Geocoder geo = new Geocoder(getActivity().getApplicationContext());
        List<Address> la = geo.getFromLocationName(address, 1);
        Address address1 = la.get(0);
        String latlong = String.format(
                "%s, %s, %s",
                address1.getLatitude() + "+",
                address1.getLongitude());
        return latlong;
    }
}
