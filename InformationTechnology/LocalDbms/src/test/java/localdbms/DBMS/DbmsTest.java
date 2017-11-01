package localdbms.DBMS;

import localdbms.DBMS.database.Database;
import localdbms.DBMS.database.DatabaseFactory;
import localdbms.DBMS.database.DatabaseImpl;
import localdbms.DBMS.exception.StorageException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Optional;

public class DbmsTest {

    private DatabaseFactory databaseFactory;

    @Before
    public void init() throws StorageException {
        databaseFactory = DatabaseImpl::new;
    }

    @Test
    public void createDbmsWhenFewDatabasesExists() throws StorageException {
        Database db1 = databaseFactory.getDatabase();
        Database db2 = databaseFactory.getDatabase();
        db1.setName("db1");
        db2.setName("db2");
        db1.save();
        db2.save();
        Dbms dbms = new DbmsImpl();
        dbms.setDatabases(new ArrayList<>(Arrays.asList(db1, db2)));
        Assert.assertEquals(dbms.getDatabase(db1.getName()), Optional.of(db1));
        Assert.assertTrue(dbms.getAllDatabases().containsAll(Arrays.asList(db1, db2)));
    }

    @Test
    public void deleteDatabase() throws StorageException {
        Dbms dbms = new DbmsImpl();
        dbms.setDatabases(new ArrayList<>());
        String dbName = "db1";
        dbms.createDatabase(dbName);
        Assert.assertTrue(dbms.getDatabase(dbName).isPresent());
        dbms.deleteDatabase(dbName);
        Assert.assertFalse(dbms.getDatabase(dbName).isPresent());
    }

    @Test
    public void createDatabase() throws StorageException {
        Dbms dbms = new DbmsImpl();
        dbms.setDatabases(new ArrayList<>());

        String dbName = "db1";
        dbms.createDatabase(dbName);
        Assert.assertTrue(dbms.getDatabase(dbName).isPresent());
    }


}
