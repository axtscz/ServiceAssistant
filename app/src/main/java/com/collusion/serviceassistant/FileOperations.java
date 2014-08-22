package com.collusion.serviceassistant;

import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;


import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Antonio on 8/13/2014.
 */
public class FileOperations
{
    public java.io.File addOneToData(File file1, String dirname)
    {
        if (file1.exists()){

            String oldData = getOldData(file1);
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

    public String getOldData(File Datafile)
    {
        try{
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
                    filename = listOfFiles[i].getName();
                    //Log.i("INFO", files);
                    filelist.add(filename);
                }
            }
        }
        return filelist;
    }
}
