package com.magneto.mutantdetector.bean;

import static org.assertj.core.api.Assertions.assertThat;
import static org.testng.Assert.fail;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.magneto.mutantdetector.dataprovider.DnaDataProvider;
import com.magneto.mutantdetector.exception.InvalidDnaSequenceException;

public class DnaBeanTest {

	@DataProvider(name = "validateSequenceDataProvider")
	private Object[][] validateSequenceDataProvider() {
		return new Object[][] {
			// Invalid, sequence length is less than 4
			{new String[] {"AAAA", "AAAA", "AAAA"}, false},

			// Invalid, some part contains less bases than the sequence length
			{new String[] {"AAA", "AAAA", "AAAA", "AAAA"}, false},
			{new String[] {"AAAA", "AAA", "AAAA", "AAAA"}, false},
			{new String[] {"AAAA", "AAAA", "AAA", "AAAA"}, false},
			{new String[] {"AAAA", "AAAA", "AAAA", "AAA"}, false},

			// Invalid, DNA sequence not a square matrix
			{new String[] {"AAAAA", "AAAAA", "AAAAA", "AAAAA"}, false},
			{new String[] {"AAAA", "AAAA", "AAAA", "AAAA", "AAAA"}, false},
			{new String[] {"AAA", "AAA", "AAA", "AAA"}, false},

			// Invalid, DNA sequence contains not valid bases
			{new String[] {"XAAA", "AAAA", "AAAA", "AAAA"}, false},
			{new String[] {"AAAA", "AXAA", "AAAA", "AAAA"}, false},
			{new String[] {"AAAA", "AXAA", "AAXA", "AAAA"}, false},
			{new String[] {"AAAA", "AAAA", "AAAA", "AAXA"}, false},

			// Valid
			{new String[] {"ATCG", "ATCG", "GGTT", "TCCG"}, true},
			{new String[] {"ATCG", "AATG", "ATCG", "ATCG"}, true},
			{new String[] {"GGTT", "ATCG", "TCCG", "GGGG"}, true},
			{new String[] {"ACCC", "ATCG", "TCCG", "CTGG"}, true},
			{new String[] {"TTTT", "ATCG", "ACCC", "CTGG"}, true}
		};
	}

	@Test(dataProvider = "validateSequenceDataProvider")
	public void testValidateSequence(String[] sequence, boolean isValid) {
		DnaBean dnaBean = new DnaBean(sequence);
		try {
			dnaBean.validateSequence();
			if (!isValid) {
				fail("Expected to fail but was marked as a valid DNA sequence");
			}
		} catch (InvalidDnaSequenceException e) {
			if (isValid) {
				fail("Expected to be valid but was marked as an invalid DNA sequence");
			}
		}
	}

	@Test(dataProviderClass = DnaDataProvider.class, dataProvider = "isMutantDataProvider")
	public void testIsMutant(String[] sequence, boolean isMutantExpected) {
		DnaBean dnaBean = new DnaBean(sequence);
		assertThat(dnaBean.isMutant()).isEqualTo(isMutantExpected);
	}
}
