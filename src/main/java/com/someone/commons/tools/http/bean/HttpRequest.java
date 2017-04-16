package com.someone.commons.tools.http.bean;

import java.util.Map;

public class HttpRequest {
	private String url;
	private Map<String, String[]> params;
	
	public HttpRequest(String url, Map<String, String[]> params) {
		this.url = url;
		this.params = params;
	}
	
	public String getUrl() {
		return url;
	}
	
	public Map<String, String[]> getParams() {
		return params;
	}
}
