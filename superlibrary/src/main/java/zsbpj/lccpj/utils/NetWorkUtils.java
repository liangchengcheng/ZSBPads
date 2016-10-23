/**
 * 
 */
package zsbpj.lccpj.utils;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.widget.Toast;

import java.util.List;

/**
 * Author:  梁铖城
 * Email:   1038127753@qq.com
 * Date:    2015年11月14日23:49:57
 * Description: 网络的工具类
 */
public class NetWorkUtils {

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

	/**
	 * 获取当前网络类型
	 * @return -1 没有网络
	 * 0 移动网络;
	 * 1 wifi;
	 * 2 其他；
	 */
	public static int getNetWorkType(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
		if (activeNetwork == null)
			return -1;
		if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
			return 1;
		} else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
			return 0;
		} else {
			return 2;
		}
	}

	public static boolean isMobileType(Context context) {
		return 0 == getNetWorkType(context);
	}

	public static boolean isConnected(Context context) {
		return -1 != getNetWorkType(context);
	}

	public static void openWifi(Activity context) {
		context.startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
	}
}
