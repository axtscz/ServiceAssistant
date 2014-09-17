package com.collusion.serviceassistant;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.collusion.serviceassistant.operations.DateOperations;
import com.collusion.serviceassistant.operations.FileOperations;
import com.dropbox.sync.android.DbxAccountManager;

import java.io.File;

public class Details extends Fragment {

    Integer elementID1 = R.id.hourData;
    Integer elementID2 = R.id.magazineData;
    Integer elementID3 = R.id.RVData;
    Integer elementID4 = R.id.BookData;
    Activity a;

    DbxAccountManager mDbxAcctMgr;
    private static final String APP_SECRET = "29dhk340mmpecsj";
    private static final String APP_KEY = "wup7j5haihrzc11" ;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_details, container, false);
        setHasOptionsMenu(true);
        return rootView;
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.detailsmenu, menu);
        //fav.setIcon(R.drawable.btn_star_big_off);
        super.onCreateOptionsMenu(menu, inflater);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.share:
                share();
                return false;
            //case R.id.fragment_menu_item:
            // Do Fragment menu item stuff here
            //return true;
            default:
                break;
        }
        return false;
    }

        @Override
        public void onViewCreated(View view, Bundle savedInstanceState)
        {

            DateOperations DO = new DateOperations();
            String CurrentMonthFilePath = DO.getdateFile();
            SharedPreferences sharedPrefs = getActivity().getSharedPreferences("details", Context.MODE_PRIVATE);

            String month = sharedPrefs.getString("Title", "fail");
            String filename = sharedPrefs.getString("filename", "fail");
            String hours = sharedPrefs.getString("hours", "fail");

            Log.i("DETAILSMonth", month.toString());
            Log.i("DETAILSFilename", filename.toString());
            Log.i("DETAILSHours", hours.toString());


            Log.i("INFO", hours.toString());
            Log.i("DETAILS", filename);
            File root = android.os.Environment.getExternalStorageDirectory();
            java.io.File filemags = new java.io.File(root.getAbsolutePath() + "/ServiceAssistant/"+ filename+"/" , filename + "mags.txt");
            java.io.File filervs = new java.io.File(root.getAbsolutePath() + "/ServiceAssistant/"+ filename+"/", filename + "revs.txt");
            java.io.File filebook = new java.io.File(root.getAbsolutePath() + "/ServiceAssistant/"+ filename+"/", filename + "book.txt");
            java.io.File filehours = new java.io.File(root.getAbsolutePath() + "/ServiceAssistant/"+ filename+"/", filename + "hour.txt");

            month = month.toString();

            TextView tv = (TextView) getView().findViewById(R.id.Title);
            tv.setText(month);

            TextView tv2 = (TextView) getView().findViewById(R.id.hourData);
            tv2.setText(hours);

            updateUIElement(filemags,elementID2);
            updateUIElement(filervs,elementID3);
            updateUIElement(filebook,elementID4);
            updateUIElement(filehours,elementID1);


        }

        public void share(){
            Intent sendIntent = new Intent();
            TextView tv = (TextView) getView().findViewById(R.id.hourData);
            TextView tvmags = (TextView) getView().findViewById(R.id.magazineData);
            TextView tvrvs = (TextView) getView().findViewById(R.id.RVData);
            TextView tvbooks = (TextView) getView().findViewById(R.id.BookData);

            CharSequence hours = tv.getText();
            CharSequence mags = tvmags.getText();
            CharSequence rvs = tvrvs.getText();
            CharSequence books = tvbooks.getText();

            String hoursStr = hours.toString();
            String magsStr = mags.toString();
            String rvsStr = rvs.toString();
            String booksStr = books.toString();




            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, "My field service report: " + "\n" +
                    "Hours: " + hoursStr + "\n" +
                    "Magazines: " + magsStr + "\n" +
                    "Return Visits: " + rvsStr + "\n" +
                    "Books: " + booksStr);
            sendIntent.setType("text/plain");
            startActivity(sendIntent);
        }

        public void updateUIElement(File file1, Integer elementID)
        {
            FileOperations FO = new FileOperations();
            String data = FO.getOldData(file1, mDbxAcctMgr);
            TextView tv = (TextView) getView().findViewById(elementID);
            tv.setText(data);
        }
    }

