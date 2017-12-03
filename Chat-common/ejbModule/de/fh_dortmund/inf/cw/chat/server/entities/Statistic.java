package de.fh_dortmund.inf.cw.chat.server.entities;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class Statistic implements Serializable{

	private static final long serialVersionUID = 2264298777801184541L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional=false)
	private long id;
	@Basic(optional=false)
	private int logins;
	@Basic(optional=false)
	private int logouts;
	@Basic(optional=false)
	private int messages;


	public Statistic(int logins, int logouts, int messages) {
		super();
		this.logins = logins;
		this.logouts = logouts;
		this.messages = messages;
	}

	public Statistic() {
		super();
		this.logins = 0;
		this.logouts = 0;
		this.messages = 0;
	}

	public int getLogins() {
		return logins;
	}

	public void setLogins(int logins) {
		this.logins = logins;
	}

	public int getLogouts() {
		return logouts;
	}

	public void setLogouts(int logouts) {
		this.logouts = logouts;
	}

	public int getMessages() {
		return messages;
	}

	public void setMessages(int messages) {
		this.messages = messages;
	}

}
