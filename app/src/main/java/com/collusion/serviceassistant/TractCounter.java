package com.collusion.serviceassistant;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.File;
import java.util.Calendar;

public class TractCounter extends Fragment {

    Calendar cal = Calendar.getInstance();
    int currentMonth = cal.get(Calendar.MONTH)+1;
    int currentYear = cal.get(Calendar.YEAR);
    String CurrentMonthFilePath1 = Integer.toString(currentMonth) + Integer.toString(currentYear)+ "tract1";
    String CurrentMonthFilePath2 = Integer.toString(currentMonth) + Integer.toString(currentYear)+"tract2";
    String CurrentMonthFilePath3 = Integer.toString(currentMonth) + Integer.toString(currentYear)+"tract3";
    String CurrentMonthFilePath4 = Integer.toString(currentMonth) + Integer.toString(currentYear)+"tract4";
    String CurrentMonthFilePath5 = Integer.toString(currentMonth) + Integer.toString(currentYear)+"tract5";
    String CurrentMonthFilePath6 = Integer.toString(currentMonth) + Integer.toString(currentYear)+"tract6";
    File root = android.os.Environment.getExternalStorageDirectory();
    java.io.File file1 = new java.io.File(root.getAbsolutePath() + "/ServiceAssistant/TractData/" , CurrentMonthFilePath1 + ".txt");
    java.io.File file2 = new java.io.File(root.getAbsolutePath() + "/ServiceAssistant/TractData/" , CurrentMonthFilePath2 + ".txt");
    java.io.File file3 = new java.io.File(root.getAbsolutePath() + "/ServiceAssistant/TractData/" , CurrentMonthFilePath3 + ".txt");
    java.io.File file4 = new java.io.File(root.getAbsolutePath() + "/ServiceAssistant/TractData/" , CurrentMonthFilePath4 + ".txt");
    java.io.File file5 = new java.io.File(root.getAbsolutePath() + "/ServiceAssistant/TractData/" , CurrentMonthFilePath5 + ".txt");
    java.io.File file6 = new java.io.File(root.getAbsolutePath() + "/ServiceAssistant/TractData/" , CurrentMonthFilePath6 + ".txt");

    String directoryListing = "/ServiceAssistant/TractData/";
    Integer elementID1 = R.id.jwt1counter;
    Integer elementID2 = R.id.jwt2counter;
    Integer elementID3 = R.id.jwt3counter;
    Integer elementID4 = R.id.jwt4counter;
    Integer elementID5 = R.id.jwt5counter;
    Integer elementID6 = R.id.jwt6counter;
	
	public TractCounter(){}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_photos, container, false);
         
        return rootView;
    }

    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        FileOperations FO = new FileOperations();
        FO.createfile(file1, directoryListing);
        FO.createfile(file2, directoryListing);
        FO.createfile(file3, directoryListing);
        FO.createfile(file4, directoryListing);
        FO.createfile(file5, directoryListing);
        FO.createfile(file6, directoryListing);

        updateUIElement(file1,elementID1);
        updateUIElement(file2,elementID2);
        updateUIElement(file3,elementID3);
        updateUIElement(file4,elementID4);
        updateUIElement(file5,elementID5);
        updateUIElement(file6,elementID6);

        TextView tvone = (TextView) getView().findViewById(R.id.jwt1counter);
        TextView tvtwo = (TextView) getView().findViewById(R.id.jwt2counter);
        TextView tvthree = (TextView) getView().findViewById(R.id.jwt3counter);
        TextView tvfour = (TextView) getView().findViewById(R.id.jwt4counter);
        TextView tvfive = (TextView) getView().findViewById(R.id.jwt5counter);
        TextView tvsix = (TextView) getView().findViewById(R.id.jwt6counter);

        tvone.setOnClickListener(tapAdd1);
        tvtwo.setOnClickListener(tapAdd2);
        tvthree.setOnClickListener(tapAdd3);
        tvfour.setOnClickListener(tapAdd4);
        tvfive.setOnClickListener(tapAdd5);
        tvsix.setOnClickListener(tapAdd6);
    }

    View.OnClickListener tapAdd1 = new View.OnClickListener() {
        public void onClick(View v) {
            FileOperations FO = new FileOperations();
            FO.addOneToData(file1, directoryListing);
            updateUIElement(file1, elementID1);
        }

    };
    View.OnClickListener tapAdd2 = new View.OnClickListener() {
        public void onClick(View v) {
            FileOperations FO = new FileOperations();
            FO.addOneToData(file2, directoryListing);
            updateUIElement(file2, elementID2);
        }

    };
    View.OnClickListener tapAdd3 = new View.OnClickListener() {
        public void onClick(View v) {
            FileOperations FO = new FileOperations();
            FO.addOneToData(file3, directoryListing);
            updateUIElement(file3, elementID3);
        }

    };
    View.OnClickListener tapAdd4 = new View.OnClickListener() {
        public void onClick(View v) {
            FileOperations FO = new FileOperations();
            FO.addOneToData(file4, directoryListing);
            updateUIElement(file4, elementID4);
        }

    };
    View.OnClickListener tapAdd5 = new View.OnClickListener() {
        public void onClick(View v) {
            FileOperations FO = new FileOperations();
            FO.addOneToData(file5, directoryListing);
            updateUIElement(file5, elementID5);
        }

    };
    View.OnClickListener tapAdd6 = new View.OnClickListener() {
        public void onClick(View v) {
            FileOperations FO = new FileOperations();
            FO.addOneToData(file6, directoryListing);
            updateUIElement(file6, elementID6);
        }

    };

    public void updateUIElement(File file1, Integer elementID)
    {
        FileOperations FO = new FileOperations();
        String data = FO.getOldData(file1);
        TextView tv = (TextView) getView().findViewById(elementID);
        tv.setText(data);
    }
}
