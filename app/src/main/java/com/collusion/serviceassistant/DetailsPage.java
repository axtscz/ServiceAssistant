package com.collusion.serviceassistant;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.collusion.serviceassistant.R;

import org.w3c.dom.Text;

import java.io.File;

public class DetailsPage extends ActionBarActivity{

    Integer elementID1 = R.id.hourData;
    Integer elementID2 = R.id.magazineData;
    Integer elementID3 = R.id.RVData;
    Integer elementID4 = R.id.BookData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_page);

        getActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle b = getIntent().getExtras();
        String value = b.getString("filename");
        String month = b.getString("month");
        String filename = value.substring(0,6);
        Log.i("DETAILS", filename);
        File root = android.os.Environment.getExternalStorageDirectory();
        java.io.File filemags = new java.io.File(root.getAbsolutePath() + "/ServiceAssistant/OtherStats/" , filename + "mags.txt");
        java.io.File filervs = new java.io.File(root.getAbsolutePath() + "/ServiceAssistant/OtherStats/" , filename + "rvs.txt");
        java.io.File filebook = new java.io.File(root.getAbsolutePath() + "/ServiceAssistant/OtherStats/" , filename + "book.txt");
        java.io.File filehours = new java.io.File(root.getAbsolutePath() + "/ServiceAssistant/" , filename + ".txt");


        getActionBar().setDisplayHomeAsUpEnabled(true);

        TextView tv = (TextView) findViewById(R.id.Title);
        tv.setText(month);

        updateUIElement(filemags,elementID2);
        updateUIElement(filervs,elementID3);
        updateUIElement(filebook,elementID4);
        updateUIElement(filehours,elementID1);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.detailsmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.share :
                share();
                return true;

            case R.id.home:
                Log.i("BACK", "Going back!");
                Intent upIntent = NavUtils.getParentActivityIntent(this);
                if (NavUtils.shouldUpRecreateTask(this, upIntent)) {
                    // This activity is NOT part of this app's task, so create a new
                    // task
                    // when navigating up, with a synthesized back stack.
                    TaskStackBuilder.create(this)
                            // Add all of this activity's parents to the back stack
                            .addNextIntentWithParentStack(upIntent)
                                    // Navigate up to the closest parent
                            .startActivities();
                } else {
                    // This activity is part of this app's task, so simply
                    // navigate up to the logical parent activity.
                    NavUtils.navigateUpTo(this, upIntent);
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void share(){
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, "Hey guys!");
        sendIntent.setType("text/plain");
        startActivity(sendIntent);
    }

    public void updateUIElement(File file1, Integer elementID)
    {
        FileOperations FO = new FileOperations();
        String data = FO.getOldData(file1);
        TextView tv = (TextView) findViewById(elementID);
        tv.setText(data);
    }
}
