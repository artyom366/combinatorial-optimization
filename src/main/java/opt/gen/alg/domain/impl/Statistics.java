package opt.gen.alg.domain.impl;

import opt.gen.alg.domain.GASolution;
import opt.gen.alg.domain.GAStatistics;
import org.apache.commons.lang3.Validate;

import java.util.*;
import java.util.stream.Collectors;

public final class Statistics implements GAStatistics {

	private int convergenceRetries;

	private final List<Integer> convergences;
	private final List<Integer> combinations;

	private int currentIteration;
	private final Map<Integer, Info> info;

	private long start;
	private long end;

	public Statistics() {
		this.currentIteration = -1;
		this.convergenceRetries = 0;
		this.convergences = new LinkedList<>();
		this.combinations = new LinkedList<>();
		this.info = new HashMap<>();
		this.start = System.currentTimeMillis();
	}

	@Override
	public int getCurrentConvergenceRetriesCount() {
		return convergenceRetries;
	}

	@Override
	public void incrementConvergenceRetryCount() {
		this.convergenceRetries++;
	}

	@Override
	public void addConvergenceValue(final int value) {
		this.convergences.add(value);
	}

	@Override
	public void addCombinationTotalValue(final int value) {
		this.combinations.add(value);
	}

	@Override
	public List<Integer> getConvergences() {
		return Collections.unmodifiableList(convergences);
	}

	@Override
	public List<Integer> getCombinations() {
		return Collections.unmodifiableList(combinations);
	}

	@Override
	public void incrementCurrentIteration() {
		this.currentIteration++;
		this.info.put(currentIteration, new Info(currentIteration));
	}

	@Override
	public void setNewCombinationsCount(final int newCombinations) {
		getCurrentIterationInfo().setNewCombinationsCount(newCombinations);
	}

	@Override
	public void addToMutatedCount() {
		getCurrentIterationInfo().addToMutatedCount();
	}

	@Override
	public void setCorrectedCount(final int correctedCount) {
		getCurrentIterationInfo().setCorrectedCount(correctedCount);
	}

	@Override
	public void setCurrentRetriesCount(final int retriesCount) {
		getCurrentIterationInfo().setRetriesCount(retriesCount);
	}

	@Override
	public void setTotalCount(final int totalCount) {
		getCurrentIterationInfo().setTotalCount(totalCount);
	}

	@Override
	public void setTotalNonUniqueCount(final Map<String, GASolution<Long, String, Double>> result) {
		final long count = result.entrySet().stream().filter(value -> value.getValue().getRealDataSequenceIds().size() > 1).count();
		getCurrentIterationInfo().setTotalNonUniqueCount((int) count);
	}

	private Info getCurrentIterationInfo() {
		return Validate.notNull(this.info.get(this.currentIteration), "Info is not defined with key: %d", this.currentIteration);
	}

	@Override
	public List<Info> getRunnerInfo() {
		return this.info.entrySet().stream().map(Map.Entry::getValue).collect(Collectors.toList());
	}

	@Override
	public long getExecutionTimeInMillis() {
		this.end = System.currentTimeMillis();
		return this.end - this.start;
	}
}
