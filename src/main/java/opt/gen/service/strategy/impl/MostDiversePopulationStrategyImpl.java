package opt.gen.service.strategy.impl;

import static opt.gen.generator.RandomGenerator.generateUniformDouble;
import static opt.gen.generator.RandomGenerator.generateUniformInt;
import static opt.gen.service.strategy.impl.MostDiversePopulationStrategyInfo.CROSSOVER_INDEX_LOWER_BOUND;
import static opt.gen.service.strategy.impl.MostDiversePopulationStrategyInfo.MAX_CHROMOSOME_SIZE;
import static opt.gen.service.strategy.impl.MostDiversePopulationStrategyInfo.MIN_CHROMOSOME_SIZE;
import static opt.gen.service.strategy.impl.MostDiversePopulationStrategyInfo.POPULATION_PERCENTILE_SIZE;
import static opt.gen.service.strategy.impl.MostDiversePopulationStrategyInfo.PROBABILITY_OF_MUTATION;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.ImmutableSet;
import opt.gen.domain.GACandidate;
import opt.gen.domain.GAStatistics;
import opt.gen.domain.impl.Chromosome;
import opt.gen.domain.impl.Statistics;
import opt.gen.error.IllegalChromosomeSizeException;
import opt.gen.generator.RandomGenerator;
import opt.gen.service.helper.GAService;
import opt.gen.service.strategy.GAStrategy;
import opt.gen.service.strategy.GAStrategyInfo;

@Service("mostDiversePopulationStrategy")
public class MostDiversePopulationStrategyImpl implements GAStrategy<Long, String> {

	@Autowired
	private GAService<Long, String> gaService;

	private GAStatistics statistics;
	private GAStrategyInfo info;

	public MostDiversePopulationStrategyImpl() {
		statistics = new Statistics();
		info = new MostDiversePopulationStrategyInfo();
	}

	@Override
	public GAStatistics getStatistics() {
		return statistics;
	}

	@Override
	public GAStrategyInfo getInfo() {
		return info;
	}

	@Override
	public List<GACandidate<Long>> initialization(final Set<Long> geneDictionary, final Map<String, List<Long>> realPopulation) {
		Validate.notEmpty(realPopulation, "GA real population is not defined");
		Validate.notEmpty(geneDictionary, "Gene dictionary is not defined");

		final long populationSize = gaService.getPopulationSize(geneDictionary, POPULATION_PERCENTILE_SIZE);
		Validate.isTrue(populationSize > 0, "Population size is 0");

		return getInitialPopulation(populationSize, geneDictionary);
	}

	private List<GACandidate<Long>> getInitialPopulation(final long populationSize, final Set<Long> geneDictionary) {

		final long populationEvenSize = getEvenlyCorrectedPopulationSize(populationSize);
		return createRandomPopulation(populationEvenSize, geneDictionary);
	}

	private long getEvenlyCorrectedPopulationSize(final long populationSize) {
		return populationSize % 2 != 0 ? populationSize + 1 : populationSize;
	}

	protected List<GACandidate<Long>> createRandomPopulation(final long populationSize, final Set<Long> geneDictionary) {

		final List<Long> geneSamples = new ArrayList<>(geneDictionary);
		final List<GACandidate<Long>> chromosomes = new ArrayList<>();

		LongStream.range(0, populationSize).forEach(chromosome -> {
			final Set<Long> genes = getGenes(geneSamples);
			chromosomes.add(new Chromosome(genes, gaService.generateHash(genes)));
		});

		return Collections.unmodifiableList(chromosomes);
	}

	private Set<Long> getGenes(final List<Long> geneSamples) {
		final Set<Long> genes = new HashSet<>();
		final long chromosomeSize = RandomGenerator.generateUniformLongInclusive(MIN_CHROMOSOME_SIZE, MAX_CHROMOSOME_SIZE);

		while (genes.size() < chromosomeSize) {
			final int sampleIndex = RandomGenerator.generateUniformInt(geneSamples.size());
			genes.add(geneSamples.get(sampleIndex));
		}

		assert genes.size() >= MIN_CHROMOSOME_SIZE && genes.size() <= MAX_CHROMOSOME_SIZE;
		return Collections.unmodifiableSet(genes);
	}

	@Override
	public List<GACandidate<Long>> crossover(final List<GACandidate<Long>> parentGeneration) {
		Validate.notEmpty(parentGeneration, "Parent generation is not defined");
		Validate.isTrue(parentGeneration.size() % 2 == 0, "Parent generation size is not even");

		final List<Pair<GACandidate<Long>, GACandidate<Long>>> parents = getParents(parentGeneration);
		return getChildren(parents);
	}

