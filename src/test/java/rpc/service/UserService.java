package rpc.service;


import rpc.BeanUtil;
import rpc.api.IUser;
import rpc.bean.DataBean;
import rpc.bean.User;

public class UserService implements IUser {

	@Override
	public DataBean<User> getUser(int userId) {
		System.err.println("userId="+userId);
		if(userId == 1) {
			return DataBean.getSuccessData(BeanUtil.getUser());
		} else {
			return DataBean.getSuccessData(null);
		}
	}

}
