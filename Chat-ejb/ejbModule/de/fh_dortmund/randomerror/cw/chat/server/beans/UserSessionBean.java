package de.fh_dortmund.randomerror.cw.chat.server.beans;

import javax.ejb.EJB;
import javax.ejb.Remove;
import javax.ejb.Stateful;

import de.fh_dortmund.randomerror.cw.chat.entity.User;
import de.fh_dortmund.randomerror.cw.chat.exceptions.UserException;
import de.fh_dortmund.randomerror.cw.chat.interfaces.HashingServiceLocal;
import de.fh_dortmund.randomerror.cw.chat.interfaces.UserManagementLocal;
import de.fh_dortmund.randomerror.cw.chat.interfaces.UserSessionLocal;
import de.fh_dortmund.randomerror.cw.chat.interfaces.UserSessionRemote;

@Stateful
public class UserSessionBean implements UserSessionLocal, UserSessionRemote {

	private User user;
	@EJB
	private UserManagementLocal userManagement;
	@EJB
	private HashingServiceLocal hashingService;

	@Override
	public String getUsername() {
		return user.getUsername();
	}

	@Override
	public void login(String username, String password) throws UserException {
		user = userManagement.login(username, password);

	}

	@Override
	@Remove
	public void logout() {
		userManagement.logout(user);
	}

	@Override
	@Remove
	public void disconnect() {
		userManagement.logout(user);
	}

	@Override
	public void delete(String password) {
		if (hashingService.generateHash(password) == user.getPassword()) {
			userManagement.delete(user);
		}
	}

	@Override
	public void changePassword(String oldPassword, String newPassword) {
		if (hashingService.generateHash(oldPassword) == user.getPassword()) {
			userManagement.changePassword(user, newPassword);
		}
	}

}