	private List<Pair<GACandidate<Long>, GACandidate<Long>>> getParents(final List<GACandidate<Long>> parentGeneration) {
		return IntStream.range(1, parentGeneration.size()).filter(this::isOddIndex).mapToObj(
				i -> new ImmutablePair<>(parentGeneration.get(i - 1), parentGeneration.get(i))).collect(Collectors.toList());
	}

	private boolean isOddIndex(final int index) {
		return index % 2 == 1;
	}

	private List<GACandidate<Long>> getChildren(final List<Pair<GACandidate<Long>, GACandidate<Long>>> parents) {

		final List<GACandidate<Long>> nextGeneration = new ArrayList<>();

		parents.forEach(pair -> {

			final List<Long> leftGeneSequence = getParentGenesSequenceAsList(pair.getLeft());
			final List<Long> rightGeneSequence = getParentGenesSequenceAsList(pair.getRight());

			final int leftSize = leftGeneSequence.size();
			final int rightSize = rightGeneSequence.size();

			if (!(leftSize > 1 || rightSize > 1)) {
				int i = 0;
			}

			final int crossoverLine = getGeneSequenceCrossoverIndex(leftSize, rightSize);

			final Pair<List<Long>, List<Long>> leftHeadAndTail = splitGenesToHeadAndTail(leftGeneSequence, crossoverLine);
			final Pair<List<Long>, List<Long>> rightHeadAndTail = splitGenesToHeadAndTail(rightGeneSequence, crossoverLine);

			final Pair<GACandidate<Long>, GACandidate<Long>> children = genesSwap(leftHeadAndTail.getLeft(), leftHeadAndTail.getRight(),
																				  rightHeadAndTail.getLeft(), rightHeadAndTail.getRight());

			if (!children.getLeft().getGeneSequence().isEmpty()) {
				nextGeneration.add(children.getLeft());
			}

			if (!children.getRight().getGeneSequence().isEmpty()) {
				nextGeneration.add(children.getRight());
			}
		});

		return nextGeneration;
	}

	private List<Long> getParentGenesSequenceAsList(final GACandidate<Long> parent) {
		return Collections.unmodifiableList(new ArrayList<>(parent.getGeneSequence()));
	}

	private int getGeneSequenceCrossoverIndex(final int leftSize, final int rightSize) {
		final int minimalSize = leftSize >= rightSize ? rightSize : leftSize;
		return minimalSize == CROSSOVER_INDEX_LOWER_BOUND ? CROSSOVER_INDEX_LOWER_BOUND : generateUniformInt(CROSSOVER_INDEX_LOWER_BOUND, minimalSize);
	}

	private Pair<List<Long>, List<Long>> splitGenesToHeadAndTail(final List<Long> geneSequence, final int splitIndex) {
		final List<Long> head = IntStream.range(0, splitIndex).mapToObj(geneSequence::get).collect(Collectors.toList());
		final List<Long> tail = IntStream.range(splitIndex, geneSequence.size()).mapToObj(geneSequence::get).collect(Collectors.toList());

		return new ImmutablePair<>(head, tail);
	}

	private Pair<GACandidate<Long>, GACandidate<Long>> genesSwap(final List<Long> leftHead, final List<Long> leftTail, final List<Long> rightHead,
																 final List<Long> rightTail) {
		final GACandidate<Long> leftChild = getChildGeneSequence(leftHead, rightTail);
		final GACandidate<Long> rightChild = getChildGeneSequence(rightHead, leftTail);
		return new ImmutablePair<>(leftChild, rightChild);
	}

	private GACandidate<Long> getChildGeneSequence(final List<Long> head, final List<Long> tail) {

		if (Collections.disjoint(head, tail)) {
			final ImmutableSet<Long> geneSequence = Stream.concat(head.stream(), tail.stream()).map(Long::valueOf).collect(
					Collectors.collectingAndThen(Collectors.toSet(), ImmutableSet::copyOf));
			return new Chromosome(geneSequence, gaService.generateHash(geneSequence));
		}

		return new Chromosome(Collections.EMPTY_SET, StringUtils.EMPTY);
	}

	@Override
	public List<GACandidate<Long>> selection(final List<GACandidate<Long>> initialGeneration, final List<GACandidate<Long>> nextGeneration) {
		Validate.notEmpty(nextGeneration, "Next generation is not defined");
		return nextGeneration;
	}

