import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class RailwaySystemImpl implements RailwaySystem {
    private Map<Path, PathInfo> ways = new HashMap<>();

    @Override
    public void addRoute(String srcStation, String dstStation, int distance) {
        if (srcStation == null && dstStation == null)
            return;
        if (srcStation.length() == 0)
            return;
        if (dstStation.length() != 0) {
            LinkedList<String> nodes = new LinkedList<>();
            nodes.add(srcStation);
            nodes.add(dstStation);
            ways.put(new Path(srcStation, dstStation), new PathInfo(nodes, distance));
        }

        Map<Path, PathInfo> tmpWays = new HashMap<>();
        for (Map.Entry<Path, PathInfo> way : ways.entrySet()) {
            if (way.getKey().getSrc().equals(dstStation)) {
                LinkedList<String> nodesCopy = new LinkedList<>(way.getValue().getNodes());
                nodesCopy.addFirst(srcStation);
                tmpWays.put(new Path(srcStation, way.getKey().getDst()),
                        new PathInfo(nodesCopy, distance + way.getValue().getDistance()));
            }
            if (way.getKey().getDst().equals(srcStation)) {
                LinkedList<String> nodesCopy = new LinkedList<>(way.getValue().getNodes());
                nodesCopy.addLast(dstStation);
                tmpWays.put(new Path(way.getKey().getSrc(), dstStation),
                        new PathInfo(nodesCopy, distance + way.getValue().getDistance()));
            }
        }

        for (Map.Entry<Path, PathInfo> tmpWay : tmpWays.entrySet()) {
            ways.put(tmpWay.getKey(), tmpWay.getValue());
        }
    }

    @Override
    public boolean canReach(String srcStation, String dstStation) {
        if (srcStation.equals(dstStation))
            return true;
        if (ways.containsKey(new Path(srcStation, dstStation)))
            return true;
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
