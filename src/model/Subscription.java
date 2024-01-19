package model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class Subscription implements Serializable{
	
	private static final long serialVersionUID = 1538582986628537678L;
	private final DateInfo dates;
	private PaymentInfo payment;
	private int copies;
	private final Journal journal;
	private final Subscriber subscriber;

	public Subscription(DateInfo dates, int copies, Journal journal, Subscriber subscriber) {
		this.dates = dates;
		this.copies = copies;
		this.journal = journal;
		this.subscriber = subscriber;
	}

	public PaymentInfo getPayment() {
		return payment;
	}

	public void setPayment(PaymentInfo payment) {
		this.payment = payment;
	}

	public int getCopies() {
		return copies;
	}

	public void setCopies(int copies) {
		this.copies = copies;
	}

	public DateInfo getDates() {
		return dates;
	}

	public Journal getJournal() {
		return journal;
	}

	public Subscriber getSubscriber() {
		return subscriber;
	}
	
	public void acceptPayment(double amount) {
		payment.increasePayment(amount);
	}
	
	
	public boolean canSend(int issueMonth, int issueYear) {
		int startMonth = dates.getStartMonth();
		int startYear = dates.getStartYear();
		
		int months = getMonthsCount(startMonth,startYear,issueMonth,issueYear);
		if(months <= 0) return false;
		
		if(subscriber instanceof Individual) {
			Individual individual = (Individual) subscriber;
			if(getMonthsCount(issueMonth,issueYear,individual.getExpireMonth(),individual.getExpireYear()) < 0) {
				return false;
			}
		}
		if(subscriber instanceof Corporation) {
			Corporation corporation = (Corporation) subscriber;
			if(getMonthsCount(issueMonth,issueYear,corporation.getIssueMonth(),corporation.getIssueYear()) < 0) {
				return false;
			}
		}
		
		double paymentNeed = journal.getIssuePrice() * months * (1 - payment.getDiscountRatio());
		double paymentSoFar = payment.getReceivedPayment();
		if(paymentSoFar >= paymentNeed) return true;
		return false;
	}
	
	private int getMonthsCount(int startMonth, int startYear, int endMonth, int endYear) {
		return (endYear - startYear)*12 + (endMonth - startMonth + 1);
	}
	
}
