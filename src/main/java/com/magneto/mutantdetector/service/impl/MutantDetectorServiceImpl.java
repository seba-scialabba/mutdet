package com.magneto.mutantdetector.service.impl;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.magneto.mutantdetector.bean.DnaBean;
import com.magneto.mutantdetector.bean.StatsBean;
import com.magneto.mutantdetector.dao.DnaDao;
import com.magneto.mutantdetector.model.Dna;
import com.magneto.mutantdetector.model.Stats;
import com.magneto.mutantdetector.service.MutantDetectorService;

@Service
@Slf4j
public class MutantDetectorServiceImpl implements MutantDetectorService {
	private Gson gsonBuilder = new GsonBuilder().create();
	@Autowired
	private DnaDao dnaDao;

	@Override
	public boolean isMutant(DnaBean dnaBean) {
		log.info("Validanting DNA sequence {}...", dnaBean);
		dnaBean.validateSequence();
		log.info("DNA sequence {} is valid. Checking if it's a mutant...", dnaBean);
		boolean isMutant = dnaBean.isMutant();
		log.info("Is sequence {} mutant? {}", dnaBean, isMutant);
		saveDna(dnaBean, isMutant);
		return isMutant;
	}

	@Override
	@Transactional(readOnly = true)
	public StatsBean readStats() {
		log.info("Retrieving DNA stats...");
		Stats stats = dnaDao.getStats();
		StatsBean statsBean = new StatsBean(stats.getMutantCount(), stats.getHumanCount());
		log.info("DNA stats: [mutant_count = {}, human_count = {}, ratio = {}", statsBean.getMutantDnaCount(), statsBean.getHumanDnaCount(), statsBean.getRatio());
		return statsBean;
	}

	@Transactional
	protected void saveDna(DnaBean dnaBean, boolean isMutant) {
		Dna dna = new Dna(gsonBuilder.toJson(dnaBean.getSequence()), isMutant);
		if (CollectionUtils.isEmpty(dnaDao.findByDna(dna.getDna()))) {
			dnaDao.save(dna);
		}
	}
}
