package de.fh_dortmund.inf.cw.chat.server.entities;

import java.util.Date;

public class CommonStatistic extends Statistic {

	private static final long serialVersionUID = 4618557313957792003L;
	private Date startingDate;
	private Date endDate;

	public Date getStartingDate() {
		return startingDate;
	}

	public void setStartingDate(Date startingDate) {
		this.startingDate = startingDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public void incrementLogins() {
		setLogins(getLogins()+1);
	}
	public void incrementLogouts() {
		setLogouts(getLogouts()+1);
	}
	public void incrementMessages() {
		setMessages(getMessages()+1);
	}

}
