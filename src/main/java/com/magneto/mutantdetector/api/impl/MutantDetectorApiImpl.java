package com.magneto.mutantdetector.api.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.magneto.mutantdetector.api.MutantDetectorApi;
import com.magneto.mutantdetector.bean.DnaBean;
import com.magneto.mutantdetector.bean.StatsBean;
import com.magneto.mutantdetector.service.MutantDetectorService;
import com.magneto.mutantdetector.ws.DnaWS;
import com.magneto.mutantdetector.ws.StatsWS;

@Service
public class MutantDetectorApiImpl implements MutantDetectorApi {
	@Autowired
	private MutantDetectorService mutantDetectorService;

	@Override
	public ResponseEntity isMutant(@RequestBody DnaWS dnaWS) {
		DnaBean dnaBean = new DnaBean(dnaWS.getDna());
		if (mutantDetectorService.isMutant(dnaBean)) {
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
	}

	@Override
	public StatsWS getStats() {
		StatsBean stats = mutantDetectorService.readStats();
		return new StatsWS(stats.getMutantDnaCount(), stats.getHumanDnaCount(), stats.getRatio());
	}
}
