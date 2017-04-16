package com.someone.commons.tools.http.interceptor;

import java.io.EOFException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import java.util.Map;

import org.apache.log4j.Logger;

import com.someone.commons.tools.http.base.RequestUtil;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.internal.http.HttpHeaders;
import okio.Buffer;
import okio.BufferedSource;

public class LogInter implements Interceptor {
	static Logger log = Logger.getLogger(LogInter.class);
	private static final Charset UTF8 = Charset.forName("UTF-8");
	
	@Override
	public Response intercept(Chain chain) throws IOException {
		long startTime = System.currentTimeMillis();
		Request request = chain.request();
		Map<String, String[]> params = RequestUtil.getParams(request);
		String url = RequestUtil.getUrlPath(request.url().toString());
		StringBuffer info = new StringBuffer();
		info.append("【").append(url).append("】");
		String result = "";
		int code = -1;
		try{
			Response response = chain.proceed(request);
			if(response == null){
				return null;
			}
			code = response.code();
			ResponseBody responseBody = response.body();
			long contentLength = responseBody.contentLength();
			
//			if (!HttpEngine.hasBody(response)) {// okhttp3.3.0一下使用
			if (!HttpHeaders.hasBody(response)) {// okhttp3.4.1这里变成了 !HttpHeader.hasBody(response)
				// END HTTP
			} else if (bodyEncoded(response.headers())) {
				// HTTP (encoded body omitted)
			} else {
				BufferedSource source = responseBody.source();
				source.request(Long.MAX_VALUE);
				Buffer buffer = source.buffer();
				
				Charset charset = UTF8;
				MediaType contentType = responseBody.contentType();
				if (contentType != null) {
					try {
						charset = contentType.charset(UTF8);
					} catch (UnsupportedCharsetException e) {// Couldn't decode the response body; charset is likely malformed.
						return response;
					}
				}
				if (!isPlaintext(buffer)) {
					return response;
				}
				if (contentLength != 0) {
					result = buffer.clone().readString(charset);
				}
			}
			return response;
		}finally {
			info.append("，请求耗时：").append(System.currentTimeMillis() - startTime).append("毫秒")
				.append("，参数：").append(RequestUtil.getUrlParams(params))
				.append("，返回值信息("+code+")：").append(result);
			log.info(info.toString());
		}
	}
	static boolean isPlaintext(Buffer buffer) throws EOFException {
        try {
            Buffer prefix = new Buffer();
            long byteCount = buffer.size() < 64 ? buffer.size() : 64;
            buffer.copyTo(prefix, 0, byteCount);
            for (int i = 0; i < 16; i++) {
                if (prefix.exhausted()) {
                    break;
                }
                int codePoint = prefix.readUtf8CodePoint();
                if (Character.isISOControl(codePoint) && !Character.isWhitespace(codePoint)) {
                    return false;
                }
            }
            return true;
        } catch (EOFException e) {
            return false;
        }
    }

    private boolean bodyEncoded(Headers headers) {
        String contentEncoding = headers.get("Content-Encoding");
        return contentEncoding != null && !contentEncoding.equalsIgnoreCase("identity");
    }

}
