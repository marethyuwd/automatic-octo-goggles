package de.fh_dortmund.inf.cw.chat.server.entities;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@NamedQueries({
		@NamedQuery(name = "CommonStatistic.findAll", query = "SELECT stat FROM CommonStatistic stat WHERE stat.endDate IS NOT NULL"),
		@NamedQuery(name = "CommonStatistic.inOrder", query = "SELECT stat FROM CommonStatistic stat WHERE stat.endDate IS NOT NULL ORDER BY stat.startingDate"),
		@NamedQuery(name = "CommonStatistic.findLast", query = "SELECT stat FROM CommonStatistic stat WHERE stat.endDate IS NULL") })
@Entity
public class CommonStatistic extends Statistic {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	private long id;

	private static final long serialVersionUID = 4618557313957792003L;
	@Temporal(TemporalType.TIMESTAMP)
	@Basic(optional = false)
	private Date startingDate;
	@Temporal(TemporalType.TIMESTAMP)
	@Basic(optional = true)
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
		setLogins(getLogins() + 1);
	}

	public void incrementLogouts() {
		setLogouts(getLogouts() + 1);
	}

	public void incrementMessages() {
		setMessages(getMessages() + 1);
	}

}
