package unitTests;

import static org.junit.Assert.*;
import model.*;

import org.junit.Before;
import org.junit.Test;

public class TestIndividual {
	
	Individual yasin;

	@Before
	public void setUp() throws Exception {
		yasin = new Individual("Yasin","Sivas","ABC123",2,2024,3467);
	}

	@Test
	public void testName() {
		assertEquals("Yasin",yasin.getName());
	}
	
	@Test
	public void testCardNr() {
		assertEquals("ABC123",yasin.getCreditCardNr());
	}

}
