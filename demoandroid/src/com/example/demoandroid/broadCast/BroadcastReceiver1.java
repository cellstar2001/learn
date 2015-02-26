package com.example.demoandroid.broadCast;

import com.example.demoandroid.MainActivity;
import com.example.demoandroid.R;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BroadcastReceiver1 extends BroadcastReceiver {
	private Context context;
	public static final int NOTIFICATION_ID = 10001;

	public void onReceive(Context context, Intent intent) {
		this.context = context;
		showNotification();
	}

	private void showNotification() {
		Notification notification = new Notification(R.drawable.ic_launcher,
				"来电话啦...", System.currentTimeMillis());
		PendingIntent contentIntent = PendingIntent.getActivity(context, 0,
				new Intent(context, MainActivity.class), 0);
		notification.setLatestEventInfo(context, "来电话啦...嘿嘿", "赶紧接电话，否则误大事了",
				contentIntent);

		NotificationManager notificationManager = (NotificationManager) context
				.getSystemService(android.content.Context.NOTIFICATION_SERVICE);
		notificationManager.notify(NOTIFICATION_ID, notification);
	}
}
