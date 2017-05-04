package com.someone.commons.tools.utils;

import java.util.List;
import java.util.UUID;

import org.apache.log4j.Logger;

import com.someone.commons.tools.json.JsonUtil;

public class Commons {
	private static Logger log = Logger.getLogger(Commons.class);
	
	/**
	 * 将list的数组转换为二维数组，主要用于JFinal Db.batch()，批量处理
	 * 
	 * @param list
	 * @return
	 */
	public static Object[][] listTo2Array(List<Object[]> list){
		if(list == null || list.size() == 0){
			return null;
		}
		int size = list.get(0).length;
		Object[][] paramsArr = new Object[list.size()][size];
		for(int i=0;i<list.size();i++){
			paramsArr[i] = list.get(i);
		}
		return paramsArr;
	}
	
	/**
	 * 生成ehcache的key值，
	 * 注意：此处是以调用方的类包名+方法名生成缓存key的前缀，所以如果一个方法里调用了两次，此处要自己传递一个args作为区分
	 * 
	 * @param args	参数为基本数据类型或者其包装类
	 * @return
	 */
	public static String generEhcacheKey(Object... args){
		StackTraceElement[] elements = Thread.currentThread().getStackTrace();
		String keyPrefix = null;
		if(elements.length > 2){// 获取调用方的类名及方法名，作为key的前缀，防止key重复
			StackTraceElement element = elements[2];
			keyPrefix = element.getClassName()+"."+ element.getMethodName();
		}else{// 通常情况下，length大于2的
			keyPrefix = UUID.randomUUID().toString().replaceAll("-", "");
			log.warn("生成cacheKey时，调用方参数有误："+JsonUtil.toStr(elements));
		}
		
		String split = ",";
		StringBuffer key = new StringBuffer(keyPrefix + "(");
		for(Object arg : args) {
			String val = "null";
			if(arg != null) {
				val = JsonUtil.toStr(arg);
			}
			key.append(val + split);
        }
		if(args != null && args.length > 0) {
			key.replace(key.length() - split.length(), key.length(), ")");
		} else {
			key.append(")");
		}
		String md5CacheKey = HashUtil.md5(key.toString());
		log.info("生成的cache key="+md5CacheKey+", 原缓存值为："+key.toString());
		return md5CacheKey;
	}
}
