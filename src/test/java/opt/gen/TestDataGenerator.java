package opt.gen;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import opt.gen.alg.domain.GACandidate;
import opt.gen.alg.domain.GADataEntry;
import opt.gen.alg.domain.GAPopulation;
import opt.gen.alg.domain.GASolution;
import opt.gen.alg.domain.impl.Chromosome;
import opt.gen.alg.domain.impl.PickLocationViewDO;
import opt.gen.alg.domain.impl.Population;
import opt.gen.alg.domain.impl.Result;
import opt.gen.alg.generator.RandomGenerator;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

public class TestDataGenerator {

	private final static String DUMMY_HASH_1 = "1";
	private final static String DUMMY_HASH_2 = "2";

	public static List<GADataEntry<Long, String>> generateRandomRealGAData(final int dataCount, final int diversity) {
		return IntStream.range(0, dataCount).boxed().map(location -> new PickLocationViewDO(RandomGenerator.generateUniformLong(diversity)))
			.collect(Collectors.collectingAndThen(Collectors.toList(), ImmutableList::copyOf));
	}

	public static List<GADataEntry<Long, String>> generateMultipleRealGADataFromGenes(final List<Long> genes) {
		return genes.stream().map(PickLocationViewDO::new).collect(Collectors.collectingAndThen(Collectors.toList(), ImmutableList::copyOf));
	}

	public static GADataEntry<Long, String> generateRealGADataFromGene(final Long gene) {
		return new PickLocationViewDO(gene);
	}

	public static Set<Long> generateRandomGeneDictionary(final int size, final int diversity) {
		return IntStream.range(0, size).mapToObj(gene -> RandomGenerator.generateUniformInt(diversity)).map(Long::valueOf)
			.collect(Collectors.collectingAndThen(Collectors.toSet(), ImmutableSet::copyOf));
	}

	public static Set<Long> generateStaticGeneDictionary(final List<Long> genes) {
		return genes.stream().map(Long::valueOf).collect(Collectors.collectingAndThen(Collectors.toSet(), ImmutableSet::copyOf));
	}

	public static Set<Long> generateGeneDictionary(final List<GADataEntry<Long, String>> realData) {
		return realData.stream().map(GADataEntry::getOptimizationParameter).collect(Collectors.collectingAndThen(Collectors.toSet(), ImmutableSet::copyOf));
	}

