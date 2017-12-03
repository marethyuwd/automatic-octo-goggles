package de.fh_dortmund.randomerror.cw.chat.interfaces;

public interface StatisticService {

	public void createTimer();
	
	public void createFirstCommonStatistic();
	
	public void createFullHourStatistic();
	
	public void timeOut();
	
	void sendStatistic(String statType);

}
