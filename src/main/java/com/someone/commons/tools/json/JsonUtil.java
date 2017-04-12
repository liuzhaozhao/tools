package com.someone.commons.tools.json;

import com.someone.commons.tools.json.base.IJson;
import com.someone.commons.tools.json.base.Jackjson;

public class JsonUtil {
	private static IJson instance = new Jackjson();
	
	public static void setInstance(IJson instance){
		if(instance == null){
			return;
		}
		JsonUtil.instance = instance;
	}
	
	/**
	 * 对象转字符串
	 * @param obj
	 * @return
	 */
	public static String toStr(Object obj){
		return instance.toStr(obj);
	}
	
	/**
	 * 字符串转对象
	 * @param json
	 * @param cls
	 * @return
	 */
	public static <T> T toBean(String json, Class<T> cls) {
		return instance.toBean(json, cls);
	}
}
