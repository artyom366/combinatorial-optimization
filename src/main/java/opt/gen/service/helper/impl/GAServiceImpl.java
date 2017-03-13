package opt.gen.service.helper.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.springframework.stereotype.Service;

import com.google.common.collect.ImmutableSet;
import opt.gen.domain.GADataEntry;
import opt.gen.service.helper.GAService;

@Service("gaServiceImpl")
public class GAServiceImpl implements GAService<Long, String> {

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
	public Map<String, List<Long>> getRealPopulationAsGroupedMap(final List<GADataEntry<Long, String>> gaRealData) {
		Validate.notEmpty(gaRealData, "GA real data is nor defined");

		return gaRealData.stream()
			.collect(Collectors.groupingBy(GADataEntry::getGroupingParameter, Collectors.mapping(GADataEntry::getOptimizationParameter, Collectors.toList())));

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
		return StringUtils.join(genes, ".");
	}
}