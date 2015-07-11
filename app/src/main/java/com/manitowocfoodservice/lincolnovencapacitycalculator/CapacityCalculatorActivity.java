package com.manitowocfoodservice.lincolnovencapacitycalculator;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;


public class CapacityCalculatorActivity extends Activity implements AdapterView.OnItemSelectedListener {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_capacity_calculator);

		Spinner spinner = (Spinner) findViewById(R.id.pan_type);
		ArrayAdapter<CharSequence> adapter;
		adapter = ArrayAdapter.createFromResource(this, R.array.pan_types,
		                                          /* android.R.layout.simple_spinner_item); */
		                                          R.layout.layout_spinner);
		adapter.setDropDownViewResource(R.layout.layout_spinner);
		//adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);
		spinner.setOnItemSelectedListener(this);
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
		TextView diameterLabel = (TextView) findViewById(R.id.pan_diameter_label);
		EditText diameter = (EditText) findViewById(R.id.pan_diameter);

		TextView widthLabel = (TextView) findViewById(R.id.pan_width_label);
		EditText width = (EditText) findViewById(R.id.pan_width);

		TextView lengthLabel = (TextView) findViewById(R.id.pan_length_label);
		EditText length = (EditText) findViewById(R.id.pan_length);

		if (position == 1) { // Round pan
			diameter.setText("");

			diameter.setVisibility(View.VISIBLE);
			diameterLabel.setVisibility(View.VISIBLE);

			widthLabel.setVisibility(View.INVISIBLE);
			width.setVisibility(View.INVISIBLE);

			length.setVisibility(View.INVISIBLE);
			lengthLabel.setVisibility(View.INVISIBLE);
		} else if (position == 2) { // Rectangular pan
			diameter.setVisibility(View.INVISIBLE);
			diameterLabel.setVisibility(View.INVISIBLE);

			length.setText("");
			width.setText("");

			widthLabel.setVisibility(View.VISIBLE);
			width.setVisibility(View.VISIBLE);

			length.setVisibility(View.VISIBLE);
			lengthLabel.setVisibility(View.VISIBLE);
		}
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		// Do nothing
	}

	public void calculate(View view) {
		double beltWidth = Double.parseDouble(((EditText) findViewById(R.id.belt_width)).getText().toString());
		double ovenCapacity = Double.parseDouble(((EditText) findViewById(R.id.oven_capacity)).getText().toString());
		double bakeTime = Double.parseDouble(((EditText) findViewById(R.id.bake_time)).getText().toString());

		double capacity;

		Spinner panTypeSpinner = (Spinner) findViewById(R.id.pan_type);
		if (panTypeSpinner.getSelectedItemPosition() == 1) { // Round pan
			double diameter = Double.parseDouble(((EditText) findViewById(R.id.pan_diameter)).getText().toString());

			capacity = new CapacityCalculator(beltWidth, ovenCapacity, bakeTime, diameter).calculateCapacity();
		} else if (panTypeSpinner.getSelectedItemPosition() == 2) {// Rectangular pan
			double length = Double.parseDouble(((EditText) findViewById(R.id.pan_length)).getText().toString());
			double width = Double.parseDouble(((EditText) findViewById(R.id.pan_width)).getText().toString());

			capacity = new CapacityCalculator(beltWidth, ovenCapacity, bakeTime, length, width).calculateCapacity();
		} else {
			capacity = 0.0;
		}

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("The capacity is " + capacity);

		AlertDialog dialog = builder.create();
		dialog.show();
	}
}
