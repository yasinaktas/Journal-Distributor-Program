package model;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import controller.*;
import ui.*;

public class Distributor {
	
	private Hashtable<String,Journal> journals;
	private Vector<Subscriber> subscribers;
	private Model model;
	ExecutorService pool = Executors.newCachedThreadPool();
	
	
	public Distributor(Model model) {
		journals = new Hashtable<>();
		subscribers = new Vector<>();
		this.model = model;
		pool.shutdown();
	}
	
	public boolean addJournal(Journal journal) {
		if(searchJournal(journal.getIssn())==null) {
			journals.put(journal.getIssn(), journal);
			return true;
		}
		return false;
		
	}
	
	public Journal searchJournal(String issn) {
		return journals.get(issn);
	}
	
	public boolean addSubscriber(Subscriber subscriber) {
		if(searchSubscriber(subscriber.getName())==null) {
			subscribers.add(subscriber);
			return true;
		}
		return false;
	}
	
	public Subscriber searchSubscriber(String name) {
		for(Subscriber subscriber : subscribers) {
			if(subscriber.getName().equals(name)) {
				return subscriber;
			}
		}
		return null;
	}
	
	public boolean addSubscription(String issn, Subscriber subscriber, Subscription subscription) {
		Journal journal = searchJournal(issn);
		if(journal==null)return false;
		if(searchSubscriber(subscriber.getName())==null)return false;
		if(searchJournal(subscription.getJournal().getIssn()) == null) return false;
		if(searchSubscriber(subscription.getSubscriber().getName())==null) return false;
		journal.addSubscription(subscription);
		return true;
	}
	
	public void listAllSendingOrders(int month, int year) {
		String out = "";
		for(Journal journal : journals.values()) {
			for(Subscription subscription : journal.getSubscriptions()) {
				if(subscription.canSend(month,year)) {
					System.out.println(journal.getName() + " will be sent to " + subscription.getSubscriber().getName() + " in " + month + "/" + year + ".");
					out += (journal.getName() + " will be sent to " + subscription.getSubscriber().getName() + " in " + month + "/" + year + "\n");
				}
			}
		}
		model.notifyInfo(out);
	}
	
	public void listSendingOrders(String issn, int month, int year) {
		String out = "";
		Journal journal = journals.get(issn);
		if(journal == null) {
			out+=("No journal for issn: " + issn);
		}else {
			for(Subscription subscription : journal.getSubscriptions()) {
				if(subscription.canSend(month,year)) {
					System.out.println(journal.getName() + " will be sent to " + subscription.getSubscriber().getName() + " in " + month + "/" + year + ".");
					out += (journal.getName() + " will be sent to " + subscription.getSubscriber().getName() + " in " + month + "/" + year + "\n");
				}
			}
		}
		model.notifyInfo(out);
	}
	
	public void listIncopletePayments(int month,int year) {
		String out = "";
		for(Journal journal : journals.values()) {
			for(Subscription subscription : journal.getSubscriptions()) {
				if(!subscription.canSend(month,year)) {
					System.out.println(journal.getName() + " can not be sent to " + subscription.getSubscriber().getName());
					out += (journal.getName() + " can not be sent to " + subscription.getSubscriber().getName() + ", started at: "+ subscription.getDates().getStartMonth() + "/" + subscription.getDates().getStartYear() + "\n");
				}
			}
		}
		model.notifyInfo(out);
	}
	
	public void listSubscriptionsOfSubscriber(String subscriberName) {
		String out = "";
		Subscriber subscriber = searchSubscriber(subscriberName);
		if(subscriber == null) {
			System.out.println("No subscriber with name: " + subscriberName);
			model.notifyInfo("No subscriber with name: " + subscriberName);
			return;
		}
		for(Journal journal : journals.values()) {
			for(Subscription subscription : journal.getSubscriptions()) {
				if(subscription.getSubscriber().equals(subscriber)) {
					System.out.println(subscriber.getName() + " has " + journal.getName() + " subscription.");
					out += (subscriber.getName() + " has subscription " + journal.getName() + ", started at: "+ subscription.getDates().getStartMonth() + "/" + subscription.getDates().getStartYear() + "\n");
				}
			}
		}
		model.notifyInfo(out);
	}
	
	public void listSubscriptions(String issn) {
		String out = "";
		Journal journal = searchJournal(issn);
		if(journal == null) {
			System.out.println("No journal with issn: " + issn);
			model.notifyInfo("No journal with issn: " + issn);
			return;
		}
		System.out.println("Subscriptions of issn: " + issn + "\n");
		out += ("Subscriptions of issn: " + issn + "\n");
		for(Subscription subscription : journal.getSubscriptions()) {
			System.out.println("\t" + subscription.getSubscriber().getName());
			out += ("\t" + subscription.getSubscriber().getName() + ", started at: "+ subscription.getDates().getStartMonth() + "/" + subscription.getDates().getStartYear() + "\n");
		}
		model.notifyInfo(out);
	}
	
	
	
