package opt.gen.ui.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import opt.gen.alg.domain.impl.PickLocationViewDO;
import opt.gen.ui.repository.PickLocationsRepository;
import opt.gen.ui.service.PickLocationsService;

@Service("realDataService")
public class PickLocationsServiceImpl implements PickLocationsService {

	@Autowired
	private PickLocationsRepository pickLocationsRepository;

	@Override
	public Iterable<PickLocationViewDO> findAll() {
		return pickLocationsRepository.findAll();
	}
}
