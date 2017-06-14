package serializable;

import java.io.Serializable;

public class Address implements Serializable {
	private static final long serialVersionUID = 1L;
	int homeNo;
	String street;
	String city;

//	public Address() {
//		// TODO Auto-generated constructor stub
//	}
	
	public Address(int homeNo, String street, String city) {
		super();
		this.homeNo = homeNo;
		this.street = street;
		this.city = city;
	}

	public int getHomeNo() {
		return homeNo;
	}

	public void setHomeNo(int homeNo) {
		this.homeNo = homeNo;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
}
