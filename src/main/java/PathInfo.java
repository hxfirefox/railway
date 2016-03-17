import java.util.LinkedList;

public class PathInfo {
    private final LinkedList<String> nodes;
    private final int distance;

    public PathInfo(LinkedList<String> nodes, int distance) {
        this.nodes = nodes;
        this.distance = distance;
    }

    public LinkedList<String> getNodes() {
        return nodes;
    }

    public int getDistance() {
        return distance;
    }
}
