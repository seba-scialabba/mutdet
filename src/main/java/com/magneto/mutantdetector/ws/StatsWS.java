package com.magneto.mutantdetector.ws;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.fasterxml.jackson.annotation.JsonProperty;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatsWS {
	@JsonProperty("count_mutant_dna")
	private int mutantDnaCount;
	@JsonProperty("count_human_dna")
	private int humanDnaCount;
	private float ratio;
}
