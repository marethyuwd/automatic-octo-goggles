package de.fh_dortmund.randomerror.cw.chat.server.beans;

import java.util.Date;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.ObjectMessage;
import javax.jms.Topic;

import de.fh_dortmund.inf.cw.chat.server.shared.ChatMessage;
import de.fh_dortmund.inf.cw.chat.server.shared.ChatMessageType;
import de.fh_dortmund.randomerror.cw.chat.interfaces.BroadcastLocal;
import de.fh_dortmund.randomerror.cw.chat.interfaces.BroadcastRemote;

@Stateless
public class BroadcastBean implements BroadcastLocal, BroadcastRemote {
	@Inject
	private JMSContext ctx;
	@Resource(lookup = "java:global/jms/ChatTopic")
	private Topic chatTopic;

	@Resource(lookup = "java:global/jms/DisconnectTopic")
	private Topic disconnectTopic;

	@Override
	public void message(ChatMessage msg) {
		try {
			ObjectMessage omsg = ctx.createObjectMessage();
			omsg.setObject(msg);
			ctx.createProducer().send(chatTopic, omsg);

		} catch (JMSException e) {

			e.printStackTrace();
		}

	}

	@Override
	public void disconnect(String username) {
		try {
			ChatMessage msg = new ChatMessage(ChatMessageType.DISCONNECT, username, "", new Date());
			ObjectMessage omsg = ctx.createObjectMessage(msg);
			omsg.setStringProperty("USER", username);
			ctx.createProducer().send(disconnectTopic, omsg);
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
