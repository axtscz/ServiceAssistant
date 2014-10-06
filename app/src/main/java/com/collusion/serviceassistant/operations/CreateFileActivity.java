package com.collusion.serviceassistant.operations;

import android.os.Bundle;

import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.drive.Drive;
import com.google.android.gms.drive.DriveApi;
import com.google.android.gms.drive.DriveFolder;
import com.google.android.gms.drive.MetadataChangeSet;

/**
 * Created by Antonio on 9/17/2014.
 */
public class CreateFileActivity extends BaseDemoActivity {

    @Override
    public void onConnected(Bundle connectionHint) {
        super.onConnected(connectionHint);
        // create new contents resource
        Drive.DriveApi.newContents(getGoogleApiClient())
                .setResultCallback(contentsCallback);
    }

    final private ResultCallback<DriveApi.ContentsResult> contentsCallback = new
            ResultCallback<DriveApi.ContentsResult>() {
                @Override
                public void onResult(DriveApi.ContentsResult result) {
                    if (!result.getStatus().isSuccess()) {
                        showMessage("Error while trying to create new file contents");
                        return;
                    }

                    MetadataChangeSet changeSet = new MetadataChangeSet.Builder()
                            .setTitle("New file")
                            .setMimeType("text/plain")
                            .setStarred(true).build();
                    // create a file on root folder
                    Drive.DriveApi.getRootFolder(getGoogleApiClient())
                            .createFile(getGoogleApiClient(), changeSet, result.getContents())
                            .setResultCallback(fileCallback);
                }
            };

    final private ResultCallback<DriveFolder.DriveFileResult> fileCallback = new
            ResultCallback<DriveFolder.DriveFileResult>() {
                @Override
                public void onResult(DriveFolder.DriveFileResult result) {
                    if (!result.getStatus().isSuccess()) {
                        showMessage("Error while trying to create the file");
                        return;
                    }
                    showMessage("Created a file: " + result.getDriveFile().getDriveId());
                }
            };
}