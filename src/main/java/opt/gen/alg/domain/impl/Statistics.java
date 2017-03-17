package opt.gen.alg.domain.impl;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import opt.gen.alg.domain.GAStatistics;

public final class Statistics implements GAStatistics {

	private int convergenceRetries;
	private List<Integer> convergence;

	public Statistics() {
		this.convergenceRetries = 0;
		this.convergence = new LinkedList<>();
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
		this.convergence.add(value);
	}

	@Override
	public List<Integer> getConvergence() {
		return Collections.unmodifiableList(convergence);
	}
	
}
