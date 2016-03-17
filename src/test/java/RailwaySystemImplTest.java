import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class RailwaySystemImplTest {
    private RailwaySystemImpl railway;

    @Before
    public void setUp() throws Exception {
        railway = new RailwaySystemImpl();
    }

    @After
    public void tearDown() throws Exception {
        railway.clearAllWays();

    }

    @Test
    public void should_throgh_b_and_a_with_distance_1_when_add_route_b_a() throws Exception {
        // given

        // when
        railway.addRoute("b", "a", 1);
        // then
        final Map<Path, PathInfo> ways = railway.getWays();
        assertThat(ways.get(new Path("b", "a")).getNodes().size(), is(2));
        assertThat(ways.get(new Path("b", "a")).getDistance(), is(1));
    }

    @Test
    public void should_not_record_way_when_src_dst_null() throws Exception {
        // given

        // when
        railway.addRoute(null, null, 1);
        // then
        final Map<Path, PathInfo> ways = railway.getWays();
        assertThat(ways.isEmpty(), is(true));
    }

    @Test
    public void should_not_record_way_when_src_empty() throws Exception {
        // given

        // when
        railway.addRoute("", "b", 1);
        // then
        final Map<Path, PathInfo> ways = railway.getWays();
        assertThat(ways.isEmpty(), is(true));
    }

    @Test
    public void should_not_record_way_when_dst_empty() throws Exception {
        // given

        // when
        railway.addRoute("g", "", 0);
        // then
        final Map<Path, PathInfo> ways = railway.getWays();
        assertThat(ways.isEmpty(), is(true));
    }

    @Test
    public void should_route_ha_equals_route_hb_add_route_ba() throws Exception {
        // given
        railway.addRoute("b", "a", 1);
        // when
        railway.addRoute("h", "b", 1);
        // then
        final Map<Path, PathInfo> ways = railway.getWays();
        assertThat(ways.size(), is(3));
        assertThat(ways.get(new Path("h", "a")).getNodes().size(), is(3));
        assertThat(ways.get(new Path("h", "a")).getDistance(), is(2));
    }

    @Test
    public void should_reachable_from_b_to_b() throws Exception {
        // given
        // when

        // then
        assertThat(railway.canReach("b", "b"), is(true));
    }

    @Test
    public void should_reachable_from_b_to_a() throws Exception {
        // given
        railway.addRoute("b", "a", 1);
        // when

        // then
        assertThat(railway.canReach("b", "a"), is(true));
        assertThat(railway.canReach("a", "b"), is(false));
    }
}