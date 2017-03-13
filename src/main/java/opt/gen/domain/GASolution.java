package opt.gen.domain;

import java.util.List;

public interface GASolution<OPTIMIZATION_TYPE, REAL_DATA_SEQUENCE_ID_TYPE> {

	GACandidate<OPTIMIZATION_TYPE> getSolutionCandidate();

	List<REAL_DATA_SEQUENCE_ID_TYPE> getRealDataSequenceIds();

	int getCandidateFrequency();
}