	@Override
	public List<GACandidate<Long>> mutation(final List<GACandidate<Long>> refinedGeneration, final Set<Long> geneDictionary) {
		Validate.notEmpty(refinedGeneration, "Refined generation is not defined");
		Validate.notEmpty(geneDictionary, "Gene dictionary is not defined");

		final List<Long> geneSamples = new ArrayList<>(geneDictionary);
		final List<GACandidate<Long>> mutatedGeneration = new ArrayList<>();

		for (final GACandidate<Long> candidate : refinedGeneration) {

			if (isMutationEventOccurred()) {
				final GACandidate<Long> mutatedCandidate = mutate(candidate, geneSamples);
				addCandidateToMutedGeneration(mutatedGeneration, mutatedCandidate);

			} else {
				addCandidateToMutedGeneration(mutatedGeneration, candidate);
			}
		}

		return Collections.unmodifiableList(mutatedGeneration);
	}

	private boolean isMutationEventOccurred() {
		return generateUniformDouble() <= PROBABILITY_OF_MUTATION;
	}

	private GACandidate<Long> mutate(final GACandidate<Long> candidate, final List<Long> geneSamples) {
		final List<Long> genes = Collections.unmodifiableList(new ArrayList<>(candidate.getGeneSequence()));
		return getNewCandidate(addOrRemoveRandomGene(genes, geneSamples));
	}

	private boolean isGeneSequenceSizeMax(final int size) {
		return size == MAX_CHROMOSOME_SIZE;
	}

	private boolean isGeneSizeSequenceSizeIsInRange(final int size) {
		return size < MAX_CHROMOSOME_SIZE && size > MIN_CHROMOSOME_SIZE;
	}

	private boolean isIllegalGeneSequenceSize(final int size) {
		return size > MAX_CHROMOSOME_SIZE && size < MIN_CHROMOSOME_SIZE;
	}

	private List<Long> addOrRemoveRandomGene(final List<Long> genes, final List<Long> geneSamples) {
		final int candidateGeneSequenceSize = genes.size();

		if (isGeneSequenceSizeMax(candidateGeneSequenceSize)) {
			final int index = generateUniformInt(MIN_CHROMOSOME_SIZE, MAX_CHROMOSOME_SIZE);
			return removeRandomGene(genes, index);
		}

		if (isGeneSizeSequenceSizeIsInRange(candidateGeneSequenceSize)) {
			return addRandomGene(genes, geneSamples);
		}

		if (isIllegalGeneSequenceSize(candidateGeneSequenceSize)) {
			throw new IllegalChromosomeSizeException(candidateGeneSequenceSize, MIN_CHROMOSOME_SIZE, MAX_CHROMOSOME_SIZE);
		}

		return genes;
	}

	private List<Long> removeRandomGene(final List<Long> genes, final int index) {
		return Collections.unmodifiableList(IntStream.range(0, genes.size()).filter(i -> i != index).mapToObj(genes::get).collect(Collectors.toList()));
	}

	private List<Long> addRandomGene(final List<Long> genes, final List<Long> geneSamples) {

		final List<Long> newGenes = new ArrayList<>(genes);

		do {
			final Long gene = geneSamples.get(generateUniformInt(MIN_CHROMOSOME_SIZE, MAX_CHROMOSOME_SIZE));

			if (isNewGeneUniqueInCandidateGeneSequence(genes, gene)) {
				newGenes.add(gene);
				return Collections.unmodifiableList(newGenes);
			}

		} while (true);
	}

	private boolean isNewGeneUniqueInCandidateGeneSequence(final List<Long> genes, final Long gene) {
		return !genes.contains(gene);
	}

	private void addCandidateToMutedGeneration(final List<GACandidate<Long>> mutatedGeneration, final GACandidate<Long> candidate) {
		mutatedGeneration.add(candidate);
	}

	private GACandidate<Long> getNewCandidate(final List<Long> genes) {
		return new Chromosome(new HashSet<>(genes), gaService.generateHash(genes));
	}

	@Override
	public List<GACandidate<Long>> correction(final List<GACandidate<Long>> mutatedGeneration, final List<GACandidate<Long>> initialGeneration,
											  final Set<Long> geneDictionary) {

		Validate.notEmpty(mutatedGeneration, "Mutated generation is not defined");
		Validate.notEmpty(initialGeneration, "Initial generation is not defined");
		Validate.notEmpty(geneDictionary, "Gene dictionary is not defined");

		final int targetPopulationSize = initialGeneration.size();
		final int currentPopulationSize = mutatedGeneration.size();
		final int populationShortage = targetPopulationSize - currentPopulationSize;
		final List<GACandidate<Long>> missingCandidates = createRandomPopulation(populationShortage, geneDictionary);

		return Collections.unmodifiableList(Stream.concat(missingCandidates.stream(), mutatedGeneration.stream()).collect(Collectors.toList()));
	}
}
