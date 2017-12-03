package de.fh_dortmund.randomerror.cw.chat.server.beans;

import javax.ejb.EJB;
import javax.ejb.Remove;
import javax.ejb.Stateful;

import de.fh_dortmund.inf.cw.chat.server.entities.UserStatistic;
import de.fh_dortmund.randomerror.cw.chat.entities.User;
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
		if (user != null)
			return user.getUsername();
		return null;
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
	public void delete(String password) throws UserException {
		if (hashingService.generateHash(password) == user.getPassword()) {
			userManagement.delete(user);
		}
		throw new UserException("falsches Passwort");
	}

	@Override
	public void changePassword(String oldPassword, String newPassword) throws UserException {


		if (hashingService.generateHash(oldPassword).equals(user.getPassword())) {
			System.out.println("oldhash: "+user.getPassword());
			user= userManagement.changePassword(user, newPassword);
			System.out.println("newhash: "+user.getPassword());
		}else {
			throw new UserException("altes Passwort falsch");
		}
	}

	@Override
	public UserStatistic getUserStatistic() {
		return user.getStatistic();
	}

}
