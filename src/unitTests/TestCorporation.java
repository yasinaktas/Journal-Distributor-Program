package unitTests;

import static org.junit.Assert.*;
import model.*;

import org.junit.Before;
import org.junit.Test;

public class TestCorporation {
	
	Corporation baykar;

	@Before
	public void setUp() throws Exception {
		baykar = new Corporation("Baykar","İstanbul",12,"Yapı Kredi",24,2,1998,5689);
	}

	@Test
	public void testBankName() {
		assertEquals("Yapı Kredi",baykar.getBankName());
	}
	
	@Test
	public void testIssueYear() {
		assertEquals(1998,baykar.getIssueYear());
	}

}
