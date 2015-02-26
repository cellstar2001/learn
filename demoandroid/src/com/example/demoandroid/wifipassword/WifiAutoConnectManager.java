package com.example.demoandroid.wifipassword;

import com.example.demoandroid.wifipassword.WifiConnectManagerSupport.WifiCipherType;

import android.annotation.SuppressLint;
import android.content.Context;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.net.wifi.WifiConfiguration;

import android.net.wifi.WifiManager;

import android.os.Handler;
import android.os.Message;

import android.util.Log;

public class WifiAutoConnectManager {
	private static final String TAG = WifiAutoConnectManager.class
			.getSimpleName();
	private static final String DEF_PASS = "cellstar2001hanzhengdujing"; // 默认密码
	private static final String FILENAME = "wifi.config"; // 默认文件名
	private static final String DEF_STITILE = "y";// wifi默认前缀
	private static final String DEF_SSID = "angmafan"; // SSid

	private static final int TIMEER = 2000; // 定时器间隔时间
	// private boolean runState = false; // 是否已经运行
	private WifiConnCallBack callback = null;

	private WifiManager wifiManager = null;
	private WifiConnectManagerSupport wifiSupport = null;
	private Context context = null;
	private int count = 0;
	// 实现定时器动能
	Handler handlerTime = new Handler();
	Runnable timeRunnable = new Runnable() {

		@Override
		public void run() {
			// handler自带方法实现定时器
			try {
				Log.d(TAG, "time----------------2");

				// 如果12秒没有返回 返回连接错误
				if (count == 5) {
					count = 0;
					sendError();
				}
				State wifiState = isWifiConnected(context);
				// 如果连接成功
				if (wifiState != null && wifiState.equals(State.CONNECTED)) {
					Log.d(TAG, "time----------------3");
					count = 0;
					handler.sendEmptyMessage(1);
					handlerTime.removeCallbacks(timeRunnable);
				} else { // 非连接成功状态 轮询等待
					Log.d(TAG, "wifiState----------------" + wifiState);
					handlerTime.postDelayed(timeRunnable, TIMEER);
					count++;

				}

			} catch (Exception e) {
				e.printStackTrace();
				sendError();
			 
			}
		}
	};
	@SuppressLint("HandlerLeak")
	Handler handler = new Handler() {

		@Override
		public void dispatchMessage(Message msg) {

			switch (msg.what) {
			// 成功
			case 1: {
				callback.wifiCallBack(msg.what, "wifi连接成功", null);
				break;
			}
			// 负数失败 （wifi连接失败）
			case -1: {
				callback.wifiCallBack(msg.what, "wifi连接失败", null);
				break;
			}// 负数失败(文件列表异常)
			case -2: {
				callback.wifiCallBack(msg.what, "获取wifi列表异常", null);
				break;
			}

			// 获取wifi列表成功
			case 11: {
				callback.wifiCallBack(msg.what, "获取wifi列表成功", (String[]) msg.obj);
				break;
			}
			}
		}
	};

	// 构造函数
	public WifiAutoConnectManager(WifiManager wifiManager,
			WifiConnCallBack callback) {
		this.wifiManager = wifiManager;
		this.wifiSupport = new WifiConnectManagerSupport(wifiManager);
		this.callback = callback;
		this.context = (Context) callback;
	}

	// 提供一个外部接口，传入要连接的无线网
	public void connect(String ssid, String password, WifiCipherType type) {
		Thread thread = new Thread(new ConnectRunnable(ssid, password, type));

		thread.start();
	}

	// 提供一个外部接口，传入要连接的无线网
	public void connect() {
		Thread thread = new Thread(new ConnectRunnable(null, null,
				WifiCipherType.WIFICIPHER_WPA));

		thread.start();
	}

	class ConnectRunnable implements Runnable {
		private String ssid;

		private String password;

		private WifiCipherType type;

		public ConnectRunnable(String ssid, String password, WifiCipherType type) {
			this.ssid = ssid;
			this.password = password;
			this.type = type;
		}

