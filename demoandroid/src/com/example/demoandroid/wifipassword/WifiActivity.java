package com.example.demoandroid.wifipassword;

import com.example.demoandroid.R;

import android.net.wifi.WifiManager;
import android.os.Bundle;

import android.app.Activity;
import android.content.Context;

import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class WifiActivity extends Activity implements WifiConnCallBack {
	private static final String TAG = WifiActivity.class.getSimpleName();
	Button myButton1, myButton2, myButton3;
 
	TextView show;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_wifi_pass);
		myButton1 = (Button) findViewById(R.id.wifibutton1);
		myButton2 = (Button) findViewById(R.id.wifibutton2);
		myButton3 = (Button) findViewById(R.id.wifibutton3);
		show = (TextView) findViewById(R.id.wifitextView1);

		// 获取wifi 列表----------------------------------------------------
		myButton1.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				WifiManager wm = (WifiManager) WifiActivity.this
						.getSystemService(Context.WIFI_SERVICE);
				WifiAutoConnectManager wac = new WifiAutoConnectManager(wm,
						WifiActivity.this);
				// 获取文件列表
				wac.getWifiList();

			}
		});

		// 写文件
		myButton2.setOnClickListener(new OnClickListener() {
			WifiManager wm = (WifiManager) WifiActivity.this
					.getSystemService(Context.WIFI_SERVICE);

			@Override
			public void onClick(View arg0) {
				WifiAutoConnectManager wac = new WifiAutoConnectManager(wm,
						WifiActivity.this);
				wac.saveWifiConfig("angmafan", "cellstar2001hanzhengdujing");
				Log.d(TAG, "savefileOK---------");
			}

		});

		// wifi连接
		myButton3.setOnClickListener(new OnClickListener() {
			WifiManager wm = (WifiManager) WifiActivity.this
					.getSystemService(Context.WIFI_SERVICE);

			@Override
			public void onClick(View arg0) {
				WifiAutoConnectManager wac = new WifiAutoConnectManager(wm,
						WifiActivity.this);
				wac.connect();
			}

		});
	}

	/* 回调接口  用于
	 * @see com.example.demoandroid.wifipassword.WifiConnCallBack#wifiCallBack(int, java.lang.String, java.lang.String[])
	 */
	@Override
	public void wifiCallBack(int state, String message, String[] arrays) {
		switch (state) {
		// 成功
		case 1: {
			 
			Log.d(TAG, "conn---------" + message);
			break;
		}

		// 获取连接列表
		case 11: {
			Log.d(TAG, message);
			for (String aa : arrays) {
				Log.d(TAG, "getwifiList---------" + aa);
			}
			break;
		}
		}
	}
}
