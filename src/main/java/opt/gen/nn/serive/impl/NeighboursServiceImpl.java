package opt.gen.nn.serive.impl;

import opt.gen.alg.domain.GADataEntry;
import opt.gen.alg.domain.GASolution;
import opt.gen.nn.serive.NeighboursService;
import opt.gen.ui.service.PickLocationsService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Service("neighboursService")
public class NeighboursServiceImpl implements NeighboursService {

	private final static double FITNESS_PERCENTAGE_THRESHOLD = 0.25;

	@Autowired
	private PickLocationsService pickLocationsService;

	@Override
	public void searchForLocationPossibleNeighbours(final Map<String, GASolution<Long, String, Double>> results, final double maxDistance) {
		Validate.notNull(results, "Result locations are not defined");

		final long algorithmExecutionStart = getCurrentTimeInMillis();

		final int maxFitnessValue = getMaxFitnessValue(results);
		final int fitnessThreshold = getMinFitnessValueThreshold(maxFitnessValue, FITNESS_PERCENTAGE_THRESHOLD);

		results.entrySet().forEach(entry -> {

			final int fitnessValue = entry.getValue().getRealDataSequenceIds().size();

			if (fitnessValue > fitnessThreshold) {
				final Set<Long> currentCustomers = entry.getValue().getSolutionCandidate().getGeneSequence();
				searchForLocationPossibleNeighboursInResultEntry(entry, maxDistance, currentCustomers);
			}
		});

		logNearestNeighboursSearchAlgorithmExecutionTime(algorithmExecutionStart);
	}

	private long getCurrentTimeInMillis() {
		return System.currentTimeMillis();
	}

	private void logNearestNeighboursSearchAlgorithmExecutionTime(long algorithmExecutionStart) {
		System.out.println(String.format("Nearest neighbours search algorithm execution time is: %d ms", getCurrentTimeInMillis() - algorithmExecutionStart));
	}

	protected int getMaxFitnessValue(final Map<String, GASolution<Long, String, Double>> results) {
		return results.entrySet().stream().map(e -> e.getValue().getRealDataSequenceIds().size()).max(Integer::compare).get();
	}

	protected int getMinFitnessValueThreshold(final int maxFitnessValue, final double fitnessPercentageThreshold) {
		return (int) Math.floor(maxFitnessValue - (maxFitnessValue * fitnessPercentageThreshold));
	}

	private void searchForLocationPossibleNeighboursInResultEntry(final Map.Entry<String, GASolution<Long, String, Double>> entry, final double maxDistance,
																  final Set<Long> currentCustomers) {

		final GASolution<Long, String, Double> resultEntry = entry.getValue();
		final Map<Pair<Double, Double>, String> locations = resultEntry.getRealDataSequenceIds();
		locations.entrySet().forEach(
				locationEntry -> searchForLocationPossibleNeighboursInResultEntryLocations(locationEntry, currentCustomers, resultEntry, maxDistance));
	}

	private void searchForLocationPossibleNeighboursInResultEntryLocations(final Map.Entry<Pair<Double, Double>, String> locationEntry,
																		   final Set<Long> currentCustomers, final GASolution<Long, String, Double>
																				   resultEntry,
																		   final double maxDistance) {

		final Pair<Double, Double> locationCoordinates = locationEntry.getKey();
		final String locationName = locationEntry.getValue();

		final List<GADataEntry<Long, String>> lineLocations = pickLocationsService.findAllInTheSameLineAsLocation(locationName);

		lineLocations.forEach(
				lineLocation -> evaluatePossibleNeighbouringLocation(lineLocation, locationCoordinates, currentCustomers, locationName, resultEntry,
																	 maxDistance));
	}

	private void evaluatePossibleNeighbouringLocation(final GADataEntry<Long, String> lineLocation, final Pair<Double, Double> locationCoordinates,
													  final Set<Long> currentCustomers, final String locationName,
													  final GASolution<Long, String, Double> resultEntry, final double maxDistance) {

		final Optional<Double> optionalLocationDistance = getOptionalLocationsDistance(lineLocation.getCoordinateX(), lineLocation.getCoordinateY(),
																			   locationCoordinates.getLeft(), locationCoordinates.getRight());

		if (isValidNeighbouringLocation(optionalLocationDistance, maxDistance, locationName, lineLocation, currentCustomers)) {
			resultEntry.addToNeighbouringSequenceIds(new ImmutablePair<>(lineLocation.getCoordinateX(), lineLocation.getCoordinateY()),
													 lineLocation.getGroupingParameter());
		}
	}

	protected Optional<Double> getOptionalLocationsDistance(final Double x1, final Double y1, final Double x2, final Double y2) {
		final double result = Math.hypot((x1 - x2), (y1 - y2));

		if (!Double.isNaN(result) || Double.isFinite(result)) {
			return Optional.of(result);
		}

		return Optional.empty();
	}

	protected boolean isValidNeighbouringLocation(final Optional<Double> optionalLocationDistance, final double maxDistance, final String currentLocationName,
												final GADataEntry<Long, String> potentialNeighbouringLocation, final Set<Long> currentCustomers) {

		return distanceIsPresentAndInRange(optionalLocationDistance, maxDistance) && isOptimizationParameterMatch(currentLocationName, potentialNeighbouringLocation,
																									   currentCustomers);
	}

	private boolean distanceIsPresentAndInRange(final Optional<Double> optionalLocationDistance, final double MaxDistance) {
		return optionalLocationDistance.isPresent() && optionalLocationDistance.get() <= MaxDistance;
	}

	private boolean isOptimizationParameterMatch(final String currentLocationName, final GADataEntry<Long, String> potentialNeighbouringLocation,
												 Set<Long> currentCustomers) {

		final String potentialNeighbouringLocationName = potentialNeighbouringLocation.getGroupingParameter();
		final Long potentialNeighbouringCustomerId = potentialNeighbouringLocation.getOptimizationParameter();

		return isLocationsAreNotTheSame(currentLocationName, potentialNeighbouringLocationName) && isCustomerMatch(currentCustomers, potentialNeighbouringCustomerId);
	}

	private boolean isLocationsAreNotTheSame(final String currentLocationName, final String potentialNeighbouringLocation) {
		return !StringUtils.equalsIgnoreCase(currentLocationName, potentialNeighbouringLocation);
	}

	private boolean isCustomerMatch(final Set<Long> currentCustomers, final Long potentialCustomerId) {
		return currentCustomers.contains(potentialCustomerId);
	}

}
