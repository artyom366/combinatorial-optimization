package opt.gen.nn.serive.impl;

import java.util.List;
import java.util.Map;
import java.util.Optional;

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

	@Autowired
	private PickLocationsService pickLocationsService;

	@Override
	public void getPossibleNeighbours(final List<GASolution<Long, String, Double>> candidates, final float distance) {
		Validate.notNull(candidates, "Location are not defined");

		for (final GASolution<Long, String, Double> candidate : candidates) {
			final Map<Pair<Double, Double>, String> realDataSequenceIds = candidate.getRealDataSequenceIds();

			realDataSequenceIds.entrySet().forEach(e -> {

				final Pair<Double, Double> coordinates = e.getKey();
				final String location = e.getValue();

				final List<GADataEntry<Long, String>> lineLocations = pickLocationsService.findAllInTheSameLineAsLocation(location);

				lineLocations.forEach(l -> {

					final Optional<Double> optionalDistance =
						getOptionalLocationsDistance(l.getCoordinateX(), l.getCoordinateY(), coordinates.getLeft(), coordinates.getRight());

					if (distanceIsPresentAndInRange(optionalDistance, distance)) {
						candidate.addToNeighbouringSequenceIds(new ImmutablePair<>(l.getCoordinateX(), l.getCoordinateY()), l.getGroupingParameter());
					}
				});
			});
		}
	}

	private Optional<Double> getOptionalLocationsDistance(final Double x1, final Double y1, final Double x2, final Double y2) {
		final double result = Math.hypot((x1 - x2), (y1 - y2));

		if (!Double.isNaN(result) || Double.isFinite(result)) {
			return Optional.of(result);
		}

		return Optional.empty();
	}

	private boolean distanceIsPresentAndInRange(final Optional<Double> optionalDistance, final float distance) {
		return optionalDistance.isPresent() && optionalDistance.get() <= distance;
	}

}
