package com.collusion.serviceassistant.ReturnVisits;


import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.collusion.serviceassistant.R;


public class ReturnVisitDetails extends Fragment {

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_return_visit_details, container, false);

        SharedPreferences sharedPrefs = getActivity().getSharedPreferences("rvs", Context.MODE_PRIVATE);

        String name = sharedPrefs.getString("name", "fail");
        String address = sharedPrefs.getString("address", "fail");


        TextView tvname = (TextView) rootView.findViewById(R.id.detailsName);
        TextView tvaddress = (TextView) rootView.findViewById(R.id.detailsAddress);

        tvname.setText(name);
        tvaddress.setText(address);

        return rootView;
    }
}
