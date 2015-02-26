package com.example.demoandroid.broadCast;

import com.example.demoandroid.R;

import android.os.Bundle;
import android.app.Activity;

import android.content.Intent;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class BroadcastActivity extends Activity {

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_broadcast);
		((Button) findViewById(R.id.brobutton1))
				.setOnClickListener(new OnClickListener() {
					public void onClick(View v) {

					}
				});
		((Button) findViewById(R.id.brobutton2))
				.setOnClickListener(new OnClickListener() {
					public void onClick(View v) {

					}
				});
	}

	private void clickMenuItem(final String action) {
		Intent intent = new Intent(action);
		sendBroadcast(intent);
	}
}