	public static List<GADataEntry<Long, String>> generateRealGAData() {

		final List<GADataEntry<Long, String>> realData = new ArrayList<>();
		realData.add(new PickLocationViewDO(0L, "A", 0, 0));
		realData.add(new PickLocationViewDO(5L, "A", 0, 0));
		realData.add(new PickLocationViewDO(10L, "A", 0, 0));
		realData.add(new PickLocationViewDO(15L, "A", 0, 0));
		realData.add(new PickLocationViewDO(20L, "A", 0, 0));
		realData.add(new PickLocationViewDO(25L, "A", 0, 0));
		realData.add(new PickLocationViewDO(30L, "A", 0, 0));
		realData.add(new PickLocationViewDO(35L, "A", 0, 0));
		realData.add(new PickLocationViewDO(40L, "A", 0, 0));
		realData.add(new PickLocationViewDO(45L, "A", 0, 0));

		realData.add(new PickLocationViewDO(0L, "B", 5, 5));
		realData.add(new PickLocationViewDO(2L, "B", 5, 5));
		realData.add(new PickLocationViewDO(4L, "B", 5, 5));
		realData.add(new PickLocationViewDO(6L, "B", 5, 5));
		realData.add(new PickLocationViewDO(8L, "B", 5, 5));
		realData.add(new PickLocationViewDO(10L, "B", 5, 5));
		realData.add(new PickLocationViewDO(12L, "B", 5, 5));
		realData.add(new PickLocationViewDO(14L, "B", 5, 5));
		realData.add(new PickLocationViewDO(16L, "B", 5, 5));
		realData.add(new PickLocationViewDO(18L, "B", 5, 5));

		realData.add(new PickLocationViewDO(1L, "C", 10, 10));
		realData.add(new PickLocationViewDO(3L, "C", 10, 10));
		realData.add(new PickLocationViewDO(5L, "C", 10, 10));
		realData.add(new PickLocationViewDO(7L, "C", 10, 10));
		realData.add(new PickLocationViewDO(9L, "C", 10, 10));
		realData.add(new PickLocationViewDO(11L, "C", 10, 10));
		realData.add(new PickLocationViewDO(13L, "C", 10, 10));
		realData.add(new PickLocationViewDO(15L, "C", 10, 10));
		realData.add(new PickLocationViewDO(17L, "C", 10, 10));
		realData.add(new PickLocationViewDO(19L, "C", 10, 10));

		realData.add(new PickLocationViewDO(1L, "D", 15, 15));
		realData.add(new PickLocationViewDO(4L, "D", 15, 15));
		realData.add(new PickLocationViewDO(7L, "D", 15, 15));
		realData.add(new PickLocationViewDO(10L, "D", 15, 15));
		realData.add(new PickLocationViewDO(13L, "D", 15, 15));
		realData.add(new PickLocationViewDO(16L, "D", 15, 15));
		realData.add(new PickLocationViewDO(19L, "D", 15, 15));
		realData.add(new PickLocationViewDO(22L, "D", 15, 15));
		realData.add(new PickLocationViewDO(25L, "D", 15, 15));
		realData.add(new PickLocationViewDO(28L, "D", 15, 15));

		realData.add(new PickLocationViewDO(1L, "E", 20, 20));
		realData.add(new PickLocationViewDO(5L, "E", 20, 20));
		realData.add(new PickLocationViewDO(9L, "E", 20, 20));
		realData.add(new PickLocationViewDO(13L, "E", 20, 20));
		realData.add(new PickLocationViewDO(17L, "E", 20, 20));
		realData.add(new PickLocationViewDO(21L, "E", 20, 20));
		realData.add(new PickLocationViewDO(25L, "E", 20, 20));
		realData.add(new PickLocationViewDO(29L, "E", 20, 20));
		realData.add(new PickLocationViewDO(33L, "E", 20, 20));
		realData.add(new PickLocationViewDO(37L, "E", 20, 20));
		realData.add(new PickLocationViewDO(41L, "E", 20, 20));

		realData.add(new PickLocationViewDO(0L, "F", 25, 25));
		realData.add(new PickLocationViewDO(3L, "F", 25, 25));
		realData.add(new PickLocationViewDO(6L, "F", 25, 25));
		realData.add(new PickLocationViewDO(9L, "F", 25, 25));
		realData.add(new PickLocationViewDO(12L, "F", 25, 25));
		realData.add(new PickLocationViewDO(15L, "F", 25, 25));
		realData.add(new PickLocationViewDO(18L, "F", 25, 25));
		realData.add(new PickLocationViewDO(21L, "F", 25, 25));
		realData.add(new PickLocationViewDO(24L, "F", 25, 25));
		realData.add(new PickLocationViewDO(27L, "F", 25, 25));

		realData.add(new PickLocationViewDO(0L, "G", 30, 30));
		realData.add(new PickLocationViewDO(4L, "G", 30, 30));
		realData.add(new PickLocationViewDO(8L, "G", 30, 30));
		realData.add(new PickLocationViewDO(12L, "G", 30, 30));
		realData.add(new PickLocationViewDO(16L, "G", 30, 30));
		realData.add(new PickLocationViewDO(20L, "G", 30, 30));
		realData.add(new PickLocationViewDO(24L, "G", 30, 30));
		realData.add(new PickLocationViewDO(28L, "G", 30, 30));
		realData.add(new PickLocationViewDO(32L, "G", 30, 30));
		realData.add(new PickLocationViewDO(36L, "G", 30, 30));

		realData.add(new PickLocationViewDO(0L, "H", 35, 35));
		realData.add(new PickLocationViewDO(1L, "H", 35, 35));
		realData.add(new PickLocationViewDO(2L, "H", 35, 35));
		realData.add(new PickLocationViewDO(3L, "H", 35, 35));
		realData.add(new PickLocationViewDO(4L, "H", 35, 35));
		realData.add(new PickLocationViewDO(5L, "H", 35, 35));
		realData.add(new PickLocationViewDO(6L, "H", 35, 35));
		realData.add(new PickLocationViewDO(7L, "H", 35, 35));
		realData.add(new PickLocationViewDO(8L, "H", 35, 35));
		realData.add(new PickLocationViewDO(9L, "H", 35, 35));

		realData.add(new PickLocationViewDO(0L, "I", 40, 40));
		realData.add(new PickLocationViewDO(1L, "I", 40, 40));
		realData.add(new PickLocationViewDO(2L, "I", 40, 40));
		realData.add(new PickLocationViewDO(3L, "I", 40, 40));
		realData.add(new PickLocationViewDO(5L, "I", 40, 40));
		realData.add(new PickLocationViewDO(8L, "I", 40, 40));
		realData.add(new PickLocationViewDO(13L, "I", 40, 40));
		realData.add(new PickLocationViewDO(21L, "I", 40, 40));
		realData.add(new PickLocationViewDO(34L, "I", 40, 40));
		realData.add(new PickLocationViewDO(55L, "I", 40, 40));

		realData.add(new PickLocationViewDO(2L, "J", 45, 45));
		realData.add(new PickLocationViewDO(3L, "J", 45, 45));
		realData.add(new PickLocationViewDO(5L, "J", 45, 45));
		realData.add(new PickLocationViewDO(7L, "J", 45, 45));
		realData.add(new PickLocationViewDO(11L, "J", 45, 45));
		realData.add(new PickLocationViewDO(13L, "J", 45, 45));
		realData.add(new PickLocationViewDO(17L, "J", 45, 45));
		realData.add(new PickLocationViewDO(19L, "J", 45, 45));
		realData.add(new PickLocationViewDO(21L, "J", 45, 45));
		realData.add(new PickLocationViewDO(23L, "J", 45, 45));

		return realData;
	}

