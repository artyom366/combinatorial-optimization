package opt.gen.alg.service.runner.impl;

import opt.gen.alg.domain.GADataEntry;
import opt.gen.alg.domain.GAPopulation;
import opt.gen.alg.domain.GASolution;
import opt.gen.alg.service.helper.impl.GAServiceImpl;
import opt.gen.alg.service.runner.GARunnerService;
import opt.gen.alg.service.strategy.impl.RandomSelectionStrategyImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static opt.gen.TestDataGenerator.generateGeneDictionary;
import static opt.gen.TestDataGenerator.generateRealGAData;
import static opt.gen.TestDataGenerator.generateRealPopulation;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class GARouletteSelectionStrategyIntegrationTest {

    @Spy
    private GAServiceImpl gaService;

    @InjectMocks
    private RandomSelectionStrategyImpl diversePopulationStrategy = new RandomSelectionStrategyImpl();

    @Spy
    private GARunnerService<Long, String, Double> gaRunnerService = new GARunnerServiceImpl(diversePopulationStrategy);

    @Test
    public void testRunWithRandomSelectionStrategy() throws Exception {

        final List<GADataEntry<Long, String>> realData = generateRealGAData();
        final Set<Long> dictionary = generateGeneDictionary(realData);
        final List<GAPopulation<Long, String, Double>> realPopulation = generateRealPopulation(realData);

        final Map<String, GASolution<Long, String, Double>> result = gaRunnerService.run(dictionary, realPopulation);
        assertThat("GA with roulette selection result should not be null", result, is(notNullValue()));
    }
}
