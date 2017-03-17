package com.someone.commons.tools.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class NumUtil {
	
	public static void main(String[] args) {
		System.err.println(round(1.2349, 2));
	}
	
	/**
	 * 保留指定位数的小数，四舍五入
	 * @param d
	 * @return
	 */
	public static double round(double d){
		return round(d, 2);
	}
	public static double round(double d, int pointNum){
		BigDecimal bg = new BigDecimal(d).setScale(pointNum, RoundingMode.HALF_UP);
        return bg.doubleValue();
	}
}
