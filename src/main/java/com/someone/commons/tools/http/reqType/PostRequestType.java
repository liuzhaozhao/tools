package com.someone.commons.tools.http.reqType;

import java.util.Map;

import okhttp3.FormBody;
import okhttp3.Request;

public class PostRequestType extends RequestType {
	
	@Override
	public Request getRequest(String url, Map<String, String[]> params, Map<String, String> headers) {
		FormBody.Builder formBuilder = new FormBody.Builder();
		for(String key : params.keySet()){
			for(String val : params.get(key)){
				formBuilder.add(key, val);
			}
		}
		return getRequestBuilder(headers).url(url).post(formBuilder.build()).build();
	}

}
