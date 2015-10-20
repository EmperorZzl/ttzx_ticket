package com.ttzx.ticket.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.TrafficStats;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;

public class OtherUtils {

	/**
	 * dip 转 px
	 * 
	 * @param activity
	 * @param pxValue
	 * @return
	 */
	public static int dip2px(Activity activity, float dpValue) {
		final float scale = activity.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	/**
	 * px 转 dip
	 * 
	 * @param activity
	 * @param pxValue
	 * @return
	 */
	public static int px2dip(Activity activity, float pxValue) {
		final float scale = activity.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

}
