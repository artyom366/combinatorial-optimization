package opt.gen.alg.service.strategy.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;

import opt.gen.alg.domain.GACandidate;
import opt.gen.alg.domain.GASolution;
import opt.gen.alg.generator.RandomGenerator;

//@Service("gaExecutionStrategy")
public class TournamentSelectionStrategyImpl extends MostDiversePopulationStrategyImpl {

	@Override
	public List<GACandidate<Long>> selection(final List<GACandidate<Long>> initialGeneration, final Map<String, GASolution<Long, String, Double>> result) {

		Validate.notEmpty(initialGeneration, "Initial generation is not defined");
		Validate.notEmpty(result, "GA result is not defined");

		final int populationSize = initialGeneration.size();
		final List<GACandidate<Long>> winners = new ArrayList<>();

		for (int i = 0; i < populationSize; i++) {
			winners.add(getPairContestWinner(populationSize, initialGeneration, result));
		}

		return Collections.unmodifiableList(winners);
	}

	private GACandidate<Long> getPairContestWinner(final int populationSize, final List<GACandidate<Long>> initialGeneration,
			final Map<String, GASolution<Long, String, Double>> result) {

		final int randomIndexLeftContestant = RandomGenerator.generateUniformInt(populationSize);
		final int randomIndexRightContestant = RandomGenerator.generateUniformInt(populationSize);

		final GACandidate<Long> leftContestant = initialGeneration.get(randomIndexLeftContestant);
		final GACandidate<Long> rightContestant = initialGeneration.get(randomIndexRightContestant);

		final Pair<GACandidate<Long>, Integer> leftContestantWithFitnessValue = getContestantWithFitnessValue(leftContestant, result);
		final Pair<GACandidate<Long>, Integer> rightContestantWithFitnessValue = getContestantWithFitnessValue(rightContestant, result);

		return getContestWinner(leftContestantWithFitnessValue, rightContestantWithFitnessValue);
	}

	private Pair<GACandidate<Long>, Integer> getContestantWithFitnessValue(final GACandidate<Long> contestant,
			final Map<String, GASolution<Long, String, Double>> result) {
		final GASolution<Long, String, Double> individual = result.get(contestant.getHash());

		if (individual == null) {
			return new ImmutablePair<>(contestant, 0);
		}

		return new ImmutablePair<>(contestant, individual.getRealDataSequenceIds().size());
	}

	private GACandidate<Long> getContestWinner(final Pair<GACandidate<Long>, Integer> leftContestantWithFitnessValue,
			final Pair<GACandidate<Long>, Integer> rightContestantWithFitnessValue) {

		final Integer leftFitnessValue = leftContestantWithFitnessValue.getRight();
		final Integer rightFitnessValue = rightContestantWithFitnessValue.getRight();

		final GACandidate<Long> leftContestant = leftContestantWithFitnessValue.getLeft();
		final GACandidate<Long> rightContestant = rightContestantWithFitnessValue.getLeft();

		if (leftFitnessValue == rightFitnessValue.intValue()) {
			return getLongestChromosomeContestWinnerOrChooseAtRandom(leftContestant, rightContestant);

		}

		return leftFitnessValue > rightFitnessValue ? leftContestant : rightContestant;

	}

	private GACandidate<Long> getLongestChromosomeContestWinnerOrChooseAtRandom(final GACandidate<Long> leftContestant,
			final GACandidate<Long> rightContestant) {

		final int leftContestantLength = leftContestant.getGeneSequence().size();
		final int rightContestantLength = rightContestant.getGeneSequence().size();

		if (leftContestantLength == rightContestantLength) {
			return getRandomContestWinner(leftContestant, rightContestant);
		}

		return leftContestantLength > rightContestantLength ? leftContestant : rightContestant;
	}

	private GACandidate<Long> getRandomContestWinner(final GACandidate<Long> leftContestant, final GACandidate<Long> rightContestant) {

		final int randomFlag = RandomGenerator.generateUniformInt(2);

		if (randomFlag == 0) {
			return leftContestant;
		}

		return rightContestant;
	}

}