	public Hashtable<String, Journal> getJournals() {
		return journals;
	}


	public Vector<Subscriber> getSubscribers() {
		return subscribers;
	}


	public void saveState(String fileName) {
		Thread thread = new Thread(new ExecuteSaveState(fileName));
		thread.start();
		
	}
	
	public void loadState(String fileName) {
		Thread thread = new Thread(new ExecuteLoadState(fileName));
		thread.start();
		
	}
	
	public void report(int month, int year, int year1, int year2) {
		pool = Executors.newCachedThreadPool();
		pool.execute(new ExecuteReport(month,year,year1,year2));
		pool.shutdown();
	}
	
	private class ExecuteSaveState implements Runnable{
		String fileName;

		public ExecuteSaveState(String fileName) {
			this.fileName = fileName;
		}

		@Override
		public void run() {
			while(!pool.isShutdown() && !pool.isTerminated());
			try {
				ObjectOutputStream writer = new ObjectOutputStream(new FileOutputStream(fileName));
				writer.writeObject(subscribers.size());
				writer.writeObject(journals.size());
				for(Subscriber subscriber : subscribers) {
					writer.writeObject(subscriber);
				}
				for(Journal journal : journals.values()) {
					writer.writeObject(journal);
				}
				writer.close();
				model.notifyInfo(fileName + " saved!");
			}catch(IOException e) {
				model.notifyInfo(fileName + " can not saved!\n" + e.getMessage());
			}
		}
		
	}
	
	private class ExecuteLoadState implements Runnable{
		String fileName;

		public ExecuteLoadState(String fileName) {
			this.fileName = fileName;
		}

		@Override
		public void run() {
			while(!pool.isShutdown() && !pool.isTerminated());
			try {
				Vector<Subscriber> newSubscribers = new Vector<>();
				Hashtable<String,Journal> newJournals = new Hashtable<>();
				ObjectInputStream reader = new ObjectInputStream(new FileInputStream(fileName));
				Integer subscriberCount = (Integer)reader.readObject();
				Integer journalCount = (Integer)reader.readObject();
				for(int i=0; i<subscriberCount;i++) {
					Subscriber subscriber = (Subscriber)reader.readObject();
					System.out.println(subscriber.getName());
					newSubscribers.add(subscriber);
				}
				for(int i=0; i<journalCount; i++) {
					Journal journal = (Journal)reader.readObject();
					System.out.println(journal.getIssn());
					newJournals.put(journal.getIssn(), journal);
				}
				journals = newJournals;
				subscribers = newSubscribers;
				reader.close();
				model.notifyInfo(fileName + " loaded!");
				model.notifyDistributorSubscribers();
				model.notifyDistributorJournals();
			}catch(IOException e) {
				model.notifyInfo(fileName + " can not loaded!\n" + e.getMessage());
			} catch (ClassNotFoundException e) {
				model.notifyInfo(fileName + " can not loaded!\n" + e.getMessage());
			}
			model.notifyInfo(fileName + " loaded!");
		}
		
	}
	
	private class ExecuteReport implements Runnable{
		
		int month,year,year1,year2;
		
		

		public ExecuteReport(int month, int year, int year1, int year2) {
			this.month = month;
			this.year = year;
			this.year1 = year1;
			this.year2 = year2;
		}



		@Override
		public void run() {
			List<Subscription> expiredSubscriptions = new ArrayList<>();
			for(Journal journal : journals.values()) {
				for(Subscription subscription : journal.getSubscriptions()) {
					if(!subscription.canSend(month, year)) expiredSubscriptions.add(subscription);
				}
			}
			List<Double> receivedAnnualPayments = new ArrayList<>();
			for(int i=year1; i<=year2; i++) {
				double amount = 0;
				for(int j=1; j<=12; j++) {
					for(Journal journal : journals.values()) {
						for(Subscription subscription : journal.getSubscriptions()) {
							if(subscription.canSend(j, i)) {
								amount += (journal.getIssuePrice() * (1-subscription.getPayment().getDiscountRatio()));
							}
						}
					}
				}
				receivedAnnualPayments.add(amount);
			}
			model.notifyReport(month,year,year1,year2,expiredSubscriptions,receivedAnnualPayments);
			
		}
		
	}
	
}

