package com.someone.commons.tools.json.base;

/**
 * 后续如果有需要可添加初始化方法
 * @Description 
 * @author liuzhao
 * @date 2017年3月22日 下午4:38:47
 *
 */
public interface IJson {
	
	
	/**
	 * 对象转json字符串
	 * @param obj
	 */
	public String toStr(Object obj);
	
	/**
	 * 字符串转对象
	 * @param json
	 * @param cls
	 * @return
	 */
	public <T> T toBean(String json, Class<T> cls);
}
