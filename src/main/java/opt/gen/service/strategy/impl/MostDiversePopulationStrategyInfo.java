package opt.gen.service.strategy.impl;

import opt.gen.service.strategy.GAStrategyInfo;

public final class MostDiversePopulationStrategyInfo implements GAStrategyInfo {

	public static final int MIN_CHROMOSOME_SIZE = 2;
	public static final int MAX_CHROMOSOME_SIZE = 4;
	public static final double POPULATION_PERCENTILE_SIZE = 1d;
	public static final double PROBABILITY_OF_BECOMING_A_PARENT = 1d;
	public static final int CONVERGENCE_RETRY_THRESHOLD = 5;
	public static final int CONVERGENCE_RETRIES= 5;
	public static final int PARALLELISM_LEVEL = 4;
	public static final double PROBABILITY_OF_MUTATION = 0.05d;
	public static final int CROSSOVER_INDEX_LOWER_BOUND = 1;

	@Override
	public int getConvergenceRetryThreshold() {
		return CONVERGENCE_RETRY_THRESHOLD;
	}

	@Override
	public int getConvergenceRetries() {
		return CONVERGENCE_RETRIES;
	}
}
