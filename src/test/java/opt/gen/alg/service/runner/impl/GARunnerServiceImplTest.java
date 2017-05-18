package opt.gen.alg.service.runner.impl;

import opt.gen.alg.domain.GACandidate;
import opt.gen.alg.domain.GADataEntry;
import opt.gen.alg.domain.GAPopulation;
import opt.gen.alg.domain.GASolution;
import opt.gen.alg.service.strategy.impl.MostDiversePopulationStrategyImpl;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.*;

import static opt.gen.TestDataGenerator.generateGACandidate;
import static opt.gen.TestDataGenerator.generateRealGAData;
import static opt.gen.TestDataGenerator.generateRealPopulation;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class GARunnerServiceImplTest {

    private static Set<Long> GENES;
    private final static String GENES_HASH = "1.3.4";

    private static List<GAPopulation<Long, String, Double>> REAL_POPULATION_GROUPS;
    private static GACandidate<Long> NEXT_GENERATION;
    private static Map<String, GASolution<Long, String, Double>> RESULT;

    @InjectMocks
    private MostDiversePopulationStrategyImpl diversePopulationStrategy = new MostDiversePopulationStrategyImpl();

    @Spy
    private GARunnerServiceImpl gaRunnerService = new GARunnerServiceImpl(diversePopulationStrategy);

    @Before
    public void setUp() {
        final List<GADataEntry<Long, String>> realData = generateRealGAData();
        REAL_POPULATION_GROUPS = generateRealPopulation(realData);

        GENES = new HashSet<>();
        NEXT_GENERATION = generateGACandidate(GENES, GENES_HASH);

        RESULT = new HashMap<>();
    }

    @Test
    public void testEstimateFitnessFrequency() {
        gaRunnerService.estimateFitnessFrequency(REAL_POPULATION_GROUPS, Collections.singletonList(NEXT_GENERATION), RESULT);

        final GASolution<Long, String, Double> result = RESULT.get(GENES_HASH);
        assertThat(String.format("Solution should contain one single result with key value: %s", GENES_HASH), result, is(notNullValue()));

        final Map<Pair<Double, Double>, String> locations = result.getRealDataSequenceIds();
        assertThat("Result location should not be null", locations, is(notNullValue()));
        assertThat("Result location count should be equal to 10", locations.size() == 10);
    }
}