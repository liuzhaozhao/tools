package serializable;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class Main {
	public static void main(String[] args) {
		serialize();
		deserialize();
	}

	private static void serialize() {
		List addresses = new ArrayList();
		addresses.add(new Address(123, "street", "city"));
		addresses.add(new Address(124, "street1", "city1"));
		Employee emp = new Employee(101, "Arpit", "CS", addresses);
		
		try {
			FileOutputStream fileOut = new FileOutputStream("C:\\Users\\Administrator\\Desktop\\employee.ser");
			ObjectOutputStream outStream = new ObjectOutputStream(fileOut);
			outStream.writeObject(emp);
			outStream.close();
			fileOut.close();
		} catch (IOException i) {
			i.printStackTrace();
		}
	}

	private static void deserialize() {
		Employee emp = null;
		try {
			FileInputStream fileIn = new FileInputStream("C:\\Users\\Administrator\\Desktop\\employee.ser");
			ObjectInputStream in = new ObjectInputStream(fileIn);
			emp = (Employee) in.readObject();
			in.close();
			fileIn.close();
		} catch (IOException i) {
			i.printStackTrace();
			return;
		} catch (ClassNotFoundException c) {
			System.out.println("Employee class not found");
			c.printStackTrace();
			return;
		}
		System.out.println("Deserialized Employee...");
		System.out.println("Emp id: " + emp.getEmployeeId());
		System.out.println("Name: " + emp.getEmployeeName());
		System.out.println("Department: " + emp.getDepartment());
		System.out.println("Addresses: " + ((Address)emp.getAddresses().get(0)).getHomeNo());
	}
}
