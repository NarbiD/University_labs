package DBMS.datatype.constraint;

import java.rmi.RemoteException;

public class RealConstraint extends Constraint<Double> {

    public RealConstraint() throws RemoteException {
        this(Double.MIN_VALUE, Double.MAX_VALUE);
    }

    public RealConstraint(Double minValue, Double maxValue) throws RemoteException {
        super(minValue, maxValue);
    }

    public boolean isDefined() {
        return !(Double.MAX_VALUE == maxValue && Double.MIN_VALUE == minValue);
    }
}
