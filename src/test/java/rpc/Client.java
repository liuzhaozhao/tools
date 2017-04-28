package rpc;

import java.io.IOException;
import java.util.Date;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import rpc.api.IOrder;
import rpc.api.IUser;
import rpc.bean.DataBean;

public class Client {
	static IOrder orderService;
	static IUser userService;
	
	public static void main(String[] args) throws IOException {
		try{
			initMotanClient();
//			initJupiterClient();
			
//			System.err.println(orderService.getOrder().getOrderId());
//			System.err.println(orderService.getOrderBean().getData().getProps().get(0).getKey());
//			System.err.println(orderService.getOrders("ORD-XXX", new Date(), new int[]{1,5,10}).getData().get(0).getName());
//			System.err.println(orderService.deleteOrder(DataBean.getSuccessData(BeanUtil.getOrders())).getData());
//			
//        	System.err.println(userService.getUser(0).getData());
//        	System.err.println(userService.getUser(1).getData().getUserName());
		}catch(Exception e) {
			e.printStackTrace();
		}
		while(true) {
			byte[] buffer = new byte[1024];
			System.in.read(buffer);
			System.err.println(">>>>>>>>>>read");
			try{
				System.err.println(orderService.getOrder().getOrderId());
			}catch(Exception e){
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
//		System.exit(0);
	}
	// jupiter 获取客户端实例
//	static void initJupiterClient() {
//		int processors = Runtime.getRuntime().availableProcessors();// CPU核心数量，用于初始化数据传输渠道
////		SystemPropertyUtil.setProperty("jupiter.executor.factory.consumer.core.workers", String.valueOf(processors));
////		SystemPropertyUtil.setProperty("jupiter.tracing.needed", "false");// Tracing是否开启(链路跟踪), 默认开启
////		SystemPropertyUtil.setProperty("jupiter.use.non_blocking_hash", "true");
//		JClient client = new DefaultClient().withConnector(new JNettyTcpConnector(processors + 1));
//		client.connector().config().setOption(JOption.WRITE_BUFFER_HIGH_WATER_MARK, 512 * 1024);
//        client.connector().config().setOption(JOption.WRITE_BUFFER_LOW_WATER_MARK, 256 * 1024);
//		UnresolvedAddress[] addresses = new UnresolvedAddress[processors];
//        for (int i = 0; i < processors; i++) {
//            addresses[i] = new UnresolvedAddress("127.0.0.1", 18090);
//            client.connector().connect(addresses[i]);// 向服务器建立多个连接
//        }
//        // 连接RegistryServer
////        client.connectToRegistryServer("127.0.0.1:20001");
//        // 自动管理可用连接
////        JConnector.ConnectionWatcher watcherIOrder = client.watchConnections(IOrder.class);
//        // 等待连接可用
////        if (!watcherIOrder.waitForAvailable(3000)) {
////            throw new ConnectFailedException();
////        }
//        
//        orderService = ProxyFactory.factory(IOrder.class)
//        		.group("testGroup").providerName("testProvider").version("1.0.0").client(client)
//                .serializerType(SerializerType.HESSIAN).loadBalancerType(LoadBalancerType.ROUND_ROBIN)
//                .addProviderAddress(addresses).newProxyInstance();
////        // 自动管理可用连接
////        JConnector.ConnectionWatcher watcherIUser = client.watchConnections(IUser.class);
////        // 等待连接可用
////        if (!watcherIUser.waitForAvailable(3000)) {
////            throw new ConnectFailedException();
////        }
////        userService = ProxyFactory.factory(IUser.class)
////                .version("1.0.0").client(client).newProxyInstance();
//	}
	// motan 获取客户端实例
	static void initMotanClient() {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:motan_client.xml");
		orderService = (IOrder) ctx.getBean("orderService");
		userService = (IUser) ctx.getBean("userService");
	}
}
