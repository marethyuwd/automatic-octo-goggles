package de.fh_dortmund.randomerror.cw.chat.interfaces;

import de.fh_dortmund.inf.cw.chat.server.entities.CommonStatistic;
import de.fh_dortmund.inf.cw.chat.server.shared.ChatMessage;

public interface Broadcast {

	public void message(ChatMessage msg);

	public void disconnect(String username);

	void statistic(CommonStatistic statistic, String interval);
}
