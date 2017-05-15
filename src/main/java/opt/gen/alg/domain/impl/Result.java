package opt.gen.alg.domain.impl;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.tuple.Pair;

import opt.gen.alg.domain.GACandidate;
import opt.gen.alg.domain.GASolution;

public final class Result implements GASolution<Long, String, Double> {

	private final GACandidate<Long> candidate;
	final Map<Pair<Double, Double>, String> realDataSequenceIds;
	final Map<Pair<Double, Double>, String> realDataNeighbouringSequenceIds;

	public Result(final GACandidate<Long> candidate, final Map<Pair<Double, Double>, String> realDataSequenceIds) {
		Validate.notNull(candidate, "GA candidate is not defined");
		Validate.notEmpty(realDataSequenceIds, "Real data sequence ids are not defined");
		this.candidate = candidate;
		this.realDataSequenceIds = realDataSequenceIds;
		this.realDataNeighbouringSequenceIds = new HashMap<>();
	}

	@Override
	public GACandidate<Long> getSolutionCandidate() {
		return candidate;
	}

	@Override
	public Map<Pair<Double, Double>, String> getRealDataSequenceIds() {
		return Collections.unmodifiableMap(realDataSequenceIds);
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
		return realDataSequenceIds.entrySet().stream().map(Map.Entry::getValue).collect(Collectors.joining(StringUtils.SPACE));
	}

	@Override
	public int getGenesCount() {
		return candidate.getGeneSequence().size();
	}

	@Override
	public int getLocationsCount() {
		return realDataSequenceIds.size();
	}

	@Override
	public void addToNeighbouringSequenceIds(final Pair<Double, Double> coordinates, final String newNeighbouringId) {
		this.realDataNeighbouringSequenceIds.put(coordinates, newNeighbouringId);
	}

	@Override
	public String getNeighbours() {
		return realDataNeighbouringSequenceIds.entrySet().stream().map(Map.Entry::getValue).collect(Collectors.joining(StringUtils.SPACE));
	}

	@Override
	public int getNeighboursCount() {
		return realDataNeighbouringSequenceIds.size();
	}

	@Override
	public int getTotalLocations() {
		return getLocationsCount() +  getNeighboursCount();
	}

	@Override
	public List<Pair<Double, Double>> getLocationsCoordinates() {
		 return Collections.unmodifiableList(realDataSequenceIds.entrySet().stream().map(Map.Entry::getKey).collect(Collectors.toList()));
	}

	@Override
	public List<Pair<Double, Double>> getNeighboursCoordinates() {
		 return Collections.unmodifiableList(realDataNeighbouringSequenceIds.entrySet().stream().map(Map.Entry::getKey).collect(Collectors.toList()));
	}


}
