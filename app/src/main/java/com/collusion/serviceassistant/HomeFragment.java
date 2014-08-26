package com.collusion.serviceassistant;

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
import android.widget.NumberPicker;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;

public class HomeFragment extends Fragment {
    private View myFragmentView;
    Integer elementID1 = R.id.magazineCounter;
    Integer elementID2 = R.id.jwt2counter;
    Integer elementID3 = R.id.jwt3counter;
    Integer elementID4 = R.id.jwt4counter;
    Integer elementID5 = R.id.jwt5counter;
    Integer elementID6 = R.id.jwt6counter;


	public HomeFragment(){}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        setHasOptionsMenu(true);
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
                reset();
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
        View v = this.getView();
        SharedPreferences sharedPrefs = this.getActivity().getSharedPreferences("GoalSettings", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = this.getActivity().getSharedPreferences("GoalSettings", Context.MODE_PRIVATE).edit();



        if (sharedPrefs.getInt("Goal", -1) == -1) {
            Log.i("INFO", "NO VALUE< WRITING NEW VALUE TO 0");
            editor.putInt("Goal", 0);
            editor.apply();
        } else {
            String savedGoal = Integer.toString(sharedPrefs.getInt("Goal", -1));
            Log.i("INFO", savedGoal);
            getPrefs();
        }
        dbCheck();
        TextView tv = (TextView) getView().findViewById(R.id.HourCount);
        tv.setOnClickListener(tapAdd);
        Button button = (Button) getView().findViewById(R.id.goalButton);
        button.setOnClickListener(goalDialogLaunch);
        TextView mags = (TextView)getView().findViewById(R.id.magazineCounter);
        mags.setOnClickListener(tapAddMags);
        TextView rvs = (TextView)getView().findViewById(R.id.rvcounter);
        rvs.setOnClickListener(tapAddrvs);
        TextView books = (TextView)getView().findViewById(R.id.bookCounter);
        books.setOnClickListener(tapAddBooks);
        TextView bros = (TextView)getView().findViewById(R.id.brochureCounter);
        bros.setOnClickListener(tapAddBros);

        alarmDeterminate();

        createFiles();

        magUpdater();
        rvUpdater();
        bookUpdater();
        broUpdater();


    }

