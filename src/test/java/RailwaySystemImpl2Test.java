import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class RailwaySystemImpl2Test {
    private RailwaySystemImpl2 railway;

    @Before
    public void setUp() throws Exception {
        railway = new RailwaySystemImpl2();
    }

    @After
    public void tearDown() throws Exception {
        railway.clearAll();
    }

    @Test
    public void should_throgh_b_and_a_with_distance_1_when_add_route_b_a() throws Exception {
        // given

        // when
        railway.addRoute("b", "a", 1);
        // then
        assertThat(railway.getHops().get("ba").size(), is(1));
        assertThat(railway.getHops().get("ba").get(0).length(), is(2));
        assertThat(railway.getDistances().get("ba").size(), is(1));
        assertThat(railway.getDistances().get("ba").get(0), is(1));
    }

    @Test
    public void should_not_record_way_when_src_dst_null() throws Exception {
        // given

        // when
        railway.addRoute(null, null, 1);
        // then
        assertThat(railway.getHops().isEmpty(), is(true));
        assertThat(railway.getDistances().isEmpty(), is(true));
    }

    @Test
    public void should_not_record_way_when_src_empty() throws Exception {
        // given

        // when
        railway.addRoute("", "b", 1);
        // then
        assertThat(railway.getHops().isEmpty(), is(true));
        assertThat(railway.getDistances().isEmpty(), is(true));
    }

    @Test
    public void should_not_record_way_when_dst_empty() throws Exception {
        // given

        // when
        railway.addRoute("g", "", 0);
        // then
        assertThat(railway.getHops().isEmpty(), is(true));
        assertThat(railway.getDistances().isEmpty(), is(true));
    }

    @Test
    public void should_route_ha_equals_route_hb_add_route_ba() throws Exception {
        // given
        railway.addRoute("b", "a", 1);
        // when
        railway.addRoute("h", "b", 1);
        // then
        assertThat(railway.getPath().size(), is(3));
        assertThat(railway.getHops().get("ha").get(0).length(), is(3));
        assertThat(railway.getDistances().get("ha").get(0), is(2));
    }

    @Test
    public void should_route_ha_equals_route_ba_add_route_hb() throws Exception {
        // given
        railway.addRoute("h", "b", 1);
        // when
        railway.addRoute("b", "a", 1);
        // then
        assertThat(railway.getPath().size(), is(3));
        assertThat(railway.getHops().get("ha").get(0).length(), is(3));
        assertThat(railway.getDistances().get("ha").get(0), is(2));
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
        assertThat(railway.getPath().size(), is(6));
        assertThat(railway.getHops().get("be").size(), is(2));
        assertThat(railway.getHops().get("be").get(0).length(), is(3));
        assertThat(railway.getDistances().get("be").get(0), is(5));
        assertThat(railway.getHops().get("be").get(1).length(), is(4));
        assertThat(railway.getDistances().get("be").get(1), is(4));
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

    @Test
    public void should_minimun_path_from_e_to_c_is_4() throws Exception {
        // given
        initRoutes();
        // when

        // then
        assertThat(railway.getMinCostPath("e", "c"), is(4));
    }

    @Test
    public void should_be_self_when_get_minimun_hop_path() throws Exception {
        // given

        // when

        // then
        assertThat(railway.getMinHopPathStations("e", "e"), is("e"));
    }

    @Test
    public void should_bc_from_b_to_c() throws Exception {
        // given
        initRoutes();
        // when

        // then
        assertThat(railway.getMinHopPathStations("b", "c"), is("bc"));
    }

    @Test
    public void should_bce_from_b_to_e() throws Exception {
        // given
        initRoutes();
        // when

        // then
        assertThat(railway.getMinHopPathStations("b", "e"), is("bce"));
    }

    @Test
    public void should_empty_from_b_to_h() throws Exception {
        // given
        initRoutes();
        // when

        // then
        assertThat(railway.getMinHopPathStations("b", "h"), is(""));
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
