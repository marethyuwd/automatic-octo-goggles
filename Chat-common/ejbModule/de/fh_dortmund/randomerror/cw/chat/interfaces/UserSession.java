package de.fh_dortmund.randomerror.cw.chat.interfaces;

import de.fh_dortmund.inf.cw.chat.server.entities.UserStatistic;
import de.fh_dortmund.randomerror.cw.chat.exceptions.UserException;

public interface UserSession {
	public String getUsername();

	public void logout();

	public void disconnect();

	public void delete(String password) throws UserException;

	public void changePassword(String oldPassword, String newPassword) throws UserException;

	public void login(String username, String password) throws UserException;

	public UserStatistic getUserStatistic();

}
