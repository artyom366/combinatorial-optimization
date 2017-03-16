package opt.gen.ui.service;

import java.util.List;

import opt.gen.alg.domain.GADataEntry;

public interface PickLocationsService {

	List<GADataEntry<Long, String>> getPickLocations();
}
