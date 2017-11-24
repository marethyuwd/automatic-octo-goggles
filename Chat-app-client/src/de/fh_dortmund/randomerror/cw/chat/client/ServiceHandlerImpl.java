package de.fh_dortmund.randomerror.cw.chat.client;

import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import de.fh_dortmund.inf.cw.chat.client.shared.ServiceHandler;
import de.fh_dortmund.inf.cw.chat.client.shared.UserSessionHandler;
import de.fh_dortmund.randomerror.cw.chat.interfaces.UserManagementRemote;
import de.fh_dortmund.randomerror.cw.chat.interfaces.UserSessionRemote;

public class ServiceHandlerImpl extends ServiceHandler implements UserSessionHandler {

	private Context ctx;
	private static ServiceHandlerImpl instance;

	private UserManagementRemote userManagement;
	private UserSessionRemote session;

	private ServiceHandlerImpl() {
		try {
			ctx = new InitialContext();
			session = (UserSessionRemote) ctx.lookup(
					"java:global/Chat-ear/Chat-ejb/UserSessionBean!de.fh_dortmund.randomerror.cw.chat.interfaces.UserSessionRemote");

			userManagement = (UserManagementRemote) ctx.lookup(
					"java:global/Chat-ear/Chat-ejb/UserManagementBean!de.fh_dortmund.randomerror.cw.chat.interfaces.UserManagementRemote");

		} catch (NamingException e) {
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
	}

	@Override
	public void logout() throws Exception {
		session.logout();
	}

	@Override
	public void register(String userName, String password) throws Exception {
		userManagement.register(userName, password);

	}

}
