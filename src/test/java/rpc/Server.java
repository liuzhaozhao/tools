package rpc;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.weibo.api.motan.common.MotanConstants;
import com.weibo.api.motan.util.MotanSwitcherUtil;

public class Server {
	public static void main(String[] args) {
		try {
			startMotanServer();
//			startJupiterServer();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
		MotanSwitcherUtil.setSwitcherValue(MotanConstants.REGISTRY_HEARTBEAT_SWITCHER, true);
        System.err.println("server start...");
	}
	// jupiter 启动服务
//	static void startJupiterServer() throws InterruptedException {
////		final int processors = Runtime.getRuntime().availableProcessors();
////        SystemPropertyUtil.setProperty("jupiter.executor.factory.provider.core.workers", String.valueOf(processors));
////        SystemPropertyUtil.setProperty("jupiter.metric.needed", "false");// 是否启用provider的指标度量, 默认启用
////        SystemPropertyUtil.setProperty("jupiter.metric.csv.reporter", "false");
//        SystemPropertyUtil.setProperty("jupiter.metric.report.period", "1");
////        SystemPropertyUtil.setProperty("jupiter.executor.factory.provider.queue.capacity", "65536");
//        JServer server = new DefaultServer().withAcceptor(new JNettyTcpAcceptor(18090));
//        server.acceptor().configGroup().child().setOption(JOption.WRITE_BUFFER_HIGH_WATER_MARK, 256 * 1024);
//        server.acceptor().configGroup().child().setOption(JOption.WRITE_BUFFER_LOW_WATER_MARK, 128 * 1024);
//        MonitorServer monitor = new MonitorServer();
//        // provider
//        IOrder orderService = new OrderService();
////        IUser userService = new UserService();
//        monitor.start();
//        // 本地注册
////        ServiceWrapper provider = 
//        server.serviceRegistry()
//        		.interfaceClass(IOrder.class)
//        		.group("testGroup")
//        		.providerName("testProvider")
//        		.version("1.0.0")
//                .provider(orderService)
////                .provider(userService)
//                .register();
//        // 连接注册中心
////        server.connectToRegistryServer("127.0.0.1:20001");
//        // 向注册中心发布服务
////        server.publish(provider);
//        // 启动server
//		server.acceptor().start();
//	}
	// motan 启动服务
	static void startMotanServer() {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:motan_server3.xml");
	}
}
