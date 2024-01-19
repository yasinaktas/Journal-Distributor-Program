package model;

import java.io.Serializable;

public abstract class Subscriber implements Serializable{
	private static final long serialVersionUID = -8005348384675695931L;
	private String name;
	private String address;
	
	public Subscriber(String name, String address) {
		this.name = name;
		this.address = address;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public abstract String getBillingInformation();
}
