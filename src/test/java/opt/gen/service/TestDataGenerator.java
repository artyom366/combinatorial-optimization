package opt.gen.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import opt.gen.domain.GADataEntry;
import opt.gen.domain.impl.PickLocationViewDO;
import opt.gen.generator.RandomGenerator;

public class TestDataGenerator {

	public static List<GADataEntry<Long, String>> generateRandomRealGAData(final int dataCount, final int diversity) {
		return IntStream.range(0, dataCount).boxed().map(location -> new PickLocationViewDO(RandomGenerator.generateUniformLong(diversity)))
			.collect(Collectors.collectingAndThen(Collectors.toList(), ImmutableList::copyOf));
	}

	public static List<GADataEntry<Long, String>> generateRandomRealGAData(final List<Long> genes) {
		return genes.stream().map(PickLocationViewDO::new).collect(Collectors.collectingAndThen(Collectors.toList(), ImmutableList::copyOf));
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
		realData.add(new PickLocationViewDO(0L, "A"));
		realData.add(new PickLocationViewDO(5L, "A"));
		realData.add(new PickLocationViewDO(10L, "A"));
		realData.add(new PickLocationViewDO(15L, "A"));
		realData.add(new PickLocationViewDO(20L, "A"));
		realData.add(new PickLocationViewDO(25L, "A"));
		realData.add(new PickLocationViewDO(30L, "A"));
		realData.add(new PickLocationViewDO(35L, "A"));
		realData.add(new PickLocationViewDO(40L, "A"));
		realData.add(new PickLocationViewDO(45L, "A"));

		realData.add(new PickLocationViewDO(0L, "B"));
		realData.add(new PickLocationViewDO(2L, "B"));
		realData.add(new PickLocationViewDO(4L, "B"));
		realData.add(new PickLocationViewDO(6L, "B"));
		realData.add(new PickLocationViewDO(8L, "B"));
		realData.add(new PickLocationViewDO(10L, "B"));
		realData.add(new PickLocationViewDO(12L, "B"));
		realData.add(new PickLocationViewDO(14L, "B"));
		realData.add(new PickLocationViewDO(16L, "B"));
		realData.add(new PickLocationViewDO(18L, "B"));

		realData.add(new PickLocationViewDO(1L, "C"));
		realData.add(new PickLocationViewDO(3L, "C"));
		realData.add(new PickLocationViewDO(5L, "C"));
		realData.add(new PickLocationViewDO(7L, "C"));
		realData.add(new PickLocationViewDO(9L, "C"));
		realData.add(new PickLocationViewDO(11L, "C"));
		realData.add(new PickLocationViewDO(13L, "C"));
		realData.add(new PickLocationViewDO(15L, "C"));
		realData.add(new PickLocationViewDO(17L, "C"));
		realData.add(new PickLocationViewDO(19L, "C"));

		realData.add(new PickLocationViewDO(1L, "D"));
		realData.add(new PickLocationViewDO(4L, "D"));
		realData.add(new PickLocationViewDO(7L, "D"));
		realData.add(new PickLocationViewDO(10L, "D"));
		realData.add(new PickLocationViewDO(13L, "D"));
		realData.add(new PickLocationViewDO(16L, "D"));
		realData.add(new PickLocationViewDO(19L, "D"));
		realData.add(new PickLocationViewDO(22L, "D"));
		realData.add(new PickLocationViewDO(25L, "D"));
		realData.add(new PickLocationViewDO(28L, "D"));

		realData.add(new PickLocationViewDO(1L, "E"));
		realData.add(new PickLocationViewDO(5L, "E"));
		realData.add(new PickLocationViewDO(9L, "E"));
		realData.add(new PickLocationViewDO(13L, "E"));
		realData.add(new PickLocationViewDO(17L, "E"));
		realData.add(new PickLocationViewDO(21L, "E"));
		realData.add(new PickLocationViewDO(25L, "E"));
		realData.add(new PickLocationViewDO(29L, "E"));
		realData.add(new PickLocationViewDO(33L, "E"));
		realData.add(new PickLocationViewDO(37L, "E"));
		realData.add(new PickLocationViewDO(41L, "E"));

		realData.add(new PickLocationViewDO(0L, "F"));
		realData.add(new PickLocationViewDO(3L, "F"));
		realData.add(new PickLocationViewDO(6L, "F"));
		realData.add(new PickLocationViewDO(9L, "F"));
		realData.add(new PickLocationViewDO(12L, "F"));
		realData.add(new PickLocationViewDO(15L, "F"));
		realData.add(new PickLocationViewDO(18L, "F"));
		realData.add(new PickLocationViewDO(21L, "F"));
		realData.add(new PickLocationViewDO(24L, "F"));
		realData.add(new PickLocationViewDO(27L, "F"));

		realData.add(new PickLocationViewDO(0L, "G"));
		realData.add(new PickLocationViewDO(4L, "G"));
		realData.add(new PickLocationViewDO(8L, "G"));
		realData.add(new PickLocationViewDO(12L, "G"));
		realData.add(new PickLocationViewDO(16L, "G"));
		realData.add(new PickLocationViewDO(20L, "G"));
		realData.add(new PickLocationViewDO(24L, "G"));
		realData.add(new PickLocationViewDO(28L, "G"));
		realData.add(new PickLocationViewDO(32L, "G"));
		realData.add(new PickLocationViewDO(36L, "G"));

		realData.add(new PickLocationViewDO(0L, "H"));
		realData.add(new PickLocationViewDO(1L, "H"));
		realData.add(new PickLocationViewDO(2L, "H"));
		realData.add(new PickLocationViewDO(3L, "H"));
		realData.add(new PickLocationViewDO(4L, "H"));
		realData.add(new PickLocationViewDO(5L, "H"));
		realData.add(new PickLocationViewDO(6L, "H"));
		realData.add(new PickLocationViewDO(7L, "H"));
		realData.add(new PickLocationViewDO(8L, "H"));
		realData.add(new PickLocationViewDO(9L, "H"));

		realData.add(new PickLocationViewDO(0L, "I"));
		realData.add(new PickLocationViewDO(1L, "I"));
		realData.add(new PickLocationViewDO(2L, "I"));
		realData.add(new PickLocationViewDO(3L, "I"));
		realData.add(new PickLocationViewDO(5L, "I"));
		realData.add(new PickLocationViewDO(8L, "I"));
		realData.add(new PickLocationViewDO(13L, "I"));
		realData.add(new PickLocationViewDO(21L, "I"));
		realData.add(new PickLocationViewDO(21L, "I"));
		realData.add(new PickLocationViewDO(34L, "I"));

		realData.add(new PickLocationViewDO(2L, "J"));
		realData.add(new PickLocationViewDO(3L, "J"));
		realData.add(new PickLocationViewDO(5L, "J"));
		realData.add(new PickLocationViewDO(7L, "J"));
		realData.add(new PickLocationViewDO(11L, "J"));
		realData.add(new PickLocationViewDO(13L, "J"));
		realData.add(new PickLocationViewDO(17L, "J"));
		realData.add(new PickLocationViewDO(19L, "J"));
		realData.add(new PickLocationViewDO(23L, "J"));
		realData.add(new PickLocationViewDO(29L, "J"));

		return realData;
	}

	public static Map<String, List<Long>> getRealDataAsMap(final List<GADataEntry<Long, String>> realData) {
		return realData.stream()
			.collect(Collectors.groupingBy(GADataEntry::getGroupingParameter, Collectors.mapping(GADataEntry::getOptimizationParameter, Collectors.toList())));
	}
}
