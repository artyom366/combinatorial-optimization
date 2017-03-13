package opt.gen.domain.impl;

import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.Validate;

import opt.gen.domain.GACandidate;
import opt.gen.domain.GASolution;

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
}
