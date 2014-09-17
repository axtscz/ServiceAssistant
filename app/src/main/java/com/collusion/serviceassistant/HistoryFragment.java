package com.collusion.serviceassistant;


import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.collusion.serviceassistant.operations.DateOperations;
import com.collusion.serviceassistant.operations.FileOperations;
import com.dropbox.sync.android.DbxAccountManager;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class HistoryFragment extends Fragment {

    DbxAccountManager mDbxAcctMgr;
    private static final String APP_SECRET = "29dhk340mmpecsj";
    private static final String APP_KEY = "wup7j5haihrzc11" ;
    
    ListView list;
    File root = android.os.Environment.getExternalStorageDirectory();
    String file2 = root.getAbsolutePath() + "/ServiceAssistant/";
    private ListView mainListView;
    private ArrayAdapter<String> listAdapter;
    Activity a;
    public HistoryFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        a = this.getActivity();
        View rootView = inflater.inflate(R.layout.fragment_history, container, false);

        return rootView;
    }

    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        newSetHistory();
        mDbxAcctMgr = DbxAccountManager.getInstance(getActivity().getApplicationContext(), APP_KEY, APP_SECRET);

    }

    public void setHistory()
    {
        String TitleMonth = "";
        String TitleYear = "";
        Integer i = 0;
        FileOperations FO = new FileOperations();
        ArrayList<String> TitlesList = new ArrayList<String>();
        ArrayList<String> dataList = new ArrayList<String>();
        ArrayList<String> filenameList = new ArrayList<String>();
        List<String> files = FO.getFileList(file2);
        Integer lengthFiles = files.size();
        Log.i("INFOHistory", String.valueOf(lengthFiles));
        while (i < lengthFiles)
        {
            String filename = files.get(i) + "hour.txt";
            Log.i("INFOFiles", filename);

            String month1 = filename.substring(0,2);
            TitleYear = filename.substring(2,6);
            java.io.File file1 = new java.io.File(root.getAbsolutePath() + "/ServiceAssistant/", filename);
            String datatxt = FO.getOldData(file1,mDbxAcctMgr);
            datatxt = datatxt + " hours";
            Log.i("INFOHistory", month1);
            Log.i("INFOHistory", TitleYear);
            TitleMonth = getMonth(month1);
            Log.i("INFOHistory", TitleMonth);
            String Title = TitleMonth + " " + TitleYear;
            filenameList.add(filename);
            TitlesList.add(Title);
            dataList.add(datatxt);
            i++;
        }
        String[] TitlesArray = new String[TitlesList.size()];
        TitlesList.toArray(TitlesArray);
        String[] dataArray = new String[dataList.size()];
        dataList.toArray(dataArray);
        String[] filesArray = new String[filenameList.size()];
        filenameList.toArray(filesArray);
        CustomList adapter = new
                CustomList(getActivity(), TitlesArray, dataArray, filesArray);
        list = (ListView)getView().findViewById(R.id.listView);
        list.addHeaderView(new View(getActivity()));
        list.addFooterView(new View(getActivity()));
        list.setAdapter(adapter);
        list.setClickable(true);
        View view = getView();
    }

    public void onListItemClick(View view) {
        TextView tv = (TextView)view.findViewById(R.id.month);
        CharSequence month = tv.getText();
        String monthStr = month.toString();
        Log.i("MONTH", monthStr);
    }

    View.OnClickListener listViewItemClick = new View.OnClickListener() {
        public void onClick(View view) {
            TextView tv = (TextView)view.findViewById(R.id.month);
            CharSequence month = tv.getText();
            String monthStr = month.toString();
            Log.i("MONTH", monthStr);
        }
    };

    public String getMonth(String month1)
    {
        String TitleMonth = "";
        if (month1.equals("01"))
        {
            TitleMonth = "January";
            Log.i("INFOMONTH", TitleMonth);
            return TitleMonth;
        }
        else if (month1.equals("02"))
        {
            TitleMonth = "February";
            Log.i("INFOMONTH", TitleMonth);
            return TitleMonth;
        }
        else if (month1.equals("03"))
        {
            TitleMonth = "March";
            Log.i("INFOMONTH", TitleMonth);
            return TitleMonth;
        }
        else if (month1.equals("04"))
        {
            TitleMonth = "April";
            Log.i("INFOMONTH", TitleMonth);
            return TitleMonth;
        }
        else if (month1.equals("05"))
        {
            TitleMonth = "May";
            Log.i("INFOMONTH", TitleMonth);
            return TitleMonth;
        }
        else if (month1.equals("06"))
        {
            TitleMonth = "June";
            Log.i("INFOMONTH", TitleMonth);
            return TitleMonth;
        }
        else if (month1.equals("07"))
        {
            TitleMonth = "July";
            Log.i("INFOMONTH", TitleMonth);
            return TitleMonth;
        }
        else if (month1.equals("08"))
        {
            TitleMonth = "August";
            Log.i("INFOMONTH", TitleMonth);
            return TitleMonth;
        }
        else if (month1.equals("09"))
        {
            TitleMonth = "September";
            Log.i("INFOMONTH", TitleMonth);
            return TitleMonth;
        }
        else if (month1.equals("10"))
        {
            TitleMonth = "October";
            Log.i("INFOMONTH", TitleMonth);
            return TitleMonth;
        }
        else if (month1.equals("11"))
        {
            TitleMonth = "November";
            Log.i("INFOMONTH", TitleMonth);
            return TitleMonth;
        }
        else if (month1.equals("12"))
        {
            TitleMonth = "December";
            Log.i("INFOMONTH", TitleMonth);
            return TitleMonth;
        }
        else {
            TitleMonth = "December";
            Log.i("INFOMONTH", TitleMonth);
            return TitleMonth;
        }
    }

    public void newSetHistory()
    {
        int i = 0;
        String TitleYear;
        String TitleMonth;
        String Title;
        FileOperations FO = new FileOperations();
        DateOperations DO = new DateOperations();
        ArrayList<String> TitlesList = new ArrayList<String>();
        ArrayList<String> dataList = new ArrayList<String>();
        ArrayList<String> filenameList = new ArrayList<String>();
        List<String> files = FO.getFolderList(file2);
        Integer lengthFiles = files.size();
        Log.i("INFOHistory", String.valueOf(lengthFiles));
        while (i < lengthFiles)
        {
            String dirname = files.get(i);
            String month1 = dirname.substring(0,2);
            TitleYear = dirname.substring(2,6);
            TitleMonth = DO.getMonth(month1);
            java.io.File file1 = new java.io.File(root.getAbsolutePath() + "/ServiceAssistant/" + dirname +"/", dirname + "hour.txt");
            Log.i("FILENAME", "Looking up" + file1);
            Title = TitleMonth + " " + TitleYear;
            String datatxt = FO.getOldData(file1, mDbxAcctMgr);
            datatxt = datatxt + " hours";
            filenameList.add(dirname);
            TitlesList.add(Title);
            dataList.add(datatxt);
            i++;
        }
        String[] TitlesArray = new String[TitlesList.size()];
        TitlesList.toArray(TitlesArray);
        String[] dataArray = new String[dataList.size()];
        dataList.toArray(dataArray);
        String[] filesArray = new String[filenameList.size()];
        filenameList.toArray(filesArray);
        CustomList adapter = new
                CustomList(getActivity(), TitlesArray, dataArray, filesArray);
        list = (ListView)getView().findViewById(R.id.listView);
        list.setAdapter(adapter);
        list.setClickable(true);
        View view = getView();
    }
}