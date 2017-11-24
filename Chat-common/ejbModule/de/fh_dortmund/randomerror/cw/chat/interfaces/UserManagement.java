package de.fh_dortmund.randomerror.cw.chat.interfaces;

import java.util.ArrayList;

import de.fh_dortmund.randomerror.cw.chat.entity.User;
import de.fh_dortmund.randomerror.cw.chat.exceptions.UserException;

public interface UserManagement {

	public User login(String username, String password) throws UserException;

	public User register(String username, String password);

	public void logout(User user);

	public void delete(User user);

	public User changePassword(User user, String newPassword);

	public ArrayList<String> getOnlineUsers();

	public int getNumberOfregisteredUsers();

	public int getNumberOfOnlineUsers();
}
