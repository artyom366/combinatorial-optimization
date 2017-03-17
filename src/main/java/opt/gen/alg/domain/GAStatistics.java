package opt.gen.alg.domain;

import java.util.List;

public interface GAStatistics {

	int getCurrentConvergenceRetriesCount();

	void incrementConvergenceRetryCount();

	void addConvergenceValue(int value);

	List<Integer> getConvergence();
}
