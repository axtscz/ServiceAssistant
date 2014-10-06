package com.collusion.serviceassistant.ReturnVisits;


import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.nfc.NfcAdapter;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.collusion.serviceassistant.R;
import com.collusion.serviceassistant.operations.DateOperations;
import com.collusion.serviceassistant.operations.FileOperations;

import java.io.File;
import java.io.IOException;
import java.util.Locale;


public class ReturnVisitDetails extends Fragment {

    String address;
    NfcAdapter mNfcAdapter;
    // Flag to indicate that Android Beam is available
    boolean mAndroidBeamAvailable  = false;

    static PackageManager packageManager;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_return_visit_details, container, false);

        SharedPreferences sharedPrefs = getActivity().getSharedPreferences("rvs", Context.MODE_PRIVATE);


        String filename = sharedPrefs.getString("filename", "fail");

        File file1 = new File(filename);
        FileOperations FO = new FileOperations();
        ReturnVisit rv = FO.retrieveRV(file1);
        String name = rv.getName();
        String address = rv.getAddress();

        DateOperations DO = new DateOperations();
        String time = DO.getTime();
        String currentDate = DO.getCurrentDate();

        TextView tvname = (TextView) rootView.findViewById(R.id.detailsName);
        TextView tvaddress = (TextView) rootView.findViewById(R.id.detailsAddress);

        tvname.setText(name);
        tvaddress.setText(address);
        // NFC isn't available on the device
        packageManager = getActivity().getPackageManager();
        if (packageManager.hasSystemFeature(PackageManager.FEATURE_NFC)) {
        } else if (Build.VERSION.SDK_INT <
                Build.VERSION_CODES.JELLY_BEAN_MR1) {
            // If Android Beam isn't available, don't continue.
            mAndroidBeamAvailable = false;
        } else {
            mNfcAdapter = NfcAdapter.getDefaultAdapter(getActivity());

        }
        return rootView;
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        Button openinMaps = (Button)getView().findViewById(R.id.mapsOpen);
        openinMaps.setOnClickListener(send);
    }

        View.OnClickListener send = new View.OnClickListener() {
        public void onClick(View v) {
            String my_new_str = address.replaceAll(" ", "+");
            String uri = String.format(Locale.ENGLISH, "geo:0,0?q=" + my_new_str);
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
            startActivity(intent);
        }
    };
}
