package A6;

import java.util.List;

public class BuildingDirector implements Director {
    private String name;
    private double wage;
    private List<Employe> workers;

    public BuildingDirector(String name, double wage, List<Employe> workers) {
        this.name = name;
        this.wage = wage;
        this.workers = workers;
    }

    @Override
    public void layOff(Employe employe) {
        this.workers.remove(employe);
    }

    @Override
    public void changeWages(Employe employe, double newWage) {
        employe.setWage(newWage);
    }

    @Override
    public void work() {
        // do something
    }

    @Override
    public synchronized void getAWage(double amount) {
        this.wage += amount;
    }

    @Override
    public void setWage(double amount) {
        this.wage = amount;
    }

    @Override
    public void goToDinner(int minutes) {
        // stop work for n minutes
    }

    @Override
    public void goHome() {
        // stop work
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getWage() {
        return wage;
    }

    public List<Employe> getWorkers() {
        return workers;
    }

    public void setWorkers(List<Employe> workers) {
        this.workers = workers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BuildingDirector that = (BuildingDirector) o;

        if (!name.equals(that.name)) return false;
        return workers.equals(that.workers);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + workers.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "BuildingDirector{" +
                "name='" + name + '\'' +
                ", wage=" + wage +
                ", workers=" + workers +
                '}';
    }
}
