package de.fh_dortmund.randomerror.cw.chat.server.beans;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import de.fh_dortmund.inf.cw.chat.server.entities.CommonStatistic;
import de.fh_dortmund.randomerror.cw.chat.interfaces.CommonStatisticRepoLocal;
import de.fh_dortmund.randomerror.cw.chat.interfaces.CommonStatisticRepoRemote;

@Singleton
public class CommonStatisticRepoBean implements CommonStatisticRepoLocal, CommonStatisticRepoRemote {

	private LinkedList<CommonStatistic> statistics;
	
	@PersistenceContext
	private EntityManager manager;


	@Override
	public CommonStatistic findLast() {
		
		TypedQuery<CommonStatistic> q=manager.createNamedQuery("CommonStatistic.findLast",CommonStatistic.class);
		try {
			return q.getSingleResult();			
		}catch(NoResultException e) {
			return null;
		}
	}
	@Override
	public List<CommonStatistic> findAll() {
		TypedQuery<CommonStatistic> q=manager.createNamedQuery("CommonStatistic.findAll",CommonStatistic.class);
		return q.getResultList();
	}

	@Override
	public CommonStatistic save(CommonStatistic statistic) {
		manager.merge(statistic);
		return statistic;
	}


}
