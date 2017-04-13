package com.someone.commons.tools.http.reqType;

import java.util.Map;

import okhttp3.Request;
import okhttp3.Request.Builder;

public abstract class RequestType {
	/**
	 * 生成对应请求类型的Request
	 * 
	 * @param url
	 * @param params
	 * @return
	 */
	public abstract Request getRequest(String url, Map<String, String[]> params, Map<String, String> headers);
	
	/**
	 * 获取Builder实例
	 * @param headers
	 * @return
	 */
	Builder getRequestBuilder(Map<String, String> headers){
		Builder builder = new Request.Builder();
		if(headers != null){
			for(String key : headers.keySet()){
				builder.header(key, headers.get(key));
			}
		}
		return builder;
	}
}
