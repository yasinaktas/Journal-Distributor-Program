package model;

import java.io.Serializable;

public class PaymentInfo implements Serializable{
	
	private static final long serialVersionUID = 5253308868995224420L;
	private final double discountRatio;
	private double receivedPayment;
	
	public PaymentInfo(double discountRatio) {
		this.discountRatio = discountRatio;
		receivedPayment = 0;
	}

	public double getReceivedPayment() {
		return receivedPayment;
	}

	public void setReceivedPayment(double receivedPayment) {
		this.receivedPayment = receivedPayment;
	}

	public double getDiscountRatio() {
		return discountRatio;
	}
	
	public void increasePayment(double amount) {
		receivedPayment += amount;
	}
	
	

}
