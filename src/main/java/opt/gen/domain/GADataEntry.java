package opt.gen.domain;

public interface GADataEntry<OPTIMIZATION, GROUPING> {

	OPTIMIZATION getOptimizationParameter();

	GROUPING getGroupingParameter();
}