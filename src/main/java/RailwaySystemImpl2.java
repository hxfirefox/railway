import java.util.*;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.*;

public class RailwaySystemImpl2 implements RailwaySystem {
    private Set<String> paths = new HashSet<>();
    private Map<String, List<String>> railwayHops = new HashMap<>();
    private Map<String, List<Integer>> railwayDistances = new HashMap<>();

    @Override
    public void addRoute(String srcStation, String dstStation, int distance) {
        if (srcStation == null && dstStation == null)
            return;
        if ((srcStation != null ? srcStation.length() : 0) == 0)
            return;
        if (dstStation.length() != 0) {
            final String edge = srcStation + dstStation;
            paths.add(edge);
            railwayHops.put(edge, asList(edge));
            railwayDistances.put(edge, asList(distance));
        }

        mergeRoutes(srcStation, dstStation, distance);
    }

    private void mergeRoutes(String srcStation, String dstStation, int distance) {
        Set<String> newPaths = new HashSet<>();
        Map<String, List<String>> newHops = new HashMap<>();
        Map<String, List<Integer>> newDistances = new HashMap<>();

        paths.stream().filter(x -> x.startsWith(dstStation)).forEach(p -> {
            final String newKey = srcStation + p.charAt(p.length() - 1);
            final List<String> hops = railwayHops.get(p).stream().map(srcStation::concat).collect(toList());
            final List<Integer> costs = railwayDistances.get(p).stream().map(x -> distance + x).collect(toList());
            newPaths.add(newKey);
            newHops.put(newKey, hops);
            newDistances.put(newKey, costs);
        });

        paths.stream().filter(x -> x.endsWith(srcStation)).forEach(p -> {
            final String newKey = p.charAt(0) + dstStation;
            final List<String> hops = railwayHops.get(p).stream().map(x -> x.concat(dstStation)).collect(toList());
            final List<Integer> costs = railwayDistances.get(p).stream().map(x -> distance + x).collect(toList());
            newPaths.add(newKey);
            newHops.put(newKey, hops);
            newDistances.put(newKey, costs);
        });

        updateRoutes(newPaths, newHops, newDistances);
    }

    private void updateRoutes(Set<String> newPaths, Map<String, List<String>> newHops, Map<String, List<Integer>> newDistances) {
        paths.addAll(newPaths);
        update(newHops, railwayHops);
        update(newDistances, railwayDistances);
    }

    private <K, V> void update(Map<K, List<V>> maps, Map<K, List<V>> sourceMaps) {
        for (Map.Entry<K, List<V>> map : maps.entrySet()) {
            final K key = map.getKey();
            final List<V> value = map.getValue();
            if (sourceMaps.containsKey(key)) {
                final List<V> origins = new ArrayList<>(sourceMaps.get(key));
                origins.addAll(value);
                sourceMaps.put(key, origins);
            } else {
                sourceMaps.put(key, value);
            }
        }
    }

    @Override
    public boolean canReach(String srcStation, String dstStation) {
        return srcStation.equals(dstStation) || paths.contains(srcStation + dstStation);
    }

    @Override
    public int getMinHopCount(String srcStation, String dstStation) {
        if (srcStation.equals(dstStation))
            return 1;
        if (!canReach(srcStation, dstStation))
            return 0xffff;
        final List<String> hops = railwayHops.get(srcStation + dstStation);
        Collections.sort(hops, (o1, o2) -> o1.length() - o2.length());
        return hops.get(0).length();
    }

    @Override
    public int getMinCostPath(String srcStation, String dstStation) {
        if (srcStation.equals(dstStation))
            return 0;
        if (!canReach(srcStation, dstStation))
            return 0xffff;
        final List<Integer> costs = railwayDistances.get(srcStation + dstStation);
        Collections.sort(costs, (o1, o2) -> o1 - o2);
        return costs.get(0);
    }

    public Map<String, List<String>> getHops() {
        return railwayHops;
    }

    public Map<String, List<Integer>> getDistances() {
        return railwayDistances;
    }

    public void clearAll() {
        paths.clear();
        railwayHops.clear();
        railwayDistances.clear();
    }

    public Set<String> getPath() {
        return paths;
    }
}
