import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
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
        final Map<Path, List<PathInfo>> ways = railway.getWays();
        assertThat(ways.get(new Path("b", "a")).get(0).getNodes().size(), is(2));
        assertThat(ways.get(new Path("b", "a")).get(0).getDistance(), is(1));
    }

    @Test
    public void should_not_record_way_when_src_dst_null() throws Exception {
        // given

        // when
        railway.addRoute(null, null, 1);
        // then
        final Map<Path, List<PathInfo>> ways = railway.getWays();
        assertThat(ways.isEmpty(), is(true));
    }

    @Test
    public void should_not_record_way_when_src_empty() throws Exception {
        // given

        // when
        railway.addRoute("", "b", 1);
        // then
        final Map<Path, List<PathInfo>> ways = railway.getWays();
        assertThat(ways.isEmpty(), is(true));
    }

    @Test
    public void should_not_record_way_when_dst_empty() throws Exception {
        // given

        // when
        railway.addRoute("g", "", 0);
        // then
        final Map<Path, List<PathInfo>> ways = railway.getWays();
        assertThat(ways.isEmpty(), is(true));
    }

    @Test
    public void should_route_ha_equals_route_hb_add_route_ba() throws Exception {
        // given
        railway.addRoute("b", "a", 1);
        // when
        railway.addRoute("h", "b", 1);
        // then
        final Map<Path, List<PathInfo>> ways = railway.getWays();
        assertThat(ways.size(), is(3));
        assertThat(ways.get(new Path("h", "a")).get(0).getNodes().size(), is(3));
        assertThat(ways.get(new Path("h", "a")).get(0).getDistance(), is(2));
    }

    @Test
    public void should_route_be_equals_multi_route() throws Exception {
        // given
        railway.addRoute("b", "c", 1);
        railway.addRoute("c", "e", 4);
        railway.addRoute("c", "d", 1);
        railway.addRoute("d", "e", 2);
        // when
        // then
        final Map<Path, List<PathInfo>> ways = railway.getWays();
        assertThat(ways.size(), is(6));
        assertThat(ways.get(new Path("b", "e")).size(), is(2));
        assertThat(ways.get(new Path("b", "e")).get(0).getNodes().size(), is(4));
        assertThat(ways.get(new Path("b", "e")).get(0).getDistance(), is(4));
        assertThat(ways.get(new Path("b", "e")).get(1).getNodes().size(), is(3));
        assertThat(ways.get(new Path("b", "e")).get(1).getDistance(), is(5));
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

    @Test
    public void should_reachable_from_b_to_f() throws Exception {
        // given
        railway.addRoute("b", "a", 1);
        // when
        railway.addRoute("a", "f", 1);
        // then
        assertThat(railway.canReach("b", "f"), is(true));
    }

    @Test
    public void should_unreachable_from_b_to_g() throws Exception {
        // given

        // when

        // then
        assertThat(railway.canReach("b", "g"), is(false));
    }

    @Test
    public void should_shortest_path_cost_0_from_b_to_b() throws Exception {
        // given

        // when

        // then
        assertThat(railway.getMinCostPath("b", "b"), is(0));
    }

    @Test
    public void should_shortest_path_cost_1_from_b_to_a() throws Exception {
        // given
        initRoutes();
        // when

        // then
        assertThat(railway.getMinCostPath("b", "a"), is(1));
    }

    @Test
    public void should_shortest_path_cost_0xffff_from_b_to_g() throws Exception {
        // given
        initRoutes();
        // when

        // then
        assertThat(railway.getMinCostPath("b", "g"), is(0xffff));
    }

    @Test
    public void should_shortest_path_cost_4_from_b_to_e() throws Exception {
        // given
        initRoutes();
        // when

        // then
        assertThat(railway.getMinCostPath("b", "e"), is(4));
    }
    
    @Test
    public void should_minimum_hop_cop_1_from_b_to_b() throws Exception {
        // given
        initRoutes();
        // when

        // then
        assertThat(railway.getMinHopCount("b", "b"), is(1));
    }

    @Test
    public void should_minimum_hop_cop_2_from_b_to_a() throws Exception {
        // given
        initRoutes();
        // when

        // then
        assertThat(railway.getMinHopCount("b", "a"), is(2));
    }

    @Test
    public void should_minimum_hop_cop_0xffff_from_b_to_g() throws Exception {
        // given
        initRoutes();
        // when

        // then
        assertThat(railway.getMinHopCount("b", "g"), is(0xffff));
    }
    
    @Test
    public void should_minimum_hop_cop_3_from_b_to_e() throws Exception {
        // given
        initRoutes();
        // when

        // then
        assertThat(railway.getMinHopCount("b", "e"), is(3));
    }

    private void initRoutes() {
        railway.addRoute("h", "b", 1);
        railway.addRoute("b", "a", 1);
        railway.addRoute("a", "f", 1);
        railway.addRoute("b", "c", 1);
        railway.addRoute("c", "e", 4);
        railway.addRoute("c", "d", 1);
        railway.addRoute("d", "e", 2);
        railway.addRoute("e", "c", 5);
        railway.addRoute("e", "b", 3);
        railway.addRoute("g", "", 0);
    }
}
