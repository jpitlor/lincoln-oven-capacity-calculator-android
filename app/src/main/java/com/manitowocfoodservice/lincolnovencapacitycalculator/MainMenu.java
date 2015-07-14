package com.manitowocfoodservice.lincolnovencapacitycalculator;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;

public class MainMenu extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_main_menu);

		Resources r = getResources();
		Bitmap logo = BitmapFactory.decodeResource(getResources(), R.mipmap.app_logo);
		ActivityManager.TaskDescription description = new ActivityManager.TaskDescription(null, logo, r.getColor(R.color.primary));
		this.setTaskDescription(description);
	}

	public void goToCalculator(View view) {
		Intent intent = new Intent(this, CapacityCalculatorActivity.class);
		startActivity(intent);
	}

	public void goToSpecSheets(View view) {
		Intent intent = new Intent(this, SpecSheetLinks.class);
		startActivity(intent);
	}

	public void goToCompareOvens(View view) {
		Intent intent = new Intent(this, OvenComparison.class);
		startActivity(intent);
	}
}
