package B;

import java.util.ArrayList;
import java.util.List;

public class SearchAppliance {

    private List<Appliance> appliances;
    private double maxPower = Double.MAX_VALUE;
    private double minPower = 0.0;
    private String producer = "";
    private String model = "";
    private Appliance.Size size = Appliance.Size.UNKNOWN;

    public SearchAppliance(List<Appliance> appliances) {
        this.appliances = appliances;
    }

    public SearchAppliance setMaxPower(double maxPower) {
        this.maxPower = maxPower;
        return this;
    }

    public SearchAppliance setMinPower(double minPower) {
        this.minPower = minPower;
        return this;
    }

    public SearchAppliance setProducer(String producer) {
        this.producer = producer;
        return this;
    }

    public SearchAppliance setModel(String model) {
        this.model = model;
        return this;
    }

    public SearchAppliance setSize(Appliance.Size size) {
        this.size = size;
        return this;
    }

    public List<Appliance> find() {
        List<Appliance> result = new ArrayList<>();
        appliances.forEach(a -> {
            if (maxPower >= a.getPower() && minPower <= a.getPower() &&
                    (producer.equals("") || producer.equals(a.getProducer())) &&
                    (model.equals("") || model.equals(a.getModel())) &&
                    (size == Appliance.Size.UNKNOWN || size.equals(a.getSize()))) {
                result.add(a);
            }
        } );
        return result;
    }
}
