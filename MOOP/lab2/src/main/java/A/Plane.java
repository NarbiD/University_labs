package A;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Plane {

    private Engine engine;
    private Wing leftWing, rightWing;
    private Chassis chassis;

    private Location currentLocation;
    private LinkedList<Location> route = new LinkedList<>();

    public Plane() {
    }

    public Plane(Engine engine, Wing leftWing, Wing rightWing, Chassis chassis) {
        this.engine = engine;
        this.leftWing = leftWing;
        this.rightWing = rightWing;
        this.chassis = chassis;
    }

    static class Engine {

    }

    static class Chassis {

    }

    static class Wing {

    }

    public void fly() throws EmptyRouteException{
        Location loc = route.pollFirst();
        if (loc != null) {
            System.out.println("Полёт в " + loc.toString() + "...\n");
            currentLocation = loc;
        }
        else {
            throw new EmptyRouteException();
        }
    }

    public void printRoute() {
        System.out.println("Текущий маршрут:");
        route.forEach(loc -> System.out.println("-> " + loc));
        System.out.println();
    }

    public void setRoute(List<Location> route) {
        this.route.addAll(route);
    }

    public void setRoute(Location ... route) {
        this.route.addAll(Arrays.asList(route));
    }

    public Location getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(Location currentLocation) {
        this.currentLocation = currentLocation;
    }

    public Engine getEngine() {
        return engine;
    }

    public void setEngine(Engine engine) {
        this.engine = engine;
    }

    public Wing getLeftWing() {
        return leftWing;
    }

    public void setLeftWing(Wing leftWing) {
        this.leftWing = leftWing;
    }

    public Wing getRightWing() {
        return rightWing;
    }

    public void setRightWing(Wing rightWing) {
        this.rightWing = rightWing;
    }

    public Chassis getChassis() {
        return chassis;
    }

    public void setChassis(Chassis chassis) {
        this.chassis = chassis;
    }

}
