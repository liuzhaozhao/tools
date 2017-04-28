package rpc.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Order implements Serializable{
	private static final long serialVersionUID = 2857047680943720281L;
	private String orderId;
	private String name;
	private Double price;
	private int status;
	private User user;
	private List<OrderProp> props;
	private Date orderTime;

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public int getStatus() {
		return status;
	}
	
	public void setStatus(int status) {
		this.status = status;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<OrderProp> getProps() {
		return props;
	}

	public void setProps(List<OrderProp> props) {
		this.props = props;
	}

	public Date getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
	}

}
