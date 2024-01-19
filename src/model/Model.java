package model;
import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import javax.swing.filechooser.FileSystemView;

import ui.IObserver;

public class Model implements IModel{
	
	private List<IObserver> observers = new ArrayList<>();
	
	private Vector<Subscriber> subscribers = new Vector<>();
	private Hashtable<String,Journal> journals = new Hashtable<>();
	private List<Subscription> subscriptions = new ArrayList<>();
	private Distributor distributor = new Distributor(this);

	@Override
	public void initialize() {
		// Load state yapılacak
	}

	@Override
	public void registerObserver(IObserver o) {
		observers.add(o);
		
	}

	@Override
	public void removeObserver(IObserver o) {
		observers.remove(o);
	}

	@Override
	public Vector<Subscriber> getSubscribers() {
		return subscribers;
	}

	@Override
	public Vector<Subscriber> getDistributorSubscribers() {
		return distributor.getSubscribers();
	}

	@Override
	public Hashtable<String, Journal> getJournals() {
		return journals;
	}

	@Override
	public Hashtable<String, Journal> getDistributorJournals() {
		return distributor.getJournals();
	}

	@Override
	public List<Subscription> getSubscriptions() {
		return subscriptions;
	}

	@Override
	public void addJournal(String name, String issn, int frequency, double price) {
		Journal journal = new Journal(name,issn,frequency,price);
		if(searchJournal(issn) == null) {
			journals.put(issn, journal);
			notifyInfo(issn + " added!");
			notifyJournals();
		}
		else notifyInfo(issn + " already exists!");
		
	}
	
	public void notifyInfo(String message) {
		for(IObserver o : observers) {
			o.notifyInfo(message);
		}
	}
	
	public void notifySubscribers() {
		for(IObserver o : observers) {
			o.notifySubscribers(subscribers);
		}
	}
	
	public void notifyDistributorSubscribers() {
		for(IObserver o : observers) {
			o.notifyDistributorSubscribers(distributor.getSubscribers());
		}
	}
	
	public void notifyJournals() {
		for(IObserver o : observers) {
			o.notifyJournals(journals);
		}
	}
	
	public void notifyDistributorJournals() {
		for(IObserver o : observers) {
			o.notifyDistributorJournals(distributor.getJournals());
		}
	}
	
	public Journal searchJournal(String issn) {
		return journals.get(issn);
	}

	public Subscriber searchSubscriber(String name) {
		for(Subscriber subscriber : subscribers) {
			if(subscriber.getName().equals(name)) {
				return subscriber;
			}
		}
		return null;
	}

	@Override
	public void addJournalsToDistributor(ArrayList<String> issns) {
		String out = "";
		for(String issn : issns) {
			boolean result = distributor.addJournal(searchJournal(issn));
			if(!result) out += (issn + " already exists!\n");
			else {
				out += (issn + " added!\n");
				notifyDistributorJournals();
			}
		}
		notifyInfo(out);
		
	}

	@Override
	public void addIndividual(String name, String address, String cardNumber, int expireMonth, int expireYear,
			int CCV) {
		Individual individual = new Individual(name,address,cardNumber,expireMonth,expireYear,CCV);
		if(searchSubscriber(name) == null) {
			subscribers.add(individual);
			notifySubscribers();
			notifyInfo(name + " added!");
		}else {
			notifyInfo(name + " already exists!");
		}
	}

	@Override
	public void addCorporation(String name, String address, int bankCode, String bankName, int day, int month, int year,
			int accountNumber) {
		Corporation corporation = new Corporation(name,address,bankCode,bankName,day,month,year,accountNumber);
		if(searchSubscriber(name) == null) {
			subscribers.add(corporation);
			notifySubscribers();
			notifyInfo(name + " added!");
		}else {
			notifyInfo(name + " already exists!");
		}
	}

	@Override
	public void addSubscribersToDistributor(ArrayList<String> names) {
		String out = "";
		for(String name : names) {
			boolean result = distributor.addSubscriber(searchSubscriber(name));
			if(!result) out += (name + " already exists!\n");
			else {
				out += (name + " added!\n");
				notifyDistributorSubscribers();
			}
		}
		notifyInfo(out);
		
	}

