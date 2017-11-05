package DBMS.datatype.constraint;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public abstract class Constraint<T extends Comparable<T>> extends UnicastRemoteObject implements Remote, Serializable {
    T minValue;
    T maxValue;

    Constraint(T minValue, T maxValue) throws RemoteException {
        super();
        this.minValue = minValue;
        this.maxValue = maxValue;
    }

    public T getMinValue() {
        return minValue;
    }

    public T getMaxValue() {
        return maxValue;
    }

    public boolean isValueValid(T value) {
        return value.compareTo(minValue) >= 0 && value.compareTo(maxValue) <= 0;
    }
}
