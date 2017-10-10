package localdbms.database;

import java.util.Comparator;

public class EntryComparator implements Comparator<Entry> {

    private int fieldNumber;

    public EntryComparator(int fieldNumber) {
        this.fieldNumber = fieldNumber;
    }

    @Override
    public int compare(Entry o1, Entry o2) {
        Comparable cmp1 = o1.getValues().get(fieldNumber) instanceof Character ||
                o1.getValues().get(fieldNumber) instanceof String ? o1.getValues().get(fieldNumber).toString().toLowerCase() :
                (Comparable)o1.getValues().get(fieldNumber);
        Comparable cmp2 = o2.getValues().get(fieldNumber) instanceof Character ||
                o1.getValues().get(fieldNumber) instanceof String ? o2.getValues().get(fieldNumber).toString().toLowerCase() :
                (Comparable)o2.getValues().get(fieldNumber);
        return cmp1.compareTo(cmp2);
    }
}
