package model;

import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import ui.IObserver;

public interface IModel {
	void initialize();
	void registerObserver(IObserver o);
	void removeObserver(IObserver o);
	Vector<Subscriber> getSubscribers();
	Vector<Subscriber> getDistributorSubscribers();
	Hashtable<String,Journal> getJournals();
	Hashtable<String,Journal> getDistributorJournals();
	List<Subscription> getSubscriptions();
	void addJournal(String name, String issn, int frequency, double price);
	void addJournalsToDistributor(ArrayList<String> issns);
	void addIndividual(String name, String address, String cardNumber, int expireMonth,int expireYear,int CCV);
	void addCorporation(String name, String address,int bankCode, String bankName, int day, int month, int year, int accountNumber);
	void addSubscribersToDistributor(ArrayList<String> names);
	void addSubscription(String name, String issn, int month, int year, int copies, double discount);
	void journalSelected(String issn);
	void increasePayment(double payment,String name,String issn);
	void loadState(String fileName);
	void saveState(String fileName);
	void listAllSendingOrders(Date date);
	void listSendingOders(String issn, Date date);
	void listIncopletePayments(Date date);
	void listSubscriptions(String issn);
	void listSubscriptionsOfSubscriber(String name);
	void createReport(int month, int year, int year1, int year2);
}
