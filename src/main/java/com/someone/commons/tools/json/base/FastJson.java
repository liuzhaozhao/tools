package com.someone.commons.tools.json.base;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.someone.commons.tools.utils.DateUtil;

public class FastJson implements IJson {
	public String toStr(Object object) {
		// 优先使用对象级的属性 datePattern, 然后才是全局性的 defaultDatePattern
		return JSON.toJSONStringWithDateFormat(object, DateUtil.DATE_FORMATE, SerializerFeature.WriteDateUseDateFormat);	// return JSON.toJSONString(object, SerializerFeature.WriteDateUseDateFormat);
	}
	
	public <T> T toBean(String jsonString, Class<T> type) {
		return JSON.parseObject(jsonString, type);
	}
}
