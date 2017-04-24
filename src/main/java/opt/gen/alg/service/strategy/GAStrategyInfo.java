package opt.gen.alg.service.strategy;

public interface GAStrategyInfo {

	int getConvergenceRetryThreshold();

	int getConvergenceRetries();

	double getNeighboursDistance();

	int getParallelismLevel();
}
