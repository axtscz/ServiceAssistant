package com.collusion.serviceassistant;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.collusion.serviceassistant.R;
import com.collusion.serviceassistant.operations.FileOperations;

import java.io.File;
import java.io.IOException;

public class ReturnVisitViewer extends Activity {

    File file;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_return_visit_viewer);

        Intent intent = getIntent();
        String path = intent.getData().getPath();
        Log.i("Files", path);
        file = new File(path);
        FileOperations FO = new FileOperations();
        String result = FO.readFileLineNumber(file, 0);
        if (result != null) {
            Log.i("Result", result);
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.return_visit_viewer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void sendEMAIL(View view)
    {
        String pathname= Environment.getExternalStorageDirectory().getAbsolutePath();
        String filename = file.toString();
        File file1 = new File(pathname, filename);
        Intent i = new Intent(Intent.ACTION_SEND);
        i.putExtra(Intent.EXTRA_SUBJECT, "Title");
        i.putExtra(Intent.EXTRA_TEXT, "Content");
        i.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file1));
        i.setType("text/plain");
        startActivity(Intent.createChooser(i, "Choose"));
    }
}
