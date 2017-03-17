package opt.gen.alg.domain.impl;

import opt.gen.alg.domain.GASolution;
import opt.gen.alg.domain.GAStatistics;
import org.apache.commons.lang3.Validate;

import java.util.*;

public final class Statistics implements GAStatistics {

	private int convergenceRetries;

	private final List<Integer> convergences;
	private final List<Integer> combinations;

	private int currentIteration;
	private int mutatedCount;


	private final Map<Integer, Info> info;

	public Statistics() {
		this.currentIteration = 0;
		this.convergenceRetries = 0;
		this.convergences = new LinkedList<>();
		this.combinations = new LinkedList<>();
		this.info = new HashMap<>();

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
	public void setNewCombinationsInfo(final int newCombinations) {
		getCurrentIterationInfo().setNewCombinationsCount(newCombinations);
	}

	@Override
	public void addToMutatedCountInfo() {
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
	public void setTotalNonUniqueCount(final GASolution<Long, String> result) {
		result.
	}

	private Info getCurrentIterationInfo() {
		return Validate.notNull(this.info.get(this.currentIteration), "Info is nor defined with key: %d", this.currentIteration);
	}


}
