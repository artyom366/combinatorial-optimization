package opt.gen.nn.serive;

import java.util.List;

import opt.gen.alg.domain.GASolution;

public interface NeighboursService {

	void getPossibleNeighbours(List<GASolution<Long, String, Double>> locations, float distance);
}
