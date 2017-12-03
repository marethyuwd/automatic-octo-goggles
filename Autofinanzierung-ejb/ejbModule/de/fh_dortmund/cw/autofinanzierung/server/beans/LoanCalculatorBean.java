package de.fh_dortmund.cw.autofinanzierung.server.beans;



import javax.annotation.Resource;
import javax.ejb.Stateless;

import de.fh_dortmund.cw.autofinanzierung.server.beans.interfaces.LoanCalculatorLocal;
import de.fh_dortmund.cw.autofinanzierung.server.beans.interfaces.LoanCalculatorRemote;

@Stateless
public class LoanCalculatorBean implements LoanCalculatorLocal, LoanCalculatorRemote {
	@Resource(name="interestRate")
	private double interestRate;

	@Override
	public double computeGrossLoanAmount(double price, double firstInstalment, int paymentTerm) {
		double netLoan= price -firstInstalment;
		double remaining=netLoan;
		double netRate= netLoan/paymentTerm;
		double interest=0;
		for(int i=0;i<paymentTerm;i++) {
			System.out.println("remaining: "+remaining+"month"+i);
			System.out.println("interest:"+interest+"month"+i);
			interest+=remaining*interestRate/12;
			remaining-= netRate;
		}
		
	
		return netLoan+interest;
	}

	@Override
	public double computeMonthlyPayment(double price, double firstInstalment, int paymentTerm) {
		return computeGrossLoanAmount(price, firstInstalment, paymentTerm)/paymentTerm;
	}

	@Override
	public double computeNetLoanAmount(double price, double firstInstalment) {
		return price-firstInstalment;
	}

	@Override
	public double getInterestRate() {

		return interestRate;
	}

}
