package com.manitowocfoodservice.lincolnovencapacitycalculator;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;

import java.util.ArrayList;


public class OvenComparison extends Activity {
	boolean[] ovenIndices = {false, false, false, false, false, false, false};
	boolean[] propertyIndices = {true, true, true, true, true, true, true};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_oven_comparison);
		showOvenPopUp();
		refreshProperties();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_oven_comparison, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.toggleOvens:
				showOvenPopUp();
				break;
			case R.id.toggleProperties:
				showPropertyPopUp();
				break;
			case R.id.action_main_menu:
				Intent intent = new Intent(this, MainMenu.class);
				startActivity(intent);
				break;
			case R.id.action_exit:
				Intent intentt = new Intent(Intent.ACTION_MAIN);
				intentt.addCategory(Intent.CATEGORY_HOME);
				intentt.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(intentt);
				break;
			default:
				break;
		}
		return true;
	}

	private void showPropertyPopUp() {
		final boolean[] temp = propertyIndices;
		final Activity activity = this;

		new AlertDialog.Builder(activity)
				.setTitle("Select The Ways In Which You Want To Compare The Ovens")
				.setMultiChoiceItems(R.array.properties, propertyIndices, new DialogInterface.OnMultiChoiceClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which, boolean isChecked) {
						propertyIndices[which] = isChecked;
					}
				})
				.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						int count = 0;
						for (boolean bool : propertyIndices) {
							count = bool ? count + 1 : count;
						}

						if (count < 1) {
							new AlertDialog.Builder(activity)
									.setTitle("Please Select At Least 1 Property")
									.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
										@Override
										public void onClick(DialogInterface dialog, int which) {
											dialog.dismiss();
											showOvenPopUp();
										}
									})
									.show();
						} else {
							refreshProperties();
							dialog.dismiss();
						}
					}
				})
				.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						propertyIndices = temp;
						dialog.dismiss();
					}
				})
				.show();
	}

	private void refreshProperties() {
		LinearLayout propNames = (LinearLayout) findViewById(R.id.properties);
		propNames.removeAllViews();

		for(int index = 0; index < propertyIndices.length; index++) {
			if (propertyIndices[index]) {
				TextView textView = new TextView(this);
				textView.setText(getResources().getStringArray(R.array.properties)[index]);
				textView.setTextAppearance(this, R.style.PropertiesTheme);
				propNames.addView(textView);
			}
		}
		refreshOvens();
	}

	public void showOvenPopUp() {
		final boolean[] temp = ovenIndices;
		final Activity activity = this;

		new AlertDialog.Builder(activity)
				.setTitle("Select 2 Or More Ovens To Compare")
				.setMultiChoiceItems(R.array.models_extended, ovenIndices, new DialogInterface.OnMultiChoiceClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which, boolean isChecked) {
						ovenIndices[which] = isChecked;
					}
				})
				.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						int count = 0;
						for (boolean bool : ovenIndices) {
							count = bool ? count + 1 : count;
						}

						if (count < 2) {
							new AlertDialog.Builder(activity)
									.setTitle("Please Select At Least 2 Ovens To Compare")
									.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
										@Override
										public void onClick(DialogInterface dialog, int which) {
											dialog.dismiss();
											showOvenPopUp();
										}
									})
									.show();
						} else {
							refreshOvens();
							dialog.dismiss();
						}
					}
				})
				.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						ovenIndices = temp;
						dialog.dismiss();
					}
				})
				.show();
	}

	private void refreshOvens() {
		TableLayout comparisonMatrix = (TableLayout) findViewById(R.id.comparison_grid);
		comparisonMatrix.removeAllViews();

		ArrayList<Integer> modelsToCompare = new ArrayList<>();
		for (int index = 0; index < ovenIndices.length; index++) {
			if (ovenIndices[index]) modelsToCompare.add(index);
		}

		Resources resources = getResources();

		// Models
		if (propertyIndices[0]) {
			TableRow models = new TableRow(this);
			for (int index = 0; index < modelsToCompare.size(); index++) {
				TextView model = new TextView(this);
				model.setText(resources.getStringArray(R.array.models_extended)[modelsToCompare.get(index)]);
				model.setTextAppearance(this, R.style.ModelsTheme);
				models.addView(model);
			}
			comparisonMatrix.addView(models);
		}

		// Belt Width
		if (propertyIndices[1]) {
			TableRow beltWidths = new TableRow(this);
			for (int index = 0; index < modelsToCompare.size(); index++) {
				TextView model = new TextView(this);
				model.setText(resources.getStringArray(R.array.belt_widths)[modelsToCompare.get(index)]);
				model.setTextColor(resources.getColor(R.color.white));
				beltWidths.addView(model);
			}
			comparisonMatrix.addView(beltWidths);
		}

		// Chamber Length
		if (propertyIndices[2]) {
			TableRow chamberLengths = new TableRow(this);
			for (int index = 0; index < modelsToCompare.size(); index++) {
				TextView model = new TextView(this);
				model.setText(resources.getStringArray(R.array.chamber_lengths)[modelsToCompare.get(index)]);
				model.setTextColor(resources.getColor(R.color.white));
				chamberLengths.addView(model);
			}
			comparisonMatrix.addView(chamberLengths);
		}

		// Stacking
		if (propertyIndices[3]) {
			TableRow stacking = new TableRow(this);
			for (int index = 0; index < modelsToCompare.size(); index++) {
				TextView model = new TextView(this);
				model.setText(resources.getStringArray(R.array.stacking)[modelsToCompare.get(index)]);
				model.setTextColor(resources.getColor(R.color.white));
				stacking.addView(model);
			}
			comparisonMatrix.addView(stacking);
		}

		// Gas/Electric
		if (propertyIndices[4]) {
			TableRow gasElectric = new TableRow(this);
			for (int index = 0; index < modelsToCompare.size(); index++) {
				TextView model = new TextView(this);
				model.setText(resources.getStringArray(R.array.gas_elec)[modelsToCompare.get(index)]);
				model.setTextColor(resources.getColor(R.color.white));
				gasElectric.addView(model);
			}
			comparisonMatrix.addView(gasElectric);
		}

		// Electric Ventless Available
		if (propertyIndices[5]) {
			TableRow elecVentless = new TableRow(this);
			for (int index = 0; index < modelsToCompare.size(); index++) {
				TextView model = new TextView(this);
				model.setText(resources.getStringArray(R.array.elec_ventless_avail)[modelsToCompare.get(index)]);
				model.setTextColor(resources.getColor(R.color.white));
				elecVentless.addView(model);
			}
			comparisonMatrix.addView(elecVentless);
		}

		// Half Pass Door
		if (propertyIndices[6]) {
			TableRow halfPassDoor = new TableRow(this);
			for (int index = 0; index < modelsToCompare.size(); index++) {
				TextView model = new TextView(this);
				model.setText(resources.getStringArray(R.array.half_pass_door)[modelsToCompare.get(index)]);
				model.setTextColor(resources.getColor(R.color.white));
				halfPassDoor.addView(model);
			}
			comparisonMatrix.addView(halfPassDoor);
		}
	}
}
