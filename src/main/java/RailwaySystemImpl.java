import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class RailwaySystemImpl implements RailwaySystem{
    private Map<Path, PathInfo> ways = new HashMap<>();
    @Override
    public void addRoute(String srcStation, String dstStation, int distance) {
        if (srcStation == null && dstStation == null)
            return;
        if (dstStation.length() != 0) {
            LinkedList<String> nodes = new LinkedList<>();
            nodes.add(srcStation);
            nodes.add(dstStation);
            ways.put(new Path(srcStation, dstStation), new PathInfo(nodes, distance));
        }
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

    public Map<Path, PathInfo> getWays() {
        return ways;
    }

    public void clearAllWays() {
        ways.clear();
    }
}
