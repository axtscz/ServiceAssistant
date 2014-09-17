package com.collusion.serviceassistant.ReturnVisits;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Paint;
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
import java.lang.reflect.Field;

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
        //Log.i("City", defaultcity);

        if (defaultcity != null) {
            EditText City = (EditText) getView().findViewById(R.id.city);
            City.setText(defaultcity);
        }
        if (defaultstate != null) {
            EditText State = (EditText) getView().findViewById(R.id.state);
            State.setText(defaultstate);
        }
        lm = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

        location = lm.getLastKnownLocation(GPS_PROVIDER);
        longitude = location.getLongitude();
        latitude = location.getLatitude();
        longstr = String.valueOf(longitude);
        latstr = String.valueOf(latitude);


        NumberPicker np = (NumberPicker) getView().findViewById(R.id.placement);
        np.setMaxValue(5);
        np.setMinValue(0);
        String[] array = getResources().getStringArray(R.array.publications);
        np.setDisplayedValues(array);
        changeNPColor(np, Color.rgb(224, 242, 241));

        Spinner spinner = (Spinner) getView().findViewById(R.id.filter);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.daysofweek, R.layout.simple_spinner_item);


// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        Button savebutton = (Button) getView().findViewById(R.id.saveButton1);
        savebutton.setOnClickListener(save);

        ImageButton location = (ImageButton) getView().findViewById(R.id.location);
        location.setOnClickListener(loc);

    }


    public void changeNPColor(NumberPicker numberPicker, int color)
    {
        final int count = numberPicker.getChildCount();
        for(int i = 0; i < count; i++){
            View child = numberPicker.getChildAt(i);
            if(child instanceof EditText){
                try{
                    Field selectorWheelPaintField = numberPicker.getClass()
                            .getDeclaredField("mSelectorWheelPaint");
                    selectorWheelPaintField.setAccessible(true);
                    ((Paint)selectorWheelPaintField.get(numberPicker)).setColor(color);
                    ((EditText)child).setTextColor(color);
                    numberPicker.invalidate();
                }
                catch(NoSuchFieldException e){
                    Log.w("setNumberPickerTextColor", e);
                }
                catch(IllegalAccessException e){
                    Log.w("setNumberPickerTextColor", e);
                }
                catch(IllegalArgumentException e){
                    Log.w("setNumberPickerTextColor", e);
                }
            }
        }
    }

    View.OnClickListener loc = new View.OnClickListener() {
        public void onClick(View v) {
            LocationManager lm = (LocationManager)getActivity().getSystemService(Context.LOCATION_SERVICE);
            Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            longitude = location.getLongitude();
            latitude = location.getLatitude();
        }
    };

        View.OnClickListener save = new View.OnClickListener() {
            public void onClick(View v) {
                String one;
                EditText name1 = (EditText) getView().findViewById(R.id.Name);
                EditText Address1 = (EditText) getView().findViewById(R.id.Address);
                EditText time1 = (EditText) getView().findViewById(R.id.time);
                EditText date1 = (EditText) getView().findViewById(R.id.date);
                EditText cityet = (EditText) getView().findViewById(R.id.city);
                EditText stateet = (EditText) getView().findViewById(R.id.state);
                NumberPicker np = (NumberPicker) getView().findViewById(R.id.placement);
                Spinner spinner = (Spinner) getView().findViewById(R.id.filter);
                if (Address1.getText().toString() == "") {
                    Fragment fragment = new ReturnVisits();
                    FragmentManager fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.frame_container, fragment).commit();
                } else {
                    city = cityet.getText().toString();
                    state = stateet.getText().toString();
                    FileOperations FO = new FileOperations();
                    String dayofWeek = spinner.getSelectedItem().toString();
                    Log.i("DAY", dayofWeek);
                    String name = name1.getText().toString();
                    String address = Address1.getText().toString() + " " + cityet.getText().toString() + ", " + stateet.getText().toString();
                    String time = time1.getText().toString();
                    String date = date1.getText().toString();
                    String[] array = getResources().getStringArray(R.array.publications);
                    int picked = np.getValue();
                    String placement = array[picked].toString();
                    longstr = Double.toString(longitude);
                    latstr = Double.toString(latitude);


                    File root = android.os.Environment.getExternalStorageDirectory();
                    java.io.File file1 = new java.io.File(root.getAbsolutePath() + "/ServiceAssistantReturnVisits/" + name + ".txt");
                    if (name != null) {
                        if (address != null) {
                            if (date != null) {
                                if (placement != null) {

                                    FO.writeRV(file1, name, address, date, placement, longstr, latstr, dayofWeek);

                                    Fragment fragment = new ReturnVisits();
                                    FragmentManager fragmentManager = getFragmentManager();
                                    fragmentManager.beginTransaction()
                                            .replace(R.id.frame_container, fragment).commit();
                                }
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

}
