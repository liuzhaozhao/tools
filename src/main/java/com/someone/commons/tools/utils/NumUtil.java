package com.someone.commons.tools.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class NumUtil {
	
	public static void main(String[] args) {
//		System.err.println(round(1.2359, 2));
//		System.err.println(getRandomNum(10, 15, new Integer[]{15,11,12,13,14,10}));
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
	
	/**
	  * 随机抽取某个数字区间的一个数字
	  * @param startNum	包含
	  * @param endNum	包含
	  * @param excludeNum	要排除的数字
	  * @return
	  * 	如果因传的数据有问题导致，无法获取数据，则返回-1
	  * 
	  * 调用方式：getRandomNum(10, 100, new Integer[]{15,50,75})
	  */
	 public static Integer getRandomNum(int startNum, int endNum, Integer[] excludeNum){
		 if(endNum < startNum){
			 return null;
		 }
		 Set<Integer> excludeFilterNum = new HashSet<Integer>();
		 if(excludeNum != null) {// 过滤排除数据，去除不在startNum到endNum之间的数字
			 for(int num: excludeNum){
				 if(num >= startNum && num <= endNum){
					 excludeFilterNum.add(num);
				 }
			 }
		 }
		 
		 if((endNum - startNum + 1) <= excludeFilterNum.size()){// 排除了所有数据，直接返回-1
			 return null;
		 }
		 if(1.0*excludeFilterNum.size()/(endNum - startNum + 1) > 0.8){// 如果排除的数量大于80%，则使用该方式生成
			 List<Integer> newNumList = new ArrayList<Integer>();
			 for(int i=startNum; i<=endNum; i++){
				 newNumList.add(i);
			 }
			 return newNumList.get((int)(Math.random()*newNumList.size()));
		 }else{
			 Date startTime = new Date();
			 while(true){
				 int num = startNum + (int)(Math.random()*(endNum - startNum + 1));
				 if(!excludeFilterNum.contains(num)){
					 return num;
				 }
				 if(new Date().getTime() - startTime.getTime() > 5*1000){// 无法生成数据
					 return null;
				 }
			 }
		 }
	 }
}
