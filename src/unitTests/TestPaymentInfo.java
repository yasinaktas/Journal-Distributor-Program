package unitTests;

import static org.junit.Assert.*;
import model.*;

import org.junit.Before;
import org.junit.Test;

public class TestPaymentInfo {
	
	PaymentInfo payment;

	@Before
	public void setUp() throws Exception {
		payment = new PaymentInfo(0.1);
		payment.increasePayment(100);
	}

	@Test
	public void testPaymentReceived() {
		assertTrue(100==payment.getReceivedPayment());
	}
	
	@Test
	public void testDiscount() {
		assertTrue(0.1==payment.getDiscountRatio());
	}

}
