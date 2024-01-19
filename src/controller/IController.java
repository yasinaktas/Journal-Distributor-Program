package controller;

import java.util.Date;
import java.time.LocalDate;
import java.util.ArrayList;

import model.Journal;
import model.Subscriber;
import model.Subscription;

public interface IController {
	void addJournal(String name,String issn,String frequenct,String issuePrice);
	void addJournalsToDistributor(ArrayList<String> issns);
	void addIndividual(String name, String address,String cardNumber,int expireMonth,int expireYear,String CCV);
	void addCorporation(String name, String address,String bankCode,String bankName,Date issueDate,String accountNumber);
	void addSubscribersToDistributor(ArrayList<String> names);
	void addSubscription(String name, String issn, int month, int year, String copies, String discount);
	void journalSelected(String issn);
	void increasePayment(String payment,String name,String issn);
	void loadState(String fileName);
	void saveState(String fileName);
	void listAllSendingOrders(Date date);
	void listSendingOders(String issn, Date date);
	void listIncopletePayments(Date date);
	void listSubscriptions(String issn);
	void listSubscriptionsOfSubscriber(String name);
	void createReport(int month, int year, int year1, int year2);
}
