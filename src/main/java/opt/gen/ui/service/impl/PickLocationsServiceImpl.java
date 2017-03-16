package opt.gen.ui.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import opt.gen.alg.domain.GADataEntry;
import opt.gen.ui.service.PickLocationsService;

@Service("realDataService")
public class PickLocationsServiceImpl implements PickLocationsService {

	@Override
	public List<GADataEntry<Long, String>> getPickLocations() {

		return null;
	}
}
