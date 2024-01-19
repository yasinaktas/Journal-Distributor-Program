package unitTests;

import static org.junit.Assert.*;
import model.*;

import org.junit.Before;
import org.junit.Test;

public class TestDateInfo {
	
	DateInfo date;

	@Before
	public void setUp() throws Exception {
		date = new DateInfo(1,2023);
	}

	@Test
	public void testEndMonth() {
		int endMonth = date.getStartMonth() - 1;
		if(endMonth == 0) endMonth += 12;
		assertEquals(endMonth,date.getEndMonth());
	}
	
	@Test
	public void testYear() {
		assertEquals(2023,date.getStartYear());
	}

}
