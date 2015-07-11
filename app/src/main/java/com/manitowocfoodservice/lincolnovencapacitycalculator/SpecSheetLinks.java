package com.manitowocfoodservice.lincolnovencapacitycalculator;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * Created by Jordan on 7/8/2015.
 */
public class SpecSheetLinks extends Activity implements AdapterView.OnItemSelectedListener {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_spec_sheet_links);

		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.spec_sheets,
		                                                                     android.R.layout.simple_list_item_1);
		ListView specSheetList = (ListView) findViewById(R.id.spec_sheet_link_list);
		specSheetList.setAdapter(adapter);
		specSheetList.setOnItemSelectedListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_capacity_calculator, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.action_main_menu:
				Intent intent = new Intent(this, MainMenu.class);
				startActivity(intent);
				return true;
			case R.id.action_exit:
				Intent intentt = new Intent(Intent.ACTION_MAIN);
				intentt.addCategory(Intent.CATEGORY_HOME);
				intentt.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(intentt);
				return true;
			default:
				return true;
		}
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
		String URI = "http://www.google.com/";
		switch (position) {
			default:
				break;
		}
		Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(URI));
		startActivity(intent);
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {

	}
}
