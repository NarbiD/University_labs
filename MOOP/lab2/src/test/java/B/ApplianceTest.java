package B;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ApplianceTest {
    private List<Appliance> appliances;

    @Before
    public void init() {
        appliances = new ArrayList<>();
        appliances.add(new Fridge(500, Appliance.Size.XL, "NORD", "qw12"));
        appliances.add(new Fridge(600, Appliance.Size.L, "NORD", "qw11"));
        appliances.add(new Fridge(300, Appliance.Size.XL, "Saturn", "Ф43"));
        appliances.add(new MicrowaveOven(500, "Saturn", "f23", Appliance.Size.M));
        appliances.add(new MicrowaveOven(380, "Saturn", "f43", Appliance.Size.S));
        appliances.add(new Multicooker(630, "Saturn", "kar4", Appliance.Size.M, 4.5));
        appliances.add(new Multicooker(320, "Saturn", "k34", Appliance.Size.M, 4.5));
        appliances.add(new Vacuum(700,"Philips", "fd4", Appliance.Size.L, "red"));

        appliances.get(1).setPluggedIn(true);
        appliances.get(4).setPluggedIn(true);
        appliances.get(5).setPluggedIn(true);
    }

    @Test
    public void searchTest() {
        List<Appliance> apps = new SearchAppliance(appliances).setMaxPower(510).setMinPower(320).setProducer("Saturn")
                .setSize(Appliance.Size.M).find();
        System.out.println("Устройства, которые удовлетвояют заданные параметры: \n" + apps + "\n");
    }

    @Test
    public void plugInTest() {
        double sum = 0.0;
        for (Appliance appliance : appliances) {
            if (appliance.isPluggedIn()) {
                sum += appliance.getPower();
            }
        }
        System.out.println("Суммарная мощность включенных устройств равна " + sum + " ватт.\n");
    }

    @Test
    public void sortByPowerTest() {
        appliances.sort(Comparator.comparingDouble(Appliance::getPower));

        System.out.println("Сортировка устройств по мощности: \n" + appliances + "\n");
    }

}
