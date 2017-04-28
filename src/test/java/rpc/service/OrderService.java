package rpc.service;

import java.util.Date;
import java.util.List;

import rpc.BeanUtil;
import rpc.api.IOrder;
import rpc.bean.DataBean;
import rpc.bean.Order;

public class OrderService implements IOrder {

	@Override
	public Order getOrder() {
		System.err.println(">>>>>>>>>>>getOrder");
		return BeanUtil.getOrder();
	}

	@Override
	public DataBean<Boolean> deleteOrder(DataBean<List<Order>> orders) {
		if(orders.isSuccessCode()) {
			for(Order o : orders.getData()) {
				System.err.println(o.getOrderId());
				System.err.println(o.getProps().get(0).getKey());
			}
		}
		return DataBean.getSuccessData(false);
	}

	@Override
	public DataBean<Order> getOrderBean() {
		return DataBean.getSuccessData(BeanUtil.getOrder());
	}

	@Override
	public DataBean<List<Order>> getOrders(String orderId, Date orderTime, int[] status) {
		System.err.println("orderId="+orderId);
		System.err.println("orderTime="+orderTime.getTime());
		System.err.println("status[0]="+status[0]);
		return DataBean.getSuccessData(BeanUtil.getOrders());
	}

}
