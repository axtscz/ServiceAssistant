package com.collusion.serviceassistant;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.collusion.serviceassistant.R;

import org.w3c.dom.Text;

public class DetailsPage extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_page);

        Bundle b = getIntent().getExtras();
        String value = b.getString("filename");

        getActionBar().setDisplayHomeAsUpEnabled(true);

        TextView tv = (TextView) findViewById(R.id.Title);
        tv.setText(value);

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
        int id = item.getItemId();
        if (id == R.id.share) {
            share();
            return true;
        }
        else if (id == R.id.home)
        {
            NavUtils.navigateUpFromSameTask(this);
            Intent sendIntent = new Intent(this, MainActivity.class);
            startActivity(sendIntent);
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
}
