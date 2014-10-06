package com.collusion.serviceassistant;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.collusion.serviceassistant.ReturnVisits.ReturnVisitDetails;
import com.collusion.serviceassistant.ReturnVisits.ReturnVisits;
import com.collusion.serviceassistant.operations.BaseDemoActivity;
import com.dropbox.sync.android.DbxAccountManager;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;

public class MainActivity extends Activity {
    private static final int RESOLVE_CONNECTION_REQUEST_CODE = 0;
    private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;

	// nav drawer title
	private CharSequence mDrawerTitle;

	// used to store app title
	private CharSequence mTitle;

	// slide menu items
	private String[] navMenuTitles;
	private TypedArray navMenuIcons;

	private ArrayList<NavDrawerItem> navDrawerItems;
	private NavDrawerListAdapter adapter;

    private DbxAccountManager mDbxAcctMgr;
    GoogleApiClient mGoogleApiClient;
    private int requestCode;
    private int resultCode;
    private Intent data;

    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mTitle = mDrawerTitle = getTitle();

		// load slide menu items
		navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);

		// nav drawer icons from resources
		navMenuIcons = getResources()
				.obtainTypedArray(R.array.nav_drawer_icons);

		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.list_slidermenu);

		navDrawerItems = new ArrayList<NavDrawerItem>();

		// adding nav drawer items to array
		// Home
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[0], navMenuIcons.getResourceId(0, -1)));
		// Find People
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[1], navMenuIcons.getResourceId(1, -1)));
		// Photos
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[2], navMenuIcons.getResourceId(2, -1)));
		// Communities, Will add a counter here
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[3], navMenuIcons.getResourceId(3, -1)));
		// Pages
		//navDrawerItems.add(new NavDrawerItem(navMenuTitles[4], navMenuIcons.getResourceId(4, -1)));
		// What's hot, We  will add a counter here
		//navDrawerItems.add(new NavDrawerItem(navMenuTitles[5], navMenuIcons.getResourceId(5, -1), true, "50+"));
		

		// Recycle the typed array
		navMenuIcons.recycle();

		mDrawerList.setOnItemClickListener(new SlideMenuClickListener());

		// setting the nav drawer list adapter
		adapter = new NavDrawerListAdapter(getApplicationContext(),
				navDrawerItems);
		mDrawerList.setAdapter(adapter);

		// enabling action bar app icon and behaving it as toggle button
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);

		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.drawable.ic_drawer, //nav menu toggle icon
				R.string.app_name, // nav drawer open - description for accessibility
				R.string.app_name // nav drawer close - description for accessibility
		) {
			public void onDrawerClosed(View view) {
				getActionBar().setTitle(mTitle);
				// calling onPrepareOptionsMenu() to show action bar icons
				invalidateOptionsMenu();
			}

			public void onDrawerOpened(View drawerView) {
				getActionBar().setTitle(mDrawerTitle);
				// calling onPrepareOptionsMenu() to hide action bar icons
				invalidateOptionsMenu();
			}
		};
		mDrawerLayout.setDrawerListener(mDrawerToggle);

		if (savedInstanceState == null) {
			// on first time display view for first nav item
			displayView(0);
		}

    }

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        this.requestCode = requestCode;
        this.resultCode = resultCode;
        this.data = data;
        switch (requestCode) {
            case RESOLVE_CONNECTION_REQUEST_CODE:
                if (resultCode == RESULT_OK) {
                    mGoogleApiClient.connect();
                }
                break;
        }
    }

	/**
	 * Slide menu item click listener
	 * */
	private class SlideMenuClickListener implements
			ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// display view for selected nav drawer item
			displayView(position);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// toggle nav drawer on selecting action bar app icon/title
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		// Handle action bar actions click
		switch (item.getItemId()) {
		case R.id.action_settings:
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	/* *
	 * Called when invalidateOptionsMenu() is triggered
	 */
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// if nav drawer is opened, hide the action items
		boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
		menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
		return super.onPrepareOptionsMenu(menu);
	}

	/**
	 * Diplaying fragment view for selected nav drawer list item
	 * */
	private void displayView(int position) {
		// update the main content by replacing fragments
		Fragment fragment = null;
		switch (position) {
		case 0:
			fragment = new HomeFragment();
			break;
		case 1:
			fragment = new HistoryFragment();
			break;
		case 2:
			//fragment = new TractCounter();
			break;
		case 3:
			fragment = new ReturnVisits();
			break;
		//case 5:
			//fragment = new WhatsHotFragment();
			//break;

		default:
			break;
		}

		if (fragment != null) {
			FragmentManager fragmentManager = getFragmentManager();
			fragmentManager.beginTransaction()
                    .setCustomAnimations(R.anim.slide_up,
                            R.anim.slide_down,
                            R.anim.slide_up,
                            R.anim.slide_down)
					.replace(R.id.frame_container, fragment).commit();

			// update selected item and title, then close the drawer
			mDrawerList.setItemChecked(position, true);
			mDrawerList.setSelection(position);
			setTitle(navMenuTitles[position]);
			mDrawerLayout.closeDrawer(mDrawerList);
		} else {
			// error in creating fragment
			Log.e("MainActivity", "Error in creating fragment");
		}
	}

	@Override
	public void setTitle(CharSequence title) {
		mTitle = title;
		getActionBar().setTitle(mTitle);
	}

	/**
	 * When using the ActionBarDrawerToggle, you must call it during
	 * onPostCreate() and onConfigurationChanged()...
	 */

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Pass any configuration change to the drawer toggls
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

    public void onListItemClick(View view) {
        Fragment fragment;
        TextView tv = (TextView)view.findViewById(R.id.filename);
        TextView textview = (TextView)view.findViewById(R.id.month);
        TextView hours = (TextView)view.findViewById(R.id.data);
        CharSequence month = tv.getText();
        String monthStr = month.toString();

        CharSequence month1 = textview.getText();
        String monthStr1 = month1.toString();

        CharSequence hours1 = hours.getText();
        String hoursStr1 = hours.toString();

        SharedPreferences.Editor editor = getSharedPreferences("details", Context.MODE_PRIVATE).edit();

        editor.putString("Title", monthStr1);
        editor.putString("filename", monthStr);
        editor.putString("hours", hoursStr1);
        editor.apply();
        fragment = new Details();

        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.frame_container, fragment).commit();
    }

    public void onClick(View v) {
        mDbxAcctMgr.startLink((Activity)this, 0);
    }


    public void rvClick(View view)
    {

        Log.i("TRANSITION", "STARTING TRANSITION");
        TextView name = (TextView)view.findViewById(R.id.nameView);
        String nameStr = name.getText().toString();

        TextView address = (TextView)view.findViewById(R.id.addressView);
        String addressStr = address.getText().toString();

        TextView longitude = (TextView)view.findViewById(R.id.longitude);
        String longstr = longitude.getText().toString();

        TextView latitude = (TextView)view.findViewById(R.id.latitude);
        String latstr = latitude.getText().toString();

        TextView latlng = (TextView)view.findViewById(R.id.latlong);
        String latlongstr = latlng.getText().toString();

        TextView filename = (TextView)view.findViewById(R.id.rvFileName);
        String filenamestr = filename.getText().toString();


        Log.i("Numbers", longstr);
        Log.i("Numbers", latlongstr);

        SharedPreferences.Editor editor = getSharedPreferences("rvs", Context.MODE_PRIVATE).edit();

        editor.putString("name", nameStr);
        editor.putString("address", addressStr);
        editor.putString("lat", latstr);
        editor.putString("long", longstr);
        editor.putString("latlong", latlongstr);
        editor.putString("filename", filenamestr);
        editor.apply();
        /*
        Intent intent = new Intent(this, mapsActivity.class);
        startActivity(intent);
        */
        Fragment fragment = new ReturnVisitDetails();

        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.frame_container, fragment).commit();

    }

}
