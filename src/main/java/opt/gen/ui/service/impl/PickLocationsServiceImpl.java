package opt.gen.ui.service.impl;

import opt.gen.alg.domain.GADataEntry;

import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import opt.gen.alg.domain.impl.PickLocationViewDO;
import opt.gen.ui.repository.PickLocationsRepository;
import opt.gen.ui.service.PickLocationsService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service("realDataService")
public class PickLocationsServiceImpl implements PickLocationsService {

	@Autowired
	private PickLocationsRepository pickLocationsRepository;

	@Override
	public List<GADataEntry<Long, String>> findAll() {
		final Iterable<PickLocationViewDO> rawData = pickLocationsRepository.findAll();

		final List<GADataEntry<Long, String>> locations = new ArrayList<>();
		locations.addAll((Collection<? extends GADataEntry<Long, String>>) rawData);

		return locations;
	}

	@Override
	public List<GADataEntry<Long, String>> findAllInTheSameLineAsLocation(final String location) {
		Validate.notEmpty(location, "Location is not defined");

		final String line = pickLocationsRepository.findLineByLocation(location);
		final List<PickLocationViewDO> rawData = pickLocationsRepository.findAllInLine(line);

		final List<GADataEntry<Long, String>> locations = new ArrayList<>();
		locations.addAll(rawData);

		return locations;
	}
}
