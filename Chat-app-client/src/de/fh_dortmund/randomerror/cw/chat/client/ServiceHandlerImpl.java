package de.fh_dortmund.randomerror.cw.chat.client;

import java.util.Date;
import java.util.List;

import javax.jms.ConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import de.fh_dortmund.inf.cw.chat.client.shared.ChatMessageHandler;
import de.fh_dortmund.inf.cw.chat.client.shared.ServiceHandler;
import de.fh_dortmund.inf.cw.chat.client.shared.UserSessionHandler;
import de.fh_dortmund.inf.cw.chat.server.shared.ChatMessage;
import de.fh_dortmund.inf.cw.chat.server.shared.ChatMessageType;
import de.fh_dortmund.randomerror.cw.chat.interfaces.UserManagementRemote;
import de.fh_dortmund.randomerror.cw.chat.interfaces.UserSessionRemote;

public class ServiceHandlerImpl extends ServiceHandler
		implements UserSessionHandler, ChatMessageHandler, MessageListener {

	private Context ctx;
	private static ServiceHandlerImpl instance;

	private UserManagementRemote userManagement;
	private UserSessionRemote session;

	private Queue chatQueue;
	private Topic chatTopic;
	private Topic disconnectTopic;
	private JMSContext jmsContext;

	private ServiceHandlerImpl() {
		try {
			ctx = new InitialContext();
			session = (UserSessionRemote) ctx.lookup(
					"java:global/Chat-ear/chat-ejb/UserSessionBean!de.fh_dortmund.randomerror.cw.chat.interfaces.UserSessionRemote");

			userManagement = (UserManagementRemote) ctx.lookup(
					"java:global/Chat-ear/chat-ejb/UserManagementBean!de.fh_dortmund.randomerror.cw.chat.interfaces.UserManagementRemote");

			jmsInit();

		} catch (NamingException e) {
			e.printStackTrace();
		}

	}

	private void jmsInit() {
		try {
			ConnectionFactory conFac = (ConnectionFactory) ctx.lookup("java:comp/DefaultJMSConnectionFactory");
			chatQueue = (Queue) ctx.lookup("java:global/jms/ChatQueue");
			chatTopic = (Topic) ctx.lookup("java:global/jms/ChatTopic");
			disconnectTopic=(Topic) ctx.lookup("java:global/jms/DisconnectTopic");
			
			jmsContext = conFac.createContext();
			jmsContext.createConsumer(chatTopic).setMessageListener(this);
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static ServiceHandlerImpl getInstance() {
		if (instance == null) {
			instance = new ServiceHandlerImpl();
		}
		return instance;
	}

	@Override
	public void changePassword(String oldPassword, String newPassword) throws Exception {
		session.changePassword(oldPassword, newPassword);

	}

	@Override
	public void delete(String password) throws Exception {
		session.delete(password);

	}

	@Override
	public void disconnect() {
		session.disconnect();

	}

	@Override
	public int getNumberOfOnlineUsers() {

		return userManagement.getNumberOfOnlineUsers();
	}

	@Override
	public int getNumberOfRegisteredUsers() {

		return userManagement.getNumberOfregisteredUsers();
	}

	@Override
	public List<String> getOnlineUsers() {

		return userManagement.getOnlineUsers();
	}

	@Override
	public String getUserName() {
		return session.getUsername();
	}

	@Override
	public void login(String username, String password) throws Exception {
		
		session.login(username, password);
		jmsContext.createConsumer(disconnectTopic).setMessageListener(this);
	}

	@Override
	public void logout() throws Exception {
		session.logout();
	}

	@Override
	public void register(String userName, String password) throws Exception {
		userManagement.register(userName, password);

	}

	@Override
	public void sendChatMessage(String text) {
		TextMessage txtMsg=jmsContext.createTextMessage(text);
		try {
			txtMsg.setStringProperty("USER", getUserName());
			jmsContext.createProducer().send(chatQueue,txtMsg);
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void onMessage(Message message) {
		ObjectMessage objectMessage = (ObjectMessage) message;
		ChatMessage chatMsg;
		try {
			chatMsg = (ChatMessage) objectMessage.getObject();
			System.out.println("message received of Type: " + chatMsg.getType());
			setChanged();
			notifyObservers(chatMsg);

		} catch (JMSException e) {
			e.printStackTrace();
		}

	}

}
