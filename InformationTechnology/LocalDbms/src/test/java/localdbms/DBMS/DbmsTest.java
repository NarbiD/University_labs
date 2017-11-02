package localdbms.DBMS;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

public class DbmsTest {

    @Test
    public void createDbmsWhenFewDatabasesExists() throws Exception {
        Database db1 = new Database();
        Database db2 = new Database();
        db1.setName("db1");
        db2.setName("db2");
        db1.save();
        db2.save();
        Dbms dbms = new Dbms();
        dbms.setDatabases(new ArrayList<>(Arrays.asList(db1, db2)));
        Assert.assertEquals(dbms.getDatabase(db1.getName()), Optional.of(db1));
        Assert.assertTrue(dbms.getAllDatabases().containsAll(Arrays.asList(db1, db2)));
    }

    @Test
    public void deleteDatabase() throws Exception {
        Dbms dbms = new Dbms();
        dbms.setDatabases(new ArrayList<>());
        String dbName = "db1";
        dbms.createDatabase(dbName);
        Assert.assertTrue(dbms.getDatabase(dbName).isPresent());
        dbms.deleteDatabase(dbName);
        Assert.assertFalse(dbms.getDatabase(dbName).isPresent());
    }

    @Test
    public void createDatabase() throws Exception {
        Dbms dbms = new Dbms();
        dbms.setDatabases(new ArrayList<>());

        String dbName = "db1";
        dbms.createDatabase(dbName);
        Assert.assertTrue(dbms.getDatabase(dbName).isPresent());
    }


}
