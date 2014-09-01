package com.collusion.serviceassistant;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dropbox.sync.android.DbxAccountManager;

import java.io.File;

public class TractCounter extends Fragment {

    DbxAccountManager mDbxAcctMgr;
    private static final String APP_SECRET = "29dhk340mmpecsj";
    private static final String APP_KEY = "wup7j5haihrzc11" ;
    Activity a;
    File root = android.os.Environment.getExternalStorageDirectory();
    java.io.File file1;
    java.io.File file2;
    java.io.File file3;
    java.io.File file4;
    java.io.File file5;
    java.io.File file6;

    String directoryListing;
    Integer elementID1 = R.id.jwt1counter;
    Integer elementID2 = R.id.jwt2counter;
    Integer elementID3 = R.id.jwt3counter;
    Integer elementID4 = R.id.jwt4counter;
    Integer elementID5 = R.id.HourLabel1;
    Integer elementID6 = R.id.jwt6counter;
	
	public TractCounter(){}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        a = this.getActivity();
        View rootView = inflater.inflate(R.layout.fragment_photos, container, false);
         
        return rootView;
    }

    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        DateOperations DO = new DateOperations();
        String month = DO.getdateFile();

        file1 = new java.io.File(root.getAbsolutePath() + "/ServiceAssistant/" + month + "/" , month + "tract1.txt");
        file2 = new java.io.File(root.getAbsolutePath() + "/ServiceAssistant/" + month + "/" , month + "tract2.txt");
        file3 = new java.io.File(root.getAbsolutePath() + "/ServiceAssistant/" + month + "/" , month + "tract3.txt");
        file4 = new java.io.File(root.getAbsolutePath() + "/ServiceAssistant/" + month + "/" , month + "tract4.txt");
        file5 = new java.io.File(root.getAbsolutePath() + "/ServiceAssistant/" + month + "/" , month + "tract5.txt");
        file6 = new java.io.File(root.getAbsolutePath() + "/ServiceAssistant/" + month + "/" , month + "tract6.txt");

        directoryListing = "ServiceAssistant/" + month + "/";

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
        TextView tvfive = (TextView) getView().findViewById(R.id.HourLabel1);
        TextView tvsix = (TextView) getView().findViewById(R.id.jwt6counter);

        ImageView ivone = (ImageView) getView().findViewById(R.id.jwtpic1);
        ImageView ivtwo = (ImageView) getView().findViewById(R.id.jwt2pic);
        ImageView ivthree = (ImageView) getView().findViewById(R.id.jwt3pic);
        ImageView ivfour = (ImageView) getView().findViewById(R.id.jwt4pic);
        ImageView ivfive = (ImageView) getView().findViewById(R.id.jwt5pic);
        ImageView ivsix = (ImageView) getView().findViewById(R.id.jwt6pic);

        tvone.setOnClickListener(tapAdd1);
        tvtwo.setOnClickListener(tapAdd2);
        tvthree.setOnClickListener(tapAdd3);
        tvfour.setOnClickListener(tapAdd4);
        tvfive.setOnClickListener(tapAdd5);
        tvsix.setOnClickListener(tapAdd6);

        ivone.setOnClickListener(tapAdd1);
        ivtwo.setOnClickListener(tapAdd2);
        ivthree.setOnClickListener(tapAdd3);
        ivfour.setOnClickListener(tapAdd4);
        ivfive.setOnClickListener(tapAdd5);
        ivsix.setOnClickListener(tapAdd6);
        mDbxAcctMgr = DbxAccountManager.getInstance(getActivity().getApplicationContext(), APP_KEY, APP_SECRET);

    }

    View.OnClickListener tapAdd1 = new View.OnClickListener() {
        public void onClick(View v) {
            FileOperations FO = new FileOperations();
            DateOperations DO = new DateOperations();
            String month = DO.getdateFile();
            java.io.File file1 = new java.io.File(root.getAbsolutePath() + "/ServiceAssistant/" + month + "/" , month + "tract1.txt");
            FO.addOneToData(file1, directoryListing, mDbxAcctMgr);
            updateUIElement(file1, elementID1);
        }

    };
    View.OnClickListener tapAdd2 = new View.OnClickListener() {
        public void onClick(View v) {
            FileOperations FO = new FileOperations();
            DateOperations DO = new DateOperations();
            String month = DO.getdateFile();
            java.io.File file1 = new java.io.File(root.getAbsolutePath() + "/ServiceAssistant/" + month + "/" , month + "tract2.txt");
            FO.addOneToData(file2, directoryListing, mDbxAcctMgr);
            updateUIElement(file2, elementID2);
        }

    };
    View.OnClickListener tapAdd3 = new View.OnClickListener() {
        public void onClick(View v) {
            FileOperations FO = new FileOperations();
            DateOperations DO = new DateOperations();
            String month = DO.getdateFile();
            java.io.File file1 = new java.io.File(root.getAbsolutePath() + "/ServiceAssistant/" + month + "/" , month + "tract3.txt");
            FO.addOneToData(file3, directoryListing, mDbxAcctMgr);
            updateUIElement(file3, elementID3);
        }

    };
    View.OnClickListener tapAdd4 = new View.OnClickListener() {
        public void onClick(View v) {
            FileOperations FO = new FileOperations();
            DateOperations DO = new DateOperations();
            String month = DO.getdateFile();
            java.io.File file1 = new java.io.File(root.getAbsolutePath() + "/ServiceAssistant/" + month + "/" , month + "tract4.txt");
            FO.addOneToData(file4, directoryListing, mDbxAcctMgr);
            updateUIElement(file4, elementID4);
        }

    };
    View.OnClickListener tapAdd5 = new View.OnClickListener() {
        public void onClick(View v) {
            FileOperations FO = new FileOperations();
            DateOperations DO = new DateOperations();
            String month = DO.getdateFile();
            java.io.File file1 = new java.io.File(root.getAbsolutePath() + "/ServiceAssistant/" + month + "/" , month + "tract5.txt");
            FO.addOneToData(file5, directoryListing, mDbxAcctMgr);
            updateUIElement(file5, elementID5);
        }

    };
    View.OnClickListener tapAdd6 = new View.OnClickListener() {
        public void onClick(View v) {
            FileOperations FO = new FileOperations();
            DateOperations DO = new DateOperations();
            String month = DO.getdateFile();
            java.io.File file1 = new java.io.File(root.getAbsolutePath() + "/ServiceAssistant/" + month + "/" , month + "tract6.txt");
            FO.addOneToData(file6, directoryListing, mDbxAcctMgr);
            updateUIElement(file6, elementID6);
        }

    };

    public void updateUIElement(File file1, Integer elementID)
    {
        FileOperations FO = new FileOperations();
        String data = FO.getOldData(file1, mDbxAcctMgr);
        TextView tv = (TextView) getView().findViewById(elementID);
        tv.setText(data);
    }
}
