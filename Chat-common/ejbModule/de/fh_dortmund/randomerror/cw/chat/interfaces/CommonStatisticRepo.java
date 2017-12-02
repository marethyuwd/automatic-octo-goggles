package de.fh_dortmund.randomerror.cw.chat.interfaces;

import java.util.List;

import de.fh_dortmund.inf.cw.chat.server.entities.CommonStatistic;

public interface CommonStatisticRepo {

	public CommonStatistic findLast();

	public List<CommonStatistic> findAll();

	public CommonStatistic save(CommonStatistic statistic);

}
