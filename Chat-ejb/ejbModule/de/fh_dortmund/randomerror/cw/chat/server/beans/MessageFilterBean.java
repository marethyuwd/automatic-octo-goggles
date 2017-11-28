package de.fh_dortmund.randomerror.cw.chat.server.beans;

import java.util.LinkedList;
import java.util.List;

import javax.ejb.Stateless;
import javax.jms.JMSException;
import javax.jms.TextMessage;

import de.fh_dortmund.randomerror.cw.chat.interfaces.MessageFilterLocal;
import de.fh_dortmund.randomerror.cw.chat.interfaces.MessageFilterRemote;

@Stateless
public class MessageFilterBean implements MessageFilterLocal, MessageFilterRemote {
	
	private List<String> blacklist=new LinkedList<String>(){{
		add("lombok");
		add("spring");
		add("REST");
		add("duck");
	}};

	@Override
	public String filter(TextMessage txtmsg) throws JMSException {
		
		String txt=txtmsg.getText().toLowerCase();
		for(String entry:blacklist) {
			txt=txt.replace(entry.toLowerCase(),"****");
		}
		return txt;
	}

}
