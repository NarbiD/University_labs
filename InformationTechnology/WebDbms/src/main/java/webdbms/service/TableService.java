package webdbms.service;

import webdbms.DBMS.*;
import webdbms.service.exception.TableNotFoundException;

import java.util.ArrayList;
import java.util.List;


public class TableService {

    private DatabaseService databaseService;

    public void deleteTable(String dbName, String tableName) throws Exception {
        databaseService.findByName(dbName).deleteTable(tableName);
    }

    public void createTable(String dbName, String tableName, List<DataType> types,
                            List<String> columnNames, IntegerInvlConstraint constraint) throws Exception {
        Database db = databaseService.findByName(dbName);
        db.addTable(new Table(tableName, dbName, types, columnNames, constraint));
        db.save();
    }

    public List<Entry> search(String dbName, String tableName, String rowTemplate) throws Exception {
        List<Entry> matchedEntries = new ArrayList<>();
        Table table = findByName(dbName, tableName);
        List<Entry> allEntries = table.getEntries();
        List<Object> objects = parseQueryString(rowTemplate, table.getTypes());
        for (Entry entry : allEntries) {
            if (isValuesEquals(entry.getValues(), objects)) {
                matchedEntries.add(entry);
            }
        }
        return matchedEntries;
    }

   private List<Object> parseQueryString(String query, List<DataType> types) throws Exception {
        String[] cellStrings = query.split(":");
        if (types.size() != cellStrings.length) {
            throw new Exception("Invalid query params");
        }
        List<Object> objects = new ArrayList<>();
        for (int i = 0; i < cellStrings.length; i++) {
            if (!cellStrings[i].equals("*")) {
                objects.add(TypeManager.parseObjectByType(cellStrings[i], types.get(i)));
            } else {
                objects.add(null);
            }
        }
        return objects;
    }

    private boolean isValuesEquals(List<Object> v1, List<Object> v2) {
        for (int i = 0; i < v1.size(); i++) {
            boolean isEquals = v1.get(i).equals(v2.get(i) instanceof Character ?(v2.get(i)).toString():v2.get(i));
            if (!(isEquals || v2.get(i) == null)) {
                return false;
            }
        }
        return true;
    }

    public Table findByName(String dbName, String tableName) throws Exception {
        Database db = databaseService.findByName(dbName);
        return db.getTables().stream()
                .filter(table -> tableName.equals(table.getName())).findAny()
                .orElseThrow(() -> new TableNotFoundException(dbName, tableName));
    }

    public void setDatabaseService(DatabaseService databaseService) {
        this.databaseService = databaseService;
    }
}
