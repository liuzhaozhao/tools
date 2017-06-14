package serializable;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

public class Main2 {
	private static Kryo kryo = new Kryo();
	
	public static void main(String[] args) throws Exception {
		kryo.setReferences(false);
		kryo.setRegistrationRequired(false);  
//		kryo.setInstantiatorStrategy(new StdInstantiatorStrategy());
		
		serialize();
		deserialize();
	}
	
	private static void serialize() throws Exception {
		
		Output output = new Output(new FileOutputStream("C:\\Users\\Administrator\\Desktop\\employee.ser"));
		
		List addresses = new ArrayList();
		addresses.add(new Address(123, "street", "city"));
		addresses.add(new Address(124, "street1", "city1"));
		kryo.writeObject(output, new Employee(101, "Arpit", "CS", addresses));  
		output.close();
	}
	
	private static void deserialize() throws Exception {
		Employee emp = null;
		FileInputStream fileIn = new FileInputStream("C:\\Users\\Administrator\\Desktop\\employee.ser");
		Input in = new Input(fileIn);
		emp = kryo.readObject(in, Employee.class);
		in.close();
		System.out.println("Deserialized Employee...");
		System.out.println("Emp id: " + emp.getEmployeeId());
		System.out.println("Name: " + emp.getEmployeeName());
		System.out.println("Department: " + emp.getDepartment());
		System.out.println("Addresses: " + ((Address)emp.getAddresses().get(0)).getHomeNo());
	}
}
