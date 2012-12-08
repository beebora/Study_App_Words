package com.words_ver_0_1;

import java.util.ArrayList;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.View;

public class MainController extends FragmentActivity implements
		ActionBar.TabListener {

	/**
	 * The serialization (saved instance state) Bundle key representing the
	 * current tab position.
	 */
	private static final String STATE_SELECTED_NAVIGATION_ITEM = "selected_navigation_item";
	SectionsPagerAdapter mSectionsPagerAdapter;
	ViewPager mViewPager;
	SQLiteDatabase db;
	WordDBHelper mHelper = new WordDBHelper(this);
	Cursor cursor;
	int retn;
	public TempDataSet mTempDataSet; 

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.maincontorller);
		mSectionsPagerAdapter = new SectionsPagerAdapter(
				getSupportFragmentManager());

		mViewPager = (ViewPager) findViewById(R.id.mainpager);
		mViewPager.setAdapter(mSectionsPagerAdapter);

		// Set up the action bar to show tabs.
		final ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		// For each of the sections in the app, add a tab to the action bar.
		actionBar.addTab(actionBar.newTab().setText(R.string.title_section1)
				.setTabListener(this));
		actionBar.addTab(actionBar.newTab().setText(R.string.title_section2)
				.setTabListener(this));
		actionBar.addTab(actionBar.newTab().setText(R.string.title_section3)
				.setTabListener(this));
		actionBar.addTab(actionBar.newTab().setText(R.string.title_section4)
				.setTabListener(this));
		
//		new TempDataSet("respect","respect","존경하다, 존경, 측면");
//		new TempDataSet("make","make","만들다.");
//		new TempDataSet("view","view","시야 , 전망");
//		new TempDataSet("never", "never", "절대로.");
//		new TempDataSet("hello","hello","안녕, 헬로우");
//		new TempDataSet("intel","intel","인텔");
//		new TempDataSet("get","get","얻다");
//		new TempDataSet("of course","of course","물론 ");
//		new TempDataSet("nothing","nothing","no에 thing붙은거 ");
//		new TempDataSet("hungry","hungry","배고픈 ");
	}

	@Override
	public void onRestoreInstanceState(Bundle savedInstanceState) {
		// Restore the previously serialized current tab position.
		if (savedInstanceState.containsKey(STATE_SELECTED_NAVIGATION_ITEM)) {
			getActionBar().setSelectedNavigationItem(
					savedInstanceState.getInt(STATE_SELECTED_NAVIGATION_ITEM));
		}
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		// Serialize the current tab position.
		outState.putInt(STATE_SELECTED_NAVIGATION_ITEM, getActionBar()
				.getSelectedNavigationIndex());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.maincontorller, menu);
		return true;
	}

	public void onTabSelected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
		// When the given tab is selected, show the tab contents in the
		// container view.
		Fragment fragment;
		switch (tab.getPosition()) {
		case 0:
			mViewPager.setVisibility(View.VISIBLE);
			((View) findViewById(R.id.container)).setVisibility(View.GONE);
			return ;
		case 1:
			fragment = new StasticsPart();
			((View) findViewById(R.id.container)).setVisibility(View.VISIBLE);
			mViewPager.setVisibility(View.GONE);
			break;
		case 2:
			fragment = new QuickExamPart();
			((View) findViewById(R.id.container)).setVisibility(View.VISIBLE);
			mViewPager.setVisibility(View.GONE);
			break;
		default:
			fragment = new Testfordev();
			((View) findViewById(R.id.container)).setVisibility(View.VISIBLE);
			mViewPager.setVisibility(View.GONE);
		}
		Bundle args = new Bundle();
		args.putInt("section_number", tab.getPosition() + 1);
		fragment.setArguments(args);
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.container, fragment).commit();
	}

	public void onTabUnselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}

	public void onTabReselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}

	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}
		@Override
		public Fragment getItem(int position) {
			// getItem is called to instantiate the fragment for the given page.
			// Return a DummySectionFragment (defined as a static inner class
			// below) with the page number as its lone argument.
			Fragment fragment = new LearningPart(position, db , mHelper);
			Bundle args = new Bundle();
			args.putInt("section_number", position + 1);
			fragment.setArguments(args);
			return fragment;
		}

		@Override
		public int getCount() {
			db = mHelper.getReadableDatabase();
			cursor = db.rawQuery("SELECT * from Words", null);
			return cursor.getCount();
		}
		@Override
		public CharSequence getPageTitle(int position) {
			ArrayList<String> templist = new ArrayList<String>();
			db = mHelper.getReadableDatabase();
			cursor = db.rawQuery("SELECT name from Words", null);
			for(;cursor.moveToNext();)
				templist.add(cursor.getString(0));
			return templist.get(position).toString();
		}
	}

	
}
