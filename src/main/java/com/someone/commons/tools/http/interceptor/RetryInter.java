package com.someone.commons.tools.http.interceptor;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;

import com.someone.commons.tools.http.base.RequestUtil;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class RetryInter implements Interceptor {
	static Logger log = Logger.getLogger(RetryInter.class);
	private int maxRetryTimes = 0;// 最大尝试次数
	private Set<Integer> successCode = new HashSet<Integer>();
	
	public RetryInter() {
	}
	
	@Override
	public Response intercept(Chain chain) throws IOException {
		Request request = chain.request();
		
		int currentTimes = 0;
		Response response = null;
		String url = RequestUtil.getUrlPath(request.url().toString());
		while(currentTimes <= maxRetryTimes){
			try {
				Thread.sleep(currentTimes*currentTimes*2000);
			} catch (InterruptedException e) {
				log.warn("sleep异常："+e);
			}
			try{
				response = chain.proceed(request);
				if(response.isSuccessful() || successCode.contains(response.code())){
					break;
				}
				log.warn("【"+url+"】请求返回异常：code="+response.code()+"，msg="+response.message());
			}catch(Exception e){
				log.warn("【"+url+"】请求异常："+e, e);
			}
			currentTimes++;
		}
		if(maxRetryTimes > 0){
			StringBuffer logBuffer = new StringBuffer();
			logBuffer.append("【").append(url).append("】请求已完成")
					.append("，请求次数：").append(currentTimes);
			log.info(logBuffer);
		}
		
		return response;
	}
	
	/**
	 * 设置最大尝试次数
	 * @param maxRetryTimes
	 * @return
	 */
	public RetryInter setMaxRetryTimes(int maxRetryTimes) {
		this.maxRetryTimes = maxRetryTimes;
		return this;
	}
	
	/**
	 * 设置不重试的code（其中已经包含了200-299的code，所以不需要重复添加）
	 * @param successCode
	 * @return
	 */
	public RetryInter setSuccessCode(Set<Integer> successCode) {
		if(successCode == null){
			successCode = new HashSet<Integer>();
		}
		this.successCode = successCode;
		return this;
	}
	
	/**
	 * 设置不重试的code（其中已经包含了200-299的code，所以不需要重复添加）
	 * @param successCode
	 * @return
	 */
	public RetryInter addSuccessCode(int code){
		this.successCode.add(code);
		return this;
	}

}
