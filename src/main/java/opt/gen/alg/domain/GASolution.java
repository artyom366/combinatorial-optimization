package opt.gen.alg.domain;

import java.util.Map;

import org.apache.commons.lang3.tuple.Pair;

public interface GASolution<OPTIMIZATION_TYPE, REAL_DATA_SEQUENCE_ID_TYPE, COORDINATE_TYPE> {

	GACandidate<OPTIMIZATION_TYPE> getSolutionCandidate();

	Map<Pair<COORDINATE_TYPE, COORDINATE_TYPE>, REAL_DATA_SEQUENCE_ID_TYPE> getRealDataSequenceIds();

	int getCandidateFrequency();

	String getHash();

	String getLocations();

	int getGenesCount();

	int getLocationsCount();

	void addToNeighbouringSequenceIds(Pair<Double, Double> coordinates, String newNeighbouringId);
}