		@Override
		public void run() {
			try {
				// 打开wifi
				wifiSupport.openWifi();
				// 开启wifi功能需要一段时间(我在手机上测试一般需要1-3秒左右)，所以要等到wifi
				// 状态变成WIFI_STATE_ENABLED的时候才能执行下面的语句
				while (wifiManager.getWifiState() == WifiManager.WIFI_STATE_ENABLING) {
					try {
						// 为了避免程序一直while循环，让它睡个100毫秒检测……
						Thread.sleep(100);
					} catch (InterruptedException ie) {
						ie.printStackTrace();
						sendError();
					}
				}

				String[] ssid_pass;

				ssid_pass = wifiSupport.readPassFile(FILENAME);

				// 如果配置文件内容是空的 写入默认配置
				if (ssid_pass == null) {
					wifiSupport.writePassFile(FILENAME, DEF_STITILE + DEF_SSID,
							DEF_PASS);
					wifiSupport.createWifiInfo(DEF_STITILE + DEF_SSID,
							DEF_PASS, type);
					this.ssid = DEF_SSID;
					this.password = DEF_PASS;

				} else {
					this.ssid = ssid_pass[0];
					this.password = ssid_pass[1];
				}

				WifiConfiguration tempConfig = wifiSupport.isExsits(ssid);
				if (tempConfig != null) {
					wifiManager.removeNetwork(tempConfig.networkId);
				}
				WifiConfiguration wifiConfig = wifiSupport.createWifiInfo(ssid,
						password, type);
				//
				if (wifiConfig == null) {
					Log.d(TAG, "wifiConfig is null!");
					return;
				}

				int netID = wifiManager.addNetwork(wifiConfig);

				wifiManager.reconnect();

				wifiManager.enableNetwork(netID, true);

				Message msg = new Message();

				handlerTime.sendMessage(msg);
				Log.d(TAG, "time----------------1");
				handlerTime.postDelayed(timeRunnable, TIMEER);
			} catch (Exception e) {
				e.printStackTrace();
				sendError();
			}
		}
	}

	/**
	 * 写入配置文件
	 * 
	 * @param ssid
	 * @param passWord
	 * @return
	 */
	public boolean saveWifiConfig(String ssid, String passWord) {
		try {
			wifiSupport.writePassFile(FILENAME, DEF_STITILE + ssid, passWord);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}

	/**
	 * 获取前缀匹配的wifi列表
	 * 
	 * @return
	 */
	public void getWifiList() {
		Thread thread = new Thread(new ConnectOppenable());
		thread.start();
	}

	class ConnectOppenable implements Runnable {
		@Override
		public void run() {
			// 打开wifi
			wifiSupport.openWifi();
			// 开启wifi功能需要一段时间(我在手机上测试一般需要1-3秒左右)，所以要等到wifi
			// 状态变成WIFI_STATE_ENABLED的时候才能执行下面的语句
			while (wifiManager.getWifiState() == WifiManager.WIFI_STATE_ENABLING) {
				try {
					// 为了避免程序一直while循环，让它睡个100毫秒检测……
					Thread.sleep(100);
				} catch (InterruptedException ie) {
					ie.printStackTrace();
					handler.sendEmptyMessage(-2);
				}
			}
			Message msg = new Message();
			msg.what = 11;
			msg.obj = wifiSupport.getScanResult(DEF_STITILE);
			handler.sendMessage(msg);

		}
	}

	public void closeWifi() {
		wifiSupport.closeWifi();
		count = 0;
	}

	// /**
	// * 运行状态
	// *
	// * @param state
	// */
	// private void checkRunState(boolean state) {
	// this.runState = state;
	// }

	private State isWifiConnected(Context context) {
		if (context != null) {
			ConnectivityManager mConnectivityManager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo mWiFiNetworkInfo = mConnectivityManager
					.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
			if (mWiFiNetworkInfo != null) {
				// Log.d(TAG,
				// "mWiFiNetworkInfo----------------"+mWiFiNetworkInfo.getState());

				return mWiFiNetworkInfo.getState();
			}
		}
		return null;
	}

	private void sendError() {
		handler.sendEmptyMessage(-1);
		closeWifi();
	}
}