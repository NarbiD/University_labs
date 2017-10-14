package localdbms.database;

import localdbms.database.exception.StorageException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Optional;

public class DbmsTest {

    private DatabaseFactory databaseFactory;
    private Iterator<String> namesIterator;

    @Before
    public void init() throws StorageException {
        int NAME_LENGTH = 8;
        databaseFactory = DatabaseImpl::new;
        namesIterator = SequenceGenerator.getStringGenerator(NAME_LENGTH).distinct().iterator();
    }

    @Test
    public void createEmptyDbms() throws StorageException {
        createDatabase();
    }

    @Test
    public void createDbmsWhenFewDatabasesExists() throws StorageException {
        Database db1 = databaseFactory.getDatabase();
        Database db2 = databaseFactory.getDatabase();
        db1.setName(namesIterator.next());
        db2.setName(namesIterator.next());
        db1.save();
        db2.save();
        Dbms dbms = new DbmsImpl();
        Assert.assertEquals(dbms.getDatabase(db1.getName()), Optional.of(db1));
        Assert.assertTrue(dbms.getAllDatabases().containsAll(Arrays.asList(db1, db2)));
    }

    @Test
    public void deleteDatabase() throws StorageException {
        Dbms dbms = new DbmsImpl();
        String dbName = namesIterator.next();
        dbms.createDatabase(dbName);
        Assert.assertTrue(dbms.getDatabase(dbName).isPresent());
        dbms.deleteDatabase(dbName);
        Assert.assertFalse(dbms.getDatabase(dbName).isPresent());
    }

    @Test
    public void createDatabase() throws StorageException {
        Dbms dbms = new DbmsImpl();
        String dbName = namesIterator.next();
        dbms.createDatabase(dbName);
        Assert.assertTrue(dbms.getDatabase(dbName).isPresent());
    }


}
