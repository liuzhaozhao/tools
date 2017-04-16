package com.someone.commons.tools.http.bean;

public class HttpResponse {
	private int code;
	private String body;
	
	public HttpResponse(int code, String body) {
		this.code = code;
		this.body = body;
	}
	
	public String getBody() {
		return body;
	}
	
	public int getCode() {
		return code;
	}
}
