package opt.gen.alg.service.helper;

import java.util.List;
import java.util.Map;
import java.util.Set;

import opt.gen.alg.domain.GADataEntry;

public interface GAService<OPTIMIZATION_TYPE, GROUPING_TYPE> {

	Set<Long> getGeneDictionary(List<GADataEntry<Long, String>> gaRealData);

	long getPopulationSize(Set<Long> geneDictionary, double initialPopulationPercentileSize);

	Map<GROUPING_TYPE, List<OPTIMIZATION_TYPE>> getRealPopulationAsGroupedMap(List<GADataEntry<OPTIMIZATION_TYPE, GROUPING_TYPE>> gaRealData);

	String generateHash(Set<OPTIMIZATION_TYPE> genes);

	String generateHash(List<Long> genes);
}
