package opt.gen.alg.domain;

import java.util.List;

public interface GAStatistics {

	int getCurrentConvergenceRetriesCount();

	void incrementConvergenceRetryCount();

	void addConvergenceValue(int value);

	void addCombinationTotalValue(int value);

	List<Integer> getConvergences();

	List<Integer> getCombinations();

	void incrementCurrentIteration();

	void setNewCombinationsInfo(int newCombinations);

	void addToMutatedCountInfo();

	void setCorrectedCount(int correctedCount);

	void setCurrentRetriesCount(int retriesCount);

	void setTotalCount(int totalCount);
}
