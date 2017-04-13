package com.someone.commons.tools.http.base;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.someone.commons.tools.http.HttpUtil;
import com.someone.commons.tools.http.bean.HttpResponse;
import com.someone.commons.tools.http.callback.ReqCallback;
import com.someone.commons.tools.http.reqType.GetRequestType;
import com.someone.commons.tools.http.reqType.PostRequestType;
import com.someone.commons.tools.http.reqType.RequestType;

import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

public class RequestBuilder {
	private Map<String, String[]> params = new HashMap<String, String[]>();
	private Map<String, String> headers = new HashMap<String, String>();
	private String url;// 请求的url
	private RequestType requestType;
	
	public RequestBuilder(String url) {
		this(url, true);
	}
	public RequestBuilder(String url, boolean isGet) {
		this.url = url;
		if(isGet){
			this.requestType = new GetRequestType();
		}else{
			this.requestType = new PostRequestType();
		}
	}
	
	/**
	 * 设置请求参数
	 * @param params
	 * @return
	 */
	public RequestBuilder setParams(Map<String, String[]> params){
		if(params == null){
			this.params.clear();
		}else{
			this.params = params;
		}
		return this;
	}
	
	/**
	 * 设置单值的key-val
	 * @param params
	 * @return
	 */
	public RequestBuilder setParamsSingle(Map<String, String> params){
		if(params == null){
			this.params.clear();
		}else{
			for(String key : params.keySet()){
				addParam(key, params.get(key));
			}
		}
		return this;
	}
	
	/**
	 * 添加请求参数
	 * 
	 * @param key
	 * @param val
	 * @return
	 */
	public RequestBuilder addParam(String key, String val){
		return addParam(key, new String[]{val});
	}
	public RequestBuilder addParam(String key, String[] vals){
		if(key == null || "".equals(key.trim())){
			return this;
		}
		if(vals == null || vals.length == 0){
			vals = new String[]{""};
		}
		params.put(key, vals);
		return this;
	}
	public RequestBuilder setHeader(String key, String val){
		if(key == null || "".equals(key.trim())){
			return this;
		}
		headers.put(key, val);
		return this;
	}
	
	/**
	 * 异步发送请求
	 */
	public void sendAsync(){
		this.sendAsync(null);
	}
	
	public void sendAsync(ReqCallback callback){
		if(callback == null){
			callback = new ReqCallback();
		}
		Request request = requestType.getRequest(url, params, headers);
		Call call = HttpUtil.getHttpClient().newCall(request);
		call.enqueue(callback);
	}
	
	/**
	 * 同步发送请求
	 * @return
	 */
	public HttpResponse send() throws IOException{
		Request request = requestType.getRequest(url, params, headers);
		Response response = HttpUtil.getHttpClient().newCall(request).execute();
		HttpResponse httpResponse = new HttpResponse(response.code(), response.body().string());
		return httpResponse;
	}
	
}
