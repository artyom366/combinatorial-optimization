package opt.gen.alg.service.helper;

import java.util.List;
import java.util.Map;
import java.util.Set;

import opt.gen.alg.domain.GADataEntry;
import opt.gen.alg.domain.GASolution;

public interface GAService<OPTIMIZATION_TYPE, GROUPING_TYPE> {

	Set<OPTIMIZATION_TYPE> getGeneDictionary(List<GADataEntry<OPTIMIZATION_TYPE, GROUPING_TYPE>> gaRealData);

	long getPopulationSize(Set<OPTIMIZATION_TYPE> geneDictionary, double initialPopulationPercentileSize);

	Map<GROUPING_TYPE, List<OPTIMIZATION_TYPE>> getRealPopulationAsGroupedMap(List<GADataEntry<OPTIMIZATION_TYPE, GROUPING_TYPE>> gaRealData);

	List<GASolution<Long, String>> getResultAsList(Map<String, GASolution<Long, String>> result);

	String generateHash(Set<OPTIMIZATION_TYPE> genes);

	String generateHash(List<OPTIMIZATION_TYPE> genes);
}
