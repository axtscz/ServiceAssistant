package com.collusion.serviceassistant.operations;

import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by Antonio on 8/26/2014.
 */
public class FileOperations2 {
    public void createFolder(File file1, String dirname)
    {
        if (file1.exists()){
            Log.i("FO", "File Found!");
        }
        else {
            Log.i("INFO", "Gone to else");
            File root = android.os.Environment.getExternalStorageDirectory();
            File dir1 = new File(root.getAbsolutePath() + dirname);
            dir1.mkdir();
        }
    }

    /**
     * Returns an Image object that can then be painted on the screen.
     * The url argument must specify an absolute {@link URL}. The name
     * argument is a specifier that is relative to the url argument.
     * <p>
     * This method always returns immediately, whether or not the
     * image exists. When this applet attempts to draw the image on
     * the screen, the data will be loaded. The graphics primitives
     * that draw the image will incrementally paint on the screen.
     *
     * @param  url  an absolute URL giving the base location of the image
     * @param  name the location of the image, relative to the url argument
     * @return      the image at the specified URL
     * @see         Image
     */

    public void createfile(File file1, String dirname) //creates a datafile with a value of 0
    {
        if (file1.exists()){
            Log.i("FO", "File Exists");
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
}
