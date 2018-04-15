package com.magneto.mutantdetector.dataprovider;

import org.testng.annotations.DataProvider;

/**
 * Set of data for multiple tests with mutant and human DNA sequences
 */
public class DnaDataProvider {

	@DataProvider(name = "isMutantDataProvider")
	public Object[][] isMutantDataProvider() {
		return new Object[][] {
			// Mutant, horizontal matches
			{new String[] {	"AAAA",
				"TTTT",
				"CCCC",
				"GGGG"}, true}, // All rows!

			{new String[] {	"ACTT",
				"GGGG",
				"ATGA",
				"TTTT"}, true}, // Second and last rows

			// Mutant, 2 horizontal matches in the same row
			{new String[] {	"AAAACCCC", // This row
				"ATATATAT",
				"CGCGCGCG",
				"ATATATAT",
				"CGCGCGCG",
				"ATATATAT",
				"CGCGCGCG",
				"ATATATAT"}, true},

			{new String[] {	"CGCGCGCG",
				"ATATATAT",
				"CGCGCGCG",
				"ATATATAT",
				"CGCGCGCG",
				"TTTTGGGG", // This row
				"CGCGCGCG",
				"ATATATAT"}, true},

			// Mutant, vertical matches
			{new String[] {	"ATCG",
				"ATGC",
				"ATCG",
				"ATGC"}, true},

			{new String[] {	"AAAT",
				"CATT",
				"CAAT",
				"GACT"}, true}, // Second and fourth column

			// Mutant, 2 vertical matches in the same column
			{new String[] {	"AGCGCGCG",
				"ATATATAT",
				"AGCGCGCG",
				"ATATATAT",
				"CGCGCGCG",
				"CTATATAT",
				"CGCGCGCG",
				"CTATATAT"}, true}, // First column

			{new String[] {	"CGCGCACG",
				"ATATAAAT",
				"CGCGCACG",
				"ATATAAAT",
				"CGCGCTCG",
				"ATATATAT",
				"CGCGCTCG",
				"ATATATAT"}, true}, // Sixth column

			// Mutant, matches in both principal diagonals (As and Ts)
			{new String[] {	"AGTT",
				"GATT",
				"ATAT",
				"TGTA"}, true},

			// Mutant, both matches in the principal diagonal (Cs and As)
			{new String[] {	"CGCGCGCG",
				"ACATATAT",
				"CGCGCGCG",
				"ATACATAT",
				"CGCGAGCG",
				"ATATAAAT",
				"CGCGCGAG",
				"ATATATAA"}, true},

			// Mutant, both matches in the inverse principal diagonal (Ts and Gs)
			{new String[] {	"CGCGCGCT",
				"ATATATTT",
				"CGCGCTCG",
				"ATATTTAT",
				"CGCGCGCG",
				"ATGTATAT",
				"CGCGCGCG",
				"GTATATAT"}, true},

			// Mutant, mixed examples
			{new String[] {	"CAAAA",
				"TACGT",
				"TCACT",
				"TCCCG",
				"TTATT"}, true}, // As in first row and Ts in first column

			{new String[] {	"CAAAA",
				"TTCGT",
				"TCTCT",
				"GCCTG",
				"TTATT"}, true}, // As in first row and Ts in the main diagonal

			{new String[] {	"CGCGCGCT",
				"ATATATTT", // As diagonal that starts in this row
				"CACGCCCG",
				"ATATTTAT",
				"CGCATGCG", // Inverse diagonal that goes down from the T on this row
				"ATCTCTAT",
				"CGTGCGCG",
				"GTATATAT"}, true},

			{new String[] {	"ATGCGA",
				"CAGTGC",
				"TTATGT",
				"AGAAGG",
				"CCCCTA",
				"TCACTG"}, true}, // As in the main diagonal, Gs in the fifth column and Cs in the fifth row

			// Not mutants
			{new String[] {	"AAAT",
				"CATT",
				"CTAC",
				"GACT"}, false},

			{new String[] {	"CGCG",
				"TATA",
				"CGCG",
				"TATA"}, false},

			{new String[] {	"ATGCGA",
				"CGGTGC",
				"TTATGT",
				"AGAATG",
				"CCACTA",
				"TCACTG"}, false},
		};
	}
}
