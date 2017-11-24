package de.fh_dortmund.randomerror.cw.chat.interfaces;

import de.fh_dortmund.randomerror.cw.chat.exceptions.UserException;

public interface UserSession {
	public String getUsername();

	public void logout();

	public void disconnect();

	public void delete(String password);

	public void changePassword(String oldPassword, String newPassword);

	void login(String username, String password) throws UserException;

}
