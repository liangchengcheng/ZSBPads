package zsbpj.lccpj.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Author:  梁铖城
 * Email:   1038127753@qq.com
 * Date:   2015年12月3日15:55:04
 * Description: md5加密工具类
 */
public class Md5Utils {
	public static String encode(String text) {
		try {
			MessageDigest digest = MessageDigest.getInstance("md5");
			byte[] result = digest.digest(text.getBytes());
			StringBuilder sb = new StringBuilder();
			for (byte b : result) {
				int number = b & 0xff;
				String hex = Integer.toHexString(number);
				if (hex.length() == 1) {
					sb.append("0" + hex);
				} else {
					sb.append(hex);
				}
			}
			return sb.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return "";
		}
	}

}
