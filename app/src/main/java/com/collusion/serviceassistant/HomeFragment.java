package com.collusion.serviceassistant;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Dialog;
import android.app.Fragment;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.dropbox.sync.android.DbxAccountManager;
import com.dropbox.sync.android.DbxException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.List;

public class HomeFragment extends Fragment {
    private static final String APP_SECRET = "29dhk340mmpecsj";
    private static final String APP_KEY = "wup7j5haihrzc11" ;
    private View myFragmentView;
    Integer elementID1 = R.id.magazineCounter;
    Integer elementID2 = R.id.jwt2counter;
    Integer elementID3 = R.id.jwt3counter;
    Integer elementID4 = R.id.jwt4counter;
    Integer elementID5 = R.id.jwt5counter;
    Integer elementID6 = R.id.jwt6counter;
    DbxAccountManager mDbxAcctMgr;
    Context thiscontext;
    Activity a;
	public HomeFragment(){}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        setHasOptionsMenu(true);
        thiscontext = container.getContext();
        a = this.getActivity();
        return rootView;
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.home_menu, menu);
        //fav.setIcon(R.drawable.btn_star_big_off);
        super.onCreateOptionsMenu(menu, inflater);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.reset:
                newReset();
                return false;
            //case R.id.fragment_menu_item:
                // Do Fragment menu item stuff here
                //return true;
            default:
                break;
        }

        return false;
    }

    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        newFileMethods();
        View v = getView();
        SharedPreferences sharedPrefs = getActivity().getSharedPreferences("GoalSettings", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = getActivity().getSharedPreferences("GoalSettings", Context.MODE_PRIVATE).edit();

        boolean trueorfalse = PreferenceManager.getDefaultSharedPreferences(getActivity())
                .getBoolean("auto_sync", false);

        if (sharedPrefs.getInt("Goal", -1) == -1) {
            Log.i("INFO", "NO VALUE< WRITING NEW VALUE TO 0");
            editor.putInt("Goal", 0);
            editor.apply();
            getPrefs();
        } else {
            String savedGoal = Integer.toString(sharedPrefs.getInt("Goal", -1));
            Log.i("INFO", savedGoal);
            getPrefs();
        }

        newFileMethods();
        TextView tv = (TextView) getView().findViewById(R.id.HourCount);
        tv.setOnClickListener(tapAdd);
        Button button = (Button) getView().findViewById(R.id.goalButton);
        button.setOnClickListener(goalDialogLaunch);
        TextView mags = (TextView)getView().findViewById(R.id.magazineCounter);
        mags.setOnClickListener(tapAddMags);
        TextView revs = (TextView)getView().findViewById(R.id.rvcounter);
        revs.setOnClickListener(tapAddrevs);
        TextView books = (TextView)getView().findViewById(R.id.bookCounter);
        books.setOnClickListener(tapAddBooks);
        TextView bros = (TextView)getView().findViewById(R.id.brochureCounter);
        bros.setOnClickListener(tapAddBros);
        Button sync = (Button) getView().findViewById(R.id.button);
        sync.setOnClickListener(buttonListener);

        alarmDeterminate();

        newFileMethods();
        setHoursLabel();

        magUpdater();
        rvUpdater();
        bookUpdater();
        broUpdater();

        newFileMethods();
        getFolder();

        mDbxAcctMgr = DbxAccountManager.getInstance(getActivity().getApplicationContext(), APP_KEY, APP_SECRET);

        if (mDbxAcctMgr.hasLinkedAccount() == true)
        {
            Log.i("DBX", "Linked!");
        }
        else
        {
            mDbxAcctMgr.startLink(a, 0);
        }
        if (trueorfalse == true)
        {
            try {
                initialSync();
                Log.i("SYNC", "Calling auto-sync");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (DbxException e) {
                e.printStackTrace();
            }
        }
        else
        {
            Log.i("SYNC", "No sync");
        }

    }
    //TODO: updateUIElement can stay
    public void updateUIElement(File file1, Integer elementID)
    {
        FileOperations FO = new FileOperations();
        mDbxAcctMgr = DbxAccountManager.getInstance(getActivity().getApplicationContext(), APP_KEY, APP_SECRET);
        if (mDbxAcctMgr == null)
        {
            Log.i("DBXHome", "Null");
        }
        String data = FO.getOldData(file1, mDbxAcctMgr);
        TextView tv = (TextView) getView().findViewById(elementID);
        tv.setText(data);
    }


    public void alarmDeterminate()
    {
        Calendar cal = Calendar.getInstance();
        int currentDay = cal.get(Calendar.DAY_OF_MONTH);
        Log.i("INFO", String.valueOf(currentDay));
        int secondsUntilday = 31-currentDay;
        secondsUntilday = secondsUntilday*24;
        secondsUntilday = secondsUntilday*60;
        secondsUntilday = secondsUntilday*60;
        Log.i("NUMBERS", String.valueOf(secondsUntilday));
        setupAlarm(secondsUntilday);
    }

    View.OnClickListener tapAddrevs = new View.OnClickListener() {
        public void onClick(View v) {
            DateOperations DO = new DateOperations();
            String CurrentMonthFilePath = DO.getdateFile();
            File root = android.os.Environment.getExternalStorageDirectory();
            java.io.File file1 = new java.io.File(root.getAbsolutePath() + "/ServiceAssistant/"+ CurrentMonthFilePath+"/", CurrentMonthFilePath + "revs.txt");
            String dir = "/ServiceAssistant/"+ CurrentMonthFilePath+"/";
            int elementid = R.id.rvcounter;

            FileOperations FO = new FileOperations();
            FO.addOneToData(file1,dir, mDbxAcctMgr);
            updateUIElement(file1, elementid);
        }
    };

    View.OnClickListener tapAddMags = new View.OnClickListener() {
        public void onClick(View v) {
            DateOperations DO = new DateOperations();
            String CurrentMonthFilePath = DO.getdateFile();
            File root = android.os.Environment.getExternalStorageDirectory();
            java.io.File file1 = new java.io.File(root.getAbsolutePath() + "/ServiceAssistant/"+ CurrentMonthFilePath+"/", CurrentMonthFilePath + "mags.txt");
            String dir = "/ServiceAssistant/"+ CurrentMonthFilePath+"/";
            int elementid = R.id.magazineCounter;

            FileOperations FO = new FileOperations();
            FO.addOneToData(file1, dir, mDbxAcctMgr);
            updateUIElement(file1, elementid);
        }

    };
    View.OnClickListener tapAddBros = new View.OnClickListener() {
        public void onClick(View v) {
            DateOperations DO = new DateOperations();
            String CurrentMonthFilePath = DO.getdateFile();
            File root = android.os.Environment.getExternalStorageDirectory();
            java.io.File file1 = new java.io.File(root.getAbsolutePath() + "/ServiceAssistant/"+ CurrentMonthFilePath+"/", CurrentMonthFilePath + "bros.txt");
            String dir = "/ServiceAssistant/"+ CurrentMonthFilePath+"/";
            int elementid = R.id.brochureCounter;

            FileOperations FO = new FileOperations();
            FO.addOneToData(file1, dir, mDbxAcctMgr);
            updateUIElement(file1, elementid);
        }

    };
    View.OnClickListener tapAddBooks = new View.OnClickListener() {
        public void onClick(View v) {
            DateOperations DO = new DateOperations();
            String CurrentMonthFilePath = DO.getdateFile();
            File root = android.os.Environment.getExternalStorageDirectory();
            java.io.File file1 = new java.io.File(root.getAbsolutePath() + "/ServiceAssistant/"+ CurrentMonthFilePath+"/", CurrentMonthFilePath + "book.txt");
            String dir = "/ServiceAssistant/"+ CurrentMonthFilePath+"/";
            int elementid = R.id.bookCounter;

            FileOperations FO = new FileOperations();
            FO.addOneToData(file1, dir, mDbxAcctMgr);
            updateUIElement(file1, elementid);
        }

    };

    View.OnClickListener buttonListener = new View.OnClickListener() {
        public void onClick(View v) {
            Boolean hasLinked = mDbxAcctMgr.hasLinkedAccount();
            DropBoxMethods dbm = new DropBoxMethods();
            Context current = getActivity().getApplicationContext();
            if (hasLinked == true){
                Log.i("DB", "Already linked");
                try {
                    dbm.doDropboxTest(current);
                } catch (DbxException.Unauthorized unauthorized) {
                    unauthorized.printStackTrace();
                }
            }
            else
            {
                mDbxAcctMgr.startLink(getActivity(), 0);
            }
        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0) {
            if (resultCode == Activity.RESULT_OK) {
                Log.i("DB", "Success");
            } else {
                // ... Link failed or was cancelled by the user.
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    public void magUpdater()
    {
        DateOperations DO = new DateOperations();
        String CurrentMonthFilePath = DO.getdateFile();
        File root = android.os.Environment.getExternalStorageDirectory();
        java.io.File file1 = new java.io.File(root.getAbsolutePath() + "/ServiceAssistant/"+ CurrentMonthFilePath+"/", CurrentMonthFilePath + "mags.txt");
        String dir = "/ServiceAssistant/"+ CurrentMonthFilePath+"/";
        int elementid = R.id.magazineCounter;

        FileOperations FO = new FileOperations();
        updateUIElement(file1, elementid);
    }

    public void rvUpdater()
    {
        DateOperations DO = new DateOperations();
        String CurrentMonthFilePath = DO.getdateFile();
        File root = android.os.Environment.getExternalStorageDirectory();
        java.io.File file1 = new java.io.File(root.getAbsolutePath() + "/ServiceAssistant/"+ CurrentMonthFilePath+"/", CurrentMonthFilePath + "revs.txt");
        String dir = "/ServiceAssistant/"+ CurrentMonthFilePath+"/";
        int elementid = R.id.rvcounter;

        FileOperations FO = new FileOperations();
        updateUIElement(file1, elementid);
    }

    public void broUpdater()
    {
        DateOperations DO = new DateOperations();
        String CurrentMonthFilePath = DO.getdateFile();
        File root = android.os.Environment.getExternalStorageDirectory();
        java.io.File file1 = new java.io.File(root.getAbsolutePath() + "/ServiceAssistant/"+ CurrentMonthFilePath+"/", CurrentMonthFilePath + "bros.txt");
        int elementid = R.id.brochureCounter;

        updateUIElement(file1, elementid);
    }
    public void bookUpdater()
    {
        DateOperations DO = new DateOperations();
        String CurrentMonthFilePath = DO.getdateFile();
        File root = android.os.Environment.getExternalStorageDirectory();
        java.io.File file1 = new java.io.File(root.getAbsolutePath() + "/ServiceAssistant/"+ CurrentMonthFilePath+"/", CurrentMonthFilePath + "book.txt");
        int elementid = R.id.bookCounter;

        updateUIElement(file1, elementid);
    }



    View.OnClickListener tapAdd = new View.OnClickListener() {
        public void onClick(View v) {
            DateOperations DO = new DateOperations();
            String CurrentMonthFilePath = DO.getdateFile();
            File root = android.os.Environment.getExternalStorageDirectory();
            java.io.File file1 = new java.io.File(root.getAbsolutePath() + "/ServiceAssistant/"+ CurrentMonthFilePath+"/", CurrentMonthFilePath + "hour.txt");
            String dir = "/ServiceAssistant/" + CurrentMonthFilePath + "/";

            FileOperations FO = new FileOperations();
            FO.addOneToData(file1, dir, mDbxAcctMgr);
            FO.getOldData(file1, mDbxAcctMgr);
            setHoursLabel();
            getPrefs();
        }
    };

    private void setupAlarm(int seconds) {
        AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(getActivity().ALARM_SERVICE);
        Intent intent = new Intent(getActivity().getBaseContext(), onAlarmReceive.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                getActivity(), 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        Log.d("INFO", "Setup the alarm");

        // Getting current time and add the seconds in it
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.SECOND, seconds);

        alarmManager.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pendingIntent);

        // Finish the currently running activity
    }

    //No File Access
    View.OnClickListener goalDialogLaunch = new View.OnClickListener() {
        public void onClick(View view) {
            //onCreateDialog();
            final Dialog d = new Dialog(getActivity());

            d.setTitle("Goal");
            d.setContentView(R.layout.goalsdialog);
            Button b2 = (Button) d.findViewById(R.id.goalButton);
            Button b1 = (Button) d.findViewById(R.id.saveButton);
            //final NumberPicker np = (NumberPicker) d.getView().findViewById(R.id.goalPicker);
            final EditText et = (EditText) d.findViewById(R.id.editText);
            b1.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {
                    String etValue = et.getText().toString();
                    Log.i("INFO", etValue);
                    Integer goal = Integer.parseInt(etValue);
                    Log.i("INFO", goal.toString());
                    d.dismiss();
                    SharedPreferences.Editor editor = getActivity().getSharedPreferences("GoalSettings", Context.MODE_PRIVATE).edit();
                    editor.putInt("Goal", goal);

                    // Commit the edits!
                    editor.apply();
                    getPrefs();

                }

            });
            b2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    d.dismiss(); // dismiss the dialog
                }
            });
            d.show();
        }
    };
    //File Access via File Operations
    void setHoursLabel()
    {
        DateOperations DO = new DateOperations();
        String CurrentMonthFilePath = DO.getdateFile();
        File root = android.os.Environment.getExternalStorageDirectory();
        java.io.File file1 = new java.io.File(root.getAbsolutePath() + "/ServiceAssistant/" + CurrentMonthFilePath + "/", CurrentMonthFilePath + "hour.txt");
        String dir = "/ServiceAssistant/" + CurrentMonthFilePath + "/";

        FileOperations FO = new FileOperations();
        String NewString = FO.getOldData(file1, mDbxAcctMgr);
        TextView tv = (TextView)getView().findViewById(R.id.HourCount);
        tv.setText(NewString);
    }

    //TODO: Create methods for more flexible access methods
    void getPrefs(){
        SharedPreferences sharedPrefs = getActivity().getSharedPreferences("GoalSettings", Context.MODE_PRIVATE);

        StringBuilder builder1 = new StringBuilder();
        builder1.append("\n Goal: "
                + sharedPrefs.getInt("Goal", -1));
        String final1 = builder1.toString();
        TextView tv2 = (TextView) getView().findViewById(R.id.MainGoalLabel);
        tv2.setText(final1);
        Log.i("INFO","Goal is: " + final1);
        Integer goalNum = sharedPrefs.getInt("Goal", -1);
        newProgressBarUpdate(goalNum);

    }

    public void newFileMethods()
    {
        DateOperations DO = new DateOperations();
        String datefile = DO.getdateFile();
        File root = android.os.Environment.getExternalStorageDirectory();
        java.io.File file1 = new java.io.File(root.getAbsolutePath() + "/ServiceAssistant/" + datefile);
        File file2 = new File(root.getAbsolutePath()+ "/ServiceAssistant/" + datefile + "/" + datefile + "hour.txt");
        File file3 = new File(root.getAbsolutePath()+ "/ServiceAssistant/" + datefile + "/" + datefile + "mags.txt");
        File file4 = new File(root.getAbsolutePath()+ "/ServiceAssistant/" + datefile + "/" + datefile + "revs.txt");
        File file5 = new File(root.getAbsolutePath()+ "/ServiceAssistant/" + datefile + "/" + datefile + "book.txt");
        File file6 = new File(root.getAbsolutePath()+ "/ServiceAssistant/" + datefile + "/" + datefile + "bros.txt");
        String dirname = "/ServiceAssistant/" + datefile + "/";

        FileOperations2 FO2 = new FileOperations2();
        FO2.createFolder(file1, dirname);
        FO2.createfile(file2, dirname);
        FO2.createfile(file3, dirname);
        FO2.createfile(file4, dirname);
        FO2.createfile(file5, dirname);
        FO2.createfile(file6, dirname);

    }

    public void newProgressBarUpdate(Integer goalNum)
    {
        DateOperations DO = new DateOperations();
        String date = DO.getdateFile();

        Calendar cal = Calendar.getInstance();
        Integer currentDay = cal.get(Calendar.DAY_OF_MONTH);

        File root = android.os.Environment.getExternalStorageDirectory();
        java.io.File file1 = new java.io.File(root.getAbsolutePath() + "/ServiceAssistant/" + date + "/", date + "hour.txt");

        FileOperations FO = new FileOperations();


        Integer mId = 001;

        ProgressBar pb = (ProgressBar) getView().findViewById(R.id.GoalBar);
        String hoursToDate = FO.getOldData(file1, mDbxAcctMgr);
        Integer hourstoDate = Integer.parseInt(hoursToDate);
        pb.setMax(goalNum);
        pb.setProgress(hourstoDate);

        Integer hoursToGo = goalNum - hourstoDate;
        String hoursToGoStr = Integer.toString(hoursToGo);


        if (currentDay > 10) {
            NotificationCompat.Builder mBuilder =
                    new NotificationCompat.Builder(getActivity())
                            .setSmallIcon(R.drawable.ic_history64)
                            .setContentTitle("Goal update")
                            .setContentText("You only need " + hoursToGoStr + " hours to reach this months goal");
            Intent resultIntent = new Intent(String.valueOf(MainActivity.class));
            TaskStackBuilder stackBuilder = TaskStackBuilder.create(getActivity());
            stackBuilder.addParentStack(MainActivity.class);
            stackBuilder.addNextIntent(resultIntent);
            PendingIntent resultPendingIntent =
                    stackBuilder.getPendingIntent(
                            0,
                            PendingIntent.FLAG_UPDATE_CURRENT
                    );
            mBuilder.setContentIntent(resultPendingIntent);
            NotificationManager mNotificationManager =
                    (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
            mNotificationManager.notify(mId, mBuilder.build());
        }

        TextView left = (TextView) getView().findViewById(R.id.HoursToGo);
        if (hoursToGo <= 0) {
            left.setText("You are done!");
        } else {
            left.setText("Hours to go: " + Integer.toString(hoursToGo));
        }
    }

    public void newReset()
    {
        DateOperations DO = new DateOperations();
        FileOperations FO = new FileOperations();
        String date = DO.getdateFile();

        File root = android.os.Environment.getExternalStorageDirectory();
        java.io.File file1 = new java.io.File(root.getAbsolutePath() + "/ServiceAssistant/" + date + "/", date + "hour.txt");
        FO.reset(file1);
        setHoursLabel();
        getPrefs();
    }

    public void getFolder()
    {
        File root = android.os.Environment.getExternalStorageDirectory();
        String dir = root.getAbsolutePath() + "/ServiceAssistant/";
        FileOperations FO = new FileOperations();
        List<String> folders = FO.getFolderList(dir);

        String[] filesArray = new String[folders.size()];
        folders.toArray(filesArray);

        Log.i("FOLDERS", filesArray.toString());
    }

    public void initialSync() throws FileNotFoundException, DbxException {
        DateOperations DO =  new DateOperations();
        FileOperations FO = new FileOperations();
        DropBoxMethods DBX = new DropBoxMethods();
        String date = DO.getdateFile();

        mDbxAcctMgr = DbxAccountManager.getInstance(getActivity().getApplicationContext(), APP_KEY, APP_SECRET);

        File root = android.os.Environment.getExternalStorageDirectory();
        java.io.File file1 = new java.io.File(root.getAbsolutePath() + "/ServiceAssistant/" + date);
        File file2 = new File(root.getAbsolutePath()+ "/ServiceAssistant/" + date + "/" + date + "hour.txt");
        File file3 = new File(root.getAbsolutePath()+ "/ServiceAssistant/" + date + "/" + date + "mags.txt");
        File file4 = new File(root.getAbsolutePath()+ "/ServiceAssistant/" + date + "/" + date + "revs.txt");
        File file5 = new File(root.getAbsolutePath()+ "/ServiceAssistant/" + date + "/" + date + "book.txt");
        File file6 = new File(root.getAbsolutePath()+ "/ServiceAssistant/" + date + "/" + date + "bros.txt");

        String data1 = FO.getOldData(file2, mDbxAcctMgr);
        String data2 = FO.getOldData(file3, mDbxAcctMgr);
        String data3 = FO.getOldData(file4, mDbxAcctMgr);
        String data4 = FO.getOldData(file5, mDbxAcctMgr);
        String data5 = FO.getOldData(file6, mDbxAcctMgr);

        DBX.uploadNumberFile(date, data1 ,mDbxAcctMgr, file2.toString());
        DBX.uploadNumberFile(date, data2 ,mDbxAcctMgr, file3.toString());
        DBX.uploadNumberFile(date, data3 ,mDbxAcctMgr, file4.toString());
        DBX.uploadNumberFile(date, data4 ,mDbxAcctMgr, file5.toString());
        DBX.uploadNumberFile(date, data5 ,mDbxAcctMgr, file6.toString());


    }
}

