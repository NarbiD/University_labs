package A;

import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        Plane plane = new Plane(new Plane.Engine.EngineBuilder().createEngine(), new Plane.Wing(12),
                new Plane.Wing(12), new Plane.Chassis(2));
        plane.setRoute(new Location(56, 45.5), new Location(50, 45));
        plane.printRoute();
        plane.printRouteToFile(new File("src/main/resources/f.txt"));
    }
}
