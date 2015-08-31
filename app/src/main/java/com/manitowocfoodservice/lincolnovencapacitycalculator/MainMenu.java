package com.manitowocfoodservice.lincolnovencapacitycalculator;

import android.app.ActivityManager;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainMenu extends AppCompatActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_main_menu);

		Resources r = getResources();
		Bitmap logo = BitmapFactory.decodeResource(r, R.mipmap.app_logo);
		ActivityManager.TaskDescription description = new ActivityManager.TaskDescription(null, logo, r.getColor(R.color.primary));
		setTaskDescription(description);

		findViewById(R.id.capacity_calculator_button).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				goToCalculator(v);
			}
		});
		findViewById(R.id.oven_comparison_button).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				goToCompareOvens(v);
			}
		});
		findViewById(R.id.spec_sheet_link_button).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				goToSpecSheets(v);
			}
		});
	}

	public void goToCalculator(View view) {
		Intent intent = new Intent(this, CapacityCalculatorActivity.class);
		startActivity(intent);
	}

	public void goToCompareOvens(View view) {
		Intent intent = new Intent(this, OvenComparison.class);
		startActivity(intent);
	}

	public void goToSpecSheets(View view) {
		Intent intent = new Intent(this, SpecSheetLinks.class);
		startActivity(intent);
	}
}
