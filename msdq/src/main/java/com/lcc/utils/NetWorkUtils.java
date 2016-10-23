/**
 * 
 */
package com.lcc.utils;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;
import android.widget.Toast;
import java.util.List;

import zsbpj.lccpj.frame.FrameManager;


/**
 * Author:  梁铖城
 * Email:   1038127753@qq.com
 * Date:    2015年11月14日23:49:57
 * Description: 网络的工具类
 */

public class NetWorkUtils {

	// 手机网络类型
	public static final int NETTYPE_WIFI = 0x01;
	public static final int NETTYPE_CMWAP = 0x02;
	public static final int NETTYPE_CMNET = 0x03;

	/**
	 * 判断是否有网络
	 * @param context 上下文
	 * @return 是否
	 */
	public static boolean isNetworkConnected(Context context) {
		if (context != null) {
			ConnectivityManager mConnectivityManager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
			if (mNetworkInfo != null) {
				return mNetworkInfo.isAvailable();
			}
		}
		return false;
	}

	/**
	 * 判断没有网络的话给提示
	 * @param context 上下文对象
	 *
	 */
	public static void netWorkStateTips(Context context) {
		if (!isNetworkConnected(context))
			Toast.makeText(context, "网络连接失败！", Toast.LENGTH_LONG).show();
	}

	/**
	 * 判断是否是WiFi连接
	 * @param context 上下文对象
	 * @return 是否
	 */
	public static boolean isWifiConnected(Context context) {
		if (context != null) {
			ConnectivityManager mConnectivityManager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo mWiFiNetworkInfo = mConnectivityManager
					.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
			if (mWiFiNetworkInfo != null) {
				return mWiFiNetworkInfo.isAvailable();
			}
		}
		return false;
	}

	/**
	 * 判断GPS是否打开
	 * @param context  上下文对象
	 * @return 是否
	 */
	public static boolean isGpsEnabled(Context context) {
		LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
		List<String> accessibleProviders = locationManager.getProviders(true);
		return accessibleProviders != null && accessibleProviders.size() > 0;

	}

	/**
	 * 打开设置界面
	 * @param  activity activity
	 */
	public static void openSetting(Activity activity){
		Intent intent=new Intent("/");
		ComponentName cm=new ComponentName("com.android.settings","com.android.settings.WirelessSettings");
		intent.setComponent(cm);
		intent.setAction("android.intent.action.VIEW");
		activity.startActivityForResult(intent, 0);
	}

	public static int getNetworkType() {
		int netType = 0;
		ConnectivityManager connectivityManager = (ConnectivityManager) FrameManager.getAppContext().getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
		if (networkInfo == null) {
			return netType;
		}
		int nType = networkInfo.getType();
		if (nType == ConnectivityManager.TYPE_MOBILE) {
			String extraInfo = networkInfo.getExtraInfo();
			if (!TextUtils.isEmpty(extraInfo)) {
				if (extraInfo.toLowerCase().equals("cmnet")) {
					netType = NETTYPE_CMNET;
				} else {
					netType = NETTYPE_CMWAP;
				}
			}
		} else if (nType == ConnectivityManager.TYPE_WIFI) {
			netType = NETTYPE_WIFI;
		}
		return netType;
	}

	public static boolean hasInternet() {
		boolean flag;
		if (((ConnectivityManager) FrameManager.getAppContext().getSystemService(
				"connectivity")).getActiveNetworkInfo() != null)
			flag = true;
		else
			flag = false;
		return flag;
	}


}
