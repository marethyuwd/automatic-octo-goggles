package de.fh_dortmund.cw.autofinanzierung.app.client.test;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import de.fh_dortmund.cw.autofinanzierung.app.client.ServiceHandlerImpl;

public class LoanCalculatorTest {

	private static ServiceHandlerImpl serviceHandler;

	@BeforeClass
	public static void setUp() throws Exception {
		serviceHandler = (ServiceHandlerImpl) ServiceHandlerImpl.getInstance();
	}

	@Test
	public void testComputeNetLoanAmout() {
		assertEquals(7500,serviceHandler.computeNetLoanAmount(10000, 2500),0.01);
	}

	@Test
	public void testGetInterestrate() {
		assertEquals(1.0,serviceHandler.getInterestRate(),0.1);
	}
	@Test
	public void testComputeGrossLoanAmount() {
		assertEquals(12065,serviceHandler.computeGrossLoanAmount(14500, 2500, 12),0.01);
	}
	@Test
	public void testMonthlyPayment() {
		assertEquals(1005.42,serviceHandler.computeMonthlyPayment(14500, 2500, 12),0.01);
	}
}
