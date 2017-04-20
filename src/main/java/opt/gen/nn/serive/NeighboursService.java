package opt.gen.nn.serive;

import java.util.Map;

import opt.gen.alg.domain.GASolution;

public interface NeighboursService {

	void searchForLocationPossibleNeighbours(Map<String, GASolution<Long, String, Double>> result, double distance);
}
