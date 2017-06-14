package serializable;

import java.io.Serializable;
import java.util.List;

public class Employee implements Serializable {
	private static final long serialVersionUID = 1L;
	int employeeId;
    String employeeName;
    String department;
    List addresses;
    
//    public Employee() {
//	}
    
    public Employee(int employeeId, String employeeName, String department, List addresses) {
		this.employeeId = employeeId;
		this.employeeName = employeeName;
		this.department = department;
		this.addresses = addresses;
	}
    
    public int getEmployeeId() {
        return employeeId;
    }
    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }
    public String getEmployeeName() {
        return employeeName;
    }
    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }
    public String getDepartment() {
        return department;
    }
    public void setDepartment(String department) {
        this.department = department;
    }
    
    public List getAddresses() {
		return addresses;
	}
    
    public void setAddresses(List addresses) {
		this.addresses = addresses;
	}
}
