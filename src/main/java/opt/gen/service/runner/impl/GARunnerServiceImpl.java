package opt.gen.service.runner.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.Validate;
import org.springframework.stereotype.Service;

import opt.gen.domain.GACandidate;
import opt.gen.domain.GASolution;
import opt.gen.domain.impl.Result;
import opt.gen.service.runner.GARunnerService;
import opt.gen.service.strategy.GAStrategy;

@Service("gaRunnerService")
public class GARunnerServiceImpl implements GARunnerService<Long, String> {

	private final GAStrategy<Long, String> strategy;

	public GARunnerServiceImpl(final GAStrategy<Long, String> strategy) {
		this.strategy = strategy;
	}

	@Override
	public Map<String, GASolution<Long, String>> run(final Set<Long> geneDictionary, final Map<String, List<Long>> realPopulation) {

		Validate.notEmpty(realPopulation, "GA real population is not defined");

		final List<GACandidate<Long>> initialPopulation =
			Validate.notEmpty(strategy.initialization(geneDictionary, realPopulation), "Initial population is not defined");

		final Map<String, GASolution<Long, String>> result = new HashMap<>();
		run(realPopulation, initialPopulation, geneDictionary, result, result.size());

		return result;
	}

	private void run(final Map<String, List<Long>> realPopulation, final List<GACandidate<Long>> initialGeneration, final Set<Long> geneDictionary,
			final Map<String, GASolution<Long, String>> result, final int diversityDifference) {

		// final ForkJoinPool forkJoinPool = new
		// ForkJoinPool(strategy.getParallelismLevel());
		// forkJoinPool.submit(() -> {
		estimateFitnessFrequency(realPopulation, initialGeneration, result);
		// });

		if (convergenceIsNotReached(result.size(), diversityDifference)) {
			run(realPopulation, getNextGeneration(initialGeneration, geneDictionary), geneDictionary, result, result.size());
		}
	}

	private boolean convergenceIsNotReached(final int resultSize, final int diversityDifference) {

		if (convergenceThresholdIsNotReached(resultSize, diversityDifference)) {
			return true;
		}

		if (canRetry()) {
			strategy.getStatistics().incrementConvergenceRetryCount();
			return true;
		}

		return false;
	}

	private boolean convergenceThresholdIsNotReached(final int resultSize, final int diversityDifference) {
		return resultSize - diversityDifference > strategy.getInfo().getConvergenceRetryThreshold();
	}

	private boolean canRetry() {
		return strategy.getInfo().getConvergenceRetryThreshold() >= strategy.getStatistics().getCurrentConvergenceRetriesCount();
	}

	private void estimateFitnessFrequency(final Map<String, List<Long>> realPopulation, final List<GACandidate<Long>> nextGeneration,
			final Map<String, GASolution<Long, String>> result) {

		nextGeneration.stream().forEach(candidate -> {
			final List<String> realSequenceIds = new ArrayList<>();

			realPopulation.forEach((id, real) -> {
				if (contains(real, candidate.getGeneSequence())) {
					realSequenceIds.add(id);
				}
			});

			if (!realSequenceIds.isEmpty()) {
				result.put(candidate.getHash(), new Result(candidate, realSequenceIds));
			}
		});
	}

	private boolean contains(final List<Long> realGeneSequence, final Set<Long> individualGeneSequence) {
		return realGeneSequence.containsAll(individualGeneSequence);
	}

	private List<GACandidate<Long>> getNextGeneration(final List<GACandidate<Long>> initialGeneration, final Set<Long> geneDictionary) {

		final List<GACandidate<Long>> nextGeneration = strategy.crossover(initialGeneration);
		Validate.notEmpty(nextGeneration, "Next generation is not defined");

		final List<GACandidate<Long>> refinedGeneration = strategy.selection(initialGeneration, nextGeneration);
		Validate.notEmpty(refinedGeneration, "Refined generation is not defined");

		final List<GACandidate<Long>> mutatedGeneration = strategy.mutation(refinedGeneration, geneDictionary);
		Validate.notEmpty(mutatedGeneration, "Mutated generation is not defined");

		final List<GACandidate<Long>> correctedGeneration = strategy.correction(mutatedGeneration, initialGeneration);
		Validate.notEmpty(correctedGeneration, "Corrected generation is not defined");

		return correctedGeneration;
	}
}
