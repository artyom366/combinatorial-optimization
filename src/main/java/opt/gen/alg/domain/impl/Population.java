package opt.gen.alg.domain.impl;

import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.Validate;

import opt.gen.alg.domain.GAPopulation;

public final class Population implements GAPopulation<Long, String, Double> {

	private final String location;
	private final List<Long> customerIds;
	private final Double coordinateX;
	private final Double coordinateY;

	public Population(final String location, final List<Long> customerIds, final double coordinateX, final Double coordinateY) {
		Validate.notEmpty(location, "Location is not defined");
		Validate.notEmpty(customerIds, "Customer Ids are not defined");
		Validate.notNull(coordinateX, "Coordinate X is not defined");
		Validate.notNull(coordinateY, "Coordiname Y is not defined");
		this.location = location;
		this.customerIds = customerIds;
		this.coordinateX = coordinateX;
		this.coordinateY = coordinateY;
	}

	@Override
	public String getGroupingParameter() {
		return location;
	}

	@Override
	public List<Long> getOptimizationParameters() {
		return Collections.unmodifiableList(customerIds);
	}

	@Override
	public Double getCoordinateX() {
		return coordinateX;
	}

	@Override
	public Double getCoordinateY() {
		return coordinateY;
	}
}
