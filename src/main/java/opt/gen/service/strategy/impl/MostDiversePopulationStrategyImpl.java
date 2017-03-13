package opt.gen.service.strategy.impl;

import opt.gen.domain.GACandidate;
import opt.gen.domain.GAStatistics;
import opt.gen.domain.impl.Chromosome;
import opt.gen.domain.impl.Statistics;
import opt.gen.error.IllegalChromosomeSizeException;
import opt.gen.generator.RandomGenerator;
import opt.gen.service.helper.GAService;
import opt.gen.service.strategy.GAStrategy;
import opt.gen.service.strategy.GAStrategyInfo;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

import static opt.gen.generator.RandomGenerator.*;
import static opt.gen.service.strategy.impl.MostDiversePopulationStrategyInfo.*;

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
    public List<GACandidate<Long>> initialization(final Set<Long> geneDictionary, final Map<String, List<Long>> realPopulation, final int geneSequenceMinSize,
                                                  final int geneSequenceMaxSize) {

        Validate.notEmpty(realPopulation, "GA real population is not defined");
        Validate.notEmpty(geneDictionary, "Gene dictionary is not defined");
        Validate.isTrue(geneSequenceMinSize > 0 && geneSequenceMaxSize > 0, "Gene min and max sizes should be greater than 0");
        Validate.isTrue(geneSequenceMinSize <= geneSequenceMaxSize, "Gene max size should be greater than min size");

        final long populationSize = gaService.getPopulationSize(geneDictionary, POPULATION_PERCENTILE_SIZE);
        Validate.isTrue(populationSize > 0, "Population size is 0");

        return getInitialPopulation(geneSequenceMinSize, geneSequenceMaxSize, populationSize, geneDictionary);
    }

    private List<GACandidate<Long>> getInitialPopulation(final int chromosomeMinSize, final int chromosomeMaxSize, final long populationSize,
                                                         final Set<Long> geneDictionary) {

        final long populationEvenSize = getEvenlyCorrectedPopulationSize(populationSize);
        return createRandomPopulation(chromosomeMinSize, chromosomeMaxSize, populationEvenSize, geneDictionary);
    }

    private long getEvenlyCorrectedPopulationSize(final long populationSize) {
        return populationSize % 2 != 0 ? populationSize + 1 : populationSize;
    }

    protected List<GACandidate<Long>> createRandomPopulation(final int chromosomeMinSize, final int chromosomeMaxSize, final long populationSize,
                                                             final Set<Long> geneSamples) {

        final List<GACandidate<Long>> chromosomes = new ArrayList<>();

        LongStream.range(0, populationSize).forEach(chromosome -> {
            final Set<Long> genes = LongStream.range(0, RandomGenerator.generateUniformLongInclusive(chromosomeMinSize, chromosomeMaxSize))
                    .mapToObj(gene -> RandomGenerator.generateUniformLong(geneSamples.size())).map(Long::valueOf).collect(Collectors.toSet());

            if (genes.size() < 2) {
                int i = 0;
            }

            final String hash = gaService.generateHash(genes);
            chromosomes.add(new Chromosome(genes, hash));
        });

        return Collections.unmodifiableList(chromosomes);
    }


    @Override
    public List<GACandidate<Long>> crossover(final List<GACandidate<Long>> parentGeneration) {
        Validate.notEmpty(parentGeneration, "Parent generation is not defined");
        Validate.isTrue(parentGeneration.size() % 2 == 0, "Parent generation size is not even");

        final List<Pair<GACandidate<Long>, GACandidate<Long>>> parents = getParents(parentGeneration);
        return getChildren(parents);
    }

    private List<Pair<GACandidate<Long>, GACandidate<Long>>> getParents(final List<GACandidate<Long>> parentGeneration) {
        return IntStream.range(1, parentGeneration.size()).filter(this::isOddIndex)
                .mapToObj(i -> new ImmutablePair<>(parentGeneration.get(i - 1), parentGeneration.get(i))).collect(Collectors.toList());
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

            if (leftSize < 2 || rightSize < 2) {
                int i = 0;
            }

            final int crossoverLine = getGeneSequenceCrossoverIndex(leftSize, rightSize);

            final Pair<List<Long>, List<Long>> leftHeadAndTail = splitGenesToHeadAndTail(leftGeneSequence, crossoverLine);
            final Pair<List<Long>, List<Long>> rightHeadAndTail = splitGenesToHeadAndTail(rightGeneSequence, crossoverLine);

            final Pair<GACandidate<Long>, GACandidate<Long>> children =
                    genesSwap(leftHeadAndTail.getLeft(), leftHeadAndTail.getRight(), rightHeadAndTail.getLeft(), rightHeadAndTail.getRight());

            if (children.getLeft().getGeneSequence().size() == 1 || children.getRight().getGeneSequence().size() == 1) {
                int i = 0;
            }

            nextGeneration.add(children.getLeft());
            nextGeneration.add(children.getRight());
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
        Validate.isTrue(geneSequence.size() > 1, "Gene sequence size should be greater than one");
        Validate.isTrue(splitIndex > 0, "Gene split index should not be zero");

        final List<Long> head = IntStream.range(0, splitIndex).mapToObj(geneSequence::get).collect(Collectors.toList());
        final List<Long> tail = IntStream.range(splitIndex, geneSequence.size()).mapToObj(geneSequence::get).collect(Collectors.toList());

        return new ImmutablePair<>(head, tail);
    }

    private Pair<GACandidate<Long>, GACandidate<Long>> genesSwap(final List<Long> leftHead, final List<Long> leftTail,
                                                                 final List<Long> rightHead, final List<Long> rightTail) {

        leftHead.addAll(rightTail);
        rightHead.addAll(leftTail);

        final Set<Long> leftGenes = new HashSet<>(leftHead);
        final Set<Long> rightGenes = new HashSet<>(rightHead);

        final GACandidate<Long> leftChild = new Chromosome(leftGenes, gaService.generateHash(leftGenes));
        final GACandidate<Long> rightChild = new Chromosome(rightGenes, gaService.generateHash(rightGenes));

        return new ImmutablePair<>(leftChild, rightChild);
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
    public List<GACandidate<Long>> correction(final List<GACandidate<Long>> mutatedGeneration, final List<GACandidate<Long>> initialGeneration) {
        Validate.notEmpty(mutatedGeneration, "Mutated generation is not defined");
        return mutatedGeneration;
    }
}