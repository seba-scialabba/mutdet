package com.magneto.mutantdetector.bean;

import static com.magneto.mutantdetector.Constant.MINIMUM_REPEATED_MUTANT_SEQUENCES;
import static com.magneto.mutantdetector.Constant.MUTANT_SEQUENCE_SIGNATURES;
import static com.magneto.mutantdetector.Constant.VALID_DNA_SEQUENCE_REGEX;

import java.util.Arrays;
import java.util.regex.Pattern;

import lombok.Value;

import org.springframework.util.StringUtils;

import com.magneto.mutantdetector.exception.InvalidDnaSequenceException;

/**
 * DNA sequence
 */
@Value
public class DnaBean {
	private String[] sequence;

	public DnaBean(String[] sequence) {
		this.sequence = Arrays.copyOf(sequence, sequence.length);
	}

	/**
	 * Validates the sequence and throws an exception if some of these rules are not followed:
	 *
	 * - Must have at least 4 bases (N >= 4)
	 * - Must be presented as a square matrix (NxN)
	 * - All components must be a valid base (A, T, C or G)
	 */
	public void validateSequence() {
		if (sequence.length < 4) {
			throw new InvalidDnaSequenceException();
		}
		Pattern sequenceValidFormat = Pattern.compile(String.format(VALID_DNA_SEQUENCE_REGEX, sequence.length));
		for (String part: sequence) {
			if (!sequenceValidFormat.matcher(part).matches()) {
				throw new InvalidDnaSequenceException();
			}
		}
	}

	/**
	 * Returns if the DNA comes from a mutant sample: must contain at least N occurrences of:
	 * "AAAA", "TTTT", "CCCC" or "GGGG"; than means four bases in a row horizontally, vertically
	 * or diagonally.
	 *
	 * @return true if the DNA comes from a mutant sample, false otherwise
	 */
	public boolean isMutant() {
		// Search horizontally
		int mutantSequencesCount = countHorizontalMutantSequenceSignatures();
		if (mutantSequencesCount >= MINIMUM_REPEATED_MUTANT_SEQUENCES) {
			return true;
		}

		// Search vertically
		mutantSequencesCount += countVerticalMutantSequenceSignatures(mutantSequencesCount);
		if (mutantSequencesCount >= MINIMUM_REPEATED_MUTANT_SEQUENCES) {
			return true;
		}

		// Search diagonally
		mutantSequencesCount += countDiagonalMutantSequenceSignatures(mutantSequencesCount);
		if (mutantSequencesCount >= MINIMUM_REPEATED_MUTANT_SEQUENCES) {
			return true;
		}
		return false;
	}

	/*
	 * Searches mutant sequences horizontally. If the minimum mutant sequences are found the process is stopped
	 * and the search finishes.
	 * Returns the count of mutant appearances (up to the minimum)
	 */
	private int countHorizontalMutantSequenceSignatures() {
		int mutantSequencesCount = 0;
		for (int index = 0; index < sequence.length && mutantSequencesCount < MINIMUM_REPEATED_MUTANT_SEQUENCES; index++) {
			mutantSequencesCount += countMutantSequenceSignaturesInDnaPart(sequence[index]);
		}
		return mutantSequencesCount;
	}

	/*
	 * Searches mutant sequences vertically (taking into account the mutant sequences already found).
	 * If the minimum mutant sequences are found the process is stopped and the search finishes (there's no need to
	 * keep generating DNA parts).
	 * Returns the count of mutant appearances (up to the minimum)
	 */
	private int countVerticalMutantSequenceSignatures(int currentMutantSequencesCount) {
		int mutantVerticalCount = 0;
		for (int index = 0; index < sequence.length && mutantVerticalCount + currentMutantSequencesCount < MINIMUM_REPEATED_MUTANT_SEQUENCES; index++) {
			String verticalSequence = new String();
			for (int j = 0; j < sequence.length; j++) {
				verticalSequence += sequence[j].charAt(index);
			}
			// Once the vertical part is obtained I look for mutant sequences before generating the next one
			mutantVerticalCount += countMutantSequenceSignaturesInDnaPart(verticalSequence);
		}
		return mutantVerticalCount;
	}

	/*
	 * Searches mutant sequences diagonally (taking into account the mutant sequences already found).
	 * This method only generates diagonals with at least 4 characters.
	 * If the minimum mutant sequences are found the process is stopped and the search finishes (there's no need to
	 * keep generating DNA parts).
	 * Returns the count of mutant appearances (up to the minimum)
	 */
	private int countDiagonalMutantSequenceSignatures(int currentMutantSequencesCount) {
		int mutantDiagonalCount = 0;
		int diagonalsByThePrincipal = sequence.length - 4;

		for (int diagonalIndex = 0; diagonalIndex < diagonalsByThePrincipal + 1 && mutantDiagonalCount + currentMutantSequencesCount < MINIMUM_REPEATED_MUTANT_SEQUENCES; diagonalIndex++) {
			/*
			 * On each iteration I can generate 4 diagonals (except for the case of principal diagonals where the complementary are the same as the original).
			 * For the other I can generate: the diagonal below the principal, the complementary (the same above) and the inverse ones (with the reverse
			 * inclination).
			 */
			String diagonalSequence = new String();
			String complementaryDiagonalSequence = new String();
			String inverseDiagonalSequence = new String();
			String inverseComplementaryDiagonalSequence = new String();
			for (int index = 0 + diagonalIndex; index < sequence.length; index++) {
				diagonalSequence += sequence[index].charAt(index - diagonalIndex);
				inverseDiagonalSequence += sequence[index].charAt(sequence.length - 1 - index + diagonalIndex);
				if (diagonalIndex > 0) {
					complementaryDiagonalSequence += sequence[index - diagonalIndex].charAt(index);
					inverseComplementaryDiagonalSequence += sequence[index - diagonalIndex].charAt(sequence.length - 1 - index);
				}
			}
			// Once all diagonals have been generated for that step I can check for the mutant count
			mutantDiagonalCount += countMutantSequenceSignaturesInDnaPart(diagonalSequence);
			if (mutantDiagonalCount + currentMutantSequencesCount < MINIMUM_REPEATED_MUTANT_SEQUENCES) {
				mutantDiagonalCount += countMutantSequenceSignaturesInDnaPart(inverseDiagonalSequence);
			}
			if (mutantDiagonalCount + currentMutantSequencesCount < MINIMUM_REPEATED_MUTANT_SEQUENCES && diagonalIndex > 0) {
				mutantDiagonalCount += countMutantSequenceSignaturesInDnaPart(complementaryDiagonalSequence);
			}
			if (mutantDiagonalCount + currentMutantSequencesCount < MINIMUM_REPEATED_MUTANT_SEQUENCES && diagonalIndex > 0) {
				mutantDiagonalCount += countMutantSequenceSignaturesInDnaPart(inverseComplementaryDiagonalSequence);
			}
		}
		return mutantDiagonalCount;
	}

	/*
	 * Searches parallelly for mutant sequences in a single DNA part (AAAA, TTTT, CCCC and GGGG at the same time).
	 * This method takes into account multiple appearances in the same part. For example: "AAAATGGAAAA" will return 2
	 * since there are two "AAAA" groups (this won't be taken into account if we simply use "contains").
	 * Returns the count of mutant appearances in the dna part
	 */
	private int countMutantSequenceSignaturesInDnaPart(String part) {
		return MUTANT_SEQUENCE_SIGNATURES.parallelStream()
			.mapToInt(mutantSequence -> StringUtils.countOccurrencesOf(part, mutantSequence))
			.sum();
	}
}
