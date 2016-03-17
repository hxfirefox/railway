public class RailwaySystemImpl implements RailwaySystem{
    @Override
    public void addRoute(String srcStation, String dstStation, int distance) {

    }

    @Override
    public boolean canReach(String srcStation, String dstStation) {
        return false;
    }

    @Override
    public int getMinHopCount(String srcStation, String dstStation) {
        return 0;
    }

    @Override
    public int getMinCostPath(String srcStation, String dstStation) {
        return 0;
    }
}
