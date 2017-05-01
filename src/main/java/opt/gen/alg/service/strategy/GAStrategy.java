package opt.gen.alg.service.strategy;

import java.util.List;
import java.util.Map;
import java.util.Set;

import opt.gen.alg.domain.GACandidate;
import opt.gen.alg.domain.GASolution;
import opt.gen.alg.domain.GAStatistics;

public interface GAStrategy<OPTIMIZATION_TYPE, GROUPING_TYPE, COORDINATE_TYPE> {

    void init();

    GAStatistics getStatistics();

	GAStrategyInfo getInfo();

	List<GACandidate<OPTIMIZATION_TYPE>> initialization(Set<OPTIMIZATION_TYPE> geneDictionary);

	List<GACandidate<OPTIMIZATION_TYPE>> crossover(List<GACandidate<OPTIMIZATION_TYPE>> initialGeneration);

	List<GACandidate<OPTIMIZATION_TYPE>> selection(List<GACandidate<OPTIMIZATION_TYPE>> initialGeneration, Map<String, GASolution<OPTIMIZATION_TYPE, GROUPING_TYPE, COORDINATE_TYPE>> result);

	List<GACandidate<OPTIMIZATION_TYPE>> mutation(List<GACandidate<OPTIMIZATION_TYPE>> refinedGeneration, Set<OPTIMIZATION_TYPE> geneDictionary);

	List<GACandidate<OPTIMIZATION_TYPE>> correction(List<GACandidate<OPTIMIZATION_TYPE>> mutatedGeneration,
			List<GACandidate<OPTIMIZATION_TYPE>> initialGeneration, Set<OPTIMIZATION_TYPE> geneDictionary);
}
