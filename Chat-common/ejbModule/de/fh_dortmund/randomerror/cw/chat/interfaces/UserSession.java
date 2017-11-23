package de.fh_dortmund.randomerror.cw.chat.interfaces;

public interface UserSession {
	public void login(String username);
	public void logout();
	public void register();
	
}
