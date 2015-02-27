package com.example.demoandroid;

import com.example.demoandroid.activitytest.TestActivity;
import com.example.demoandroid.broadCast.BroadcastActivity;
import com.example.demoandroid.broadCast.NetworkStateReceiver;
import com.example.demoandroid.flip.ViewFlipperDemoActivity;
import com.example.demoandroid.wifipassword.WifiActivity;


import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {
 
	
	private Button mBtn1, mBtn2, mBtn3, mBtn4, mBtn5, mBtn6, mBtn7, mBtn8,
			mBtn9;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 加载视图配置
		setContentView(R.layout.activity_main);

		mBtn1 = (Button) findViewById(R.id.main_button1);
		mBtn2 = (Button) findViewById(R.id.main_button2);
		mBtn3 = (Button) findViewById(R.id.main_button3);
		mBtn4 = (Button) findViewById(R.id.main_button4);
		mBtn5 = (Button) findViewById(R.id.main_button5);
		mBtn7= (Button) findViewById(R.id.main_button7);
		

		mBtn1.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent testIntent = new Intent(MainActivity.this,
						TestActivity.class);
				// 仅仅是跳转到目标页面，若是想跳回当前页面，则必须再使用一次startActivity( )。
				startActivity(testIntent);
				/** -------------------------------- **/
				// 可以一次性完成这项任务，当程序执行到这段代码的时候，假若从T1Activity跳转到下一个Text2Activity，
				// 而当这个Text2Activity调用了finish()方法以后，程序会自动跳转回T1Activity，
				// 并调用前一个T1Activity中的onActivityResult( )方法。
				// startActivityForResult(bintent, requestCode);

			}
		});

		mBtn2.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent testIntent = new Intent(MainActivity.this,
						BroadcastActivity.class);
				startActivity(testIntent);
			}

		});
		// 连接wifi
		mBtn3.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent testIntent = new Intent(MainActivity.this,
						WifiActivity.class);
				startActivity(testIntent);
			}

		});
		
		mBtn4.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent testIntent = new Intent(MainActivity.this,
						WifiActivity.class);
				startActivity(testIntent);
			}

		});
		
		
		mBtn7.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent testIntent = new Intent(MainActivity.this,
						ViewFlipperDemoActivity.class);
				startActivity(testIntent);
			}

		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	/*
	 * 返回结果回调
	 * 
	 * @see android.app.Activity#onActivityResult(int, int,
	 * android.content.Intent)
	 */
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (resultCode) { // resultCode为回传的标记，我在B中回传的是RESULT_OK
		case RESULT_OK:
			Bundle b = data.getExtras(); // data为B中回传的Intent
			String str = b.getString("str1");// str即为回传的值
			break;
		default:
			break;
		}
	}
	private void doBroadcast(){
	 
		IntentFilter intentFilter = new IntentFilter();
	 
		intentFilter.addAction("cn.abel.action.broadcast");
		registerReceiver(new NetworkStateReceiver(), intentFilter);
	}
}
