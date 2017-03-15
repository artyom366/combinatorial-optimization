package opt.gen.alg.domain;

import java.util.Set;

public interface GACandidate<OPTIMIZATION_TYPE> {

	Set<OPTIMIZATION_TYPE> getGeneSequence();

	String getHash();

}
