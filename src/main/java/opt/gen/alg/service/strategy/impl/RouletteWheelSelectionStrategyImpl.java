package opt.gen.alg.service.strategy.impl;

import java.util.*;

import com.google.common.collect.*;
import opt.gen.alg.generator.RandomGenerator;
import org.apache.commons.lang3.Validate;

import opt.gen.alg.domain.GACandidate;
import opt.gen.alg.domain.GASolution;
import org.springframework.stereotype.Service;

@Service("gaExecutionStrategy")
public class RouletteWheelSelectionStrategyImpl extends MostDiversePopulationStrategyImpl {

	@Override
	public List<GACandidate<Long>> selection(final List<GACandidate<Long>> initialGeneration,
											 final Map<String, GASolution<Long, String, Double>> result) {

		Validate.notEmpty(initialGeneration, "Initial generation is not defined");
		Validate.notEmpty(result, "GA result is not defined");

		final int populationSize = initialGeneration.size();
		final int totalFitnessValue = getTotalFitnessValue(result);
		final Map<Double, GACandidate<Long>> individualFitnessRatioValues = new TreeMap<>();

		double fitnessInterval = 0;

		for (final GACandidate<Long> entry : initialGeneration) {
			final GASolution<Long, String, Double> individual = result.get(entry.getHash());

			if (individual == null) {
				continue;
			}

			final int fitnessValue = individual.getRealDataSequenceIds().size();
			fitnessInterval += (double)fitnessValue / totalFitnessValue;
			individualFitnessRatioValues.put(fitnessInterval, entry);
		}

		return selection(individualFitnessRatioValues, populationSize);
	}

	private int getTotalFitnessValue(final Map<String, GASolution<Long, String, Double>> result) {
		return result.entrySet().stream().mapToInt(e -> e.getValue().getRealDataSequenceIds().size()).sum();
	}

	private List<GACandidate<Long>> selection(final Map<Double, GACandidate<Long>> individualFitnessRatioValues, final int populationSize) {

		final double intervalMax = getIntervalMax(individualFitnessRatioValues);
		final List<Double> intervals = new ArrayList<>(individualFitnessRatioValues.keySet());

		final List<GACandidate<Long>> chosenIndividuals = new ArrayList<>();

		for (int i = 0; i < populationSize; i++) {
			final double randomInterval = RandomGenerator.generateUniformDouble(intervalMax);

			for (int j = 1; j < intervals.size(); j++) {
				final double intervalStart = intervals.get(j - 1);
				final double intervalEnd = intervals.get(j);

				if (randomInterval > intervalStart && randomInterval <= intervalEnd) {
					final GACandidate<Long> individual = individualFitnessRatioValues.get(intervalEnd);
					chosenIndividuals.add(individual);
					break;

				} else if (randomInterval >= 0 && randomInterval < intervalStart) {
					final GACandidate<Long> individual = individualFitnessRatioValues.get(intervalStart);
					chosenIndividuals.add(individual);
					break;
				}
			}
		}

		return Collections.unmodifiableList(chosenIndividuals);
	}

	private double getIntervalMax(final Map<Double, GACandidate<Long>> individualFitnessRatioValues) {
		final Set<Double> keys = individualFitnessRatioValues.keySet();
		return Iterables.getLast(keys);
	}

}
