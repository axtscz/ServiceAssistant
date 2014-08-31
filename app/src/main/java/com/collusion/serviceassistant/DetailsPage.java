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
import com.dropbox.sync.android.DbxAccountManager;

import org.w3c.dom.Text;

import java.io.File;

public class DetailsPage extends ActionBarActivity{

    Integer elementID1 = R.id.hourData;
    Integer elementID2 = R.id.magazineData;
    Integer elementID3 = R.id.RVData;
    Integer elementID4 = R.id.BookData;
    Activity a;

    DbxAccountManager mDbxAcctMgr;
    private static final String APP_SECRET = "29dhk340mmpecsj";
    private static final String APP_KEY = "wup7j5haihrzc11" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_page);
        a = this.getParent();

        getActionBar().setDisplayHomeAsUpEnabled(true);

        DateOperations DO = new DateOperations();
        String CurrentMonthFilePath = DO.getdateFile();

        Bundle b = getIntent().getExtras();
        String value = b.getString("filename");
        String month = b.getString("month");
        String hours = b.getString("hours");
        Log.i("INFO", hours.toString());
        String filename = value.substring(0,6);
        Log.i("DETAILS", filename);
        File root = android.os.Environment.getExternalStorageDirectory();
        java.io.File filemags = new java.io.File(root.getAbsolutePath() + "/ServiceAssistant/"+ CurrentMonthFilePath+"/" , filename + "mags.txt");
        java.io.File filervs = new java.io.File(root.getAbsolutePath() + "/ServiceAssistant/"+ CurrentMonthFilePath+"/", filename + "revs.txt");
        java.io.File filebook = new java.io.File(root.getAbsolutePath() + "/ServiceAssistant/"+ CurrentMonthFilePath+"/", filename + "book.txt");
        java.io.File filehours = new java.io.File(root.getAbsolutePath() + "/ServiceAssistant/"+ CurrentMonthFilePath+"/", filename + "hour.txt");


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView tv = (TextView) findViewById(R.id.Title);
        tv.setText(month);

        TextView tv2 = (TextView) findViewById(R.id.hourData);
        tv2.setText(hours);

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
            case R.id.home:
                Log.i("BACK", "Going back!");
                Intent upIntent = NavUtils.getParentActivityIntent(this);
                NavUtils.navigateUpFromSameTask(this);
                return true;
            case R.id.share :
                share();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void share(){
        Intent sendIntent = new Intent();
        TextView tv = (TextView) findViewById(R.id.hourData);
        TextView tvmags = (TextView) findViewById(R.id.magazineData);
        TextView tvrvs = (TextView) findViewById(R.id.RVData);
        TextView tvbooks = (TextView) findViewById(R.id.BookData);

        CharSequence hours = tv.getText();
        CharSequence mags = tvmags.getText();
        CharSequence rvs = tvrvs.getText();
        CharSequence books = tvbooks.getText();

        String hoursStr = hours.toString();
        String magsStr = mags.toString();
        String rvsStr = rvs.toString();
        String booksStr = books.toString();




        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, "My field service report: " + "\n" +
                                                "Hours: " + hoursStr + "\n" +
                                                "Magazines: " + magsStr + "\n" +
                                                "Return Visits: " + rvsStr + "\n" +
                                                "Books: " + booksStr);
        sendIntent.setType("text/plain");
        startActivity(sendIntent);
    }

    public void updateUIElement(File file1, Integer elementID)
    {
        FileOperations FO = new FileOperations();
        String data = FO.getOldData(file1, mDbxAcctMgr);
        TextView tv = (TextView) findViewById(elementID);
        tv.setText(data);
    }
}
