package opt.gen.alg.service.helper.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import opt.gen.alg.domain.GAPopulation;
import opt.gen.alg.domain.GASolution;

import org.apache.commons.lang3.Validate;
import org.springframework.stereotype.Service;

import com.google.common.collect.ImmutableSet;
import opt.gen.alg.domain.GADataEntry;
import opt.gen.alg.domain.impl.Population;
import opt.gen.alg.service.helper.GAService;

@Service("gaService")
public class GAServiceImpl implements GAService<Long, String, Double> {

	@Override
	public Set<Long> getGeneDictionary(final List<GADataEntry<Long, String>> gaRealData) {
		Validate.notEmpty(gaRealData, "Real data is not defined");
		return gaRealData.stream().map(GADataEntry::getOptimizationParameter).distinct().map(Long::valueOf)
			.collect(Collectors.collectingAndThen(Collectors.toSet(), ImmutableSet::copyOf));
	}

	@Override
	public long getPopulationSize(final Set<Long> geneDictionary, final double initialPopulationPercentileSize) {
		Validate.notEmpty(geneDictionary, "Gene dictionary is not defined");
		Validate.isTrue(initialPopulationPercentileSize > 0, "Initial percentile of population 0");
		return BigDecimal.valueOf(initialPopulationPercentileSize).multiply(BigDecimal.valueOf(geneDictionary.size())).longValue();
	}

	@Override
	public List<GAPopulation<Long, String, Double>> getRealPopulationGrouped(final List<GADataEntry<Long, String>> gaRealData) {
		Validate.notEmpty(gaRealData, "GA real data is nor defined");

		final Map<String, List<GADataEntry<Long, String>>> groups = gaRealData.stream().collect(Collectors.groupingBy(GADataEntry::getGroupingParameter));
		final List<GAPopulation<Long, String, Double>> population = new ArrayList<>();

		groups.entrySet().forEach(e -> {
			final List<Long> customerIds = new ArrayList<>();
			e.getValue().forEach(d -> customerIds.add(d.getOptimizationParameter()));

			final Double x = e.getValue().get(0).getCoordinateX();
			final Double y = e.getValue().get(0).getCoordinateY();

			population.add(new Population(e.getKey(), customerIds, x, y));
		});

		return population;
	}

	@Override
	public List<GASolution<Long, String, Double>> getResultAsList(final Map<String, GASolution<Long, String, Double>> result) {
		Validate.notEmpty(result, "Result map is not defined");
		return result.entrySet().stream().map(Map.Entry::getValue).collect(Collectors.toList());
	}

	@Override
	public String generateHash(final Set<Long> genes) {
		Validate.notEmpty(genes, "Genes are not defined");
		return generateHash(new TreeSet<>(genes));
	}

	@Override
	public String generateHash(final List<Long> genes) {
		Validate.notEmpty(genes, "Genes are not defined");
		return generateHash(new TreeSet<>(genes));
	}

	private String generateHash(final TreeSet<Long> genes) {
		return genes.stream().map(Object::toString).collect(Collectors.joining("."));
	}
}