    public void updateUIElement(File file1, Integer elementID)
    {
        FileOperations FO = new FileOperations();
        String data = FO.getOldData(file1);
        TextView tv = (TextView) getView().findViewById(elementID);
        tv.setText(data);
    }
//TODO: Write this method to the DateOperations and FileOperations class
    private void createFiles() {
        Calendar cal = Calendar.getInstance();
        int currentMonth = cal.get(Calendar.MONTH) + 1;
        int currentYear = cal.get(Calendar.YEAR);
        int currentDay = cal.get(Calendar.DAY_OF_MONTH);
        Log.i("INFO", Integer.toString(currentYear));
        if (currentMonth > 9) {
            String CurrentMonthFilePath = Integer.toString(currentMonth) + Integer.toString(currentYear);
            Log.i("INFO", "adding!");
            File root = android.os.Environment.getExternalStorageDirectory();
            java.io.File fileMags = new java.io.File(root.getAbsolutePath() + "/ServiceAssistant/OtherStats/", CurrentMonthFilePath + "mags" +".txt");
            java.io.File fileRVS = new java.io.File(root.getAbsolutePath() + "/ServiceAssistant/OtherStats/", CurrentMonthFilePath + "rvs" + ".txt");
            java.io.File fileBooks = new java.io.File(root.getAbsolutePath() + "/ServiceAssistant/OtherStats/", CurrentMonthFilePath + "book"+".txt");
            java.io.File fileBrochures = new java.io.File(root.getAbsolutePath() + "/ServiceAssistant/OtherStats/", CurrentMonthFilePath + "bro" + ".txt");
            String dir = "/ServiceAssistant/OtherStats/";

            FileOperations FO = new FileOperations();
            FO.createfile(fileMags,dir);
            FO.createfile(fileRVS,dir);
            FO.createfile(fileBooks,dir);
            FO.createfile(fileBrochures,dir);

        }
        else
        {
            String CurrentMonthFilePath = "0"+ Integer.toString(currentMonth) + Integer.toString(currentYear);
            File root = android.os.Environment.getExternalStorageDirectory();
            java.io.File fileMags = new java.io.File(root.getAbsolutePath() + "/ServiceAssistant/OtherStats/", CurrentMonthFilePath + "mags" +".txt");
            java.io.File fileRVS = new java.io.File(root.getAbsolutePath() + "/ServiceAssistant/OtherStats/", CurrentMonthFilePath + "rvs" + ".txt");
            java.io.File fileBooks = new java.io.File(root.getAbsolutePath() + "/ServiceAssistant/OtherStats/", CurrentMonthFilePath + "book"+".txt");
            java.io.File fileBrochures = new java.io.File(root.getAbsolutePath() + "/ServiceAssistant/OtherStats/", CurrentMonthFilePath + "bro" + ".txt");
            String dir = "/ServiceAssistant/OtherStats/";

            FileOperations FO = new FileOperations();
            FO.createfile(fileMags,dir);
            FO.createfile(fileRVS,dir);
            FO.createfile(fileBooks,dir);
            FO.createfile(fileBrochures,dir);
        }

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

    View.OnClickListener tapAddrvs = new View.OnClickListener() {
        public void onClick(View v) {
            DateOperations DO = new DateOperations();
            String CurrentMonthFilePath = DO.getdateFile();
            File root = android.os.Environment.getExternalStorageDirectory();
            java.io.File file1 = new java.io.File(root.getAbsolutePath() + "/ServiceAssistant/OtherStats/", CurrentMonthFilePath + "rvs.txt");
            String dir = "/ServiceAssistant/OtherStats/";
            int elementid = R.id.rvcounter;

            FileOperations FO = new FileOperations();
            FO.addOneToData(file1,dir);
            updateUIElement(file1, elementid);
        }
    };

    View.OnClickListener tapAddMags = new View.OnClickListener() {
        public void onClick(View v) {
            DateOperations DO = new DateOperations();
            String CurrentMonthFilePath = DO.getdateFile();
            File root = android.os.Environment.getExternalStorageDirectory();
            java.io.File file1 = new java.io.File(root.getAbsolutePath() + "/ServiceAssistant/OtherStats/", CurrentMonthFilePath + "mags.txt");
            String dir = "/ServiceAssistant/OtherStats/";
            int elementid = R.id.magazineCounter;

            FileOperations FO = new FileOperations();
            FO.addOneToData(file1, dir);
            updateUIElement(file1, elementid);
        }

    };
    View.OnClickListener tapAddBros = new View.OnClickListener() {
        public void onClick(View v) {
            DateOperations DO = new DateOperations();
            String CurrentMonthFilePath = DO.getdateFile();
            File root = android.os.Environment.getExternalStorageDirectory();
            java.io.File file1 = new java.io.File(root.getAbsolutePath() + "/ServiceAssistant/OtherStats/", CurrentMonthFilePath + "bro.txt");
            String dir = "/ServiceAssistant/OtherStats/";
            int elementid = R.id.brochureCounter;

            FileOperations FO = new FileOperations();
            FO.addOneToData(file1, dir);
            updateUIElement(file1, elementid);
        }

    };
    View.OnClickListener tapAddBooks = new View.OnClickListener() {
        public void onClick(View v) {
            DateOperations DO = new DateOperations();
            String CurrentMonthFilePath = DO.getdateFile();
            File root = android.os.Environment.getExternalStorageDirectory();
            java.io.File file1 = new java.io.File(root.getAbsolutePath() + "/ServiceAssistant/OtherStats/", CurrentMonthFilePath + "book.txt");
            String dir = "/ServiceAssistant/OtherStats/";
            int elementid = R.id.bookCounter;

            FileOperations FO = new FileOperations();
            FO.addOneToData(file1, dir);
            updateUIElement(file1, elementid);
        }

    };

    public void magUpdater()
    {
        DateOperations DO = new DateOperations();
        String CurrentMonthFilePath = DO.getdateFile();
        File root = android.os.Environment.getExternalStorageDirectory();
        java.io.File file1 = new java.io.File(root.getAbsolutePath() + "/ServiceAssistant/OtherStats/", CurrentMonthFilePath + "mags.txt");
        String dir = "/ServiceAssistant/OtherStats/";
        int elementid = R.id.magazineCounter;

        FileOperations FO = new FileOperations();
        updateUIElement(file1, elementid);
    }

    public void rvUpdater()
    {
        DateOperations DO = new DateOperations();
        String CurrentMonthFilePath = DO.getdateFile();
        File root = android.os.Environment.getExternalStorageDirectory();
        java.io.File file1 = new java.io.File(root.getAbsolutePath() + "/ServiceAssistant/OtherStats/", CurrentMonthFilePath + "rvs.txt");
        String dir = "/ServiceAssistant/OtherStats/";
        int elementid = R.id.rvcounter;

        FileOperations FO = new FileOperations();
        updateUIElement(file1, elementid);
    }

    public void broUpdater()
    {
        DateOperations DO = new DateOperations();
        String CurrentMonthFilePath = DO.getdateFile();
        File root = android.os.Environment.getExternalStorageDirectory();
        java.io.File file1 = new java.io.File(root.getAbsolutePath() + "/ServiceAssistant/OtherStats/", CurrentMonthFilePath + "bro.txt");
        int elementid = R.id.brochureCounter;

        updateUIElement(file1, elementid);
    }
    public void bookUpdater()
    {
        DateOperations DO = new DateOperations();
        String CurrentMonthFilePath = DO.getdateFile();
        File root = android.os.Environment.getExternalStorageDirectory();
        java.io.File file1 = new java.io.File(root.getAbsolutePath() + "/ServiceAssistant/OtherStats/", CurrentMonthFilePath + "book.txt");
        int elementid = R.id.bookCounter;

        updateUIElement(file1, elementid);
    }



    View.OnClickListener tapAdd = new View.OnClickListener() {
        public void onClick(View v) {
            DateOperations DO = new DateOperations();
            String CurrentMonthFilePath = DO.getdateFile();
            File root = android.os.Environment.getExternalStorageDirectory();
            java.io.File file1 = new java.io.File(root.getAbsolutePath() + "/ServiceAssistant/", CurrentMonthFilePath + ".txt");
            String dir = "/ServiceAssistant/";

            FileOperations FO = new FileOperations();
            FO.addOneToData(file1, dir);
            FO.getOldData(file1);
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
        java.io.File file1 = new java.io.File(root.getAbsolutePath() + "/ServiceAssistant/", CurrentMonthFilePath + ".txt");
        String dir = "/ServiceAssistant/";

        FileOperations FO = new FileOperations();
        String NewString = FO.getOldData(file1);
        TextView tv = (TextView)getView().findViewById(R.id.HourCount);
        tv.setText(NewString);
    }
    //File Access
    public void dbCheck(){
        Calendar cal = Calendar.getInstance();
        int currentMonth = cal.get(Calendar.MONTH)+1;
        int currentYear = cal.get(Calendar.YEAR);
        Log.i("INFO", Integer.toString(currentYear));
        if (currentMonth > 9)
        {
            String CurrentMonthFilePath = Integer.toString(currentMonth) + Integer.toString(currentYear);
            //Log.i("INFO", CurrentMonthFilePath);
            File root = android.os.Environment.getExternalStorageDirectory();
            java.io.File file1 = new java.io.File(root.getAbsolutePath() + "/ServiceAssistant/" , CurrentMonthFilePath + ".txt");

            FileOperations FO = new FileOperations();
            String NewString = FO.getOldData(file1);
            TextView tv = (TextView)getView().findViewById(R.id.HourCount);
            tv.setText(NewString);

            if (file1.exists()) {
                Log.i("INFO", "Found the external file!");
                setHoursLabel();
            }
            else{
                // See http://stackoverflow.com/questions/3551821/android-write-to-sd-card-folder

                File dir1 = new File (root.getAbsolutePath() + "/ServiceAssistant/");
                dir1.mkdirs();
                File file = new File(dir1, CurrentMonthFilePath + ".txt");
                int i = 0;

                try {
                    FileOutputStream f = new FileOutputStream(file);
                    PrintWriter pw = new PrintWriter(f);
                    pw.print("0");
                    pw.flush();
                    pw.close();
                    f.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    Log.i("INFO", "******* File not found. Did you" +
                            " add a WRITE_EXTERNAL_STORAGE permission to the   manifest?");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                setHoursLabel();
            }
        }
        else {
            String CurrentMonthFilePath = "0" + Integer.toString(currentMonth) + Integer.toString(currentYear);
            //Log.i("INFO", CurrentMonthFilePath);
            File root = android.os.Environment.getExternalStorageDirectory();
            java.io.File file1 = new java.io.File(root.getAbsolutePath() + "/ServiceAssistant/", CurrentMonthFilePath + ".txt");

            FileOperations FO = new FileOperations();
            String NewString = FO.getOldData(file1);
            TextView tv = (TextView) getView().findViewById(R.id.HourCount);
            tv.setText(NewString);
            if (file1.exists()) {
                Log.i("INFO", "Found the external file!");
                setHoursLabel();
            } else {
                // See http://stackoverflow.com/questions/3551821/android-write-to-sd-card-folder

                File dir1 = new File(root.getAbsolutePath() + "/ServiceAssistant/");
                dir1.mkdirs();
                File file = new File(dir1, CurrentMonthFilePath + ".txt");
                int i = 0;

                try {
                    FileOutputStream f = new FileOutputStream(file);
                    PrintWriter pw = new PrintWriter(f);
                    pw.print("0");
                    pw.flush();
                    pw.close();
                    f.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    Log.i("INFO", "******* File not found. Did you" +
                            " add a WRITE_EXTERNAL_STORAGE permission to the   manifest?");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                setHoursLabel();
            }
        }
    }

    //No File Access
    /*void addTime()
    {
        Intent intent = new Intent(this, AddTime.class);
        startActivity(intent);
    }
    */
    // File Access
    //TODO: Calendar fixes
    void reset()
    {

        Calendar cal = Calendar.getInstance();
        int currentMonth = cal.get(Calendar.MONTH)+1;
        int currentYear = cal.get(Calendar.YEAR);
        Log.i("INFO", Integer.toString(currentYear));
        if (currentMonth > 9) {
            String CurrentMonthFilePath = Integer.toString(currentMonth) + Integer.toString(currentYear);
            //Log.i("INFO", CurrentMonthFilePath);
            File root = android.os.Environment.getExternalStorageDirectory();
            java.io.File file1 = new java.io.File(root.getAbsolutePath() + "/ServiceAssistant/", CurrentMonthFilePath + ".txt");

            try {
                FileOutputStream f = new FileOutputStream(file1);
                PrintWriter pw = new PrintWriter(f);
                pw.print("0");
                pw.flush();
                pw.close();
                f.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Log.i("INFO", "File not found. Did you add a WRITE_EXTERNAL_STORAGE permission to the   manifest?");
            } catch (IOException e) {
                e.printStackTrace();
            }
            setHoursLabel();
            getPrefs();
        }
        else
        {
            String CurrentMonthFilePath = "0" + Integer.toString(currentMonth) + Integer.toString(currentYear);
            //Log.i("INFO", CurrentMonthFilePath);
            File root = android.os.Environment.getExternalStorageDirectory();
            java.io.File file1 = new java.io.File(root.getAbsolutePath() + "/ServiceAssistant/", CurrentMonthFilePath + ".txt");

            try {
                FileOutputStream f = new FileOutputStream(file1);
                PrintWriter pw = new PrintWriter(f);
                pw.print("0");
                pw.flush();
                pw.close();
                f.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Log.i("INFO", "File not found. Did you add a WRITE_EXTERNAL_STORAGE permission to the   manifest?");
            } catch (IOException e) {
                e.printStackTrace();
            }
            setHoursLabel();
            getPrefs();
        }

    }
/*
    public void goDrawer(View view)
    {
        Intent intent = new Intent(this, NavigationDrawer.class);
        startActivity(intent);
    }
    */
    //Preference Access
    void getPrefs(){
        SharedPreferences sharedPrefs = this.getActivity().getSharedPreferences("GoalSettings", Context.MODE_PRIVATE);

        StringBuilder builder1 = new StringBuilder();
        builder1.append("\n Goal: "
                + sharedPrefs.getInt("Goal", -1));
        String final1 = builder1.toString();
        TextView tv2 = (TextView) getView().findViewById(R.id.MainGoalLabel);
        tv2.setText(final1);
        Log.i("INFO","Goal is: " + final1);
        Integer goalNum = sharedPrefs.getInt("Goal", -1);
        progressBarUpdate(goalNum);

    }
    //TODO: Calendar fixes
    void progressBarUpdate(Integer goalNum)
    {
        Calendar cal = Calendar.getInstance();
        int currentMonth = cal.get(Calendar.MONTH)+1;
        int currentYear = cal.get(Calendar.YEAR);
        int currentDay = cal.get(Calendar.DAY_OF_MONTH);
        Log.i("INFO", Integer.toString(currentYear));
        if (currentMonth > 9) {
            String CurrentMonthFilePath = Integer.toString(currentMonth) + Integer.toString(currentYear);
            //Log.i("INFO", CurrentMonthFilePath);
            File root = android.os.Environment.getExternalStorageDirectory();
            java.io.File file1 = new java.io.File(root.getAbsolutePath() + "/ServiceAssistant/", CurrentMonthFilePath + ".txt");

            FileOperations FO = new FileOperations();


            Integer mId = 001;

            ProgressBar pb = (ProgressBar) getView().findViewById(R.id.GoalBar);
            String hoursToDate = FO.getOldData(file1);
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
// Creates an explicit intent for an Activity in your app
                Intent resultIntent = new Intent(String.valueOf(MainActivity.class));

// The stack builder object will contain an artificial back stack for the
// started Activity.
// This ensures that navigating backward from the Activity leads out of
// your application to the Home screen.
                TaskStackBuilder stackBuilder = TaskStackBuilder.create(getActivity());
// Adds the back stack for the Intent (but not the Intent itself)
                stackBuilder.addParentStack(MainActivity.class);
// Adds the Intent that starts the Activity to the top of the stack
                stackBuilder.addNextIntent(resultIntent);
                PendingIntent resultPendingIntent =
                        stackBuilder.getPendingIntent(
                                0,
                                PendingIntent.FLAG_UPDATE_CURRENT
                        );
                mBuilder.setContentIntent(resultPendingIntent);
                NotificationManager mNotificationManager =
                        (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
// mId allows you to update the notification later on.
                mNotificationManager.notify(mId, mBuilder.build());
            }

            TextView left = (TextView) getView().findViewById(R.id.HoursToGo);
            if (hoursToGo <= 0) {
                left.setText("You are done!");
            } else {
                left.setText("Hours to go: " + Integer.toString(hoursToGo));
            }
        }
        else
        {
            String CurrentMonthFilePath = "0" + Integer.toString(currentMonth) + Integer.toString(currentYear);
            File root = android.os.Environment.getExternalStorageDirectory();
            java.io.File file1 = new java.io.File(root.getAbsolutePath() + "/ServiceAssistant/", CurrentMonthFilePath + ".txt");
            FileOperations FO = new FileOperations();
            Integer mId = 001;

            ProgressBar pb = (ProgressBar) getView().findViewById(R.id.GoalBar);
            Log.i("INFO", String.valueOf(file1));
            String hoursToDate = FO.getOldData(file1);
            Integer hourstoDate = Integer.parseInt(hoursToDate);
            pb.setMax(goalNum);
            pb.setProgress(hourstoDate);

            Integer hoursToGo = goalNum - hourstoDate;
            String hoursToGoStr = Integer.toString(hoursToGo);
            TextView left = (TextView) getView().findViewById(R.id.HoursToGo);
            if (hoursToGo <= 0) {
                left.setText("You are done!");
            } else {
                left.setText("Hours to go: " + Integer.toString(hoursToGo));
            }
        }
    }
}

