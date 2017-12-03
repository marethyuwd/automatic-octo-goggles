package de.fh_dortmund.randomerror.cw.chat.entities;

import java.io.Serializable;

import javax.enterprise.util.Nonbinding;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MappedSuperclass;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import de.fh_dortmund.inf.cw.chat.server.entities.UserStatistic;

@NamedQueries({
		@NamedQuery(name = "User.findByUsername", query = "SELECT user FROM User user WHERE user.username = :username"),
		@NamedQuery(name = "User.findRegisteredUser", query = "SELECT user FROM User user"),
		@NamedQuery(name = "User.countRegisteredUser", query = "SELECT COUNT(1) FROM User user"),
		@NamedQuery(name = "User.findOnlineUsers", query = "SELECT user FROM User user WHERE user.online = true"),
		@NamedQuery(name = "User.countOnlineUsers", query = "SELECT COUNT(1) FROM User user WHERE user.online = true") })
@Entity
@Table(name = "CHAT_USER")
public class User implements Serializable {
	@Transient
	private static final long serialVersionUID = 601501416429316308L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional=false)
	private long id;
	
	@Basic(optional = false)
	private String username;

	@Basic(optional = false)
	private String password;

	@Basic(optional = false)
	private boolean online;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "stat_id", unique = true, nullable = false)
	private UserStatistic statistic;

	public User(){
		
	}
	public boolean isOnline() {
		return online;
	}

	public void setOnline(boolean online) {
		this.online = online;
	}

	public long getId() {
		return id;
	}

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
