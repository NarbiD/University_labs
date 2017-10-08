package localdbms.database;

import org.junit.Assert;
import org.junit.Test;

public class DatabaseTest {

    @Test
    public void createDatabase() {
        String dbName = "1687zw4Lksc";
        Database db = new Database(dbName);
        try {
            db.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Assert.assertFalse(Database.isDatabaseExists(dbName));
        db.save();
        Assert.assertTrue(Database.isDatabaseExists(dbName));
    }

    @Test
    public void createTable() {
        String dbName = "1587zw4Lksc";
        Database db = new Database(dbName);
        if (Table.isTableExists("table1", db))
            try {
                Table.delete("table1", db);
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
        String dbName = "HK4f5MXl895";
        Database db = new Database(dbName);
        if (Table.isTableExists("table1", db))
            try {
                Table.delete("table1", db);
            } catch (Exception e) {
                e.printStackTrace();
            }
        if (Table.isTableExists("table2", db))
            try {
                Table.delete("table2", db);
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
        String dbName = "2E2N87lm3o3";
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
        String dbName = "d43c9Y73UK7";
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
        String dbName = "8w8TQfbU401";
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
