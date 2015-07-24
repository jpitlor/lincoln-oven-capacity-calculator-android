package com.manitowocfoodservice.lincolnovencapacitycalculator;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;

public class SpecSheet extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_spec_sheet);

		WebView webView = (WebView) findViewById(R.id.pdfViewer);
		webView.getSettings().setJavaScriptEnabled(true);
		String id = getIntent().getStringExtra("id");
		String pdf = "http://www.lincolnfp.com/asset/?id=" + id;
		webView.loadUrl("http://docs.google.com/gview?embedded=true&url=" + pdf);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_spec_sheet, menu);
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
}
