package de.fh_dortmund.cw.autofinanzierung.app.client;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import de.fh_dortmund.cw.autofinanzierung.server.beans.interfaces.LoanCalculatorRemote;
import de.fh_dortmund.inf.cw.car_financing.client.shared.CarFinancingHandler;
import de.fh_dortmund.inf.cw.car_financing.client.shared.ServiceHandler;

public class ServiceHandlerImpl extends ServiceHandler implements CarFinancingHandler {

	private Context ctx;
	private static ServiceHandlerImpl instance;
	private LoanCalculatorRemote loanCalculator;

	private ServiceHandlerImpl() {
		try {
			ctx = new InitialContext();
			loanCalculator = (LoanCalculatorRemote) ctx.lookup(
					"java:global/Autofinanzierung-ear/Autofinanzierung-ejb/LoanCalculatorBean!de.fh_dortmund.cw.autofinanzierung.server.beans.interfaces.LoanCalculatorRemote");

		} catch (NamingException e) {

			e.printStackTrace();
		}
	}

	@Override
	public double computeGrossLoanAmount(double price, double firstInstalment,  int paymentTerm) {
		
		return loanCalculator.computeGrossLoanAmount(price, firstInstalment, paymentTerm);
	}

	@Override
	public double computeMonthlyPayment(double price, double firstInstalment, int paymentTerm) {

		return loanCalculator.computeMonthlyPayment(price, firstInstalment, paymentTerm);
	}

	@Override
	public double computeNetLoanAmount(double price, double firstInstalment) {

		return loanCalculator.computeNetLoanAmount(price, firstInstalment);
	}

	@Override
	public double getInterestRate() {

		return loanCalculator.getInterestRate()*100;
	}

	public static ServiceHandler getInstance() {
		if (instance == null) {
			instance = new ServiceHandlerImpl();
		}
		return instance;
	}

}
