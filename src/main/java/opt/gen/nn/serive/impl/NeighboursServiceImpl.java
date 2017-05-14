package opt.gen.nn.serive.impl;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import opt.gen.alg.domain.GADataEntry;
import opt.gen.alg.domain.GASolution;
import opt.gen.nn.serive.NeighboursService;
import opt.gen.ui.service.PickLocationsService;

@Service("neighboursService")
public class NeighboursServiceImpl implements NeighboursService {

	private final static double FITNESS_PERCENTAGE_THRESHOLD = 0.75;

	@Autowired
	private PickLocationsService pickLocationsService;

	@Override
	public void searchForLocationPossibleNeighbours(final Map<String, GASolution<Long, String, Double>> results, final double distance) {
		Validate.notNull(results, "Result locations are not defined");

		final int maxFitnessValue = getMaxFitnessValue(results);
		final int fitnessThreshold = getMinFitnessValue(maxFitnessValue, FITNESS_PERCENTAGE_THRESHOLD);

		results.entrySet().forEach(entry -> {

			final int fitnessValue = entry.getValue().getRealDataSequenceIds().size();

			if (fitnessValue > fitnessThreshold) {
				final Set<Long> currentCustomers = entry.getValue().getSolutionCandidate().getGeneSequence();
				searchForLocationPossibleNeighboursInResultEntry(entry, distance, currentCustomers);
			}
		});
	}

	private int getMaxFitnessValue(final Map<String, GASolution<Long, String, Double>> results) {
		return results.entrySet().stream().map(e -> e.getValue().getRealDataSequenceIds().size()).max(Integer::compare).get();
	}

	private int getMinFitnessValue(final int results, final double fitnessPercentageThreshold) {
		return (int) Math.floor(results - (results * fitnessPercentageThreshold));
	}

	private void searchForLocationPossibleNeighboursInResultEntry(final Map.Entry<String, GASolution<Long, String, Double>> entry, final double distance,
																  final Set<Long> currentCustomers) {

		final GASolution<Long, String, Double> resultEntry = entry.getValue();
		final Map<Pair<Double, Double>, String> locations = resultEntry.getRealDataSequenceIds();
		locations.entrySet().forEach(
				locationEntry -> searchForLocationPossibleNeighboursInResultEntryLocations(locationEntry, currentCustomers, resultEntry, distance));
	}

	private void searchForLocationPossibleNeighboursInResultEntryLocations(final Map.Entry<Pair<Double, Double>, String> locationEntry,
																		   final Set<Long> currentCustomers, final GASolution<Long, String, Double>
																				   resultEntry,
																		   final double distance) {

		final Pair<Double, Double> locationCoordinates = locationEntry.getKey();
		final String locationName = locationEntry.getValue();

		final List<GADataEntry<Long, String>> lineLocations = pickLocationsService.findAllInTheSameLineAsLocation(locationName);

		lineLocations.forEach(
				lineLocation -> evaluatePossibleNeighbouringLocation(lineLocation, locationCoordinates, currentCustomers, locationName, resultEntry,
																	 distance));
	}

	private void evaluatePossibleNeighbouringLocation(final GADataEntry<Long, String> lineLocation, final Pair<Double, Double> locationCoordinates,
													  final Set<Long> currentCustomers, final String locationName,
													  final GASolution<Long, String, Double> resultEntry, final double distance) {

		final Optional<Double> optionalDistance = getOptionalLocationsDistance(lineLocation.getCoordinateX(), lineLocation.getCoordinateY(),
																			   locationCoordinates.getLeft(), locationCoordinates.getRight());

		if (isValidNeighbouringLocation(optionalDistance, distance, locationName, lineLocation, currentCustomers)) {
			resultEntry.addToNeighbouringSequenceIds(new ImmutablePair<>(lineLocation.getCoordinateX(), lineLocation.getCoordinateY()),
													 lineLocation.getGroupingParameter());
		}
	}

	private Optional<Double> getOptionalLocationsDistance(final Double x1, final Double y1, final Double x2, final Double y2) {
		final double result = Math.hypot((x1 - x2), (y1 - y2));

		if (!Double.isNaN(result) || Double.isFinite(result)) {
			return Optional.of(result);
		}

		return Optional.empty();
	}

	private boolean isValidNeighbouringLocation(final Optional<Double> optionalDistance, final double distance, final String currentLocationName,
												final GADataEntry<Long, String> potentialLocation, final Set<Long> currentCustomers) {

		return distanceIsPresentAndInRange(optionalDistance, distance) && isOptimizationParameterMatch(currentLocationName, potentialLocation,
																									   currentCustomers);
	}

	private boolean distanceIsPresentAndInRange(final Optional<Double> optionalDistance, final double distance) {
		return optionalDistance.isPresent() && optionalDistance.get() <= distance;
	}

	private boolean isOptimizationParameterMatch(final String currentLocationName, final GADataEntry<Long, String> potentialLocation,
												 Set<Long> currentCustomers) {

		final String potentialNeighbourLocationName = potentialLocation.getGroupingParameter();
		final Long potentialCustomerId = potentialLocation.getOptimizationParameter();

		return isLocationsAreNotTheSame(currentLocationName, potentialNeighbourLocationName) && isCustomerMatch(currentCustomers, potentialCustomerId);
	}

	private boolean isLocationsAreNotTheSame(final String currentLocationName, final String potentialNeighbourLocationName) {
		return !StringUtils.equalsIgnoreCase(currentLocationName, potentialNeighbourLocationName);
	}

	private boolean isCustomerMatch(final Set<Long> currentCustomers, final Long potentialCustomerId) {
		return currentCustomers.contains(potentialCustomerId);
	}

}
