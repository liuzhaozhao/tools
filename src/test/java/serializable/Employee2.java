package serializable;

import java.io.Serializable;

public class Employee2 implements Serializable {
	private static final long serialVersionUID = 2281687077885824882L;
	
	int employeeId;
    String employeeName;
    String department;
    String test;
    
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
    
    public String getTest() {
		return test;
	}
    
    public void setTest(String test) {
		this.test = test;
	}
}
