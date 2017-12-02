package de.fh_dortmund.randomerror.cw.chat.server.beans;

import java.util.Date;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import de.fh_dortmund.inf.cw.chat.server.shared.ChatMessage;
import de.fh_dortmund.inf.cw.chat.server.shared.ChatMessageType;
import de.fh_dortmund.randomerror.cw.chat.interfaces.BroadcastLocal;
import de.fh_dortmund.randomerror.cw.chat.interfaces.CommonStatisticRepoLocal;
import de.fh_dortmund.randomerror.cw.chat.interfaces.MessageFilterLocal;
import de.fh_dortmund.randomerror.cw.chat.interfaces.UserManagementLocal;

@MessageDriven(mappedName = "java:global/jms/ChatQueue", messageListenerInterface = MessageListener.class, activationConfig = {
		@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue") })
public class MessageProcessingBean implements MessageListener {

	@EJB
	private MessageFilterLocal filter;
	
	@EJB
	private BroadcastLocal broadcast;
	
	@EJB
	private CommonStatisticRepoLocal commonStatisticRepo;
	
	@EJB
	private UserManagementLocal userManagement;

	@Override
	public void onMessage(Message msg) {
		try {
			System.out.println("recieved message from user: " + msg.getStringProperty("USER"));
			TextMessage txtMsg = (TextMessage) msg;
			ChatMessage chatMsg = new ChatMessage(ChatMessageType.TEXT, txtMsg.getStringProperty("USER"),
					filter.filter(txtMsg), new Date());
			userManagement.incrementUserMessageStatistic(txtMsg.getStringProperty("USER"));
			broadcast.message(chatMsg);
			
		} catch (JMSException e) {
			e.printStackTrace();
		}

	}

}
