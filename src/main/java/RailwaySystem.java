public interface RailwaySystem {
    void addRoute(String srcStation, String dstStation, int distance);

    boolean canReach(String srcStation, String dstStation);

    int getMinHopCount(String srcStation, String dstStation);

    int getMinCostPath(String srcStation, String dstStation);
}
