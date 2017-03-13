package opt.gen.service.runner;

import java.util.List;
import java.util.Map;
import java.util.Set;

import opt.gen.domain.GADataEntry;
import opt.gen.domain.GASolution;
import opt.gen.error.GAException;

public interface GARunnerService<OPTIMIZATION_TYPE, GROUPING_TYPE> {

	Map<String, GASolution<Long, String>> run(Set<OPTIMIZATION_TYPE> geneDictionary, Map<GROUPING_TYPE, List<OPTIMIZATION_TYPE>> realPopulation, int chromosomeMinSize, int chromosomeMaxSize)
			throws GAException;
}
