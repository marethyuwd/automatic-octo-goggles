package de.fh_dortmund.randomerror.cw.chat.interfaces;

import java.util.ArrayList;

import de.fh_dortmund.randomerror.cw.chat.entities.User;
import de.fh_dortmund.randomerror.cw.chat.exceptions.UserException;

public interface UserManagement {

	public User login(String username, String password) throws UserException;

	public void register(String username, String password);

	public void logout(User user);

	public void delete(User user);

	public User changePassword(User user, String newPassword);

	public ArrayList<String> getOnlineUsers();

	public int getNumberOfregisteredUsers();

	public int getNumberOfOnlineUsers();

	public void incrementUserMessageStatistic(String username);

	void incrementUserLogoutStatistic(User u);

	void incrementUserLoginStatistic(User u);
}
