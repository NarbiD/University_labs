package localdbms.database;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Iterator;
import java.util.Random;
import java.util.stream.Stream;

public class DatabaseTest {

    private final int NAME_LENGTH = 8;
    private Iterator<String> namesIterator;

    @Before
    public void init() {
        Stream<String> names = Stream.generate(() -> {
            Random random = new Random(System.nanoTime());
            StringBuilder name = new StringBuilder(NAME_LENGTH);
            for (int i = 0; i < NAME_LENGTH; i++) {
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
        String tableName = namesIterator.next();
        Database db = new Database(dbName);
        if (Table.isTableExists(tableName, db.getLocation()))
            try {
                Table.delete(tableName, db.getLocation());
            } catch (Exception e) {
                e.printStackTrace();
            }
        Assert.assertFalse(db.isTableExists(tableName));
        db.createTable(tableName);
        db.save();
        Assert.assertTrue(db.isTableExists(tableName));
    }

    @Test
    public void createFewTables() {
        String dbName = namesIterator.next();
        String tableName1 = namesIterator.next();
        String tableName2 = namesIterator.next();
        Database db = new Database(dbName);

        Assert.assertFalse(db.isTableExists(tableName1));
        Assert.assertFalse(db.isTableExists(tableName2));

        db.createTable(tableName1);
        db.createTable(tableName2);
        db.save();

        Assert.assertTrue(db.isTableExists(tableName1));
        Assert.assertTrue(db.isTableExists(tableName2));
    }

    @Test
    public void deleteTable() {
        String dbName = namesIterator.next();
        String tableName = namesIterator.next();
        Database db = new Database(dbName);
        db.createTable(tableName);
        db.save();
        Assert.assertTrue(db.isTableExists(tableName));
        try {
            db.deleteTable(tableName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Assert.assertFalse(db.isTableExists(tableName));
    }

    @Test
    public void deleteDatabaseWithTables() {
        String dbName = namesIterator.next();
        String tableName1 = namesIterator.next();
        String tableName2 = namesIterator.next();
        Database db = new Database(dbName);
        db.createTable(tableName1);
        db.createTable(tableName2);
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
