package opt.gen.alg.service.strategy.impl;

import java.util.*;
import java.util.stream.Collectors;

import com.google.common.collect.*;
import opt.gen.alg.error.EmptyIntervalException;
import opt.gen.alg.generator.RandomGenerator;
import org.apache.commons.collections4.MultiMap;
import org.apache.commons.lang3.Validate;

import opt.gen.alg.domain.GACandidate;
import opt.gen.alg.domain.GASolution;
import org.springframework.util.MultiValueMap;

//@Service("stochasticUniversalSamplingStrategy")
public class StochasticUniversalSamplingStrategyImpl extends MostDiversePopulationStrategyImpl {

	@Override
	public List<GACandidate<Long>> selection(final List<GACandidate<Long>> initialGeneration,
											 final Map<String, GASolution<Long, String, Double>> result) {

		Validate.notEmpty(initialGeneration, "Initial generation is not defined");
		Validate.notEmpty(result, "GA result is not defined");

		final int totalFitnessValue = getTotalFitnessValue(result);
		final Multimap<Double, GACandidate<Long>> individualFitnessRatioValues = ArrayListMultimap.create();

		initialGeneration.forEach(e -> {
			final GASolution<Long, String, Double> individual = result.get(e.getHash());
			final int fitnessValue = individual.getRealDataSequenceIds().size();

			final double fitnessRatio = (double)fitnessValue / totalFitnessValue;
			individualFitnessRatioValues.put(fitnessRatio, e);
		});

		selection(individualFitnessRatioValues);

		return null;
	}

	private int getTotalFitnessValue(final Map<String, GASolution<Long, String, Double>> result) {
		return result.entrySet().stream().mapToInt(e -> e.getValue().getRealDataSequenceIds().size()).sum();
	}

	private Optional<Integer> getMaxFitnessValue(final Map<String, GASolution<Long, String, Double>> result) {
		final List<Integer> fitnessValues = result.entrySet().stream().
				mapToInt(e -> e.getValue().getRealDataSequenceIds().size()).mapToObj(v -> v).collect(Collectors.toList());

		final OptionalInt maxFitnessValue = fitnessValues.stream().mapToInt(Integer::intValue).max();

		if (maxFitnessValue.isPresent()) {
			return Optional.of(maxFitnessValue.getAsInt());
		}

		return Optional.empty();
	}

	private void selection(final Multimap<Double, GACandidate<Long>> individualFitnessRatioValues) {

		final List<Double> intervals = getIntervals(individualFitnessRatioValues.keySet());
		final List<Double> selectedIntervals = new ArrayList<>();

		final Multiset<Double> keys = individualFitnessRatioValues.keys();

		keys.stream().forEach(k -> {
			final Collection<GACandidate<Long>> candidates = individualFitnessRatioValues.get(k);

			candidates.forEach(e -> {
				final double randomIntervalValue = RandomGenerator.generateUniformDouble();
				final double interval = getInterval(intervals, randomIntervalValue);
				selectedIntervals.add(interval);
			});
		});

		selection(individualFitnessRatioValues, selectedIntervals);
	}

	private List<Double> getIntervals(final Set<Double> fitnessRatioValues) {
		final List<Double> intervals = new ArrayList<>(fitnessRatioValues);
		Collections.sort(intervals);
		return Collections.unmodifiableList(intervals);
	}

	private double getInterval(final List<Double> intervals, final double randomIntervalValue) {
		final Iterator<Double> iterator = intervals.iterator();

		while (iterator.hasNext()) {
			final Double interval = iterator.next();

			if (randomIntervalValue >= interval) {
				if (iterator.hasNext()) {
					return iterator.next();
				}

				return interval;
			}
		}

		throw new EmptyIntervalException();
	}

	private void selection(final Multimap<Double, GACandidate<Long>> individualFitnessRatioValues, final List<Double> selectedIntervals) {



	}

}
