package de.fh_dortmund.randomerror.cw.chat.client;

import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;

import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import de.fh_dortmund.inf.cw.chat.server.entities.CommonStatistic;
import de.fh_dortmund.randomerror.cw.chat.interfaces.UserManagementRemote;

public class StatisticHandlerTest {
	
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
	public void testGetUserStatistic() throws Exception {
		service.login("test", "test");
		assertThat(service.getUserStatistic().getLogins(),greaterThan(0));
	}
	@Test
	public void testGetCommonStatistic() throws Exception {
		service.login("test","test");
		List<CommonStatistic>l=service.getStatistics();
		assertFalse(l.isEmpty());
		
	}
	
}
