package A6;

import org.junit.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EmployeTest {

    @Test
    public void createEmploys() {
        List<Employe> employs = new ArrayList<>(Arrays.asList(
                new BuildingEngineer(0, "Alex", 20_000, Arrays.asList("build", "to paint walls")),
                new BuildingEngineer(1,"Mustafa", 14_000, Arrays.asList("build", "wallpapering"))));
        BuildingDirector buildingDirector = new BuildingDirector("Stanislav", 50_000, new ArrayList<>(employs));
        employs.add(buildingDirector);
        System.out.println("All employs:");
        employs.forEach(System.out::println);
        System.out.println("Stanislav's employs:");
        buildingDirector.getWorkers().forEach(System.out::println);

    }
}
