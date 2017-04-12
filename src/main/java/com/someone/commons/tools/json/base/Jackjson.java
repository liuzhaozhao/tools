package com.someone.commons.tools.json.base;

import java.io.IOException;
import java.io.StringWriter;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class Jackjson implements IJson {
	private static ObjectMapper mapper = new ObjectMapper();
	
	static{
		mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);// 如果json字符串中有的key在bean中不存在，则不抛异常
	}

	public String toStr(Object obj) {
		try {
			StringWriter sw = new StringWriter();
			JsonGenerator gen = new JsonFactory().createGenerator(sw);
			mapper.writeValue(gen, obj);
			gen.close();
			return sw.toString();
		} catch (IOException e) {
			e.printStackTrace();
			return "";
		}
	}

	public <T> T toBean(String json, Class<T> cls) {
		try {
			return mapper.readValue(json, cls);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
