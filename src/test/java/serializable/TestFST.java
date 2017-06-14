package serializable;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.nustaq.serialization.FSTConfiguration;

public class TestFST {
	static FSTConfiguration configuration = 
			FSTConfiguration.createJsonConfiguration(false, false);
//			FSTConfiguration.createDefaultConfiguration();
//			FSTConfiguration.createStructConfiguration();
	
	public static void main(String[] args) throws Exception {
		configuration.setForceSerializable(true);// 即使对象不实现Serializable接口也可序列化
		
//		serialize();
		deserialize();
	}
	
	private static void serialize() throws Exception {
		FileOutputStream fileOut = new FileOutputStream("C:\\Users\\Administrator\\Desktop\\employee.ser");
		List addresses = new ArrayList();
		addresses.add(new Address(123, "street", "city"));
		addresses.add(new Address(124, "street1", "city1"));
		Employee employee = new Employee(101, "Arpit", "CS", addresses);
		
		fileOut.write(configuration.asByteArray(employee));
		fileOut.close();
	}
	
	private static void deserialize() throws Exception {
		FileInputStream fileIn = new FileInputStream("C:\\Users\\Administrator\\Desktop\\employee.ser");
		byte[] bs = new byte[fileIn.available()];
		fileIn.read(bs);
		Employee emp = (Employee) configuration.asObject(bs);
		fileIn.close();
		
		System.out.println("Deserialized Employee...");
		System.out.println("Emp id: " + emp.getEmployeeId());
//		System.out.println("Name: " + emp.getEmployeeName());
//		System.out.println("Department: " + emp.getDepartment());
		System.out.println("Addresses: " + emp.getAddresses());
		System.out.println("Addresses: " + ((Address)emp.getAddresses().get(0)).getHomeNo());
	}
}
