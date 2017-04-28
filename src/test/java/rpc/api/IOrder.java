package rpc.api;

import java.util.Date;
import java.util.List;

import rpc.bean.DataBean;
import rpc.bean.Order;

public interface IOrder {
	
	public Order getOrder();
	
	public DataBean<Boolean> deleteOrder(DataBean<List<Order>> orders);
	
	public DataBean<Order> getOrderBean();
	
	public DataBean<List<Order>> getOrders(String orderId, Date orderTime, int[] status);
	
}
