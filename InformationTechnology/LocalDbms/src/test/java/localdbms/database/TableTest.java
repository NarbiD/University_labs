package localdbms.database;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

public class TableTest {

    private Database db;

    @Before
    public void init(){
        db = new Database("TestDB");
    }

    @Test
    public void createTable() {
        Table table = new Table("testTable", db.getLocation());
        Assert.assertTrue(table.getData().length()==0);
    }

    @Test
    public void addEmptyRow() {
        Table table = new Table("testTable1", db.getLocation());
        table.addRow(new Entry());
        Assert.assertTrue(table.getData().length()==1);
    }

    @Test
    public void sortIntTest() {
        Table table = new Table("testTable1", db.getLocation(), DataType.INTEGER, DataType.CHAR);
        table.addRow(new Entry(table.getTypes(), Arrays.asList(12, 'c')));
        table.addRow(new Entry(table.getTypes(), Arrays.asList(2, 'd')));
        table.addRow(new Entry(table.getTypes(), Arrays.asList(4, 'a')));
        Table table2 = new Table("testTable2", db.getLocation(), DataType.INTEGER, DataType.CHAR);
        table2.addRow(new Entry(table.getTypes(), Arrays.asList(2, 'd')));
        table2.addRow(new Entry(table.getTypes(), Arrays.asList(4, 'a')));
        table2.addRow(new Entry(table.getTypes(), Arrays.asList(12, 'c')));

        table.sort(0);
        Assert.assertTrue(table.getEntries().equals(table2.getEntries()));
    }

    @Test
    public void sortCharTest() {
        Table table = new Table("testTable1", db.getLocation(), DataType.INTEGER, DataType.CHAR);
        table.addRow(new Entry(table.getTypes(), Arrays.asList(12, 'c')));
        table.addRow(new Entry(table.getTypes(), Arrays.asList(2, 'd')));
        table.addRow(new Entry(table.getTypes(), Arrays.asList(4, 'a')));
        Table table2 = new Table("testTable2", db.getLocation(), DataType.INTEGER, DataType.CHAR);
        table2.addRow(new Entry(table.getTypes(), Arrays.asList(4, 'a')));
        table2.addRow(new Entry(table.getTypes(), Arrays.asList(12, 'c')));
        table2.addRow(new Entry(table.getTypes(), Arrays.asList(2, 'd')));
        table.sort(1);
        Assert.assertTrue(table.getEntries().equals(table2.getEntries()));
    }
}
