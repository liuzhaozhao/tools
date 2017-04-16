package com.someone.commons.tools.http.callback;

import java.io.IOException;

import org.apache.log4j.Logger;

import com.someone.commons.tools.http.base.RequestUtil;
import com.someone.commons.tools.http.bean.HttpRequest;
import com.someone.commons.tools.http.bean.HttpResponse;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class ReqCallback implements Callback {
	Logger log = Logger.getLogger(this.getClass());
	protected Call call;
	private HttpRequest httpRequest;

	@Override
	public final void onFailure(Call call, IOException e) {
		initData(call);
		
		StringBuffer logError = new StringBuffer("请求异常：");
		logError.append(RequestUtil.getRequestInfo(call.request()));
		logError.append("，异常信息："+e);
		log.warn(logError.toString());
		
		this.onFailure(e);
	}

	@Override
	public final void onResponse(Call call, Response response) throws IOException {
		initData(call);
		
		HttpResponse httpResponse = new HttpResponse(response.code(), response.body().string());
				
		StringBuffer logInfo = new StringBuffer("请求完成：");
		logInfo.append(RequestUtil.getRequestInfo(call.request()));
		logInfo.append("，返回code="+httpResponse.getCode());
		logInfo.append("，返回值="+httpResponse.getBody());
		log.info(logInfo.toString());
		
		this.onResponse(httpResponse);
	}
	
	private void initData(Call call){
		this.call = call;
		this.httpRequest = new HttpRequest(RequestUtil.getUrlPath(call.request().url().toString()), RequestUtil.getParams(call.request()));
	}
	
	/**
	 * 获取request对象，其中包含请求的url及参数
	 * @return
	 */
	protected HttpRequest getRequest() {
		return httpRequest;
	}
	
	/**
	 * 如需处理异常，子类继承
	 * @param e
	 */
	public void onFailure(IOException e){}
	
	/**
	 * 如需处理返回值，子类继承
	 * @param response
	 */
	public void onResponse(HttpResponse response){}
}
