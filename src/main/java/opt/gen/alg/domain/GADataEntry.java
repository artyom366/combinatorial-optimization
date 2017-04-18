package opt.gen.alg.domain;

public interface GADataEntry<OPTIMIZATION, GROUPING> {

	OPTIMIZATION getOptimizationParameter();

	GROUPING getGroupingParameter();

	Double getCoordinateX();

	Double getCoordinateY();
}
