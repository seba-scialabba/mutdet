package com.magneto.mutantdetector.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.magneto.mutantdetector.model.Dna;
import com.magneto.mutantdetector.model.Stats;

/**
 * DNA dao definition
 */
public interface DnaDao extends CrudRepository<Dna, Long> {

	@Query("select max((select count(dna.id) from Dna dna where is_mutant = true)) as mutantCount," +
		"max((select count(dna.id) from Dna dna where is_mutant = false)) as humanCount " +
		"from Dna")
	Stats getStats();

	/**
	 * Finds a DNA by its sequence
	 *
	 * @param dnaSequence
	 * @return found DNAs
	 */
	List<Dna> findByDna(String dnaSequence);
}
