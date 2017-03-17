package com.someone.commons.tools.utils;

public class StrUtil {
	/**
	 * 判断字符串是否为null，或者空字符串
	 * @param str
	 * @return
	 */
	public static boolean isBlank(String str) {
		return str == null || "".equals(str.trim());
	}
}
