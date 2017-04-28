package rpc.bean;

import java.io.Serializable;

public class OrderProp implements Serializable {
	private static final long serialVersionUID = -126278239362012783L;
	
	private String key;
	private String val;
	
	public OrderProp(String key, String val) {
		this.key = key;
		this.val = val;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getVal() {
		return val;
	}

	public void setVal(String val) {
		this.val = val;
	}

	
}
