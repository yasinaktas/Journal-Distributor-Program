package model;

import java.io.Serializable;

public class Individual extends Subscriber implements Serializable{
	
	private static final long serialVersionUID = -979044898624964777L;
	private String creditCardNr;
	private int expireMonth;
	private int expireYear;
	private int CCV;
	

	public Individual(String name, String address, String creditCardNr, int expireMonth, int expireYear, int cCV) {
		super(name, address);
		this.creditCardNr = creditCardNr;
		this.expireMonth = expireMonth;
		this.expireYear = expireYear;
		CCV = cCV;
	}
	
	

	public String getCreditCardNr() {
		return creditCardNr;
	}




	public void setCreditCardNr(String creditCardNr) {
		this.creditCardNr = creditCardNr;
	}




	public int getExpireMonth() {
		return expireMonth;
	}




	public void setExpireMonth(int expireMonth) {
		this.expireMonth = expireMonth;
	}




	public int getExpireYear() {
		return expireYear;
	}




	public void setExpireYear(int expireYear) {
		this.expireYear = expireYear;
	}




	public int getCCV() {
		return CCV;
	}




	public void setCCV(int cCV) {
		CCV = cCV;
	}




	@Override
	public String getBillingInformation() {
		return "Credit Card Number: " +  creditCardNr + ", Expire Date: " + expireMonth + "/" + expireYear + ", CCV: " + CCV;
	}

}
