package com.magneto.mutantdetector.service.impl;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.magneto.mutantdetector.bean.DnaBean;
import com.magneto.mutantdetector.bean.StatsBean;
import com.magneto.mutantdetector.dao.DnaDao;
import com.magneto.mutantdetector.model.Dna;
import com.magneto.mutantdetector.model.Stats;
import com.magneto.mutantdetector.service.MutantDetectorService;

public class MutantDetectorServiceImplTest {
	@Mock
	private DnaDao dnaDao;
	@InjectMocks
	private MutantDetectorService mutantDetectorService;

	@BeforeClass
	public void init() {
		mutantDetectorService = new MutantDetectorServiceImpl();
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testIsMutant() {
		DnaBean dnaBean = new DnaBean(new String[] {"ATCG", "ATCG", "GGTT", "TCCG"});
		String jsonDna = "[\"ATCG\",\"ATCG\",\"GGTT\",\"TCCG\"]";

		boolean isMutant = mutantDetectorService.isMutant(dnaBean);

		assertThat(isMutant).isFalse();
		verify(dnaDao).save(eq(new Dna(jsonDna, isMutant)));
	}

	@Test
	public void testReadStats() {
		Stats statsMock = mock(Stats.class);
		when(statsMock.getMutantCount()).thenReturn(10);
		when(statsMock.getHumanCount()).thenReturn(80);

		when(dnaDao.getStats()).thenReturn(statsMock);

		StatsBean stats = mutantDetectorService.readStats();

		assertThat(stats).isNotNull()
			.hasFieldOrPropertyWithValue("mutantDnaCount", 10)
			.hasFieldOrPropertyWithValue("humanDnaCount", 80);
		verify(dnaDao).getStats();
	}

}
