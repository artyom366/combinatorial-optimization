package opt.gen.alg.service.runner;

import java.util.List;
import java.util.Map;
import java.util.Set;

import opt.gen.alg.domain.GAPopulation;
import opt.gen.alg.domain.GASolution;


public interface GARunnerService<OPTIMIZATION_TYPE, GROUPING_TYPE, COORDINATE_TYPE extends Number> {

    Map<String, GASolution<OPTIMIZATION_TYPE, GROUPING_TYPE, COORDINATE_TYPE>> run(Set<OPTIMIZATION_TYPE> geneDictionary,
                                                                                   final List<GAPopulation<OPTIMIZATION_TYPE, GROUPING_TYPE, COORDINATE_TYPE>> realPopulationGroups);

}
