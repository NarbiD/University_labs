package A6;

public interface Director extends Employe {
    void layOff(Employe employe);
    void changeWages(Employe employe, double newWage);
}
