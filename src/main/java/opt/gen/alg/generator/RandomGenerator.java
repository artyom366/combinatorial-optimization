package opt.gen.alg.generator;

import java.util.concurrent.ThreadLocalRandom;

public final class RandomGenerator {

	public static int generateUniformInt(final int max) {
		return ThreadLocalRandom.current().nextInt(max);
	}

	public static int generateUniformInt(final int min, final int max) {
		return ThreadLocalRandom.current().nextInt(min, max);
	}

	public static int generateUniformIntInclusive(final int min, final int max) {
		return ThreadLocalRandom.current().nextInt(min, max + 1);
	}

	public static long generateUniformLong(final long max) {
		return ThreadLocalRandom.current().nextLong(max);
	}

	public static long generateUniformLong(final long min, final long max) {
		return ThreadLocalRandom.current().nextLong(min, max);
	}

	public static long generateUniformLongInclusive(final long min, final long max) {
		return ThreadLocalRandom.current().nextLong(min, max + 1);
	}

	public static double generateUniformDouble(final long max) {
		return ThreadLocalRandom.current().nextDouble() * max;
	}

	public static double generateUniformDouble(final double max) {
		return ThreadLocalRandom.current().nextDouble() * max;
	}

	public static double generateUniformDouble() {
		return ThreadLocalRandom.current().nextDouble();
	}


}
