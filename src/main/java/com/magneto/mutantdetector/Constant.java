package com.magneto.mutantdetector;

import java.util.Set;

import com.google.common.collect.ImmutableSet;

public final class Constant {
	public static final String VALID_DNA_SEQUENCE_REGEX = "^[ATCG]{%d}$";
	public static final int MINIMUM_REPEATED_MUTANT_SEQUENCES = 2;
	public static Set<String> MUTANT_SEQUENCE_SIGNATURES = ImmutableSet.of("AAAA", "TTTT", "CCCC", "GGGG");
}
