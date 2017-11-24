package de.fh_dortmund.randomerror.cw.chat.server.beans;

import java.util.ArrayList;
import java.util.HashMap;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import de.fh_dortmund.randomerror.cw.chat.entity.User;
import de.fh_dortmund.randomerror.cw.chat.exceptions.UserException;
import de.fh_dortmund.randomerror.cw.chat.interfaces.HashingServiceLocal;
import de.fh_dortmund.randomerror.cw.chat.interfaces.UserManagementLocal;
import de.fh_dortmund.randomerror.cw.chat.interfaces.UserManagementRemote;

@Singleton
@Startup
public class UserManagementBean implements UserManagementLocal, UserManagementRemote {

	private HashMap<String, User> registeredUser;
	private ArrayList<String> onlineUsers;

	@EJB
	private HashingServiceLocal hashingService;

	@PostConstruct
	private void init() {
		registeredUser = new HashMap<>();
		onlineUsers = new ArrayList<>();
	}

	@Override
	public User login(String username, String password) throws UserException {
		password = hashingService.generateHash(password);
		if (registeredUser.containsKey(username)) {
			if (registeredUser.get(username).getPassword().equals(password)) {
				onlineUsers.add(username);
				return registeredUser.get(username);
			} else {
				throw new UserException("falsches Passwort");
			}
		} else {
			throw new UserException("Unbekannter Nutzername");
		}
	}

	@Override
	public User register(String username, String password) {
		if (registeredUser.containsKey(username)) {
			try {
				throw new UserException("User already registered");
			} catch (UserException e) {
				e.printStackTrace();
			}
		}
		User user = new User(username, hashingService.generateHash(password));
		registeredUser.put(username, user);
		return user;
	}

	@Override
	public void logout(User user) {
		onlineUsers.remove(user.getUsername());
	}

	@Override
	public void delete(User user) {
		registeredUser.remove(user.getUsername());
	}

	@Override
	public User changePassword(User user, String newPassword) {
		user.setPassword(newPassword);
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

}
