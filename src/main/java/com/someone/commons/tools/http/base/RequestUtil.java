package com.someone.commons.tools.http.base;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;

public class RequestUtil {
	
	/**
	 * 根据request获取其中的url及参数字符串
	 * 
	 * 用于日志展示
	 * @param request
	 * @return
	 */
	public static String getRequestInfo(Request request){
		StringBuffer info = new StringBuffer("url=");
		info.append(request.url().toString());
		RequestBody requestBody = request.body();
		if(requestBody instanceof FormBody){
			FormBody formBody = (FormBody)requestBody;
			if(formBody.size() > 0){
				info.append("，参数：");
			}
			for(int i=0;i<formBody.size();i++){
				info.append(formBody.name(i)).append('=').append(formBody.value(i)).append("&");
			}
			if(formBody.size() > 0){
				info.deleteCharAt(info.length() - 1);
			}
		}
		return info.toString();
	}
	
//	/**
//	 * 返回request中的url
//	 * 注意：针对get请求url后会带有参数
//	 * 
//	 * @param request
//	 * @return
//	 */
//	public static String getRequestUrl(Request request){
//		return request.url().toString();
//	}
	
	/**
	 * 获取request中的参数
	 * 注意：针对get请求不包含参数值
	 * @param request
	 * @return
	 */
	public static Map<String, String[]> getParams(Request request){
		TreeMap<String, String[]> params = new TreeMap<String, String[]>();
		RequestBody requestBody = request.body();
		if(requestBody instanceof FormBody){// post请求
			FormBody formBody = (FormBody)requestBody;
			for(int i=0;i<formBody.size();i++){
				String key = formBody.name(i);
				String val = formBody.value(i);
				setParamVal(params, key, val);
			}
		}
		// get 请求
		Set<String> names = request.url().queryParameterNames();
		for(String name : names){
			params.put(name, request.url().queryParameterValues(name).toArray(new String[]{}));
		}
		return params;
	}
	
	/**
	 * 添加参数
	 * @param params
	 * @param key
	 * @param val
	 */
	public static void setParamVal(Map<String, String[]> params, String key, String val){
		if(!params.containsKey(key)){
			params.put(key, new String[]{val});
		}else{
			List<String> vals = new ArrayList<String>();
			if(params.get(key) != null){
				vals.addAll(Arrays.asList(params.get(key)));
			}
			vals.add(val);
			params.put(key, vals.toArray(new String[]{}));
		}
	}
	
	/**
	 * 
	 * @param url	带参数的url或者带
	 * @return	返回不带参数的url
	 */
	public static String getUrlPath(String url){
		if(url.indexOf("?") != -1){
			return url.substring(0, url.indexOf("?"));
		}
		return url;
	}
	
	
	/**
	 * 将Map转换为字符串，方便打印日志
	 * @param map
	 * @return
	 */
	public static String getUrlParams(Map<String, String[]> map){
		StringBuffer info = new StringBuffer("");
		for(String key : map.keySet()){
			for(String val : map.get(key)){
				info.append(key).append('=').append(val).append("&");
			}
		}
		if(info.length() > 0){
			info.deleteCharAt(info.length() - 1);
		}
		return info.toString();
	}
}
