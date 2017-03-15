package opt.gen.alg.domain.impl;

import opt.gen.alg.domain.GAStatistics;

public final class Statistics implements GAStatistics {

	private int convergenceRetries;

	public Statistics() {
		this.convergenceRetries = 0;
	}

	@Override
	public int getCurrentConvergenceRetriesCount() {
		return convergenceRetries;
	}

	@Override
	public void incrementConvergenceRetryCount() {
		this.convergenceRetries++;
	}
	
	
}
