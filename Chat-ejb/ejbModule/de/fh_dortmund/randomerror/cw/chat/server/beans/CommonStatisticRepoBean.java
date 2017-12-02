package de.fh_dortmund.randomerror.cw.chat.server.beans;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import de.fh_dortmund.inf.cw.chat.server.entities.CommonStatistic;
import de.fh_dortmund.randomerror.cw.chat.interfaces.CommonStatisticRepoLocal;
import de.fh_dortmund.randomerror.cw.chat.interfaces.CommonStatisticRepoRemote;

@Singleton
public class CommonStatisticRepoBean implements CommonStatisticRepoLocal, CommonStatisticRepoRemote {

	private LinkedList<CommonStatistic> statistics;

	@PostConstruct
	private void init() {
		statistics = new LinkedList<>();
	}

	@Override
	public CommonStatistic findLast() {
		
		return statistics.getLast();
	}
	@Override
	public List<CommonStatistic> findAll() {

		return new ArrayList<>(statistics);
	}

	@Override
	public CommonStatistic save(CommonStatistic statistic) {
		statistics.add(statistic);
		return statistics.getLast();
	}


}
