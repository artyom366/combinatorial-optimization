package opt.gen.alg.service.strategy.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.Validate;

import opt.gen.alg.domain.GACandidate;
import opt.gen.alg.domain.GASolution;

//@Service("stochasticUniversalSamplingStrategy")
public class StochasticUniversalSamplingStrategyImpl extends MostDiversePopulationStrategyImpl {

	@Override
	public List<GACandidate<Long>> selection(final List<GACandidate<Long>> initialGeneration, final List<GACandidate<Long>> nextGeneration, final Map<String, GASolution<Long, String, Double>> result) {
		Validate.notEmpty(initialGeneration, "Initial generation is not defined");
		Validate.notEmpty(nextGeneration, "Next generation is not defined");
		Validate.notEmpty(result, "GA result is not defined");


		return null;
	}

}
