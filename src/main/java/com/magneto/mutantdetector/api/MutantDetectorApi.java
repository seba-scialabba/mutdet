package com.magneto.mutantdetector.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.magneto.mutantdetector.ws.DnaWS;
import com.magneto.mutantdetector.ws.StatsWS;

@RestController
public interface MutantDetectorApi {

	/**
	 * Calculates if a given DNA comes from a mutant
	 *
	 * @param dna
	 * @return HTTP 200 if mutant, HTTP 403 otherwise
	 */
	@PostMapping("/mutant")
	ResponseEntity isMutant(@RequestBody DnaWS dna);

	/**
	 * Returns the statistics for the received requests:
	 * - count of human DNAs found
	 * - count of mutant DNAs found
	 * - ratio mutant/human
	 *
	 * @return statistics
	 */
	@GetMapping("/stats")
	StatsWS getStats();
}
