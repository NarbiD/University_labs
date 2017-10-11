package localdbms.database;

import java.util.Comparator;

public class EntryComparator implements Comparator<Entry> {

    private int fieldNumber;

    public EntryComparator(int fieldNumber) {
        this.fieldNumber = fieldNumber;
    }

    @Override
    @SuppressWarnings("unchecked")
    public int compare(Entry entry1, Entry entry2) {
        Comparable cmp1 = getComparableElement(entry1);
        Comparable cmp2 = getComparableElement(entry2);
        return cmp1.compareTo(cmp2);
    }

    private Comparable<? extends Comparable> getComparableElement(Entry entry) {
        Comparable field = (Comparable)entry.getValues().get(fieldNumber);
        return (field instanceof Character || field instanceof String) ? field.toString().toLowerCase() : field;
    }
}
