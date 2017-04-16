package com.someone.commons.tools.json.base;

//import java.text.SimpleDateFormat;

//import com.fasterxml.jackson.annotation.JsonInclude.Include;
//import com.fasterxml.jackson.databind.DeserializationFeature;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.databind.SerializationFeature;
//import com.someone.commons.tools.utils.DateUtil;
//
//public class Jackson implements IJson {
//	// Jackson 生成 json 的默认行为是生成 null value，可设置此值全局改变默认行为
//	private static boolean defaultGenerateNullValue = true;
//	
//	// generateNullValue 通过设置此值，可临时改变默认生成 null value 的行为
//	protected Boolean generateNullValue = null;
//	
//	protected ObjectMapper objectMapper = new ObjectMapper();
//	
//	public Jackson() {
//		objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
//		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);// 如果json字符串中有的key在bean中不存在，则不抛异常
//	}
//	
//	public static void setDefaultGenerateNullValue(boolean defaultGenerateNullValue) {
//		Jackson.defaultGenerateNullValue = defaultGenerateNullValue;
//	}
//	
//	public Jackson setGenerateNullValue(boolean generateNullValue) {
//		this.generateNullValue = generateNullValue;
//		return this;
//	}
//	
//	/**
//	 * 通过获取 ObjectMapper 进行更个性化设置，满足少数特殊情况
//	 */
//	public ObjectMapper getObjectMapper() {
//		return objectMapper;
//	}
//	
//	public String toStr(Object object) {
//		try {
//			objectMapper.setDateFormat(new SimpleDateFormat(DateUtil.DATE_FORMATE));
//			
//			// 优先使用对象属性 generateNullValue，决定转换 json时是否生成 null value
//			Boolean pnv = generateNullValue != null ? generateNullValue : defaultGenerateNullValue;
//			if (pnv == false) {
//				objectMapper.setSerializationInclusion(Include.NON_NULL);
//			}
//			
//			return objectMapper.writeValueAsString(object);
//		} catch (Exception e) {
//			throw e instanceof RuntimeException ? (RuntimeException)e : new RuntimeException(e);
//		}
//	}
//	
//	public <T> T toBean(String jsonString, Class<T> type) {
//		try {
//			return objectMapper.readValue(jsonString, type);
//		} catch (Exception e) {
//			throw e instanceof RuntimeException ? (RuntimeException)e : new RuntimeException(e);
//		}
//	}
//}
