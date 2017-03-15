package opt.gen.alg.service.runner.impl;

import static opt.gen.alg.service.TestDataGenerator.generateGeneDictionary;
import static opt.gen.alg.service.TestDataGenerator.generateRealGAData;
import static opt.gen.alg.service.TestDataGenerator.getRealDataAsMap;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import opt.gen.alg.domain.GADataEntry;
import opt.gen.alg.domain.GASolution;
import opt.gen.alg.service.helper.impl.GAServiceImpl;
import opt.gen.alg.service.runner.GARunnerService;
import opt.gen.alg.service.strategy.impl.MostDiversePopulationStrategyImpl;

@RunWith(MockitoJUnitRunner.class)
public class GARunnerServiceImplTest {

	@Spy
	private GAServiceImpl gaService;

	@InjectMocks
	private MostDiversePopulationStrategyImpl diversePopulationStrategy = new MostDiversePopulationStrategyImpl();

	@Spy
	private GARunnerService<Long, String> gaRunnerService = new GARunnerServiceImpl(diversePopulationStrategy);

	@Test
	public void testRunWithMostDiversePopulationStrategy() throws Exception {

		final List<GADataEntry<Long, String>> realData = generateRealGAData();
		final Set<Long> dictionary = generateGeneDictionary(realData);
		final Map<String, List<Long>> realDataGroups = getRealDataAsMap(realData);

		final Map<String, GASolution<Long, String>> result = gaRunnerService.run(dictionary, realDataGroups);
		assertThat("GA result should not be null", result, is(notNullValue()));
	}



}