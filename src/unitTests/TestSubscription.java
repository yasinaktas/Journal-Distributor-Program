package unitTests;

import static org.junit.Assert.*;
import model.*;

import org.junit.Before;
import org.junit.Test;

public class TestSubscription {
	
	Subscriber subscriber;
	Journal journal;
	DateInfo date;
	PaymentInfo payment;
	Subscription subscription;

	@Before
	public void setUp() throws Exception {
		subscriber = new Individual("Yasin","Sivas","ABC123",2,2026,56);
		journal = new Journal("Kitap","ISSN",12,10);
		date = new DateInfo(2,2025);
		payment = new PaymentInfo(0.2);
		subscription = new Subscription(date,2,journal,subscriber);
		subscription.setPayment(payment);
	}

	@Test
	public void testCanSend() {
		assertFalse(subscription.canSend(4, 2025));
		subscription.getPayment().increasePayment(100);
		assertTrue(subscription.canSend(4, 2025));
	}
	
	@Test
	public void testDate() {
		assertTrue(subscription.getDates().getStartYear() == 2025);
	}

}
