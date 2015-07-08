package com.manitowocfoodservice.lincolnovencapacitycalculator;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainMenu extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_menu);
	}

	public void goToCalculator(View view) {
		Intent intent = new Intent(this, CapacityCalculator.class);
		startActivity(intent);
	}

	public void goToSpecSheets(View view) {
		Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.manitowocfoodservice.com/"));
		startActivity(browserIntent);
	}
}
