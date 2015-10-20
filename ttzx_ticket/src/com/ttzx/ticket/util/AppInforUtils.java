package com.ttzx.ticket.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;

/** 获取app版本号和包名 */
public class AppInforUtils {

	/**
	 * 获取应用版本号
	 * 
	 * @param context
	 *            上下文对象
	 * @return 版本号
	 */
	public static String getVersionName(Context context) {
		String versionName = null;// 版本号
		// 获取PackageManager的实例
		PackageManager packageManager = context.getPackageManager();
		// getPackageName()是当前类的包名，0代表是获取版本信息
		PackageInfo packInfo;
		try {
			packInfo = packageManager.getPackageInfo(context.getPackageName(),
					0);
			versionName = packInfo.versionName;
		} catch (NameNotFoundException e) {
			versionName = null;
		}
		return versionName;
	}

	/**
	 * 获取app包名
	 */

	public static String getPackageName(Context context) {
		try {
			String pkName = context.getPackageName();
			return pkName;
		} catch (Exception e) {
		}
		return null;
	}
}
