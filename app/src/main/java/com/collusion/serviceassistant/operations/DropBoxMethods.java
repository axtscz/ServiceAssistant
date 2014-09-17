package com.collusion.serviceassistant.operations;

import android.content.Context;
import android.util.Log;

import com.dropbox.sync.android.DbxAccountManager;
import com.dropbox.sync.android.DbxException;
import com.dropbox.sync.android.DbxFile;
import com.dropbox.sync.android.DbxFileInfo;
import com.dropbox.sync.android.DbxFileSystem;
import com.dropbox.sync.android.DbxPath;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

/**
 * Created by Antonio on 8/26/2014.
 */
public class DropBoxMethods {

    public void doDropboxTest(Context activity) throws DbxException.Unauthorized {
        DbxAccountManager mDbxAcctMgr = DbxAccountManager.getInstance(activity, "wup7j5haihrzc11", "29dhk340mmpecsj");
        try {
            final String TEST_DATA = "Hello Dropbox";
            final String TEST_FILE_NAME = "Pars";

            DbxPath testPath = new DbxPath(DbxPath.ROOT, TEST_FILE_NAME);

            // Create DbxFileSystem for synchronized file access.
            DbxFileSystem dbxFs = DbxFileSystem.forAccount(mDbxAcctMgr.getLinkedAccount());

            // Print the contents of the root folder.  This will block until we can
            // sync metadata the first time.
            List<DbxFileInfo> infos = dbxFs.listFolder(DbxPath.ROOT);
            Log.i("DB", "Contents of app folder:");
            for (DbxFileInfo info : infos) {
                Log.i("DB","    " + info.path + ", " + info.modifiedTime + '\n');
            }

            // Create a test file only if it doesn't already exist.
            if (!dbxFs.exists(testPath)) {
                DbxFile testFile = dbxFs.create(testPath);
                try {
                    testFile.writeString(TEST_DATA);
                } finally {
                    testFile.close();
                }
                Log.i("DB","\nCreated new file '" + testPath + "'.\n");
            }

            // Read and print the contents of test file.  Since we're not making
            // any attempt to wait for the latest version, this may print an
            // older cached version.  Use getSyncStatus() and/or a listener to
            // check for a new version.
            if (dbxFs.isFile(testPath)) {
                String resultData;
                DbxFile testFile = dbxFs.open(testPath);
                try {
                    resultData = testFile.readString();
                } finally {
                    testFile.close();
                }
                Log.i("DB","\nRead file '" + testPath + "' and got data:\n    " + resultData);
            } else if (dbxFs.isFolder(testPath)) {
                Log.i("DB","'" + testPath.toString() + "' is a folder.\n");
            }
        } catch (IOException e) {
           Log.i("DB", "Dropbox test failed: " + e);
        }
    }

    public void uploadNumberFile(String filename, String data, DbxAccountManager mDbxAcctMgr, String fullfile) throws FileNotFoundException, DbxException {
        DateOperations DO = new DateOperations();
        String date = DO.getdateFile();
        Log.i("DBX", date);
        Log.i("DBXFile", filename);
        String path = filename + "/";
        int len = fullfile.length();
        Log.i("Numbers", Integer.toString(len));
        String addition = fullfile.substring(len-8, len-4);
        Log.i("DBX", addition);
        String file = filename + addition + ".txt";
        DbxPath testPath = new DbxPath(DbxPath.ROOT, path + file);
        DbxFileSystem dbxFs = DbxFileSystem.forAccount(mDbxAcctMgr.getLinkedAccount());
        if (!dbxFs.exists(testPath)) {
            DbxFile testFile = dbxFs.create(testPath);
            try {
                testFile.writeString(data);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                testFile.close();
            }
        }
        else {
            DbxFile testFile = dbxFs.open(testPath);
            try {
                testFile.writeString(data);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                testFile.close();
            }
            Log.i("DB","\nRead file '" + testPath + "' and got data:\n    " + data);
        }
    }
}
