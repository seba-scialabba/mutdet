package com.magneto.mutantdetector.api.impl;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import com.magneto.mutantdetector.dataprovider.DnaDataProvider;
import com.magneto.mutantdetector.ws.DnaWS;
import com.magneto.mutantdetector.ws.StatsWS;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class MutantDetectorApiImplIT extends AbstractTestNGSpringContextTests {
	@Autowired
	private TestRestTemplate restTemplate;

	@Test(
		dataProviderClass = DnaDataProvider.class,
		dataProvider = "isMutantDataProvider"
	)
	public void testIsMutant(String[] sequence, boolean isMutantExpected) {
		DnaWS dnaWS = new DnaWS();
		dnaWS.setDna(sequence);

		ResponseEntity<DnaWS> response = restTemplate.postForEntity("/mutant", dnaWS, DnaWS.class);

		assertThat(response).isNotNull()
			.hasFieldOrPropertyWithValue("statusCodeValue", isMutantExpected ? 200 : 403);
	}

	@Test(dependsOnMethods = "testIsMutant")
	public void testGetStats() {
		StatsWS response = restTemplate.getForObject("/stats", StatsWS.class);

		assertThat(response).isNotNull()
			.hasFieldOrPropertyWithValue("mutantDnaCount", 15)
			.hasFieldOrPropertyWithValue("humanDnaCount", 3)
			.hasFieldOrPropertyWithValue("ratio", 5.0f);
	}
}
