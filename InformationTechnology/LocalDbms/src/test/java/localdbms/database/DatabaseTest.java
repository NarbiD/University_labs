package localdbms.database;

import localdbms.database.exception.StorageException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Iterator;
import java.util.Random;
import java.util.stream.Stream;

public class DatabaseTest {

    private DatabaseFactory databaseFactory;
    private Iterator<String> namesIterator;

    @Before
    public void init() {
        final int NAME_LENGTH = 8;
        namesIterator = SequenceGenerator.getStringGenerator(NAME_LENGTH).distinct().iterator();
        databaseFactory = DatabaseImpl::new;
    }

    @Test
    public void createDatabase() throws StorageException {
        String dbName = namesIterator.next();
        Database db = databaseFactory.getDatabase();
        db.setName(dbName);
        Assert.assertFalse(Databases.doesDatabaseExist(dbName));
        db.save();
        Assert.assertTrue(Databases.doesDatabaseExist(dbName));
    }

    @Test
    public void createTable() throws StorageException {
        Database db = databaseFactory.getDatabase();
        db.setName(namesIterator.next());
        String tableName = namesIterator.next();

        Assert.assertFalse(db.doesTableExist(tableName));
        db.createTable(tableName, DataType.INTEGER);
        db.save();
        Assert.assertTrue(db.doesTableExist(tableName));
    }

    @Test
    public void createFewTables() throws StorageException {
        Database db = databaseFactory.getDatabase();
        db.setName(namesIterator.next());
        String tableName1 = namesIterator.next();
        String tableName2 = namesIterator.next();

        Assert.assertFalse(db.doesTableExist(tableName1));
        Assert.assertFalse(db.doesTableExist(tableName2));

        db.createTable(tableName1, DataType.CHAR);
        db.createTable(tableName2, DataType.CHAR);
        db.save();

        Assert.assertTrue(db.doesTableExist(tableName1));
        Assert.assertTrue(db.doesTableExist(tableName2));
    }

    @Test
    public void deleteTable() throws StorageException {
        Database db = databaseFactory.getDatabase();
        db.setName(namesIterator.next());
        String tableName = namesIterator.next();
        db.createTable(tableName, DataType.REAL);
        db.save();
        Assert.assertTrue(db.doesTableExist(tableName));
        db.deleteTable(tableName);
        Assert.assertFalse(db.doesTableExist(tableName));
    }

    @Test
    public void deleteDatabaseWithTables() throws StorageException {
        String dbName = namesIterator.next();
        String tableName1 = namesIterator.next();
        String tableName2 = namesIterator.next();
        Database db = databaseFactory.getDatabase();
        db.setName(dbName);
        db.createTable(tableName1, DataType.REAL);
        db.createTable(tableName2, DataType.REAL);
        db.save();
        Assert.assertTrue(Databases.doesDatabaseExist(dbName));
        db.delete();
        Assert.assertFalse(Databases.doesDatabaseExist(dbName));
    }

    @Test
    public void deleteDatabaseWithoutTables() throws StorageException {
        String dbName = namesIterator.next();
        Database db = databaseFactory.getDatabase();
        db.setName(dbName);
        db.save();
        Assert.assertTrue(Databases.doesDatabaseExist(dbName));
        db.delete();
        Assert.assertFalse(Databases.doesDatabaseExist(dbName));
    }
}
