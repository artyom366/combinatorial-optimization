package opt.gen.alg.domain.impl;

import java.util.Collections;
import java.util.Set;

import opt.gen.alg.domain.GACandidate;
import org.apache.commons.lang3.Validate;

public final class Chromosome implements GACandidate<Long> {

	private final String hash;
	private final Set<Long> genes;

	public Chromosome(final Set<Long> genes, final String hash) {
		Validate.notNull(genes, "Candidate gene sequence is not defined");
		Validate.notNull(hash, "Candidate hash is bot defined");

		this.genes = genes;
		this.hash = hash;

	}

	@Override
	public Set<Long> getGeneSequence() {
		return getGenes();
	}

	private Set<Long> getGenes() {
		return Collections.unmodifiableSet(this.genes);
	}

	@Override
	public String getHash() {
		return this.hash;
	}

}
