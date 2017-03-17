package opt.gen.alg.domain.impl;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.Validate;

import opt.gen.alg.domain.GACandidate;
import opt.gen.alg.domain.GASolution;

public final class Result implements GASolution<Long, String> {

	private final GACandidate<Long> candidate;
	private final List<String> realDataSequenceIds;

	public Result(final GACandidate<Long> candidate, List<String> realDataSequenceIds) {
		Validate.notNull(candidate, "GA candidate is not defined");
		Validate.notEmpty(realDataSequenceIds, "Real data sequence ids are not defined");
		this.candidate = candidate;
		this.realDataSequenceIds = realDataSequenceIds;
	}

	@Override
	public GACandidate<Long> getSolutionCandidate() {
		return candidate;
	}

	@Override
	public List<String> getRealDataSequenceIds() {
		return Collections.unmodifiableList(realDataSequenceIds);
	}

	@Override
	public int getCandidateFrequency() {
		return realDataSequenceIds.size();
	}

	@Override
	public String getHash() {
		return candidate.getHash();
	}

	@Override
	public String getLocations() {
		return realDataSequenceIds.stream().collect(Collectors.joining("-"));
	}

	@Override
	public int getGenesCount() {
		return candidate.getGeneSequence().size();
	}

	@Override
	public int getLocationsCount() {
		return realDataSequenceIds.size();
	}
}
