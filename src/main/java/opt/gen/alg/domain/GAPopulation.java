package opt.gen.alg.domain;

import java.util.List;

public interface GAPopulation<OPTIMIZATION_TYPE, GROUPING_TYPE, COORDINATE_TYPE extends Number> {

	GROUPING_TYPE getGroupingParameter();

	List<OPTIMIZATION_TYPE> getOptimizationParameters();

	COORDINATE_TYPE getCoordinateX();

	COORDINATE_TYPE getCoordinateY();
}
