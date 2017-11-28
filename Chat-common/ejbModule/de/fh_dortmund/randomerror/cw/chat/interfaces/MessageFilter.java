package de.fh_dortmund.randomerror.cw.chat.interfaces;

import javax.jms.JMSException;
import javax.jms.TextMessage;

public interface MessageFilter {
	public String filter(TextMessage txtmsg) throws JMSException;
}
