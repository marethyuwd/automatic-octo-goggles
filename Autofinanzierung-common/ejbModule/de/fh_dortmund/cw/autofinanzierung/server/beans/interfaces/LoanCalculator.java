package de.fh_dortmund.cw.autofinanzierung.server.beans.interfaces;

public interface LoanCalculator {
	
	public double computeGrossLoanAmount(double price, double firstInstalment, int paymentTerm) ;

	public double computeMonthlyPayment(double price, double firstInstalment, int paymentTerm) ;

	public double computeNetLoanAmount(double price, double firstInstalment);

	public double getInterestRate();


}
