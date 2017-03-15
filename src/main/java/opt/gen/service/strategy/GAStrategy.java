package opt.gen.service.strategy;

import java.util.List;
import java.util.Map;
import java.util.Set;

import opt.gen.domain.GACandidate;
import opt.gen.domain.GAStatistics;

public interface GAStrategy<OPTIMIZATION_TYPE, GROUPING_TYPE> {

	GAStatistics getStatistics();

	GAStrategyInfo getInfo();

	List<GACandidate<Long>> initialization(Set<OPTIMIZATION_TYPE> geneDictionary, Map<GROUPING_TYPE, List<OPTIMIZATION_TYPE>> realPopulation);

	List<GACandidate<OPTIMIZATION_TYPE>> crossover(List<GACandidate<OPTIMIZATION_TYPE>> initialGeneration);

	List<GACandidate<OPTIMIZATION_TYPE>> selection(List<GACandidate<OPTIMIZATION_TYPE>> initialGeneration, List<GACandidate<OPTIMIZATION_TYPE>> nextGeneration);

	List<GACandidate<OPTIMIZATION_TYPE>> mutation(List<GACandidate<OPTIMIZATION_TYPE>> refinedGeneration, Set<OPTIMIZATION_TYPE> geneDictionary);

	List<GACandidate<OPTIMIZATION_TYPE>> correction(List<GACandidate<OPTIMIZATION_TYPE>> mutatedGeneration,
			List<GACandidate<OPTIMIZATION_TYPE>> initialGeneration, Set<OPTIMIZATION_TYPE> geneDictionary);
}
