package de.fh_dortmund.inf.cw.chat.server.entities;

import java.util.Date;

public class UserStatistic extends Statistic{

	private static final long serialVersionUID = 9155603997970446277L;
	private Date lastLogin;

	public UserStatistic() {
		super();
		lastLogin=new Date();
	}
	public Date getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}

}
