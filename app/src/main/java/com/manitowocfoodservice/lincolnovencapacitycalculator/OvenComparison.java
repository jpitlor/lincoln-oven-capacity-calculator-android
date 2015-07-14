package com.manitowocfoodservice.lincolnovencapacitycalculator;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.*;
import android.widget.*;

import java.util.ArrayList;


public class OvenComparison extends Activity {
	boolean[] ovenIndices = {false, false, false, false, false, false, false};

	boolean[] propertyIndices = {true, true, true, true, true, true};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_oven_comparison);
		showOvenPopUp();
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
				.setMultiChoiceItems(R.array.properties_no_newlines, propertyIndices, new DialogInterface.OnMultiChoiceClickListener() {
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
							refreshModels();
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

	private void refreshModels() {
		LinearLayout propNames = (LinearLayout) findViewById(R.id.models);
		propNames.removeAllViews();

		TextView title = new TextView(this);
		title.setText("\nModel");
		applyModelNameTheme(title);
		propNames.addView(title);

		for (int index = 0; index < ovenIndices.length; index++) {
			if (ovenIndices[index]) {
				TextView textView = new TextView(this);
				textView.setText(getResources().getStringArray(R.array.models_extended)[index]);
				applyPropertyTheme(textView);
				propNames.addView(textView);
			}
		}
		refreshMatrix();
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
							refreshModels();
							refreshMatrix();
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

	private void refreshMatrix() {
		TableLayout comparisonMatrix = (TableLayout) findViewById(R.id.comparison_grid);
		comparisonMatrix.removeAllViews();

		ArrayList<Integer> modelsToCompare = new ArrayList<>();
		for (int index = 0; index < ovenIndices.length; index++) {
			if (ovenIndices[index]) {
				modelsToCompare.add(index);
			}
		}

		ArrayList<Integer> propsToUse = new ArrayList<>();
		for (int index = 0; index < propertyIndices.length; index++) {
			if (propertyIndices[index]) {
				propsToUse.add(index);
			}
		}

		Resources r = getResources();

		TableRow properties = new TableRow(this);
		for (int prop : propsToUse) {
			String propName = r.getStringArray(R.array.properties)[prop];
			TextView property = new TextView(this);
			property.setText(propName);
			applyModelNameTheme(property);
			properties.addView(property);
		}
		comparisonMatrix.addView(properties);

		for (int ovenIndex : modelsToCompare) {
			TableRow row = new TableRow(this);

			for (int propertyIndex : propsToUse) {
				TextView cell = new TextView(this);
				cell.setText(r.getStringArray(getPropertyArray(propertyIndex))[ovenIndex]);
				applyTheme(cell);

				row.addView(cell);
			}
			comparisonMatrix.addView(row);
		}
	}

	private int getPropertyArray(int index) {
		switch (index) {
			case 0:
				return R.array.belt_widths;
			case 1:
				return R.array.chamber_lengths;
			case 2:
				return R.array.stacking;
			case 3:
				return R.array.gas_elec;
			case 4:
				return R.array.elec_ventless_avail;
			default:
				return R.array.half_pass_door;
		}
	}

	private void applyPropertyTheme(TextView text) {
		text.setTextColor(getResources().getColor(R.color.primary));
		text.setTextSize(25);
		text.setHeight((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 40, getResources().getDisplayMetrics()));
		text.setGravity(Gravity.CENTER);
		text.setPadding(30, 0, 30, 0);
	}

	private void applyModelNameTheme(TextView text) {
		text.setTextColor(getResources().getColor(R.color.textPrimary));
		text.setTextSize(25);
		text.setHeight((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 80, getResources().getDisplayMetrics()));
		text.setGravity(Gravity.CENTER);
		text.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
		text.setPadding(30, 0, 30, 0);
	}

	private void applyTheme(TextView text) {
		text.setTextColor(getResources().getColor(R.color.white));
		text.setTextSize(30);
		text.setHeight((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 40, getResources().getDisplayMetrics()));
		text.setGravity(Gravity.CENTER);
		text.setTypeface(text.getTypeface(), Typeface.BOLD);
		text.setPadding(30, 0, 30, 0);
	}

	public void showOvenPopUp(View view) {
		showOvenPopUp();
	}

	public void showPropertyPopUp(View view) {
		showPropertyPopUp();
	}
}
