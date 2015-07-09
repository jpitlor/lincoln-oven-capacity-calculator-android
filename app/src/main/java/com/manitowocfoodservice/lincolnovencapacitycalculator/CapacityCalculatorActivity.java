package com.manitowocfoodservice.lincolnovencapacitycalculator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;


public class CapacityCalculatorActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.capacity_calculator);

		Spinner spinner = (Spinner) findViewById(R.id.pan_type);
		ArrayAdapter<CharSequence> adapter;
		adapter = ArrayAdapter.createFromResource(this, R.array.pan_types,
		                                          android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);
		spinner.setOnItemSelectedListener(this);

		findViewById(R.id.pan_diameter_label).setVisibility(View.INVISIBLE);
		findViewById(R.id.pan_diameter).setVisibility(View.INVISIBLE);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_capacity_calculator, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		//noinspection SimplifiableIfStatement
		if (id == R.id.action_settings) {
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
		TextView diameterLabel = (TextView) findViewById(R.id.pan_diameter_label);
		EditText diameter = (EditText) findViewById(R.id.pan_diameter);

		TextView widthLabel = (TextView) findViewById(R.id.pan_width_label);
		EditText width = (EditText) findViewById(R.id.pan_width);

		TextView lengthLabel = (TextView) findViewById(R.id.pan_length_label);
		EditText length = (EditText) findViewById(R.id.pan_length);

		if (position == 0) { // Round pan
			diameter.setText("");

			diameter.setVisibility(View.VISIBLE);
			diameterLabel.setVisibility(View.VISIBLE);

			widthLabel.setVisibility(View.INVISIBLE);
			width.setVisibility(View.INVISIBLE);

			length.setVisibility(View.INVISIBLE);
			lengthLabel.setVisibility(View.INVISIBLE);
		} else { // The position was 1 (Rectangular pan)
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
}
