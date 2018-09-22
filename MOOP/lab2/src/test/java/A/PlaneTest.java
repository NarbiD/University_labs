package A;

import org.junit.Test;

public class PlaneTest {
    private Plane plane;

    private void initPlane() {
        plane = new Plane(new Plane.Engine.EngineBuilder().createEngine(), new Plane.Wing(12), new Plane.Wing(12), new Plane.Chassis(2));

    }

    private void init() {
        initPlane();
        plane.setRoute(new Location(3.2123, 39.4), new Location(34.5, 34.55),
                new Location(23.3343,32.4), new Location(85.41, 59.5432));
    }

    @Test
    public void singleFlyingTest() throws EmptyRouteException {
        System.out.println("Тест одного полёта:\n");
        init();
        plane.printRoute();
        plane.fly();
        plane.printRoute();
    }

    @Test
    public void multipleFlyingTest() throws EmptyRouteException {
        System.out.println("Тест нескольких полётов:\n");
        init();
        plane.printRoute();
        plane.fly();
        plane.printRoute();
        plane.fly();
        plane.printRoute();
        plane.fly();
    }

    @Test(expected = EmptyRouteException.class)
    public void flyingWithEmptyRouteTest() throws EmptyRouteException {
        initPlane();
        plane.setRoute();
        plane.printRoute();
        plane.fly();
    }
}
