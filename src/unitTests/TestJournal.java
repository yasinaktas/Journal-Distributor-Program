package unitTests;

import static org.junit.Assert.*;
import model.*;

import org.junit.Before;
import org.junit.Test;

public class TestJournal {
	
	Journal journal;

	@Before
	public void setUp() throws Exception {
		journal = new Journal("Kitap","ISSN",12,100);
	}

	@Test
	public void testISSN() {
		assertEquals("ISSN",journal.getIssn());
	}
	
	@Test
	public void testPrice() {
		assertEquals(12,journal.getFrequency());
	}

}
