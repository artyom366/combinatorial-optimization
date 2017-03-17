package opt.gen.alg.domain.impl;

public final class Info {

    private final int iteration;
    private int newCombinationsCount;
    private int mutatedCount;
    private int correctedCount;
    private int retriesCount;
    private int totalCount;
    private int totalNonUniqueCount;

    public Info(final int currentIteration) {
        this.iteration = currentIteration;
    }

    public void setNewCombinationsCount(int newCombinations) {
        this.newCombinationsCount = newCombinations;
    }

    public void addToMutatedCount() {
        this.mutatedCount++;
    }

    public void setCorrectedCount(final int correctedCount) {
        this.correctedCount = correctedCount;
    }

    public void setRetriesCount(final int retriesCount) {
        this.retriesCount = retriesCount;
    }

    public void setTotalCount(final int totalCount) {
        this.totalCount = totalCount;
    }

    public void setTotalNonUniqueCount(final int totalNonUniqueCount) {
        this.totalNonUniqueCount = totalNonUniqueCount;
    }

    public int getIteration() {
        return iteration;
    }

    public int getNewCombinationsCount() {
        return newCombinationsCount;
    }

    public int getMutatedCount() {
        return mutatedCount;
    }

    public int getCorrectedCount() {
        return correctedCount;
    }

    public int getRetriesCount() {
        return retriesCount;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public int getTotalNonUniqueCount() {
        return totalNonUniqueCount;
    }
}
