package localdbms.DBMS;

import localdbms.DBMS.entry.EntryImpl;
import localdbms.DBMS.datatype.constraint.RealConstraint;
import localdbms.DataType;
import localdbms.DBMS.exception.StorageException;
import localdbms.DBMS.table.Table;
import localdbms.DBMS.table.TableFactory;
import localdbms.DBMS.table.TableImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

public class TableTest {

    private TableFactory tableFactory;

    @Before
    public void init() {
        tableFactory = TableImpl::new;
    }

    @Test
    public void createTable() throws StorageException {
        Table table = tableFactory.getTable();
        Assert.assertTrue(table.isEmpty());
    }

    @Test
    public void addEmptyRow() throws StorageException {
        Table table = tableFactory.getTable();
        Assert.assertTrue(table.isEmpty());
        table.addRows(new EntryImpl());
        Assert.assertFalse(table.isEmpty());
    }

    @Test
    public void addRow() throws StorageException {
        DataType[] dataTypes = {DataType.INTEGER, DataType.CHAR};
        Table table = tableFactory.getTable();
        table.setTypes(dataTypes);
        EntryImpl entry = new EntryImpl(Arrays.asList(12, 'c'), table.getTypes(), new RealConstraint());
        table.addRows(entry);
        Assert.assertFalse(table.isEmpty());
        Assert.assertTrue(entry.equals(table.getEntries().get(0)));
    }

    @Test
    public void sortIntTest() throws StorageException {
        DataType[] dataTypes = {DataType.INTEGER, DataType.CHAR};
        Table unsortedTable = tableFactory.getTable();
        unsortedTable.setTypes(dataTypes);
        unsortedTable.addRows(new EntryImpl(Arrays.asList(12, 'c'), unsortedTable.getTypes(), new RealConstraint()),
            new EntryImpl(Arrays.asList(2, 'd'), unsortedTable.getTypes(), new RealConstraint()),
            new EntryImpl(Arrays.asList(4, 'a'), unsortedTable.getTypes(), new RealConstraint()));
        Table sortedTable = tableFactory.getTable();
        sortedTable.setTypes(dataTypes);
        sortedTable.addRows(new EntryImpl(Arrays.asList(2, 'd'), unsortedTable.getTypes(), new RealConstraint()),
            new EntryImpl(Arrays.asList(4, 'a'), unsortedTable.getTypes(), new RealConstraint()),
            new EntryImpl(Arrays.asList(12, 'c'), unsortedTable.getTypes(), new RealConstraint()));
        Assert.assertFalse(unsortedTable.getEntries().equals(sortedTable.getEntries()));
        unsortedTable.sort(0);
        Assert.assertTrue(unsortedTable.getEntries().equals(sortedTable.getEntries()));
    }

    @Test
    public void sortCharTest() throws StorageException {
        DataType[] dataTypes = {DataType.INTEGER, DataType.CHAR};
        Table unsortedTable = tableFactory.getTable();
        unsortedTable.setTypes(dataTypes);
        unsortedTable.addRows(new EntryImpl(Arrays.asList(12, 'c'), unsortedTable.getTypes(), new RealConstraint()),
                new EntryImpl(Arrays.asList(2, 'D'), unsortedTable.getTypes(), new RealConstraint()),
                new EntryImpl(Arrays.asList(4, 'A'), unsortedTable.getTypes(), new RealConstraint()));
        Table sortedTable = tableFactory.getTable();
        sortedTable.setTypes(dataTypes);
        sortedTable.addRows(new EntryImpl(Arrays.asList(4, 'A'), sortedTable.getTypes(), new RealConstraint()),
            new EntryImpl(Arrays.asList(12, 'c'), sortedTable.getTypes(), new RealConstraint()),
            new EntryImpl(Arrays.asList(2, 'D'), sortedTable.getTypes(), new RealConstraint()));
        Assert.assertFalse(unsortedTable.getEntries().equals(sortedTable.getEntries()));
        unsortedTable.sort(1);
        Assert.assertTrue(unsortedTable.getEntries().equals(sortedTable.getEntries()));
    }

    @Test
    public void sortRealTest() throws StorageException {
        DataType[] dataTypes = {DataType.CHAR, DataType.REAL};
        Table unsortedTable = tableFactory.getTable();
        unsortedTable.setTypes(dataTypes);
        unsortedTable.addRows(new EntryImpl(Arrays.asList('c', 12.7545), unsortedTable.getTypes(), new RealConstraint()),
            new EntryImpl(Arrays.asList('d', 34.45), unsortedTable.getTypes(), new RealConstraint()),
            new EntryImpl(Arrays.asList('a', 24.1), unsortedTable.getTypes(), new RealConstraint()));
        Table sortedTable = tableFactory.getTable();
        sortedTable.setTypes(dataTypes);
        sortedTable.addRows(new EntryImpl(Arrays.asList('c', 12.7545), unsortedTable.getTypes(), new RealConstraint()),
            new EntryImpl(Arrays.asList('a', 24.1), unsortedTable.getTypes(), new RealConstraint()),
            new EntryImpl(Arrays.asList('d', 34.45), unsortedTable.getTypes(), new RealConstraint()));
        Assert.assertFalse(unsortedTable.getEntries().equals(sortedTable.getEntries()));
        unsortedTable.sort(1);
        Assert.assertTrue(unsortedTable.getEntries().equals(sortedTable.getEntries()));
    }
}
