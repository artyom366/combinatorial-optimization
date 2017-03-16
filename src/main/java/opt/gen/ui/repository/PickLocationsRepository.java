package opt.gen.ui.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import opt.gen.alg.domain.GADataEntry;
import opt.gen.alg.domain.impl.PickLocationViewDO;

@Repository
public interface PickLocationsRepository extends CrudRepository<PickLocationViewDO, Long> {}
