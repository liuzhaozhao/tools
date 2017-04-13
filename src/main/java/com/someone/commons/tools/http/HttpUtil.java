package com.someone.commons.tools.http;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import com.someone.commons.tools.http.base.RequestBuilder;
import com.someone.commons.tools.http.base.TrustAllCerts;
import com.someone.commons.tools.http.interceptor.LogInter;
import com.someone.commons.tools.http.interceptor.RetryInter;

import okhttp3.OkHttpClient;
import okhttp3.OkHttpClient.Builder;

public class HttpUtil {
	private static final long DEFAULT_CONNECT_TIMEOUT_SECONDS = 20;
	private static final long DEFAULT_READ_TIMEOUT_SECONDS = 30;
	
	private static OkHttpClient client;
	
	/**
	 * 
	 * @param connectTimeout	连接超时时间（秒）
	 * @param readTimeout	读取服务器返回数据超时时间（秒）
	 * @param startLog	是否开启请求日志打印
	 * @param retryTimes	错误重试次数，该次数不含第一次请求
	 */
	@SuppressWarnings("deprecation")
	public synchronized static void initHttpClient(Long connectTimeout, Long readTimeout, Boolean startLog, Integer retryTimes, boolean ignoreSafe){
		if(client != null){
			return;
		}
		if(connectTimeout == null){
			connectTimeout = DEFAULT_CONNECT_TIMEOUT_SECONDS;
		}
		if(readTimeout == null){
			readTimeout = DEFAULT_READ_TIMEOUT_SECONDS;
		}
		Builder requestSetting = new OkHttpClient.Builder()
		        .connectTimeout(connectTimeout, TimeUnit.SECONDS)
		        .readTimeout(readTimeout, TimeUnit.SECONDS);
		if(startLog == null || startLog){
			requestSetting.addInterceptor(new LogInter());//添加日志拦截器
		}
		if(retryTimes != null && retryTimes > 0){
			requestSetting.addInterceptor(new RetryInter().setMaxRetryTimes(retryTimes));//添加重试拦截器
		}
		if(ignoreSafe){// 是否忽略https证书（针对https请求如果不忽略证书，是请求不通的）
			requestSetting.sslSocketFactory(TrustAllCerts.createSSLSocketFactory());
			requestSetting.hostnameVerifier(new HostnameVerifier() {
				@Override
				public boolean verify(String hostname, SSLSession session) {
					return true;
				}
			});
		}
		client = requestSetting.build();
		
//		TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
//		trustManagerFactory.init((KeyStore) null);
//		TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();
//		if (trustManagers.length != 1 || !(trustManagers[0] instanceof X509TrustManager)) {
//			throw new IllegalStateException("Unexpected default trust managers:" + Arrays.toString(trustManagers));
//		}
//		X509TrustManager trustManager = (X509TrustManager) trustManagers[0];
//
//		SSLContext sslContext = SSLContext.getInstance("TLS");
//		sslContext.init(null, new TrustManager[] { trustManager }, null);
//		SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
//
//		OkHttpClient client = new OkHttpClient.Builder().sslSocketFactory(sslSocketFactory, trustManager).build();

	}
	
	public static OkHttpClient getHttpClient(){
		if(client == null){
			initHttpClient(null, null, null, null, true);
		}
		return client;
	}
	/**
	 * 同步get请求
	 * @param url
	 * @return
	 */
	public static RequestBuilder get(String url){
		return new RequestBuilder(url);
	}
	
	/**
	 * 同步post请求
	 * @param url
	 * @return
	 */
	public static RequestBuilder post(String url){
		return new RequestBuilder(url, false);
	}
	
	/**
	 * 向url后追加一系列参数
	 * 
	 * @param urlBuffer
	 * @param params
	 * @param excludes	需要从params中排除的key值
	 */
	public static void appendUrlParam(StringBuffer urlBuffer, Map<String, String[]> params, String... excludes){
		appendUrlParam(urlBuffer, params, 2000, excludes);
	}
	/**
	 * 
	 * @param urlBuffer
	 * @param params
	 * @param maxParamsLength 参数最大长度，如果超出这个长度，就不再追加参数，防止因调用方程序有问题，一直请求，导致追加参数过长
	 * @param excludes
	 */
	public static void appendUrlParam(StringBuffer urlBuffer, Map<String, String[]> params, int maxParamsLength, String... excludes){
		if(urlBuffer == null || urlBuffer.length() == 0){
			return;
		}
		if(params == null || params.size() == 0){
			return;
		}
		List<String> excludeList = new ArrayList<String>();
		if(excludes != null){
			excludeList.addAll(Arrays.asList(excludes));
		}
		int startLength = urlBuffer.length();
		for(String key : params.keySet()){
			if(excludeList.contains(key)){
				continue;
			}
			if(params.get(key) == null || params.get(key).length==0){
				if((urlBuffer.length() - startLength + key.length() + 1) > maxParamsLength){// 判断追加参数后，追加的参数长度是否大于设置的最大长度，如果大于，则停止追加
					return;
				}
				appendUrlParam(urlBuffer, key, "");
			}else{
				for(String v : params.get(key)){
					if((urlBuffer.length() - startLength + key.length() + v.length() + 1) > maxParamsLength){// 判断追加参数后，追加的参数长度是否大于设置的最大长度，如果大于，则停止追加
						return;
					}
					appendUrlParam(urlBuffer, key, v);
				}
			}
		}
	}
	/**
	 * 向url后追加参数
	 * @param url
	 * @param key
	 * @param value
	 * @return
	 */
	public static void appendUrlParam(StringBuffer urlBuffer, String key, String value){
		if(urlBuffer == null || urlBuffer.length() == 0){
			return;
		}
		if (urlBuffer.indexOf("?") == -1) {
			urlBuffer.append("?");
		} else if(!"&".endsWith(urlBuffer.substring(urlBuffer.length()-1))){
			urlBuffer.append("&");
		}
		try {value = URLEncoder.encode(value, "UTF-8");} catch (UnsupportedEncodingException e) {throw new RuntimeException(e);}
		urlBuffer.append(key + "=" + value);
	}
}

