package com.someone.commons.tools.http.reqType;

import java.util.Map;

import com.someone.commons.tools.http.HttpUtil;

import okhttp3.Request;

public class GetRequestType extends RequestType {

	@Override
	public Request getRequest(String url, Map<String, String[]> params, Map<String, String> headers) {
		StringBuffer thisUrl = new StringBuffer(url);
		HttpUtil.appendUrlParam(thisUrl, params);
		return getRequestBuilder(headers).url(thisUrl.toString()).build();
	}

}
