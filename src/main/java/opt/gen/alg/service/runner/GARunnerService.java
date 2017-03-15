package opt.gen.alg.service.runner;

import opt.gen.alg.domain.GASolution;

import java.util.List;
import java.util.Map;
import java.util.Set;


public interface GARunnerService<OPTIMIZATION_TYPE, GROUPING_TYPE> {

    Map<String, GASolution<Long, String>> run(Set<OPTIMIZATION_TYPE> geneDictionary, Map<GROUPING_TYPE, List<OPTIMIZATION_TYPE>> realPopulation);

}
