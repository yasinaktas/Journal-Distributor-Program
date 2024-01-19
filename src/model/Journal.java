package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Journal implements Serializable{
	private static final long serialVersionUID = 8673657594928864146L;
	private List<Subscription> subscriptions;
	private String name,issn;
	private int frequency;
	private double issuePrice;
	
	
	
	public Journal(String name, String issn, int frequency, double issuePrice) {
		this.name = name;
		this.issn = issn;
		this.frequency = frequency;
		this.issuePrice = issuePrice;
		subscriptions = new ArrayList<>();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIssn() {
		return issn;
	}

	public void setIssn(String issn) {
		this.issn = issn;
	}

	public int getFrequency() {
		return frequency;
	}

	public void setFrequency(int frequency) {
		this.frequency = frequency;
	}

	public double getIssuePrice() {
		return issuePrice;
	}

	public void setIssuePrice(double issuePrice) {
		this.issuePrice = issuePrice;
	}
	
	
	
	public List<Subscription> getSubscriptions() {
		return subscriptions;
	}

	public void setSubscriptions(List<Subscription> subscriptions) {
		this.subscriptions = subscriptions;
	}

	private Subscription searchSubscription(Subscription subscription) {
		for(Subscription s : subscriptions) {
			if(s.getSubscriber().getName().equals(subscription.getSubscriber().getName())) {
				return s;
			}
		}
		return null;
	}
	
	public void addSubscription(Subscription subscription) {
		Subscription s = searchSubscription(subscription);
		if(s == null) {
			subscriptions.add(subscription);
		}else {
			s.setCopies(s.getCopies()+1);
		}
	}
	


}
