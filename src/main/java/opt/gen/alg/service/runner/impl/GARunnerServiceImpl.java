package opt.gen.alg.service.runner.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.math3.util.DoubleArray;
import org.springframework.stereotype.Service;

import opt.gen.alg.domain.GACandidate;
import opt.gen.alg.domain.GAPopulation;
import opt.gen.alg.domain.GASolution;
import opt.gen.alg.domain.impl.Result;
import opt.gen.alg.service.runner.GARunnerService;
import opt.gen.alg.service.strategy.GAStrategy;

@Service("gaRunnerService")
public class GARunnerServiceImpl implements GARunnerService<Long, String, Double> {

	private final GAStrategy<Long, String> strategy;

	public GARunnerServiceImpl(final GAStrategy<Long, String> strategy) {
		this.strategy = strategy;
	}

	@Override
	public Map<String, GASolution<Long, String, Double>> run(final Set<Long> geneDictionary, final List<GAPopulation<Long, String, Double>> realPopulationGroups) {
		Validate.notEmpty(realPopulationGroups, "GA real population is not defined");

		strategy.init();

		final List<GACandidate<Long>> initialPopulation = Validate.notEmpty(strategy.initialization(geneDictionary), "Initial population is not defined");

		final Map<String, GASolution<Long, String, Double>> result = new HashMap<>();
		run(realPopulationGroups, initialPopulation, geneDictionary, result, result.size());

		return result;
	}

	private void run(final List<GAPopulation<Long, String, Double>> realPopulationGroups, final List<GACandidate<Long>> initialGeneration, final Set<Long> geneDictionary,
			final Map<String, GASolution<Long, String, Double>> result, final int diversityDifference) {

		// final ForkJoinPool forkJoinPool = new
		// ForkJoinPool(strategy.getParallelismLevel());
		// forkJoinPool.submit(() -> {
		estimateFitnessFrequency(realPopulationGroups, initialGeneration, result);
		// });

		strategy.getStatistics().incrementCurrentIteration();
		strategy.getStatistics().setNewCombinationsCount(result.size() - diversityDifference);
		strategy.getStatistics().setCurrentRetriesCount(strategy.getStatistics().getCurrentConvergenceRetriesCount());
		strategy.getStatistics().setTotalCount(result.size());
		strategy.getStatistics().setTotalNonUniqueCount(result);

		strategy.getStatistics().addConvergenceValue(result.size() - diversityDifference);
		strategy.getStatistics().addCombinationTotalValue(result.size());

		if (convergenceIsNotReached(result.size(), diversityDifference)) {
			run(realPopulationGroups, getNextGeneration(initialGeneration, geneDictionary), geneDictionary, result, result.size());
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

	private void estimateFitnessFrequency(final List<GAPopulation<Long, String, Double>> realPopulationGroups, final List<GACandidate<Long>> nextGeneration,
			final Map<String, GASolution<Long, String, Double>> result) {

		nextGeneration.stream().forEach(candidate -> {
			final Map<Pair<Double, Double>, String> realSequenceIds = new HashMap<>();

			realPopulationGroups.forEach(real -> {
				if (contains(real.getOptimizationParameters(), candidate.getGeneSequence())) {
					realSequenceIds.put(new ImmutablePair<>(real.getCoordinateX(), real.getCoordinateY()), real.getGroupingParameter());
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

		final List<GACandidate<Long>> correctedGeneration = strategy.correction(mutatedGeneration, initialGeneration, geneDictionary);
		Validate.notEmpty(correctedGeneration, "Corrected generation is not defined");

		return correctedGeneration;
	}
}
