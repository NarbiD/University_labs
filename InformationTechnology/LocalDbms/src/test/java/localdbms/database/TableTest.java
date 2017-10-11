package localdbms.database;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

public class TableTest {

    private final String LOCATION = "/test/";

    @Test
    public void createTable() {
        Table table = new Table("testTable", LOCATION);
        Assert.assertTrue(table.isEmpty());
    }

    @Test
    public void addEmptyRow() {
        Table table = new Table("testTable", LOCATION);
        Assert.assertTrue(table.isEmpty());
        table.addRows(new Entry());
        Assert.assertFalse(table.isEmpty());
    }

    @Test
    public void addRow() {
        DataType[] dataTypes = {DataType.INTEGER, DataType.CHAR};
        Table table = new Table("testTable", LOCATION, dataTypes);
        Entry entry = new Entry(table.getTypes(), Arrays.asList(12, 'c'));
        table.addRows(entry);
        Assert.assertFalse(table.isEmpty());
        Assert.assertTrue(entry.equals(table.getEntries().get(0)));
    }

    @Test
    public void sortIntTest() {
        DataType[] dataTypes = {DataType.INTEGER, DataType.CHAR};
        Table table1 = new Table("testTable1", LOCATION, dataTypes);
        table1.addRows(new Entry(table1.getTypes(), Arrays.asList(12, 'c')),
            new Entry(table1.getTypes(), Arrays.asList(2, 'd')),
            new Entry(table1.getTypes(), Arrays.asList(4, 'a')));
        Table table2 = new Table("testTable2", LOCATION, dataTypes);
        table2.addRows(new Entry(table2.getTypes(), Arrays.asList(2, 'd')),
            new Entry(table2.getTypes(), Arrays.asList(4, 'a')),
            new Entry(table2.getTypes(), Arrays.asList(12, 'c')));
        Assert.assertFalse(table1.getEntries().equals(table2.getEntries()));
        table1.sort(0);
        Assert.assertTrue(table1.getEntries().equals(table2.getEntries()));
    }

    @Test
    public void sortCharTest() {
        DataType[] dataTypes = {DataType.INTEGER, DataType.CHAR};
        Table table1 = new Table("testTable1", LOCATION, dataTypes);
        table1.addRows(new Entry(table1.getTypes(), Arrays.asList(12, 'c')),
            new Entry(table1.getTypes(), Arrays.asList(2, 'D')),
            new Entry(table1.getTypes(), Arrays.asList(4, 'A')));
        Table table2 = new Table("testTable2", LOCATION, dataTypes);
        table2.addRows(new Entry(table2.getTypes(), Arrays.asList(4, 'A')),
            new Entry(table2.getTypes(), Arrays.asList(12, 'c')),
            new Entry(table2.getTypes(), Arrays.asList(2, 'D')));
        Assert.assertFalse(table1.getEntries().equals(table2.getEntries()));
        table1.sort(1);
        Assert.assertTrue(table1.getEntries().equals(table2.getEntries()));
    }

    @Test
    public void sortRealTest() {
        DataType[] dataTypes = {DataType.CHAR, DataType.REAL};
        Table table1 = new Table("testTable1", LOCATION, dataTypes);
        table1.addRows(new Entry(table1.getTypes(), Arrays.asList('c', 12.7545)),
            new Entry(table1.getTypes(), Arrays.asList('d', 34.45)),
            new Entry(table1.getTypes(), Arrays.asList('a', 24.1)));
        Table table2 = new Table("testTable2", LOCATION, dataTypes);
        table2.addRows(new Entry(table1.getTypes(), Arrays.asList('c', 12.7545)),
            new Entry(table1.getTypes(), Arrays.asList('a', 24.1)),
            new Entry(table1.getTypes(), Arrays.asList('d', 34.45)));
        Assert.assertFalse(table1.getEntries().equals(table2.getEntries()));
        table1.sort(1);
        Assert.assertTrue(table1.getEntries().equals(table2.getEntries()));
    }


}
