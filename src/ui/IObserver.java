package ui;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;
import model.*;

public interface IObserver {
	void notifyInfo(String message);
	void notifySubscribers(Vector<Subscriber> subscribers);
	void notifyJournals(Hashtable<String,Journal> journals);
	void notifyDistributorJournals(Hashtable<String,Journal> journals);
	void notifyDistributorSubscribers(Vector<Subscriber> subscribers);
	void notifySubscriptions(List<Subscription> subscriptions);
	void notifyReport(int month, int year, int year1, int year2, List<Subscription> expiredSubscriptions, List<Double> annualPayments);
}
