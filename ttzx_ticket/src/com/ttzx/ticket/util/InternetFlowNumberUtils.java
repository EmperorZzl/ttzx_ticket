package com.ttzx.ticket.util;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.TrafficStats;

/**
 * 获取app获取的流量总量
 * 
 * @author Administrator
 *
 */
public class InternetFlowNumberUtils {
	public static long uidRxBytes = 0;// app 接受的总流量

	/** 获取总的接受字节数，包含Mobile和WiFi等 */
	public static long getUidRxBytes(Context context) {
		PackageManager pm = context.getPackageManager();
		ApplicationInfo ai = null;
		try {
			ai = pm.getApplicationInfo("com.study.ttzx",
					PackageManager.GET_ACTIVITIES);
		} catch (NameNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return TrafficStats.getUidRxBytes(ai.uid) == TrafficStats.UNSUPPORTED ? 0
				: (TrafficStats.getTotalRxBytes() / 1024);
	}

	/**
	 * 判断当前网络连接状态
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isNetworkConnected(Context context) {
		NetworkInfo networkInfo = ((ConnectivityManager) context
				.getApplicationContext().getSystemService("connectivity"))
				.getActiveNetworkInfo();
		if (networkInfo != null) {
			return networkInfo.isConnectedOrConnecting();
		}
		return false;
	}
}
