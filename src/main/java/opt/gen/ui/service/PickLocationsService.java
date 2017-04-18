package opt.gen.ui.service;

import opt.gen.alg.domain.GADataEntry;

import java.util.List;

public interface PickLocationsService {

	List<GADataEntry<Long, String>> findAll();

	List<GADataEntry<Long,String>> findAllInTheSameLineAsLocation(String location);
}
