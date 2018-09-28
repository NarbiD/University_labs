package A6;

import java.util.List;

public class BuildingEngineer implements Engineer {
    private int id;
    private String name;
    private double wage;
    private List<String> skills;

    public BuildingEngineer(int id, String name, double wage, List<String> skills) {
        this.id = id;
        this.name = name;
        this.wage = wage;
        this.skills = skills;
    }

    @Override
    public void goOnStrike() {
        // stop work while something happens
    }

    @Override
    public void work() {
        // do some work
    }

    @Override
    public synchronized void getAWage(double amount) {
        this.wage +=amount;
    }

    @Override
    public void goToDinner(int minutes) {
        // stop work for n minutes
    }

    @Override
    public void goHome() {
        // stop work
    }

    public double getWage() {
        return wage;
    }

    public void setWage(double wage) {
        this.wage = wage;
    }

    public List<String> getSkills() {
        return skills;
    }

    public void setSkills(List<String> skills) {
        this.skills = skills;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BuildingEngineer that = (BuildingEngineer) o;

        if (id != that.id) return false;
        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + name.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "BuildingEngineer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", wage=" + wage +
                ", skills=" + skills +
                '}';
    }
}
