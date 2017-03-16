package opt.gen.ui.service;

import opt.gen.alg.domain.impl.PickLocationViewDO;

public interface PickLocationsService {

	Iterable<PickLocationViewDO> findAll();
}
