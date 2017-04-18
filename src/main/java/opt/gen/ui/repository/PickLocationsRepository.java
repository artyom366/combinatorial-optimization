package opt.gen.ui.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import opt.gen.alg.domain.GADataEntry;
import opt.gen.alg.domain.impl.PickLocationViewDO;

@Repository
public interface PickLocationsRepository extends CrudRepository<PickLocationViewDO, Long> {

	@Query("select p.line from opt.gen.alg.domain.impl.PickLocationViewDO p where p.location = :location")
	String findLineByLocation(@Param("location") final String location);

	@Query("select p from opt.gen.alg.domain.impl.PickLocationViewDO p where p.line = :line")
	List<PickLocationViewDO> findAllInLine(@Param("line") final String line);
}
