package de.fh_dortmund.randomerror.cw.chat.entities;

import java.io.Serializable;

import de.fh_dortmund.inf.cw.chat.server.entities.UserStatistic;

public class User implements Serializable {

	private static final long serialVersionUID = 601501416429316308L;
	private String username;
	private String password;

	private UserStatistic statistic;

	public UserStatistic getStatistic() {
		return statistic;
	}

	public void setStatistic(UserStatistic statistic) {
		this.statistic = statistic;
	}

	public User(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
