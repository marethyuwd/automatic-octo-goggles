package de.fh_dortmund.randomerror.cw.chat.server.beans;

import java.util.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
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
	@EJB
	private CommonStatisticRepoLocal statistics;
	@EJB
	private BroadcastLocal broadcast;

	private static final String COMMON_STATISTIC_TIMER = "COMMON_STATISTIC_TIMER";

	@Override
	public void createTimer() {
		for(Timer timer:timerService.getTimers()) {
			if(COMMON_STATISTIC_TIMER.equals(timer.getInfo())) 
				return;
			}
			LocalDateTime start=LocalDateTime.now();
			start.plusMinutes(30).withSecond(0);
			
			TimerConfig conf=new TimerConfig();
			conf.setInfo(COMMON_STATISTIC_TIMER);
			
			timerService.createIntervalTimer(convertToDate(start), 1000*60, conf);
		
		
	}

	private Date convertToDate(LocalDateTime localDateTime) {
		return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
	}

	@Override
	public void createFirstCommonStatistic() {
		if(statistics.findLast()==null) {
			CommonStatistic cs=new CommonStatistic();
			 LocalDateTime startDate=LocalDateTime.now().withMinute(0).withSecond(0);
			 LocalDateTime endDate=startDate.plusHours(1).minusSeconds(1);
			 
			 Date start=convertToDate(startDate);
			 Date end =convertToDate(endDate);
			 
			 cs.setStartingDate(start);
			 cs.setEndDate(end);
			 
			 statistics.save(cs);
			 
		}
	}

	@Override
	@Schedule(hour="*")
	public void createFullHourStatistic() {
		sendStatistic("Stunde");
		
		CommonStatistic cs=new CommonStatistic();
		 LocalDateTime startDate=LocalDateTime.now().withMinute(0).withSecond(0).withSecond(0);
		 LocalDateTime endDate=startDate.plusHours(1).minusSeconds(1);
		 
		 Date start=convertToDate(startDate);
		 Date end =convertToDate(endDate);
		 
		 cs.setStartingDate(start);
		 cs.setEndDate(end);
		 statistics.save(cs);
		
		
		
	}

	@Override
	@Timeout
	public void timeOut() {
		for(Timer timer:timerService.getTimers()) {
			if(COMMON_STATISTIC_TIMER.equals(timer.getInfo())) {
				sendStatistic("halben Stunde");
			}
		}
		
	}

	@Override
	public void sendStatistic(String statType) {
		createFirstCommonStatistic();
		broadcast.statistic(statistics.findLast(), statType);
		
	}



}
