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


    public Plane(Engine engine, Wing leftWing, Wing rightWing, Chassis chassis) {
        this.engine = engine;
        this.leftWing = leftWing;
        this.rightWing = rightWing;
        this.chassis = chassis;
    }

    public void fly() throws EmptyRouteException{
        Location loc = route.pollFirst();
        if (loc != null) {
            System.out.println("Полёт в " + loc.toString() + "...\n");
            currentLocation = loc;
        }
        else {
            throw new EmptyRouteException("Введён пустой маршрут");
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

    public void addDestinationIntoRoute(Location destination) {
        this.route.add(destination);
    }

    public static class Engine {
        int id;
        double power;
        double weight;
        String fuelType;
        String producer;
        String model;

        public Engine() {
        }

        public Engine(int id, double power, double weight, String fuelType, String producer, String model) {
            this.id = id;
            this.power = power;
            this.weight = weight;
            this.fuelType = fuelType;
            this.producer = producer;
            this.model = model;
        }

        static class EngineBuilder {
            private int id;
            private double power;
            private double weight;
            private String fuelType;
            private String producer;
            private String model;

            public EngineBuilder setId(int id) {
                this.id = id;
                return this;
            }

            public EngineBuilder setPower(double power) {
                this.power = power;
                return this;
            }

            public EngineBuilder setWeight(double weight) {
                this.weight = weight;
                return this;
            }

            public EngineBuilder setFuelType(String fuelType) {
                this.fuelType = fuelType;
                return this;
            }

            public EngineBuilder setProducer(String producer) {
                this.producer = producer;
                return this;
            }

            public EngineBuilder setModel(String model) {
                this.model = model;
                return this;
            }

            public Plane.Engine createEngine() {
                return new Plane.Engine(id, power, weight, fuelType, producer, model);
            }
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Engine engine = (Engine) o;

            if (Double.compare(engine.power, power) != 0) return false;
            if (Double.compare(engine.weight, weight) != 0) return false;
            if (fuelType != null ? !fuelType.equals(engine.fuelType) : engine.fuelType != null) return false;
            if (producer != null ? !producer.equals(engine.producer) : engine.producer != null) return false;
            return model != null ? model.equals(engine.model) : engine.model == null;
        }

        @Override
        public int hashCode() {
            int result;
            long temp;
            temp = Double.doubleToLongBits(power);
            result = (int) (temp ^ (temp >>> 32));
            temp = Double.doubleToLongBits(weight);
            result = 31 * result + (int) (temp ^ (temp >>> 32));
            result = 31 * result + (fuelType != null ? fuelType.hashCode() : 0);
            result = 31 * result + (producer != null ? producer.hashCode() : 0);
            result = 31 * result + (model != null ? model.hashCode() : 0);
            return result;
        }

        @Override
        public String toString() {
            return "Engine{" +
                    "id=" + id +
                    ", power=" + power +
                    ", weight=" + weight +
                    ", fuelType='" + fuelType + '\'' +
                    ", producer='" + producer + '\'' +
                    ", model='" + model + '\'' +
                    '}';
        }
    }

    static class Chassis {
        String producer;
        double diameter;

        public Chassis(double diameter, String producer) {
            this.producer = producer;
            this.diameter = diameter;
        }

        public Chassis(double diameter) {
            this(diameter, "unknown");
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Chassis chassis = (Chassis) o;

            if (Double.compare(chassis.diameter, diameter) != 0) return false;
            return producer != null ? producer.equals(chassis.producer) : chassis.producer == null;
        }

        @Override
        public int hashCode() {
            int result;
            long temp;
            result = producer != null ? producer.hashCode() : 0;
            temp = Double.doubleToLongBits(diameter);
            result = 31 * result + (int) (temp ^ (temp >>> 32));
            return result;
        }

        @Override
        public String toString() {
            return "Chassis{" +
                    "producer='" + producer + '\'' +
                    ", diameter=" + diameter +
                    '}';
        }
    }

    static class Wing {
        double length;
        String producer;

        public Wing(double length, String producer) {
            this.length = length;
            this.producer = producer;
        }

        public Wing(double length) {
            this(length, "unknown");
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Wing wing = (Wing) o;

            if (Double.compare(wing.length, length) != 0) return false;
            return producer != null ? producer.equals(wing.producer) : wing.producer == null;
        }

        @Override
        public int hashCode() {
            int result;
            long temp;
            temp = Double.doubleToLongBits(length);
            result = (int) (temp ^ (temp >>> 32));
            result = 31 * result + (producer != null ? producer.hashCode() : 0);
            return result;
        }

        @Override
        public String toString() {
            return "Wing{" +
                    "length=" + length +
                    ", producer='" + producer + '\'' +
                    '}';
        }
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
