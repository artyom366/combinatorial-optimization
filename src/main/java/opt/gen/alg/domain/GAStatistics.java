package opt.gen.alg.domain;

import opt.gen.alg.domain.impl.Info;

import java.util.List;
import java.util.Map;

public interface GAStatistics {

	int getCurrentConvergenceRetriesCount();

	void incrementConvergenceRetryCount();

	void addConvergenceValue(int value);

	void addCombinationTotalValue(int value);

	List<Integer> getConvergences();

	List<Integer> getCombinations();

	void incrementCurrentIteration();

	void setNewCombinationsCount(int newCombinations);

	void addToMutatedCount();

	void setCorrectedCount(int correctedCount);

	void setCurrentRetriesCount(int retriesCount);

	void setTotalCount(int totalCount);

    void setTotalNonUniqueCount(Map<String, GASolution<Long, String, Double>> result);

	List<Info> getRunnerInfo();

	long getExecutionTimeInMillis();
}
