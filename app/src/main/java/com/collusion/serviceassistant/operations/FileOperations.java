package com.collusion.serviceassistant.operations;

import android.util.Log;

import com.collusion.serviceassistant.ReturnVisits.ReturnVisit;
import com.dropbox.sync.android.DbxAccountManager;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Antonio on 8/13/2014.
 */
public class FileOperations
{
    public java.io.File addOneToData(File file1, String dirname, DbxAccountManager dbxAccountManager)
    {
        if (file1.exists()){
            String oldData = getOldData(file1, dbxAccountManager);
            Log.i("Data", oldData);
            Integer oldDataInt = Integer.parseInt(oldData);
            Integer newData = 1 + oldDataInt;
            String newDataString = Integer.toString(newData);
            try {
                FileOutputStream f = new FileOutputStream(file1);
                PrintWriter pw = new PrintWriter(f);
                pw.print(newDataString);
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
            return file1;
        }
        else {
            Log.i("INFO", "Gone to else");
            // See http://stackoverflow.com/questions/3551821/android-write-to-sd-card-folder
            File root = android.os.Environment.getExternalStorageDirectory();
            File dir1 = new File(root.getAbsolutePath() + dirname);
            dir1.mkdir();
            File file = new File(String.valueOf(file1));
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
        }
        return file1;
    }

    public String getOldData(File Datafile,  DbxAccountManager dbxAccountManager)
    {
        try{
            String fullfile = Datafile.toString();
            DateOperations DO = new DateOperations();
            String filename = DO.getdateFile();
            BufferedReader br = new BufferedReader(new FileReader(Datafile));
            String line = br.readLine();
            Log.i("INFO", line);
            String data = line;
            /*if (dbxAccountManager != null) {
                Log.i("DBX", "Not null");
                if (dbxAccountManager.hasLinkedAccount() == true)
                {
                    Log.i("DBX", "Linked!");
                    DBXM.uploadNumberFile(filename, data, dbxAccountManager, fullfile);
                }
            }*/
            //else {
            //    Log.i("DBX", "Null");
            //}
            return data;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void createfile(File file1, String dirname)
    {
        if (file1.exists()){
            Log.i("INFO", "File Exists");
        }
        else {
            File root = android.os.Environment.getExternalStorageDirectory();
            File dir1 = new File(root.getAbsolutePath() + dirname);
            dir1.mkdir();
            File file = new File(String.valueOf(file1));
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
        }

    }

    public List<String> getFileList(String dirname)
    {
        List<String> filelist = new ArrayList<String>();
        String files;
        String filename;
        File folder = new File(dirname);
        File[] listOfFiles = folder.listFiles();
        for (int i = 0; i < listOfFiles.length; i++)
        {

            if (listOfFiles[i].isFile())
            {
                files = listOfFiles[i].getName();
                if (files.endsWith(".txt") || files.endsWith(".TXT"))
                {
                    int length = files.length();
                    if (length >= 5) {
                        filename = listOfFiles[i].getName();
                        //Log.i("INFO", files);
                        filelist.add(filename);
                    }
                }
            }
        }
        return filelist;
    }

    public List<String> getFolderList(String dirname)
    {
        List<String> filelist = new ArrayList<String>();
        String files;
        String filename;
        File folder = new File(dirname);
        File[] listOfFiles = folder.listFiles();
        Log.i("FOLDERS", listOfFiles.toString());

        for (int i = 0; i < listOfFiles.length; i++)
        {

            if (listOfFiles[i].isDirectory())
            {
                filename = listOfFiles[i].toString();
                Integer len = filename.length();
                Integer start = len - 6;
                filename = filename.substring(start,len);
                Log.i("FILENAME", filename);
                filelist.add(filename);
            }
        }
        return filelist;
    }

    public void reset(File file1)
    {
        try {
            FileOutputStream f = new FileOutputStream(file1);
            PrintWriter pw = new PrintWriter(f);
            pw.print("0");
            pw.flush();
            pw.close();
            f.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Log.i("INFO", "File not found. Did you add a WRITE_EXTERNAL_STORAGE permission to the manifest?");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public java.io.File removeOneToData(File file1, String dirname, DbxAccountManager dbxAccountManager)
    {
        if (file1.exists()){
            String oldData = getOldData(file1, dbxAccountManager);
            Log.i("Data", oldData);
            Integer oldDataInt = Integer.parseInt(oldData);
            Integer newData = oldDataInt - 1;
            String newDataString = Integer.toString(newData);
            try {
                FileOutputStream f = new FileOutputStream(file1);
                PrintWriter pw = new PrintWriter(f);
                pw.print(newDataString);
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
            return file1;
        }
        else {
            Log.i("INFO", "Gone to else");
            File root = android.os.Environment.getExternalStorageDirectory();
            File dir1 = new File(root.getAbsolutePath() + dirname);
            dir1.mkdir();
            File file = new File(String.valueOf(file1));
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
        }
        return file1;
    }

    public String readFileLineNumber(File file, int number){
        FileInputStream fs= null;
        try {
            fs = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        BufferedReader br = new BufferedReader(new InputStreamReader(fs));
        for(int i = 0; i < number; ++i)
            try {
                br.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
        String lineIWant = null;
        try {
            lineIWant = br.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.i("Results", lineIWant);
        return lineIWant;
    }

    public void writeRV(File file, String name, String Address, String day, String placement, String longi, String lat, String dayofWeek, String notes)
    {
        if (file.exists())
        {
            try {
                FileOutputStream f = new FileOutputStream(file);
                PrintWriter pw = new PrintWriter(f);
                pw.print(name + "\n");
                pw.print(Address + "\n");
                pw.print(day + "\n");
                pw.print(placement + "\n");
                pw.print(longi + "\n");
                pw.print(lat + "\n");
                pw.print(dayofWeek + "\n");
                pw.print(notes + "\n");

                pw.flush();
                pw.close();
                f.close();

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else
        {
            try {
                FileOutputStream f = new FileOutputStream(file);
                PrintWriter pw = new PrintWriter(f);
                pw.print(name + "\n");
                pw.print(Address + "\n");
                pw.print(day + "\n");
                pw.print(placement + "\n");
                pw.print(longi + "\n");
                pw.print(lat + "\n");
                pw.print(dayofWeek);
                pw.flush();
                pw.close();
                f.close();

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public ReturnVisit retrieveRV(File file)
    {

        String name = readFileLineNumber(file, 0);
        String address = readFileLineNumber(file, 1);
        String latitudeStr = readFileLineNumber(file, 5);
        String longitudeStr = readFileLineNumber(file, 4);
        String dayofweekSTR = readFileLineNumber(file, 6);
        String notes = readFileLineNumber(file, 7);
        ReturnVisit rv = new ReturnVisit(name, address, file.toString(), longitudeStr, latitudeStr, "empty", dayofweekSTR, "0.0", notes);
        return rv;
    }

    public java.io.File addOneToDataFloat(File file1, String dirname, DbxAccountManager dbxAccountManager, double newdata) {
        if (file1.exists()) {
            String oldData = getOldData(file1, dbxAccountManager);
            Log.i("Data", oldData);
            Double oldDataInt = Double.parseDouble(oldData);
            Double newData = newdata + oldDataInt;
            String newDataString = Double.toString(newData);
            try {
                FileOutputStream f = new FileOutputStream(file1);
                PrintWriter pw = new PrintWriter(f);
                pw.print(newDataString);
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
            return file1;
        } else {
            Log.i("INFO", "Gone to else");
            // See http://stackoverflow.com/questions/3551821/android-write-to-sd-card-folder
            File root = android.os.Environment.getExternalStorageDirectory();
            File dir1 = new File(root.getAbsolutePath() + dirname);
            dir1.mkdir();
            File file = new File(String.valueOf(file1));
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
        }
        return file1;
    }

    public String getOldDataFloat(File Datafile,  DbxAccountManager dbxAccountManager)
    {
        try{
            String fullfile = Datafile.toString();
            DateOperations DO = new DateOperations();
            String filename = DO.getdateFile();
            BufferedReader br = new BufferedReader(new FileReader(Datafile));
            String line = br.readLine();
            Log.i("INFO", line);
            String data = line;
            return data;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public java.io.File removeOneToDataFloat(File file1, String dirname, DbxAccountManager dbxAccountManager)
    {
        if (file1.exists()){
            String oldData = getOldData(file1, dbxAccountManager);
            Log.i("Data", oldData);
            Double oldDataInt = Double.parseDouble(oldData);
            Double newData = oldDataInt - 1.00;
            String newDataString = Double.toString(newData);
            try {
                FileOutputStream f = new FileOutputStream(file1);
                PrintWriter pw = new PrintWriter(f);
                pw.print(newDataString);
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
            return file1;
        }
        else {
            Log.i("INFO", "Gone to else");
            File root = android.os.Environment.getExternalStorageDirectory();
            File dir1 = new File(root.getAbsolutePath() + dirname);
            dir1.mkdir();
            File file = new File(String.valueOf(file1));
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
        }
        return file1;
    }
}
