package com.manitowocfoodservice.lincolnovencapacitycalculator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class SpecSheet extends AppCompatActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_spec_sheet);

		WebView webView = (WebView) findViewById(R.id.pdfViewer);
		webView.getSettings().setJavaScriptEnabled(true);
		String id = getIntent().getStringExtra("id");
		String pdf = "http://www.lincolnfp.com/asset/?id=" + id;

		webView.setWebViewClient(new WebViewClient() {
			public void onPageFinished(WebView view, String url) {
				findViewById(R.id.progressBar).setVisibility(View.GONE);
			}
		});

		webView.loadUrl("http://docs.google.com/gview?embedded=true&url=" + pdf);
		getSupportActionBar().setTitle(getResources().getStringArray(R.array.spec_sheets)[getIntent().getIntExtra("position", 0)] + " Spec Sheet");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		return super.onOptionsItemSelected(item);
	}
}
