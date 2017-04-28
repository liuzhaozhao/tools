package rpc;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import rpc.bean.Order;
import rpc.bean.OrderProp;
import rpc.bean.User;

public class BeanUtil {
	public static Order getOrder() {
		return getOrder("ORD-XXX");
	}
	public static Order getOrder(String orderId) {
		Order o = new Order();
		o.setOrderId(orderId);
		o.setName("订单名称");
		o.setPrice(5.56);
		o.setOrderTime(new Date());
		o.setStatus(5);
		o.setUser(getUser());
		o.setProps(getOrderProps());
		return o;
	}
	
	public static List<Order> getOrders() {
		List<Order> orders = new ArrayList<Order>();
		orders.add(BeanUtil.getOrder("ORD-XXX1"));
		orders.add(BeanUtil.getOrder("ORD-XXX2"));
		orders.add(BeanUtil.getOrder("ORD-XXX3"));
		orders.add(BeanUtil.getOrder("ORD-XXX4"));
		return orders;
	}
	
	public static User getUser() {
		User user = new User();
		user.setAge(10);
		user.setMoney(100.5);
		user.setUserId(1);
		user.setUserName("用户名称");
		return user;
	}
	
	public static OrderProp getOrderProp(String key, String val) {
		return new OrderProp(key, val);
	}
	
	public static List<OrderProp> getOrderProps() {
		List<OrderProp> list = new ArrayList<OrderProp>();
		list.add(getOrderProp("key1", "val1"));
		list.add(getOrderProp("key2", "val2"));
		list.add(getOrderProp("key3", "val3"));
		return list;
	}
}
