import java.util.*;

import static java.util.Arrays.asList;

public class RailwaySystemImpl implements RailwaySystem {
    private Map<Path, List<PathInfo>> ways = new HashMap<>();

    @Override
    public void addRoute(String srcStation, String dstStation, int distance) {
        if (srcStation == null && dstStation == null)
            return;
        if ((srcStation != null ? srcStation.length() : 0) == 0)
            return;
        if (dstStation.length() != 0) {
            LinkedList<String> nodes = new LinkedList<>();
            nodes.add(srcStation);
            nodes.add(dstStation);
            ways.put(new Path(srcStation, dstStation), asList(new PathInfo(nodes, distance)));
        }

        mergeRoute(srcStation, dstStation, distance);
    }

    private void mergeRoute(String srcStation, String dstStation, int distance) {
        Map<Path, List<PathInfo>> tmpWays = new HashMap<>();
        for (Map.Entry<Path, List<PathInfo>> way : ways.entrySet()) {
            if (way.getKey().getSrc().equals(dstStation)) {
                List<PathInfo> tmpPathInfo = new ArrayList<>();
                for (PathInfo pathInfo : way.getValue()) {
                    LinkedList<String> nodesCopy = new LinkedList<>(pathInfo.getNodes());
                    nodesCopy.addFirst(srcStation);
                    tmpPathInfo.add(new PathInfo(nodesCopy, distance + pathInfo.getDistance()));
                }

                Path key = new Path(srcStation, way.getKey().getDst());
                if (ways.containsKey(key)) {
                    tmpPathInfo.addAll(ways.get(key));
                }
                tmpWays.put(key, tmpPathInfo);
            }
            if (way.getKey().getDst().equals(srcStation)) {
                List<PathInfo> tmpPathInfo = new ArrayList<>();
                for (PathInfo pathInfo : way.getValue()) {
                    LinkedList<String> nodesCopy = new LinkedList<>(pathInfo.getNodes());
                    nodesCopy.addLast(dstStation);
                    tmpPathInfo.add(new PathInfo(nodesCopy, distance + pathInfo.getDistance()));
                }
                Path key = new Path(way.getKey().getSrc(), dstStation);
                if (ways.containsKey(key)) {
                    tmpPathInfo.addAll(ways.get(key));
                }
                tmpWays.put(key, tmpPathInfo);
            }
        }

        for (Map.Entry<Path, List<PathInfo>> tmpWay : tmpWays.entrySet()) {
            ways.put(tmpWay.getKey(), tmpWay.getValue());
        }
    }

    @Override
    public boolean canReach(String srcStation, String dstStation) {
        return srcStation.equals(dstStation) || ways.containsKey(new Path(srcStation, dstStation));
    }

    @Override
    public int getMinHopCount(String srcStation, String dstStation) {
        if (srcStation.equals(dstStation))
            return 1;
        if (!canReach(srcStation, dstStation))
            return 0xffff;
        final List<PathInfo> pathInfos = ways.get(new Path(srcStation, dstStation));
        Collections.sort(pathInfos, new Comparator<PathInfo>() {
            @Override
            public int compare(PathInfo o1, PathInfo o2) {
                return o1.getNodes().size() - o2.getNodes().size();
            }
        });
        return pathInfos.get(0).getNodes().size();

    }

    @Override
    public int getMinCostPath(String srcStation, String dstStation) {
        if (srcStation.equals(dstStation))
            return 0;
        if (!canReach(srcStation, dstStation))
            return 0xffff;
        final List<PathInfo> pathInfos = ways.get(new Path(srcStation, dstStation));
        Collections.sort(pathInfos, new Comparator<PathInfo>() {
            @Override
            public int compare(PathInfo o1, PathInfo o2) {
                return o1.getDistance() - o2.getDistance();
            }
        });
        return pathInfos.get(0).getDistance();
    }

    public Map<Path, List<PathInfo>> getWays() {
        return ways;
    }

    public void clearAllWays() {
        ways.clear();
    }
}
