package localdbms.database;

import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Iterator;
import java.util.Random;
import java.util.stream.Stream;

public class DatabaseTest {

    private final int nameLength = 8;
    private Iterator<String> namesIterator;

    @Before
    public void init() {
        Stream<String> names = Stream.generate(() -> {
            Random random = new Random(System.nanoTime());
            StringBuilder name = new StringBuilder(nameLength);
            for (int i = 0; i < nameLength; i++) {
                name.append((char)('a' + Math.abs(random.nextInt()%26)));
            }
            return name.toString();
        });
        namesIterator = names.distinct().iterator();
    }


    @Test
    public void createDatabase() {
        String dbName = namesIterator.next();
        Database db = new Database(dbName);
        Assert.assertFalse(Database.isDatabaseExists(dbName));
        db.save();
        Assert.assertTrue(Database.isDatabaseExists(dbName));
    }

    @Test
    public void createTable() {
        String dbName = namesIterator.next();
        Database db = new Database(dbName);
        if (Table.isTableExists("table1", db.getLocation()))
            try {
                Table.delete("table1", db.getLocation());
            } catch (Exception e) {
                e.printStackTrace();
            }
        Assert.assertFalse(db.isTableExists("table1"));
        db.createTable("table1");
        db.save();
        Assert.assertTrue(db.isTableExists("table1"));
    }

    @Test
    public void createFewTables() {
        String dbName = namesIterator.next();
        Database db = new Database(dbName);
        if (Table.isTableExists("table1", db.getLocation()))
            try {
                Table.delete("table1", db.getLocation());
            } catch (Exception e) {
                e.printStackTrace();
            }
        if (Table.isTableExists("table2", db.getLocation()))
            try {
                Table.delete("table2", db.getLocation());
            } catch (Exception e) {
                e.printStackTrace();
            }
        Assert.assertFalse(db.isTableExists("table1"));
        Assert.assertFalse(db.isTableExists("table2"));
        db.createTable("table1");
        db.createTable("table2");
        db.save();
        Assert.assertTrue(db.isTableExists("table1"));
        Assert.assertTrue(db.isTableExists("table2"));
    }

    @Test
    public void deleteTable() {
        String dbName = namesIterator.next();
        Database db = new Database(dbName);
        db.createTable("table1");
        db.save();
        Assert.assertTrue(db.isTableExists("table1"));
        try {
            db.deleteTable("table1");
        } catch (Exception e) {
            e.printStackTrace();
        }
        Assert.assertFalse(db.isTableExists("table1"));
    }

    @Test
    public void deleteDatabaseWithTables() {
        String dbName = namesIterator.next();
        Database db = new Database(dbName);
        db.createTable("table1");
        db.createTable("table2");
        db.save();
        Assert.assertTrue(Database.isDatabaseExists(dbName));
        try {
            db.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Assert.assertFalse(Database.isDatabaseExists(dbName));
    }

    @Test
    public void deleteDatabaseWithoutTables() {
        String dbName = namesIterator.next();
        Database db = new Database(dbName);
        db.save();
        Assert.assertTrue(Database.isDatabaseExists(dbName));
        try {
            db.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Assert.assertFalse(Database.isDatabaseExists(dbName));
    }
}
