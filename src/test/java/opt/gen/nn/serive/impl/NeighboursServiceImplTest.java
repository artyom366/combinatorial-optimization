package opt.gen.nn.serive.impl;

import opt.gen.alg.domain.GADataEntry;
import opt.gen.alg.domain.GASolution;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static junit.framework.TestCase.assertFalse;
import static opt.gen.TestDataGenerator.generateDummyGAResults;
import static opt.gen.TestDataGenerator.generateRealGADataFromGene;
import static org.junit.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class NeighboursServiceImplTest {

    private final static double MAX_DISTANCE = 5d;
    private final static String CURRENT_LOCATION_NAME = "AAD";
    private final static Double LOCATION_DISTANCE_LOW = 3d;
    private final static Double LOCATION_DISTANCE_HIGH = 7d;
    private final static long CUSTOMER_ID_SAME = 1L;
    private final static long CUSTOMER_ID_DIFFERENT = 2L;

    private final static double x1 = 1;
    private final static double y1 = 2;
    private final static double x2 = 3;
    private final static double y2 = 4;

    private static GADataEntry<Long, String> POTENTIAL_NEIGHBOURING_LOCATION_WITH_SAME_CUSTOMER_ID;
    private static GADataEntry<Long, String> POTENTIAL_NEIGHBOURING_LOCATION_WITH_DIFFERENT_CUSTOMER_ID;
    private static Set<Long> CURRENT_CUSTOMERS;
    private final static String CURRENT_CUSTOMERS_HASH = "1.3.4";

    private static Map<String, GASolution<Long, String, Double>> RESULTS;
    private final static int MAX_FITNESS_VALUE = 10;
    private final static double FITNESS_THRESHOLD_50 = 0.5;

    @BeforeClass
    public static void setUpClass() {
        POTENTIAL_NEIGHBOURING_LOCATION_WITH_SAME_CUSTOMER_ID = generateRealGADataFromGene(CUSTOMER_ID_SAME);
        POTENTIAL_NEIGHBOURING_LOCATION_WITH_DIFFERENT_CUSTOMER_ID = generateRealGADataFromGene(CUSTOMER_ID_DIFFERENT);

        CURRENT_CUSTOMERS = new HashSet<>();
        CURRENT_CUSTOMERS.add(1L);
        CURRENT_CUSTOMERS.add(3L);
        CURRENT_CUSTOMERS.add(4L);

        RESULTS = generateDummyGAResults(CURRENT_CUSTOMERS);
    }

    private static String getSetAsString(final Set<Long> collection) {
        return collection.stream().map(Object::toString).collect(Collectors.joining("-"));
    }

    @Spy
    private NeighboursServiceImpl neighboursService;

    @Test
    public void testIsValidNeighbouringLocationWithSameCustomerIdAndLowDistance() {
        final boolean result = neighboursService.isValidNeighbouringLocation(Optional.of(LOCATION_DISTANCE_LOW),
                MAX_DISTANCE,
                CURRENT_LOCATION_NAME,
                POTENTIAL_NEIGHBOURING_LOCATION_WITH_SAME_CUSTOMER_ID,
                CURRENT_CUSTOMERS);

        assertTrue(String.format("Potential neighboring location should be valid %s", getFormattedAssertionErrorMessage()), result);
    }

    @Test
    public void testIsValidNeighbouringLocationWithDifferentCustomerIdAndLowDistance() {
        final boolean result = neighboursService.isValidNeighbouringLocation(Optional.of(LOCATION_DISTANCE_LOW),
                MAX_DISTANCE,
                CURRENT_LOCATION_NAME,
                POTENTIAL_NEIGHBOURING_LOCATION_WITH_DIFFERENT_CUSTOMER_ID,
                CURRENT_CUSTOMERS);

        assertFalse(String.format("Potential neighboring location should not be valid %s", getFormattedAssertionErrorMessage()), result);
    }

    @Test
    public void testIsValidNeighbouringLocationWithDifferentCustomerIdAndHighDistance() {
        final boolean result = neighboursService.isValidNeighbouringLocation(Optional.of(LOCATION_DISTANCE_HIGH),
                MAX_DISTANCE,
                CURRENT_LOCATION_NAME,
                POTENTIAL_NEIGHBOURING_LOCATION_WITH_DIFFERENT_CUSTOMER_ID,
                CURRENT_CUSTOMERS);

        assertFalse(String.format("Potential neighboring location should not be valid %s", getFormattedAssertionErrorMessage()), result);
    }

    @Test
    public void testIsValidNeighbouringLocationWithSameCustomerIdAndHighDistance() {
        final boolean result = neighboursService.isValidNeighbouringLocation(Optional.of(LOCATION_DISTANCE_HIGH),
                MAX_DISTANCE,
                CURRENT_LOCATION_NAME,
                POTENTIAL_NEIGHBOURING_LOCATION_WITH_SAME_CUSTOMER_ID,
                CURRENT_CUSTOMERS);

        assertFalse(String.format("Potential neighboring location should not be valid %s", getFormattedAssertionErrorMessage()), result);
    }

    private String getFormattedAssertionErrorMessage() {
        return String.format("given the following parameters: " +
                        "location distance - %s, " +
                        "max allowed distance - %s " +
                        "current location name - %s " +
                        "potential neighboring location customer id - %s " +
                        "and current location customers: %s",
                        LOCATION_DISTANCE_LOW,
                        MAX_DISTANCE,
                        CURRENT_LOCATION_NAME,
                        POTENTIAL_NEIGHBOURING_LOCATION_WITH_SAME_CUSTOMER_ID,
                        getSetAsString(CURRENT_CUSTOMERS));
    }

    @Test
    public void testGetOptionalLocationsDistance() {
        final Optional<Double> optionalResult = neighboursService.getOptionalLocationsDistance(x1, y1, x2, y2);
        assertTrue("Optional value should present in the result", optionalResult.isPresent());
        assertTrue("Location distance should be less than 3 and more than 2.5", optionalResult.get() > 2.5d && optionalResult.get() < 3d);
    }

    @Test
    public void testGetMaxFitnessValue() {
        final int result = neighboursService.getMaxFitnessValue(RESULTS);
        assertTrue("Maximum fitness value should be equal to 3", result == 3);
    }

    @Test
    public void testGetMinFitnessValueThreshold() {
        final int result = neighboursService.getMinFitnessValueThreshold(MAX_FITNESS_VALUE, FITNESS_THRESHOLD_50);
        assertTrue("Minimum fitness threshold value should be equal to 5", result == 5);
    }
}