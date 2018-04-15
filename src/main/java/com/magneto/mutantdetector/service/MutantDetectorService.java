package com.magneto.mutantdetector.service;

import com.magneto.mutantdetector.bean.DnaBean;
import com.magneto.mutantdetector.bean.StatsBean;

public interface MutantDetectorService {

	/**
	 * Returns if the given DNA comes from a mutant
	 *
	 * @param dna
	 * @return true if it's a mutant DNA, false otherwise
	 */
	boolean isMutant(DnaBean dna);

	/**
	 * Return statistics regarding mutant vs human found DNAs
	 *
	 * @return statistics
	 */
	StatsBean readStats();
}
