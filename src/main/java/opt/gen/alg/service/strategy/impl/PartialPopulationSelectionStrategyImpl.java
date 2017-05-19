package opt.gen.alg.service.strategy.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.Validate;

import opt.gen.alg.domain.GACandidate;
import opt.gen.alg.domain.GASolution;

//@Service("gaExecutionStrategy")
public class PartialPopulationSelectionStrategyImpl extends RandomSelectionStrategyImpl {

	//@Override
	public List<GACandidate<Long>> selection(final List<GACandidate<Long>> initialGeneration, final List<GACandidate<Long>> nextGeneration, final Map<String, GASolution<Long, String, Double>> result) {
		Validate.notEmpty(initialGeneration, "Initial generation is not defined");
		Validate.notEmpty(nextGeneration, "Next generation is not defined");

		final List<GACandidate<Long>> populationHead = initialGeneration.subList(0, initialGeneration.size() / 2);
		final List<GACandidate<Long>> populationTail = initialGeneration.subList(nextGeneration.size() / 2, nextGeneration.size());
		final List<GACandidate<Long>> population = new ArrayList<>();

		population.addAll(populationHead);
		population.addAll(populationTail);
		return Collections.unmodifiableList(population);
	}
}
