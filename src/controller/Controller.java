package controller;
import java.util.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;

import model.Corporation;
import model.IModel;
import model.Individual;
import model.Journal;
import model.Subscriber;
import model.Subscription;
import ui.Screen;

public class Controller implements IController{
	
	private Screen screen;
	private IModel model;
	
	public Controller(IModel model) {
		this.model = model;
		
		screen = new Screen(model,this);
		screen.create();
		screen.start();
		screen.setDate(System.currentTimeMillis());
		
		model.initialize();
	}

	@Override
	public void addJournal(String name, String issn, String frequency, String issuePrice) {
		if(name.trim().equals("") || issn.trim().equals("") || frequency.trim().equals("") || issuePrice.trim().equals("")) {
			screen.setInformation("Fields must be filled!");
			return;
		}
		int freq = -1;
		double price = -1;
		try {
			freq = Integer.parseInt(frequency);
		}catch(NumberFormatException e) {
			screen.setInformation("Frequenct must be integer!");
			return;
		}
		try {
			price = Double.parseDouble(issuePrice);
		}catch(NumberFormatException e) {
			screen.setInformation("Issue Price must be double!");
			return;
		}
		if(price<0 || freq<0) {
			screen.setInformation("Price and Frequency can not be negative!");
			return;
		}
		model.addJournal(name, issn, freq, price);
	}

	@Override
	public void addJournalsToDistributor(ArrayList<String> issns) {
		if(issns.size()<=0) {
			screen.setInformation("Please select journals.");
			return;
		}
		model.addJournalsToDistributor(issns);
	}

	@Override
	public void addIndividual(String name, String address, String cardNumber, int expireMonth, int expireYear,
			String CCV) {
		if(name.trim().equals("") || address.trim().equals("") || cardNumber.trim().equals("") || CCV.trim().equals("")) {
			screen.setInformation("Fields must be filled!");
			return;
		}
		int ccv = -1;
		try {
			ccv = Integer.parseInt(CCV);
		}catch(NumberFormatException e) {
			screen.setInformation("CCV must be integer!");
			return;
		}
		model.addIndividual(name, address, cardNumber, expireMonth, expireYear, ccv);
		
	}

	@Override
	public void addCorporation(String name, String address, String bankCode, String bankName, Date issueDate,
			String accountNumber) {
		if(name.trim().equals("") || address.trim().equals("") || bankCode.trim().equals("") || bankName.trim().equals("") || accountNumber.trim().equals("")) {
			screen.setInformation("Fields must be filled!");
			return;
		}
		if(issueDate == null) {
			screen.setInformation("Please select a issue date!");
			return;
		}
		int _bankCode = -1;
		int _accountNumber = -1;
		try {
			_bankCode = Integer.parseInt(bankCode);
			_accountNumber = Integer.parseInt(accountNumber);
		}catch(NumberFormatException e) {
			screen.setInformation("Bank Code and Account Number must be integer!");
			return;
		}
		Calendar calendar = Calendar.getInstance();
        calendar.setTime(issueDate);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH) + 1; 
        int year = calendar.get(Calendar.YEAR);
		model.addCorporation(name, address, _bankCode, bankName, day, month, year, _accountNumber);
	}

	@Override
	public void addSubscribersToDistributor(ArrayList<String> names) {
		if(names.size()<=0) {
			screen.setInformation("Please select subscribers.");
			return;
		}
		model.addSubscribersToDistributor(names);
		
	}

	@Override
	public void addSubscription(String name, String issn, int month, int year, String copies, String discount) {
		
		if(name.isEmpty() || issn.isEmpty() || copies.isEmpty() || discount.isEmpty()) {
			screen.setInformation("Fields must be filled!");
			return;
		}
		int cop = -1;
		double dis = -1;
		try {
			cop = Integer.parseInt(copies);
			dis = Double.parseDouble(discount);
		}catch(NumberFormatException e) {
			screen.setInformation("Copies and Discount Ratio must be integer and double!");
			return;
		}
		if(dis<0 || dis >1) {
			screen.setInformation("Discount Ratio must be between 0 and 1!");
			return;
		}
		model.addSubscription(name,issn,month,year,cop,dis);
	}

	@Override
	public void journalSelected(String issn) {
		model.journalSelected(issn);
		
	}

	@Override
	public void increasePayment(String payment, String name, String issn) {
		double pay = -1;
		try {
			pay = Double.parseDouble(payment);
		}catch(NumberFormatException e) {
			screen.setInformation("Payment should be integer!");
		}
		model.increasePayment(pay,name,issn);
		
	}

	@Override
	public void loadState(String fileName) {
		if(fileName.isEmpty()) {
			screen.setInformation("Please write a file name!");
			return;
		}
		model.loadState(fileName);
		
	}

	@Override
	public void saveState(String fileName) {
		if(fileName.isEmpty()) {
			screen.setInformation("Please write a file name!");
			return;
		}
		model.saveState(fileName);
		
	}

	@Override
	public void listAllSendingOrders(Date date) {
		model.listAllSendingOrders(date);
		
	}

	@Override
	public void listSendingOders(String issn, Date date) {
		model.listSendingOders(issn, date);
		
	}

	@Override
	public void listIncopletePayments(Date date) {
		model.listIncopletePayments(date);
		
	}

	@Override
	public void listSubscriptions(String issn) {
		model.listSubscriptions(issn);
		
	}

	@Override
	public void listSubscriptionsOfSubscriber(String name) {
		model.listSubscriptionsOfSubscriber(name);
		
	}

	@Override
	public void createReport(int month, int year, int year1, int year2) {
		if(year2 < year1) {
			int temp = year1;
			year1 = year2;
			year2 = temp;
		}
		model.createReport(month,year,year1,year2);
	}

	

	

	

	
	
	

}
