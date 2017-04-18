package opt.gen.alg.service.helper;

import java.util.List;
import java.util.Map;
import java.util.Set;

import opt.gen.alg.domain.GADataEntry;
import opt.gen.alg.domain.GAPopulation;
import opt.gen.alg.domain.GASolution;

public interface GAService<OPTIMIZATION_TYPE, GROUPING_TYPE, COORDINATE_TYPE> {

	Set<OPTIMIZATION_TYPE> getGeneDictionary(List<GADataEntry<OPTIMIZATION_TYPE, GROUPING_TYPE>> gaRealData);

	long getPopulationSize(Set<OPTIMIZATION_TYPE> geneDictionary, double initialPopulationPercentileSize);

	List<GAPopulation<OPTIMIZATION_TYPE, GROUPING_TYPE, Double>> getRealPopulationGrouped(List<GADataEntry<OPTIMIZATION_TYPE, GROUPING_TYPE>> gaRealData);

	List<GASolution<OPTIMIZATION_TYPE, GROUPING_TYPE, COORDINATE_TYPE>> getResultAsList(Map<String, GASolution<OPTIMIZATION_TYPE, GROUPING_TYPE, COORDINATE_TYPE>> result);

	String generateHash(Set<OPTIMIZATION_TYPE> genes);

	String generateHash(List<OPTIMIZATION_TYPE> genes);
}
