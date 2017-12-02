package de.fh_dortmund.randomerror.cw.chat.server.beans;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import javax.jms.JMSContext;
import javax.jms.Message;
import javax.jms.Topic;

import de.fh_dortmund.inf.cw.chat.server.entities.UserStatistic;
import de.fh_dortmund.inf.cw.chat.server.shared.ChatMessage;
import de.fh_dortmund.inf.cw.chat.server.shared.ChatMessageType;
import de.fh_dortmund.randomerror.cw.chat.entities.User;
import de.fh_dortmund.randomerror.cw.chat.exceptions.UserException;
import de.fh_dortmund.randomerror.cw.chat.interfaces.Broadcast;
import de.fh_dortmund.randomerror.cw.chat.interfaces.BroadcastLocal;
import de.fh_dortmund.randomerror.cw.chat.interfaces.CommonStatisticRepoLocal;
import de.fh_dortmund.randomerror.cw.chat.interfaces.HashingServiceLocal;
import de.fh_dortmund.randomerror.cw.chat.interfaces.StatisticServiceLocal;
import de.fh_dortmund.randomerror.cw.chat.interfaces.UserManagementLocal;
import de.fh_dortmund.randomerror.cw.chat.interfaces.UserManagementRemote;

@Singleton
@Startup
public class UserManagementBean implements UserManagementLocal, UserManagementRemote {

	private HashMap<String, User> registeredUser;
	private ArrayList<String> onlineUsers;

	@EJB
	private CommonStatisticRepoLocal commonStatisticRepo;
	@EJB
	private HashingServiceLocal hashingService;
	@EJB
	private BroadcastLocal broadcast;
	@EJB
	private StatisticServiceLocal statistic;

	@PostConstruct
	private void init() {
		registeredUser = new HashMap<>();
		onlineUsers = new ArrayList<>();
	}

	@Override
	public User login(String username, String password) throws UserException {

		User u = getUser(username);
		if (!checkPassword(username, password)) {
			throw new UserException("falsches Passwort");
		}
		if(onlineUsers.isEmpty())
				statistic.createTimer();
				statistic.createFirstStatistic();
		if (onlineUsers.contains(username)) {
			broadcast.disconnect(username);
			return u;
		} else {
			onlineUsers.add(username);
			setLastLogin(u, new Date());
			incrementUserLoginStatistic(u);

			broadcast.message(new ChatMessage(ChatMessageType.LOGIN, username, "", new Date()));
			return u;
		}

	}

	private void setLastLogin(User u, Date date) {
		u.getStatistic().setLastLogin(date);
		registeredUser.put(u.getUsername(), u);

	}

	private User getUser(String username) throws UserException {
		User u = registeredUser.get(username);
		if (u == null)
			throw new UserException("Unbekannter Nutzername");
		return u;
	}

	private boolean checkPassword(String username, String password) {
		String hash = hashingService.generateHash(password);
		return registeredUser.get(username).getPassword().equals(hash);
	}

	@Override
	public void register(String username, String password) {
		if (registeredUser.containsKey(username)) {
			try {
				throw new UserException("User already registered");
			} catch (UserException e) {
				e.printStackTrace();
			}
		}
		User user = new User(username, hashingService.generateHash(password));
		user.setStatistic(new UserStatistic());
		registeredUser.put(username, user);
		broadcast.message(new ChatMessage(ChatMessageType.REGISTER, username, "", new Date()));

	}

	@Override
	public void logout(User user) {
		onlineUsers.remove(user.getUsername());
		incrementUserLogoutStatistic(user);
		broadcast.message(new ChatMessage(ChatMessageType.LOGOUT, user.getUsername(), "", new Date()));
	}

	@Override
	public void delete(User user) {
		registeredUser.remove(user.getUsername());
	}

	@Override
	public User changePassword(User user, String newPassword) {
		user.setPassword(hashingService.generateHash(newPassword));
		registeredUser.put(user.getUsername(), user);
		return user;
	}

	@Override
	public ArrayList<String> getOnlineUsers() {

		return onlineUsers;
	}

	@Override
	public int getNumberOfregisteredUsers() {
		return registeredUser.keySet().size();
	}

	@Override
	public int getNumberOfOnlineUsers() {
		return onlineUsers.size();
	}

	@Override
	public void incrementUserMessageStatistic(String username) {
		try {
			User u = getUser(username);
			UserStatistic uStat = u.getStatistic();
			uStat.setMessages(uStat.getMessages() + 1);
			registeredUser.put(username, u);
		} catch (UserException e) {

			e.printStackTrace();
		}

	}

	@Override
	public void incrementUserLoginStatistic(User u) {
		UserStatistic uStat = u.getStatistic();
		uStat.setLogins(uStat.getLogins() + 1);
		registeredUser.put(u.getUsername(), u);

	}

	@Override
	public void incrementUserLogoutStatistic(User u) {
		UserStatistic uStat = u.getStatistic();
		uStat.setLogouts(uStat.getLogouts() + 1);
		registeredUser.put(u.getUsername(), u);

	}

}