	@Override
	public void addSubscription(String name, String issn, int month, int year, int copies, double discount) {
		Journal journal = distributor.searchJournal(issn);
		Subscriber subscriber = distributor.searchSubscriber(name);
		if(subscriber==null) {
			notifyInfo(name + " is not a subscriber!");
			return;
		}
		if(journal==null) {
			notifyInfo(issn + " is not exist!");
			return;
		}
		DateInfo dateInfo = new DateInfo(month,year);
		PaymentInfo paymentInfo = new PaymentInfo(discount);
		Subscription subscription = new Subscription(dateInfo,copies,journal,subscriber);
		subscription.setPayment(paymentInfo);
		boolean result = distributor.addSubscription(issn, subscriber, subscription);
		if(!result) {
			notifyInfo("Subscription not added!");
			return;
		}
		notifyInfo("Subscription added for issn: " + issn + " and name: " + name);
	}
	
	public void notifySubscriptions(Journal journal) {
		for(IObserver o : observers) {
			o.notifySubscriptions(journal.getSubscriptions());
		}
	}

	@Override
	public void journalSelected(String issn) {
		Journal journal = distributor.searchJournal(issn);
		if(journal == null) {
			notifyInfo("There is no journal with this issn to show subscriptions: " + issn);
			return;
		}
		notifySubscriptions(journal);
	}

	@Override
	public void increasePayment(double payment, String name, String issn) {
		Journal journal = distributor.searchJournal(issn);
		Subscriber subscriber = distributor.searchSubscriber(name);
		if(journal == null) {
			notifyInfo("There is no journal with this issn to increase payment: " + issn);
			return;
		}
		if(subscriber == null) {
			notifyInfo("There is no subscriber with this name to increase payment: " + name);
			return;
		}
		if(payment < 0) {
			notifyInfo("Payment can not be negative: " + payment);
			return;
		}
		List<Subscription> subscriptions = journal.getSubscriptions();
		for(Subscription subscription : subscriptions) {
			if(subscription.getSubscriber().getName().equals(name)) {
				subscription.acceptPayment(payment);
				notifySubscriptions(journal);
				notifyInfo("Payment added for issn: " + issn + ", name: " + name + ", amount: " + payment);
			}
		}
		
	}

	@Override
	public void loadState(String fileName) {
		fileName = (FileSystemView.getFileSystemView().getHomeDirectory().getPath() + File.separator + "Desktop"+File.separator+fileName );
		File file = new File(fileName);
		if(!file.exists()) {
			notifyInfo("No file to load: " + fileName);
			return;
		}
		distributor.loadState(fileName);
	}

	@Override
	public void saveState(String fileName) {
		fileName = (FileSystemView.getFileSystemView().getHomeDirectory().getPath() + File.separator + "Desktop"+File.separator+fileName );
		distributor.saveState(fileName);
		
	}

	@Override
	public void listAllSendingOrders(Date date) {
		Calendar calendar =  Calendar.getInstance();
		calendar.setTime(date);
		int month = calendar.get(Calendar.MONTH) + 1;
		int year = calendar.get(Calendar.YEAR);
		distributor.listAllSendingOrders(month, year);
		
	}

	@Override
	public void listSendingOders(String issn, Date date) {
		Calendar calendar =  Calendar.getInstance();
		calendar.setTime(date);
		int month = calendar.get(Calendar.MONTH) + 1;
		int year = calendar.get(Calendar.YEAR);
		distributor.listSendingOrders(issn, month, year);
		
	}

	@Override
	public void listIncopletePayments(Date date) {
		Calendar calendar =  Calendar.getInstance();
		calendar.setTime(date);
		int month = calendar.get(Calendar.MONTH) + 1;
		int year = calendar.get(Calendar.YEAR);
		distributor.listIncopletePayments(month,year);
		
	}

	@Override
	public void listSubscriptions(String issn) {
		distributor.listSubscriptions(issn);
		
	}

	@Override
	public void listSubscriptionsOfSubscriber(String name) {
		distributor.listSubscriptionsOfSubscriber(name);
		
	}

	@Override
	public void createReport(int month, int year, int year1, int year2) {
		distributor.report(month, year, year1, year2);
	}	
	
	public void notifyReport(int month,int year, int year1, int year2, List<Subscription> subscriptions, List<Double> payments) {
		for(IObserver o : observers) {
			o.notifyReport(month, year, year1, year2, subscriptions, payments);
		}
	}

	
	

}
