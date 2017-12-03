package de.fh_dortmund.randomerror.cw.chat.client;

import static org.junit.Assert.assertEquals;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;


import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Categories.ExcludeCategory;
import org.junit.rules.ExpectedException;

import de.fh_dortmund.randomerror.cw.chat.exceptions.UserException;
import de.fh_dortmund.randomerror.cw.chat.interfaces.UserManagementRemote;

public class UserSessionHandlerTest {

	private ServiceHandlerImpl service;
	private static UserManagementRemote database;

	@Rule
	public ExpectedException expectedEx = ExpectedException.none();

	@BeforeClass
	public static void initialize() {
		Context ctx;
		try {
			ctx = new InitialContext();
			database = (UserManagementRemote) ctx.lookup(
					"java:global/Chat-ear/chat-ejb/UserManagementBean!de.fh_dortmund.randomerror.cw.chat.interfaces.UserManagementRemote");
		} catch (NamingException e) {

			e.printStackTrace();
		}

	}

	@Before
	public void setup() throws Exception {
		database.deleteAll();
		service = new ServiceHandlerImpl();
		service.register("test", "test");
	}

	@After
	public void teardown() {
		service.disconnect();
	}

	@Test
	public void testLogin() throws Exception {
		service.login("test", "test");
		assertEquals(service.getNumberOfOnlineUsers(), 1);
	}

	@Test
	public void testLoginWrongUsername() throws Exception {
		expectedEx.expect(UserException.class);
		expectedEx.expectMessage("Unbekannter Nutzername");
		service.login("wrong", "test");

	}

	@Test
	public void testLoginWrongPassword() throws Exception {
		expectedEx.expect(UserException.class);
		expectedEx.expectMessage("falsches Passwort");
		service.login("test", "wrong");

	}

	@Test
	public void testRegister() throws Exception {
		service.register("username", "password");
		service.login("username", "password");
		assertEquals(service.getNumberOfRegisteredUsers(), 2);
	}

	@Test
	public void testUserAlreadyRegistered() throws Exception {
		expectedEx.expect(UserException.class);
		expectedEx.expectMessage("User bereits registriert");
		service.register("test", "test");
	}

	@Test
	public void changePassword() throws Exception {
		service.login("test", "test");
		service.changePassword("test", "newpassword");
		service.logout();

		expectedEx.expect(UserException.class);
		expectedEx.expectMessage("falsches Passwort");
		service.login("test", "test");
	}

	@Test
	public void changePasswordOldPasswordWrong() throws Exception {
		service.login("test", "test");

		expectedEx.expect(UserException.class);
		expectedEx.expectMessage("altes Passwort falsch");

		service.changePassword("test", "newpassword");
	}

	@Test(expected = Exception.class)
	public void testDelete() throws Exception {
		service.login("test", "test");
		service.delete("test");

	}

	@Test
	public void testDeleteWrongPassword() throws Exception {
		expectedEx.expect(UserException.class);
		expectedEx.expectMessage("falsches Passwort");
		service.login("test", "WRONG");
		service.delete("test");
		assertEquals(0, service.getNumberOfRegisteredUsers());
	}

	@Test
	public void testUsername() throws Exception {
		service.login("test", "test");
		assertEquals("test", service.getUserName());
	}

	@Test
	public void testLogout() throws Exception {
		service.login("test", "test");
		service.logout();
	}
}
