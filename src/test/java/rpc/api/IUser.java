package rpc.api;


import rpc.bean.DataBean;
import rpc.bean.User;

public interface IUser {
	
	public DataBean<User> getUser(int userId);
	
}