	public static Map<String, List<GADataEntry<Long, String>>> generateRealDataAsMap(final List<GADataEntry<Long, String>> realData) {
		return realData.stream()
			.collect(Collectors.groupingBy(GADataEntry::getGroupingParameter, Collectors.mapping(e -> e, Collectors.toList())));
	}

	public static List<GAPopulation<Long, String, Double>> generateRealPopulation(final List<GADataEntry<Long, String>> realData) {
		final Map<String, List<GADataEntry<Long, String>>> realDataAsMap = generateRealDataAsMap(realData);

		final List<GAPopulation<Long, String, Double>> realPopulation = new ArrayList<>();

		realDataAsMap.entrySet().forEach( e -> {
			final List<Long> customerIds = e.getValue().stream().map(GADataEntry::getOptimizationParameter).collect(Collectors.toList());
			realPopulation.add(new Population(e.getKey(), customerIds, e.getValue().get(0).getCoordinateX(), e.getValue().get(0).getCoordinateY()));
		});

		return realPopulation;
	}

	public static Map<String, GASolution<Long, String, Double>> generateDummyGAResults(final Set<Long> genes) {

		final Map<String, GASolution<Long, String, Double>> results = new HashMap<>();
		final GASolution<Long, String, Double> result1 = generateGASolutionWithDummyCoordinates(genes);
		final GASolution<Long, String, Double> result2 = generateGAMultipleSolutionsWithDummyCoordinates(genes);

		results.put(DUMMY_HASH_1, result1);
		results.put(DUMMY_HASH_2, result2);
		return results;
	}

	private static GASolution<Long, String, Double> generateGASolutionWithDummyCoordinates(final Set<Long> genes) {

		final GACandidate<Long> candidate = generateGACandidate(genes, DUMMY_HASH_1);

		Pair<Double, Double> dummyCoordinates = new ImmutablePair<>(0d, 0d);
		final Map<Pair<Double, Double>, String> dummyCoordinatesAndLocation = new HashMap<>();
		dummyCoordinatesAndLocation.put(dummyCoordinates, StringUtils.EMPTY);

		return new Result(candidate, dummyCoordinatesAndLocation);
	}

	private static GASolution<Long, String, Double> generateGAMultipleSolutionsWithDummyCoordinates(final Set<Long> genes) {

		final GACandidate<Long> candidate = generateGACandidate(genes, DUMMY_HASH_2);

		final Map<Pair<Double, Double>, String> dummyCoordinatesAndLocation = new HashMap<>();
		final Pair<Double, Double> dummyCoordinates1 = new ImmutablePair<>(0d, 0d);
		final Pair<Double, Double> dummyCoordinates2 = new ImmutablePair<>(1d, 1d);
		final Pair<Double, Double> dummyCoordinates3 = new ImmutablePair<>(2d, 2d);

		dummyCoordinatesAndLocation.put(dummyCoordinates1, StringUtils.EMPTY);
		dummyCoordinatesAndLocation.put(dummyCoordinates2, StringUtils.EMPTY);
		dummyCoordinatesAndLocation.put(dummyCoordinates3, StringUtils.EMPTY);

		return new Result(candidate, dummyCoordinatesAndLocation);
	}

	public static GACandidate<Long> generateGACandidate(final Set<Long> genes, final String hash) {
		return  new Chromosome(genes, hash);
	}


}
