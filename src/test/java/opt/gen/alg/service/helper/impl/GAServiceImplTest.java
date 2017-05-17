package opt.gen.alg.service.helper.impl;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import opt.gen.TestDataGenerator;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import opt.gen.alg.domain.GADataEntry;
import opt.gen.alg.domain.GAPopulation;
import opt.gen.alg.domain.impl.PickLocationViewDO;
import opt.gen.alg.generator.RandomGenerator;

@RunWith(MockitoJUnitRunner.class)
public class GAServiceImplTest {

	private final static double INITIAL_POPULATION_PERCENTILE_SIZE = 1d;

	private static List<GADataEntry<Long, String>> REAL_DATA;
	private static Set<Long> DICTIONARY_MAP;
	private static String DICTIONARY_HASH_RESULT = "1.2.3.4.5";

	@Spy
	private GAServiceImpl gaService;

	@BeforeClass
	public static void setUpClass() {
		REAL_DATA = TestDataGenerator.generateMultipleRealGADataFromGenes(new ArrayList<>(Arrays.asList(1L, 2L, 3L, 4L, 5L)));
		DICTIONARY_MAP = new HashSet<>(Arrays.asList(1L, 2L, 3L, 4L, 5L));
	}

	@Test
	public void testGetGeneDictionary() throws Exception {
		final Set<Long> result = gaService.getGeneDictionary(REAL_DATA);
		assertThat("Gene dictionary should not be null", result, is(notNullValue()));
		assertEquals("Gene dictionary size is not correct", result.size(), REAL_DATA.size());
	}

	@Test
	public void testGetInitialPopulationSize() throws Exception {
		final long result = gaService.getPopulationSize(DICTIONARY_MAP, INITIAL_POPULATION_PERCENTILE_SIZE);
		assertEquals("Initial population size is not correct", result, DICTIONARY_MAP.size());
	}

	@Test
	public void testGetRealPopulationAsGroupedMap() throws Exception {
		mockRealDataParameters();

		final List<GAPopulation<Long, String, Double>> result = gaService.getRealPopulationGrouped(REAL_DATA);
		assertThat("Population list should not be null", result, is(notNullValue()));
		assertTrue("Population list size is not correct", result.size() <= REAL_DATA.size());
		result.forEach(sequence -> assertFalse("Population optimization parameters be empty", sequence.getOptimizationParameters().isEmpty()));
	}

	private void mockRealDataParameters() {
		REAL_DATA.forEach(data -> {
			((PickLocationViewDO)data).setCustomerId(RandomGenerator.generateUniformLong(200));
			((PickLocationViewDO)data).setLocation("A-" + RandomGenerator.generateUniformLong(200));
			((PickLocationViewDO)data).setX(RandomGenerator.generateUniformDouble());
			((PickLocationViewDO)data).setY(RandomGenerator.generateUniformDouble());
		});
	}

	@Test
	public void testGenerateHashFromSetDictionary() throws Exception {
		final String result = gaService.generateHash(DICTIONARY_MAP);
		assertThat("Gene hash code should not be null", result, is(notNullValue()));
		assertFalse("Gene hash code should not be empty string", result.isEmpty());
		assertTrue("Gene hash code is not correct", result.equals(DICTIONARY_HASH_RESULT));
	}

	@Test
	public void testGenerateHashFromListDictionary() throws Exception {
		final String result = gaService.generateHash(new ArrayList<>(DICTIONARY_MAP));
		assertThat("Gene hash code should not be null", result, is(notNullValue()));
		assertFalse("Gene hash code should not be empty string", result.isEmpty());
		assertTrue("Gene hash code is not correct", result.equals(DICTIONARY_HASH_RESULT));
	}

}