package com.manitowocfoodservice.lincolnovencapacitycalculator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainMenu extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_main_menu);
	}

	public void goToCalculator(View view) {
		Intent intent = new Intent(this, CapacityCalculatorActivity.class);
		startActivity(intent);
	}

	public void goToSpecSheets(View view) {
		Intent intent = new Intent(this, SpecSheetLinks.class);
		startActivity(intent);
	}
}
