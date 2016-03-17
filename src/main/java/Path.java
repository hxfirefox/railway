public class Path {
    private final String src;
    private final String dst;

    public Path(String src, String dst) {
        this.src = src;
        this.dst = dst;
    }

    public String getSrc() {
        return src;
    }

    public String getDst() {
        return dst;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Path path = (Path) o;

        if (!src.equals(path.src)) return false;
        return dst.equals(path.dst);

    }

    @Override
    public int hashCode() {
        int result = src.hashCode();
        result = 31 * result + dst.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return src + "->" + dst;
    }
}
