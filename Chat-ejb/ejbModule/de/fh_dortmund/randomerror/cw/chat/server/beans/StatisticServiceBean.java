package de.fh_dortmund.randomerror.cw.chat.server.beans;

import java.util.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedList;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.Stateless;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.ejb.TimerConfig;
import javax.ejb.TimerService;

import de.fh_dortmund.inf.cw.chat.server.entities.CommonStatistic;
import de.fh_dortmund.inf.cw.chat.server.entities.UserStatistic;
import de.fh_dortmund.randomerror.cw.chat.entities.User;
import de.fh_dortmund.randomerror.cw.chat.interfaces.BroadcastLocal;
import de.fh_dortmund.randomerror.cw.chat.interfaces.CommonStatisticRepoLocal;
import de.fh_dortmund.randomerror.cw.chat.interfaces.StatisticServiceLocal;
import de.fh_dortmund.randomerror.cw.chat.interfaces.StatisticServiceRemote;

@Stateless
@Startup
public class StatisticServiceBean implements StatisticServiceLocal, StatisticServiceRemote {
	@Resource
	private TimerService timerService;
	private static final String COMMON_STATISTIC_TIMER = "COMMON_STATISTIC_TIMER";
	@EJB
	private CommonStatisticRepoLocal commonStatistic;
	@EJB
	private BroadcastLocal broadcast;

	

	@Override
	public void createTimer() {
		boolean initTimer = true;
		for (Timer timer : timerService.getTimers()) {
			if (COMMON_STATISTIC_TIMER.equals(timer.getInfo())) {
				initTimer = false;
			}
		}
		if (initTimer) {
			TimerConfig timerConfig = new TimerConfig();
			timerConfig.setInfo(COMMON_STATISTIC_TIMER);
			timerConfig.setPersistent(true);


			timerService.createIntervalTimer(0, 1000 * 60 * 60, timerConfig);
		}
	}

	@Schedule(minute="*",hour = "*", persistent = false)
	private void fullHourStatistic() {
		createStatistic("Stunde");
	}
	private void createStatistic(String interval) {
		
		CommonStatistic current= commonStatistic.findLast();
		current.setEndDate(new Date());
		broadcast.statistic(current,interval);

		CommonStatistic newStat = new CommonStatistic();
		newStat.setStartingDate(new Date());

		commonStatistic.save(newStat);
	}

	@Timeout
	private void timeOut() {
		if (COMMON_STATISTIC_TIMER.equals(timer.getInfo())) {
			createStatistic("halben Stunde");
			
		}
	}

	@Override
	public void createFirstStatistic() {
		CommonStatistic newStat = new CommonStatistic();
		Date d=new Date();
		newStat.setStartingDate(d);
		commonStatistic.save(newStat);
		
	}

}
