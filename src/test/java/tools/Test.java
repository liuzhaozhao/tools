package tools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.someone.commons.tools.json.JsonUtil;

public class Test {
	public static void main(String[] args) {
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		Map<String, String> data1 = new HashMap<String, String>();
		data1.put("key", "webUrl");
		data1.put("name", "网站目标URL");
		list.add(data1);
		
		Map<String, String> data2 = new HashMap<String, String>();
		data2.put("key", "androidUrl");
		data2.put("name", "Android目标URL");
		list.add(data2);
		
		Map<String, String> data3 = new HashMap<String, String>();
		data3.put("key", "iphoneUrl");
		data3.put("name", "IOS目标URL");
		list.add(data3);
		
		System.err.println(JsonUtil.toStr(list));
	}
}
