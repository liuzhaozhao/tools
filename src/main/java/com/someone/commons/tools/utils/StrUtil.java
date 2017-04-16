package com.someone.commons.tools.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StrUtil {
	
	public static void main(String[] args) {
		System.err.println(join(Arrays.asList(new String[]{"sadf","wer32"," cvvber"}), ","));
	}
	
	/**
	 * 将list转换为sql的in查询字符串
	 * @param list
	 * @return '1','2',''		没有数据时返回''
	 */
	public static String toSqlIn(List<String> list){
		if(list == null || list.size() == 0){
			return "''";
		}
		StringBuffer sqlIn = new StringBuffer("'");
		Iterator<String> ite = list.iterator();
		while(ite.hasNext()){
			sqlIn.append(ite.next()+"','");
		}
		sqlIn.append("'");
		return sqlIn.toString();
	}
	
	/**
	 * sql like时防止注入
	 * @param srcStr
	 * @return
	 */
	public static String queryLike(String srcStr) {
		//适用于sqlserver
//		result = StringUtils.replace(result, "[", "[[]");
//		result = StringUtils.replace(result, "_", "[_]");
//		result = StringUtils.replace(result, "%", "[%]");
//		result = StringUtils.replace(result, "^", "[^]");
		//适用于mysql
		srcStr = replace(srcStr, "\\", "\\\\");
		srcStr = replace(srcStr, "'", "\\'");
		srcStr = replace(srcStr, "_", "\\_");
		srcStr = replace(srcStr, "%", "\\%");
		
		return "%" + srcStr + "%";
	}
	
	/**
	 * 替换字符串中的指定字符串，与String.replace()不一样
	 * @param text
	 * @param repl
	 * @param with
	 * @return
	 */
	public static String replace(String text, String repl, String with) {
        if (isBlank(text) || isBlank(repl) || with == null) {
            return text;
        }
        int start = 0;
        int end = text.indexOf(repl, start);
        if (end == -1) {
            return text;
        }
        int replLength = repl.length();
        int increase = with.length() - replLength;
        int max = -1;
        increase = (increase < 0 ? 0 : increase);
        increase *= (max < 0 ? 16 : (max > 64 ? 64 : max));
        StringBuffer buf = new StringBuffer(text.length() + increase);
        while (end != -1) {
            buf.append(text.substring(start, end)).append(with);
            start = end + replLength;
            if (--max == 0) {
                break;
            }
            end = text.indexOf(repl, start);
        }
        buf.append(text.substring(start));
        return buf.toString();
    }
	
	/**
	 * 校验手机号码合法性
	 */
	public static boolean mobileVerify(String phone){
		return match(phone, "^1[3-9]\\\\d{9}$");
	}
	
	/**
	 * 校验QQ是否合法
	 * @param qq
	 * @return
	 */
	public static boolean qqVerify(String qq){
		return match(qq, "^[1-9][0-9]{4,11}");
	}
	
	/**
	 * 校验字符串是否合法
	 * @param text
	 * @param regExp
	 * @return
	 */
	public static boolean match(String text, String regExp){
		if(isBlank(text)) {
			return false;
		}
		Pattern p = Pattern.compile(regExp);
		Matcher m = p.matcher(text);
		return m.find();
	}
	
	/**
	 * 合并数组
	 * @param array
	 * @param separator
	 * @return
	 */
	public static String join(Object[] array, String separator) {
		if (array == null || array.length == 0) {
            return "";
        }
        separator = toString(separator);
        StringBuffer buf = new StringBuffer();
        buf.append(array[0]);
        for (int i = 1; i < array.length; i++) {
            buf.append(separator).append(array[i]);
        }
        return buf.toString();
	}
	/**
	 * 合并集合
	 * @param array
	 * @param separator
	 * @return
	 */
	public static String join(Collection<?> collection, String separator) {
		if (collection == null || collection.size() == 0) {
            return "";
        }
		separator = toString(separator);
		Iterator<?> iterator = collection.iterator();
		StringBuffer buf = new StringBuffer();
		buf.append(iterator.next());
        while (iterator.hasNext()) {
            buf.append(separator).append(iterator.next());
        }
        return buf.toString();
	}
	
	/**
	 * 判断字符串是否为null，或者空字符串
	 * @param str
	 * @return
	 */
	public static boolean isBlank(String str) {
		return str == null || "".equals(str.trim());
	}
	
	/**
	 * 对象转字符串，针对null的转换为空字符串
	 * @param obj
	 * @return
	 */
	public static String toString(Object obj) {
		return toString(obj, "");
	}
	public static String toString(Object obj, String nullVal) {
		return obj == null ? nullVal : obj.toString();
	}
	
	/**
	 * 处理特殊字符，如表情字符，无法保存到数据库，需要先替换特殊字符
	 * 
	 * 将特殊字符替换为指定字符
	 * 
	 * 如：aa的😁😁😁😁123   替换后为：aa的****123
	 * 
	 * @param text	需要替换的字符串
	 * @param replace	替换成哪个字符
	 * @return
	 */
	public static String replaceChar(String text, char replace){
		if(isBlank(text)){
			return text;
		}
		byte[] conbyte = text.getBytes();
		byte[] newByte = new byte[conbyte.length];
		int length = 0;
        for (int i = 0; i < conbyte.length; i++) {
        	newByte[length] = conbyte[i];
        	if((conbyte[i] & 0xF8) == 0xF0){
        		newByte[length] = (replace+"").getBytes()[0];
        		i += 3;
        	}
        	length ++;
        }
        
        text = new String(newByte).trim();
        return text;
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
		urlBuffer.append(key + "=" + value);
	}
	
	public static void appendUrlParams(StringBuffer urlBuffer, Map<String, String[]> params, String... excludes){
		appendUrlParams(urlBuffer, params, 600, excludes);
	}
	/**
	 * 
	 * @param urlBuffer
	 * @param params
	 * @param maxParamsLength 参数最大长度，如果超出这个长度，就不再追加参数，防止因调用方程序有问题，一直请求，导致追加参数过长
	 * @param excludes
	 */
	public static void appendUrlParams(StringBuffer urlBuffer, Map<String, String[]> params, int maxParamsLength, String... excludes){
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
}
